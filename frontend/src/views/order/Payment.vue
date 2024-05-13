<template>
    <!-- Jumbotron -->
  <app-header></app-header>
  <br>
  <br>
  <br>
    <!-- Jumbotron -->
  
   
   
  <section class="bg-light py-5">
    
    <div class="container">
      <div class="row">
        <div class="col-xl-8 col-lg-8 mb-4">
      

          <!-- Checkout -->
          <div class="card shadow-0 border">
            <div class="p-4">
        
  
  <div>
    Thanh Toán Trực Tiếp
    <a @click="payment" >Tại Đây</a>
  </div>
  <br>  
   <img class="qr" :src="getQr()" v-if="urlTT == ''">
   <div v-else>

    <a :href="urlTT"> Đi tới trang thanh toán  </a>
   </div>
<!-- {{ order }} -->
            </div>
          </div>
          <!-- Checkout -->
        </div>
        <div class="col-xl-4 col-lg-4 d-flex justify-content-center justify-content-lg-end">
      
          <div class="ms-lg-4 mt-4 mt-lg-0">
  
            <div class="concac">
                    <input type="checkbox" @click="setCK()">
                    <br>
                    <span>Xác Nhận Đã Chuyển Khoản theo mã QR đang có </span>
                   </div>
                   <div class="float">
                    <button v-if="!ck" class="btn btn-success shadow-0 border" @click="mes('Vui Lòng Xác Nhận Đã Chuyển Khoản')" style="background-color: darkgray;">Xác Nhận Thanh Toán</button>
              <button v-else class="btn btn-success shadow-0 border" @click="ok()" >Xác Nhận Thanh Toán</button>

              </div>
          </div>

        </div>
      </div>
    </div>
  </section>
  
  <!-- Footer -->
      </template>
      
      <script>
      // @ is an alias to /src
      import Header from '@/views/support/Header.vue';
      import Footer from '@/views/support/Footer.vue';
      import { toast } from 'vue3-toastify';
      import { mapGetters } from 'vuex';
  import 'vue3-toastify/dist/index.css';
  import { AccountService } from '@/core/service/accountservice';
  import { baseWeb,baseURL } from '@/router/index';
  import {OrderService} from '@/core/service/orderservice.js';
  import axios from 'axios';
  
      
      export default {
        computed: {
   ...mapGetters(['loggedInUser']),
  },
        name: 'Payment',
        components: {
          'app-header': Header,
          'app-footer': Footer,
        },
         data(){
        return{
          orderService : new OrderService(),
          service: new AccountService(),
          order:{},
          totalPrice: 0,
      
           orderKey : this.$route.params.order_key,
           id: this.$route.params.id,
           
          token: localStorage.getItem('token'),
          orders: localStorage.getItem('orders'),
          baseUrl : baseURL,
          accountName: 'Shop Plaza',
        addInfo: '',
        stk: '0383087656',
        urlTT:'',

        ck:false
        }
       
      },
      created(){
        this.getOrder();
      },
      methods :{
        getOrder(){
            this.orderService.getOrderByKey(this.orderKey,this.id,this.token).then(res =>{
                this.order = res.data.data;
                if(this.order.status != 1){
                    window.location.href = "/order";
                }
            }).catch(error => {
          toast.warning(error.response.data.message)

          setTimeout(() => {
          window.location.href = "/order";
				}, 1500);


         }); 
        },
  
        getQr(){
          this.addInfo = this.orderKey;

          if (this.order.details && this.order.details.total !== undefined) {
    const link = `https://api.vietqr.io/image/MBbank-${this.stk}-SUKDJlE.jpg?accountName=${this.accountName}&amount=${this.order.details.total}&addInfo=${this.addInfo}`;
    // Trả về đường link
    return link;
  }
      },
   ok(){
    toast.success('Xác Nhận Thành Công!,Vui lòng chờ hệ thống duyệt đơn hàng của bạn');
    setTimeout(() => {
          window.location.href = "/order/details/" + this.orderKey;
				}, 3000);
   },
  setCK(){
        this.ck = true;
  },
  mes(m){
toast.info(m);
  },

payment(){

  this.orderService.getPay(`${this.order.details.total}`).then(res =>{
    this.urlTT = res.data.data.url;
  })
},
        goto(ad){
  
          window.location.href = "/" + ad;
        },
  
        truncatedProductName(name) {
      const max = 70;
            if (name.length > max) {
                return name.substring(0, max) + '...';
            }
            return name;
        },

      }
      }
     
      </script>




      <style scoped>
  .badge-brown {
      background-color: #8B4513; /* Màu đỏ đậm dạng brown */
      color: white; /* Màu chữ là trắng để tương phản */
  }
  .form-check.h-100.border.rounded-3 {
      background-color: floralwhite;
  }
  
    .container {
  
      zoom: 149%;
      text-align: center;
  }
  .chat{
    border: none;
      background-color: #ffffff;
      color: #f30000;
      font-size: 12px;
  }
  button.btn.btn-success.shadow-0.border {
      background-color: brown;
  }
  button.btn.btn-success.shadow-0.border:hover {
      background-color: rgb(209, 0, 0);
  }
  .col-sm-8.text-secondary {
      display: flex;
      /* padding: 2px; */
  }
  .qr {
      zoom:50%;
      left: 50%;
  }
  .mb-4, .my-4 {
      margin-bottom: 1.5rem!important;
      margin-right: 99px;
  }
  .col-xl-4.col-lg-4.d-flex.justify-content-center.justify-content-lg-end {
      background-color: white;
      padding: 30px;
      border: 1px solid darkorange;
      position: fixed;
      right:0;
  }
    </style>
  