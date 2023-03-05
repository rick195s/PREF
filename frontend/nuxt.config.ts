// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    
    postcss: {
        plugins: {
            tailwindcss: {},
            autoprefixer: {},
        },
    },
    css: [
        '~/assets/styles/index.css',
        "@fortawesome/fontawesome-free/css/all.min.css",
        "@/assets/styles/tailwind.css"
    ],
});
