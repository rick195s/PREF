<template>
  <div
    class="relative flex flex-col min-w-0 break-words bg-white w-full mb-6 shadow-lg rounded"
  >
    <div class="rounded-t mb-0 px-4 py-3 border-0">
      <div class="flex flex-wrap items-center">
        <div class="relative w-full px-4 max-w-full flex-grow flex-1">
          <h3 class="font-semibold text-base text-blueGray-700">Orders</h3>
        </div>
      </div>
    </div>
    <div class="block w-full overflow-x-auto">
      <!-- Projects table -->
      <table class="items-center w-full bg-transparent border-collapse">
        <thead>
          <tr>
            <th
              class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              Tracking Number
            </th>
            <th
              class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              Date
            </th>
            <th
              class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              Source
            </th>
            <th
              class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              Destination
            </th>
            <th
              class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              State
            </th>
            <th
              class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              Weight
            </th>
            <th
              class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              Carrier
            </th>
            <th
              class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              Actions
            </th>
          </tr>
        </thead>
        <tbody v-if="!orders">
        <tr class="w-full">
          <td class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs p-4 text-center font-bold mx-auto" colspan="8">
            <div class="flex justify-center items-center h-full">
              <div class="animate-spin rounded-full border-t-4 border-gray-500 border-solid h-12 w-12 mr-4"></div>
            </div>
          </td>
        </tr>
        </tbody>

        <tbody v-else-if="orders.data.length === 0">
        <tr class="w-full">
          <td class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs p-4 text-center font-bold mx-auto" colspan="8">
            No data found
          </td>
        </tr>
        </tbody>
        <tbody v-else>
          <tr v-for="order in orders?.data" :key="order.trackingNumber">
            <th
              class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
            >
              {{ order.trackingNumber }}
            </th>
            <td
              class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
            >
              {{ new Date(order.orderDate).toLocaleDateString("pt-pt") }} -
              {{
                new Date(order.orderDate).toLocaleTimeString("pt-PT", {
                  hour12: false,
                  hour: "numeric",
                  minute: "numeric"
                })
              }}
            </td>
            <td
              class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
            >
              {{ order.source }}
            </td>
            <td
              class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
            >
              {{ order.destination }}
            </td>
            <td
              class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
            >
              {{ order.state }}
            </td>
            <td
              class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
            >
              {{ order.weight.toFixed(2) }}kg
            </td>
            <td
              class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
            >
              {{ order.carrier }}
            </td>
            <td
              class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
            >
              <NuxtLink :to="'/orders/' + order.trackingNumber">
                <button
                  class="bg-emerald-500 text-white active:bg-emerald-600 font-bold uppercase text-xs px-4 py-2 rounded shadow hover:shadow-md outline-none focus:outline-none mr-1 mb-1 ease-linear transition-all duration-150"
                  type="button"
                >
                  <i class="fa-regular fa-pen-to-square"></i>
                </button>
              </NuxtLink>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <PaginationComponent
      v-if="orders != null"
      :total="orders.metadata.totalCount"
      :per-page="perPage"
      :current-page="currentPage"
      @change-page="offset = ($event - 1) * perPage"
    />
  </div>
</template>
<script setup>
import PaginationComponent from "@/components/Pagination/PaginationComponent.vue";

const runtimeConfig = useRuntimeConfig();

const offset = ref(0);
const perPage = ref(10);
const currentPage = computed(() =>
  offset.value == 0 ? 1 : offset.value / perPage.value + 1
);

const { data: orders } = await useAsyncData(
  "orders",
  () =>
    $fetch(`/orders`, {
      method: "GET",
      baseURL: runtimeConfig.public.apiUrl,
      params: {
        offset: offset.value,
        limit: perPage.value
      }
    }),
  {
    watch: [offset, perPage]
  }
);
</script>
