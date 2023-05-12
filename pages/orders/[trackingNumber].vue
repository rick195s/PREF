<template>
  <div class="flex flex-wrap">
    <div class="w-full">
      <CardOrderDetails :order-data="orderData" :loading="pending" />
    </div>

    <div class="w-full lg:w-8/12 px-4" v-if="shouldRenderCardOrderPackages">
      <CardOrderPackages @add-order-package="addOrderPackage($event)" />
    </div>

    <div class="w-full lg:w-4/12 px-4" v-if="shouldRenderCardOrderPackages">
      <CardSelectedOrderPackages v-model="selectedPackages" @add-packages="addPackages($event)" />
    </div>

    <NotificationToast
      v-show="toastMessage"
      :message="toastMessage"
      :type="toastType"
      @close="toastMessage = ''"
    />
  </div>
  <CardOrderLines :order-lines="orderData?.orderLines" :loading="pending" />
</template>

<script setup>
import { ref, computed } from 'vue';
import CardOrderDetails from "@/components/Cards/CardOrderDetails.vue";
import CardOrderLines from "~/components/Cards/CardOrderLines.vue";
import CardOrderPackages from "~/components/Cards/CardOrderPackages.vue";
import CardSelectedOrderPackages from "@/components/Cards/CardSelectedOrderPackages.vue";

const selectedPackages = ref([]);
const toastMessage = ref("");
const toastType = ref("success");
const loading = ref(false);
const addOrderPackage = (selectedPackage) => {
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
        element.weight = element.product.weight;
        element.price = element.product.price;
        element.category = element.product.category;
      });
      return data;
    }
  }
);

const shouldRenderCardOrderPackages = computed(() => {
  return orderData?.value?.state === 'PENDING';
});

//Add selectedPackages to order
const addPackages = async () => {
  if (selectedPackages.value.length > 0) {
    for (const selectedPackage of selectedPackages.value) {
      console.log(selectedPackages);
      loading.value = true;
      const { pending } = await useLazyAsyncData(
        "addPackage",
        async () => { // Make the callback function async
          const response = await $fetch(`/api/orders/${orderData.value.trackingNumber}`, {
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
          });
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
