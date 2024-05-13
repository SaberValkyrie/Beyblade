<template>
  <!-- <nav>
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link>
  </nav> -->
  <router-view/>
  
  <!-- <link rel="icon" type="image/png" href="./assets/img/user_avatar.png"> -->
</template>
<script>
import axios from 'axios';
import { baseWeb,baseURL } from '@/router/index';
import { mapGetters } from 'vuex';
import moment from 'moment';
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';

export default {
  name:'App',
  computed: {
    ...mapGetters(['loggedInUser']),
   
  },
  data() {
    return {
      baseUrl : baseURL,
      cartProducts: [], // khởi tạo một mảng rỗng
      addressUser:[],
      userInfo: {},
      notifications: [],
    
    };
  },
 
  methods: {
    //-----------------------------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------------------------
    getListAddress() {
   const token = localStorage.getItem('token'); // Lấy token từ localStorage
       axios.get(`${baseURL}/address/${token}`)
         .then(response => {
           this.addressUser = response.data.data;
           localStorage.setItem('addressUser', JSON.stringify(response.data.data));    
         })
         .catch(error => {
             toast.success('Hãy cập nhật địa chỉ nhé!');
           console.error('Error fetching:', error);
         });
     },
//-----------------------------------------------------------------------------------------------------------------
    // Hàm fetchCartItems để lấy danh sách cart từ API
    fetchCartItems() {
      const loggedInUserToken = localStorage.getItem('token'); // Lấy token từ localStorage
      axios.get(`${this.baseUrl}/cart/${loggedInUserToken}`)
      .then(response => {
        this.cartProducts = response.data.data; 
        localStorage.setItem('cartProducts', JSON.stringify(response.data.data));
      })
      .catch(error => {
        this.cartProducts.length = 0;
      });
    },
//-----------------------------------------------------------------------------------------------------------------
    // Hàm fetchNotifications để lấy danh sách thông báo từ API
    fetchNotifications() {
      const loggedInUserToken = localStorage.getItem('token'); // Lấy token từ localStorage
      axios.get(`${this.baseUrl}/notify/read=false/${loggedInUserToken}`)
      .then(response => {
        this.notifications = response.data.data;
        localStorage.setItem('notifications', JSON.stringify(response.data.data));
        // console.log(response.data.data)
      })
      .catch(error => {
        console.error('Error fetching notifications:', error);
      });
    },
//-----------------------------------------------------------------------------------------------------------------

 
  },
  created() {

  }
};
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
