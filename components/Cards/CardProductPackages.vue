<template>
  <TableComponent
    :data="packages"
    :keys="keys"
    :loading="pending"
    :actions="props.actionsEnabled"
    title="Product Packages"
    @add-package="addPackage($event)"
  ></TableComponent>
</template>
<script setup>
import TableComponent from "@/components/Tables/TableComponent.vue";

const props = defineProps({
  actionsEnabled: {
    type: Boolean,
    default: true
  }
});

const emit = defineEmits(["addPackage"]);

const keys = ref([
  {
    key: "name",
    label: "Name"
  }
]);

const { data: packages, pending } = await useLazyAsyncData(
  "productPackages",
  () => $fetch(`/api/product-packages-types`),
  {
    server: false,
    transform: (data) => {
      data.forEach((element) => {
        element.actions = [
          {
            emit: { name: "addPackage", value: element },
            icon: "fa-regular fa-plus"
          }
        ];
      });

      return data;
    }
  }
);

const addPackage = (productPackage) => {
  const cleanedProductPackage = { ...productPackage };
  delete cleanedProductPackage.actions;
  emit("addPackage", cleanedProductPackage);
};
</script>
