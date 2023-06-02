<template>
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
    <div
      class="grid lg:grid-cols-4 gap-4 px-4 py-3 text-blueGray-500 align-middle border border-solid border-blueGray-100 bg-slate-100"
    >
      <CardProduct
        v-for="product in groupedProductsWithDuplicates"
        :key="product"
        :product="product"
        @product-package-selected="updateSelectedProductPackages"
        class="mb-4"
      />
    </div>
  </div>
</template>

<script setup>
import CardProduct from "@/components/Cards/CardProduct.vue";

const emit = defineEmits(["product-package-selected"]);

const props = defineProps({
  orderLines: {
    type: Array,
    required: true,
    default: () => []
  }
});

const products = computed(() => {
  return props.orderLines.flatMap((orderLine) =>
    orderLine.orderLineProductRelation.flatMap((item) => {
      item.product.orderLineProductPackages?.forEach((orderLinePackage) => {
        const packageType = item.product.productPackageTypes.find(
          (packageType) =>
            packageType.id === orderLinePackage.simplePackageTypeId
        );
        if (packageType) {
          orderLinePackage.packageName = packageType.name;
          orderLinePackage.isSmart = packageType.smart;
        }
      });
      return item.product;
    })
  );
});

const groupedProductsWithDuplicates = computed(() => {
  const groupedProductsMap = new Map();
  products.value.forEach((product) => {
    if (!groupedProductsMap.has(product.name)) {
      groupedProductsMap.set(product.name, []);
    }
    groupedProductsMap.get(product.name).push(product);
  });

  const groupedProductsArray = Array.from(
    groupedProductsMap,
    ([name, products]) => ({ name, products })
  );

  return groupedProductsArray;
});

const updateSelectedProductPackages = (payload) => {
  emit("product-package-selected", payload);
};
</script>
