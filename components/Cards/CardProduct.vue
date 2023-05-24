<template>
  <div class="break-words bg-white mb-6 shadow-lg rounded">
    <div
      class="py-3 text-center justify-center items-center border-b-4 border-blueGray-100"
    >
      <div class="py-3 flex justify-between px-3">
        <h3 class="font-semibold text-base text-blueGray-700">
          {{ props.product.name }}
        </h3>
        <label v-if="showSelectAllButton" class="checkbox-label">
          <input
            v-model="allSelected"
            type="checkbox"
            class="checkbox-input"
            @click="selectAllPackages"
          />
          <span class="checkbox-custom"></span>
        </label>
      </div>
    </div>
    <div v-for="product in props.product.products" :key="product.id">
      <div
        v-for="orderLineProductPackage in product.orderLineProductPackages"
        :key="orderLineProductPackage.id"
        class="py-3 flex justify-between px-3"
      >
        <span
          >{{ orderLineProductPackage.packageName }} -
          {{ orderLineProductPackage.id }}</span
        >
        <label
          v-if="orderLineProductPackage.hasObservations"
          class="checkbox-label"
        >
          <input
            v-model="orderLineProductPackage.checked"
            type="checkbox"
            class="checkbox-input"
            @change="updateSelectedCheckboxes(orderLineProductPackage)"
          />
          <span class="checkbox-custom"></span>
        </label>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";

const emit = defineEmits(["product-package-selected"]);

const props = defineProps({
  product: {
    type: Object,
    required: true,
    default: () => ({})
  }
});

const allSelected = ref(false);

const updateSelectedCheckboxes = (orderLineProductPackage) => {
  emit("product-package-selected", {
    checked: orderLineProductPackage.checked,
    packageId: orderLineProductPackage.id
  });
  // Check if all smart packages are still checked
  const allSmartPackagesChecked = props.product.products
    .flatMap((product) => {
      return product.orderLineProductPackages.filter(
        (orderLineProductPackage) => orderLineProductPackage.isSmart
      );
    })
    .every((productPackage) => productPackage.checked);

  allSelected.value = allSmartPackagesChecked;
};

const selectAllPackages = () => {
  // Select packages which are smart and if they already are selected, then unselect them
  const smartPackages = props.product.products.flatMap((product) => {
    return product.orderLineProductPackages.filter(
      (orderLineProductPackage) => orderLineProductPackage.isSmart
    );
  });
  const selectAll = !allSelected.value;
  smartPackages.forEach((orderLineProductPackage) => {
    orderLineProductPackage.checked = selectAll;
    updateSelectedCheckboxes(orderLineProductPackage);
  });
  allSelected.value = selectAll;
};

const showSelectAllButton = computed(() => {
  // Check if one of the products is smart
  return props.product.products.some((product) => {
    return product.orderLineProductPackages.some((orderLineProductPackage) => {
      return orderLineProductPackage.isSmart;
    });
  });
});
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
