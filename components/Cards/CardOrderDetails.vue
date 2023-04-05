<template>
  <div
    class="relative flex flex-col min-w-0 break-words bg-white w-full mb-6 shadow-lg rounded"
  >
    <div class="rounded-t mb-0 px-4 py-3 border-0">
      <div class="flex flex-wrap items-center">
        <div class="relative w-full px-4 max-w-full flex-grow flex-1">
          <h3 class="font-semibold text-base text-blueGray-700">
            Order Details
          </h3>
        </div>
      </div>
    </div>
    <div class="block w-full overflow-x-auto">
      <!-- Order table -->
      <table class="items-center w-full bg-transparent border-collapse">
        <thead>
        <tr>
          <th
            class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Tracking Number
          </th>
          <td
            class="px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
          >
            {{ ordersData.trackingNumber }}
          </td>
        </tr>
        <tr>
          <th
            class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Date
          </th>
          <td
            class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
          >
            {{ new Date(ordersData.orderDate).toLocaleDateString("pt-pt") }}
          </td>
        </tr>
        <tr>
          <th
            class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Hour
          </th>
          <td
            class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
          >
            {{
              new Date(ordersData.orderDate).toLocaleTimeString("pt-PT", {
                hour12: false,
                hour: "numeric",
                minute: "numeric"
              })
            }}
          </td>
        </tr>
        <tr>
          <th
            class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Source
          </th>
          <td
            class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
          >
            {{ ordersData.source }}
          </td>
        </tr>
        <tr>
          <th
            class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Destination
          </th>
          <td
            class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
          >
            {{ ordersData.destination }}
          </td>
        </tr>
        <tr>
          <th
            class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            State
          </th>
          <td
            class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
          >
            {{ ordersData.state }}
          </td>
        </tr>
        <tr>
          <th
            class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Weight
          </th>
          <td
            class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
          >
            {{ ordersData.weight.toFixed(2) }}kg
          </td>
        </tr>
        <tr>
          <th
            class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Carrier
          </th>
          <td
            class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
          >
            {{ ordersData.carrier }}
          </td>
        </tr>
        <tr>
          <th
            class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Shipping Methods
          </th>
          <td
            class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
          >
            {{ ordersData.shippingMethods.join(", ") }}
          </td>
        </tr>
        </thead>
      </table>
    </div>
  </div>
</template>
<script setup>
const runtimeConfig = useRuntimeConfig();

const ordersUrl =
  runtimeConfig.public.apiUrl + `/orders/${useRoute().params.trackingNumber}`;

const { data: ordersData } = await useFetch(ordersUrl);

</script>
