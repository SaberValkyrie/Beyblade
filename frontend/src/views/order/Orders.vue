<template>
 
  <app-header></app-header>

  <!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
<div class="container">
<div class="row">

    
<div class="col-md-12">
   
    <select class="form-select"  
        data-trigger="true" 
        name="choices-single-filter-orderby"
        id="choices-single-filter-orderby"
        aria-label="Default select example"
        @change="changeStatus($event.target.value)"
>
<option value="">Lọc Trạng Thái</option>
    <option value="0">Tất Cả</option>
    <option value="1">Chờ Thanh Toán</option>
    <option value="2">Chờ Xác Nhận</option>
    <option value="3">Đang Gói Hàng</option>
    <option value="4">Đang Vận Chuyển</option>
    <option value="5">Đang Giao Hàng</option>
    <option value="6">Hoàn Thành</option>
    <option value="7">Khiếu Nại</option>
    <option value="8">Đã Hủy Đơn</option>
</select>




<div id="content" class="content content-full-width">

<div class="profile-content">

<div class="tab-content p-0">

<div class="tab-pane fade active show" id="profile-post">
<ul  class="timeline" v-for="order in paginatedOrders" >
    
<li>

<div class="timeline-time">
<span class="date">{{ formatDay(order.date) }}</span>
<span class="time">{{ formatHour(order.date) }}</span>
</div>


<div class="timeline-icon">
<a href="javascript:;">&nbsp;</a>
</div>


<div class="timeline-body" >
<div class="timeline-header">
<span class="userimage">
    <img :src="baseUrl + '/files/' +  order.seller.avatar" alt>
</span>

<span class="username"><a href="javascript:;">Người Bán: {{ order.seller.username }}</a> <small></small></span>
<h3 class="pull-right">Tổng Tiền : 
    <span class="success">
        {{ order.details.total + order.details.ship }} VNĐ
    </span>
</h3>
</div>
<div class="timeline-content">
<div>
    <span class="">#{{ order.details.code }}</span>

    <span class="pull-right">{{ getS(order.status) }}</span>
</div>
<br>
<div class="action">

  <button v-if="order.status == 1" class="comfirm" @click="goto('payment/' + order.orderId +'/'+ order.key)">Thanh Toán</button>
<button v-if="order.status == 5" class="comfirm"  @click="updateStatus(order.key,order.orderId,'end')" >Xác Nhận</button>
<button v-if="order.status == 5" class="cancel"  @click="updateStatus(order.key,order.orderId,'return')">Trả Hàng</button>
<a v-if="order.status == 4" >Hàng Đang Vận Chuyển</a>
<button v-if="order.status == 1 || order.status == 2" class="cancel" @click="updateStatus(order.key,order.orderId,'cancel')">Hủy Đơn</button>
<button @click="goto('order/'+ order.orderId + '/' + order.key)" class="details">Chi Tiết</button>
<button v-if="order.status == 6 && !order.isRate" class="comfirm" @click="goto('review/' + order.orderId + '/' + order.key)">Đánh Giá</button>


</div>

</div>



</div>

</li>

</ul>


<div class="row"  v-if="orders.length > 0">
        <div class="mt-4 pt-2 col-lg-12">
            <nav aria-label="Page navigation example">
  <ul class="pagination justify-content-center">
    <li class="page-item" :class="{ disabled: currentPage === 1 }">
      <a class="page-link" @click="prevPage" tabindex="-1">&laquo;</a>
    </li>
    <li class="page-item" v-for="page in totalPages" :key="page" :class="{ active: currentPage === page }">
      <a class="page-link" @click="goToPage(page)">{{ page }}</a>
    </li>
    <li class="page-item" :class="{ disabled: currentPage === totalPages }">
      <a class="page-link" @click="nextPage">&raquo;</a>
    </li>
  </ul>
</nav>

        </div>
        </div>

</div>
</div>
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
import Header from '@/views/support/Header.vue';
import Footer from '@/views/support/Footer.vue';
import { baseWeb,baseURL } from '@/router/index';
import { toast } from 'vue3-toastify';
import { mapGetters } from 'vuex';
import { AccountService } from '@/core/service/accountservice';
import { OrderService } from '@/core/service/orderservice.js';
import moment from 'moment';

export default {
  name: 'Orders',
  components: {
    'app-header': Header,
    'app-footer': Footer,
  },
  data() {
    return {
      orderService: new OrderService(),
      service: new AccountService(),
      orders: [],
      token: localStorage.getItem('token'),
      baseUrl: baseURL,
      loggedInUser:localStorage.getItem('loggedInUser'),
      status: 0,
      currentPage: 1,
      pageSize: 3, // Số lượng đơn hàng trên mỗi trang
      type:'',
    };
  },
  created() {
    this.getst();
    this.getOrder();

  },
  computed: {
      // Calculate the orders to display on the current page
  paginatedOrders() {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    return this.orders.slice(startIndex, endIndex);
  },
  // Calculate the total number of pages
  totalPages() {
    return Math.ceil(this.orders.length / this.pageSize);
  },
  },
  methods: {
    getst(){
      switch(this.$route.params.status){
        case 'all':
          this.status = 0;
          break

          case 'pay':
          this.status = 1;
          break

          case 'pending':
          this.status = 2;
          break

          
          case 'packing':
          this.status = 3;
          break

          
          case 'shipping':
          this.status = 4;
          break

          
          case 'sending':
          this.status = 5;
          break


          case 'success':
          this.status = 6;
          break
        
          case 'return':
          this.status = 7;
          break


          
          case 'cancel':
          this.status = 8;
          break
        
      }
    },
    changeStatus(status) {
        let routeName = '';
        switch(status) {
          case '0':
                routeName = 'all';
                break;
            case '1':
                routeName = 'pay';
                break;
            case '2':
                routeName = 'pending';
                break;
            case '3':
                routeName = 'packing';
                break;
            case '4':
                routeName = 'shipping';
                break;
            case '5':
                routeName = 'sending';
                break;
            case '6':
                routeName = 'success';
                break;
            case '7':
                routeName = 'return';
                break;
            case '8':
                routeName = 'cancel';
                break;
        }
        window.location.href = '/order/' + routeName;
    },

    
    getS(id){
    switch(id){
        case 1:
            return "Chờ Thanh Toán";
            case 2:
            return "Chờ Xác Nhận";
            case 3:
            return "Đang Gói Hàng";
            case 4:
            return "Đang Vận Chuyển";
            case 5:
            return "Đang Giao Hàng";
            case 6:
            return "Hoàn Thành";  
            case 7:
            return "Khiếu Nại";    
            case 8:
            return "Đã Hủy Đơn";      
    }
},

    getStatus() {
      this.orderService.getStatusByID(this.status).then(res => {
        this.s = res.data.data;
      });
    },
    formatCreatedAt(timestamp) {
      return moment(timestamp).format('DD/MM/YYYY HH:mm');
    },
    
    goto(ad){
  
  window.location.href = "/" + ad;
},
    paginatedProducts() {
        const startIndex = (this.currentPage - 1) * this.itemsPerPage;
        const endIndex = startIndex + this.itemsPerPage;
        return this.orders.slice(startIndex, endIndex);
      },

      updateStatus(key,id,type){
this.type = type
this.orderService.update(key,id,this.token,this.type).then(res =>{
            toast.success(res.data.message)
            this.orderService.changeStatus(res.data.data.status,0)

        }).catch(error => {
      toast.warning(error.response.message)
      console.log(error)
     }); 
},



formatDay(timestamp) {
     return moment(timestamp).format('DD/MM/YYYY'); // Định dạng ngày tháng giờ phút theo ý muốn
 },
 formatHour(timestamp) {
     return moment(timestamp).format('HH:mm'); // Định dạng ngày tháng giờ phút theo ý muốn
 },
    getOrder() {
      this.orderService
        .getAllOrdersByUser(this.token, this.status)
        .then(res => {
          this.orders = res.data.data;
          toast.success(res.data.message);
        })
        .catch(error => {
          toast.warning(error.response.data.message);
          setTimeout(() => {
          window.location.href = "/";
				}, 1500);
         
        });
    },
    // Go to a specific page
  goToPage(page) {
    this.currentPage = page;
  },
  // Navigate to the previous page
  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  },
  // Navigate to the next page
  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
  }
},
  }
};
</script>


<style type="text/css">
    	body{
    margin-top:20px;
    background:#eee;
}

.profile-header {
    position: relative;
    overflow: hidden
}
button.details {
    border: 1px solid rgb(198 198 198);
    border-radius: 7px;
    background-color: rgb(235, 235, 235);
    color: rgb(0, 0, 0);
}
button.details:hover {
    border: 1px solid whitesmoke;
    border-radius: 7px;
    background-color: brown;
    color: white;
}


button.cancel {
    border: 1px solid rgb(255, 255, 255);
    border-radius: 7px;
    background-color: rgb(94, 0, 0);
    color: rgb(250, 250, 250);
}
button.cancel:hover {
    border: 1px solid whitesmoke;
    border-radius: 7px;
    background-color: brown;
    color: white;
}

button.comfirm{
    border: 1px solid rgb(238, 238, 238);
    border-radius: 7px;
    background-color: rgb(0, 131, 39);
    color: rgb(255, 250, 250);
}
button.comfirm:hover {
    border: 1px solid whitesmoke;
    border-radius: 7px;
    background-color: brown;
    color: white;
}
.profile-header .profile-header-cover {
    background-image: url(https://bootdey.com/img/Content/bg1.jpg);
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0
}
span.success {
    color: forestgreen;
}

.profile-header .profile-header-cover:before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: linear-gradient(to bottom, rgba(0, 0, 0, 0) 0, rgba(0, 0, 0, .75) 100%)
}

.profile-header .profile-header-content {
    color: #fff;
    padding: 25px
}

.profile-header-img {
    float: left;
    width: 120px;
    height: 120px;
    overflow: hidden;
    position: relative;
    z-index: 10;
    margin: 0 0 -20px;
    padding: 3px;
    border-radius: 4px;
    background: #fff
}

.profile-header-img img {
    max-width: 100%
}

.profile-header-info h4 {
    font-weight: 500;
    color: #fff
}

.profile-header-img+.profile-header-info {
    margin-left: 140px
}

.profile-header .profile-header-content,
.profile-header .profile-header-tab {
    position: relative
}

.b-minus-1,
.b-minus-10,
.b-minus-2,
.b-minus-3,
.b-minus-4,
.b-minus-5,
.b-minus-6,
.b-minus-7,
.b-minus-8,
.b-minus-9,
.b-plus-1,
.b-plus-10,
.b-plus-2,
.b-plus-3,
.b-plus-4,
.b-plus-5,
.b-plus-6,
.b-plus-7,
.b-plus-8,
.b-plus-9,
.l-minus-1,
.l-minus-2,
.l-minus-3,
.l-minus-4,
.l-minus-5,
.l-minus-6,
.l-minus-7,
.l-minus-8,
.l-minus-9,
.l-plus-1,
.l-plus-10,
.l-plus-2,
.l-plus-3,
.l-plus-4,
.l-plus-5,
.l-plus-6,
.l-plus-7,
.l-plus-8,
.l-plus-9,
.r-minus-1,
.r-minus-10,
.r-minus-2,
.r-minus-3,
.r-minus-4,
.r-minus-5,
.r-minus-6,
.r-minus-7,
.r-minus-8,
.r-minus-9,
.r-plus-1,
.r-plus-10,
.r-plus-2,
.r-plus-3,
.r-plus-4,
.r-plus-5,
.r-plus-6,
.r-plus-7,
.r-plus-8,
.r-plus-9,
.t-minus-1,
.t-minus-10,
.t-minus-2,
.t-minus-3,
.t-minus-4,
.t-minus-5,
.t-minus-6,
.t-minus-7,
.t-minus-8,
.t-minus-9,
.t-plus-1,
.t-plus-10,
.t-plus-2,
.t-plus-3,
.t-plus-4,
.t-plus-5,
.t-plus-6,
.t-plus-7,
.t-plus-8,
.t-plus-9 {
    position: relative!important
}

.profile-header .profile-header-tab {
    background: #fff;
    list-style-type: none;
    margin: -10px 0 0;
    padding: 0 0 0 140px;
    white-space: nowrap;
    border-radius: 0
}

.text-ellipsis,
.text-nowrap {
    white-space: nowrap!important
}

.profile-header .profile-header-tab>li {
    display: inline-block;
    margin: 0
}

.profile-header .profile-header-tab>li>a {
    display: block;
    color: #929ba1;
    line-height: 20px;
    padding: 10px 20px;
    text-decoration: none;
    font-weight: 700;
    font-size: 12px;
    border: none
}

.profile-header .profile-header-tab>li.active>a,
.profile-header .profile-header-tab>li>a.active {
    color: #242a30
}

.profile-content {
    padding: 25px;
    border-radius: 4px
}

.profile-content:after,
.profile-content:before {
    content: '';
    display: table;
    clear: both
}

.profile-content .tab-content,
.profile-content .tab-pane {
    background: 0 0
}

.profile-left {
    width: 200px;
    float: left
}

.profile-right {
    margin-left: 240px;
    padding-right: 20px
}

.profile-image {
    height: 175px;
    line-height: 175px;
    text-align: center;
    font-size: 72px;
    margin-bottom: 10px;
    border: 2px solid #E2E7EB;
    overflow: hidden;
    border-radius: 4px
}

.profile-image img {
    display: block;
    max-width: 100%
}

.profile-highlight {
    padding: 12px 15px;
    background: #FEFDE1;
    border-radius: 4px
}

.profile-highlight h4 {
    margin: 0 0 7px;
    font-size: 12px;
    font-weight: 700
}

.table.table-profile>thead>tr>th {
    border-bottom: none!important
}

.table.table-profile>thead>tr>th h4 {
    font-size: 20px;
    margin-top: 0
}

.table.table-profile>thead>tr>th h4 small {
    display: block;
    font-size: 12px;
    font-weight: 400;
    margin-top: 5px
}

.table.table-profile>tbody>tr>td,
.table.table-profile>thead>tr>th {
    border: none;
    padding-top: 7px;
    padding-bottom: 7px;
    color: #242a30;
    background: 0 0
}

.table.table-profile>tbody>tr>td.field {
    width: 20%;
    text-align: right;
    font-weight: 600;
    color: #2d353c
}

.table.table-profile>tbody>tr.highlight>td {
    border-top: 1px solid #b9c3ca;
    border-bottom: 1px solid #b9c3ca
}

.table.table-profile>tbody>tr.divider>td {
    padding: 0!important;
    height: 10px
}

.profile-section+.profile-section {
    margin-top: 20px;
    padding-top: 20px;
    border-top: 1px solid #b9c3ca
}

.profile-section:after,
.profile-section:before {
    content: '';
    display: table;
    clear: both
}

.profile-section .title {
    font-size: 20px;
    margin: 0 0 15px
}

.profile-section .title small {
    font-weight: 400
}

body.flat-black {
    background: #E7E7E7
}

.flat-black .navbar.navbar-inverse {
    background: #212121
}

.flat-black .navbar.navbar-inverse .navbar-form .form-control {
    background: #4a4a4a;
    border-color: #4a4a4a
}

.flat-black .sidebar,
.flat-black .sidebar-bg {
    background: #3A3A3A
}

.flat-black .page-with-light-sidebar .sidebar,
.flat-black .page-with-light-sidebar .sidebar-bg {
    background: #fff
}

.flat-black .sidebar .nav>li>a {
    color: #b2b2b2
}

.flat-black .sidebar.sidebar-grid .nav>li>a {
    border-bottom: 1px solid #474747;
    border-top: 1px solid #474747
}

.flat-black .sidebar .active .sub-menu>li.active>a,
.flat-black .sidebar .nav>li.active>a,
.flat-black .sidebar .nav>li>a:focus,
.flat-black .sidebar .nav>li>a:hover,
.flat-black .sidebar .sub-menu>li>a:focus,
.flat-black .sidebar .sub-menu>li>a:hover,
.sidebar .nav>li.nav-profile>a {
    color: #fff
}

.flat-black .sidebar .sub-menu>li>a,
.flat-black .sidebar .sub-menu>li>a:before {
    color: #999
}

.flat-black .page-with-light-sidebar .sidebar .active .sub-menu>li.active>a,
.flat-black .page-with-light-sidebar .sidebar .active .sub-menu>li.active>a:focus,
.flat-black .page-with-light-sidebar .sidebar .active .sub-menu>li.active>a:hover,
.flat-black .page-with-light-sidebar .sidebar .nav>li.active>a,
.flat-black .page-with-light-sidebar .sidebar .nav>li.active>a:focus,
.flat-black .page-with-light-sidebar .sidebar .nav>li.active>a:hover {
    color: #000
}

.flat-black .page-sidebar-minified .sidebar .nav>li.has-sub:focus>a,
.flat-black .page-sidebar-minified .sidebar .nav>li.has-sub:hover>a {
    background: #323232
}

.flat-black .page-sidebar-minified .sidebar .nav li.has-sub>.sub-menu,
.flat-black .sidebar .nav>li.active>a,
.flat-black .sidebar .nav>li.active>a:focus,
.flat-black .sidebar .nav>li.active>a:hover,
.flat-black .sidebar .nav>li.nav-profile,
.flat-black .sidebar .sub-menu>li.has-sub>a:before,
.flat-black .sidebar .sub-menu>li:before,
.flat-black .sidebar .sub-menu>li>a:after {
    background: #2A2A2A
}

.flat-black .page-sidebar-minified .sidebar .sub-menu>li:before,
.flat-black .page-sidebar-minified .sidebar .sub-menu>li>a:after {
    background: #3e3e3e
}
button {
    margin-right: 10px;
}
.flat-black .sidebar .nav>li.nav-profile .cover.with-shadow:before {
    background: rgba(42, 42, 42, .75)
}

.bg-white {
    background-color: #fff!important;
}
.p-10 {
    padding: 10px!important;
}
.media.media-xs .media-object {
    width: 32px;
}
.m-b-2 {
    margin-bottom: 2px!important;
}
.media>.media-left, .media>.pull-left {
    padding-right: 15px;
}
.media-body, .media-left, .media-right {
    display: table-cell;
    vertical-align: top;
}
select.form-control:not([size]):not([multiple]) {
    height: 34px;
}
.form-control.input-inline {
    display: inline;
    width: auto;
    padding: 0 7px;
}


.timeline {
    list-style-type: none;
    margin: 0;
    padding: 0;
    position: relative
}

.timeline:before {
    content: '';
    position: absolute;
    top: 5px;
    bottom: 5px;
    width: 5px;
    background: #2d353c;
    left: 20%;
    margin-left: -2.5px
}

.timeline>li {
    position: relative;
    min-height: 50px;
    padding: 20px 0
}

.timeline .timeline-time {
    position: absolute;
    left: 0;
    width: 18%;
    text-align: right;
    top: 30px
}

.timeline .timeline-time .date,
.timeline .timeline-time .time {
    display: block;
    font-weight: 600
}

.timeline .timeline-time .date {
    line-height: 16px;
    font-size: 12px
}

.timeline .timeline-time .time {
    line-height: 24px;
    font-size: 20px;
    color: #242a30
}

.timeline .timeline-icon {
    left: 15%;
    position: absolute;
    width: 10%;
    text-align: center;
    top: 40px
}

.timeline .timeline-icon a {
    text-decoration: none;
    width: 20px;
    height: 20px;
    display: inline-block;
    border-radius: 20px;
    background: #d9e0e7;
    line-height: 10px;
    color: #fff;
    font-size: 14px;
    border: 5px solid #2d353c;
    transition: border-color .2s linear
}

.timeline .timeline-body {
    margin-left: 23%;
    margin-right: 17%;
    background: #f8f8f8;
    position: relative;
    padding: 20px 25px;
    border-radius: 7px;
    box-shadow: 0 1px 25px #e0e0e0;
}
.timeline .timeline-body:hover {
    margin-left: 23%;
    margin-right: 17%;
    background: #ffffff;
    position: relative;
    padding: 20px 25px;
    border-radius: 7px;
    box-shadow: 0 1px 25px #e0e0e0;
}

.timeline .timeline-body:before {
    content: '';
    display: block;
    position: absolute;
    border: 10px solid transparent;
    border-right-color: #fff;
    left: -20px;
    top: 20px
}

.timeline .timeline-body>div+div {
    margin-top: 15px
}

.timeline .timeline-body>div+div:last-child {
    margin-bottom: -20px;
    padding-bottom: 20px;
    border-radius: 0 0 6px 6px
}

.timeline-header {
    padding-bottom: 10px;
    border-bottom: 1px solid #e2e7eb;
    line-height: 30px
}

.timeline-header .userimage {
    float: left;
    width: 34px;
    height: 34px;
    border-radius: 40px;
    overflow: hidden;
    margin: -2px 10px -2px 0
}

.timeline-header .username {
    font-size: 16px;
    font-weight: 600
}

.timeline-header .username,
.timeline-header .username a {
    color: #2d353c
}

.timeline img {
    max-width: 100%;
    display: block
}

.timeline-content {
    letter-spacing: .25px;
    line-height: 18px;
    font-size: 13px
}

.timeline-content:after,
.timeline-content:before {
    content: '';
    display: table;
    clear: both
}

.timeline-title {
    margin-top: 0
}

.timeline-footer {
    background: #fff;
    border-top: 1px solid #e2e7ec;
    padding-top: 15px
}

.timeline-footer a:not(.btn) {
    color: #575d63
}

.timeline-footer a:not(.btn):focus,
.timeline-footer a:not(.btn):hover {
    color: #2d353c
}

.timeline-likes {
    color: #6d767f;
    font-weight: 600;
    font-size: 12px
}

.timeline-likes .stats-right {
    float: right
}

.timeline-likes .stats-total {
    display: inline-block;
    line-height: 20px
}

.timeline-likes .stats-icon {
    float: left;
    margin-right: 5px;
    font-size: 9px
}

.timeline-likes .stats-icon+.stats-icon {
    margin-left: -2px
}

.timeline-likes .stats-text {
    line-height: 20px
}

.timeline-likes .stats-text+.stats-text {
    margin-left: 15px
}

.timeline-comment-box {
    background: #f2f3f4;
    margin-left: -25px;
    margin-right: -25px;
    padding: 20px 25px
}

.timeline-comment-box .user {
    float: left;
    width: 34px;
    height: 34px;
    overflow: hidden;
    border-radius: 30px
}

.timeline-comment-box .user img {
    max-width: 100%;
    max-height: 100%
}

.timeline-comment-box .user+.input {
    margin-left: 44px
}

.lead {
    margin-bottom: 20px;
    font-size: 21px;
    font-weight: 300;
    line-height: 1.4;
}

.text-danger, .text-red {
    color: #ff5b57!important;
}
.form-select {
    position: fixed;
    z-index: 9999;
    zoom: 153%;
    display: block;
    width: 15%;
    margin-top: 12rem;
    /* margin-left: -5rem;
     */
     right:0;
    padding: 0.375rem 2.25rem 0.375rem 0.75rem;
    -moz-padding-start: calc(0.75rem - 3px);
    font-size: 1rem;
    font-weight: 400;
    line-height: 1.5;
    color: #212529;
    background-color: #fff;
    background-repeat: no-repeat;
    background-position: right 0.75rem center;
    background-size: 16px 12px;
    border: 1px solid #ced4da;
    border-radius: 0.375rem;
    transition: border-color .15s ease-in-out, box-shadow .15s ease-in-out;
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
}
ul.pagination.justify-content-center {
    zoom: 130%;
}
    </style>
