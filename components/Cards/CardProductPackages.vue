<template>
  <TableComponent
    :data="packages"
    :keys="keys"
    :loading="pending"
    title="Packages"
    @add-package="addPackage($event)"
  ></TableComponent>
</template>
<script setup>
import TableComponent from "@/components/Tables/TableComponent.vue";

const emit = defineEmits(["addPackage"]);

const keys = ref([
  {
    key: "name",
    label: "Name"
  }
]);

const { data: packages, pending } = await useLazyAsyncData(
  "productPackages",
  () => $fetch(`/api/product-packages`),
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
  console.log(productPackage);
  const cleanedProductPackage = { ...productPackage };
  delete cleanedProductPackage.actions;
  emit("addPackage", cleanedProductPackage);
};
</script>
