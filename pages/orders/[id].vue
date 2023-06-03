<template>
  <div class="flex flex-wrap">
    <div class="w-full">
      <CardOrderDetails :order-data="orderData" :loading="pending" />
    </div>

    <div class="w-full lg:w-8/12" v-if="shouldRenderCardOrderPackages">
      <CardOrderPackages @add-order-package="addSelectedPackage($event)" />
    </div>

    <div
      class="w-full lg:w-4/12 md:px-4 py-4"
      v-if="shouldRenderCardOrderPackages"
    >
      <CardSelectedOrderPackages
        v-model="selectedPackages"
        @add-packages="associatePackagesWithOrder($event)"
      />
    </div>

    <div class="w-full">
      <CardOrderLines :order-lines="orderData?.orderLines" :loading="pending" />
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
import CardOrderDetails from "@/components/Cards/CardOrderDetails.vue";
import CardOrderLines from "~/components/Cards/CardOrderLines.vue";
import CardOrderPackages from "~/components/Cards/CardOrderPackages.vue";
import CardSelectedOrderPackages from "@/components/Cards/CardSelectedOrderPackages.vue";
import NotificationToast from "@/components/Toasts/NotificationToast.vue";

const selectedPackages = ref([]);
const toastMessage = ref("");
const toastType = ref("success");
const loading = ref(false);

const addSelectedPackage = (selectedPackage) => {
  selectedPackages.value.push(selectedPackage);
};

const { data: orderData, pending } = await useLazyAsyncData(
  "orderData",
  () => $fetch(`/api/orders/${useRoute().params.trackingNumber}`, {}),
  {
    server: false,
    transform: (data) => {
      data.orderLines.forEach((element) => {
        element.productId = element.product.id;
        element.productName = element.product.name;
        element.validityRange = element.product.validityRange;
        element.dimensions =
          element.product.length +
          "x" +
          element.product.width +
          "x" +
          element.product.height;
        element.weight = element.product.weight + "kg";
        element.price =
          (element.product.price * element.quantity).toFixed(2) + "€";
        element.category = element.product.category;
        element.productPrice = element.productPrice.toFixed(2) + "€";
      });
      return data;
    }
  }
);

const shouldRenderCardOrderPackages = computed(() => {
  return orderData?.value?.state === "PENDING";
});

//Add selectedPackages to order
const associatePackagesWithOrder = async () => {
  if (selectedPackages.value.length > 0) {
    for (const selectedPackage of selectedPackages.value) {
      loading.value = true;
      const { pending } = await useLazyAsyncData(
        "addPackage",
        async () => {
          // Make the callback function async
          const response = await $fetch(
            `/api/orders/${orderData.value.trackingNumber}`,
            {
              method: "PATCH",
              body: JSON.stringify({
                id: selectedPackage.id
              }),
              onResponseError: ({ request, options, response }) => {
                toastMessage.value = response._data?.reason;
                toastType.value = "error";
              },
              onResponse: ({ request, response, options }) => {
                toastMessage.value = "Packages added successfully";
                toastType.value = "success";
                //reload page
                location.reload();
              }
            }
          );
          return response;
        },
        {
          server: false
        }
      );
      loading.value = pending.value;
    }
  }
};
</script>
