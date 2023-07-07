<template>
  <TableComponent
    :data="products?.data"
    :per-page="perPage"
    :total="products?.metadata.totalCount"
    :current-page="currentPage"
    :keys="keys"
    :loading="pending"
    paginated
    title="Products"
    @change-page="offset = ($event - 1) * perPage"
    @add-product="addProduct($event)"
  ></TableComponent>
</template>
<script setup>
import TableComponent from "@/components/Tables/TableComponent.vue";

const emit = defineEmits(["addProduct"]);

const offset = ref(0);
const perPage = ref(10);
const currentPage = computed(() =>
  offset.value == 0 ? 1 : offset.value / perPage.value + 1
);

const keys = ref([
  {
    key: "id",
    label: "Id"
  },
  {
    key: "name",
    label: "Name"
  },
  {
    key: "type",
    label: "Type"
  },
  {
    key: "height",
    label: "Height"
  },
  {
    key: "width",
    label: "Width"
  }
]);

const { data: products, pending } = await useLazyAsyncData(
  "products",
  () =>
    $fetch(`/api/products`, {
      params: {
        offset: offset.value,
        limit: perPage.value
      }
    }),
  {
    server: false,
    watch: [offset, perPage],
    transform: (data) => {
      data.data.forEach((element) => {
        element.actions = [
          {
            emit: { name: "addProduct", value: element },
            icon: "fa-regular fa-plus"
          }
        ];
      });

      return data;
    }
  }
);

console.log(products);

const addProduct = (product) => {
  const cleanedProduct = { ...product };
  delete cleanedProduct.actions;
  emit("addProduct", cleanedProduct);
};
</script>
