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

    <CardProductPackages />
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
import NotificationToast from "@/components/Toasts/NotificationToast.vue";

const selectedProducts = ref([]);
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
    newProduct.shippingMethods = newProduct.shippingMethods.split(" ");
    newProduct.productsQuantities =
      selectedProductsComponent.value.productsByQuantity.map(
        ({ id, quantity }) => ({
          productId: id,
          quantity
        })
      );

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
  if (selectedProducts.value.length === 0) {
    toastMessage.value = "Please add products to the order";
    toastType.value = "error";
    return true;
  }

  for (const field in formData) {
    if (!formData[field]) {
      toastMessage.value = "Please fill all the fields";
      toastType.value = "error";
      return true;
    }
  }

  return false;
};
</script>
