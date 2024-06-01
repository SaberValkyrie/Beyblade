<template>
  <!DOCTYPE html>
  <html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Beyblade Burst</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" rel="stylesheet">
  </head>
  <body>

  <header class="header-area overlay">
    <nav class="navbar navbar-expand-md navbar-dark">
      <div class="container">
        <a href="#" class="navbar-brand">
          <div class="header__logo img">
            <a href="/" class="header__logo-link">
              <img class="header__logo-img" :src="baseUrl + '/files/logo.png'">
            </a>
        

          </div>
      
        </a>
        <button type="button" class="navbar-toggler collapsed" data-toggle="collapse" data-target="#main-nav">
          <span class="menu-icon-bar"></span>
          <span class="menu-icon-bar"></span>
          <span class="menu-icon-bar"></span>
        </button>
        <div id="main-nav" class="collapse navbar-collapse">
          <ul class="navbar-nav ml-auto">
            <li><a href="/shop" class="nav-item nav-link">Cửa Hàng</a></li>
            <li class="dropdown" v-if="loggedInUser">

              <a  class="nav-item nav-link" data-toggle="dropdown">Kho Đồ</a>
              <div class="dropdown-menu">
                <a href="/user/bag" class="dropdown-item">Beyblade của tôi</a>
                <a   href="/voucher" class="dropdown-item">Voucher của tôi</a>
              </div>
            </li>
            <li class="dropdown" v-if="loggedInUser">
              <a  class="nav-item nav-link" data-toggle="dropdown">Ví Của Tôi</a>
              <div class="dropdown-menu">
                <a class="dropdown-item">Số Dư Nạp:{{account.tienmat}}</a>
                <a class="dropdown-item">BeyPoint :{{account.coint}}</a>
                <a href="/naptien" class="dropdown-item">Nạp Tiền</a>
 </div>
            </li>
            <li class="dropdown" v-if="loggedInUser">
              <a  class="nav-item nav-link" data-toggle="dropdown">Tài Khoản</a>
              <div class="dropdown-menu">
                <a href="/profile" class="dropdown-item">Thông Tin Cá Nhân</a>
                <a  @click="logout()" class="dropdown-item">Đăng Xuất</a>
              </div>
            </li>
          
            <li v-else 
            ><a href="/login" class="nav-item nav-link">Đăng Nhập</a></li>
                    </ul>
        </div>
      </div>
    </nav>
    
  </header>

  <br><br><br><br><br>
  
  <div class="notify" v-if="isShow" @click="acp">
      
      <div class="tb">
  <span class="header__cart-notice">X</span>
  <h1 class="notification-title">Thông Báo</h1>
  <h1 class="text-danger">HÃY BẬT MÀN HÌNH NGANG ĐỂ CHƠI GAME !!!!!</h1>
  <p class="notification-content">

  Sự kiện đua top sẽ hết hạn vào 25/6/2024 !
  </p>
</div>

</div>
  </body>
  </html>
</template>

<script>
import { ref, onMounted } from 'vue';
import { baseURL } from '@/router/index';
import { AccountService } from '@/core/service/accountservice';
import axios from 'axios';
import moment from 'moment';
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';
export default {
  name: 'Header',
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
      // Thêm script vào DOM
      const jQueryScript = document.createElement('script');
      jQueryScript.src = 'https://code.jquery.com/jquery-3.2.1.slim.min.js';
      document.body.appendChild(jQueryScript);

      const bootstrapScript = document.createElement('script');
      bootstrapScript.src = 'https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.bundle.min.js';
      document.body.appendChild(bootstrapScript);

      window.addEventListener('resize', adjustNav);
      adjustNav();
    });

    window.addEventListener('scroll', () => {
      scrollTop.value = window.scrollY;
    });

    return { scrollTop };
  },
  data() {
    return {
      service: new AccountService(),
      token: localStorage.getItem('token'),
      loggedInUser: localStorage.getItem('loggedInUser'),
      codeXN: localStorage.getItem('codeXN'),
      isShow: false,
      account: {},
      baseUrl: baseURL,
    }
  },
  methods: {
  
    checkAuthenticate(){
  this.service.checkAuthenticate(this.token,this.codeXN)
      .then(response => {
          })
      .catch(error => {
        console.log(error)
        toast(error.response.data.message)
        this.logout();
      });
    },
    logout() {
    axios.post(`${this.baseUrl}/logout/${this.token}`)
      .then(response => {
        localStorage.clear();
        toast.info('Đăng xuất thành công,vui lòng đăng nhập lại')
        setTimeout(() => {
          window.location.reload();
				}, 1000);
      })
      .catch(error => {
        console.error('Error logging out:', error);
      });
  },
  acp(){
this.isShow = false;
},
    getAcc() {
      this.service.getAccountLogin(this.token).then(res => {
        this.account = res;
      })
    },
  },
  created() {
   this.isShow = true;
   setInterval(() => {
    if(this.loggedInUser){
    this.getAcc()
    this.checkAuthenticate()
   }
  }, 1000);

  },
  mounted() {
    setTimeout(() => {
      const header = document.querySelector('.header-area');
      if (header) {
        header.classList.add('header-area--show');
      }
    }, 10);


    setTimeout(() => {
      const header = document.querySelector('.notify');
      if (header) {
        this.isShow = true;
        header.classList.add('notify--show');
      }
    }, 10); 
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Open+Sans:400,700,800');
@import url('https://fonts.googleapis.com/css?family=Lobster');
html {
  font-size: 62.5%;
}
body {
  top:0;
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
  opacity: 0;
  transition: opacity 0.5s ease-in-out;
  position: fixed;
  z-index: 1000;
  width: 100%;
  background-image: linear-gradient(0, #610404, #b11e0a);
  background-attachment: fixed;
  background-position: center center;
  background-repeat: no-repeat;
  background-size: cover;
}

.notify.notify--show {
    background-color: white;
    position: fixed;
    z-index: 9999;
    width: 80vw;
    margin-left: 10%;
    box-shadow: 0 24px 62px #999;
    /* height: 80%; */
    border-radius: 0.5rem;
    font-size: 1.5vw;
    zoom: 80vw;
    text-align: center;
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
  background-color: brown;
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
  -ms-transform: translateY(-1px) rotate(45deg);
  transform: translateY(-1px) rotate(45deg);
}
.navbar .navbar-toggler .menu-icon-bar:last-child {
  opacity: 1;
  -webkit-transform: translateY(-1px) rotate(135deg);
  -ms-transform: translateY(-1px) rotate(135deg);
  transform: translateY(-1px) rotate(135deg);
}
.navbar .navbar-toggler.collapsed .menu-icon-bar {
  opacity: 1;
}
.navbar .navbar-toggler.collapsed .menu-icon-bar:first-child {
  -webkit-transform: translateY(-7px) rotate(0);
  -ms-transform: translateY(-7px) rotate(0);
  transform: translateY(-7px) rotate(0);
}
.navbar .navbar-toggler.collapsed .menu-icon-bar:last-child {
  -webkit-transform: translateY(5px) rotate(0);
  -ms-transform: translateY(5px) rotate(0);
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
.header-area--show {
  opacity: 1;
}
</style>
