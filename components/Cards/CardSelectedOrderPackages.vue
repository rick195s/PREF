<template>
  <div>
    <div class="bg-white flex flex-row justify-between px-6 align-middle p-4 overflow-x-auto">
      <h4>SuggestedPackage:</h4>
      <span v-if="!pending" class="ml-2 font-medium text-gray-600">{{ suggestedPackage }}</span>
      <span v-else class="ml-2 font-medium text-gray-600"> Searching... </span>

      <select class="ml-2 w-40 border border-gray-300 rounded px-2 py-1"
              v-model="selectedStrategy"
              @change="updateSelectedStrategy">
        <!-- Opções da combobox -->
        <option value="" disabled>Select Strategy</option>
        <option v-for="strategy in strategies" :key="strategy" :value="strategy">
          {{ strategy }}
        </option>
      </select>

      <button
        class="bg-red-500 text-white active:bg-red-600 font-bold uppercase text-xs px-4 py-2 rounded shadow hover:shadow-md outline-none focus:outline-none mr-1 ease-linear transition-all duration-150"
        type="button"
      >
        Search
      </button>
    </div>

    <div class="bg-white flex flex-row justify-between px-6 align-middle p-4">
      <h4>Selected Packages</h4>
      <button
        class="bg-red-500 text-white active:bg-red-600 font-bold uppercase text-xs px-4 py-2 rounded shadow hover:shadow-md outline-none focus:outline-none mr-1 ease-linear transition-all duration-150"
        type="button"
        @click="addPackages()"
      >
        Add Packages
      </button>
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

const selectedStrategy = ref("");
const suggestedPackage = ref("");
const pending = ref(false);

const emit = defineEmits(["update:modelValue"], ["addPackages"]);

const { data: strategies } = await useLazyAsyncData(
  "strategies",
  () => $fetch("/api/order-package-types/strategies", {}),
  {
    server: false,
    transform: (data) => {
      return data;
    }
  }
);


const selectedPackages = computed({
  get: () => {
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

const addPackages = () => {
  emit("addPackages");
};

const updateSelectedStrategy = () => {
  pending.value = true;
  console.log(selectedStrategy.value);
  $fetch(`/api/order-package-types/suggest-package/${selectedStrategy.value}`)
    .then((response) => {
      pending.value = false;
      suggestedPackage.value = response.id;
    })
    .catch((error) => {
      pending.value = false;
      suggestedPackage.value = "-";
      console.log(error);
    });
};


</script>
