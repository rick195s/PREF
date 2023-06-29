<template>
  <div
    class="relative flex flex-col min-w-0 break-words w-full mb-6 shadow-lg rounded"
    style="background-color: #fdfdfd"
  >
    <div class="rounded-t mb-0 px-4 py-3 bg-transparent">
      <div class="flex flex-wrap items-center">
        <div class="relative w-full max-w-full flex-grow flex-1">
          <h2 class="text-grey text-xl font-semibold">{{chartDataset?.chartTitle}}</h2>
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
import { useRouter } from "vue-router";

Chart.register(...registerables);

Chart.defaults.color = "rgba(0,0,0,0.7)";

const router = useRouter();

const chartDataset = JSON.parse(router.currentRoute.value.query.chartDataset)[0];

const defaultDatasets = ref([
  {
    label: chartDataset?.chartTitle,
    backgroundColor: "#e30613",
    borderColor: "rgba(84,84,84,0.7)",
    data: chartDataset?.data
  }
]);

const linechart = ref(null);

const chartOptions = ref({
  maintainAspectRatio: false,
  responsive: true,
  tension: 0.4,
  plugins: {
    legend: {
      labels: {
        color: "rgb(0,0,0)"
      },
      align: "end",
      position: "bottom"
    },
    tooltip: {
      mode: "index",
      intersect: false
    }
  }
});

const chartData = ref({
  datasets: defaultDatasets.value
});
</script>
