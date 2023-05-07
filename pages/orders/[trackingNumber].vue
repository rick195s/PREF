<template>
  <div class="flex flex-wrap mt-4">
    <div class="w-full mb-12 xl:mb-0 px-4">
      <CardOrderDetails :order-data="orderData" />
      <CardOrderLinePackages :order-lines="orderData?.orderLines" />
      <CardOrderLines :order-lines="orderData?.orderLines" :loading="pending" />
    </div>
  </div>
</template>
<script setup>
import CardOrderDetails from "@/components/Cards/CardOrderDetails.vue";
import CardOrderLines from "~/components/Cards/CardOrderLines.vue";
import CardOrderLinePackages from "~/components/Cards/CardOrderLinesPackages.vue";

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
