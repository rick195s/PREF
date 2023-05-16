<template>
  <div class="flex flex-wrap mt-4">
    <div class="w-full mb-12 xl:mb-0 px-4">
      <CardOrderPackagesOfOrder
        :order-packages="orderData?.orderPackages"
        @order-package-selected="updateSelectedPackages"
      />
      <CardOrderLinePackages
        :order-lines="orderData?.orderLines"
        @product-package-selected="updateSelectedPackages"
      />
      <CardObservations
        :loading="pending"
        :selected-packages="selectedPackages"
      />
    </div>
  </div>
</template>
<script setup>
import CardOrderLinePackages from "~/components/Cards/CardOrderLinesPackages.vue";
import CardOrderPackagesOfOrder from "~/components/Cards/CardOrderPackagesOfOrder.vue";
import CardObservations from "~/components/Cards/CardObservations.vue";

const selectedPackages = ref([]);

const updateSelectedPackages = (payload) => {
  if (payload.checked) {
    selectedPackages.value.push(payload.packageId);
  } else {
    // remove from selectedPackages
    selectedPackages.value.splice(
      selectedPackages.value.indexOf(payload.packageId),
      1
    );
  }
};

const { data: orderData, pending } = await useLazyAsyncData(
  "orderData",
  () => $fetch(`/api/orders/${useRoute().params.trackingNumber}`, {}),
  {
    server: false,
    transform: (data) => {
      data.orderLines.forEach((element) => {
        element.orderLineProductRelation.forEach((item) => {
          element.productId = item.product.id;
          element.productName = item.product.name;
          element.validityRange = item.product.validityRange;
          element.dimensions =
            item.product.length +
            "x" +
            item.product.width +
            "x" +
            item.product.height;
          element.weight = item.product.weight;
          element.price = item.product.price;
          element.category = item.product.category;
          //adicionar array orderLineProductPackages to product
          item.product.orderLineProductPackages = item.orderLineProductPackages;
        });
      });
      data.orderPackages.forEach((element) => {
        data.orderPackageTypes.forEach((item) => {
          if (item.id === element.simplePackageTypeId) {
            element.packageName = item.name;
            element.isSmart = item.smart
          }
        });
      });
      return data;
    }
  }
);

</script>
