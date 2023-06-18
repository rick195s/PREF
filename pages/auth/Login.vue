<template>
  <div class="container mx-auto px-4 h-full">
    <div class="flex content-center items-center justify-center h-full">
      <div class="w-full lg:w-4/12 px-4">
        <div
          class="relative flex flex-col min-w-0 break-words w-full mb-6 shadow-lg rounded-lg bg-blueGray-200 border-0"
        >
          <div class="flex-auto mt-6 px-6 py-6 px-4 lg:px-10 py-10 pt-0">
            <form>
              <div class="relative w-full mb-3">
                <label
                  class="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                  htmlFor="grid-password"
                >
                  Email
                </label>
                <input
                  v-model="formData.email"
                  type="email"
                  class="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                  placeholder="Email"
                />
              </div>

              <div class="relative w-full mb-3">
                <label
                  class="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                  htmlFor="grid-password"
                >
                  Password
                </label>
                <input
                  v-model="formData.password"
                  type="password"
                  class="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                  placeholder="Password"
                />
              </div>

              <div
                class="cursor-pointer flex flex-row justify-evenly px-6 py-3 ease-linear transition-all duration-150 text-center mt-6 bg-blueGray-800 text-white active:bg-blueGray-600 text-sm font-bold uppercase rounded shadow hover:shadow-lg outline-none focus:outline-none mr-1 mb-1 w-full"
                @click="login"
              >
                <button type="button">Sign In</button>
                <SpinnerComponent v-if="pending" />
              </div>
              <div>
                <p class="pt-3 text-center text-red-500 text-small italic">
                  {{ errorMsg }}
                </p>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import SpinnerComponent from "@/components/Spinner/SpinnerComponent.vue";
const { signIn } = useAuth();
const formData = ref({
  email: "manager@gmail.com",
  password: "123"
});
const errorMsg = ref("");
const pending = ref(false);

const login = async () => {
  if (pending.value) {
    return;
  }
  errorMsg.value = "";
  try {
    pending.value = true;
    await signIn(
      { email: formData.value.email, password: formData.value.password },
      { redirect: false }
    );
    pending.value = false;
    await navigateTo("/");
  } catch (error) {
    pending.value = false;
    errorMsg.value = "Invalid email or password";
  }
};

definePageMeta({
  auth: {
    unauthenticatedOnly: true
  },
  layout: "auth"
});
</script>
