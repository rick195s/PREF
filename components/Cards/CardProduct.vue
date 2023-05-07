<template>
  <div class="break-words bg-white mb-6 shadow-lg rounded">
    <div
      class="py-3 text-center justify-center items-center border-b-4 border-blueGray-100"
    >
      <h3 class="font-semibold text-base text-blueGray-700">
        {{ props.product.name }}
      </h3>
    </div>
    <div
      v-for="productPackage in props.product.productPackages"
      :key="productPackage"
      class="py-3 flex justify-between px-3"
    >
      <span> {{ productPackage.name }}</span>
      <NuxtLink :to="`/observations/` + productPackage.id">
        <button
          class="bg-red-500 text-white active:bg-red-600 font-bold uppercase text-xs px-4 py-2 rounded shadow hover:shadow-md outline-none focus:outline-none mr-1 mb-1 ease-linear transition-all duration-150"
          type="button"
        >
          <i class="fas fa-location-dot"></i>
        </button>
      </NuxtLink>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  product: {
    type: Object,
    required: true,
    default: () => {}
  }
});
</script>
