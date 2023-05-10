<template>
  <TableComponent
    :data="observations"
    :loading="loading"
    :keys="keys"
    title="History"
  ></TableComponent>
</template>

<script setup>
import { ref, watch } from "vue";
import TableComponent from "@/components/Tables/TableComponent.vue";

const props = defineProps({
  orderData: {
    type: Object,
    required: true,
    default: () => null
  }
});

const keys = ref([
  {
    key: "date",
    label: "Date"
  },
  {
    key: "hour",
    label: "Hour"
  },
  {
    key: "package",
    label: "Package ID"
  }
]);

let phenomenonTypes = [];
const loading = ref(true);
const observations = ref([]);

watch(() => props.orderData, (newOrderData) => {
  if (newOrderData) {
    fetchData(newOrderData);
  }
});

async function fetchData(orderData) {
  const orderPackages = orderData.orderPackages;
  const orderLines = orderData.orderLines;
  const productPackages = [];
  const orderPackagesToFetch = [];

  // Collect unique product package IDs from order lines
  orderLines.forEach((orderLine) => {
    orderLine.product.productPackages.forEach((productPackage) => {
      if (!productPackages.includes(productPackage.id)) {
        productPackages.push(productPackage.id);
      }
    });
  });

  orderPackages.forEach((orderPackage) => {
    if (!orderPackagesToFetch.includes(orderPackage.id)) {
      orderPackagesToFetch.push(orderPackage.id);
    }
  });

  // Generate URLs for each combination of order package ID and product package ID
  const urls = [];
  orderPackagesToFetch.forEach((orderPackage) => {
    const url = `/api/observations/package/${orderPackage}`;
    urls.push(url);
  });
  productPackages.forEach((productPackageId) => {
    const url = `/api/observations/package/${productPackageId}`;
    urls.push(url);
  });

  // Fetch observations for each URL concurrently
  const fetchPromises = urls.map((url) => $fetch(url, {}));
  const fetchedObservations = await Promise.all(fetchPromises);

  // Flatten the array of fetched observations
  const flattenedObservations = fetchedObservations.flat();

  flattenedObservations.forEach((element) => {
    element.date = new Date(element.date).toLocaleDateString("pt-pt");
    element.hour = new Date(element.date).toLocaleTimeString("pt-PT", {
      hour12: false,
      hour: "numeric",
      minute: "numeric"
    });

    element.package = element.simplePackageId;

    if (element.quantity) {
      element[element.phenomenonType] = element.quantity;
    } else if (element.category) {
      element[element.phenomenonType] = element.category;
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

  observations.value = flattenedObservations;
  loading.value = false;
}

// Initial fetch
if (props.orderData) {
  fetchData(props.orderData);
}
</script>
