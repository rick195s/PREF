<template>
  <div class="flex flex-wrap mt-4">
    <div class="w-full mb-12 xl:mb-0 px-4">
      <CardOrderDetails />
      <CardOrderLines />
      <CardOrderLinePackages />
    </div>
  </div>
</template>
<script setup>
import CardOrderDetails from "@/components/Cards/CardOrderDetails.vue";
import CardOrderLines from "~/components/Cards/CardOrderLines.vue";
import CardOrderLinePackages from "~/components/Cards/CardOrderLinesPackages.vue";
</script>
