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

    <div class="lucky-wheel" v-if="buoc == 0">

      
    <div class="select">
      
    <img v-if="!selectedBey.images" src ="https://i.pinimg.com/236x/4b/d2/3a/4bd23affc771182ef9569c87eeb2c1bd.jpg">

    <button v-if="buoc == 0 && !selectedBey.images" @click="select()">Chọn Bey</button>
    
</div>


<button v-if="selectedBey.images" @click="select()">Đổi Bey khác</button>

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





    <button v-if="selectedBey.images && hpBoss > 0 && hpMe > 0" @click="spinWheel" :disabled="spinning">Chiến Đấu ({{ selectedBey.price }} BeyPoint)</button>
    <div v-if=" selectedBey.images && hpMe == 0 && hpBoss == 0"  class="win">
      <img src="https://media3.giphy.com/media/aWYqyxiLMZg5dVV81Y/giphy.gif">
      <h2>Hòa</h2>
    </div>

    <div v-if="hpBoss <= 0 && hpMe > 0"  class="win">
      <img src="https://media4.giphy.com/media/wX7I4l8SfFyG8rqhsd/giphy.gif?cid=6c09b9521m9ha26wetn2vncs3anyrie18dr0kfp6ey3j82jy&ep=v1_internal_gif_by_id&rid=giphy.gif&ct=s">
    <img  src="https://i.pinimg.com/originals/de/38/61/de386180de84192a63b1c6186bd6e46c.gif">
    </div>
    <div v-if=" selectedBey.images && hpMe <= 0 && hpBoss > 0"  class="win">
      <img src="https://logos.flamingtext.com/Name-Logos/Lost-design-stripes-name.gif">
    <img  src="https://media0.giphy.com/media/TpsuCxwsNH8gatbpR5/giphy.gif?cid=6c09b952zptbp0zl5yaxdybsdknmk4dlfwusw4t67j3hc5kb&ep=v1_gifs_search&rid=giphy.gif&ct=g">
    </div>



    <div class="col-sm-4 cc" v-if="selectedBey.images" >
          <div class="concac">
            <div class="card-body1">
                <h5 class="d-flex align-items-center mb-3">Chỉ Số Bản Thân</h5>
                <p>Tấn Công : {{ convert(selectedBey.power) }}</p>
                <div class="progress mb-3" style="height: 5px">
                    <div class="progress-bar bg-primary" role="progressbar" :style="{ width: (selectedBey.power / 10000) * ((8 - selectedBey.season) * 2) + '%' }" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"></div>
                </div>
                <p>Sức Bền:{{ convert(hpMe > 0 ? hpMe : 0) }}</p>
                <div class="progress mb-3" style="height: 5px">
                    <div class="progress-bar bg-danger" role="progressbar"  :style="{ width: (hpMe / selectedBey.hp * 100) + '%' }" aria-valuemin="0" aria-valuemax="100"></div>
                </div>
            </div>
        </div>
        <div class="concac">
            <div class="card-body1">
                <h5 class="d-flex align-items-center mb-3">Chỉ Số Boss</h5>
                <p>Tấn Công : {{ convert(Boss.dame) }}</p>
                <div class="progress mb-3" style="height: 5px">
                    <div class="progress-bar bg-primary" role="progressbar" :style="{ width: (Boss.dame / 10000) * ((8 - Boss.bey.season) * 2) + '%' }" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"></div>
                </div>
                <p>Sức Bền:{{ convert(hpBoss > 0 ? hpBoss : 0) }}</p>
                <div class="progress mb-3" style="height: 5px">
                    <div class="progress-bar bg-danger" role="progressbar"  :style="{ width: (hpBoss / Boss.hp * 100) + '%' }" aria-valuemin="0" aria-valuemax="100"></div>
                </div>
            </div>
        </div>
    </div>










  </div>
  <div class="lucky-wheel">
<section id="team" class="section bg-gray-100" v-if="buoc == 1" >
  <h3 > Vui Lòng Chọn Hệ Quay Bạn Muốn </h3>
    <div class="container">
      
        <div class="row section-heading justify-content-center text-center wow fadeInUp"
         data-wow-duration="0.3s" data-wow-delay="0.3s"
          style="visibility: visible;
           animation-duration: 0.3s; 
           animation-delay: 0.3s;
            animation-name: fadeInUp;
   
            ">
 
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
                        <h6 class="fw-700 dark-color mb-1"></h6><small>{{ type.name }}</small>
                </div>
                </div>
            </div>
       
        </div>
    </div>
</section>

<div v-if="buoc == 2">


<div class="team-area sp">
    <div class="container">
       <div class="row">
          <div class="col-sm-6 col-md-4 col-lg-3 single-team" v-for="bey in listBeyType">
             <div class="inner">
                <div class="team-img" @click="chonbey(bey.id)">
                   <img class="demo" :src="bey.images" alt="Member Photo">
                </div>
                <div class="team-content">
                   <h4>{{bey.name}}</h4>
                
                </div>
             </div>
          </div>
       </div>
    </div>
 </div>





</div>

<div v-if="buoc == 3" > 

  


 <div class="container">
		<div class="main-body">
			<div class="row">
				<div class="col-lg-4">
					<div class="card">
						<div class="card-body">
							<div class="d-flex flex-column align-items-center text-center">
								<img :src="selectedBey.images" alt="Admin" class="rounded-circle p-1 bg-primary" width="110">
								<div class="mt-3">
									<h4>{{ selectedBey.name }}</h4>
									<p class="text-secondary mb-1">LV: {{ selectedBey.season }}</p>
									<p class="text-muted success">Giá :{{ selectedBey.price }} BeyPoint</p>
								</div>
                <button style="background-color: brown;" @click="setTypeBey(selectedType)">Hủy</button>

                <button @click="accept()">OK</button>
							</div>
					
						</div>
					</div>
				</div>
        
				<div class="col-lg-8">
			
					<div class="row">
						<div class="col-sm-12">
							<div class="card">
								<div class="card-body">
									<h5 class="d-flex align-items-center mb-3">Chỉ Số</h5>
									<p>Tấn Công : {{ convert(selectedBey.power) }}</p>
                  <div class="progress mb-3" style="height: 5px">
    <div class="progress-bar bg-primary" role="progressbar" :style="{ width: (selectedBey.power / 10000) * ((8 - selectedBey.season) * 2) + '%' }" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"></div>
</div>
									<p>Sức Bền:{{ convert(selectedBey.hp) }}</p>
									<div class="progress mb-3" style="height: 5px">
										<div class="progress-bar bg-danger" role="progressbar"  :style="{ width: (selectedBey.hp / 10000) * ((8 - selectedBey.season) * 2) + '%' }" aria-valuemin="0" aria-valuemax="100"></div>
									</div>
									<p>Tỉ Lệ Né Đòn : {{ convert(selectedBey.tiLeNeDon) }}%</p>
									<div class="progress mb-3" style="height: 5px">
										<div class="progress-bar bg-success" role="progressbar" :style="{ width: (selectedBey.tiLeNeDon) + '%' }"  aria-valuenow="89" aria-valuemin="0" aria-valuemax="100"></div>
									</div>
									<p>Tỉ Lệ Chí Mạng :{{ convert(selectedBey.crit) }}%</p>
									<div class="progress mb-3" style="height: 5px">
										<div class="progress-bar bg-warning" role="progressbar"  :style="{ width: (selectedBey.crit) + '%' }"  aria-valuemax="100"></div>
									</div>
									<p>Tỉ Lệ Gây Burst :{{ ((5 - selectedBey.type.id )* 3) }}%</p>
									<div class="progress" style="height: 5px">
										<div class="progress-bar bg-info" role="progressbar" :style="{ width: ((5 - selectedBey.type.id )* 3) + '%' }" aria-valuenow="66" aria-valuemin="0" aria-valuemax="100"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>




</div>

  <button v-if="buoc == 1" @click="cancel()">Hủy Chọn</button>

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
      imgBoss: "",
      img:'https://images-cdn.ubuy.co.in/635b867191f5134064149b62-beyblade-burst-b-189-booster-guilty.jpg',
      eff:'https://i.gifer.com/origin/4a/4a0225d3bbd093b282a33c369a368730_w200.gif',
      types:[],
      buoc:0,
      selectedType:{},
      listBeyType:[],
      selectedBey:{},
      Boss:{},
      hpBoss:0,
      hpMe:0,
      dameTru: 0,
    };
  },
  created(){
    this.getType()
    this.getBoss()
    this.stop()

 },
  methods: {
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
    setBey(id){
      this.gameService.getBeyByID(id).then(res => {
        this.selectedBey = res.data.data ;
        this.hpMe = this.selectedBey.hp;
        this.hpBoss = this.Boss.hp;
  

  }).catch(error => {
      toast.warning(error.response.data.message)
   });
    },
    getBoss(){
      this.gameService.getBosss().then(res => {
        this.Boss = res.data.data ;
        this.imgBoss = this.Boss.bey.images
        this.hpBoss = this.Boss.hp;
  }).catch(error => {
      toast.warning(error.response.data.message)
   });
    },

    setTypeBey(type){
      this.buoc = 2;
      this.selectedType = type;
      this.getBeyByType()
    },
    select(){
      this.buoc = 1;
    },
    done(){
      this.isSelect = false;
      
    },

    cancel(){
      this.buoc = 0;
    },

    chonbey(id){
      this.buoc = 3;
      this.setBey(id)
    },

    accept(){
      this.cancel()
      toast('Chọn Bey Thành Công')
    },

  spinWheel() {
  if (!this.spinning) {
    this.gameService.checkCoint(this.token,this.selectedBey.id).then(res => {
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

  const boss = {
    boss:this.Boss,
    me:this.selectedBey
  };
  this.gameService.spin(this.token,boss).then(res => {
    this.dameTru = res.data.data;
    this.hpBoss -= this.dameTru;
    toast.success(res.data.message)
  }) .catch(error => {
      toast.warning(error.response.data.message)
   });

   this.pst(boss)
},

pst(boss){
  this.gameService.pst(boss).then(res => {
    this.dameTru = res.data.data;
    this.hpMe -= this.dameTru;
    toast.success(res.data.message)
  }).catch(error => {
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
  background-color: #138500;
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
  width: 20rem;
    zoom: 280%;
    height: auto;
}

/* Tùy chỉnh grid layout khi quay */
.wheel.spinning {
  transition-timing-function: cubic-bezier(0.25, 0.1, 0.25, 1);
  transform: rotate(calc(var(--final-rotation, 0) * 360deg));
}















body{
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


img.imgshow {
    max-width: 218px;
}


img {
    max-width: 11rem;
    height: auto;
}
section#team {
    zoom: 210%;
}
.card {
    position: relative;
    display: flex;
    flex-direction: column;
    min-width: 0;
    word-wrap: break-word;
    background-color: #fff;
    background-clip: border-box;
    border: 0 solid transparent;
    border-radius: .25rem;
    margin-bottom: 1.5rem;
    box-shadow: 0 2px 6px 0 rgb(218 218 253 / 65%), 0 2px 6px 0 rgb(206 206 238 / 54%);
}
.me-2 {
    margin-right: .5rem!important;
}
.text-muted {
    --bs-text-opacity: 1;
    color: #5e0000 !important;
}
.card-body1 {
    flex: 1 1 auto;
    padding: 1rem 1rem;
    border: 1px solid #a79273;
    border-radius: 12%;
    padding: 24px;
    margin-top: 2rem;
    margin-right: 1rem;
    margin-left: 1rem;
    /* margin-bottom: -30px; */
}
.row {
    zoom: 140%;
    --bs-gutter-x: 1.5rem;
    --bs-gutter-y: 0;
    display: flex;
    flex-wrap: wrap;
    margin-top: calc(-1* var(--bs-gutter-y));
    margin-right: calc(-.5* var(--bs-gutter-x));
    margin-left: calc(-.5* var(--bs-gutter-x));
}

.inc {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
                margin-bottom: 30px; /* Khoảng cách giữa các cột */

    }


@media (min-width: 992px)
  .col-lg-3 {
    flex: 0 0 auto;
    width: 10%;
}
.select {
    zoom: 200%;
}

    
 
.concac {
    display: inline-flex;
}
.win{
  display: inline-flex;
}
</style>
