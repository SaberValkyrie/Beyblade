<template>
<app-header></app-header>
<app-1></app-1>
<br><br><br>
<app-footer></app-footer>

</template>

<script>
import Header from '@/views/support/Header.vue';
import Footer from '@/views/support/Footer.vue';
import App1 from '@/views/game/diemdanh/1.vue';
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';
import { baseURL } from '@/router/index';
import axios from 'axios';
import { GameService } from '@/core/service/game';
import Chart from 'chart.js/auto';
import moment from 'moment';

export default {
components: {
'app-header': Header,
'app-footer': Footer,
'app-1': App1,
},

data() {

}
}
</script>