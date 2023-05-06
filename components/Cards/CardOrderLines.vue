<template>
  <TableComponent
    :data="order?.orderLines"
    :loading="pending"
    :keys="keys"
    title="Products"
  ></TableComponent>
</template>
<script setup>
import TableComponent from "@/components/Tables/TableComponent.vue";

const keys = ref([
  {
    key: "productId",
    label: "Id"
  },
  {
    key: "productName",
    label: "Name"
  },
  {
    key: "validityRange",
    label: "Validity Range"
  },
  {
    key: "dimensions",
    label: "Dimensions"
  },
  {
    key: "weight",
    label: "Weight"
  },
  {
    key: "category",
    label: "Category"
  },
  {
    key: "quantity",
    label: "Quantity"
  },{
    key: "price",
    label: "Product Price"
  },
  {
    key: "productPrice",
    label: "Total Price"
  }
]);

const { data: order, pending } = await useLazyAsyncData(
  "order",
  () =>
    $fetch(`/api/orders/${useRoute().params.trackingNumber}`, {
    }),
  {
    server: false,
    transform: (data) => {
      data.orderLines.forEach((element) => {
        element.productId = element.product.id;
        element.productName = element.product.name;
        element.validityRange = element.product.validityRange;
        element.dimensions = element.product.length + "x" + element.product.width + "x" + element.product.height;
        element.weight = element.product.weight;
        element.price = element.product.price;
        element.category = element.product.category;
      });
      return data;
    },
  });
</script>
