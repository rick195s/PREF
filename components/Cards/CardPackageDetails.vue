<template>
  <div
    class="relative flex flex-col min-w-0 break-words bg-white w-full mb-6 shadow-lg rounded"
  >
    <div class="rounded-t mb-0 px-4 py-3 border-0">
      <div class="flex flex-wrap items-center">
        <div class="relative w-full px-4 max-w-full flex-grow flex-1">
          <h3 class="font-semibold text-base text-blueGray-700">
            Package Details
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
              Type
            </th>
            <td
              class="px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
            >
              {{ packageData.packageType }}
            </td>
          </tr>
          <tr>
            <th
              class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              Cost
            </th>
            <td
              class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
            >
              {{ packageData.cost }}€
            </td>
          </tr>
          <tr>
            <th
              class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              Dimensions
            </th>
            <td
              class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
            >
              {{ packageData.dimension }}
            </td>
          </tr>
          <tr>
            <th
              class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              Materials
            </th>
            <td
              class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
            >
              {{
                packageData.materialsType
                  .map(
                    (item) =>
                      item.slice(0, 1).toUpperCase() +
                      item.slice(1).toLowerCase()
                  )
                  .join(", ")
              }}
            </td>
          </tr>
          <tr>
            <th
              class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              Resistance
            </th>
            <td
              class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
            >
              {{ packageData.resistance }}
            </td>
          </tr>
          <tr>
            <th
              class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              Smart
            </th>
            <td
              class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
            >
              {{ packageData.smart ? "Yes" : "No" }}
            </td>
          </tr>
          <tr>
            <th
              class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              Sustainable
            </th>
            <td
              class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
            >
              {{ packageData.sustainable ? "Yes" : "No" }}
            </td>
          </tr>
          <tr>
            <th
              class="px-6 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
            >
              Category
            </th>
            <td
              class="border-t-0 px-6 align-middle border border-solid border-blueGray-100 border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left"
            >
              {{ packageData.category }}
            </td>
          </tr>
        </thead>
      </table>
    </div>
  </div>
</template>
<script setup>
const runtimeConfig = useRuntimeConfig();

const packageUrl =
  runtimeConfig.public.apiUrl + `/packages/${useRoute().params.id}`;

const { data: packageData } = await useFetch(packageUrl);
</script>
