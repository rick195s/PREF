<template>
  <div
    class="relative flex flex-col min-w-0 break-words bg-white w-full mb-6 shadow-lg rounded"
  >
    <div class="rounded-t mb-0 px-4 py-3 bg-transparent">
      <div class="flex flex-wrap items-center">
        <div class="relative w-full max-w-full flex-grow flex-1">
          <h6 class="uppercase text-blueGray-400 mb-1 text-xs font-semibold">
            Performance
          </h6>
          <h2 class="text-blueGray-700 text-xl font-semibold">
            {{ props.title }}
          </h2>
        </div>
      </div>
    </div>
    <div class="p-4 flex-auto">
      <div class="relative h-350-px">
        <Bar id="my-chart-id" :options="chartOptions" :data="chartData" />
      </div>
    </div>
  </div>
</template>
<script setup>
import { Bar } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
} from "chart.js";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
);
const props = defineProps({
  title: {
    type: String,
    required: true
  }
});
const chartData = ref({
  labels: ["January", "February", "March", "April", "May", "June", "July"],
  datasets: [
    {
      label: new Date().getFullYear(),
      backgroundColor: "#ed64a6",
      borderColor: "#ed64a6",
      data: [30, 78, 56, 34, 100, 45, 13],
      fill: false,
      barThickness: 8
    },
    {
      label: new Date().getFullYear() - 1,
      fill: false,
      backgroundColor: "#4c51bf",
      borderColor: "#4c51bf",
      data: [27, 68, 86, 74, 10, 4, 87],
      barThickness: 8
    }
  ]
});

const chartOptions = ref({
  maintainAspectRatio: false,
  responsive: true,
  plugins: {
    tooltip: {
      mode: "index",
      intersect: false
    },
    legend: {
      labels: {
        fontColor: "rgba(0,0,0,.4)"
      },
      align: "end",
      position: "bottom"
    }
  }
});
</script>
