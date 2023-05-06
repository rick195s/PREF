<template>
  <TableComponent
    :data="observations"
    :loading="pending"
    :keys="keys"
    title="Package History"
  ></TableComponent>
</template>

<script setup>
import { ref } from 'vue';
import TableComponent from "@/components/Tables/TableComponent.vue";

const keys = ref([
  {
    key: "date",
    label: "Date"
  },
  {
    key: "hour",
    label: "Hour"
  }
]);

let phenomenonTypes = [];
const { data: observations, pending } = await useLazyAsyncData(
  "observations",
  () => $fetch(`/api/observations/package/${useRoute().params.id}`, {}),
  {
    server: false,
    transform: (data) => {
      data.forEach((element) => {
        element.date = new Date(element.date).toLocaleDateString("pt-pt");
        element.hour = new Date(element.date).toLocaleTimeString("pt-PT", {
          hour12: false,
          hour: "numeric",
          minute: "numeric"
        });

        // Assign the value to the corresponding phenomenon type key
        if (element.quantity?.value) {
          element[element.phenomenonType] = element.quantity.value;
        } else if (element.category?.value) {
          element[element.phenomenonType] = element.category.value;
        }

        if (element.phenomenonType && !phenomenonTypes.includes(element.phenomenonType)) {
          phenomenonTypes.push(element.phenomenonType);
          keys.value.push({
            key: element.phenomenonType,
            label: element.phenomenonType
          });
        }
      });

      // Add "Details" key at the end
      keys.value.push({
        key: "details",
        label: "Details"
      });

      return data;
    }
  });

console.log(observations);
</script>
