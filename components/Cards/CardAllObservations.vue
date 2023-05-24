<template>
  <v-data-table-server
    v-model:per-page="perPage"
    :search="search.value"
    :headers="headers"
    :items-length="observations?.metadata.totalCount"
    :items="observations?.data"
    :loading="loading.value"
    class="elevation-1"
    item-value="date"
    @update:options="loadItems"
  >
    <template v-slot:tfoot>
      <tr>
        <td>
          <v-text-field v-model="date" hide-details label="Search Date" class="ma-2"
                        density="compact" type="date" variant="solo-inverted"></v-text-field>
        </td>
        <td>
          <v-text-field v-model="observablePackage"
                        type="number" hide-details label="Search Observable Package" class="ma-2"
                        density="compact" variant="solo-inverted"></v-text-field>
        </td>
        <td>
          <v-text-field v-model="observer" hide-details label="Search Observer"
                        type="number"
                        density="compact" variant="solo-inverted"></v-text-field>
        </td>
        <td v-if="phenomenonTypes">
          <v-combobox
            label="Select Phenomenon Type"
            hide-details
            class="ma-2"
            v-model="selectedComboBox"
            :items="phenomenonTypes"
            :value="selectedComboBox"
            density="compact"
            variant="solo-inverted"
          ></v-combobox>
        </td>
        <td v-if="selectedComboBox.length > 0">
          <v-text-field v-model="value" hide-details label="Search Value" class="ma-2"
                        density="compact" variant="solo-inverted"></v-text-field>
        </td>
      </tr>
    </template>

  </v-data-table-server>
</template>


<script setup>
const offset = ref(0);
const perPage = ref(10);
const sortedBy = ref([]);

const date = ref("");
const observer = ref("");
const observablePackage = ref("");
const phenomenonType = ref("");
const value = ref("");
const selected = ref([]);
const selectedComboBox = ref([]);

const loading = ref(true);
const search = ref({});

const headers = ref([
  {
    title: "Date",
    align: "start",
    key: "date"
  },
  {
    title: "Observable Package ID",
    align: "end",
    key: "observablePackage"
  },
  {
    title: "Observer ID",
    align: "end",
    key: "observer"
  }
]);

watch([date, observablePackage, observer, selectedComboBox, value], () => {
  attributeValueToSearch();
});

const { data: phenomenonTypes } = await useLazyAsyncData(
  "phenomenonTypes",
  () => $fetch(`/api/observations/phenomenonTypes`)
);

const { data: observations, pending } = await useLazyAsyncData(
  "observations",
  () =>
    $fetch(`/api/observations`, {
      params: {
        offset: offset.value,
        limit: perPage.value,
        sort: sortedBy.value,
        search: search.value
      }
    }),
  {
    server: false,
    transform: (data) => {
      data.data.forEach((element) => {
        element.date =
          new Date(element.date).toLocaleDateString("pt-pt") +
          " - " +
          new Date(element.date).toLocaleTimeString("pt-PT", {
            hour12: false,
            hour: "numeric",
            minute: "numeric"
          });

        element.observablePackage = element.observablePackageId;
        element.observer = element.observerId;


        element[element.phenomenonType] = element.quantity ?? element.category;

        if (element.phenomenonType && !headers.value.some((k) => k.key === element.phenomenonType)) {
          if (element.category) {
            headers.value.push({
              title: element.phenomenonType,
              align: "end",
              sortable: false,
              key: element.phenomenonType
            });
          } else {
            headers.value.push({
              title: element.phenomenonType,
              align: "end",
              key: element.phenomenonType
            });
          }
        }
        // transform the details into a json object to be present in the table as column
        const json = JSON.parse(element.details);

        Object.keys(json).forEach((key) => {
          element[key] = json[key];
          if (!headers.value.some((k) => k.key === key)) {
            headers.value.push({
              title: key,
              align: "end",
              sortable: false,
              key: key
            });
          }
        });
      });

      headers.value = headers.value.filter((column) => {
        if (column.key === "date" || column.key === "observablePackage" || column.key === "observer") {
          return true;
        }
        return data.data.some((element) => element[column.key] !== undefined);
      });

      return data;
    },
    watch: [offset, perPage, sortedBy, search]
  }
);

const attributeValueToSearch = () => {
  search.value = { date: date.value, observablePackage: observablePackage.value, observer: observer.value, phenomenonType: selectedComboBox.value, value: value.value };
};

const loadItems = ({ page, itemsPerPage, sortBy }) => {
  loading.value = true;
  offset.value = (page - 1) * itemsPerPage;
  perPage.value = itemsPerPage;
  sortedBy.value = sortBy;
  loading.value = false;
};


</script>
