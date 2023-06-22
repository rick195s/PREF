<template>
  <div class="flex flex-wrap mt-4">
    <div class="w-full mb-12 xl:mb-0 px-4">
      <CardOrderPackagesOfOrder
        :order-packages="orderDataWithDetails?.orderPackages"
        @order-package-selected="updateSelectedPackages"
        :loading="pending"
      />
      <CardOrderLinesPackages
        :order-lines="orderDataWithDetails?.orderLines"
        @product-package-selected="updateSelectedPackages"
        :loading="pending"
      />
      <CardObservations
        :loading="hasObservationsPending"
        :selected-packages="selectedPackages"
      />
    </div>
  </div>
</template>
<script setup>
import CardOrderLinesPackages from "~/components/Cards/CardOrderLinesPackages.vue";
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

const packageIds = ref([]);

const { data: orderData, pending } = await useLazyAsyncData(
  "orderData",
  () => $fetch(`/api/orders/${useRoute().params.id}`, {}),
  {
    server: false,
    transform: (data) => {
      console.log(data);
      data.orderPackages.forEach((orderPackage) => {
        packageIds.value.push(orderPackage.id);
      });
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
          item.product.orderLineProductPackages.forEach((productPackage) => {
            packageIds.value.push(productPackage.id);
            productPackage.hasObservations = false;
          });
        });
      });
      data.orderPackages.forEach((element) => {
        data.orderPackageTypes.forEach((item) => {
          if (item.id === element.simplePackageTypeId) {
            element.packageName = item.name;
            element.isSmart = item.smart;
          }
        });
      });

      return data;
    }
  }
);

const { data: orderDataWithDetails, pending: hasObservationsPending } =
  await useLazyAsyncData(
    "packagesHasObservations",
    () =>
      $fetch("/api/observations/packages-has-observations/", {
        method: "POST",
        body: JSON.stringify({ ids: packageIds.value })
      }),
    {
      server: false,
      watch: orderData,
      transform: (data) => {
        const orderDataTemp = orderData.value;
        orderDataTemp.orderPackages.forEach((element) => {
          if (data.includes(element.id)) {
            element.hasObservations = true;
          }
        });
        orderDataTemp.orderLines.forEach((element) => {
          element.orderLineProductRelation.forEach((item) => {
            item.product.orderLineProductPackages.forEach((productPackage) => {
              if (data.includes(productPackage.id)) {
                productPackage.hasObservations = true;
              }
            });
          });
        });

        return orderDataTemp;
      }
    }
  );
</script>
