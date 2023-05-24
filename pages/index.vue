<template>
  <div>
    <div class="flex flex-wrap">
      <div class="w-full mb-12 xl:mb-0 px-4">
        <CardLineChart
          :labels="labels"
          :datasets="datasets"
          title="Average Temperatures to Destination (Cº)"
        />
      </div>
    </div>

    <div>
      <div class="w-full mb-12 xl:mb-0">
        <CardOrders />
      </div>
    </div>
  </div>
</template>
<script setup>
import CardLineChart from "@/components/Charts/CardLineChart.vue";
import CardOrders from "@/components/Cards/CardOrders.vue";

const labels = ref([]);
const datasets = ref([]);
await useLazyAsyncData(
  "getTemperatureByCarrier",
  async () => {
    // Make the callback function async
    const response = await $fetch(
      `/api/statistics/temperature-by-carrier/DHL;`
    );
    return response;
  },
  {
    server: false,
    transform: (data) => {
      labels.value = data.map((item) => item[0]);
      datasets.value = [
        {
          label: "DHL",
          backgroundColor: "#4c51bf",
          borderColor: "#4c51bf",
          data: data.map((item) => Number(item[1].toFixed(0))),
          fill: false
        }
      ];

      return data;
    }
  }
);
</script>
