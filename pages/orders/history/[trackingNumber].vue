<template>
  <div class="flex flex-wrap mt-4">
    <div class="w-full mb-12 xl:mb-0 px-4">
      <CardCardOrderPackagesOfOrder :order-packages="orderData?.orderPackages" @order-package-selected="updateSelectedOrderPackages"/>
      <CardOrderLinePackages :order-lines="orderData?.orderLines" @product-package-selected="updateSelectedProductPackages" />
      <CardObservations :order-data="orderData"
                        :loading="pending" :selected-product-packages="selectedProductPackages" :selected-order-packages="selectedOrderPackages"/>

    </div>
  </div>
</template>
<script setup>
import CardOrderLinePackages from "~/components/Cards/CardOrderLinesPackages.vue";
import CardCardOrderPackagesOfOrder from "~/components/Cards/CardOrderPackagesOfOrder.vue";
import CardObservations from "~/components/Cards/CardObservations.vue";

const selectedProductPackages = ref([]);
const selectedOrderPackages = ref([]);
const updateSelectedProductPackages = (payload) => {
  if(payload.checked){
    selectedProductPackages.value.push(payload.packageId);
  }else{
    selectedProductPackages.value = selectedProductPackages.value.filter((item) => item !== payload.packageId);
  }
};

const updateSelectedOrderPackages = (payload) => {
  if(payload.checked){
    selectedOrderPackages.value.push(payload.packageId);
  }else{
    selectedOrderPackages.value = selectedOrderPackages.value.filter((item) => item !== payload.packageId);
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
          }
        });
      });
      return data;
    }
  }
);

console.log("ORDER DATA",orderData);

</script>
