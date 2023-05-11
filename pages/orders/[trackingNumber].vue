<template>
  <div class="flex flex-wrap mt-4">
    <div class="w-full mb-12 xl:mb-0 px-4">
      <CardOrderLinePackages :order-lines="orderData?.orderLines" @package-selected="updateSelectedPackages" />
      <CardObservations :order-data="orderData"
                        :loading="pending" :selected-packages="selectedPackages"/>
      <CardOrderLines :order-lines="orderData?.orderLines" :loading="pending" />
      <CardOrderDetails :order-data="orderData" />

    </div>
  </div>
</template>
<script setup>
import CardOrderDetails from "@/components/Cards/CardOrderDetails.vue";
import CardOrderLines from "~/components/Cards/CardOrderLines.vue";
import CardOrderLinePackages from "~/components/Cards/CardOrderLinesPackages.vue";
import CardObservations from "~/components/Cards/CardObservations.vue";

const selectedPackages = ref([]);
const updateSelectedPackages = (payload) => {
  if(payload.checked){
    selectedPackages.value.push(payload.packageId);
  }else{
    selectedPackages.value = selectedPackages.value.filter((item) => item !== payload.packageId);
  }
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
</script>
