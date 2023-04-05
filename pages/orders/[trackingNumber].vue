<template>
  <div>
    <div class="flex flex-wrap mt-4">
      <div class="w-full mb-12 xl:mb-0 px-4">
        <CardOrderDetails />
        <CardsCardOrderLines />
      </div>
    </div>
  </div>
</template>
<script setup>
import CardOrderDetails from "@/components/Cards/CardOrderDetails.vue";
import CardsCardOrderLines from "@/components/Cards/CardOrderLines.vue";
</script>
