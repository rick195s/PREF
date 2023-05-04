<template>
  <div
    class="relative flex flex-col min-w-0 break-words bg-white w-full mb-6 shadow-lg rounded"
  >
    <div class="rounded-t mb-0 px-4 py-3 border-0">
      <div class="flex flex-wrap items-center">
        <div class="relative w-full px-4 max-w-full flex-grow flex-1">
          <h3 class="font-semibold text-base text-blueGray-700">
            Package History
          </h3>
        </div>
      </div>
    </div>
    <div class="block w-full overflow-x-auto">
      <!-- Projects table -->
      <table class="items-center w-full bg-transparent border-collapse">
        <thead v-if="!observations || observations.length === 0">
        <tr>
          <th
            class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Date
          </th>
          <th
            class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Hour
          </th>
          <th
            class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Details
          </th>
        </tr>
        </thead>
        <thead v-else>
        <tr>
          <th
            class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Date
          </th>
          <th
            class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Hour
          </th>
          <th v-for="phenomenon in phenomenonTypes" :key="phenomenon.id"
              class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            {{ phenomenon }}
          </th>
          <th
            class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Details
          </th>
        </tr>
        </thead>
        <tbody v-if="!observations">
        <tr class="w-full">
          <td
            class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs p-4 text-center font-bold mx-auto"
            colspan="8"
          >
            <div class="flex justify-center items-center h-full">
              <div
                class="animate-spin rounded-full border-t-4 border-gray-500 border-solid h-12 w-12 mr-4"
              ></div>
            </div>
          </td>
        </tr>
        </tbody>
        <tbody v-else-if="observations.length === 0 || observations == null">
        <tr class="w-full">
          <td
            class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs p-4 text-center font-bold mx-auto"
            colspan="6"
          >
            No data found
          </td>
        </tr>
        </tbody>
        <tbody v-else>
        <tr v-for="log in observations" :key="log.id">
          <td
            class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
          >
            {{ new Date(log.date).toLocaleDateString("pt-pt") }}
          </td>
          <td
            class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
          >
            {{ new Date(log.date).toLocaleTimeString("pt-PT") }}
          </td>
          <td
            class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
            v-for="phenomenon in phenomenonTypes"
            :key="phenomenon.id"
          >
    <span v-if="log.phenomenonType === phenomenon">
      {{ log.quantity?.value || log.category?.value }}
    </span>
            <span v-else style="display: inline-block; width: 100%; text-align: center">-</span>

          </td>
          <td
            class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
          >
            {{log.details}}
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script setup>

import { watchEffect } from "vue";

let phenomenonTypes = [];

const { data: observations } = await useLazyAsyncData(
  "orderLines",
  () => $fetch(`/api/observations/package/${useRoute().params.id}`, {}),
  {
    server: false
  }
);

watchEffect(() => {
  const observationsValue = observations?.value;
  if (observationsValue && observationsValue.length > 0) {
    phenomenonTypes = observationsValue.map((observation) => observation.phenomenonType)
      .filter((value, index, self) => self.indexOf(value) === index);
  }
});

</script>
