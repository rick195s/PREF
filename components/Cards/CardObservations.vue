<template>
  <TableComponent
    :data="observations"
    :loading="pending || props.loading"
    :keys="keys"
    title="History"
  ></TableComponent>
</template>

<script setup>
import TableComponent from "@/components/Tables/TableComponent.vue";

// Define the props
const props = defineProps({
  loading: {
    type: Boolean,
    required: true,
    default: () => true
  },
  selectedPackages: {
    type: Array,
    required: false
  }
});

// Define the initial keys
const keys = ref([
  {
    key: "date",
    label: "Date"
  },
  {
    key: "observablePackageId",
    label: "Observable Package ID"
  },
  {
    key: "observerId",
    label: "Observer ID"
  }
]);

const url = computed(() => {
  let newUrl = `/api/observations/package`;
  if (props.selectedPackages.length > 0) {
    newUrl += `?id=${props.selectedPackages.join("&id=")}`;
  }
  return newUrl;
});

const { data: observations, pending } = await useLazyAsyncData(
  "observations",
  () => {
    if (props.selectedPackages.length > 0) {
      return $fetch(url.value, {});
    }
  },
  {
    transform: (data) => {
      // Sort the observations by date in descending order (most recent date appears first)
      data = data.flat().sort((a, b) => new Date(b.date) - new Date(a.date));

      data.forEach((element) => {
        element.date =
          new Date(element.date).toLocaleDateString("pt-pt") +
          " - " +
          new Date(element.date).toLocaleTimeString("pt-PT", {
            hour12: false,
            hour: "numeric",
            minute: "numeric"
          });

        element[element.phenomenonType] = element.quantity ?? element.category;

        if (
          element.phenomenonType &&
          !keys.value.some((k) => k.key === element.phenomenonType)
        ) {
          keys.value.push({
            key: element.phenomenonType,
            label: element.phenomenonType
          });
        }

        // transform the details into a json object to be present in the table as column
        const json = JSON.parse(element.details);

        Object.keys(json).forEach((key) => {
          element[key] = json[key];
          if (!keys.value.some((k) => k.key === key)) {
            keys.value.push({
              key: key,
              label: key
            });
          }
        });
      });

      return data;
    },
    server: false,
    watch: [props.selectedPackages]
  }
);
</script>
