<template>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">


<title>Đăng Nhập</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="section bg-theme-color-light overlay-dark overlay-opacity-8 bg-cover lazy" data-loaded="true">
<div class="container">
<div class="row text-center-md text-center-xs d-middle justify-content-start">
<div class="col-12 col-lg-6 mb-5 text-white aos-init aos-animate" data-aos="fade-in" data-aos-delay="0" data-aos-offset="0">

<h1 class="display-4 fw-bold mb-0">
<span class="d-inline-block">
<span class="h6 fw-normal d-block text-align-end text-center-xs">
Đã có hơn 100 người
</span>Chơi
<span class="text-danger">Beyblade Game</span> 
</span>
</h1>
<p class="h3 fw-normal mb-0">
trong suốt 1 năm qua
</p>
</div>

<div class="col-12 col-lg-6 text-align-end text-center-md text-center-xs aos-init aos-animate"
 data-aos="fade-in" data-aos-delay="50" data-aos-offset="0">
<div class="d-inline-block bg-white shadow-primary-xs rounded p-4 p-md-5 w-100 max-w-450 text-align-start">
<h2 class="h5 mb-5">
<i class="fi fi-homeadvisor"></i>
<span class="d-inline-block px-2">Đăng Nhập</span>
</h2>

<form novalidate class="bs-validate" @submit.prevent="checklogin()">


<div class="form-floating mb-3">
<input placeholder="Address or Zipcode" id="s_address" type="text" value class="form-control" v-model="username">
<label for="s_address">Tài Khoản</label>
</div>

<div class="form-floating mb-3">
<input placeholder="Address or Zipcode" id="s_address" type="password" value class="form-control"  v-model="password">
<label for="s_address">Mật Khẩu</label>
</div>

<button type="submit" class="btn btn-lg btn-danger bg-gradient-danger btn-block">
  <i class="fi fi-search"></i>
  Đăng Nhập
</button>



</form>
<br>
<p class="h6 fw-normal mb-0">
Bạn chưa có tài khoản? <span class="register" @click="goto('register')">Đăng Ký Ngay</span>
</p>
</div>
</div>
</div>
</div>
</div>

</body>
<!-- <app-footer></app-footer> -->
</html>
</template>
<script>
import axios from 'axios';
import { baseURL } from '@/router/index';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';
import Foooter from '/src/views/support/Footer.vue';

export default {
  setup() {

    // const accountService = new AccountService();
    const store = useStore();
    const router = useRouter();

    return {
      store,
      router,
      // accountService // Trả về instance của AccountService từ setup
    };
  },
  data() {
    return {
      username : "",
			password : "",
    };
  },
  components: {
      'app-footer': Foooter,
    },
  methods: {

    goto(link){
window.location.href = '/' + link;
  },
   
    async checklogin() {

      


      try {
        let object = {
          username: this.username,
          password: this.password
        };
        
        await axios.post(`${baseURL}/login`, object).then(res =>{
          toast.success(res?.data.message);
          localStorage.setItem("token", res?.data.data.token);
          localStorage.setItem('loggedInUser', JSON.stringify(res?.data.data.user));
          localStorage.setItem('codeXN', (res.data.data.user.code));
          setTimeout(() => {
          window.location.href = "/";
				}, 1000);
    
        });
    
      } catch (error) {
        console.error('Failed to call API:', error);
        toast.error('Tên Đăng Nhập Hoặc Mật Khẩu Không Chính Xác');
      }
    }
  }
};

</script>
<style type="text/css">
  .register{
    color: black;
  }
  .register:hover{
    color: rgb(175, 0, 0);
    font-weight: bold;

  }
  body {
  background-image: url(https://scontent.fhan18-1.fna.fbcdn.net/v/t1.15752-9/436419524_1129278501671301_1369923404645869328_n.png?_nc_cat=109&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeGZI_BoM06YoivUNgrf_NheUZf2kbAmdGpRl_aRsCZ0atiI4h59KRHloQr6SbRRuGTAq59fsUBR0tGWSAXKA6Gn&_nc_ohc=8Vyi3t0AkK4Q7kNvgEvgmIJ&_nc_ht=scontent.fhan18-1.fna&oh=03_Q7cD1QEUaZwueHphDqr33YQzoHJ11x6IKrqn5x4kBicVn4XdJA&oe=66814DFD);
  background-size: cover;
}
/* @media only screen and (max-width: 768px) {
  .d-inline-block.bg-white.shadow-primary-xs.rounded.p-4.p-md-5.w-100.max-w-450.text-align-start {
    zoom: 130%;
  }
} */


@media only screen and (min-width: 768px){
body:not(.layout-admin) .section, body:not(.layout-admin) section {
    padding: 100px 0;
}
}

.d-middle {
    align-items: center;
    display: flex;
    justify-content: center;
}

.opacity-8,.overlay-opacity-8:after {
    opacity: .8
}

opacity-8, .overlay-opacity-8:after {
    opacity: .8;
}
.overlay-dark-hover:hover:after, .overlay-dark:after {
    background: #131f43;
}
.overlay-dark-hover:after, .overlay-dark:after, .overlay-light-hover:after, .overlay-light:after {
    bottom: 0;
    content: "";
    height: 100%;
    left: 0;
    position: absolute;
    right: 0;
    top: 0;
    width: 100%;
    z-index: 0;
}

.row.text-center-md.text-center-xs.d-middle.justify-content-start {
    zoom: 150%;
}
.bg-theme-color-light {
    background-color: rgba(87,79,236,.04)!important;
}
.bg-cover {
    background-position: 50%!important;
    background-repeat: no-repeat!important;
    background-size: cover!important;
}
.overlay-dark, .overlay-dark-hover, .overlay-light, .overlay-light-hover {
    position: relative;
}
#overlay-default, .overlay-dark, .overlay-dark-hover:after, .overlay-default, .overlay-light, .overlay-light-hover:after {
    transition: all .2s ease;
}
.section, section {
    position: relative;
    word-break: break-word;
}

.overlay-dark>*, .overlay-light>* {
    position: relative;
    z-index: 1;
}




button.btn.w-100.btn-lg.btn-danger.bg-gradient-danger {
    background-color: brown;
}
button.btn.w-100.btn-lg.btn-danger.bg-gradient-danger:hover {
    background-color: rgb(37, 0, 0);
}

.col-12.col-lg-6.text-align-end.text-center-md.text-center-xs.aos-init.aos-animate {
    padding: 4rem;
}
    </style>
