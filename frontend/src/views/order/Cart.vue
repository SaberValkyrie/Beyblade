<template>
  <app-header></app-header>
  <br>  <br>  <br>  <br>  
  <div class="card ">

    <div class="row" v-for="cart in cartProducts" :key="cart.seller">

    
      <div class="seller">
        <button class="chat" @click="chat(cart.seller)"> ✉ </button>

      <img class="avt" :src="baseUrl + '/files/' + cart.avatarSeller">
     {{ cart.seller }} 
     <a></a>
     
     </div>
      <div class="col-md-8 cart" v-for="details in cart.cartDetails" :key="details.id">
       
     

      <div>
        <div class="row border-top border-bottom"  >
       <div class="col-1">
         <input type="checkbox" @click="check(details)">
       </div>

      
      <div class="col-2">
         <img  @click="goto('product/' + details.product.productId)" class="img-fluid" :src="baseUrl + '/files/' + details.product.images">
       </div>
      <div class="col">
         <div class="row text-muted">{{ truncatedProductName(details.product.name) }}</div>
       </div> 
       <div class="col">
       </div> 
       <div class="col">
          <span style="text-decoration: line-through;color: darkgray;"> ₫{{ (details.product.beforePrice ) }}</span>   <a style="font-weight: bold">₫{{ (details.product.price) }}</a>
       </div> 
    
       
       <div class="col">
         <button  class="concac" @click="updateQuantity(details.product.productId, -1)">-</button>
         <button  class="concac" >{{ details.quantityCart }}</button>
         <button  class="concac"  @click="updateQuantity(details.product.productId, 1 )">+</button>
       </div>
        <div class="col" style="font-weight: bold;color: crimson">₫{{ details.product.price * details.quantityCart }}
        </div> 
        <div class="col" @click="DeleteCart(details.id)">
          <button class="delete" >Xóa </button>
        </div> 
     </div>
        </div>
 
  </div>


    </div>
    <div class="summary"  v-if="selectProducts.length > 0" >
      <div class="row">Đã chọn {{ selectProducts.length }} sản phẩm</div>
      <div class="row">
        <div class="col text-right">Tổng Giá:</div>
  <div class="col text-right" style="font-weight: bold; color: brown;font-size: 14px;">₫ {{ convert(totalPrice) }}</div>
<button @click="goto('checkout')" class="btn">Mua Hàng</button>
      </div>
     
</div>

  </div>
  <div v-if="selectProducts.length > 0">
  <br>  <br>  <br>  <br>  <br>  <br>  <br>  <br> <br>  <br>  <br>  <br>
  </div>
</template>

<script>
// @ is an alias to /src
import Header from '@/views/support/Header.vue';
import Footer from '@/views/support/Footer.vue';
import { AccountService } from '@/core/service/accountservice';
import { mapGetters } from 'vuex';
import { baseWeb,baseURL } from '@/router/index';
import { ProductService } from '@/core/service/productservice';
import moment from 'moment';
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';
import router from '@/router/index';

export default {
name: 'HomeView',
computed: {
 ...mapGetters(['loggedInUser'])
},
components: {
  'app-header': Header,
  'app-footer': Footer,
},

data() {
  return {
    service: new AccountService(),
    productService: new ProductService(),
    baseUrl : baseURL,
    cartProducts: [], 
    // vouchers: [], 
    selectProducts: [], 
    cartForLocalStorage: [], // Mảng chứa các cartSelect
    totalPrice: 0,
    token: localStorage.getItem('token'),
  };
},
created(){
this.getAllCart();
// this.getAllVoucher();
},
methods :{

  check(details) {
  const index = this.selectProducts.findIndex(item => item.id === details.id);

  if (index === -1) {
    // Sản phẩm chưa được chọn, thêm vào danh sách
    this.selectProducts.push(details);
  } else {
    // Sản phẩm đã được chọn, loại bỏ khỏi danh sách
    this.selectProducts.splice(index, 1);
  }

  // Tạo một đối tượng cartSelect mới cho mỗi seller
  const cartForLocalStorage = {};

  // Duyệt qua danh sách các sản phẩm đã chọn để nhóm theo từng seller
  this.selectProducts.forEach(product => {
    const sellerUsername = product.product.seller.username;

    // Nếu chưa có thông tin seller này trong cartForLocalStorage, thêm mới
    if (!cartForLocalStorage[sellerUsername]) {
      cartForLocalStorage[sellerUsername] = {
        seller: sellerUsername,
        cartDetails: [product] // Khởi tạo danh sách sản phẩm cho seller này
      };
    } else {
      // Đã có thông tin seller này, thêm sản phẩm vào danh sách của seller đó
      cartForLocalStorage[sellerUsername].cartDetails.push(product);
    }
  });

  // Chuyển đổi cartForLocalStorage thành mảng để lưu vào LocalStorage
  const cartArray = Object.values(cartForLocalStorage);

  // Lưu đối tượng vào LocalStorage
  localStorage.setItem('productBuy', JSON.stringify(cartArray));

  console.log(cartArray);

  // Gọi phương thức tính tổng giá sau khi thay đổi danh sách sản phẩm đã chọn
  this.calculateTotalPrice();
},


calculateTotalPrice() {
  this.totalPrice = this.selectProducts.reduce((total, product) => {
    const gia = total + (product.product.price * product.quantityCart);
    localStorage.setItem('totalProductPrice', (gia));  
    return gia;
  }, 0);
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
  truncatedProductName(name) {
    const max = 70;
          if (name.length > max) {
              return name.substring(0, max) + '...';
          }
          return name;
      },
  getAllCart(){
    this.service.getCart(this.token).then(res =>{
      this.cartProducts = res;
    }).catch(error => {
      toast.error(error.response.data.message)
      setTimeout(() => {
        window.location.href ='/';
      }, 3000);
  });
  },
  goto(link){
window.location.href = '/' + link;
  },
  router(link){
    this.$router.push('/' + link);
  },
  updateQuantity(productId,action){
    this.productService.updateQuantityCart(productId,this.token,action).then(res =>{
      this.getAllCart();
 
    }).catch(error => {
      if(error.response.status == 400){
        toast.error(error.response.data.message);
      }
  });
  },
  chat(name){
    window.location.href = '/chat/' + name;
  },
  test(text){
    toast.success(JSON.stringify(text));
  },
  // getAllVoucher(){
  //   this.productService.getAllVouchers(this.token).then(res =>{
  //     this.vouchers = res.data.data;
  //   })
  // },
  DeleteCart(id){
  this.service.deleteCart(this.token,id).then(cart => {
          this.getAllCart();
            }).catch(error => {
    });;
},
}
}
</script>
<style scoped>

.seller{
  display: flex;
  align-items: center;
  zoom: 120%;
}
.img-fluid{
  border: 1px solid rgb(112, 105, 105);
}
.concac{
border:none ;
background: none
}
.concac:hover{
  color:red
}
.delete{
border:none ;
background: none
}
.delete:hover{
  color:red
}
.avt {
    width: 3rem;
    padding: 6px;
}
body{
  min-height: 100vh;
  vertical-align: middle;
  display: flex;
  font-family: sans-serif;
  font-size: 0.8rem;
  font-weight: bold;
}
.title{
  margin-bottom: 5vh;
}
.card{
  margin: auto;
  zoom:117%;
  box-shadow: 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  border-radius: 1rem;
  border: transparent;
}
@media(max-width:767px){
  .card{
      margin: 3vh auto;
  }
  .row.abc {
    grid-template-columns: repeat(1, 1fr); /* Adjust for smaller screens */
  }
}
.cart{
  background-color: #fff;
  border-bottom-left-radius: 1rem;
  border-top-left-radius: 1rem;
}
@media(max-width:767px){
  .cart{
      padding: 4vh;
      border-bottom-left-radius: unset;
      border-top-right-radius: 1rem;
  }
}

.summary{    position: fixed;
  display: flex;
  bottom: 34px;
  text-align: center;
  align-items: center;
  background-color: #ffffff;
  border: 1px solid whitesmoke;
  width: 100%;
  color: rgb(65, 65, 65);
  box-shadow: 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}

@media(max-width:767px){
  .summary{
  border-top-right-radius: unset;
  border-bottom-left-radius: 1rem;
  }
}
.summary .col-2{
  padding: 0;
}
.summary .col-10
{
  padding: 0;
}
.form-data {
  align-items: center;
    display: flex;
    height: 48px;
    padding: 0px;
}
.row {    margin-right: 305px;
  margin-left: 8rem;
  align-items: center;
  width: 100%;
  font-size: 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}
.title b{
  font-size: 1.5rem;
}
.main{
  margin: 0;
  padding: 2vh 0;
  width: 100%;
}
.col {
    flex: 1; /* Căn đều độ rộng của các cột con */
    padding: 5px; /* Khoảng cách bên trong của các cột con */

  }
.col-2 {
  padding: 0 1vh;
}
a{
  padding: 0 1vh;
}
.close{
  margin-left: auto;
  font-size: 0.7rem;
}
img{
  width: 100%
}
.back-to-shop{
  margin-top: 4.5rem;
}
h5{
  margin-top: 4vh;
}
hr{
  margin-top: 1.25rem;
}
form{
  padding: 2vh 0;
}
select{
  border: 1px solid rgba(0, 0, 0, 0.137);
  padding: 1.5vh 1vh;
  margin-bottom: 4vh;
  outline: none;
  width: 100%;
  background-color: rgb(247, 247, 247);
}
input{
  border: 1px solid rgba(0, 0, 0, 0.137);
  padding: 1vh;
  margin-bottom: 4vh;
  outline: none;
  width: 100%;
  background-color: rgb(247, 247, 247);
}
input:focus::-webkit-input-placeholder
{
    color:transparent;
}
.btn{
  background-color: #9e0000;
  border-color: #e7e7e7;
  color: white;
  width: 100%;
  font-size: 12px;
  margin-top: 4vh;
  padding: 1vh;
  border-radius: 0;
}
.chat{
  border: none;
    /* border-radius: 50%; */
    /* width: 21px; */
    background-color: #ffffff;
    color: #f30000;
    font-size: 20px;
}
.chat:hover{
  border: none;
  background-color: #4e0000;
  color: white;
}
.btn:hover{
  background-color: #2c0000;
  border-color: #1a0000;
  color: white;
  width: 100%;
  font-size: 14px;
  margin-top: 4vh;
  padding: 1vh;
  border-radius: 0;
}
.btn:focus{
  box-shadow: none;
  outline: none;
  box-shadow: none;
  color: white;
  -webkit-box-shadow: none;
  -webkit-user-select: none;
  transition: none; 
}
.btn:hover{
  color: white;
}
a{
  color: black; 
}
a:hover{
  color: black;
  text-decoration: none;
}
#code{
  background-image: linear-gradient(to left, rgba(255, 255, 255, 0.253) , rgba(255, 255, 255, 0.185)), url("https://img.icons8.com/small/16/000000/long-arrow-right.png");
  background-repeat: no-repeat;
  background-position-x: 95%;
  background-position-y: center;
}

</style>