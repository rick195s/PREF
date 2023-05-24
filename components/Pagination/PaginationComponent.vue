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
        <li v-if="showFirstPageButton">
          <a
            class="cursor-pointer first:ml-0 text-xs font-semibold flex w-8 h-8 mx-1 p-0 rounded-full items-center justify-center leading-tight relative border border-solid border-red-500 bg-white text-red-500"
            @click="changePage(1)"
          >
            1
          </a>
        </li>
        <li v-if="showFirstPageButton">
          <a
            class="cursor-pointer first:ml-0 text-xs font-semibold flex w-8 h-8 mx-1 p-0 rounded-full items-center justify-center leading-tight relative border border-solid border-red-500 bg-white text-red-500"
          >
            ...
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
        <li v-if="showLastDots">
          <a
            class="cursor-pointer first:ml-0 text-xs font-semibold flex w-8 h-8 mx-1 p-0 rounded-full items-center justify-center leading-tight relative border border-solid border-red-500 bg-white text-red-500"
          >
            ...
          </a>
        </li>
        <li v-if="showLastPageButton">
          <a
            class="cursor-pointer first:ml-0 text-xs font-semibold flex w-8 h-8 mx-1 p-0 rounded-full items-center justify-center leading-tight relative border border-solid border-red-500 bg-white text-red-500"
            @click="changePage(props.total / props.perPage)"
          >
            {{ props.total / props.perPage }}
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

const totalPages = ref(null);
const hasPrev = ref(null);
const hasNext = ref(null);

const pages = () => {
  totalPages.value = Math.ceil(props.total / props.perPage);
  hasPrev.value = props.currentPage !== 1;
  hasNext.value = props.currentPage !== totalPages.value;

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
  if (page !== props.currentPage) {
    if (page < 1) {
      page = 1;
    } else if (page > totalPages.value) {
      page = totalPages.value;
    }

    hasNext.value = page !== totalPages.value;
    hasPrev.value = page !== 1;
    emit("changePage", page);
  }
}

const showFirstPageButton = computed(() => {
  return totalPages.value && totalPages.value > 5 && props.currentPage > 3;
});

const showLastPageButton = computed(() => {
  return totalPages.value !== null && totalPages.value > 5 && props.currentPage < totalPages.value - 2;
});

const showLastDots = computed(() => {
  return totalPages.value !== null && totalPages.value > 5 && props.currentPage < totalPages.value - 3;
});

</script>
