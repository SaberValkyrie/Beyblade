<template>
  <app-header></app-header>
  <div class="lucky-wheel">
    <div class="wheel-container">
      <div class="wheel" :style="wheelStyle" :class="{ 'spinning': spinning }">
        <img :src="img" class="wheel-image">
      </div>
      <div class="effect" v-if="showEffect">
        <img src="https://i.gifer.com/origin/4a/4a0225d3bbd093b282a33c369a368730_w200.gif" class="effect-image">
      </div>
 
  
      <div class="wheel" :style="wheelStyle" :class="{ 'spinning': spinning }">
        <img :src="imgBoss" class="wheel-image">

      </div>

  

    </div>
    <button @click="spinWheel" :disabled="spinning">Quay</button>
  </div>
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
      img: 'https://m.media-amazon.com/images/I/71WgKP-cPSL._AC_UF1000,1000_QL80_DpWeblab_.jpg',
      imgBoss:'https://images-cdn.ubuy.co.in/635b867191f5134064149b62-beyblade-burst-b-189-booster-guilty.jpg',

    };
  },
  created(){
    this.stop()
 },
  methods: {
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
components: {
  'app-header': Header,
  'app-footer': Footer,
  },
rotateWheel() {
  setTimeout(() => {
    this.rotationCount++;
    if (this.rotationCount < this.maxRotationCount) {
      // Tiếp tục quay nếu chưa đạt số lần quay tối đa
      this.rotateWheel();
    } else {
      // Dừng quay khi đạt số lần quay tối đa
      // const randomIndex = Math.floor(Math.random() * this.items.length);
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
</style>
