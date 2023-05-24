<template>
  <div
    class="relative flex flex-col min-w-0 break-words w-full mb-6 shadow-lg rounded"
    style="background-color: #333333"
  >
    <div class="rounded-t mb-0 px-4 py-3">
      <div class="flex flex-wrap items-center">
        <div class="relative w-full max-w-full flex-grow flex-1">
          <h6 class="uppercase text-blueGray-100 mb-1 text-xs font-semibold">
            Overview
          </h6>
          <h2 class="text-white text-xl font-semibold">
            Temperature Amplitude (Cº)
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
  Chart,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
} from "chart.js";
Chart.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale);

const props = defineProps({
  title: {
    type: String,
    required: true
  }
});
const chartData = ref({
  datasets: [
    {
      label: "DPD",
      backgroundColor: "#e30613",
      data: [
        { x: "Porto", y: 12 },
        { x: "Lisboa", y: -2 },
        { x: "Coimbra", y: 13 },
        { x: "Amarante", y: 3 },
        { x: "Viseu", y: -4 }
      ]
    },
    {
      label: "CTT",
      backgroundColor: "#4c51bf",
      data: [
        { x: "Porto", y: 11 },
        { x: "Lisboa", y: -4 },
        { x: "Coimbra", y: 12 },
        { x: "Amarante", y: 8 }
      ]
    },
    {
      label: "NACEX",
      backgroundColor: "#808080",
      data: [
        { x: "Lisboa", y: 1 },
        { x: "Coimbra", y: 12 },
        { x: "Amarante", y: 10 },
        { x: "Viseu", y: -1 }
      ]
    },
    {
      label: "DHL",
      backgroundColor: "#fca510",
      data: [
        { x: "Porto", y: 11 },
        { x: "Lisboa", y: 2 },
        { x: "Coimbra", y: 3 },
        { x: "Amarante", y: 8 }
      ]
    }
  ]
});

const chartOptions = ref({
  maintainAspectRatio: false,
  responsive: true,
  options: {
    plugins: {
      customCanvasBackgroundColor: {
        color: "lightGreen"
      }
    }
  },
  plugins: {
    legend: {
      align: "end",
      position: "bottom"
    },

    tooltip: {
      mode: "index",
      intersect: false
    }
  }
});
Chart.defaults.color = "rgba(255,255,255,.7)";
</script>
