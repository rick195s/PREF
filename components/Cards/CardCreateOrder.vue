<template>
  <div
    class="relative flex flex-col min-w-0 break-words w-full mb-6 shadow-lg rounded-lg bg-blueGray-100 border-0"
  >
    <div class="rounded-t bg-white mb-0 px-6 py-6">
      <div class="text-center flex justify-between">
        <h6 class="text-blueGray-700 text-xl font-bold">Create Order</h6>
        <SpinnerComponent v-if="props.loading"></SpinnerComponent>
        <button
          v-else
          class="bg-red-500 text-white active:bg-red-600 font-bold uppercase text-xs px-4 py-2 rounded shadow hover:shadow-md outline-none focus:outline-none mr-1 ease-linear transition-all duration-150"
          type="button"
          @click="createOrder()"
        >
          Create Order
        </button>
      </div>
    </div>
    <div class="flex-auto px-4 lg:px-10 py-10 pt-0">
      <form>
        <h6 class="text-blueGray-400 text-sm mt-3 mb-6 font-bold uppercase">
          Order Information
        </h6>

        <div class="flex flex-wrap">
          <FormField v-model="formData.source" label="Source"></FormField>
          <FormField
            v-model="formData.destination"
            label="Destination"
          ></FormField>
          <FormField v-model="formData.carrier" label="Carrier"></FormField>
          <FormField
            v-model="formData.shippingMethods"
            label="Shipping Methods"
          ></FormField>
        </div>
      </form>
    </div>
  </div>
</template>
<script setup>
import FormField from "@/components/Forms/FormField.vue";
import SpinnerComponent from "@/components/Spinner/SpinnerComponent.vue";

const props = defineProps({
  loading: {
    type: Boolean,
    default: false
  }
});

const emit = defineEmits(["createOrder"]);

const formData = ref({
  source: "",
  destination: "",
  carrier: "",
  shippingMethods: ""
});

const createOrder = () => {
  emit("createOrder", formData.value);
};
</script>
