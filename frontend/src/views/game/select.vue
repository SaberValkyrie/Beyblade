<template>
  
  <div class="row">

    <div class="item features-without-image col-12 col-md-6 col-lg-4" @click="go('/game/top')">
          <div class="item-wrapper">
    <div class="card-box align-left">
      <div class="img-wrapper mt-4 mb-3">
        <img src="https://t3.ftcdn.net/jpg/05/67/74/42/360_F_567744258_Th2Yjsk6BcKJ0T81KQSpcH6wjFV23TjE.jpg" alt="" data-slide-to="0" data-bs-slide-to="0">
      </div>
      <h5 class="card-title mbr-fonts-style display-7">
        <strong>Giải Đấu Beyblade</strong>
        <p class="card-text mbr-fonts-style display-7">Cùng nhau đạt 10 thứ hạng cao nhất để rinh ngay phần thưởng hấp dẫn</p>

      </h5>
    </div>
  </div>
</div>


<div class="item features-without-image col-12 col-md-6 col-lg-4" @click="go('/game/Boss')">
  <div class="item-wrapper">
    <div class="card-box align-left">
      <div class="img-wrapper mt-4 mb-3">
        <img src="https://png.pngtree.com/element_our/sm/20180516/sm_5afc60f766848.png"
         data-slide-to="2" data-bs-slide-to="2" alt="">
      </div>
      <h5 class="card-title mbr-fonts-style display-7">
        <strong>Thử Thách Boss</strong>
        <p class="card-text mbr-fonts-style display-7">Đánh Bại Boss Bí Ẩn để Nhận Quà</p>

      </h5>
    </div>
  </div>
</div>


<div class="item features-without-image col-12 col-md-6 col-lg-4" @click="go('/luyentap')">
  <div class="item-wrapper">
    <div class="card-box align-left">
      <div class="img-wrapper mt-4 mb-3">
        <img src="https://ih1.redbubble.net/image.693422754.2998/st,small,507x507-pad,600x600,f8f8f8.u6.jpg" data-slide-to="2" data-bs-slide-to="2" alt="">
      </div>
      <h5 class="card-title mbr-fonts-style display-7">
        <strong>Tập Luyện Sơ Cấp</strong>
        <p class="card-text mbr-fonts-style display-7">Tham gia luyện tập để quen với cơ chế</p>

      </h5>
    </div>
  </div>
</div>



<div class="item features-without-image col-12 col-md-6 col-lg-4"  @click="go('/shop')">
  <div class="item-wrapper">
    <div class="card-box align-left">
      <div class="img-wrapper mt-4 mb-3">
        <img src="https://banner2.cleanpng.com/20180703/vtz/kisspng-shopping-cart-software-computer-icons-mayline-5b3b72a89c95a3.3174593115306226326414.jpg" data-slide-to="4" data-bs-slide-to="4" alt="">
      </div>
      <h5 class="card-title mbr-fonts-style display-7">
        <strong>Săn Beyblade Hiếm</strong>
        <p class="card-text mbr-fonts-style display-7">Cơ Hội Mua Được Beyblade Siêu Mạnh Với Số Lượng Có Hạn</p>

      </h5>
    </div>
  </div>
</div>


<div class="item features-without-image col-12 col-md-6 col-lg-4" @click="go('/user/bag')">
  <div class="item-wrapper">
    <div class="card-box align-left">
      <div class="img-wrapper mt-4 mb-3">
        <img src="https://cf.shopee.vn/file/7cbad3fd1acb8bceddff3765bc863ea4" data-slide-to="6" data-bs-slide-to="6" alt="">
      </div>
      <h5 class="card-title mbr-fonts-style display-7">
        <strong>Kho Đồ Của Tôi</strong>
        <p class="card-text mbr-fonts-style display-7">Vào Kho Đồ Tìm Kiếm Và Trang Bị Bey Mạnh Nhất</p>

      </h5>
    </div>
  </div>
</div>
<div class="item features-without-image col-12 col-md-6 col-lg-4" @click="go('/checkIn')">
  <div class="item-wrapper">
    <div class="card-box align-left">
      <div class="img-wrapper mt-4 mb-3">
        <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdTeZDfZOsrU6afSehJFyNkUzwep8VKgiE-iEFhuMnYg&s" data-slide-to="7" data-bs-slide-to="7" alt="">
      </div>
      <h5 class="card-title mbr-fonts-style display-7">
        <strong>Ưu Đãi Mỗi Ngày</strong>
        <p class="card-text mbr-fonts-style display-7">
           Check in mỗi ngày để nhận thưởng nhé ! </p>

      </h5>
    </div>
  </div>
</div>
</div>
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
  name: 'Select',
  data(){
 
  },
  methods:{
    go(link){
      window.location.href = link;
  },
  },
}
</script>

<style>
.background-video-wrapper {
position: absolute;
top: 0;
left: 0;
width: 100%;
height: 100%;
overflow: hidden;
}

.background-video {
width: 100%;
height: 100%;
object-fit: cover;
}

body{
  margin-top: -25rem;
}
a.btn.btn-primary.display-7 {
    font-size: 17px;
    background-color: brown;
}
.card.col-12.col-md-12.col-lg-8 {
    padding: 1rem;
}
section#contacts-3-ubHUN1CDhi {
    zoom: 72%;
}
.btn-primary, .btn-primary:active {
  background-color: #b90000 !important;
  border-color: #9c9c9c !important;
  color: #ffffff !important;
  box-shadow: none;
}


.card-box.align-left:hover {
zoom: 120%;
  border: 1px solid bisque;
  box-shadow: 0 1px 2px #999;
}

      </style>

