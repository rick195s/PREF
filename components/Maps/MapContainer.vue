v<template>
  <div class="relative flex flex-col min-w-0 break-words bg-white w-full mb-6 shadow-lg rounded">
    <div class="rounded-t mb-0 px-4 py-3 border-0">
      <div class="flex flex-wrap items-center">
        <div class="relative w-full px-4 max-w-full flex-grow flex-1">
          <h3 class="font-semibold text-base text-blueGray-700">Map History</h3>
        </div>
      </div>
    </div>
    <div class="w-full h-screen" id="mapContainer"></div>
  </div>
</template>

<script setup>
import { onMounted, onUnmounted, nextTick } from 'vue';
import maplibre from 'maplibre-gl';

let map;

const getCoordinates = async (location) => {
  const url = `https://nominatim.openstreetmap.org/search?q=${encodeURIComponent(location)}&format=json`;
  const response = await fetch(url);
  const data = await response.json();
  if (data.length > 0) {
    const { lat, lon } = data[0];
    return [parseFloat(lon), parseFloat(lat)];
  } else {
    throw new Error(`No coordinates found for location: ${location}`);
  }
}

onMounted(async () => {
  const coordinates = await getCoordinates('Portugal');
  nextTick(() => {
    map = new maplibre.Map({
      container: 'mapContainer',
      style: 'https://api.maptiler.com/maps/streets/style.json?key=ZdGogv4i6qyhb1uXqKxV',
      center: coordinates,
      zoom: 7
    });

  });
});

onUnmounted(() => {
  map.remove();
});
</script>

