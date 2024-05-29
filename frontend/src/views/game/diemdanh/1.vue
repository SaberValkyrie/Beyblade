
  <template>
 <link href="https://cdnjs.cloudflare.com/ajax/libs/MaterialDesign-Webfont/4.9.95/css/materialdesignicons.css" rel="stylesheet">
<div class="container">
        <div class="row justify-content-center text-center">
            <div class="col-lg-12">
                <h2 class="font-weight-light">Ưu Đãi Mỗi Ngày</h2>
                <p class="text-muted mt-4 title-subtitle mx-auto">Hãy chọn ưu đãi phù hợp với nhu cầu của bạn nhé!</p>
            </div>
        </div>                
        <div class="row mt-5">
            <div class="col-lg-4">
        		<div class="bg-white mt-3 price-box">
        			<div class="pricing-name text-center">
        				<h4 class="mb-0">Ưu Đãi 1</h4>
        			</div>
        			<div class="plan-price text-center mt-4">
        				<h1 class="text-custom font-weight-normal mb-0">FREE !!!    </h1>
        			</div>
        			<div class="price-features mt-5">
        				<p> Mức Giá: <span class="font-weight-bold">0 VNĐ</span><i class="mdi mdi-check"></i></p>
        				<p>Reset: <span class="font-weight-bold">Mỗi Ngày</span><i class="mdi mdi-check"></i></p>
        				<p>Loại Ưu Đãi: <span style="color: green" class="font-weight-bold">Điểm Danh</span><i class="mdi mdi-check"></i></p>
        				<p> Nhận BeyPoint: <span class="font-weight-bold">Ngẫu Nhiên</span><i class="mdi mdi-check"></i></p>
        				<p> Nhận Voucher: <span class="font-weight-bold">No</span><i class="mdi mdi-close"></i></p>
                        <p> Nhận GIFTCODE: <span style="color: red"  class="font-weight-bold">Bey Hạn Sử Dụng</span><i class="mdi mdi-close"></i></p>
</div>
        			<div class="text-center mt-5">
        				<a @click="diemdanh(0)" class="btn btn-custom">{{ loggedInUser.diemdanh ? 'Đã Điểm Danh' : 'Điểm Danh' }}</a>
        			</div>
        		</div>
        	</div>
        	<div class="col-lg-4">
        		<div class="bg-white mt-3 price-box">
        			<div class="pricing-name text-center">
        				<h4 class="mb-0">Ưu Đãi 2</h4>
        			</div>
        			<div class="plan-price text-center mt-4">
        				<h1 class="text-custom font-weight-normal mb-0">20.000<span> VNĐ</span></h1>
        			</div>
        			<div class="price-features mt-5">
        				<p> Mức Giá: <span class="font-weight-bold">20K VNĐ</span><i class="mdi mdi-check"></i></p>
        				<p>Reset: <span class="font-weight-bold">No</span><i class="mdi mdi-close"></i></p>
        				<p>Loại Ưu Đãi: <span style="color: green" class="font-weight-bold">Mở Thành Viên</span><i class="mdi mdi-check"></i></p>
        				<p> Nhận BeyPoint: <span class="font-weight-bold">Ngẫu Nhiên</span><i class="mdi mdi-check"></i></p>
        				<p> Nhận Voucher: <span style="color: red"  class="font-weight-bold">Tỉ Lệ 80%</span><i class="mdi mdi-check"></i></p>
                        <p> Nhận GIFTCODE: <span style="color: green"  class="font-weight-bold">Bey Vĩnh Viễn</span><i class="mdi mdi-check"></i></p>
</div>
        			<div class="text-center mt-5">
        				<a  @click="diemdanh(1)" class="btn btn-custom">{{ loggedInUser.active ? 'Đã Mở Thành Viên' : 'Mở Ngay' }}</a>
        			</div>
        		</div>
        	</div>
        	<div class="col-lg-4">
        		<div class="bg-white mt-3 price-box">
        			<div class="pricing-name text-center">
        				<h4 class="mb-0">Ưu Đãi 3</h4>
        			</div>
        			<div class="plan-price text-center mt-4">
        				<h1 class="text-custom font-weight-normal mb-0">5.000<span> VNĐ</span></h1>
        			</div>
                    <div class="price-features mt-5">
        				<p> Mức Giá: <span class="font-weight-bold">5K VNĐ</span><i class="mdi mdi-check"></i></p>
        				<p>Reset: <span class="font-weight-bold">Mỗi Ngày</span><i class="mdi mdi-check"></i></p>
        				<p style="color: green;" >Yêu Cầu: <span class="font-weight-bold">Phải Mở Thành Viên Trước</span><i class="mdi mdi-check"></i></p>
        				<p> Nhận BeyPoint: <span class="font-weight-bold">Ngẫu Nhiên</span><i class="mdi mdi-check"></i></p>
        				<p> Nhận Voucher: <span class="font-weight-bold">Tỉ lệ 25%</span><i class="mdi mdi-check"></i></p>
                        <p> Nhận GIFTCODE: <span style="color: green"  class="font-weight-bold">Bey Vĩnh Viễn</span><i class="mdi mdi-check"></i></p>
	</div>
        			<div class="text-center mt-5">
        				<a  @click="diemdanh(2)" class="btn btn-custom">{{ loggedInUser.diemdanh ? 'Đã Điểm Danh' : 'Điểm Danh VIP' }}</a>
        			</div>
        		</div>
        	</div>
        </div>
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
import { AccountService } from '@/core/service/accountservice';
import { mapGetters } from 'vuex';
import Chart from 'chart.js/auto';
import moment from 'moment';

export default {
  name: 'App1',
  computed: {
    ...mapGetters(['loggedInUser']),
  },
  data(){
    return {
 token : localStorage.getItem('token'),
 gameService: new GameService(),
 accountService: new AccountService(),
};
  },
  created() {
	this.getLogin()
},
  methods:{
    diemdanh(type){
    this.gameService.diemdanh(this.token,type).then(res => {
		toast.success(res.data.message)
	}).catch(error => {
 toast.warning(error.response.data.message)
});

    },
	getLogin(){
		this.accountService.getUserLogin(this.token).then(res => {
		}).catch(error => {
 toast.warning(error.response.data.message)
});
	},


    go(link){
      window.location.href = link;
  },
  },
}
</script>



















<style scoped>
body{
    background-color: #f8f9fa!important;
    margin-top:20px;
}

.text-custom,
.navbar-custom .navbar-nav li a:hover,
.navbar-custom .navbar-nav li a:active,
.navbar-custom .navbar-nav li.active a,
.service-box .services-icon,
.price-features p i,
.faq-icon,
.social .social-icon:hover {
    color: #f6576e !important;
}

.bg-custom,
.btn-custom,
.timeline-page .timeline-item .date-label-left::after,
.timeline-page .timeline-item .duration-right::after,.back-to-top:hover {
    background-color: #f6576e;
}
a.btn.btn-custom {
    font-size: 2rem;
    color: rgb(131, 0, 0);
    background-color: rgb(245, 245, 245);
    border: 1px solid rgb(97, 0, 0);
}
a.btn.btn-custom:hover {
    font-size: 2rem;
    color: rgb(241, 241, 241);
    background-color: brown;
    border: 1px solid white;
}
.btn-custom,
.custom-form .form-control:focus,
.social .social-icon:hover,
.registration-input-box:focus {
    border-color: #f6576e;
}

.service-box .services-icon,
.price-features p i {
    background-color: rgba(246, 87, 110, 0.1);
}

.btn-custom:hover,
.btn-custom:focus,
.btn-custom:active,
.btn-custom.active,
.btn-custom.focus,
.btn-custom:active,
.btn-custom:focus,
.btn-custom:hover,
.open > .dropdown-toggle.btn-custom {
    border-color: #e45267;
    background-color: #e45267;
}

p {
    font-size: 1.2rem;
    color: #000000;
}
.price-box {
    padding: 40px 50px;
    border:1px solid;
}

.plan-price h1 span {
    font-size: 16px;
    color: #000;
}

.price-features p i {
    height: 20px;
    width: 20px;
    display: inline-block;
    text-align: center;
    line-height: 20px;
    font-size: 14px;
    border-radius: 50%;
    margin-right: 20px;
}
</style>
  