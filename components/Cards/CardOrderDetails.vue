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
              {{ props.orderData?.trackingNumber }}
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
              {{
                new Date(props.orderData?.orderDate).toLocaleDateString("pt-pt")
              }}
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
                new Date(props.orderData?.orderDate).toLocaleTimeString(
                  "pt-PT",
                  {
                    hour12: false,
                    hour: "numeric",
                    minute: "numeric"
                  }
                )
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
              {{ props.orderData?.source }}
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
              {{ props.orderData?.destination }}
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
              Shipping Methods
            </th>
            <td
              class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
            >
              {{ props.orderData?.shippingMethods.join(", ") }}
            </td>
          </tr>
          <tr>
            <th
              class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              Package
            </th>
            <td
              class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
            >
              <div
                v-if="
                  props.orderData != null &&
                  !props.orderData?.orderPackage?.name
                "
                class="flex items-center"
              >
                <select
                  v-model="props.orderData.orderPackage"
                  class="form-select block appearance-none w-full bg-gray-200 border border-gray-200 text-gray-700 py-2 px-4 pr-8 rounded leading-tight focus:outline-none focus:bg-white focus:border-gray-500 mb-2 md:mb-0 mr-2"
                >
                  <option
                    v-if="!orderPackages || orderPackages.length === 0"
                    disabled
                    selected
                    :value="null"
                  >
                    No packages available
                  </option>
                  <option v-else disabled selected :value="null">
                    Select a package
                  </option>
                  <option
                    v-for="orderPackage in orderPackages"
                    :key="orderPackage?.id"
                    :value="orderPackage?.id"
                  >
                    {{ orderPackage?.name }} -
                    {{ orderPackage?.dimension }}
                  </option>
                </select>
                <svg
                  v-if="isPackageSelected(props.orderData?.orderPackage)"
                  class="fill-current h-4 w-4 md:ml-2 svg-icon"
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 20 20"
                  @click="
                    choosePackage(
                      props.orderData?.orderPackage,
                      props.orderData?.trackingNumber
                    )
                  "
                >
                  <path
                    d="M17.5 0h-15C1.675 0 1.07.605 1.07 1.346v17.308c0 .741.604 1.346 1.346 1.346h15c.742 0 1.346-.605 1.346-1.346V1.346c0-.741-.604-1.346-1.346-1.346zM15.4 4.038H4.6c-.17 0-.308-.138-.308-.308s.138-.308.308-.308h10.8c.17 0 .308.138.308.308s-.138.308-.308.308zm-5.4 5.192h-5c-.17 0-.308-.138-.308-.308s.138-.308.308-.308h5c.17 0 .308.138.308.308s-.138.308-.308.308zm0 3.077h-5c-.17 0-.308-.138-.308-.308s.138-.308.308-.308h5c.17 0 .308.138.308.308s-.138.308-.308.308zm5.4-8.846H4.6c-.17 0-.308-.138-.308-.308s.138-.308.308-.308h10.8c.17 0 .308.138.308.308s-.138.308-.308.308z"
                  />
                </svg>
              </div>
              <div v-if="errorMessage" class="text-red-500 mb-2 md:mb-0">
                {{ errorMessage }}
              </div>
              <div v-if="props.orderData?.orderPackage?.name">
                <div style="display: flex; align-items: center">
                  <span style="margin-right: 30px">{{
                    props.orderData?.orderPackage?.name
                  }}</span>
                  <NuxtLink
                    :to="'/observations/' + props.orderData?.orderPackage?.id"
                  >
                    <button
                      class="bg-red-500 text-white active:bg-red-600 font-bold uppercase text-xs px-4 py-2 rounded shadow hover:shadow-md outline-none focus:outline-none"
                      type="button"
                    >
                      Track Package
                    </button>
                  </NuxtLink>
                </div>
              </div>
            </td>
          </tr>
        </thead>
      </table>
    </div>
  </div>
</template>
<script setup>
const props = defineProps({
  orderData: {
    type: Object,
    required: true
  }
});

const { data: orderPackages } = await useLazyAsyncData(
  "orderPackages",
  () => $fetch(`/api/orderPackages`, {}),
  {
    server: false
  }
);

const orderWithError = ref("");
const errorMessage = ref(null);

const isPackageSelected = (order) => {
  return order !== null;
};

const choosePackage = async (selectedPackage, orderId) => {
  await useLazyAsyncData(
    "orderLines",
    () =>
      $fetch(`/api/orders/${orderId}`, {
        method: "PATCH",
        body: JSON.stringify({
          orderPackageId: selectedPackage
        }),
        onResponse: () => {
          orderWithError.value = "";
          errorMessage.value = null;
          location.reload();
        },
        onResponseError: () => {
          orderWithError.value = orderId;
          errorMessage.value = "An error occurred while choosing the package";
        }
      }),
    {
      server: false
    }
  );
};
</script>
