<template>
  <ClientOnly>
    <div class="break-words bg-white w-full mb-6 shadow-lg rounded">
      <div class="rounded-t mb-0 px-4 py-3 border-0">
        <div class="flex flex-wrap items-center">
          <div class="relative w-full px-4 max-w-full flex-grow flex-1">
            <h3 class="font-semibold text-base text-blueGray-700">
              {{ props.title }}
            </h3>
          </div>
        </div>
      </div>
      <div class="block w-full overflow-x-auto">
        <!-- Projects table -->
        <table class="items-center w-full bg-transparent border-collapse">
          <thead>
            <tr>
              <th
                v-for="arrayKey in props.keys"
                :key="arrayKey.key"
                class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
              >
                {{ arrayKey.label }}
              </th>
              <th
                class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left"
              >
                Actions
              </th>
            </tr>
          </thead>
          <tbody v-if="props.loading">
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

          <tbody v-else-if="!props.data">
            <tr class="w-full">
              <td
                class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs p-4 text-center font-bold mx-auto"
                colspan="8"
              >
                No data found
              </td>
            </tr>
          </tbody>
          <tbody v-else>
            <tr v-for="record in props.data" :key="record">
              <td
                v-for="arrayKey in props.keys"
                :key="arrayKey.key"
                class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
              >
                {{ record[arrayKey.key] }}
              </td>

              <td
                class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4"
              >
                <span v-for="action in record.actions" :key="action">
                  <NuxtLink v-if="action.to" :to="action.to">
                    <button
                      class="bg-red-500 text-white active:bg-red-600 font-bold uppercase text-xs px-4 py-2 rounded shadow hover:shadow-md outline-none focus:outline-none mr-1 mb-1 ease-linear transition-all duration-150"
                      type="button"
                    >
                      <i :class="action.icon"></i>
                    </button>
                  </NuxtLink>
                  <button
                    v-else-if="action.emit"
                    class="bg-red-500 text-white active:bg-red-600 font-bold uppercase text-xs px-4 py-2 rounded shadow hover:shadow-md outline-none focus:outline-none mr-1 mb-1 ease-linear transition-all duration-150"
                    type="button"
                    @click="$emit(action.emit.name, action.emit.value)"
                  >
                    <i :class="action.icon"></i>
                  </button>
                </span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <PaginationComponent
        v-if="props.data != null"
        :total="props.total"
        :per-page="props.perPage"
        :current-page="currentPage"
        @change-page="$emit('changePage', $event)"
      />
    </div>
  </ClientOnly>
</template>
<script setup>
import PaginationComponent from "@/components/Pagination/PaginationComponent.vue";

const props = defineProps({
  data: {
    type: Object,
    default: null
  },
  keys: {
    type: Array,
    default: () => []
  },
  perPage: {
    type: Number,
    default: 10
  },
  total: {
    type: Number,
    default: 0
  },
  currentPage: {
    type: Number,
    default: 1
  },
  title: {
    type: String,
    default: ""
  },
  loading: {
    type: Boolean,
    default: false
  }
});
</script>
