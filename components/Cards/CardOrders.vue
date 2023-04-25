<template>
  <TableComponent
    :data="orders.data"
    :per-page="perPage"
    :total="orders.metadata.totalCount"
    :current-page="currentPage"
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

const { data: orders } = await useAsyncData(
  "orders",
  () =>
    $fetch(`/orders`, {
      method: "GET",
      baseURL: runtimeConfig.public.apiUrl,
      params: {
        offset: offset.value,
        limit: perPage.value
      }
    }),
  {
    watch: [offset, perPage]
  }
);
</script>
