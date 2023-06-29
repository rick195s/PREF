<template>
  <div @click="handleCardClick">
    <h2>{{ props.labels.title }} </h2>
    <p>{{ props.labels.value }}</p>
  </div>
</template>

<script setup>
import { defineProps } from "vue";
import { useRouter } from "vue-router";

const props = defineProps({
  labels: {
    type: Array,
    required: true,
    default: () => []
  }
});
const router = useRouter();

const handleCardClick = () => {
  const chartDataset = props.labels.chartDatasets;
  if (chartDataset.length !== 0) {
    router.push({ path: "/chart/chartUsers", query: { title: props.labels.title, chartDataset: JSON.stringify(chartDataset) } });
  }
};

</script>