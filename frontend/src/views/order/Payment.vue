<template>
  <!-- Jumbotron -->
  <app-header></app-header>
  <br>
  <br>
  <br>
  <!-- Jumbotron -->
  
  <section class="bg-light py-5">
      <div class="container">
          <div class="card shadow-0 border">
              <div class="p-4">
            
                  <br>  
                  <img class="qr" :src="getQr()" v-if="urlTT == ''">

              
              </div>
            
              <input type="number" v-model="amount">
          </div>
    
      </div>
  </section>
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
          service: new AccountService(),
          order:{},
          totalPrice: 0,
          token: localStorage.getItem('token'),
          baseUrl : baseURL,
          accountName: 'Beyblade',
        addInfo: '',
        stk: '0383087656',
        urlTT:'',
        amount:20000,
        noidung:'',
        ck:false
        }
       
      },
      created(){

      },
      methods :{
        
        getQr(){
          this.addInfo = 'dđ';
  
    const link = `https://api.vietqr.io/image/MBbank-${this.stk}-SUKDJlE.jpg?accountName=${this.accountName}&amount=${this.amount}&addInfo=${this.addInfo}`;
    // Trả về đường link
    // const link='https://api.vietqr.io/image/MBbank-0383087656-SUKDJlE.jpg?addInfo=tra%20tien%20trinh%20dit&accountName=H%E1%BA%A3i%20%C4%90%E1%BA%B9p%20Trai'
    return link;
  
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
      .container {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: flex-start;
    padding: 2vw;
}

.card {
    flex: 1;
    margin-right: 2vw;
}

.qr {
    width: 50vw; /* Điều chỉnh kích thước QR theo đơn vị vw */
    height: auto;
}

.col-xl-8.col-lg-8.mb-4, 
.col-xl-4.col-lg-4.d-flex.justify-content-center.justify-content-lg-end {
    padding: 2vw;
    box-sizing: border-box;
}

.col-xl-4.col-lg-4.d-flex.justify-content-center.justify-content-lg-end {
    flex: 0 0 30vw; /* Điều chỉnh phần bên phải có kích thước cố định theo đơn vị vw */
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    background-color: white;
    padding: 2vw;
    border: 1px solid darkorange;
}

.concac, .float {
    margin-bottom: 2vw;
}





  .badge-brown {
      background-color: #8B4513; /* Màu đỏ đậm dạng brown */
      color: white; /* Màu chữ là trắng để tương phản */
  }
  .form-check.h-100.border.rounded-3 {
      background-color: floralwhite;
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
  