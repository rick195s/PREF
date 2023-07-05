<template>
  <div
    class="relative flex h-full flex-col min-w-0 break-words bg-white rounded mb-6 xl:mb-0 shadow-lg"
  >
    <div v-if="props.loading" class="text-center h-24 align-middle">
      <SpinnerComponent />
    </div>
    <div v-else class="flex-auto p-4">
      <div class="flex flex-wrap">
        <div class="relative w-full pr-4 max-w-full flex-grow flex-1">
          <h5 class="text-blueGray-400 uppercase font-bold text-xs">
            {{ statSubtitle }}
          </h5>
          <span class="font-semibold text-xl text-blueGray-700">
            {{ statTitle }}
          </span>
        </div>

        <div
          class="justify-between relative w-auto pl-4 flex flex-col flex-initial"
        >
          <div
            class="p-3 text-center border border-black inline-flex items-center justify-center w-12 h-12 shadow-lg rounded-full"
            :class="[statIconColor, statIconBackground]"
          >
            <i :class="[statIconName]"></i>
          </div>
          <div
            v-if="props.chartDatasets.length !== 0"
            class="p-3 text-center inline-flex items-center justify-center hover:cursor-pointer"
            @click="handleCardClick"
          >
            <i class="fa-regular fa-eye hover:text-red-600"></i>
          </div>
        </div>
      </div>
      <p v-if="statDescripiron" class="text-sm text-blueGray-400 mt-4">
        <span v-if="statPercent" class="mr-2" :class="[statPercentColor]">
          <i
            :class="[
              statArrow === 'up' ? `fas fa-arrow-up` : `fas fa-arrow-down`
            ]"
          ></i>
          {{ statPercent }}%
        </span>
        <span class="whitespace-nowrap">{{ statDescripiron }}</span>
      </p>
    </div>
  </div>
</template>
<script setup>
import { useRouter } from "vue-router";
import SpinnerComponent from "@/components/Spinner/SpinnerComponent.vue";
const router = useRouter();

const handleCardClick = () => {
  if (props.chartDatasets.length !== 0) {
    router.push({
      path: "/chart/chartUsers",
      query: {
        title: props.statSubtitle,
        chartDataset: JSON.stringify(props.chartDatasets)
      }
    });
  }
};

const props = defineProps({
  statSubtitle: {
    type: String,
    default: "Traffic"
  },
  chartDatasets: {
    type: Array,
    default: () => []
  },
  statTitle: {
    type: String,
    default: "350,897"
  },
  statArrow: {
    default: "up",
    validator: function (value) {
      // The value must match one of these strings
      return ["up", "down"].indexOf(value) !== -1;
    }
  },
  statPercent: {
    type: String,
    default: null
  },
  // can be any of the text color utilities
  // from tailwindcss
  statPercentColor: {
    type: String,
    default: "text-red-500"
  },
  statDescripiron: {
    type: String,
    default: null
  },
  statIconName: {
    type: String,
    default: "fas fa-triangle-exclamation"
  },
  // can be any of the background color utilities
  // from tailwindcss
  statIconColor: {
    type: String,
    default: "white"
  },
  statIconBackground: {
    type: String,
    default: "bg-red-500"
  },
  loading: {
    type: Boolean,
    default: true
  }
});
</script>
