<template>
  <v-data-table-server
    v-model:per-page="perPage"
    :search="search"
    :headers="headers"
    :items-length="observations?.metadata.totalCount"
    :items="observations?.data"
    :loading="loading.value"
    class="elevation-1"
    item-value="date"
    @update:options="loadItems"
  ></v-data-table-server>
</template>


<script setup>
const offset = ref(0);
const perPage = ref(10);
const sortedBy = ref([]);

const loading = ref(true);
const search = ref("");

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

const { data: observations, pending } = await useLazyAsyncData(
  "observations",
  () =>
    $fetch(`/api/observations`, {
      params: {
        offset: offset.value,
        limit: perPage.value,
        sort: sortedBy.value,
      }
    }),
  {
    server: false,
    transform: (data) => {
      console.log("DATA",data);
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
          if (element.category){
            headers.value.push({
              title: element.phenomenonType,
              align: "end",
              sortable: false,
              key: element.phenomenonType
            });
          }else {
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
        if (column.key === "date" || column.key === "observablePackageId" || column.key === "observerId") {
          return true;
        }
        return data.data.some((element) => element[column.key] !== undefined);
      });

      return data;
    },
    watch: [offset, perPage, sortedBy]
  }
);
const loadItems = ({ page, itemsPerPage, sortBy }) => {
  loading.value = true;
  offset.value = (page - 1) * itemsPerPage;
  perPage.value = itemsPerPage;
  if (sortBy.length !== 0) {
    sortedBy.value = sortBy;
  }
  loading.value = false;
};


</script>
