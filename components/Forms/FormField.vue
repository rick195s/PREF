<template>
  <div class="w-full lg:w-6/12 px-4">
    <div class="relative w-full mb-3">
      <label
        class="block uppercase text-blueGray-600 text-xs font-bold mb-2"
        htmlFor="grid-password"
      >
        {{ props.label }}
      </label>
      <input
        v-model="computedValue"
        :type="props.fieldType"
        class="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
      />
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  fieldType: {
    type: String,
    default: "text"
  },
  label: {
    type: String,
    default: ""
  },
  modelValue: {
    type: [String, Number],
    default: ""
  },
  error: {
    type: String,
    default: ""
  }
});

const emit = defineEmits(["update:modelValue"]);

const computedValue = computed({
  get: () => props.modelValue,
  set: (value) => {
    emit("update:modelValue", value);
  }
});
</script>
