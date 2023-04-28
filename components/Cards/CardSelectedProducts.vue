<template>
  <TableComponent
    :data="productsByQuantity"
    :keys="keys"
    title="Selected Products"
    @change-page="offset = ($event - 1) * perPage"
    @removeProduct="emit('removeProduct', $event)"
  ></TableComponent>
</template>
<script setup>
import TableComponent from "@/components/Tables/TableComponent.vue";

const productsByQuantity = computed(() => {
  return transform(props.products);
});

const emit = defineEmits(["removeProduct"]);

const props = defineProps({
  products: {
    type: Object,
    default: null
  }
});

defineExpose({
  productsByQuantity
});

const keys = ref([
  {
    key: "name",
    label: "Name"
  },
  {
    key: "quantity",
    label: "Quantity"
  }
]);

const transform = (products) => {
  const productsByQuantity = {};

  products.forEach(({ id, ...item }) => {
    productsByQuantity[id] = productsByQuantity[id] || {
      ...item,
      quantity: 0,
      id: id
    };
    productsByQuantity[id].quantity++;

    productsByQuantity[id].actions = [
      {
        emit: { name: "removeProduct", value: id },
        icon: "fa-solid fa-minus"
      }
    ];
  });

  return Object.values(productsByQuantity);
};
</script>
