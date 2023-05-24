<template>
  <div
    class="relative flex flex-col min-w-0 break-words w-full mb-6 shadow-lg rounded"
    style="background-color: #333333"
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
        <Line ref="linechart" :options="chartOptions" :data="chartData" />
      </div>
    </div>
  </div>
</template>
<script setup>
import { Line } from "vue-chartjs";
import { Chart, registerables } from "chart.js";

Chart.register(...registerables);

Chart.defaults.color = "rgba(255,255,255,.7)";

const props = defineProps({
  title: {
    type: String,
    required: true
  },
  labels: {
    type: Array,
    required: true
  },
  datasets: {
    type: Array,
    required: true
  }
});

const linechart = ref(null);

const chartOptions = computed(() => {
  return {
    maintainAspectRatio: false,
    responsive: true,
    tension: 0.4,

    plugins: {
      legend: {
        labels: {
          color: "rgb(255, 255, 255)"
        },
        align: "end",
        position: "bottom"
      },
      tooltip: {
        mode: "index",
        intersect: false
      }
    }
  };
});

const chartData = computed(() => {
  return {
    labels: props.labels,
    datasets: props.datasets
  };
});
</script>
