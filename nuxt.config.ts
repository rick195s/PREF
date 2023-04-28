// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    postcss: {
        plugins: {
            tailwindcss: {},
            autoprefixer: {},
        },
    },
    vite: {
        server: {
            
            proxy: {
                '/api': {
                    target: process.env.API_BASE_URL || 'https://pref.azurewebsites.net/pref/api',
                    changeOrigin: true,
                    rewrite: (path) => path.replace(/^\/api/, ''),
                },
            },
        },
    },
 
    css: ['~/assets/styles/index.css', '@fortawesome/fontawesome-free/css/all.min.css', '@/assets/styles/tailwind.css'],
});
