<template>
  <!-- Jumbotron -->
  <app-header></app-header>



  <section class="bg-light py-5">

<div class="cc">
  <div v-if="phuongthuc == 0" >
    <a > Bạn Muốn Đổi {{convert(account.tienmat)}} Số Dư Ví Đang Có Sang BeyPoint? </a> <span class="cm" @click="setPhuongThuc(1)"> Đổi ngay</span>
  </div>
  <div v-else>
    <a  >Bạn muốn nạp bằng TKNH?  </a> <span class="cm" @click="setPhuongThuc(0)"> Dùng ngay</span>
  </div>

<br>
<a>Bạn Không Có Tài Khoản Ngân Hàng? </a> <a href="https://shopee.vn/product/347108613/28951422476/" class="cm">Bấm Vào Đây!</a>

</div>

      <div class="container" v-if="phuongthuc != 1">
          <div class="card shadow-0 border">
              <div class="p-4">
              <div class="cc">  Nhập Số Tiền Cần Nạp: <input type="number" v-model="amount"></div>
                  <br>  
                  <img class="qr" :src="getQr()" v-if="urlTT == ''">

              
              </div>
            
          
          </div>
    
      </div>

      <div class="container" v-else>
          <div class="card shadow-0 border">
              <div class="p-4">
              <div class="cc">  Nhập Số Dư Cần Tiêu Hao: <input type="number" v-model="amountLeft"></div>
                  <br>  
                <h2 class="text-danger">Bạn sẽ nhận được {{ amountLeft * 2 }} BeyPoint </h2> 
                <br>
                <h2 class="text-success">Ưu Đãi: Mỗi khi tiêu trên 20K Số Dư lấy BeyPoint,bạn sẽ có cơ hội nhận được phần quà đặc biệt </h2> 

                <br>
                <button @click="naptien()">Đổi Ngay</button>
              </div>
            
          
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
          accountName: 'NGUYEN DINH HAI',
        addInfo: '',
        stk: '0383087656',
        urlTT:'',
        amount:20000,
        noidung:'',
        ck:false,
        account: {},
        phuongthuc:0,
        amountLeft:20000,
        }
       
      },
      created(){
        this.getAcc()

      },
      methods :{

        naptien(){

         this.service.checkNapTien(this.token,this.amount)
                  .then(response => {
                    toast.success(response.data.message);
         })
         .catch(error => {    
            toast.error(error.response.data.message);
      
         });
        },
        setPhuongThuc(s){

          this.phuongthuc = s;
        },
        getAcc() {
      this.service.getAccountLogin(this.token).then(res => {
        this.account = res;
      })
    },
    truncatedProductName(name) {
    const max = 5;
          if (name.length > max) {
              return name.substring(0, max) + '...';
          }
          return name;
      },
        getQr(){
          this.addInfo = 'naptk ' + this.account.username;
    const link = `https://api.vietqr.io/image/MBbank-${this.stk}-SUKDJlE.jpg?accountName=${this.accountName}&amount=${this.amount}&addInfo=${this.addInfo}`;
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
convert(power) {
    const formatter = new Intl.NumberFormat('vi-VN', { maximumFractionDigits: 1 });

    if (power >= 1e9) {
        return formatter.format(power / 1e9) + ' Tỷ';
    } else if (power >= 1e6) {
        return formatter.format(power / 1e6) + ' Tr';
    } else if (power >= 1e3) {
        return formatter.format(power / 1e3) + ' K';
    } else {
        return formatter.format(power);
    }
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
  .cc {
    zoom: 200%;
}
.cm {
   color: blue;
}
button {
    zoom: 200%;
    border-radius: 10px;
    border: 1px solid #580000;
    background-color: white;
    color: black;
}
button:hover {
    zoom: 200%;
    border-radius: 10px;
    border: 1px solid #ffffff;
    background-color: rgb(77, 0, 0);
    color: rgb(255, 255, 255);
}
    </style>
  