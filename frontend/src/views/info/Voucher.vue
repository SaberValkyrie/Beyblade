<template>
  <!DOCTYPE html>
  <html lang="en">
  <head>
  <meta charset="utf-8">
  
  
  <title>Mã Giảm Giá</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
  </head>
  <body>
    <app-header></app-header>
    <br>    <br>    <br>    <br>    <br>
  <section class="container">
    
  <h1>Mã Giảm Giá</h1>
  <div class="menu">
    <select class="form-select"  
          data-trigger="true" 
          name="choices-single-filter-orderby"
          id="choices-single-filter-orderby"
          aria-label="Default select example"
          @change="setStatus($event.target.value)"
  >
  <option>Lọc Trạng Thái</option>
      <option value="khadung"> Khả Dụng</option>
      <option value="hethan">Hết Hạn</option>
      <option value="used">Đã Sử Dụng</option>
  </select>
  
  </div>
  
  <div v-for="(voucher, index) in Vouchers" :key="index" class="col-md-6">
   
  <article class="card1 fl-left"  >
  <section class="date" >
  <time datetime="23th feb">
  <span>Giảm {{ voucher.percent }}%</span>
  </time>
  </section>
  <section class="card1-cont">
  <h3>#{{ voucher.voucher_code }}</h3>
  <div class="even-date">
  <i class="fa fa-calendar"></i>
  <time>
  <span>  Ngày Hết Hạn: {{ formatCreatedAt(voucher.endTime) }}</span>
  </time>
  </div>
  <br>
  <div class="even-info">
  <i class="fa fa-map-marker"></i>

  <h5>
  Áp dụng cho đơn từ {{ voucher.giaToiThieu }}₫
  </h5>
  </div>
  <a href="#">Cạnh Tranh: {{ (voucher.countLeft) }}</a>
  </section>
  </article>
  
  </div>
  
  </section>
  
  </body>
  </html>
  
  </template>
  <script>
  import Header from '@/views/support/Header.vue';
  import Footer from '@/views/support/Footer.vue';
  import axios from 'axios';
  import { baseWeb,baseURL } from '@/router/index';
  import { mapGetters } from 'vuex';
  import moment from 'moment';
  import { toast } from 'vue3-toastify';
  import 'vue3-toastify/dist/index.css';
  import { AccountService } from '@/core/service/accountservice';
  
  export default {
    name: 'Profile',
    computed: {
      ...mapGetters(['loggedInUser']),
   },
  created() {
    this.status = 'khadung';
    this.getListVoucher();  
   },
   data() {
     return {
       baseUrl : baseURL,
       userInfo : {},
       defaultA:{},//mac dinh
       basicA: {}, //thuong
       service: new AccountService(),
       token: localStorage.getItem('token'),
       Vouchers:[],
       status: '',
     };
   },
   methods: {
    setStatus(status){
      this.status = status;
      this.getListVoucher();
    },
  
  
    getListVoucher() {
    this.service.getAllVoucher(this.token,this.status).then(response => {
       this.Vouchers = response; 
  
      }).catch(error => { 
        toast.error(error.response.data.message)
        this.Vouchers = []
      }); 
       },
  
     formatCreatedAt(timestamp) {
       return moment(timestamp).format('DD/MM/YYYY HH:mm'); // Định dạng ngày tháng giờ phút theo ý muốn
   },
  
    },
    components: {
      'app-header': Header,
      'app-footer': Footer,
    }
   
  }
  </script>
  <style type="text/css">
        @import url('https://fonts.googleapis.com/css?family=Oswald');
  * {
      margin: 0;
      padding: 0;
      border: 0;
      box-sizing: border-box
  }
  
  body {
      font-family: arial
  }
  
  .fl-left {
      float: left
  }
  
  .fl-right {
      float: right
  }
  
  h1 {
      text-transform: uppercase;
      font-weight: 900;
      border-left: 10px solid #fec500;
      padding-left: 10px;
      margin-bottom: 30px
  }
  
  .row {
      overflow: hidden
  }
  
  .card1 {
    zoom: 130%;
    display: inline-flex;
    width: 100%;
    background-color: #f5f5f5;
    color: #000000;
    margin-bottom: 38px;
    font-family: 'Oswald', sans-serif;
    text-transform: uppercase;
    border-radius: 4px;
    position: relative;
}
  
  .card1+.card1 {
      margin-left: 2%
  }
  
  .date {
      display: table-cell;
      width: 25%;
      position: relative;
      text-align: center;
      border-right: 2px dashed #dadde6
  }

  .date:before,
  .date:after {
      content: "";
      display: block;
      width: 30px;
      height: 30px;
      background-color: #DADDE6;
      position: absolute;
      top: -15px;
      right: -15px;
      z-index: 1;
      border-radius: 50%
  }
  
  .date:after {
      top: auto;
      bottom: -15px
  }
  
  .date time {
      display: block;
      position: absolute;
      top: 50%;
      left: 50%;
      -webkit-transform: translate(-50%, -50%);
      -ms-transform: translate(-50%, -50%);
      transform: translate(-50%, -50%)
  }
  
  .date time span {
      display: block
  }
  
  .date time span:first-child {
      color: #2b2b2b;
      font-weight: 600;
      font-size: 250%
  }
  
  .date time span:last-child {
      text-transform: uppercase;
      font-weight: 600;
      margin-top: -10px
  }
  
  .card1-cont {
      display: table-cell;
      width: 75%;
      font-size: 85%;
      padding: 10px 10px 30px 50px
  }
  
  .card1-cont h3 {
      color: #3C3C3C;
      font-size: 130%
  }
  
  
  
  .card1-cont>div {
      display: table-row
  }
  
  .card1-cont .even-date i,
  .card1-cont .even-info i,
  .card1-cont .even-date time,
  .card1-cont .even-info p {
      display: table-cell
  }
  
  .card1-cont .even-date i,
  .card1-cont .even-info i {
      padding: 5% 5% 0 0
  }
  
  .card1-cont .even-info p {
      padding: 30px 50px 0 0
  }
  
  .card1-cont .even-date time span {
      display: block
  }
  
  .card1-cont a {
      display: block;
      text-decoration: none;
      width: 80px;
      height: 30px;
      background-color: #F8504C;
      color: #fff;
      text-align: center;
      line-height: 30px;
      border-radius: 2px;
      position: absolute;
      right: 10px;
      bottom: 10px
  }
  
  .row:last-child .card1:first-child .card1-cont a {
      background-color: #037FDD
  }
  
  .row:last-child .card1:last-child .card1-cont a {
      background-color: #F8504C
  }
  
  @media screen and (max-width: 860px) {
      .card1 {
          display: block;
          float: none;
          width: 100%;
          margin-bottom: 10px
      }
      .card1+.card1 {
          margin-left: 0
      }
      .card1-cont .even-date,
      .card1-cont .even-info {
          font-size: 75%
      }
  }
  .menu {
      padding: 2rem;
      zoom: 150%;
      width: 30%;
  }
  section.container {
    background-color: #DADDE6;
    border-radius: 1rem;
    box-shadow: 0 0px 38px #999;
}
      </style>
  
  
  