// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    postcss: {
        plugins: {
            tailwindcss: {},
            autoprefixer: {},
        },
    },
    css: ['~/assets/styles/index.css', '@fortawesome/fontawesome-free/css/all.min.css', '@/assets/styles/tailwind.css'],
    runtimeConfig: {
        // Keys within public, will be also exposed to the client-side
        public: {
            apiUrl: process.env.API_BASE_URL || 'https://pref.azurewebsites.net/pref/api',
        },
    },
});
