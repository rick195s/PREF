<template>
  <div>
    <div class="bg-white flex flex-row justify-between px-6 align-middle p-4">
      <h4>Selected Packages</h4>
    </div>
    <draggable v-model="selectedPackages" item-key="id">
      >
      <template #item="{ element }">
        <div
          class="bg-white flex flex-row justify-between px-6 align-middle text-xs whitespace-nowrap p-4"
        >
          <i class="fas fa-bars"></i>
          <div>
            {{ element.name }}
          </div>
          <div>
            {{ element.type }}
          </div>

          <div>
            <button
              class="bg-red-500 text-white active:bg-red-600 font-bold uppercase px-1 rounded shadow hover:shadow-md outline-none focus:outline-none"
              type="button"
              @click="removePackage(element)"
            >
              <i class="fas fa-minus"></i>
            </button>
          </div>
        </div>
      </template>
    </draggable>
  </div>
</template>
<script setup>
import draggable from "vuedraggable";

const props = defineProps({
  modelValue: {
    type: Array,
    default: () => []
  }
});

const packagesTypes = ref(["Primary", "Secondary", "Terciary"]);

const emit = defineEmits(["update:modelValue"]);

const selectedPackages = computed({
  get: () => {
    props.modelValue.forEach((element, index) => {
      element.type = packagesTypes.value[index];
    });
    return props.modelValue;
  },
  set: (value) => {
    emit("update:modelValue", value);
  }
});

const removePackage = (element) => {
  const index = selectedPackages.value.indexOf(element);
  if (index > -1) {
    selectedPackages.value.splice(index, 1);
  }
};
</script>
