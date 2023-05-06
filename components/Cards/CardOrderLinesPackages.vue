<template>
  <TableComponent
  :data="orders?.orderLines?.flatMap((orderLine) => orderLine.packages || [orderLine])"
  :loading="pending"
  :keys="keys"
  title="Products Packages"
></TableComponent>
</template>
<script setup>
import TableComponent from "@/components/Tables/TableComponent.vue";


const keys = ref([
  {
    key: "packageId",
    label: "Package Id"
  },
  {
    key: "packageName",
    label: "Package Name"
  },
  {
    key: "packageType",
    label: "Package Type"
  },
  {
    key: "productId",
    label: "Product Id"
  }
]);

const { data: orders, pending } = await useLazyAsyncData(
  "orders",
  () =>
    $fetch(`/api/orders/${useRoute().params.trackingNumber}`, {
    }),
  {
    server: false,
    transform: (data) => {
      data.orderLines.forEach((orderLine) => {
        orderLine.product.productPackages.forEach((productPackage) => {
          const newPackage = {
            packageId: productPackage.id,
            packageName: productPackage.name,
            packageType: productPackage.packageType,
            productId: orderLine.product.id,
            actions: [
              {
                to: `/observations/${productPackage.id}`,
                icon: "fa-solid fa-location-dot"
              }
            ]
          };
          if (!orderLine.packages) {
            orderLine.packages = [newPackage];
          } else {
            orderLine.packages.push(newPackage);
          }
        });
      });
      return data;
    },
  });
</script>
