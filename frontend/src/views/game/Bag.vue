<template>
    <link rel="stylesheet" href="https://allyoucan.cloud/cdn/icofont/1.0.1/icofont.css" integrity="sha384-jbCTJB16Q17718YM9U22iJkhuGbS0Gd2LjaWb4YJEZToOPmnKDjySVa323U+W7Fv" crossorigin="anonymous">
    <app-header></app-header>
    <br>    <br>    <br>    <br>    <br>
<div class="container">
   
    <div class="row">
        <div class="col-md-3">
            <div class="osahan-account-page-left shadow-sm bg-white h-100">
                <div class="border-bottom p-4">
                    <div class="osahan-user text-center">
                        <div class="osahan-user-media">
                            <img class="mb-3 rounded-pill shadow-sm mt-1" :src="baseUrl + '/files/' + loggedInUser.avatar">
                            <div class="osahan-user-media-body">
                                <p class="mb-1">{{loggedInUser.username }}</p>
                                
                                <p class="mb-0 text-black font-weight-bold"><a class="text-primary mr-3" data-toggle="modal" data-target="#edit-profile-modal" href="/profile"><i class="icofont-ui-edit"></i> Sửa</a></p>
                            </div>
                        </div>
                    </div>
                </div>
               
            </div>
        </div>
        <div class="col-md-9">
            <div class="osahan-account-page-right shadow-sm bg-white p-4 h-100">
                <div class="tab-content" id="myTabContent">
                    <div class="tab-pane fade  active show" id="favourites" role="tabpanel" aria-labelledby="favourites-tab">
                        <div class="row">
                            <div class="col-md-4 col-sm-6 mb-4 pb-2" v-for="item in ListItems">
                                <div class="list-card bg-white h-100 rounded overflow-hidden position-relative shadow-sm">
                                    <div class="list-card-image">
                                        <div class="favourite-heart text-danger position-absolute"><a href="#"><i class="icofont-heart"></i></a></div>
                                        <div v-if="item.vinhvien" class="member-plan position-absolute"><span class="badge badge-dark">Vĩnh Viễn</span></div>
                                        <a href="#">
                                            <img :src="item.beyBlade.images" class="img-fluid item-img">
                                        </a>
                                 
                                    </div>
                                    <div class="p-3 position-relative">
                                        <div class="list-card-body">
                                          
                                            <p class="text-gray mb-3">{{ truncated(item.beyBlade.name) }}</p>
                                            <p v-if="!item.vinhvien" class="text-gray mb-3">Ngày Hết Hạn:{{ formatCreatedAt(item.ngayhethan) }}</p>
                                            <p v-else class="text-gray mb-3">Unlimited </p>
                                            
                                           <div v-if="!item.selectedBey">
                                            <button @click="setBey(item)" class="btn btn-primary"  type="button" > Chọn
                                      <span class="spinner-grow spinner-green-sm" role="status" aria-hidden="true">

                                      </span>
                                     
                                </button>
                             
                                           </div>
                                                <div v-else >
                                <button class="btn btn-primary" type="button" disabled="">
                                    Đang Sử Dụng
                                </button>
                            </div>
                                                 
                                        </div>
                                      
                                    </div>
                                </div>
                            </div>
                           
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</template>








<script>
import HelloWorld from '@/components/HelloWorld.vue'
import Header from '@/views/support/Header.vue';
import Footer from '@/views/support/Footer.vue';
import axios from 'axios';
import { baseWeb,baseURL } from '@/router/index';
import { mapGetters } from 'vuex';
import moment from 'moment';
import store from '@/store';
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';
import { AccountService } from '@/core/service/accountservice';

import { GameService } from '@/core/service/game';

export default {
  name: 'Profile',
  computed: {
   ...mapGetters(['loggedInUser']),
 },
created() {
 this.getInfo();
 this.loadUser();
 this.getItemBag();
 },
 data() {
   return {
     baseUrl : baseURL,
     token: localStorage.getItem('token'),
     accountService: new AccountService(),
     gameService: new GameService(),
     userInfo: {},
     ListItems:[],
     selected:{}

   };
 },

 methods: {

setBey(bey){
    this.selected = bey;
    this.gameService.setItem(this.token,bey).then(res => {
        toast.success(res.data.message)
        this.getItemBag()
        }).catch(error => {
 toast.warning(error.response.data.message)
});
},

    getItemBag(){
        this.gameService.getItems(this.token).then(res => {
            this.ListItems = res.data.data.items;
        })
    },

   async getInfo() {
 try {
   this.userInfo = await this.accountService.getUserInfo(this.token);
   
 } catch (error) {
   toast.success('Hãy cập nhật thông tin của bạn');
   window.location.href ='/';
   console.error('Error fetching:', error);
 }
},
truncated(name) {
    const max = 18;
          if (name.length > max) {
              return name.substring(0, max) + '...';
          }
          return name;
      },


loadUser(){
  this.accountService.getUserLogin(this.token).then(res => {

  })
},

  formatBirthDate(date) {
   if (!date) return ''; // Đảm bảo xử lý trường hợp ngày sinh không tồn tại
   return moment(date).format('YYYY-MM-DD');
 },
   formatCreatedAt(timestamp) {
     return moment(timestamp).format('DD/MM/YYYY HH:mm'); // Định dạng ngày tháng giờ phút theo ý muốn
 },


  },
  components: {
    'app-header': Header,
    'app-footer': Footer,

    HelloWorld
  }
 
}
</script>













<style>

body{
    margin-top:20px;
    background:#eee;
}
/* My Account */
.payments-item img.mr-3 {
    width: 47px;
}
.order-list .btn {
    border-radius: 2px;
    min-width: 121px;
    font-size: 13px;
    padding: 7px 0 7px 0;
}
.osahan-account-page-left .nav-link {
    padding: 18px 20px;
    border: none;
    font-weight: 600;
    color: #535665;
}
.osahan-account-page-left .nav-link i {
    width: 28px;
    height: 28px;
    background: #535665;
    display: inline-block;
    text-align: center;
    line-height: 29px;
    font-size: 15px;
    border-radius: 50px;
    margin: 0 7px 0 0px;
    color: #fff;
}
.osahan-account-page-left .nav-link.active {
    background: #f3f7f8;
    color: #282c3f !important;
}
.osahan-account-page-left .nav-link.active i {
    background: #282c3f !important;
}
.osahan-user-media img {
    width: 6vw;
}
.card offer-card h5.card-title {
    border: 2px dotted #000;
}
.card.offer-card h5 {
    border: 1px dotted #daceb7;
    display: inline-table;
    color: #17a2b8;
    margin: 0 0 19px 0;
    font-size: 15px;
    padding: 6px 10px 6px 6px;
    border-radius: 2px;
    background: #fffae6;
    position: relative;
}
.card.offer-card h5 img {
    height: 22px;
    object-fit: cover;
    width: 22px;
    margin: 0 8px 0 0;
    border-radius: 2px;
}
.card.offer-card h5:after {
    border-left: 4px solid transparent;
    border-right: 4px solid transparent;
    border-bottom: 4px solid #daceb7;
    content: "";
    left: 30px;
    position: absolute;
    bottom: 0;
}
.card.offer-card h5:before {
    border-left: 4px solid transparent;
    border-right: 4px solid transparent;
    border-top: 4px solid #daceb7;
    content: "";
    left: 30px;
    position: absolute;
    top: 0;
}
.payments-item .media {
    align-items: center;
}
.payments-item .media img {
    margin: 0 40px 0 11px !important;
}
.reviews-members .media .mr-3 {
    width: 56px;
    height: 56px;
    object-fit: cover;
}
.order-list img.mr-4 {
    width: 70px;
    height: 70px;
    object-fit: cover;
    box-shadow: 0 .125rem .25rem rgba(0, 0, 0, .075)!important;
    border-radius: 2px;
}
.osahan-cart-item p.text-gray.float-right {
    margin: 3px 0 0 0;
    font-size: 12px;
}
.osahan-cart-item .food-item {
    vertical-align: bottom;
}

.h1, .h2, .h3, .h4, .h5, .h6, h1, h2, h3, h4, h5, h6 {
    color: #000000;
}

.shadow-sm {
    box-shadow: 0 .125rem .25rem rgba(0,0,0,.075)!important;
}

.rounded-pill {
    border-radius: 50rem!important;
}
a:hover{
    text-decoration:none;
}
p {
    font-size: 1.5em;
    color: #000000;
}
.col-md-3{
    zoom: 150%;
}


.badge-dark {
    color: #fff;
    zoom: 200%;
    background-color: #8f0000;
    border: 1px solid #fff;
}


.activeTrue{
    color: #fff;
    background-color: #8f0000;
    border: 1px solid #fff;
}


.list-card-image {
    height: 20vw;
}


.img-fluid {
    padding: 1vw;
    width: 20vw;
    height: 20vw;
}


.col-md-4.col-sm-6.mb-4.pb-2:hover {
    box-shadow: 0 1px 2px #080808;
}
</style>