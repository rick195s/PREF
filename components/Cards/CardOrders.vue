<template>
  <TableComponent
    :data="orders.data"
    :per-page="perPage"
    :total="orders.metadata.totalCount"
    :current-page="currentPage"
    :keys="keys"
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

const keys = [
  {
    key: "trackingNumber",
    label: "Tracking Number"
  },
  {
    key: "orderDate",
    label: "Order Date"
  },
  {
    key: "weight",
    label: "Weight"
  },
  {
    key: "carrier",
    label: "Carrier"
  },
  {
    key: "source",
    label: "Source"
  },
  {
    key: "destination",
    label: "Destination"
  },
  {
    key: "state",
    label: "State"
  }
];

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
    transform: (data) => {
      data.data.forEach((element) => {
        element.weight = element.weight.toFixed(2) + "kg";
        element.orderDate =
          new Date(element.orderDate).toLocaleDateString("pt-pt") +
          " - " +
          new Date(element.orderDate).toLocaleTimeString("pt-PT", {
            hour12: false,
            hour: "numeric",
            minute: "numeric"
          });
        element.actions = [
          {
            to: `/orders/${element.trackingNumber}`,
            icon: "fa-regular fa-pen-to-square"
          }
        ];
      });

      return data;
    },
    watch: [offset, perPage]
  }
);
</script>
