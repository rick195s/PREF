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
        <button
          v-if="props.orderData?.state === 'PENDING'"
          class="bg-red-500 text-white active:bg-red-600 font-bold uppercase text-xs px-4 py-2 rounded shadow hover:shadow-md outline-none focus:outline-none mr-1 ease-linear transition-all duration-150"
          type="button"
          @click="packOrder()"
        >
          Pack Order
        </button>
      </div>
    </div>
    <div
      v-if="props.loading"
      class="flex items-center justify-center"
      style="min-height: 200px"
    >
      <SpinnerComponent></SpinnerComponent>
    </div>
    <div v-else class="block w-full overflow-x-auto">
      <!-- Order table -->
      <table class="items-center w-full bg-transparent border-collapse">
        <thead>
          <tr>
            <th
              class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              Id
            </th>
            <td
              class="px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
            >
              {{ props.orderData?.id }}
            </td>
          </tr>
          <tr>
            <th
              class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              FEEDBACK
            </th>
            <td
              class="px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
            >
              {{ props.orderData?.feedback }}
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
              {{ props.orderData?.orderDate }}
            </td>
          </tr>
          <tr>
            <th
              class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              Prev. Delivery Date
            </th>
            <td
              class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
            >
              {{ props.orderData?.prevDeliveryDateHour === null ? "N/A" : props.orderData?.prevDeliveryDateHour }}
            </td>
          </tr>
          <tr>
            <th
              class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              Distribution Center
            </th>
            <td
              class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
            >
              {{ props.orderData?.distributionCenter }}
            </td>
          </tr>
          <tr>
            <th
              class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              CP Destination
            </th>
            <td
              class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
            >
              {{ props.orderData?.cpDestiny }}
            </td>
          </tr>
          <tr>
            <th
              class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              Store
            </th>
            <td
              class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
            >
              {{ props.orderData?.store }}
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
              {{ props.orderData?.weight.toFixed(2) }}kg
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
              {{ props.orderData?.carrier }}
            </td>
          </tr>
          <tr>
            <th
              class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              Shipping Method
            </th>
            <td
              class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
            >
              {{ props.orderData?.shippingMethod }}
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
              {{ props.orderData?.state }}
            </td>
          </tr>
          <tr>
            <th
              class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              Package Types
            </th>
            <td
              class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
            >
              {{
                props.orderData?.orderPackageTypes
                  ?.map((orderPackage) => orderPackage.id)
                  .join(", ")
              }}
            </td>
          </tr>
          <tr>
            <th
              class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              Packages Id Used
            </th>
            <td
              class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
            >
              {{
                props.orderData?.orderPackages
                  ?.map((orderPackage) => orderPackage.id)
                  .join(", ")
              }}
            </td>
          </tr>
        </thead>
      </table>
    </div>
  </div>
</template>
<script setup>
import SpinnerComponent from "@/components/Spinner/SpinnerComponent.vue";

const props = defineProps({
  loading: {
    type: Boolean,
    default: false
  },
  orderData: {
    type: Object,
    required: false,
    default: () => ({})
  }
});

const packOrder = async () => {
  await useLazyAsyncData(
    "packOrder",
    async () => {
      // Make the callback function async
      const response = await $fetch(
        `/api/orders/${props.orderData.id}/pack`,
        {
          method: "PATCH",
          headers: {
            "Content-Type": "application/json"
          }
        }
      );
      return response;
    },
    {
      server: false
    }
  );
  //reload page
  location.reload();
};
</script>
