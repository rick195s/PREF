<template>
  <div>
    <div class="flex flex-wrap">
      <!-- <div class="w-full xl:w-8/12 mb-12 xl:mb-0 px-4">
        <CardLineChart
          :labels="labels"
          :datasets="datasets"
          title="Average Temperatures to Destination (Cº)"
        />
      </div> -->
      <div class="w-full px-4">
        <CardBarChart :selected-carrier="selectedCarrier" />
      </div>
    </div>

    <div>
      <div class="w-full mb-12 xl:mb-0">
        <CardOrders
          @selected-carrier="($event) => (selectedCarrier = $event)"
        />
      </div>
    </div>
  </div>
</template>
<script setup>
import CardBarChart from "@/components/Charts/CardBarChart.vue";
import CardOrders from "@/components/Cards/CardOrders.vue";

const selectedCarrier = ref(null);
</script>
