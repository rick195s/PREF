<template>
  <TableComponent
    :data="orders?.data"
    :per-page="perPage"
    :loading="pending"
    :total="orders?.metadata.totalCount"
    :current-page="currentPage"
    :keys="keys"
    :carriers="carrierOptions"
    title="Orders"
    paginated
    :update-carrier="updateCarrier"
    @change-page="offset = ($event - 1) * perPage"
  >
  </TableComponent>
</template>

<script setup>
import TableComponent from "@/components/Tables/TableComponent.vue";

const offset = ref(0);
const perPage = ref(10);
const currentPage = computed(() =>
  offset.value == 0 ? 1 : offset.value / perPage.value + 1
);

const carrierFilter = ref("");

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
    key: "temperatureMax",
    label: "Temperature Max"
  },
  {
    key: "temperatureMin",
    label: "Temperature Min"
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

const emit = defineEmits(["selectedCarrier"]);

const { data: carrierOptions } = await useLazyAsyncData(
  "orderCarriers",
  () => $fetch("/api/orders/carriers", {}),
  {
    server: false
  }
);

const { data: orders, pending } = await useLazyAsyncData(
  "orders",
  () =>
    $fetch(`/api/orders`, {
      params: {
        offset: offset.value,
        limit: perPage.value,
        carrier: carrierFilter.value
      }
    }),
  {
    server: false,
    transform: (data) => {
      console.log("DATA", data);
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
        //randomize max temperature
        element.temperatureMax = Math.random() * (21 - 5) + 5;
        //randomize min temperature
        element.temperatureMin = Math.random() * (5 - -20) + -20;
        element.temperatureMin = element.temperatureMin.toFixed(2) + "ºC";
        element.temperatureMax = element.temperatureMax.toFixed(2) + "ºC";
        element.actions = [
          {
            to: `/orders/${element.id}`,
            icon: "fa-regular fa-pen-to-square"
          },
          {
            to: `/orders/history/${element.id}`,
            icon: "fa-regular fa-file-alt"
          }
        ];
      });

      return data;
    },
    watch: [offset, perPage, carrierFilter]
  }
);

function updateCarrier(item) {
  carrierFilter.value = item;
  emit("selectedCarrier", item);
}
</script>
