<template>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">


<title>Chi Tiết Sản Phẩm</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://netdna.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">

	</head>
    <app-header @cartUpdated="updateCart"></app-header>

    <br>    <br>    <br>
<body>

<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" />
<div class="container">

<div class="product-content product-wrap clearfix product-deatil">
<div class="row">
<div class="col-md-5 col-sm-12 col-xs-12">
<div class="product-image">
<div id="myCarousel-2" class="carousel slide">
<ol class="carousel-indicators">
<li data-target="#myCarousel-2" data-slide-to="0" class></li>
<li data-target="#myCarousel-2" data-slide-to="1" class="active"></li>
<li data-target="#myCarousel-2" data-slide-to="2" class></li>
</ol>
<div class="carousel-inner" v-if="product">
<div class="item active">
    <h3 class="price-sale">

    <span class="discount-percent">{{product.saleOff}}%</span> 
    <span class="discount-text">OFF</span>


</h3>

    <img :src="baseUrl + '/files/' + product.images"  alt="" class="img-responsive" />
    
</div>
<br>
<div class="tab-des">

<strong>Mô Tả Sản Phẩm</strong>
<br>
{{ product.description }}




</div>


</div>



</div>


</div>

<div class="description description-tabs">
    <a v-if="reviews.length > 0" class="viewReviews" href="">Xem tất cả đánh giá </a>
<div id="myTabContent" class="tab-content">
    







</div>
</div>

</div>


<div class="ok" v-if="product">
<h2 class="name">
{{ product.name }}
<br>
<br>
<small>Đăng bởi <a href="javascript:void(0);">{{ product.seller.username }}</a></small>
<i class="fa fa-star fa-2x text-primary"></i>
<i class="fa fa-star fa-2x text-primary"></i>
<i class="fa fa-star fa-2x text-primary"></i>
<i class="fa fa-star fa-2x text-primary"></i>
<i class="fa fa-star fa-2x text-muted"></i>
<span class="fa fa-2x"><h5> 4.6</h5></span>
</h2>



<h3 class="price-container">
{{ product.price }}đ 
<span class="before">{{ product.beforePrice }}đ</span>


</h3>


<br>
<div class="row">
<button class="btn btn-white btn-default"><i class="fa fa-star"></i> Yêu thích</button>
<button class="btn btn-white btn-default" @click="chatWithUser(product.seller.username)" ><i class="fa fa-envelope"></i> Liên Hệ Người Bán</button>









</div>
<a v-if="product.quantityStock > 0"  @click="addToCart" class="btn btn-success">Thêm Vào Giỏ Hàng (Còn: {{ product.quantityStock }} Sản Phẩm)</a>
<a v-if="product.quantityStock <= 0"  class="btn btn-success">Hết Hàng</a>




</div>


<div class="tab">
<div class="chat-body no-padding profile-message">
<ul>
    <li class="message" v-for="review in reviews" :key="review.id">


 <img :src=" baseUrl + '/files/' + review.user.avatar" class="online" />
<span class="message-text">
<a class="username">{{ review.user.username }}
<!-- <span class="badge" v-if="review.user > 0"> VIP{{ review.user.vip }}</span> -->
<span class="pull-right">
<i class="fa fa-star fa-2x text-primary"></i>
<i class="fa fa-star fa-2x text-primary"></i>
<i class="fa fa-star fa-2x text-primary"></i>
<i class="fa fa-star fa-2x text-primary"></i>
<i class="fa fa-star fa-2x text-muted"></i>
</span>
</a>
<br>
{{ review.comment }}
</span>
<img :src=" baseUrl + '/files/' + review.imagesReview" class="online" />

<ul class="list-inline font-xs">
<li>
<a href="javascript:void(0);" class="text-info"><i class="fa fa-thumbs-up"></i> {{ review.rating }} Sao</a>
</li>
<li class="pull-right">
<small class="text-muted pull-right ultra-light"> {{ formatCreatedAt(review.createAt) }} </small>
</li>
</ul>
<hr>
</li>


</ul>
</div>
</div>
</div>
</div>

</div>
</body>
</html>
<app-footer></app-footer>
</template>

<script>
import HelloWorld from '@/components/HelloWorld.vue'
import Footer from '@/views/support/Footer.vue';
import axios from 'axios';
import { baseWeb,baseURL } from '@/router/index';
import { mapGetters } from 'vuex';
import moment from 'moment';
import { toast } from 'vue3-toastify';
import { useRouter } from 'vue-router';
import 'vue3-toastify/dist/index.css';
import {ProductService} from '@/core/service/productservice';
import { AccountService } from '@/core/service/accountservice';
import Header from '@/views/support/Header.vue';
export default {
  name: 'ProductDetails',
  computed: {
   ...mapGetters(['loggedInUser'])
 },
created() {
  this.getInfo();
  this.getReviews();
 },
 data() {
   return {

    service: new ProductService(),
    accountService: new AccountService(),
     product: null,
     reviews:[],
     profile: '',
     baseUrl : baseURL,
     concac:'',
     cartProducts: [],
     token: localStorage.getItem('token'),
      productId : this.$route.params.id,

   };
 },
 methods: {
   updateDate(event) {
   this.user.date = event.target.value;
 },
  formatBirthDate(date) {
   if (!date) return ''; // Đảm bảo xử lý trường hợp ngày sinh không tồn tại
   return moment(date).format('YYYY-MM-DD');
 },

   formatCreatedAt(timestamp) {
     return moment(timestamp).format('DD/MM/YYYY HH:mm'); // Định dạng ngày tháng giờ phút theo ý muốn
 },

 chatWithUser(name){
    window.location.href= '/chat/' + name ;
 },
  getReviews() {
      this.service.getReviewsDemo(this.productId)
        .then(response => {   
          this.reviews = response.data.data;
        }) .catch(error => {
        });
    },

    getAllReviews() {
      this.service.getReviews(this.productId)
        .then(response => {   
          this.reviews = response.data.data;
        })   .catch(error => {
        });
    },


   addToCart() {
   if(!this.loggedInUser){
    toast.warning('Vui Lòng Đăng Nhập')
    return
   }
    this.service.addToCart(this.productId,this.token)
        .then(response => {   
            if (response.status === 200) {
                toast.success(response.data.message);
                this.cartProducts = Header.fetchCartItems(); 
            } 
        })
        .catch(error => {
            if(error.response){
                switch(error.response.status){
                case 401:
                    case 400:
                    toast.error(error.response.data.message);
                    break;
                }
            
            }
        });
},
loadCart(){
    this.accountService.getCart(this.token)
    .then(cart => {
      this.cartProducts = cart; 
    })
    .catch(error => {
    });
},

 getInfo() {
    this.service.getProductById(this.productId)

        .then(response => {   
          this.product = response.data.data;
        })
        .catch(error => {
          toast.error('Lỗi!');
        });
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

.container{
    zoom: 130%;
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
    margin-right: auto;
    margin-left: auto;
}

.product-content {
    border: 1px solid #dfe5e9;
    margin-bottom: 20px;
    margin-top: 12px;
    background: #fff
}

.product-content .carousel-control.left {
    margin-left: 0
}

.product-content .product-image {
    background-color: #fff;
    display: block;
    min-height: 238px;
    overflow: hidden;
    position: relative
}

.product-content .product-deatil {
    border-bottom: 1px solid #dfe5e9;
    padding-bottom: 17px;
    padding-left: 16px;
    padding-top: 16px;
    position: relative;
    background: #fff
}

.product-content .product-deatil h5 a {
    color: #2f383d;
    font-size: 15px;
    line-height: 19px;
    text-decoration: none;
    padding-left: 0;
    margin-left: 0
}

.product-content .product-deatil h5 a span {
    color: #9aa7af;
    display: block;
    font-size: 13px
}

.product-content .product-deatil span.tag1 {
    border-radius: 50%;
    color: #fff;
    font-size: 15px;
    height: 50px;
    padding: 13px 0;
    position: absolute;
    right: 10px;
    text-align: center;
    top: 10px;
    width: 50px
}

.product-content .product-deatil span.sale {
    background-color: #21c2f8
}

.product-content .product-deatil span.discount {
    background-color: #71e134
}

.product-content .product-deatil span.hot {
    background-color: #fa9442
}

.product-content .description {
    font-size: 12.5px;
    line-height: 20px;
    padding: 10px 14px 16px 19px;
    background: #fff
}

.product-content .product-info {
    padding: 11px 19px 10px 20px
}

.product-content .product-info a.add-to-cart {
    color: #2f383d;
    font-size: 13px;
    padding-left: 16px
}

.product-content name.a {
    padding: 5px 10px;
    margin-left: 16px
}

.product-info.smart-form .btn {
    padding: 6px 12px;
    margin-left: 12px;
    margin-top: -10px
}

.product-entry .product-deatil {
    border-bottom: 1px solid #dfe5e9;
    padding-bottom: 17px;
    padding-left: 16px;
    padding-top: 16px;
    position: relative
}

.product-entry .product-deatil h5 a {
    color: #2f383d;
    font-size: 15px;
    line-height: 19px;
    text-decoration: none
}


@media only screen and (max-width: 767px) {
    .row {
        width: 90%;
    display: -ms-flexbox;
    display: flex;
    -ms-flex-wrap: wrap;
    flex-wrap: wrap;
    margin-right: -15px;
    margin-left: -15px;
}

   
}
.btn-success:not(:disabled):not(.disabled).active, .btn-success:not(:disabled):not(.disabled):active, .show>.btn-success.dropdown-toggle {
    color: #fff;
    background-color: #730000;
    border-color: #ffffff;
}

.product-entry .product-deatil h5 a span {
    color: #9aa7af;
    display: block;
    font-size: 13px
}

.load-more-btn {
    background-color: #21c2f8;
    border-bottom: 2px solid #037ca5;
    border-radius: 2px;
    border-top: 2px solid #0cf;
    margin-top: 20px;
    padding: 9px 0;
    width: 100%
}

.product-block .product-deatil p.price-container span,
.product-content .product-deatil p.price-container span,
.product-entry .product-deatil p.price-container span,
.shipping table tbody tr td p.price-container span,
.shopping-items table tbody tr td p.price-container span {
    color: #21c2f8;
    font-family: Lato, sans-serif;
    font-size: 24px;
    line-height: 20px
}
.col-sm-12.col-md-6.col-lg-6 {
    margin-left: 22%;
}
span.before {
    text-decoration: line-through;
    font-size: 18px;
    color:#616161;
    margin-left: 1%;
}
.price-sale {
    display: flex;
    flex-direction: column;
    position: fixed;
    color: rgb(170 0 0);
    background-color: currentColor;
    font-size: 2.2rem;
    font-weight: 00;
    border-bottom-right-radius: 14px;
}

.discount-percent, .discount-text {
    width: 13%;
    margin-left: 3%;

    text-align: center;
    color: #dddddd;
}


.product-info.smart-form .rating label {
    margin-top: 0
}

.product-wrap .product-image span.tag2 {
    position: absolute;
    top: 10px;
    right: 10px;
    width: 36px;
    height: 36px;
    border-radius: 50%;
    padding: 10px 0;
    color: #fff;
    font-size: 11px;
    text-align: center
}
.form-control{
    align-items: center;
}
.product-wrap .product-image span.sale {
    background-color: #57889c
}

.product-wrap .product-image span.hot {
    background-color: #a90329
}

.shop-btn {
    position: relative
}

.shop-btn>span {
    background: #a90329;
    display: inline-block;
    font-size: 10px;
    box-shadow: inset 1px 1px 0 rgba(0, 0, 0, .1), inset 0 -1px 0 rgba(0, 0, 0, .07);
    font-weight: 700;
    border-radius: 50%;
    padding: 2px 4px 3px!important;
    text-align: center;
    line-height: normal;
    width: 19px;
    top: -7px;
    left: -7px
}

.description-tabs {
    padding: 30px 0 5px!important
}

.description-tabs .tab-content {
    padding: 10px 0
}
.item.active {
    border: 1px solid #616161;
}
.ok {
    padding: 0%;
    margin-left: 8%;
    width: 50%;
}
.product-deatil {
    padding: 30px 30px 50px
}

.product-deatil hr+.description-tabs {
    padding: 0 0 5px!important
}

.product-deatil .carousel-control.left,
.product-deatil .carousel-control.right {
    background: none!important
}

.product-deatil .glyphicon {
    color: #3276b1
}


form.well.padding-bottom-10 {
    padding: 0px;
    margin: -4%;
}
li.message {
    width: 51%;
    zoom: 85%;
}
a.btn.btn-success {
    justify-content: center;
    display: flex;
    width: 105%;
    margin-left: -4%;
    align-items: center;
    background-color: rgb(187, 0, 0);
    border: 1px solid;
    color: white;
    font-size: 12px;
}

a.btn.btn-success:hover {
    background-color: rgb(68, 0, 0);
    color: white;

}

.product-deatil .product-image {
    border-right: none!important
}

.product-deatil .name {
    margin-top: 0;
    margin-bottom: 0
}

.product-deatil .name small {
    display: block
}

.product-deatil .name a {
    margin-left: 0
}

.product-deatil .price-container {
    font-size: 24px;
    margin: 0;
    font-weight: 300
}

.product-deatil .price-container small {
    font-size: 12px
}

.product-deatil .fa-2x {
    font-size: 16px!important
}

.product-deatil .fa-2x>h5 {
    font-size: 12px;
    margin: 0
}

.product-deatil .fa-2x+a,
.product-deatil .fa-2x+a+a {
    font-size: 13px
}

.profile-message ul {
  list-style: none ;  
}

.product-deatil .certified {
    margin-top: 10px
}

.product-deatil .certified ul {
    padding-left: 0
}

.product-deatil .certified ul li:not(first-child) {
    margin-left: -3px
}

.product-deatil .certified ul li {
    display: inline-block;
    background-color: #f9f9f9;
    border: 1px solid #ccc;
    padding: 13px 19px
}

.product-deatil .certified ul li:first-child {
    border-right: none
}

.product-deatil .certified ul li a {
    text-align: left;
    font-size: 12px;
    color: #6d7a83;
    line-height: 16px;
    text-decoration: none
}
.product-image {
    width: 100%;
}
.product-deatil .certified ul li a span {
    display: block;
    color: #21c2f8;
    font-size: 13px;
    font-weight: 700;
    text-align: center
}
span.message-text {
    margin: 6px;
}
.product-deatil .message-text {
    width: calc(100% - 70px)
}

@media only screen and (min-width:1024px) {
    .product-content div[class*=col-md-4] {
        padding-right: 0
    }
    .product-content div[class*=col-md-8] {
        padding: 0 13px 0 0
    }
    .product-wrap div[class*=col-md-5] {
        padding-right: 0
    }
    .product-wrap div[class*=col-md-7] {
        padding: 0 13px 0 0
    }
    .product-content .product-image {
        border-right: 1px solid #dfe5e9
    }
    .product-content .product-info {
        position: relative
    }
}
button.btn.btn-white.btn-default {
    width: 49%;
}

.message img.online {
    width:40px;
    height:40px;
}
button.btn.btn-sm.btn-primary.pull-right {
    margin-top: 10px;
    margin-right: 72%;
}
    </style>
