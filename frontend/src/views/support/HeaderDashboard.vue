<template>
    <!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Kênh Bán Hàng</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<header class="header-area overlay">
<nav class="navbar navbar-expand-md navbar-dark">
<div class="container">
<a href="#" class="navbar-brand"><img class="header__logo-img" src="/img/logo.41d046f6.png" data-v-e23121f2=""></a>
<button type="button" class="navbar-toggler collapsed" data-toggle="collapse" data-target="#main-nav">
<span class="menu-icon-bar"></span>
<span class="menu-icon-bar"></span>
<span class="menu-icon-bar"></span>
</button>
<div id="main-nav" class="collapse navbar-collapse">
<ul class="navbar-nav ml-auto">
<li><a href="/dashboard" class="nav-item nav-link">Hiệu Quả</a></li>
<li class="dropdown">
<a href="#" class="nav-item nav-link" data-toggle="dropdown">Sản Phẩm</a>
<div class="dropdown-menu">
<a href="/seller/product/all" class="dropdown-item">Quản Lý Sản Phẩm</a>
<a href="/seller/add/product" class="dropdown-item">Thêm Sản Phẩm</a>
</div>
</li>

<li class="dropdown">
<a href="#" class="nav-item nav-link" data-toggle="dropdown">Đơn Hàng</a>
<div class="dropdown-menu">
<a href="/seller/order/all" class="dropdown-item">Tất Cả</a>
<a href="/seller/order/pending" class="dropdown-item">Đơn Mới</a>
<a href="/seller/order/shipping" class="dropdown-item">Đang Giao</a>
<a href="/seller/order/return" class="dropdown-item">Đơn Hoàn Trả</a>
</div>
</li>
<li><a href="#" class="nav-item nav-link">Số Dư :{{account.coint}}</a></li>
</ul>
</div>
</div>
</nav>
<div class="banner">

</div>
</header>
</body>
</html>

</template>


<script>
import { ref, onMounted } from 'vue';
import HeaderDashboard from '@/views/support/HeaderDashboard.vue';
import Footer from '@/views/support/Footer.vue';
import { mapGetters } from 'vuex';
import { baseURL } from '@/router/index';
import { toast } from 'vue3-toastify';
import axios from 'axios';
import 'vue3-toastify/dist/index.css';
import { AccountService } from '@/core/service/accountservice';

export default {
  name: 'HeaderApp',
  setup() {
      const scrollTop = ref(0);
      const adjustNav = () => {
          const winWidth = window.innerWidth;
          const dropdown = document.querySelectorAll('.dropdown');
          const dropdownMenu = document.querySelectorAll('.dropdown-menu');

          if (winWidth >= 768) {
              dropdown.forEach(item => {
                  item.addEventListener('mouseenter', () => {
                      item.classList.add('show');
                      const menu = item.querySelector('.dropdown-menu');
                      if (menu) menu.classList.add('show');
                  });
                  item.addEventListener('mouseleave', () => {
                      item.classList.remove('show');
                      const menu = item.querySelector('.dropdown-menu');
                      if (menu) menu.classList.remove('show');
                  });
              });
          } else {
              dropdown.forEach(item => {
                  item.removeEventListener('mouseenter', () => {});
                  item.removeEventListener('mouseleave', () => {});
              });
          }
      };

      onMounted(() => {
          window.addEventListener('resize', adjustNav);
          adjustNav();
      });

      window.addEventListener('scroll', () => {
          scrollTop.value = window.scrollY;
      });

      return { scrollTop };
  },
  data(){
    return {
      service: new AccountService(),
     token : localStorage.getItem('token'),
     loggedInUser : localStorage.getItem('loggedInUser'),
     account : {},
    }
  },
  methods:{
    getAcc(){
        this.service.getAccountLogin(this.token).then(res => {
            this.account = res;
        })
    },
  },

  created(){
    this.getAcc()
  },
  mounted() {

  }
}
</script>

<style type="text/css">
@import url('https://fonts.googleapis.com/css?family=Open+Sans:400,700,800');
@import url('https://fonts.googleapis.com/css?family=Lobster');
html {
font-size: 62.5%;
}
body {
font-family: 'Open Sans', sans-serif;
font-size: 1.6rem;
font-weight: 400;
}
h1 {
margin-bottom: 0.5em;
font-size: 3.6rem;
}
p {
margin-bottom: 0.5em;
font-size: 1.6rem;
line-height: 1.6;
}
.button {
display: inline-block;
margin-top: 20px;
padding: 8px 25px;
border-radius: 4px;
}
.button-primary {
position: relative;
background-color: #c0ca33;
color: #fff;
font-size: 1.8rem;
font-weight: 700;
transition: color 0.3s ease-in;
z-index: 1;
}
.button-primary:hover {
color: #c0ca33;
text-decoration: none;
}
.button-primary::after {
content: '';
position: absolute;
bottom: 0;
left: 0;
right: 0;
top: 0;
background-color: #fff;
border-radius: 4px;
opacity: 0;
-webkit-transform: scaleX(0.8);
-ms-transform: scaleX(0.8);
transform: scaleX(0.8);
transition: all 0.3s ease-in;
z-index: -1;
}
.button-primary:hover::after {
opacity: 1;
-webkit-transform: scaleX(1);
-ms-transform: scaleX(1);
transform: scaleX(1);
}
.overlay::after {
content: '';
position: absolute;
bottom: 0;
left: 0;
right: 0;
top: 0;
background-color: rgba(0, 0, 0, .3);
}
.header-area {
position: fixed;
z-index: 1000;
width: 100%;
background-image: linear-gradient(0, #610404, #b11e0a);
background-attachment: fixed;
background-position: center center;
background-repeat: no-repear;
background-size: cover;
}
.banner {
display: flex;
align-items: center;
position: relative;
height: 100%;
color: #fff;
text-align: center;
z-index: 1;
}
.banner h1 {
font-weight: 800;
}
.banner p {
font-weight: 700;
}
.navbar {
position: absolute;
left: 0;
top: 0;
padding: 0;
width: 100%;
transition: background 0.6s ease-in;
z-index: 99999;
}
.navbar .navbar-brand {
font-family: 'Lobster', cursive;
font-size: 2.5rem;
}
.navbar .navbar-toggler {
position: relative;
height: 50px;
width: 50px;
border: none;
cursor: pointer;
outline: none;
}
.navbar .navbar-toggler .menu-icon-bar {
position: absolute;
left: 15px;
right: 15px;
height: 2px;
background-color: #fff;
opacity: 0;
-webkit-transform: translateY(-1px);
-ms-transform: translateY(-1px);
transform: translateY(-1px);
transition: all 0.3s ease-in;
}
.navbar .navbar-toggler .menu-icon-bar:first-child {
opacity: 1;
-webkit-transform: translateY(-1px) rotate(45deg);
-ms-sform: translateY(-1px) rotate(45deg);
transform: translateY(-1px) rotate(45deg);
}
.navbar .navbar-toggler .menu-icon-bar:last-child {
opacity: 1;
-webkit-transform: translateY(-1px) rotate(135deg);
-ms-sform: translateY(-1px) rotate(135deg);
transform: translateY(-1px) rotate(135deg);
}
.navbar .navbar-toggler.collapsed .menu-icon-bar {
opacity: 1;
}
.navbar .navbar-toggler.collapsed .menu-icon-bar:first-child {
-webkit-transform: translateY(-7px) rotate(0);
-ms-sform: translateY(-7px) rotate(0);
transform: translateY(-7px) rotate(0);
}
.navbar .navbar-toggler.collapsed .menu-icon-bar:last-child {
-webkit-transform: translateY(5px) rotate(0);
-ms-sform: translateY(5px) rotate(0);
transform: translateY(5px) rotate(0);
}
.navbar-dark .navbar-nav .nav-link {
position: relative;
color: #fff;
font-size: 1.6rem;
}
.navbar-dark .navbar-nav .nav-link:focus, .navbar-dark .navbar-nav .nav-link:hover {
color: #fff;
}
.navbar .dropdown-menu {
padding: 0;
background-color: rgba(0, 0, 0, .9);
}
.navbar .dropdown-menu .dropdown-item {
position: relative;
padding: 10px 20px;
color: #fff;
font-size: 1.4rem;
border-bottom: 1px solid rgba(255, 255, 255, .1);
transition: color 0.2s ease-in;
}
.navbar .dropdown-menu .dropdown-item:last-child {
border-bottom: none;
}
.navbar .dropdown-menu .dropdown-item:hover {
background: transparent;
color: #c0ca33;
}
.navbar .dropdown-menu .dropdown-item::before {
content: '';
position: absolute;
bottom: 0;
left: 0;
top: 0;
width: 5px;
background-color: #c0ca33;
opacity: 0;
transition: opacity 0.2s ease-in;
}
.navbar .dropdown-menu .dropdown-item:hover::before {
opacity: 1;
}
.navbar.fixed-top {
position: fixed;
-webkit-animation: navbar-animation 0.6s;
animation: navbar-animation 0.6s;
background-color: rgba(0, 0, 0, .9);
}
.navbar.fixed-top.navbar-dark .navbar-nav .nav-link.active {
color: #c0ca33;
}

.navbar.fixed-top.navbar-dark .navbar-nav .nav-link::after {
background-color: #c0ca33;
}
.content {
padding: 120px 0;
}
@media screen and (max-width: 768px) {
.navbar-brand {
margin-left: 20px;
}
.navbar-nav {
padding: 0 20px;
background-color: rgba(0, 0, 0, .9);
}
.navbar.fixed-top .navbar-nav {
background: transparent;
}
}
@media screen and (min-width: 767px) {
.banner {
padding: 0 150px;
}
.banner h1 {
font-size: 5rem;
}
.banner p {
font-size: 2rem;
}
.navbar-dark .navbar-nav .nav-link {
padding: 23px 15px;
}
.navbar-dark .navbar-nav .nav-link::after {
content: '';
position: absolute;
bottom: 15px;
left: 30%;
right: 30%;
height: 1px;
background-color: #fff;
-webkit-transform: scaleX(0);
-ms-transform: scaleX(0);
transform: scaleX(0);
transition: transform 0.1s ease-in;
}
.navbar-dark .navbar-nav .nav-link:hover::after {
-webkit-transform: scaleX(1);
-ms-transform: scaleX(1);
transform: scaleX(1);
}
.dropdown-menu {
min-width: 200px;
-webkit-animation: dropdown-animation 0.3s;
animation: dropdown-animation 0.3s;
-webkit-transform-origin: top;
-ms-transform-origin: top;
transform-origin: top;
}
}
@-webkit-keyframes navbar-animation {
0% {
opacity: 0;
-webkit-transform: translateY(-100%);
-ms-transform: translateY(-100%);
transform: translateY(-100%);
}
100% {
opacity: 1;
-webkit-transform: translateY(0);
-ms-transform: translateY(0);
transform: translateY(0);
}
}
@keyframes navbar-animation {
0% {
opacity: 0;
-webkit-transform: translateY(-100%);
-ms-transform: translateY(-100%);
transform: translateY(-100%);
}
100% {
opacity: 1;
-webkit-transform: translateY(0);
-ms-transform: translateY(0);
transform: translateY(0);
}
}
@-webkit-keyframes dropdown-animation {
0% {
-webkit-transform: scaleY(0);
-ms-transform: scaleY(0);
transform: scaleY(0);
}
75% {
-webkit-transform: scaleY(1.1);
-ms-transform: scaleY(1.1);
transform: scaleY(1.1);
}
100% {
-webkit-transform: scaleY(1);
-ms-transform: scaleY(1);
transform: scaleY(1);
}
}
@keyframes dropdown-animation {
0% {
-webkit-transform: scaleY(0);
-ms-transform: scaleY(0);
transform: scaleY(0);
}
75% {
-webkit-transform: scaleY(1.1);
-ms-transform: scaleY(1.1);
transform: scaleY(1.1);
}
100% {
-webkit-transform: scaleY(1);
-ms-transform: scaleY(1);
transform: scaleY(1);
}
}

</style>
