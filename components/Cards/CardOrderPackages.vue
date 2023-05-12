<template>
  <TableComponent
    :data="packages"
    :keys="keys"
    :loading="pending"
    :actions="props.actionsEnabled"
    title="Orders Packages"
    @add-order-package="addOrderPackage($event)"
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

const emit = defineEmits(["addOrderPackage"]);

const keys = ref([
  {
    key: "name",
    label: "Name"
  }
]);

const { data: packages, pending } = await useLazyAsyncData(
  "orderPackages",
  () => $fetch(`/api/order-package-types`),
  {
    server: false,
    transform: (data) => {
      data.forEach((element) => {
        element.actions = [
          {
            emit: { name: "addOrderPackage", value: element },
            icon: "fa-regular fa-plus"
          }
        ];
      });

      return data;
    }
  }
);

const addOrderPackage = (orderPackage) => {
  const cleanedOrderPackage = { ...orderPackage };
  delete cleanedOrderPackage.actions;
  emit("addOrderPackage", cleanedOrderPackage);
};
</script>
