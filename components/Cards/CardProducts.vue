<template>
  <TableComponent
    :data="products?.data"
    :per-page="perPage"
    :total="products?.metadata.totalCount"
    :current-page="currentPage"
    :keys="keys"
    :loading="pending"
    title="Products"
    @change-page="offset = ($event - 1) * perPage"
  ></TableComponent>
</template>
<script setup>
import TableComponent from "@/components/Tables/TableComponent.vue";

const runtimeConfig = useRuntimeConfig();

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
    key: "category",
    label: "Category"
  },
  {
    key: "price",
    label: "Price"
  },
  {
    key: "weight",
    label: "Weight"
  },
  {
    key: "validityRange",
    label: "Validity Range"
  }
]);

const { data: products, pending } = await useAsyncData(
  "products",
  () =>
    $fetch(`/products`, {
      method: "GET",
      baseURL: runtimeConfig.public.apiUrl,
      params: {
        offset: offset.value,
        limit: perPage.value
      }
    }),
  {
    lazy: true,
    watch: [offset, perPage]
  }
);
</script>
