<template>
  <div class="break-words bg-white mb-6 shadow-lg rounded">
    <div class="py-3 text-center justify-center items-center border-b-4 border-blueGray-100">
      <h3 class="font-semibold text-base text-blueGray-700">{{ props.product.name }}</h3>
    </div>
    <div v-for="productPackage in props.product.productPackages" :key="productPackage" class="py-3 flex justify-between px-3">
      <span>{{ productPackage.name}} - {{productPackage.id}}</span>
      <label class="checkbox-label">
        <input type="checkbox" v-model="productPackage.checked" class="checkbox-input" @change="updateSelectedCheckboxes(productPackage)" />
        <span class="checkbox-custom"></span>
      </label>
    </div>
  </div>
</template>

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

<script setup>

const emit = defineEmits(["package-selected"]);

const props = defineProps({
  product: {
    type: Object,
    required: true,
    default: () => {}
  }
});
const updateSelectedCheckboxes = (productPackage) => {
  emit('package-selected', {
    checked: productPackage.checked,
    packageId: productPackage.id
  });
};
</script>
