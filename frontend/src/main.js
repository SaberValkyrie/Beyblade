import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios' // Import axios
import '@/assets/css/base.css';
import '@/assets/css/main.css';
import '@/assets/css/grid.css';
import '@/views/template/css/bootstrap.min.css';
import '@/views/template/style.css';
import '@/assets/css/responsive.css';
import store from './store'; 


import 'vue3-toastify/dist/index.css'
import Vue3Toast, { toast } from 'vue3-toastify';

const options = {
    position: 'top-center',
    timeout: 1000,
    closeOnClick: true,
    pauseOnFocusLoss: true,
    pauseOnHover: true,
    draggable: true,
    draggablePercent: 0.6,
    showCloseButtonOnHover: false,
    hideProgressBar: false,
    // closeButton: 'button',
    icon: true,
    rtl: false,
};

const app = createApp(App)


// Sử dụng axios trong toàn bộ ứng dụng Vue
app.config.globalProperties.$http = axios

// Sử dụng store Vuex trong ứng dụng Vue
app.use(store);

// Sử dụng Vue Router trong ứng dụng Vue
app.use(router);

// Sử dụng Vue3Toastify trong ứng dụng Vue
app.use(Vue3Toast, options);

// Đăng ký biến toast để sử dụng toàn cục
app.config.globalProperties.$toast = toast;

app.mount('#app');

