<template>
  <div class="py-2">
    <nav class="block">
      <ul class="flex pl-0 rounded list-none flex-wrap justify-center">
        <li v-if="hasPrev">
          <a
            class="cursor-pointer first:ml-0 text-xs font-semibold flex w-8 h-8 mx-1 p-0 rounded-full items-center justify-center leading-tight relative border border-solid border-red-500 bg-white text-red-500"
            @click="changePage(props.currentPage - 1)"
          >
            <i class="fas fa-chevron-left -ml-px"></i>
          </a>
        </li>
        <li v-for="page in pages()" :key="page" @click="changePage(page)">
          <a
            class="first:ml-0 text-xs font-semibold flex w-8 h-8 mx-1 p-0 rounded-full items-center justify-center leading-tight relative border border-solid border-red-500"
            :class="
              page === props.currentPage
                ? 'bg-red-500 text-white'
                : 'bg-white text-red-500 cursor-pointer'
            "
          >
            {{ page }}
          </a>
        </li>

        <li>
          <a
            v-if="hasNext"
            class="cursor-pointer first:ml-0 text-xs font-semibold flex w-8 h-8 mx-1 p-0 rounded-full items-center justify-center leading-tight relative border border-solid border-red-500 bg-white text-red-500"
            @click="changePage(props.currentPage + 1)"
          >
            <i class="fas fa-chevron-right -mr-px"></i>
          </a>
        </li>
      </ul>
    </nav>
  </div>
</template>

<script setup>
import { ref } from "vue";
const props = defineProps({
  total: {
    type: Number,
    required: true
  },
  perPage: {
    type: Number,
    required: true
  },
  currentPage: {
    type: Number,
    required: true
  }
});

const emit = defineEmits(["changePage"]);

const totalPages = ref(props.total / props.perPage);
const hasPrev = ref(props.currentPage != 1);
const hasNext = ref(props.currentPage != totalPages.value);

const pages = () => {
  const pagesArray = [];
  let startPage = Math.max(1, props.currentPage - 2);
  let endPage = Math.min(totalPages.value, startPage + 4);

  if (endPage - startPage < 4) {
    startPage = Math.max(1, endPage - 4);
  }

  for (let i = startPage; i <= endPage; i++) {
    pagesArray.push(i);
  }
  return pagesArray;
};

function changePage(page) {
  if (page != props.currentPage) {
    hasNext.value = page != totalPages.value;
    hasPrev.value = page != 1;
    emit("changePage", page);
  }
}
</script>
