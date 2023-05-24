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
  selectedCarrier: {
    type: String,
    required: false,
    default: null
  }
});

const defaultDatasets = ref([
  {
    label: "DPD",
    backgroundColor: "#e30613",
    data: [
      { x: "Fátima", y: 12 },
      { x: "Seia", y: -2 },
      { x: "Esposende", y: 13 },
      { x: "Viseu", y: 3 },
      { x: "Bragança", y: -4 }
    ]
  },
  {
    label: "CTT",
    backgroundColor: "#4c51bf",
    data: [
      { x: "Fátima", y: 11 },
      { x: "Seia", y: -4 },
      { x: "Esposende", y: 12 },
      { x: "Viseu", y: 8 }
    ]
  },
  {
    label: "NACEX",
    backgroundColor: "#808080",
    data: [
      { x: "Fátima", y: 1 },
      { x: "Seia", y: 12 },
      { x: "Esposende", y: 10 },
      { x: "Viseu", y: -1 }
    ]
  },
  {
    label: "DHL",
    backgroundColor: "#fca510",
    data: [
      { x: "Fátima", y: 11 },
      { x: "Seia", y: 2 },
      { x: "Esposende", y: 3 },
      { x: "Viseu", y: 8 }
    ]
  }
]);

watch(
  () => props.selectedCarrier,
  () => {
    const datasets = [];

    switch (props.selectedCarrier) {
      case "DHL":
        datasets.push(defaultDatasets.value[3]);
        break;
      case "DPD":
        datasets.push(defaultDatasets.value[0]);
        break;

      case "CTT":
        datasets.push(defaultDatasets.value[1]);
        break;

      case "NACEX":
        datasets.push(defaultDatasets.value[2]);
        break;
      default:
        datasets.push(...defaultDatasets.value);
        break;
    }
    chartData.value = { datasets: datasets };
  }
);

const chartData = ref({
  datasets: defaultDatasets.value
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
