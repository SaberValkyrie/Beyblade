<template>
    <app-header></app-header>
<div class="container mt-5 pt-4">
    <div class="row align-items-end mb-4 pb-2">
    </div><!--end row-->
    <div class="row">
        <div class="col-lg-4 col-md-6 col-12 mt-4 pt-2" v-for="voucher in Ruong">
            <div class="card border-0 bg-light rounded shadow">
                <div class="card-body p-4">
                    <br>
                    <img :class="voucher.prize.id == 5 ? 'boss img' : 'img'" :src="voucher.prize.img">

                    <h5 style="font-weight:bold">{{ voucher.prize.name }}</h5>
                    <span class="badge rounded-pill bg-primary float-md-end mb-3 mb-sm-0">x{{voucher.soluong }}</span>

                    <div class="mt-3">
                        <span class="text-muted d-block"><i class="fa fa-home" aria-hidden="true">

                        </i> <a href="#" target="_blank" class="text-success">{{voucher.prize.type == 1 ? 'Quà sẽ trao vào cuối sự kiện' : 'Sử dụng ngay để nhận quà' }}
                        </a></span>
                        <span class="text-muted d-block"><i class="fa fa-map-marker" aria-hidden="true"></i></span>
                    </div>
                    
                    <div class="mt-3">
                        <a @click="useItem(voucher)" class="btn btn-primary">Dùng Ngay</a>
                    </div>
                </div>
            </div>
        </div><!--end col-->


        <div class="col-lg-4 col-md-6 col-12 mt-4 pt-2" v-for="voucher in Vouchers">
            <div class="card border-0 bg-light rounded shadow">
                <div class="card-body p-4">
                    <br>
                    <img class="img" :src="voucher.prize.img">

                    <h5 style="font-weight:bold">{{ voucher.prize.name }}</h5>
                    <span class="badge rounded-pill bg-primary float-md-end mb-3 mb-sm-0">x{{voucher.soluong }}</span>

                    <div class="mt-3">
                        <span class="text-muted d-block"><i class="fa fa-home" aria-hidden="true">

                        </i> <a href="#" target="_blank" class="text-success">{{voucher.prize.type == 1 ? 'Quà sẽ trao vào cuối sự kiện' : 'Sử dụng ngay nào!!!' }}
                        </a></span>
                        <span class="text-muted d-block"><i class="fa fa-map-marker" aria-hidden="true"></i></span>
                    </div>
                    
                    <div class="mt-3">
                        <a @click="useItem(voucher)" class="btn btn-primary">Dùng Ngay</a>
                    </div>
                </div>
            </div>
        </div><!--end col-->



    </div><!--end row-->
</div>
</template>

<script>
import Header from '@/views/support/Header.vue';
import Footer from '@/views/support/Footer.vue';
import axios from 'axios';
import { baseWeb, baseURL } from '@/router/index';
import { mapGetters } from 'vuex';
import moment from 'moment';
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';
import { AccountService } from '@/core/service/accountservice';

export default {
  name: 'Profile',
  computed: {
    ...mapGetters(['loggedInUser']),
  },
  created() {
    this.getListVoucher();
  },
  data() {
    return {
      baseUrl: baseURL,
      userInfo: {},
      defaultA: {}, //mac dinh
      basicA: {}, //thuong
      service: new AccountService(),
      token: localStorage.getItem('token'),
      Vouchers: [],
      Ruong: [],
      status: 1,
    };
  },
  methods: {
    useItem(item){
        this.service.useItem(this.token, item).then(response => {
            toast.success(response.data.message);
            this.getListVoucher()
      }).catch(error => {
        toast.error(error.response.data.message);
      });
    },
    
    getListVoucher() {
      this.service.getAllVoucher(this.token, 1).then(response => {
        this.Vouchers = response.data.data;
      }).catch(error => {
        toast.error(error.response.data.message);
        this.Vouchers = [];
      });
      this.service.getAllVoucher(this.token, 2).then(response => {
        this.Ruong = response.data.data;
      }).catch(error => {
        toast.error(error.response.data.message);
        this.Ruong = [];
      });
    },

    formatCreatedAt(timestamp) {
      return moment(timestamp).format('DD/MM/YYYY HH:mm'); // Định dạng ngày tháng giờ phút theo ý muốn
    },
  },
  components: {
    'app-header': Header,
    'app-footer': Footer,
  },
}
</script>
<style scoped>
esult that you can see in the preview selection

body{margin-top:20px;}
.shadow {
    box-shadow: 0 0 3px rgb(53 64 78 / 20%) !important;
}
.rounded {
    border-radius: 5px !important;
}
.bg-light {
    zoom: 150%;
    background-color: #f7f8fa !important;
}
.bg-primary, .btn-primary, .btn-outline-primary:hover, .btn-outline-primary:focus, .btn-outline-primary:active, .btn-outline-primary.active, .btn-outline-primary.focus, .btn-outline-primary:not(:disabled):not(.disabled):active, .badge-primary, .nav-pills .nav-link.active, .pagination .active a, .custom-control-input:checked ~ .custom-control-label:before, #preloader #status .spinner > div, .social-icon li a:hover, .back-to-top:hover, .back-to-home a, ::selection, #topnav .navbar-toggle.open span:hover, .owl-theme .owl-dots .owl-dot.active span, .owl-theme .owl-dots.clickable .owl-dot:hover span, .watch-video a .play-icon-circle, .sidebar .widget .tagcloud > a:hover, .flatpickr-day.selected, .flatpickr-day.selected:hover, .tns-nav button.tns-nav-active, .form-check-input.form-check-input:checked {
    background-color: #6dc77a !important;
}

.badge {
    padding: 5px 8px;
    border-radius: 3px;
    letter-spacing: 0.5px;
    font-size: 12px;
}

.btn-primary, .btn-outline-primary:hover, .btn-outline-primary:focus, .btn-outline-primary:active, .btn-outline-primary.active, .btn-outline-primary.focus, .btn-outline-primary:not(:disabled):not(.disabled):active {
    box-shadow: 0 3px 7px rgba(110, 0, 0, 0.5) !important;
}
.btn-primary, .btn-outline-primary, .btn-outline-primary:hover, .btn-outline-primary:focus, .btn-outline-primary:active, .btn-outline-primary.active, .btn-outline-primary.focus, .btn-outline-primary:not(:disabled):not(.disabled):active, .bg-soft-primary .border, .alert-primary, .alert-outline-primary, .badge-outline-primary, .nav-pills .nav-link.active, .pagination .active a, .form-group .form-control:focus, .form-group .form-control.active, .custom-control-input:checked ~ .custom-control-label:before, .custom-control-input:focus ~ .custom-control-label::before, .form-control:focus, .social-icon li a:hover, #topnav .has-submenu.active.active .menu-arrow, #topnav.scroll .navigation-menu > li:hover > .menu-arrow, #topnav.scroll .navigation-menu > li.active > .menu-arrow, #topnav .navigation-menu > li:hover > .menu-arrow, .flatpickr-day.selected, .flatpickr-day.selected:hover, .form-check-input:focus, .form-check-input.form-check-input:checked, .container-filter li.active, .container-filter li:hover {
    border-color: #471700 !important;
}
.bg-primary, .btn-primary, .btn-outline-primary:hover, .btn-outline-primary:focus, .btn-outline-primary:active, .btn-outline-primary.active, .btn-outline-primary.focus, .btn-outline-primary:not(:disabled):not(.disabled):active, .badge-primary, .nav-pills .nav-link.active, .pagination .active a, .custom-control-input:checked ~ .custom-control-label:before, #preloader #status .spinner > div, .social-icon li a:hover, .back-to-top:hover, .back-to-home a, ::selection, #topnav .navbar-toggle.open span:hover, .owl-theme .owl-dots .owl-dot.active span, .owl-theme .owl-dots.clickable .owl-dot:hover span, .watch-video a .play-icon-circle, .sidebar .widget .tagcloud > a:hover, .flatpickr-day.selected, .flatpickr-day.selected:hover, .tns-nav button.tns-nav-active, .form-check-input.form-check-input:checked {
    background-color: #8b0000 !important;
}
.p-4 {
    padding: 0.5rem !important;
}
.btn {
    padding: 8px 20px;
    outline: none;
    text-decoration: none;
    font-size: 16px;
    letter-spacing: 0.5px;
    transition: all 0.3s;
    font-weight: 600;
    border-radius: 5px;
}
.btn-primary {
    background-color: #910000 !important;
    border: 1px solid #771800 !important;
    color: #fff !important;
    box-shadow: 0 3px 7px rgba(78, 0, 0, 0.5);
}

a {
text-decoration:none;    
}
img.img {
    width: 10vw;
    height: 10vw;
    border-radius:50%;

}
@keyframes glowing-border {
  1% {
    border-color: #ac2a02;
    box-shadow: 0 0 5px #8f1100;
  }
  50% {
    border-color: #3400c4;
    box-shadow: 0 0 20px #242c02;
  }
  100% {
    border-color: #6b6035;
    box-shadow: 0 0 5px #31018b;
  }
}
.boss {
    padding: 0vw;
    border: 3px solid;
    /* border-radius: 50%; */
    animation: glowing-border 3s infinite;
}

</style>






