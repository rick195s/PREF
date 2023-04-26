<template>
  <div class="flex flex-wrap">
    <div class="w-full">
      <CardCreateOrder />
    </div>
    <div class="w-full">
      <CardProducts />
    </div>
  </div>
</template>
<script setup>
import CardCreateOrder from "@/components/Cards/CardCreateOrder.vue";
import CardProducts from "@/components/Cards/CardProducts.vue";
</script>
