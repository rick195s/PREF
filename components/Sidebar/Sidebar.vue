<template>
  <nav
    class="md:left-0 md:block md:fixed md:top-0 md:bottom-0 md:overflow-y-auto md:flex-row md:flex-nowrap md:overflow-hidden shadow-xl bg-white no-scrollbar flex flex-wrap items-center justify-between relative md:w-64 z-10 py-4 px-6"
  >
    <div
      class="md:flex-col md:items-stretch md:min-h-full md:flex-nowrap px-0 flex flex-wrap items-center justify-between w-full mx-auto"
    >
      <!-- Toggler -->
      <button
        class="cursor-pointer text-black opacity-50 md:hidden px-3 py-1 text-xl leading-none bg-transparent rounded border border-solid border-transparent"
        type="button"
        @click="toggleCollapseShow('bg-white m-2 py-3 px-6')"
      >
        <i class="fas fa-bars"></i>
      </button>
      <!-- Brand -->
      <nuxt-link
        class="md:block text-left md:pb-2 text-blueGray-600 mr-0 inline-block whitespace-nowrap text-sm uppercase font-bold p-4 px-0"
        to="/"
      >
        Embalagens do Futuro
      </nuxt-link>

      <ul class="md:hidden items-center flex flex-wrap list-none">
        <li class="inline-block relative">
          <NotificationDropdown />
        </li>
        <li class="inline-block relative">
          <UserDropdown />
        </li>
      </ul>
      <!-- Collapse -->
      <div
        class="md:flex md:flex-col md:items-stretch md:opacity-100 md:relative md:mt-4 md:shadow-none shadow absolute top-0 left-0 right-0 z-40 overflow-y-auto overflow-x-hidden h-auto items-center flex-1 rounded"
        :class="collapseShow"
      >
        <!-- Collapse header -->
        <div
          class="md:min-w-full md:hidden block pb-4 mb-4 border-b border-solid border-blueGray-200"
        >
          <div class="flex flex-wrap">
            <div class="w-6/12">
              <nuxt-link
                class="md:block text-left md:pb-2 text-blueGray-600 mr-0 inline-block whitespace-nowrap text-sm uppercase font-bold p-4 px-0"
                to="/"
              >
                Vue Notus
              </nuxt-link>
            </div>
            <div class="w-6/12 flex justify-end">
              <button
                type="button"
                class="cursor-pointer text-black opacity-50 md:hidden px-3 py-1 text-xl leading-none bg-transparent rounded border border-solid border-transparent"
                @click="toggleCollapseShow('hidden')"
              >
                <i class="fas fa-times"></i>
              </button>
            </div>
          </div>
        </div>
        <!-- Form -->
        <form class="mt-6 mb-4 md:hidden">
          <div class="mb-3 pt-0">
            <input
              type="text"
              placeholder="Search"
              class="border-0 px-3 py-2 h-12 border border-solid border-blueGray-500 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-base leading-snug shadow-none outline-none focus:outline-none w-full font-normal"
            />
          </div>
        </form>

        <!-- Divider -->
        <hr class="my-4 md:min-w-full" />
        <!-- Heading -->
        <h6
          class="md:min-w-full text-blueGray-500 text-xs uppercase font-bold block pt-1 pb-4 no-underline"
        >
          Admin Layout Pages
        </h6>
        <!-- Navigation -->

        <ul class="md:flex-col md:min-w-full flex flex-col list-none">
          <li v-for="page in pages" :key="page.name" class="items-center">
            <nuxt-link
              v-slot="{ isActive }"
              :to="page.route"
              class="text-xs uppercase py-3 font-bold block"
            >
              <span
                :class="[
                  isActive
                    ? 'text-red-500 hover:text-red-600'
                    : 'text-blueGray-700 hover:text-blueGray-500'
                ]"
              >
                <i
                  class="mr-2 text-sm"
                  :class="[
                    isActive ? 'opacity-75' : 'text-blueGray-300',
                    page.icon
                  ]"
                ></i>
                {{ page.name }}
              </span>
            </nuxt-link>
          </li>
          <!--
          <li class="items-center">
            <nuxt-link
              v-slot="{ isActive }"
              to="/settings"
              class="text-xs uppercase py-3 font-bold block"
            >
              <span
                :class="[
                  isActive
                    ? 'text-red-500 hover:text-red-600'
                    : 'text-blueGray-700 hover:text-blueGray-500'
                ]"
              >
                <i
                  class="fas fa-tools mr-2 text-sm"
                  :class="[isActive ? 'opacity-75' : 'text-blueGray-300']"
                ></i>
                Settings
              </span>
            </nuxt-link>
          </li>
          -->
        </ul>

        <!-- Divider -->
        <hr class="my-4 md:min-w-full" />
        <!-- Heading -->
        <h6
          class="md:min-w-full text-blueGray-500 text-xs uppercase font-bold block pt-1 pb-4 no-underline"
        >
          Auth Layout Pages
        </h6>
        <!-- Navigation -->
        <!--
        <ul class="md:flex-col md:min-w-full flex flex-col list-none md:mb-4">
          <li class="items-center">
            <nuxt-link
              class="text-blueGray-700 hover:text-blueGray-500 text-xs uppercase py-3 font-bold block"
              to="/auth/login"
            >
              <i class="fas fa-fingerprint text-blueGray-300 mr-2 text-sm"></i>
              Login
            </nuxt-link>
          </li>

          <li class="items-center">
            <nuxt-link
              class="text-blueGray-700 hover:text-blueGray-500 text-xs uppercase py-3 font-bold block"
              to="/auth/register"
            >
              <i
                class="fas fa-clipboard-list text-blueGray-300 mr-2 text-sm"
              ></i>
              Register
            </nuxt-link>
          </li>
        </ul>
        -->
        <!-- Divider -->
        <hr class="my-4 md:min-w-full" />
        <!-- Heading -->
        <h6
          class="md:min-w-full text-blueGray-500 text-xs uppercase font-bold block pt-1 pb-4 no-underline"
        >
          Documentation
        </h6>
        <!-- Navigation -->
        <!--
        <ul class="md:flex-col md:min-w-full flex flex-col list-none md:mb-4">
          <li class="inline-flex">
            <a
              href="https://www.creative-tim.com/learning-lab/tailwind/vue/overview/notus"
              target="_blank"
              class="text-blueGray-700 hover:text-blueGray-500 text-sm block mb-4 no-underline font-semibold"
            >
              <i class="fab fa-vuejs mr-2 text-blueGray-300 text-base"></i>
              VueJS
            </a>
          </li>
        </ul>
        -->
      </div>
    </div>
  </nav>
</template>

<script setup>
import NotificationDropdown from "@/components/Dropdowns/NotificationDropdown.vue";
import UserDropdown from "@/components/Dropdowns/UserDropdown.vue";

const pages = ref([
  {
    name: "Dashboard",
    route: "/",
    icon: "fas fa-tv"
  },
  {
    name: "Create Order",
    route: "/orders/create",
    icon: "fas fa-tools"
  }
]);
const collapseShow = ref("hidden");

const toggleCollapseShow = (classes) => {
  collapseShow.value = classes;
};
</script>
