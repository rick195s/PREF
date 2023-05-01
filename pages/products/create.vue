<template>
  <div class="flex flex-wrap">
    <div class="w-full">
      <CardCreate
        :loading="loading"
        title="Create Product"
        :fields="fields"
        @create="createProduct($event)"
      />
    </div>

    <div class="w-full lg:w-8/12 px-4">
      <CardProductPackages
        :actions-enabled="selectedPackages.length < 3"
        @add-package="addPackage($event)"
      />
    </div>

    <div class="w-full lg:w-4/12 px-4">
      <CardSelectedProductPackages v-model="selectedPackages" />
    </div>
    <NotificationToast
      v-show="toastMessage"
      :message="toastMessage"
      :type="toastType"
      @close="toastMessage = ''"
    />
  </div>
</template>
<script setup>
import CardCreate from "@/components/Cards/CardCreate.vue";
import CardProductPackages from "@/components/Cards/CardProductPackages.vue";
import CardSelectedProductPackages from "@/components/Cards/CardSelectedProductPackages.vue";
import NotificationToast from "@/components/Toasts/NotificationToast.vue";

const selectedPackages = ref([]);
const toastMessage = ref("");
const toastType = ref("success");
const loading = ref(false);
const fields = ref([
  {
    label: "Name",
    name: "name",
    type: "text"
  },
  {
    label: "Category",
    name: "category",
    type: "text"
  },
  {
    label: "Price",
    name: "price",
    type: "text"
  },
  {
    label: "Weight",
    name: "weight",
    type: "text"
  },
  {
    label: "Validity Range",
    name: "validityRange",
    type: "text"
  },
  {
    label: "Dimensions",
    name: "dimensions",
    type: "text"
  }
]);

const createProduct = async (formData) => {
  if (!hasErrors(formData)) {
    const newProduct = { ...formData };
    newProduct.productPackages = { ...selectedPackages };

    loading.value = true;
    const { pending } = await useLazyAsyncData(
      "createProduct",
      () =>
        $fetch(`/api/products`, {
          method: "POST",
          body: JSON.stringify(newProduct),
          onResponseError: ({ request, options, response }) => {
            toastMessage.value = response._data?.reason;
            toastType.value = "error";
          },
          onResponse: ({ request, response, options }) => {
            toastMessage.value = "Product created successfully";
            toastType.value = "success";
          }
        }),

      {
        server: false
      }
    );

    loading.value = pending.value;
  }
};

const hasErrors = (formData) => {
  if (selectedPackages.value.length === 0) {
    toastMessage.value = "Please add packages to the product";
    toastType.value = "error";
    return true;
  }

  for (const field in fields) {
    if (!formData[field]) {
      toastMessage.value = "Please fill all the fields";
      toastType.value = "error";
      return true;
    }
  }

  return false;
};

const addPackage = (selectedPackage) => {
  if (
    !selectedPackages.value.find((object) => object.id === selectedPackage.id)
  ) {
    selectedPackages.value.push(selectedPackage);
  }
};
</script>
