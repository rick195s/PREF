// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    postcss: {
        plugins: {
            tailwindcss: {},
            autoprefixer: {},
        },
    },
    nitro:{
        devProxy: {
            '/api': {
                target: process.env.API_BASE_URL || 'https://pref.azurewebsites.net/pref/api',
                changeOrigin: true,
            },
        },
    },
    css: ['~/assets/styles/index.css', '@fortawesome/fontawesome-free/css/all.min.css', '@/assets/styles/tailwind.css'],
});
