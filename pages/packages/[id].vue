<template>
  <div>
    <div class="flex flex-wrap mt-4">
      <div class="w-full mb-12 xl:mb-0 px-4">
        <CardHistory />
        <MapExample/>
      </div>
    </div>
  </div>
</template>
<script setup>
import CardHistory from "~/components/Cards/CardPackageLogs.vue";
import MapExample from "~/components/Maps/MapContainer.vue";
</script>
