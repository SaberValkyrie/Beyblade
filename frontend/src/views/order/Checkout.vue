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
            <br>
            <h5 class="card-title mb-3" style="color: orangered;font-weight: bold;">Địa Chỉ Nhận Hàng
       
          </h5>
            <div class="row">
              <div class="col-lg-6 mb-3">
                <!-- Default checked radio -->
                <div class="form-check h-100 border rounded-3">
                  <div class="p-3">
                    <input class="form-check-input"  @click="setAddressOrder(defaultA)"
                     type="radio" name="flexRadioDefault1" id="flexRadioDefault2" checked />
                    <label class="form-check-label" for="flexRadioDefault2">
          
                      <small class="text">         
                                       {{ defaultA.fullname }}</small><br>
                       <small class="text">
                       ☎ {{ defaultA.phone }}</small><br>
                      <small class="text-muted">🏠 {{ defaultA.addressDetails }}</small>
                      <small class="text-muted">--{{ defaultA.phuongXa }}-{{ defaultA.quanHuyen }}-{{ defaultA.thanhPho }}</small><br>


                    </label>
                  </div>
                </div>
              </div>
              <div class="col-lg-6 mb-3">


                <!-- Default radio -->
                <div class="form-check h-100 border rounded-3">
                  <div class="p-3">
                    <input class="form-check-input"
                    @click="setAddressOrder(basicA)"
                     type="radio" name="flexRadioDefault1" id="flexRadioDefault2" />
                    <label class="form-check-label" for="flexRadioDefault2">
              
                      <small class="text">         
                                       {{ basicA.fullname }}</small><br>
                       <small class="text">
                       ☎ {{ basicA.phone }}</small><br>
                      <small class="text-muted">🏠 {{ basicA.addressDetails }}</small>
                      <small class="text-muted">--{{ basicA.phuongXa }}-{{ basicA.quanHuyen }}-{{ basicA.thanhPho }}</small><br>

                    </label>
                  </div>
                </div>
              </div>

            </div>
            <hr>
            <br>
       
            <h5 class="card-title mb-3" style="color: orangered;font-weight: bold;">Sản Phẩm Đã Chọn</h5>

        <div class="item"  v-for="cart in productBuy" :key="cart.seller">
  
          <hr style="font-size: 100;color: beige;"> 
          
          Người Bán :
          <button class="chat" @click="chat(cart.seller)"> ✉ </button>

          <span class="" style="font-weight: 300;">{{cart.seller}} </span>
          <span></span>
       <div class="d-flex align-items-center mb-4" v-for="details in cart.cartDetails" :key="details.id" >

  <div class="me-3 position-relative">
   
    <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill badge-danger">
      X{{ details.quantityCart }}
    </span>
    <img :src="baseUrl + '/files/' + details.product.images"
    style="    height: 7rem;
    width: 7rem;" class="img-sm rounded border" />
  </div>
  <div class="">
    <a >
     {{ details.product.name }} -
     <span style="color: crimson;font-weight: 300;"> ₫{{ details.product.price * details.quantityCart }} </span>
     <br>
     
  
    </a>
  </div>
</div> 
</div>

            <hr />

            <br>
            <h5 class="card-title mb-3" style="color: orangered;font-weight: bold;">Thông tin vận chuyển</h5>

            <div class="row mb-3">
              <div class="col-lg-4 mb-3">
                <!-- Default checked radio -->
                <div class="form-check h-100 border rounded-3">
                  <div class="p-3">
                    <input @click="setShipping(25000,'Nhanh')" class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault1" checked />
                    <label class="form-check-label" for="flexRadioDefault1">

                      <i class="fas fa-truck fa-fw fa-2x fa-beat-fade" style="color: var(--example-color); --fa-animation-duration: 2s;"></i>
                     <br>
                      Vận Chuyển Nhanh
                      <br> 
                      <small class="text-muted">Nhận Hàng Sau 3-4 Ngày </small>
                    </label>
                  </div>
                </div>
              </div>
              <div class="col-lg-4 mb-3">
                <!-- Default radio -->
                <div class="form-check h-100 border rounded-3">
                  <div class="p-3">
                    <input @click="setShipping(15000,'Tiết Kiệm')"  class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault2" />
                    <label class="form-check-label" for="flexRadioDefault2">
                      <i class="fas fa-motorcycle fa-fw fa-2x fa-beat-fade" style="color: var(--example-color); --fa-animation-duration: 2s;"></i>                     <br>
                      Vận Chuyển Tiết Kiệm <br />
                      <small class="text-muted">Nhận Hàng Sau 5-7 Ngày </small>
                    </label>
                  </div>
                </div>
              </div>
              <div class="col-lg-4 mb-3">
                <!-- Default radio -->
                <div class="form-check h-100 border rounded-3">
                  <div class="p-3">
                    <input @click="setShipping(0,'Người bán tự gửi')"   class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault3" />
                    <label class="form-check-label" for="flexRadioDefault3">
                      <i class="fas fa-user fa-fw fa-2x fa-beat-fade" style="color: var(--example-color); --fa-animation-duration: 2s;"></i>
                      <br>
                      Người Bán Tự Gửi <br />
                      <small class="text-muted">Liên Hệ Với Shop </small>
                    </label>
                  </div>
                </div>
              </div>
            </div>
        








<br>
<hr>


<br>
 <h5 class="card-title mb-3" style="color: orangered;font-weight: bold;">Phương Thức Thanh Toán</h5>

 
            <div class="row mb-3">
              <div class="col-lg-4 mb-3">
                <!-- Default checked radio -->
                <div class="form-check h-100 border rounded-3">
                  <div class="p-3">
                    <input @click="setPaymentMethod('COD')" class="form-check-input" type="radio" name="paymentMethod" id="paymentMethod" checked />
                    <label class="form-check-label" for="flexRadioDefault1">
                      <i class="fas fa-handshake fa-fw fa-2x fa-beat-fade" style="color: var(--example-color); --fa-animation-duration: 2s;"></i>
                      <br />
                      <small class="text-muted">Thanh Toán Khi Nhận Hàng </small>
                    </label>
                  </div>
                </div>
              </div>
              <div class="col-lg-4 mb-3">
                
                <!-- Default radio -->
                <div class="form-check h-100 border rounded-3">
                  
                  <div class="p-3">
                    
                    <input @click="setPaymentMethod('Coin')"  class="form-check-input" type="radio" name="paymentMethod" id="paymentMethod" />
                   
                    <label class="form-check-label" for="flexRadioDefault2">
                      <i class="fas fa-coins fa-fw fa-2x fa-beat-fade" style="color: var(--example-color); --fa-animation-duration: 4s;"></i>

                      <br />
                      <small class="text-muted">Thanh Toán bằng Coin </small>
                    </label>
                    
                  </div>
                </div>
              </div>



              
              <div class="col-lg-4 mb-3">
                <!-- Default radio -->
                <div class="form-check h-100 border rounded-3">
                  <div class="p-3">
                    <input @click="setPaymentMethod('Chuyển Khoản')"   class="form-check-input" type="radio" name="paymentMethod" id="paymentMethod" />
                    <label class="form-check-label" for="flexRadioDefault3">
                      <i class="fas fa-qrcode fa-fw fa-2x fa-beat-fade" style="color: var(--example-color); --fa-animation-duration: 2s;"></i>
                       <br />
                      <small class="text-muted">Chuyển khoản cho ShopPlaza </small>
                    </label>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- Checkout -->
      </div>
      <div class="col-xl-4 col-lg-4 d-flex justify-content-center justify-content-lg-end">
        <div class="ms-lg-4 mt-4 mt-lg-0" style="max-width: 320px;">
                 <h5 class="card-title mb-3" style="color: orangered;font-weight: bold;">Thanh Toán</h5>

          <div class="d-flex justify-content-between">
            <p class="mb-2">Tổng Tiền Sản Phẩm:</p>
            <p class="mb-2" style="color:black">₫{{ totalProductPrice }}</p>
          </div>
       
          <div class="d-flex justify-content-between">
            <p class="mb-2">Phí Vận Chuyển:</p>
            <p class="mb-2" style="color: black" >+ ₫{{ shippingCost }}</p>
          </div>
          <div v-if="codeApply && codeApply.percent > 0" class="d-flex justify-content-between">
            <p class="mb-2">Số Tiền Được Giảm:</p>
            <p class="mb-2 " style="color: green"  >- ₫{{ saleOff }}</p>
          </div>
          <div v-if="codeApply && codeApply.percent > 0" class="d-flex justify-content-between">
            <p class="mb-2">% Giảm Giá</p>
            <p class="mb-2" style="color: black" >{{ codeApply.percent }}%</p>
          </div>
          <hr />
         
          <div class="input-group mt-3 mb-4">
            <input type="text" class="form-control border" v-model="code" name="" placeholder="Nhập mã giảm giá (nếu có)" />
   
            <button class="apdung" style="color: white" @click="searchCode(code)"> ➔</button>

          </div>
        

          <hr />

          <div class="d-flex justify-content-between">
            <p class="mb-2">Tổng Tiền:</p>
            <p class="mb-2 fw-bold" style="color:brown">₫{{ totalPrice }}</p>
          </div>
          <h6 class="text-dark my-4"></h6>
          <div class="d-flex align-items-center mb-4">
          
          </div>
          <div class="float">
            <button class="btn btn-success shadow-0 border" @click="placeOrder">Đặt Hàng</button>
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
 ...mapGetters(['productBuy']),
 ...mapGetters(['totalProductPrice']),
},
      name: 'Checkout',
      components: {
        'app-header': Header,
        'app-footer': Footer,
      },
       data(){
      return{
        orderService : new OrderService(),
        addressUser:[], //full
        defaultA:{},//mac dinh
        basicA: {}, //thuong
        shippingCost: 25000,
        service: new AccountService(),
        ListItem:[],
        addressOrder:{},//địa chỉ order
        code: '',
        method: 'Nhanh',
        paymentMethod: 'COD',
        codeApply:{},
        totalPrice: 0,
        saleOff: 0,
        cs : false,
        token: localStorage.getItem('token'),
        baseUrl : baseURL,
        accountName: 'Shop Plaza',
      addInfo: '',
      stk: '0383087656',
      order:true
      }
     
    },
    created(){
      this.loadTotal();
      this.getListAddress();
      if(this.productBuy == null){
        window.location.href = '/cart';
      }
    },
    methods :{

      setMethod(m){
        this.method = m;
      },
      setPaymentMethod(m){
        this.paymentMethod = m;
      },

      setAddressOrder(address){
      this.addressOrder = address;
      },

      loadTotal(){
        this.totalPrice = this.totalProductPrice - this.saleOff  + this.shippingCost;
      },

      goto(ad){

        window.location.href = "/" + ad;
      },

      sale(price){
       this.saleOff = price;
      },
      setShipping(price,m){
       this.shippingCost = price;
       this.method = m;
       this.loadTotal();
      },
      chat(name){
    window.location.href = '/chat/' + name;
  },
      truncatedProductName(name) {
    const max = 70;
          if (name.length > max) {
              return name.substring(0, max) + '...';
          }
          return name;
      },

      getListAddress() {
  this.service.getAddress(this.token).then(response => {
     this.addressUser = response; 
     this.defaultA = response.find(address => address.isDefault === true);
     this.basicA = response.find(address => address.isDefault === false);
     this.addressOrder =  this.defaultA;
    }).catch(error => { }); 
     },
     
      custom(status){
      this.cs = status;
      },


      placeOrder(){

if(this.order == false){
  toast.info('Bạn đã đặt đơn hàng này')
  return;
}
        const addressInfo = {
    fullname: this.addressOrder.fullname,
    phone: this.addressOrder.phone,
    phuongXa: this.addressOrder.phuongXa,
    quanHuyen: this.addressOrder.quanHuyen,
    thanhPho: this.addressOrder.thanhPho,
    addressDetails: this.addressOrder.addressDetails
  };
  const orderItems = this.productBuy.map(cart => {
    return {
      seller: cart.seller,
      avatarSeller: cart.avatarSeller,
      cartDetails: cart.cartDetails.map(item => {
        return {
          id: item.id,
          user: item.user,
          product: item.product,
          quantityCart: item.quantityCart,
          createdAt: item.createdAt
        };
      })
    };
  });
  const voucherId = this.codeApply ? this.codeApply.id : 0;
  const orderRequest = {
    orderItems: orderItems,
    addressInfo: addressInfo,
    voucher: voucherId,
    ship: this.shippingCost
  };
  
        this.orderService.order(this.token,this.paymentMethod,this.method,orderRequest).then(res =>{
          toast.success(res.data.message)
         
          this.order = false;
          localStorage.setItem('productBuy',null);

          setTimeout(() => {
          window.location.href = "/order/all";
				}, 1500);

        }).catch(error => {
          toast.warning(error.response.data.message)
         }); 
      },



    searchCode(code){
      if(code != ''){
        this.orderService.findVoucher(code,this.token).then(res => {
          this.codeApply = res.data.data;
          this.saleOff = this.totalProductPrice * this.codeApply.percent / 100;
        this.code = '';
        toast.success(res.data.message);
        this.loadTotal()
        }).catch(error => {
          toast.warning(error.response.data.message)
          this.codeApply = {}
          this.loadTotal()
         }); 
      }else{
        toast.warning('Vui lòng nhập mã nếu có');
      }
    },
    }
    }
   
    </script>
    <style scoped>
    .apdung {
      border: none;
    background-color:black;
 
}
.check{
  border: none;
}
.apdung:hover {
      border: none;
    background-color: rgb(2, 0, 0)
 
}
/* Định nghĩa màu đỏ đậm (brown) */
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
.chat:hover{
  border: none;
    background-color: #b90000;
    color: #cccccc;
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
    /* position: fixed; */
    /* z-index: 9999; */
    /* top: 50%; */
    zoom:50%;
    left: 50%;
    /* transform: translate(-50%, -50%); */
    /* box-shadow: 0 0 100rem rgb(0 0 0 / 100%); */
}
/* .close {
    position: fixed;
    z-index: 10000;
    top: 12%;
    background-color: #b90000;
    left: 65%;
    transform: translate(-50%, -50%);
    box-shadow: 0 0 100rem rgb(0 0 0 / 100%);
} */

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
