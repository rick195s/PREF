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
          <h2 class="text-blueGray-700 text-xl font-semibold">Total orders</h2>
        </div>
      </div>
    </div>
    <div class="p-4 flex-auto">
      <div class="relative h-350-px">
        <Bar
          v-if="!props.loading"
          id="my-chart-id"
          :options="chartOptions"
          :data="chartData"
        />
        <SpinnerComponent v-else />
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
import SpinnerComponent from "@/components/Spinner/SpinnerComponent.vue";

const props = defineProps({
  ordersComparation: {
    type: Array,
    required: true
  },
  loading: {
    type: Boolean,
    default: false
  }
});

const ordersComparation = computed(() => {
  const datasets = [];
  const labels = [];

  props.ordersComparation.forEach((element) => {
    if (labels.indexOf(element[2]) === -1) {
      labels.push(element[2]);
      datasets.push({
        label: "",
        backgroundColor: "#fca510",
        data: []
      });
      datasets[labels.indexOf(element[2])].label = element[2];
    }

    datasets[labels.indexOf(element[2])].data.push({
      x: element[1].trim(),
      y: element[0]
    });
  });
  datasets[1].backgroundColor = "#e30613";

  return datasets;
});

const chartData = ref({
  datasets: ordersComparation
});

const chartOptions = ref({
  maintainAspectRatio: false,
  responsive: true,
  plugins: {
    legend: {
      align: "end",
      position: "bottom"
    }
  },
  options: {
    plugins: {
      customCanvasBackgroundColor: {
        color: "lightGreen"
      }
    },
    title: {
      display: false,
      text: "Orders Chart"
    },
    legend: {
      fontColor: "rgba(0,0,0,.4)",
      align: "end",
      position: "bottom"
    },

    tooltip: {
      mode: "index",
      intersect: false
    },
    scales: {
      x: {
        border: {
          display: false
        },
        grid: {
          display: false
        }
      },
      y: {
        border: {
          display: false
        },
        grid: {
          display: false,

          color: "rgb(255,255,0)"
        }
      }
    }
  }
});

Chart.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale);
</script>
