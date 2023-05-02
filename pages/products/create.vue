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
    type: "number"
  },
  {
    label: "Weight",
    name: "weight",
    type: "number"
  },
  {
    label: "Validity Range",
    name: "validityRange",
    type: "number"
  },
  {
    label: "Dimensions (Length x Weigth x Height)",
    name: "dimensions",
    type: "text"
  }
]);

const createProduct = async (formData) => {
  if (!hasErrors(formData)) {
    const newProduct = {
      ...formData,
      ...getDimensionsSeparated(formData.dimensions)
    };
    delete newProduct.dimensions;
    newProduct.productPackages = selectedPackages.value.map(({ id }) => ({
      id: id
    }));
    console.log(newProduct);
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

  for (const field of fields.value) {
    if (!formData[field.name]) {
      toastMessage.value = "Please fill all the fields";
      toastType.value = "error";
      return true;
    }

    if (formData["dimensions"].trim().split("x").length !== 3) {
      toastMessage.value =
        "Dimensions field is incorrect (correct: L x W x H) ";
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

const getDimensionsSeparated = (dimensions) => {
  const dimensionsArray = dimensions.trim().split("x");
  return {
    length:
      Number(dimensionsArray[0]) !== (NaN || 0)
        ? Number(dimensionsArray[0])
        : 1,
    width:
      Number(dimensionsArray[1]) !== (NaN || 0)
        ? Number(dimensionsArray[1])
        : 1,
    height:
      Number(dimensionsArray[2]) !== (NaN || 0) ? Number(dimensionsArray[2]) : 1
  };
};
</script>
