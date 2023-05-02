<template>
  <div class="flex flex-wrap">
    <div class="w-full">
      <CardCreate
        :loading="loading"
        title="Create Order"
        :fields="fields"
        @create="createOrder($event)"
      />
    </div>
    <div class="w-full lg:w-8/12 px-4">
      <CardProducts @add-product="addProduct($event)" />
    </div>
    <div class="w-full lg:w-4/12 px-4">
      <CardSelectedProducts
        ref="selectedProductsComponent"
        :products="selectedProducts"
        @remove-product="removeProduct($event)"
      />
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
import CardProducts from "@/components/Cards/CardProducts.vue";
import CardSelectedProducts from "@/components/Cards/CardSelectedProducts.vue";
import NotificationToast from "@/components/Toasts/NotificationToast.vue";

const selectedProducts = ref([]);
const toastMessage = ref("");
const toastType = ref("success");
const loading = ref(false);
const selectedProductsComponent = ref(null);
const fields = ref([
  {
    label: "Source",
    name: "source",
    type: "text"
  },
  {
    label: "Destination",
    name: "destination",
    type: "text"
  },
  {
    label: "Carrier",
    name: "carrier",
    type: "text"
  },
  {
    label: "Shipping Methods",
    name: "shippingMethods",
    type: "text"
  }
]);

const addProduct = (product) => {
  selectedProducts.value.push(product);
};

const removeProduct = (id) => {
  const index = selectedProducts.value.findIndex(
    (product) => product.id === id
  );
  if (index !== -1) {
    selectedProducts.value.splice(index, 1);
  }
};

const createOrder = async (formData) => {
  if (!hasErrors(formData)) {
    const newOrder = { ...formData };
    newOrder.shippingMethods = newOrder.shippingMethods.split(" ");
    newOrder.productsQuantities =
      selectedProductsComponent.value.productsByQuantity.map(
        ({ id, quantity }) => ({
          productId: id,
          quantity
        })
      );

    loading.value = true;
    const { pending } = await useLazyAsyncData(
      "createOrder",
      () =>
        $fetch(`/api/orders`, {
          method: "POST",
          body: JSON.stringify(newOrder),
          onResponseError: ({ request, options, response }) => {
            toastMessage.value = response._data?.reason;
            toastType.value = "error";
          },
          onResponse: ({ request, response, options }) => {
            toastMessage.value = "Order created successfully";
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

  for (const field of fields.value) {
    if (!formData[field.name]) {
      toastMessage.value = "Please fill all the fields";
      toastType.value = "error";
      return true;
    }
  }

  return false;
};
</script>
