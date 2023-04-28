<template>
  <div
    id="toast"
    class="flex z-10 fixed right-5 bottom-5 items-center p-4 mb-4 w-full max-w-xs text-gray-500 bg-white rounded-lg shadow dark:text-gray-400 dark:bg-gray-800"
    role="alert"
  >
    <div
      class="inline-flex flex-shrink-0 justify-center items-center w-8 h-8 rounded-lg"
      :class="props.type === 'success' ? 'bg-green-500' : 'bg-red-500'"
    >
      <i
        class="text-white"
        :class="
          props.type === 'success'
            ? ['fa-solid fa-check']
            : ['fa-solid fa-xmark']
        "
      ></i>
    </div>
    <div class="ml-3 text-sm font-normal">{{ props.message }}</div>
    <button
      type="button"
      class="ml-auto justify-center items-center bg-white text-gray-400 hover:text-gray-900 rounded-lg focus:ring-2 focus:ring-gray-300 p-1.5 hover:bg-gray-100 inline-flex h-8 w-8 dark:text-gray-500 dark:hover:text-white dark:bg-gray-800 dark:hover:bg-gray-700"
      aria-label="Close"
      @click="$emit('close')"
    >
      <i class="fa-solid fa-xmark"></i>
    </button>
  </div>
</template>

<script setup>
const props = defineProps({
  message: {
    type: String,
    default: null
  },
  type: {
    type: String,
    default: null
  }
});

defineEmits(["close"]);
</script>
