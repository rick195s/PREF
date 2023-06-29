<template>
  <div>
    <div class="flex flex-wrap">
      <div v-if="data?.role.toUpperCase()==='LOGISTICS_OPERATOR'" class="w-full px-4">
        <CardBarChart :selected-carrier="selectedCarrier" />
      </div>
    </div>

    <div>
      <div v-if="data?.role.toUpperCase()==='LOGISTICS_OPERATOR'" class="w-full mb-12 xl:mb-0">
        <CardOrders
          @selected-carrier="($event) => (selectedCarrier = $event)"
        />
      </div>
    </div>

    <div class="flex flex-wrap">
      <div v-for="card in cards" :key="card.title" class="card">
        <CardGlobalCard :labels="card" />
      </div>
    </div>


  </div>
</template>
<script setup>
import CardBarChart from "@/components/Charts/CardBarChart.vue";
import CardOrders from "@/components/Cards/CardOrders.vue";
import CardGlobalCard from "@/components/Cards/CardGlobalCard.vue";

const { data } = useAuth();

const selectedCarrier = ref(null);

const { data: cards, pending } = await useLazyAsyncData(
    "cards",
  () =>
    $fetch(`/api/statistics/${data?.value.role.toUpperCase()}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json"
      }
    }),
  {
    server: false
  }
);

console.log("CARDS", cards);

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
