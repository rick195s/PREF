// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  postcss: {
    plugins: {
      tailwindcss: {},
      autoprefixer: {}
    }
  },
  modules: ["@nuxtjs-alt/proxy"],
  app: {
    head: {
      title: "PREF",
      link: [
        { rel: "stylesheet", href: "https://cdnjs.cloudflare.com/ajax/libs/flowbite/1.6.5/flowbite.min.css" }
      ],
      script: [{ src: "https://cdnjs.cloudflare.com/ajax/libs/flowbite/1.6.5/flowbite.min.js" }]
    }
  },
  proxy: {
    enableProxy: true,
    proxies: {
      "/api": {
        target: process.env.API_BASE_URL || "https://pref.azurewebsites.net/pref/api",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, "")
      }
    }
  },
  css: ["~/assets/styles/index.css", "@fortawesome/fontawesome-free/css/all.min.css", "@/assets/styles/tailwind.css"],
});
