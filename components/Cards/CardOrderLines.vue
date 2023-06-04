<template>
  <TableComponent
    :data="props.orderLines"
    :loading="props.loading"
    :keys="keys"
    title="Order Lines"
  ></TableComponent>
</template>
<script setup>
import TableComponent from "@/components/Tables/TableComponent.vue";

const props = defineProps({
  orderLines: {
    type: Array,
    required: true,
    default: () => []
  },
  loading: {
    type: Boolean,
    required: true
  }
});

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
    key: "dimensions",
    label: "Dimensions"
  },
  {
    key: "quantity",
    label: "Quantity"
  },
  {
    key: "productPrice",
    label: "Product Price"
  },
  {
    key: "price",
    label: "Total Price"
  }
]);
</script>
