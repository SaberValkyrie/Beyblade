<template>
  <head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
  <link rel="icon" type="image/png" href="./google_play.png">
  <title>Shop Plaza</title>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="stylesheet" type="text/css" href="./assets/css/base.css" />
  <link rel="stylesheet" type="text/css" href="./assets/css/main.css" />
  <link rel="stylesheet" type="text/css" href="./assets/css/grid.css" />
  <link rel="stylesheet" type="text/css" href="./assets/css/responsive.css" />
  <link  rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"  />
  <link   href="https://fonts.googleapis.com/css?family=Roboto&display=swap"  rel="stylesheet"  />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
  <link  href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.min.css" rel="stylesheet">
  </head>
<body>
  <header   class="header" :class="{ 'header--sticky': isSticky }">
        <div class="grid wide">
          <nav class="header__navbar hide-on-mobile-tablet">
            <ul class="header__navbar-list">
             
              <li class="header__navbar-item">
                <span class="header__navbar-title--no-pointer">Kết Nối</span>

                <a href="" class="header__navbar-icon-link">
                  <i class="header__navbar-icon fab fa-facebook"></i>
                </a>
                <a href="" class="header__navbar-icon-link">
                  <i class="header__navbar-icon fab fa-instagram"></i>
                </a>
              </li>
            </ul>      
            <ul class="header__navbar-list">
          
              <li class="header__navbar-item">
                <a href="" class="header__navbar-item-link">
                  <i class="header__navbar-icon far fa-question-circle"></i>                  Trợ giúp
                </a>
              </li>
              <li class="header__navbar-item header__navbar-user">
                <img :src="loggedInUser && loggedInUser.avatar ? baseUrl + '/files/' + loggedInUser.avatar : baseUrl + '/files/user_avatar.png'" 
     alt="" class="header__navbar-user-img" style="margin-top: 3px;" />
                <span class="header__navbar-user-name" v-if="!loggedInUser" v-on:click="redirectToLogin"> Đăng Nhập  </span>
                  <span class="header__navbar-user-name"  v-if="loggedInUser">
                    {{ loggedInUserName }}

                    </span>
                <ul class="header__navbar-user-menu " v-if="loggedInUser">
                  <br>
                  <li class="header__navbar-user-item">
                    <a href="/profile">Tài khoản của tôi</a>
                  </li>
                  <li class="header__navbar-user-item">
                    <a href="/changePassWord">Đổi Mật Khẩu</a>
                  </li>
                <li class="header__navbar-user-item">
                    <a @click="logout"  style="color: black;"> Đăng Xuất </a>
                  </li>
                    <br>
                
                </ul>
              </li>
            </ul>






            
          </nav>

          <!-- Header with Search -->
          <div class="header-with-search">
            <label for="mobile-search-checkbox" class="header__mobile-search">
              <i class="header__mobile-search-icon fas fa-search"></i>
            </label>

            <!-- Header Logo -->
            <div class="header__logo hide-on-tablet">
              <a href="/" class="header__logo-link">
                <img class="header__logo-img" :src="baseUrl + '/files/logo.png'">
              </a>
            </div>
            <input
              type="checkbox"
              hidden
              id="mobile-search-checkbox"
              class="header__search-checkbox"
            />
         <!-- Header Search -->
<div class="header__search ">
  <div class="header__search-input-wrap">
      <input
            type="text"
            class="header__search-input"
            placeholder="Nhập để tìm kiếm sản phẩm" @keyup.enter ="searchProducts"
            v-model="keyword"
          />
    <div class="header__search-history" v-if="sortedHistorySearch.length > 0">
      <h3 class="header__search-history-heading" >
        Lịch sử tìm kiếm
      </h3>

      <ul class="header__search-history-list"  v-for="product in sortedHistorySearch">
        <div 
        @click.middle="goto('/search/search?keyword=' + product.keyword)">
        <li class="header__search-history-item" >
          <a >{{ product.keyword }}</a>
        </li>
      </div>
       
      </ul>

    </div>
  </div>
  <div class="header__search-select">
    <span class="header__search-select-label">Tất Cả</span>
    <i class="header__search-select-icon fas fa-angle-down"></i>

    <ul class="header__search-option">
      <li
        class="header__search-option-item header__search-option-item--active"
      >
        <span>Tìm Shop</span>
        <i class="fas fa-check "></i>
      </li>
      <li class="header__search-option-item">
        <span>Tìm Sản Phẩm</span>
        <i class="fas fa-check"></i>
      </li>
    </ul> 
  </div> 

<button class="header__search-btn" @click="searchProducts">
          <i class="header__search-btn-icon fas fa-search"></i>
        </button>

</div>
 <div v-if="searchResult">
      <div v-for="product in searchResult" :key="product.productId">
        <h3>{{ product.name }}</h3>
        <p>{{ product.description }}</p>
      </div>
    </div>


        
          </div>
        </div>
        <ul class="header__sort-bar">
          <li class="header__sort-item">
            <a href="/profile" class="header__sort-link">Cá Nhân</a>
          </li>
          <li class="header__sort-item">
            <a href="/shop" class="header__sort-link">Cửa Hàng</a>
          </li>
          <li class="header__sort-item">
            <a href="/user/bag" class="header__sort-link">Kho Đồ</a>
          </li>
          <li class="header__sort-item header__sort-item--active">
            <a href="/game/select" class="header__sort-link">Thách Đấu</a>
          </li>
         
        </ul>
        
      </header>
      <br>   <br>   <br>   <br>   <br>  

      <div class="notify" v-if="isShow" @click="acp">
      
        <div class="tb">
    <span class="header__cart-notice">X</span>
    <h1 class="notification-title">Thông Báo</h1>
    <p class="notification-content">
      Hiện tại đang là phiên bản thử nghiệm, mọi người có thể sử dụng web thoải mái, dự kiến ngày update chính thức là 22/6/2024.
    </p>
  </div>
  
  </div>
</body>

</template>
<script>
import axios from 'axios';
import { baseWeb,baseURL } from '@/router/index';
import { mapGetters } from 'vuex';
import moment from 'moment';
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';
import { AccountService } from '@/core/service/accountservice';


export default {
  name:'Header',
  computed: {


    sortedHistorySearch() {
    return this.HistorySearch.sort((a, b) => new Date(b.timestamp) - new Date(a.timestamp)).slice(0, 5);
  },

    ...mapGetters(['loggedInUser']),
    loggedInUserName() {
      return this.loggedInUser ? this.loggedInUser.username : 'Đăng Nhập';
    },
    cartSize() {
  const productCount = {}; // Using an object to store unique product IDs

  if (this.cartProducts && this.cartProducts.length > 0) {
    this.cartProducts.forEach(cart => {
      if (cart.cartDetails && cart.cartDetails.length > 0) {
        cart.cartDetails.forEach(details => {
          const productId = details.product.productId; // Get the product ID
          if (!productCount[productId]) {
            productCount[productId] = true; // Using an object to track unique products
          }
        });
      }
    });
  }
  const totalQuantity = Object.keys(productCount).length;
  return totalQuantity.toString(); 
},

  },
  data() {
    return {
      accountService: new AccountService(),
      isSticky: true,
      scrollPosition: 0,
      ticking: false,
      basew: baseWeb,
      baseUrl : baseURL,
      createdAt: '',
      keyword: '',
      cartProducts: [], // Khởi tạo với một mảng rỗng
      cart: null,
      isSearchPage: false ,// Đặt thành true nếu đây là trang tìm kiếm
      searchResult: null,
      currentUser: '',
      searchKeyword: '',
      token: localStorage.getItem('token'),
      listUser:[],
      HistorySearch: JSON.parse(localStorage.getItem('searchHistory')) || [],
      isShow: false,
    };
  },
  mounted() {

     setTimeout(() => {
      const header = document.querySelector('.header');
      if (header) {
        header.classList.add('header--show');
      }
    }, 10); 


    setTimeout(() => {
      const header = document.querySelector('.notify');
      if (header) {
        this.isShow = true;
        header.classList.add('notify--show');
      }
    }, 10); 


    window.addEventListener('scroll', this.handleScroll);
  },
  beforeDestroy() {
    window.removeEventListener('scroll', this.handleScroll);
  },
  methods: {
    gotoDashBoard(){
window.location.href = '/dashboard';
    },
    goto(link){
window.location.href = '/' + link;
  },
    handleScroll() {
    const threshold = 0; // Ngưỡng để kích hoạt thanh header cố định
    const scrollPosition = window.scrollY || window.pageYOffset;

    if (scrollPosition >= threshold) {
      this.isSticky = true;
    } else {
      this.isSticky = false;
    }
  },
    checkSticky() {
      const threshold = 100;
      this.isSticky = this.scrollPosition > threshold;
    },
    getCart(id){
      window.location.href = '/product/' + id;
    },
      
getAllMess(){
  this.accountService.getAllUserChat(this.token)
      .then(response => {
        this.listUser = response.data.data;
          })
      .catch(error => {
      });
    },

          
checkAuthenticate(){
  this.accountService.checkAuthenticate(this.token,this.loggedInUser.code)
      .then(response => {
          })
      .catch(error => {
        toast(error.response.data.message)
        this.logout();
      });
    },
    goto(link){
window.location.href = '/' + link;
  },
DeleteCart(id){
  this.accountService.deleteCart(this.token,id).then(cart => {
          this.fetchCartItems();
			}).catch(error => {
        window.location.reload();
    });;
},
fetchCartItems() {
  this.accountService.getCart(this.token)
    .then(cart => {
      this.cartProducts = cart; 
    })
    .catch(error => {
    });
},

acp(){

this.isShow = false;
},

    logout() {
    axios.post(`${this.baseUrl}/logout/${this.token}`)
      .then(response => {
        localStorage.clear();
        toast.info('Đăng xuất thành công,vui lòng đăng nhập lại')
        setTimeout(() => {
          window.location.reload();
				}, 1000);
      })
      .catch(error => {
        console.error('Error logging out:', error);
      });
  },
     redirectToLogin() {
       window.location.href = '/login'; // Chuyển hướng bằng cách thay đổi URL
    },

    formatCreatedAt(timestamp) {
      return moment(timestamp).format('DD/MM/YYYY HH:mm'); // Định dạng ngày tháng giờ phút theo ý muốn
  },   
  
  fetchNotifications() {
     this.accountService.getNotify(this.token)
           .then(response => {
        this.notifications = response.data.data;
      })
      .catch(error => {
        console.error('Error fetching notifications:', error);
      });
    },
    searchProducts() {
  const keyword = this.keyword.trim();
  if (keyword !== '') {

    const searchItem = {
      keyword: keyword,
      timestamp: new Date()
    };
    this.HistorySearch.push(searchItem);
    localStorage.setItem('searchHistory', JSON.stringify(this.HistorySearch));


    window.open(`https://shopee.vn/search?keyword=${keyword}`);

  

  } else {
    keyword == '';
    console.log('Từ khóa tìm kiếm trống');
  }


},
  },
  created() {
if(this.loggedInUser){
  this.checkAuthenticate()
}
this.isShow = true;
  
}
};
</script>
<style scoped>
.header {
  opacity: 0; /* Mặc định ẩn */
  transition: opacity 0.5s ease-in-out; /* Thời gian và kiểu chuyển đổi */
}

.header--show {
  opacity: 1; /* Hiển thị dần dần */
}



.header--sticky {
  position: fixed;
  top: 0;
  left: 0;
  padding: 1px;
  width: 100%;
  z-index: 1000;
}

.notify.notify--show {
  background-color: white;
    position: fixed;
    z-index: 9999;
    width: 35vw;
    height: 14vw;
    margin-left: 10%;
    box-shadow: 0 24px 62px #999;
    /* height: 80%; */
    border-radius: 0.5rem;
    zoom: 220%;
    font-size: 15px;
    text-align: center;
}





button.accept {
    background-color: brown;
    color: aliceblue;
    border-radius: 4px;
    border: darkslategrey;
}

button.accept:hover {
    background-color: rgb(0, 0, 0);
    color: rgb(226, 226, 226);
    border-radius: 4px;
    border: darkslategrey;
}
p.notification-content {
    padding: 0vw;
    font-size: 1vw;
}
.h1, h1 {
    padding: 1vw;
    font-size: 2vw;
}
</style>
