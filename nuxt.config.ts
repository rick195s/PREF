// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    postcss: {
        plugins: {
            tailwindcss: {},
            autoprefixer: {},
        },
    },
    build: {
        transpile: ['jsonwebtoken'],
    },
    modules: ['@nuxtjs-alt/proxy', '@sidebase/nuxt-auth'],
    app: {
        head: {
            title: 'PREF',
            link: [
                { rel: 'stylesheet', href: 'https://cdnjs.cloudflare.com/ajax/libs/flowbite/1.6.5/flowbite.min.css' },
            ],
            script: [{ src: 'https://cdnjs.cloudflare.com/ajax/libs/flowbite/1.6.5/flowbite.min.js' }],
        },
    },
    auth: {
        provider: {
            type: 'local',
            pages: {
                login: '/auth/login',
            },
            endpoints: {
                signIn: { path: '/login', method: 'post' },
                signOut: { path: '/logout', method: 'post' },
                signUp: { path: '/register', method: 'post' },
                getSession: { path: '/user', method: 'get' },
            },
        },
        session: {
          enableRefreshPeriodically: false,
          enableRefreshOnWindowFocus: false
        },
        globalAppMiddleware: {
          isEnabled: true,
          addDefaultCallbackUrl: false
        }

    },
    proxy: {
        enableProxy: true,
        proxies: {
            '/api': {
                target: process.env.API_BASE_URL || 'https://pref.azurewebsites.net/pref/api',
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api/, ''),
            },
        },
    },
    css: ['~/assets/styles/index.css', '@fortawesome/fontawesome-free/css/all.min.css', '@/assets/styles/tailwind.css'],
});
