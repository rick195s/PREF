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
  },
  {
    key: "price",
    label: "Product Price"
  },
  {
    key: "productPrice",
    label: "Total Price"
  }
]);
</script>
