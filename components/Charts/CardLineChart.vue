<template>
  <div
    class="relative flex flex-col min-w-0 break-words w-full mb-6 shadow-lg rounded bg-blueGray-700"
  >
    <div class="rounded-t mb-0 px-4 py-3 bg-transparent">
      <div class="flex flex-wrap items-center">
        <div class="relative w-full max-w-full flex-grow flex-1">
          <h6 class="uppercase text-blueGray-100 mb-1 text-xs font-semibold">
            Overview
          </h6>
          <h2 class="text-white text-xl font-semibold">{{ props.title }}</h2>
        </div>
      </div>
    </div>
    <div class="p-4 flex-auto">
      <!-- Chart -->
      <div class="relative h-350-px">
        <Bar id="my-chart-id" :options="chartOptions" :data="chartData" />
      </div>
    </div>
  </div>
</template>
<script setup>
import { Bar } from "vue-chartjs";
const props = defineProps({
  title: {
    type: String,
    required: true
  }
});
const chartData = ref({
  labels: ["January", "February", "March"],
  datasets: [{ data: [40, 20, 12] }]
});

const chartOptions = ref({
  responsive: true
});
</script>
