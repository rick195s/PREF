<template>
  <TableComponent
    :data="observations"
    :loading="props.loading"
    :keys="keys"
    title="History"
  ></TableComponent>
</template>

<script setup>
import TableComponent from "@/components/Tables/TableComponent.vue";

// Define the props
const props = defineProps({
  orderData: {
    type: Object,
    required: true,
    default: () => null
  },
  loading: {
    type: Boolean,
    required: true,
    default: () => true
  },
  selectedPackages: {
    type: Array,
    required: true,
    default: () => []
  }
});

// Define the initial keys
const keys = ref([
  {
    key: "date",
    label: "Date"
  },
  {
    key: "simplePackageId",
    label: "Package ID"
  },
  {
    key: "observerId",
    label: "Observer ID"
  }
]);

const phenomenonTypes = ref([]);
const observations = ref([]);

// Watch for changes in props.orderData and props.selectedPackages
watchEffect(() => {
  fetchData(props.orderData, props.selectedPackages);
});

// Function to fetch multiple data based on orderData and selectedPackages
async function fetchData(orderData, selectedPackages) {
  // If orderData is null, return
  if (!orderData) {
    return;
  }

  // Get the order packages from orderData
  const orderPackages = orderData.orderPackages;
  const orderPackagesToFetch = [];

  // Get the IDs of the order packages and add them to an array
  orderPackages.forEach((orderPackage) => {
    if (!orderPackagesToFetch.includes(orderPackage.id)) {
      orderPackagesToFetch.push(orderPackage.id);
    }
  });

  // Generate URLs for each combination of order package
  const urls = [];
  orderPackagesToFetch.forEach((orderPackage) => {
    const url = `/api/observations/package/${orderPackage}`;
    urls.push(url);
  });

  // Generate URLs for each combination of selected package
  if (selectedPackages) {
    selectedPackages.forEach((productPackageId) => {
      if (!orderPackagesToFetch.includes(productPackageId)) {
        const url = `/api/observations/package/${productPackageId}`;
        urls.push(url);
      }
    });
  }

  // Fetch observations for each URL concurrently
  const fetchPromises = urls.map((url) => $fetch(url, {}));

  // Wait for all fetches to complete
  const fetchedObservations = await Promise.all(fetchPromises);

  // Flatten the array of fetched observations (transform an array of arrays into a single array)
  const flattenedObservations = fetchedObservations.flat();

  // Sort the observations by date in descending order (most recent date appears first)
  const sortedObservations = flattenedObservations.sort((a, b) => new Date(b.date) - new Date(a.date));

  // Format the observations to have the keys needed for the table
  sortedObservations.forEach((element) => {
    // Format date and hour
    element.date = new Date(element.date).toLocaleDateString("pt-pt") + " - " + new Date(element.date).toLocaleTimeString("pt-PT", {
      hour12: false,
      hour: "numeric",
      minute: "numeric"
    });

    if (element.quantity) {
      element[element.phenomenonType] = element.quantity;
    } else if (element.category) {
      element[element.phenomenonType] = element.category;
    }

    // Add the phenomenonType to the keys array if it doesn't exist
    if (element.phenomenonType && !phenomenonTypes.value.includes(element.phenomenonType)) {
      phenomenonTypes.value.push(element.phenomenonType);
      keys.value.push({
        key: element.phenomenonType,
        label: element.phenomenonType
      });
    }
  });

  // Remove keys from phenomenonTypes that don't exist in the observations ignore Date, simplePackageId and observerId
  //if "HUMIDIY" is not present in any observation (don't has values), it will be removed from "phenomenonTypes" and "keys
  keys.value = keys.value.filter((key) => {
    // Check if the key is date, simplePackageId or observerId
    if (["date", "simplePackageId", "observerId"].includes(key.key)) {
      return true;
    }

    // Check if the key exists in any observation
    return sortedObservations.some((observation) => observation[key.key]);
  });

  // Remove phenomenonTypes that don't have key
  phenomenonTypes.value = phenomenonTypes.value.filter((phenomenonType) => keys.value.some((key) => key.key === phenomenonType));

  observations.value = sortedObservations;

  //remove details in keys array, just to appear always in the end of the array
  keys.value = keys.value.filter((key) => key.key !== "details");
  //add details in keys array
  keys.value.push({
    key: "details",
    label: "Details"
  });
}

</script>
