<template>
  <div>
    <div class="flex flex-wrap">
      <div
        v-if="data?.role.toUpperCase() === 'LOGISTICS_OPERATOR'"
        class="w-full px-4"
      >
        <CardBarChart :selected-carrier="selectedCarrier" />
      </div>
    </div>

    <div>
      <div
        v-if="data?.role.toUpperCase() === 'LOGISTICS_OPERATOR'"
        class="w-full mb-12 xl:mb-0"
      >
        <CardOrders
          @selected-carrier="($event) => (selectedCarrier = $event)"
        />
      </div>
    </div>

    <div class="flex flex-wrap">
      <div
        v-for="card in cards"
        :key="card.title"
        class="w-full lg:w-6/12 xl:w-4/12 px-4 py-5"
      >
        <CardStats
          :stat-subtitle="card.title"
          :stat-title="card.value"
          :stat-descripiron="card.description"
          :chart-datasets="card.chartDatasets"
          :stat-icon-color="card.danger ? 'text-white' : 'text-black'"
          :stat-icon-background="card.danger ? 'bg-red-500' : 'bg-white'"
          :loading="pending"
        />
      </div>
    </div>
  </div>
</template>
<script setup>
import CardBarChart from "@/components/Charts/CardBarChart.vue";
import CardOrders from "@/components/Cards/CardOrders.vue";
import CardStats from "@/components/Cards/CardStats.vue";

const { data, token } = useAuth();

const selectedCarrier = ref(null);

const { data: cards, pending } = await useLazyAsyncData(
  "cards",
  () =>
    $fetch(`/api/statistics/`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: token.value
      }
    }),
  {
    server: false,
    transform: (data) => {
      data.forEach((element) => {
        if (parseFloat(element.value) > 15) {
          element.danger = true;
        }
        if (element.title.toUpperCase().includes("(LAST 5 DAYS)")) {
          element.description = "Last 5 Days";
          element.title = element.title
            .toUpperCase()
            .replace("(LAST 5 DAYS)", "");
        }
      });

      console.log(data);
      return data;
    }
  }
);
</script>

<style scoped>
.card {
  width: 30%;
  padding: 20px;
  background-color: #ffffff;
  border-radius: 5px;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  margin-right: 10px;
}
</style>
