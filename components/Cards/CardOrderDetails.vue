<template>
  <div
    class="relative flex flex-col min-w-0 break-words bg-white w-full mb-6 shadow-lg rounded"
  >
    <div class="rounded-t mb-0 px-4 py-3 border-0">
      <div class="flex flex-wrap items-center">
        <div class="relative w-full px-4 max-w-full flex-grow flex-1">
          <h3 class="font-semibold text-base text-blueGray-700">Order Details</h3>
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
            {{ new Date(ordersData.orderDate).toLocaleDateString('pt-pt') }}
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
            {{ new Date(ordersData.orderDate).toLocaleTimeString('pt-PT', {hour12: false, hour: 'numeric', minute: 'numeric'}) }}
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
        </tr> <tr>
          <th
            class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Shipping Methods
          </th>
          <td
            class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
          >
            {{ ordersData.shippingMethods.join(', ') }}
          </td>
        </tr>
        </thead>
      </table>
    </div>
  </div>
  <div
    class="relative flex flex-col min-w-0 break-words bg-white w-full mb-6 shadow-lg rounded"
  >
    <div class="rounded-t mb-0 px-4 py-3 border-0">
      <div class="flex flex-wrap items-center">
        <div class="relative w-full px-4 max-w-full flex-grow flex-1">
          <h3 class="font-semibold text-base text-blueGray-700">Products</h3>
        </div>
      </div>
    </div>
    <div class="block w-full overflow-x-auto">
      <!-- Products table -->
      <table class="items-center w-full bg-transparent border-collapse">
        <thead>
        <tr>
          <th
            class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Id
          </th>
          <th
            class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Name
          </th>
          <th
            class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Vadility Range
          </th>
          <th
            class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Dimensions
          </th>
          <th
            class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Weight
          </th>
          <th
            class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Price
          </th>
          <th
            class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Category
          </th>
          <th
            class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Quantidade
          </th>
          <th
            class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
          >
            Package
          </th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="orderLine in ordersData.orderLines" :key="orderLine.id">

          <th
            class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
          >
            {{ orderLine.product.id }}
          </th>
          <td
            class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
          >
            {{ orderLine.product.name }}
          </td>
          <td
            class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
          >
            {{ orderLine.product.validityRange }}
          </td>
          <td
            class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
          >
            {{ orderLine.product.length }}x{{ orderLine.product.width }}x{{ orderLine.product.height }}
          </td>
          <td
            class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
          >
            {{ orderLine.product.weight }}kg
          </td>
          <td
            class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
          >
            {{ orderLine.product_price }}€
          </td>
          <td
            class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
          >
            {{ orderLine.product.category }}
          </td>
          <td
            class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
          >
            {{ orderLine.quantity }}
          </td>

          <td class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left">
            <div v-if="!orderLine.simplePackage?.packageType" class="flex items-center">
              <select v-model="orderLine.simplePackage"
                      class="form-select block appearance-none w-full bg-gray-200 border border-gray-200 text-gray-700 py-2 px-4 pr-8 rounded leading-tight focus:outline-none focus:bg-white focus:border-gray-500 mb-2 md:mb-0 mr-2">
                <option disabled selected :value="null">Select a package</option>
                <option v-for="simplePackage in packagesData" :key="simplePackage.id" :value="simplePackage.id">
                  {{ simplePackage.packageType }} - {{ simplePackage.dimension }}
                </option>
              </select>
              <svg class="fill-current h-4 w-4 md:ml-2 svg-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"
                   @click="choosePackage(orderLine.simplePackage, orderLine)">
                <path
                  d="M17.5 0h-15C1.675 0 1.07.605 1.07 1.346v17.308c0 .741.604 1.346 1.346 1.346h15c.742 0 1.346-.605 1.346-1.346V1.346c0-.741-.604-1.346-1.346-1.346zM15.4 4.038H4.6c-.17 0-.308-.138-.308-.308s.138-.308.308-.308h10.8c.17 0 .308.138.308.308s-.138.308-.308.308zm-5.4 5.192h-5c-.17 0-.308-.138-.308-.308s.138-.308.308-.308h5c.17 0 .308.138.308.308s-.138.308-.308.308zm0 3.077h-5c-.17 0-.308-.138-.308-.308s.138-.308.308-.308h5c.17 0 .308.138.308.308s-.138.308-.308.308zm5.4-8.846H4.6c-.17 0-.308-.138-.308-.308s.138-.308.308-.308h10.8c.17 0 .308.138.308.308s-.138.308-.308.308z" />
              </svg>
            </div>
            <div v-else>
              {{ orderLine.simplePackage.packageType }}
            </div>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script setup>
const ordersUrl = `https://pref.azurewebsites.net/pref/api/orders/${useRoute().params.trackingNumber}`;
const packagesUrl = `https://pref.azurewebsites.net/pref/api/packages`;

const { data: ordersData } = await useFetch(ordersUrl);
const { data: packagesData } = await useFetch(packagesUrl);

const choosePackage = async (selectedPackage, orderLine) => {
  const url = `https://pref.azurewebsites.net/pref/api/ordersLine/${orderLine.id}`
  const options = {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ simplePackageId: selectedPackage })
  }
  const { data, error } = await useFetch(url, options)
  location.reload()

}


</script>


<style scoped>
.svg-icon {
  height: 35px;
  width: 35px;
}

select {
  max-width: 100%;
  text-overflow: ellipsis;
  white-space: nowrap;
  padding-right: 25px;
  appearance: none;
}

select option[selected] {
  display: none;
}


</style>