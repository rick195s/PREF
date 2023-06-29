<template>
    <CardGlobalCard :title="title" :value="value"/>
</template>

<script setup>
import CardGlobalCard from "@/components/Cards/CardGlobalCard.vue";

const title = ref("Total Orders On Going");
const value = ref(20);


</script>