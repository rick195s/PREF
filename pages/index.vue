<template>
  <div>
    <div class="flex flex-wrap">
      <div class="w-full xl:w-8/12 mb-12 xl:mb-0 px-4">
        <CardLineChart title="Average Temperatures to Destination" />
      </div>
    </div>

    <div>
      <div class="w-full mb-12 xl:mb-0">
        <CardOrders />
      </div>
    </div>
  </div>
</template>
<script setup>
import CardLineChart from "@/components/Charts/CardLineChart.vue";
import CardOrders from "@/components/Cards/CardOrders.vue";
</script>
