<template>
  <div>
    <div class="flex flex-wrap">
      <div class="w-full xl:w-8/12 mb-12 xl:mb-0 px-4">
        <CardLineChart />
      </div>
      <div class="w-full xl:w-4/12 px-4">
        <CardBarChart />
      </div>
    </div>
    <div class="flex flex-wrap mt-4">
      <div class="w-full mb-12 xl:mb-0 px-4">
        <CardOrders />
      </div>
    </div>
  </div>
</template>
<script setup>
import CardLineChart from "@/components/Cards/CardLineChart.vue";
import CardBarChart from "@/components/Cards/CardBarChart.vue";
import CardOrders from "@/components/Cards/CardOrders.vue";
</script>
