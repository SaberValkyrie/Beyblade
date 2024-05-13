<template>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Gửi Đánh Giá</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


</head>
<body>

<div class="container1">
  <h2>Đánh Giá</h2>
  <div>
    <div class="form-group">
      <div class="rating" id="raiting" @change="setStar($event.target.value)">
        <input type="radio" id="star5" name="rating" value="5" required>
        <label for="star5"></label>
        <input type="radio" id="star4" name="rating" value="4" required>
        <label for="star4"></label>
        <input type="radio" id="star3" name="rating" value="3" required>
        <label for="star3"></label>
        <input type="radio" id="star2" name="rating" value="2" required>
        <label for="star2"></label>
        <input type="radio" id="star1" name="rating" value="1" required>
        <label for="star1"></label>
      </div>
    </div>
    <div class="form-group">
      <label for="comment">Bình Luận Về Sản Phẩm:</label>
      <textarea class="form-control" id="comment" name="comment" rows="4" required v-model="comment"></textarea>
    </div>
    <br>
    <div class="form-group">
      <input type="file" class="form-control-file" id="imageReview" name="imageReview" @change="upload" required>
    </div>
    <br>
    <button type="submit" class="btn btn-primary" @click="submitReview">Gửi Đánh Giá</button>
  </div>
</div>
</body>
</html>


</template>
<script>
import HeaderDashboard from '@/views/support/HeaderDashboard.vue';
import Footer from '@/views/support/Footer.vue';
import { mapGetters } from 'vuex';
import { baseURL } from '@/router/index';
import { toast } from 'vue3-toastify';
import axios from 'axios';
import 'vue3-toastify/dist/index.css';
import { AccountService } from '@/core/service/accountservice';
import { OrderService } from '@/core/service/orderservice';
import Chart from 'chart.js/auto';
import moment from 'moment';


export default {
  data() {
    return {
      comment: '',
      imageReview: null,
      orderService: new OrderService(),
      accountService: new AccountService(),
      token: localStorage.getItem('token'),
      loggedInUser: localStorage.getItem('loggedInUser'),
      order:{},
      orderKey : this.$route.params.order_key,
     id: this.$route.params.id,
      baseUrl: baseURL,
      star:0,
      comment:'',
      imageName:'',
      danhgia:false,


    };
  },
  methods: {
    setStar(sao){
      this.star = sao;
    },

    getOrder(){
            this.orderService.getOrderByKey(this.orderKey,this.id,this.token).then(res =>{
                this.order = res.data.data;
                this.getStatus(this.order.status);
                if(this.order.isRate){
                  toast('cc')
                }
            }).catch(error => {
          toast.warning(error.response.data.message)
         }); 
        },

    
        upload(file){
          this.accountService.upload(file).then(response => {
      this.imageName = response
    console.log(response)
    })
        },


  submitReview() {
   
    if(this.danhgia){
      toast.success('Không thể đánh giá lần 2')
    return
    }

    const data = {
      comment: this.comment,
      imagesReview: this.imageName,
      rating: this.star
      };
    this.orderService.createReviews(this.token,this.id,this.orderKey, data).then(res =>{
  
      toast.success(res.data.message)

      this.danhgia = true;

      setTimeout(() => {
        window.location.href ='/order/all';
      }, 1000);

            }).catch(error => {
          toast.warning(error.response.data.message)
          console.log(error)
         }); 
  }
}

};



</script>
<style>
/* Thêm CSS tùy chỉnh cho biểu tượng ngôi sao */
.rating {
    display: flex;
    flex-direction: row-reverse;
    justify-content: space-between;
}

.rating input {
  display: none; /* Ẩn input số sao mặc định */
}


.rating label {
  cursor: pointer;
  font-size: 24px;
}
.container1 {
    zoom: 130%;
    margin-left: 30%;
    padding: 6rem;
    width: 400px;
    background-color: #f9f9f9;
    border-radius: 20px;
    /* height: 600px; */
    margin-top: 5rem;
    line-height: 3;
}
.rating label::before {
  content: "\2605"; /* Biểu tượng ngôi sao */
  color: #ccc; /* Màu mặc định cho ngôi sao trống */
}


.rating input:checked ~ label::before {
  color: #919100; /* Màu khi người dùng chọn */
}
button.btn.btn-primary {
  background-color: #b62323;
  color: white;
  border-radius: 10px;
}
button.btn.btn-primary:hover {
  background-color: red;
  color: white;
}
.container {
    max-width: 390px;
}
</style>