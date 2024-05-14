<template>
       <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="generator" content="Mobirise v5.9.13, a.mobirise.com">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
    <link rel="shortcut icon" href="" type="image/x-icon">
    <meta name="description" content="Trang web thương mại điện tử giống Shopee với hàng ngàn sản phẩm đa dạng và giá cả cạnh tranh.">
    <title>Beyblade</title>
   
    
    
  </head>
  <body>

{{ selectedBey.name }}
    <div class="lucky-wheel" v-if="!isSelect">
    <div class="select">
  <button @click="select(true)">{{selectedBey.images ? 'Đổi Bey khác' : 'Chọn Bey'}} </button>
</div>

    <div class="wheel-container" v-if="selectedBey.images">
      <div class="wheel" :style="wheelStyle" :class="{ 'spinning': spinning }">
        <img :src="selectedBey.images" class="wheel-image">
      </div>
      <div class="effect" v-if="showEffect">
        <img :src="eff" class="effect-image">
      </div>
      <div class="wheel" :style="wheelStyle" :class="{ 'spinning': spinning }">
        <img :src="imgBoss" class="wheel-image">
      </div>

    </div>
    <button v-if="selectedBey.images" @click="spinWheel" :disabled="spinning">Chiến Đấu (1000 BeyPoint)</button>

  </div>
  <div class="lucky-wheel" v-if="isSelect">
  
  
  

<section id="team" class="section bg-gray-100" v-if="!isDone">
  <h3 > Vui Lòng Chọn Hệ Quay Bạn Muốn </h3>
    <div class="container">
      
        <div class="row section-heading justify-content-center text-center wow fadeInUp" data-wow-duration="0.3s" data-wow-delay="0.3s" style="visibility: visible; animation-duration: 0.3s; animation-delay: 0.3s; animation-name: fadeInUp;">
 
          <div class="col-lg-8 col-xl-6">

            </div>

        </div>
        <div class="row">
            <div class="col-lg-3 col-sm-6 my-3 wow fadeInUp" 
            data-wow-duration="0.3s" data-wow-delay="0.3s"
             style="visibility: visible; animation-duration: 0.3s;
              animation-delay: 0.3s; animation-name: fadeInUp;" v-for="type in types">
                <div class="hover-top-in text-center">
                    <div class="overflow-hidden z-index-1 position-relative px-5" @click="setTypeBey(type)">
                      <img class="rounded-circle border border-5 border-white shadow" :src="type.images" title="" alt="">
                      </div>
                   
                      <div class="mx-2 mx-xl-3 shadow rounded-3 position-relative mt-n4 bg-white p-4 pt-6 mx-4 text-center hover-top--in">
                        <h6 class="fw-700 dark-color mb-1">Chọn</h6><small>{{ type.name }}</small>
                </div>
                </div>
            </div>
       
        </div>
    </div>
</section>



<div v-if="isDone" > 

  <select class="form-select"  
        data-trigger="true" 
        name="choices-single-filter-orderby"
        id="choices-single-filter-orderby"
        aria-label="Default select example"
        @change="setBey($event.target.value)"
        >
  <option v-for="bey in listBeyType" :key="bey.id" :value="bey.id">{{ bey.name }}</option>
</select>




  <button @click="accept()">Xác Nhận</button>
</div>
  


  <button v-else @click="cancel()">Hủy Chọn</button>

  </div>

  </body>


</template>

<script>
  import Header from '@/views/support/Header.vue';
  import Footer from '@/views/support/Footer.vue';
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';
  import { baseURL } from '@/router/index';
  import axios from 'axios';
  import { GameService } from '@/core/service/game';
  import Chart from 'chart.js/auto';
  import moment from 'moment';

export default {
  components: {
  'app-header': Header,
  'app-footer': Footer,
  },
  data() {
    return {
      items: ['1', '2'], // Danh sách các phần thưởng
      spinning: false, // Trạng thái đang quay
      showEffect: false, // Biến kiểm soát hiển thị của hình ảnh effect
      rotationCount: 0, // Số lần đã quay
      maxRotationCount: 10, // Số lần quay tối đa
      spinDuration: 0.5, // Thời gian quay (giây) cho mỗi vòng
      token : localStorage.getItem('token'),
      gameService: new GameService(),
      imgBoss: 'https://m.media-amazon.com/images/I/71WgKP-cPSL._AC_UF1000,1000_QL80_DpWeblab_.jpg',
      img:'https://images-cdn.ubuy.co.in/635b867191f5134064149b62-beyblade-burst-b-189-booster-guilty.jpg',
      eff:'https://i.gifer.com/origin/4a/4a0225d3bbd093b282a33c369a368730_w200.gif',
      types:[],
      isSelect:false,
      isDone:false,
      selectedType:{},
      listBeyType:[],
      selectedBey:{},
    };
  },
  created(){
    this.getType()

    this.stop()
 },
  methods: {

    setBey(id){
      this.gameService.getBeyByID(id).then(res => {
        this.selectedBey = res.data.data ;
  }).catch(error => {
      toast.warning(error.response.data.message)
   });
    },


    setTypeBey(type){
      this.isDone = true;
      this.selectedType = type;
      this.getBeyByType()
    },
    select(s){
      this.isSelect = s;
    },
    done(){
      this.isSelect = false;
    },

    cancel(){
      this.isDone = false;
      this.isSelect = false;
    },

    accept(){
      this.cancel()

    },

  spinWheel() {
  if (!this.spinning) {
    this.gameService.checkCoint(this.token).then(res => {
      this.spinning = true; // Chỉ đặt spinning thành true khi bắt đầu quay thực sự
    this.showEffect = true; // Hiển thị hình ảnh effect khi bắt đầu quay
    this.rotateWheel(); // Bắt đầu quay vòng quay
    })  .catch(error => {
      toast.error(error.response.data.message)
   });
  }
},
rotateWheel() {
  setTimeout(() => {
    this.rotationCount++;
    if (this.rotationCount < this.maxRotationCount) {
      // Tiếp tục quay nếu chưa đạt số lần quay tối đa
      this.rotateWheel();
    } else {
      // Dừng quay khi đạt số lần quay tối đa
      this.stopSpin();
    }
  }, this.spinDuration * 1000);
},
stop() {
  this.spinning = false; // Đặt lại spinning thành false khi quay kết thúc
  this.showEffect = false; // Ẩn đi hình ảnh effect khi dừng quay
  const finalRotation = this.items.length - 0 - 1;
  document.documentElement.style.setProperty('--final-rotation', finalRotation);
},
    stopSpin(index) {
  this.spinning = false; // Đặt lại spinning thành false khi quay kết thúc
  this.showEffect = false; // Ẩn đi hình ảnh effect khi dừng quay

  const finalRotation = this.items.length - index - 1;
  document.documentElement.style.setProperty('--final-rotation', finalRotation);

  this.gameService.spin(this.token).then(res => {
    toast.success(res.data.message)
  }) .catch(error => {
      toast.warning(error.response.data.message)
   });
},

getType(){
  this.gameService.getType().then(res => {
  this.types = res.data.data 
  }).catch(error => {
      toast.warning(error.response.data.message)
   });
},

getBeyByType(){
  this.gameService.getBeyByType(this.selectedType.id).then(res => {
  this.listBeyType = res.data.data 
  }).catch(error => {
      toast.warning(error.response.data.message)
   });
}

  },
  computed: {
    wheelStyle() {
      return {
        '--item-count': this.items.length,
        '--spin-duration': `${this.spinDuration}s`,
      };
    },
  },
};
</script>

<style scoped>
.lucky-wheel {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.wheel-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.wheel {
  position: relative;
  width: 300px; /* Kích thước vòng quay */
  height: 300px; /* Kích thước vòng quay */
  border-radius: 50%;
  background-color: brown; /* Màu chủ đạo */
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  transition: transform var(--spin-duration) cubic-bezier(0.25, 0.1, 0.25, 1);
  margin: 0 10px; /* Khoảng cách giữa hai vòng quay */
}

.wheel-image {
  width: 100%; /* Kích thước hình ảnh */
  height: auto; /* Kích thước hình ảnh tự điều chỉnh theo tỉ lệ */
}
.effect1 {
  position: fixed;
  z-index: 1;
}

.effect1-image {
  height: auto; /* Kích thước hình ảnh effect tự điều chỉnh theo tỉ lệ */
}

.effect {
  position: absolute;

  z-index: 3; /* Đảm bảo hiển thị trên cùng */
}

.effect-image {
  width: 100%; /* Kích thước hình ảnh effect */
  zoom: 120%;
  height: auto; /* Kích thước hình ảnh effect tự điều chỉnh theo tỉ lệ */
}

/* Thêm style cho nút quay */
button {
  margin-top: 20px;
  padding: 10px 20px;
  font-size: 16px;
  background-color: brown;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #8b4513; /* Tương phản màu sắc */
}

button:disabled {
  background-color: #ccc; /* Màu sắc khi nút bị vô hiệu hóa */
  cursor: not-allowed;
}

.item {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  background-color: white; /* Màu trắng */
  display: flex;
  justify-content: center;
  align-items: center;
  transform: rotate(calc((var(--item-count) * 45deg) * (1 - var(--direction, 1))));
}

.wheel {
  position: relative;
  width: 300px; /* Kích thước vòng quay */
  height: 300px; /* Kích thước vòng quay */
  border-radius: 50%;
  background-color: rgb(255, 255, 255); /* Màu chủ đạo */
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  transition: transform var(--spin-duration) cubic-bezier(0.25, 0.1, 0.25, 1);
  margin: 0 -1px; /* Khoảng cách giữa hai vòng quay */
}

.wheel img {
  width: 100%; /* Kích thước hình ảnh */
  height: auto; /* Kích thước hình ảnh tự điều chỉnh theo tỉ lệ */
}

/* Tùy chỉnh grid layout khi quay */
.wheel.spinning {
  transition-timing-function: cubic-bezier(0.25, 0.1, 0.25, 1);
  transform: rotate(calc(var(--final-rotation, 0) * 360deg));
}















body{
    margin-top:20px;

  }
.rounded-circle {
    border-radius: 50%!important;
}
.border-5 {
    border-width: 5px;
}

.border-white {
    border-opacity: 1;
    border-color: rgba(255,255,255, 1) !important;
}

.shadow {
    box-shadow: 0 0.375rem 1.5rem 0 rgba(140,152,164,.125)!important;
}


img {
    max-width: 100%;
    height: auto;
}

.icon-sm {
    width: 1.5rem;
    height: 1.5rem;
    display: inline-flex;
    align-items: center;
    justify-content: center;
    text-align: center;
    font-size: 75%;
    line-height: normal;
}
.rounded-circle {
    border-radius: 50%!important;
}

.hover-top-in .hover-top--in {
    transition: ease-in-out all .35s;
    position: relative;
    top: 0
}

.hover-top-in:hover .hover-top--in {
    top: -10px
}

.me-1 {
    margin-right: 0.25rem!important;
}

.fw-700 {
    font-weight: 700!important;
}
.mb-1 {
    margin-bottom: 0.25rem!important;
}

.z-index-1 {
    z-index: 1!important;
}

.pt-6 {
    padding-top: 2.5rem!important;
}
.p-4 {
    padding: 1.5rem!important;
}
.mt-n4 {
    margin-top: -1.5rem!important;
}

.shadow {
    box-shadow: 0 0.375rem 1.5rem 0 rgba(var(--bs-gray-700-rgb),.125)!important;
}
.px-5 {
    padding-right: 2rem!important;
    padding-left: 2rem!important;
}
.position-relative {
    position: relative!important;
}
.overflow-hidden {
  overflow: hidden !important;
    zoom: 70%;
}


img.rounded-circle.border.border-5.border-white.shadow {
    zoom: 60%;
}

img.rounded-circle.border.border-5.border-white.shadow:hover {
    background-color: #0029e2;
    box-shadow: 0 1px 2px #999;
}

</style>
