<template>
  <div
    class="relative flex flex-col min-w-0 break-words bg-white w-full mb-6 shadow-lg rounded"
  >
    <div class="rounded-t mb-0 px-4 py-3 border-0">
      <div class="flex flex-wrap items-center">
        <div class="relative w-full px-4 max-w-full flex-grow flex-1">
          <h3 class="font-semibold text-base text-blueGray-700">
            Order Packages
          </h3>
        </div>
      </div>
    </div>
    <div
      class="grid lg:grid-cols-4 px-4 py-3 text-blueGray-500 align-middle border border-solid border-blueGray-100 bg-slate-100"
    >
      <div v-if="props.loading">
        <SpinnerComponent></SpinnerComponent>
      </div>
      <div
        v-for="orderPackage in computedOrderPackages"
        v-else
        :key="orderPackage.id"
        class="break-words bg-white mb-6 shadow-lg rounded mx-4"
      >
        <div class="py-3 flex justify-between px-3">
          <span
            >type {{ orderPackage.simplePackageTypeId }} -
            {{ orderPackage.id }}</span
          >
          <label v-if="orderPackage.hasObservations" class="checkbox-label">
            <input
              v-model="orderPackage.checkedComputed"
              type="checkbox"
              class="checkbox-input"
              @change="updateSelectedCheckboxes(orderPackage)"
            />
            <span class="checkbox-custom"></span>
          </label>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import SpinnerComponent from "@/components/Spinner/SpinnerComponent.vue";

const emit = defineEmits(["order-package-selected"]);

const props = defineProps({
  loading: {
    type: Boolean,
    required: false,
    default: true
  },
  orderPackages: {
    type: Array,
    required: true,
    default: () => []
  }
});

const computedOrderPackages = computed(() => {
  return props.orderPackages.map((orderPackage) => {
    return {
      ...orderPackage,
      checkedComputed: true
    };
  });
});

watch(computedOrderPackages, (newOrderPackages) => {
  if (newOrderPackages.length > 0) {
    newOrderPackages.forEach((orderPackage) => {
      orderPackage.checkedComputed = true;
      updateSelectedCheckboxes(orderPackage);
    });
  }
});

const updateSelectedCheckboxes = (orderPackage) => {
  emit("order-package-selected", {
    checked: orderPackage.checkedComputed,
    packageId: orderPackage.id
  });
};
</script>

<style scoped>
.checkbox-label {
  position: relative;
  display: inline-block;
  vertical-align: middle;
}

.checkbox-input {
  position: absolute;
  opacity: 0;
}

.checkbox-custom {
  position: relative;
  display: inline-block;
  width: 16px;
  height: 16px;
  border: 1px solid #999;
  border-radius: 3px;
}

.checkbox-input:checked + .checkbox-custom {
  background-color: #e30a0a;
}

.checkbox-input:checked + .checkbox-custom::after {
  content: "";
  position: absolute;
  top: 3px;
  left: 5px;
  width: 4px;
  height: 8px;
  border: solid white;
  border-width: 0 2px 2px 0;
  transform: rotate(45deg);
}
</style>
