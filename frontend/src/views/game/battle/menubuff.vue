<template>
    <app-header></app-header>
<section id="pricing" class="pricing-content section-padding">
	<div class="container">					
		<div class="section-title text-center">
			<h2>Buff Chỉ Số</h2>
			<div style="zoom :150%">
                <p>HP của bạn sẽ được buff theo cấp số nhân để tăng khả năng giữ top,hãy chọn loại phù hợp với yêu cầu nhé!</p>
            <a>Nếu bạn muốn buff bằng {{ type == 1 ? 'Số Dư' : 'BeyPoint' }} hãy </a><span style="color:blue" @click="setStatus()">Bấm Vào Đây</span>
            </div>
		</div>				
		<div class="row text-center">									
			<div class="col-lg-4 col-sm-6 col-xs-12 wow fadeInUp" data-wow-duration="1s" data-wow-delay="0.1s" data-wow-offset="0" style="visibility: visible; animation-duration: 1s; animation-delay: 0.1s; animation-name: fadeInUp;">
				<div class="pricing_design">
					<div class="single-pricing">
						<div class="price-head">		
							<h2>BUFF x2</h2>
							<h1>{{ type == 1 ? '60K BeyPoint'  : '3.000 VNĐ'}}</h1>
							<span>/Ngày</span>
						</div>
						<ul>
							<li><b>Lưu ý:</b>khi hết 1 ngày sẽ phải mua lại</li>
						</ul>
						<div class="pricing-price">
							
						</div>
						<a @click="buyBuff(2)" style="color: white;" class="price_btn">Mua Ngay</a>
					</div>
				</div>
			</div><!--- END COL -->	
            <div class="col-lg-4 col-sm-6 col-xs-12 wow fadeInUp" data-wow-duration="1s" data-wow-delay="0.1s" data-wow-offset="0" style="visibility: visible; animation-duration: 1s; animation-delay: 0.1s; animation-name: fadeInUp;">
				<div class="pricing_design">
					<div class="single-pricing">
						<div class="price-head">		
							<h2>BUFF x3</h2>
							<h1>{{ type == 1 ? '80K BeyPoint'  : '4.000 VNĐ'}}</h1>
							<span>/Ngày</span>
						</div>
						<ul>
							<li><b>Lưu ý:</b>khi hết 1 ngày sẽ phải mua lại</li>
						</ul>
						<div class="pricing-price">
							
						</div>
						<a @click="buyBuff(3)" style="color: white;" class="price_btn">Mua Ngay</a>
					</div>
				</div>
			</div><!--- END COL -->	
            <div class="col-lg-4 col-sm-6 col-xs-12 wow fadeInUp" data-wow-duration="1s" data-wow-delay="0.1s" data-wow-offset="0" style="visibility: visible; animation-duration: 1s; animation-delay: 0.1s; animation-name: fadeInUp;">
				<div class="pricing_design">
					<div class="single-pricing">
						<div class="price-head">		
							<h2>BUFF x5</h2>
							<h1>{{ type == 1 ? '100K BeyPoint'  : '5.000 VNĐ'}}</h1>
							<span>/Ngày</span>
						</div>
						<ul>
							<li><b>Lưu ý:</b>khi hết 1 ngày sẽ phải mua lại</li>
						</ul>
						<div class="pricing-price">
							
						</div>
						<a @click="buyBuff(5)" style="color: white;" class="price_btn">Mua Ngay</a>
					</div>
				</div>
			</div><!--- END COL -->	
              
		</div><!--- END ROW -->
	</div><!--- END CONTAINER -->
</section>
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
    name: 'BUFF',
  components: {
  'app-header': Header,
  'app-footer': Footer,
  },
  data() {
  
  return {
  token : localStorage.getItem('token'),
  gameService: new GameService(),
  type:1,
  };
  },
  created(){

  },
  
  methods: {
    setStatus(){
     if(this.type == 0){
        this.type = 1;
     }else{
        this.type = 0;
     }
    },
    buyBuff(x){
        this.gameService.buyBUFF(this.token,x,this.type).then(res =>{
            toast.success(res.data.message)
            setTimeout(() => {
          window.location.href = "/game/top";
				}, 2000);
        }).catch(error => {
            toast.error(error.response.data.message)
});
    }
  
  },
  
  computed: {

  },
  };
  </script>
  
  
  
  
  
  
  <style scoped>
  body{margin-top:20px;
background:#DCDCDC;
}
.pricing-content{position:relative;}
.pricing_design{
    position: relative;
    margin: 0px 15px;
}
.pricing_design .single-pricing{
    background: #a5a5a5;
    padding: 5vw;
    border-radius: 12%;
    box-shadow: 0 10px 40px 27px rgb(110 110 110 / 20%);
    position: relative;
    z-index: 2;
}
.pricing_design .single-pricing:before{
    content: "";
    background-color: #fff;
    width: 100%;
    height: 100%;
    border-radius: 18px 18px 190px 18px;
    border: 1px solid #eee;
    position: absolute;
    bottom: 0;
    right: 0;
    z-index: -1;
}
.price-head{}
.price-head h2 {
	margin-bottom: 20px;
	font-size: 26px;
	font-weight: 600;
}
.price-head h1 {
	font-weight: 600;
	margin-top: 30px;
	margin-bottom: 5px;
}
.price-head span{}

.single-pricing ul{list-style:none;margin-top: 30px;}
.single-pricing ul li {
	line-height: 36px;
}
.single-pricing ul li i {
	background: #000000;
	color: #fff;
	width: 20px;
	height: 20px;
	border-radius: 30px;
	font-size: 11px;
	text-align: center;
	line-height: 20px;
	margin-right: 6px;
}
.pricing-price{}

.price_btn {
	background: #0f0202;
	padding: 10px 30px;
	color: #fff;
	display: inline-block;
	margin-top: 20px;
	border-radius: 2px;
	-webkit-transition: 0.3s;
	transition: 0.3s;
}
.price_btn:hover{background:#640000;}
a{
text-decoration:none;    
}

.section-title {
    margin-bottom: 60px;
}
.text-center {
    text-align: center!important;
}

.section-title h2 {
    font-size: 45px;
    font-weight: 600;
    margin-top: 0;
    position: relative;
    text-transform: capitalize;
}
  </style>
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  