<template>
 
    <html lang="vi">
    <app-header ></app-header>

      <body>
        <div class="app">
  
       <div class="app__container">
            <div class="grid wide">
              <div class="row sm-gutter app__content">
                <!-- Category -->
                <!-- <div class="col l-10 m-18 c-2"> -->
                  <div class="item">
    <!-- Product -->

<!--
    {{products}} -->
    <div class="home-product">
      <div class="row sm-gutter">
        <!-- Product item -->
        <div v-for="(product, index) in products" :key="index" class="product-item">
          <a class="home-product-item" @click="go('/shop/item/' + product.code)">
            <div class="home-product-item__img" 
     :style="{ backgroundImage: 'url(' + product.beyBlade.images + ')' }">
</div>
<div v-if="product.quantity <= 0" class="home-product-item__out-of-stock">
        Hết Hàng
      </div>


            <h4 class="home-product-item__name">{{ product.beyBlade.name }}</h4>
            <div class="home-product-item__price">
        
                <span class="home-product-item__price-current">Giá: {{ convert(product.price) }}</span>   
                <span class="home-product-item__price-current">Còn: {{ convert(product.quantity) }}</span>   

                 </div>
      

    
            <div class="home-product-item__action">
              <span class="home-product-item__like home-product-item__like--liked">
                <i class="home-product-item__like-icon-empty far fa-heart"></i>
                <i class="home-product-item__like-icon-fill fas fa-heart"></i>
              </span>
  
              <div class="home-product-item__rating">
                <i  :class="product.beyBlade.power + product.beyBlade.hp > 50000 ?
                'home-product-item__star--gold fas fa-star': 'fas fa-star'"></i>
                     <i  :class="product.beyBlade.power + product.beyBlade.hp > 150000 ?
                'home-product-item__star--gold fas fa-star': 'fas fa-star'"></i>
                    <i  :class="product.beyBlade.power + product.beyBlade.hp > 250000 ?
                'home-product-item__star--gold fas fa-star': 'fas fa-star'"></i>
                    <i  :class="product.beyBlade.power + product.beyBlade.hp > 350000 ?
                'home-product-item__star--gold fas fa-star': 'fas fa-star'"></i>
                    <i  :class="product.beyBlade.power + product.beyBlade.hp >= 450000 ?
                'home-product-item__star--gold fas fa-star': 'fas fa-star'"></i>
                            </div>
  
  
            </div>
            <div class="home-product-item__origin">
                            <span class="home-product-item__brand"></span>
                            <span class="home-product-item__origin-name"
                              >Sức Mạnh : {{ convert(product.beyBlade.power + product.beyBlade.hp) }}</span
                            >
                          </div>
            <div class="home-product-item__favourite" v-if="product.beyBlade.isBoss">
              <i class="fas fa-check"></i>
              <span>Ưu Tiên</span>
            </div>
            <div class="home-product-item__sale-off"  >
                            <span class="home-product-item__sale-off-percent" >{{ product.day > 0 ? product.day : 'Vĩnh' }}</span>
                            <br>
                            <span class="home-product-item__sale-off-percent">{{ product.day > 0 ? 'Ngày' : 'Viễn' }}</span>
                          </div>
          </a>
        </div>
      </div>
    </div>
    

                </div>
              </div>
            </div>
          
          </div>
        
        </div>
   
      
      </body>
      <app-footer></app-footer>
    </html>
    
    </template>
    <script>
    import axios from 'axios'; // Import Axios
    import { baseURL } from '@/router/index';
    import Header from '/src/views/support/Header.vue'
    import Foooter from '/src/views/support/Footer.vue'
    import { toast } from 'vue3-toastify';
    import {GameService} from '@/core/service/game';
  import 'vue3-toastify/dist/index.css';
  
    
    export default {
      components: {
        'app-header': Header,
        'app-footer': Foooter,
      },
  
      data() {
        return {
          service: new GameService(),
          baseUrl: baseURL,
          products: [],
          categories: [],
          selectedCategoryId: null,
          currentPage: 1,
          totalPages: 0,
          itemsPerPage: 20,
          currentStatus: null,  
        };
      },
      created() {
        this.getItemShop()

      },
      methods: {

   
        getItemShop(){
            this.service.getItemShop().then(res => {
                this.products = res.data.data
            })
        },

      
     

  convert(power) {
      const formatter = new Intl.NumberFormat('vi-VN', { maximumFractionDigits: 1 });
  
      if (power >= 1e9) {
          return formatter.format(power / 1e9) + 'Tỷ';
      } else if (power >= 1e6) {
          return formatter.format(power / 1e6) + 'Tr';
      } else if (power >= 1e3) {
          return formatter.format(power / 1e3) + 'K';
      } else {
          return formatter.format(power);
      }
  },
  
go(link){
  window.location.href = link;
}
      }
    };
    </script>
    <style>
    body{
    
      text-decoration: none;
      background-color: #f6f6f6;
    }
    .product-item {
    width: 16vw;
    margin-bottom: 3px;
    padding-right: 10px;
}
    .app {
    overflow: hidden;

    width: 100%;
}
  .row.sm-gutter {
      margin-left: -5px;
      margin-right: -5px;
  }
  .home-filter {
      /* padding: 18px; */
      background-color: rgb(226 226 226 / 4%);
      display: flex;
      align-items: center;
      padding: 9px 111px;
      margin-bottom: -35px;
      border-radius: 2px;
  }
  .select-input {
      text-align: center;
      font-size: 20px;
      min-width: 200px;
      height: 34px;
      padding: 0 12px;
      border-radius: 2px;
      background-color: var(--white-color);
      display: flex;
      justify-content: space-between;
      align-items: center;
      position: relative;
  }
  .select-input {
    margin-right: -11px;
      margin-left: 24px;
  }
  button.home-filter__btn.btn {
      font-size: 15px;
  }
  button.home-filter__btn.btn:hover {
      font-size: 16px;
      color: rgb(2, 2, 2);
  }
  .home-product {
      margin-bottom: 10px;
      margin-left: 109px;
  }

  .home-product-item__out-of-stock {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.7);
    color: white;
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 0;
    font-size: 1.5rem;
    font-weight: bold;
    text-align: center;
}

  </style>
  
  
    