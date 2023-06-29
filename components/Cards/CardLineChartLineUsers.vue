<template>
  <div
    class="relative flex flex-col min-w-0 break-words w-full mb-6 shadow-lg rounded"
    style="background-color: #fdfdfd"
  >
    <div class="rounded-t mb-0 px-4 py-3 bg-transparent">
      <div class="flex flex-wrap items-center">
        <div class="relative w-full max-w-full flex-grow flex-1">
          <h2 class="text-grey text-xl font-semibold">{{title}}</h2>
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

const chartDataset = JSON.parse(router.currentRoute.value.query.chartDataset);

const title = router.currentRoute.value.query.title

const colors = ["#b70202", "#336933"];
const selectedColors = [];

const getRandomColor = () => {
  if (colors.length === 0) {
    // Caso todas as cores tenham sido selecionadas, você pode optar por gerar uma nova cor ou parar a seleção
    // return getRandomColor(); // Gere uma nova cor aleatória
    return null; // Pare a seleção de cores
  }

  const randomIndex = Math.floor(Math.random() * colors.length);
  const color = colors[randomIndex];
  colors.splice(randomIndex, 1); // Remove a cor selecionada do array original
  selectedColors.push(color); // Adiciona a cor selecionada ao array de cores selecionadas
  return color;
};

const defaultDatasets = ref(chartDataset.map((dataset) => ({
  label: dataset?.chartTitle,
  backgroundColor: getRandomColor(),
  borderColor: "rgba(84, 84, 84, 0.7)",
  data: dataset?.data,
})));

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
