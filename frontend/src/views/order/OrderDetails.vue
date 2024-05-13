<template>
  <app-header></app-header>
  <div class="container1">
    <article class="card">
        <header class="card-header"> Đơn Hàng Của Tôi</header>
        <div class="card-body">
     
          <article class="card">

                <div class="card-body row">
                    <div class="col"> <strong>Ngày Đặt Hàng</strong> <br>{{ formatCreatedAt(order.date) }} </div>



                    <div v-if="loggedInUser.username == order.user.username" class="col"> <strong>Người Bán:</strong>
                       <br>{{ order.seller &&  order.seller.username !== null ? order.seller.username : '' }}
                       <button class="chat" @click="goto('chat/' + order.seller.username)">
                        <i class="fa fa-user"></i>
                        Chat
                       </button>
                       </div>

                       <div v-else class="col"> <strong>Người Mua:</strong>
                       <br>{{ order.user &&  order.user.username !== null ? order.user.username : '' }}
                       <button class="chat" @click="goto('chat/' + order.user.username)">
                        <i class="fa fa-user"></i>
                        Chat
                       </button>
                       </div>





                    <div class="col"> <strong>Trạng Thái:</strong> <br> {{ s.status }}</div>
                    <div class="col">
    <strong>Mã Đơn Hàng:</strong> <br>
    {{ order.details &&  order.details.code !== null ? order.details.code : '' }}
</div>
                </div>

           
            </article>
           
            <div class="track" v-if="order.status <= 7">

         <div v-for="(st, index) in listStatus" :key="st.id"
         :class="[ s.id <= 7 &&  s.id >= st.id ? 'step active' : 'step']">
          <span  class="icon">
            <i class="" :class="  s.id <= 7 ? st.iconClass : 'fa fa-ban'"></i></span>
         <span class="text" >{{ s.id <= 7 ? st.status : 'Đã Hủy Đơn'}}</span>
     
    
  </div>
</div>
<div class="track" v-else>
  <div class="step">
          <span  class="icon">
            <i class="fa fa-ban"></i></span>
         <span class="text" >Đã Hủy Đơn</span>
  </div>
</div>
            <hr>
            <ul class="row" v-for="item in ListItem" :key="item.Id">
                    <div class="itemside mb-3" >
                      <div class="me-3 position-relative">
   <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill badge-danger">
     X{{ item.quantityOrder }}
   </span>
   <img :src="baseUrl + '/files/' + item.product.images"
   style="    height: 7rem;
   width: 7rem;" class="img-sm rounded border" />
 </div>
 <div class="">
    <a >
     {{ item.product.name }} -
     <span style="color: crimson;font-weight: 300;"> ₫{{ item.product.price * item.quantityOrder }} </span>
     <br>
     
    </a>
  </div>
 </div>
                

            </ul>
            <hr>

            <div class="haidz">
        <div class="ms-lg-4 mt-4 mt-lg-0" v-if="order.details">
                 <h5 class="card-title mb-3" style="color: orangered;font-weight: bold;">Thanh Toán</h5>

          <div class="d-flex justify-content-between">
            <p class="mb-2">Tổng Tiền Sản Phẩm:</p>
            <p class="mb-2" style="color:black">₫{{ totalProductPrice }}</p>
          </div>
       
          <div class="d-flex justify-content-between" v-if="order.details.ship > 0">
            <p class="mb-2">Phí Vận Chuyển:</p>
            <p class="mb-2" style="color: black" >+ ₫{{ order.details.ship }}</p>
          </div>
          <div class="d-flex justify-content-between" v-if="giam > 0">
            <p class="mb-2">Số Tiền Được Giảm:</p>
            <p class="mb-2 " style="color: green">- ₫{{giam}}</p>
          </div>
          <div class="d-flex justify-content-between" v-if="order.details.voucher ? order.details.voucher.percent > 0 : 0">
            <p class="mb-2">% Giảm Giá</p>
            <p class="mb-2" style="color: black" >{{ order.details.voucher ? order.details.voucher.percent : 0}} %</p>
          </div>

          <div class="d-flex justify-content-between">
            <p class="mb-2">Tổng Tiền:</p>
            <p class="mb-2 fw-bold" style="color:brown">₫ {{ totalPrice  +  order.details.ship - (giam) }} </p>
          </div>

          <div class="d-flex justify-content-between" v-if="loggedInUser.username == order.seller.username">
            <p class="mb-2" style="color:green">Số Tiền Nhận Được :</p>
       
            <p class="mb-2 fw-bold" style="color:green">₫ {{ order.details.moneyReceived }} </p>
          </div>

          <h6 class="text-dark my-4"></h6>
          <div class="d-flex align-items-center mb-4">
          
          </div>
        
        </div>
      </div>

            <hr>
            <a href="#" class="btn btn-warning" data-abc="true"> Về Trang Đơn Hàng</a>
         
        </div>
    </article>
</div>

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
import moment from 'moment';

export default {
  computed: {
...mapGetters(['loggedInUser']),
totalProductPrice() {
      // Tính tổng giá tiền sản phẩm dựa trên danh sách ListItem
      return this.ListItem.reduce((total, item) => {
        // Tính tổng giá tiền của mỗi mặt hàng
        const itemPrice = item.product.price * item.quantityOrder;
        this.totalPrice = total + itemPrice;
        return total + itemPrice;
      }, 0); // Khởi tạo total ban đầu là 0
    },
    giam(){
      return this.order.details && this.order.details.voucher ? this.totalPrice * this.order.details.voucher.percent / 100 : 0;
    }
},
  name: 'Checkout',
  components: {
    'app-header': Header,
    'app-footer': Footer,
  },
   data(){
  return{
    orderService : new OrderService(),
    service: new AccountService(),
    ListItem:[],  
    order:{},
    codeApply:{},
     orderKey : this.$route.params.order_key,
     id: this.$route.params.id,
    token: localStorage.getItem('token'),
    baseUrl : baseURL,
    s:'',
    listStatus:[],
    totalPrice:'',
    saleOff:0,
    }
 
},
created(){
this.getOrder()
this.getItems()
this.getAllStatus()
this.load()
},
methods :{
  
  getItems(){
            this.orderService.getItemsByOrder(this.orderKey,this.id,this.token).then(res =>{
                this.ListItem = res.data.data;
            }).catch(error => {
          toast.warning(error.response.data.message)
          setTimeout(() => {
          window.location.href = "/order";
				}, 1500);
         }); 
        },
        getAllStatus(){
  this.orderService.getStatus().then(res => {
    // Lấy danh sách status từ response
    const statusList = res.data.data;
    console.log(res)
    
    // Lọc danh sách, loại bỏ các mục có status > 6
    this.listStatus = statusList.filter(status => status.id <= 6);
  })
},

        getStatus(id){
    this.orderService.getStatusByID(id).then(res =>{
      this.s = res.data.data;
    })
  },
  load(){
    },
  getOrder(){
            this.orderService.getOrderByKey(this.orderKey,this.id,this.token).then(res =>{
                this.order = res.data.data;
                this.getStatus(this.order.status);
            }).catch(error => {
          toast.warning(error.response.data.message)

          setTimeout(() => {
          window.location.href = "/order";
				}, 1500);


         }); 
        },
ok(){
toast.success('Xác Nhận Thành Công!,Vui lòng chờ hệ thống duyệt đơn hàng của bạn');
setTimeout(() => {
    window.location.href = "/";
  }, 3000);
},
setCK(c){
this.ck = c;
},
mes(m){
toast.info(m);
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
  formatCreatedAt(timestamp) {
      return moment(timestamp).format('DD/MM/YYYY HH:mm'); // Định dạng ngày tháng giờ phút theo ý muốn
  },   
}
}

</script>



<style>
@import url('https://fonts.googleapis.com/css?family=Open+Sans&display=swap');
body{background-color: #eeeeee;font-family: 'Open Sans',serif}
.container
{
  margin-top:50px;
  margin-bottom: 50px;
zoom:150%;
max-width: 542px;

}

.container1, .container1-lg, .container1-md, .container1-sm, .container1-xl {
    max-width: 541px;
    zoom: 150%;
    margin-left: 20%;
}

.card{
  position: relative;
  display: -webkit-box;

  display: -ms-flexbox;
  display: flex;
  -webkit-box-orient: vertical;
  -webkit-box-direction: normal;
  -ms-flex-direction: column;flex-direction: column;
  min-width: 0;word-wrap: break-word;
  background-color: #fff;background-clip: border-box;

  border: 1px solid rgba(0, 0, 0, 0.1);border-radius: 0.10rem
}
  .card-header:first-child{border-radius: calc(0.37rem - 1px) calc(0.37rem - 1px) 0 0}.card-header{padding: 0.75rem 1.25rem;margin-bottom: 0;background-color: #fff;border-bottom: 1px solid rgba(0, 0, 0, 0.1)}.track{position: relative;background-color: #ddd;height: 7px;display: -webkit-box;display: -ms-flexbox;display: flex;margin-bottom: 60px;margin-top: 50px}.track .step{-webkit-box-flex: 1;-ms-flex-positive: 1;flex-grow: 1;width: 25%;margin-top: -18px;text-align: center;position: relative}.track .step.active:before{background: #FF5722}.track .step::before{height: 7px;position: absolute;content: "";width: 100%;left: 0;top: 18px}.track .step.active .icon{background: #ee5435;color: #fff}.track .icon{display: inline-block;width: 40px;height: 40px;line-height: 40px;position: relative;border-radius: 100%;background: #ddd}.track .step.active .text{font-weight: 400;color: #000}.track .text{display: block;margin-top: 7px}.itemside{position: relative;display: -webkit-box;display: -ms-flexbox;display: flex;width: 100%}.itemside .aside{position: relative;-ms-flex-negative: 0;flex-shrink: 0}.img-sm{width: 80px;height: 80px;padding: 7px}

ul.row{
    list-style: none;
    display: block;
}



ul.row {
    padding: 2rem;
}

.itemside .info{padding-left: 15px;padding-right: 7px}.itemside .title{display: block;margin-bottom: 5px;color: #212529}p{margin-top: 0;margin-bottom: 1rem}
a.btn.btn-warning {
    padding: 1rem;
    color: #ffffff;
    background-color: #a9060b;
    border-color: #ffc107;
}
.btn-warning:hover{color: #ffffff;background-color: #ff2b00;border-color: #ff2b00;border-radius: 1px}
ul.row {
    padding: 1rem;
}
@media (min-width: 542px)
{
  .col-md-4 {
    max-width: 100%;
}

}
button.chat {
    border: none;
    background-color: brown;
    color: white;
    border-radius: 11%;
}
button.chat:hover {
    border: none;
    background-color: rgb(236, 0, 0);
    color: rgb(255, 255, 255);
    border-radius: 11%;
}
</style>