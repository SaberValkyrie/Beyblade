<template>
  <!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Địa chỉ</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" rel="stylesheet">

</head>

<app-header></app-header>
<body>
<!-- <link rel="stylesheet" href="https://allyoucan.cloud/cdn/icofont/1.0.1/icofont.css" integrity="sha384-jbCTJB16Q17718YM9U22iJkhuGbS0Gd2LjaWb4YJEZToOPmnKDjySVa323U+W7Fv" crossorigin="anonymous"> -->
<div class="container">
<div class="row">
<div class="col-md-3">
<div class="osahan-account-page-left shadow-sm bg-white h-100">
<div class="border-bottom p-4">
<div class="osahan-user text-center">
<div class="osahan-user-media">
  <img 
  alt="gurdeep singh osahan"
    class="mb-3 rounded-pill shadow-sm mt-1" :src="baseUrl + '/files/' + loggedInUser.avatar "
><div class="osahan-user-media-body">
<h6 class="mb-2">{{ userInfo.fullname }}</h6>
<p class="mb-1">{{ userInfo.phone }}</p>
<p class="text-black font-weight-bold">
  <a class="text-primary" data-toggle="modal" data-target="#edit-profile-modal" href="/profile">
    <i class="icofont-ui-edit"></i> Sửa Thông Tin </a></p>
</div>
</div>
</div>
</div>

</div>
</div>
<div class="col-md-9">
<div class="osahan-account-page-right shadow-sm bg-white p-4 h-100">
<div class="tab-content" id="myTabContent">
<div class="tab-pane fade  active show" id="addresses" role="tabpanel" aria-labelledby="addresses-tab">
<h4 class="font-weight-bold mt-0 mb-4">Danh Sách Địa Chỉ</h4>
<h6 >Địa Chỉ Mặc Định</h6>

<div class="row">
  
<div class="col-md-10"  v-for="address in addressUser" :key="address.addressId">
<div class="bg-white card addresses-item mb-4 border border-primary shadow" v-if="address.isDefault">
<div class="gold-members p-4">
<div class="media">
  
<div class="mr-3"><i class="icofont-ui-home icofont-3x"></i></div>

<div class="media-body">

  <div class="row mb-3">
  <div class="col-sm-3">
  <h6 class="mb-0">Tên</h6>
  </div>
  <div class="col-sm-9 text-secondary">
  <input v-if="edited" class="form-control" v-model="defaultA.fullname">
  <a v-else >{{defaultA.fullname}}</a>
  </div>
  </div>
  <div class="row mb-3">
  
  <div class="col-sm-3">
  <h6 class="mb-0">Số Điện Thoại</h6>
  </div>
  <div class="col-sm-9 text-secondary">
  <input v-if="edited" type="text" class="form-control" v-model="defaultA.phone">
  <a v-else >{{defaultA.phone}}</a>
  </div>
  </div>
  <div class="row mb-3">
  <div class="col-sm-3">
    <h6 class="mb-0">Địa Chỉ</h6>
  </div>
 
  <div v-if="edited" class="col-sm-8 text-secondary">

   <!-- Dropdown for city -->
<select class="form-select form-select-sm mb-3" v-model="selectedCity" @change="loadDistricts(selectedCity,defaultA)">
  <option value="" selected>Chọn Thành Phố</option>
  <option v-for="city in cities" :key="city.Id" :value="city">{{ city.Name }}</option>
</select>

<!-- Dropdown for district -->
<select class="form-select form-select-sm mb-3" v-model="selectedDistrict" @change="loadWards(selectedDistrict,defaultA)">
  <option value="" selected>Chọn Quận Huyện</option>
  <option v-for="district in districts" :key="district.Id" :value="district">{{ district.Name }}</option>
</select>
<!-- Dropdown for ward -->
<select class="form-select form-select-sm mb-3" v-model="selectedWard"  @change="saveWard(selectedWard,defaultA)">
  <option value="" selected>Chọn Phường Xá</option>
  <option v-for="ward in wards" :key="ward.Id" :value="ward">{{ ward.Name }}</option>
</select>

<input type="hidden" ref="address" id="address" name="address" value="">
  </div>
  <a v-else  class="col-sm-8 text-secondary" >{{defaultA.thanhPho}} - {{ defaultA.quanHuyen }} - {{ defaultA.phuongXa }}</a>
  <h2 id="result" style="display:none"></h2>

</div>
  <div class="row mb-3">
  
  <div class="col-sm-3">
  <h6 class="mb-0">Địa Chỉ Chi Tiết</h6>
  </div>
  <div class="col-sm-9 text-secondary">
  <input  v-if="edited"  type="text" class="form-control" v-model="defaultA.addressDetails">
  <a v-else >{{defaultA.addressDetails}}</a>

  </div>
  </div>


<p v-if="!edited" class="mb-0 text-black font-weight-bold">
  <a @click="edit(true)" class="text-primary mr-3" data-toggle="modal" data-target="#add-address-modal" href="#">
    <i class="icofont-ui-edit"></i> Chỉnh Sửa</a> 
  </p>
  <p v-else class="mb-0 text-black font-weight-bold">
  <a @click="editAdress(defaultA.addressId,defaultA)" class="text-primary mr-3" data-toggle="modal" data-target="#add-address-modal" href="#">
    <i class="icofont-ui-edit"></i>Lưu</a> 
  </p>
</div>
</div>
</div>
</div>
</div>







<div class="col-md-6">

</div>
</div>
<br>
<h6> Địa Chỉ Phụ</h6>
<div class="row">



<div class="col-md-10 "  v-for="address in addressUser" :key="address.addressId">
<div class="bg-white card addresses-item mb-4 shadow-sm" v-if="!address.isDefault">

<div class="gold-members p-4" >
<div class="media">
<div class="mr-3"><i class="icofont-ui-home icofont-3x"></i></div>
<div class="media-body">
<h5 class="mb-1 text-secondary"></h5>
<br>



<div class="row mb-3">
  <div class="col-sm-3">
  <h6 class="mb-0">Tên </h6>
  </div>
  <div class="col-sm-9 text-secondary">
  <input v-if="edited1" type="text" class="form-control" v-model="basicA.fullname">
  <a v-else>{{ basicA.fullname }}</a>
  </div>
  </div>
  <div class="row mb-3">
  
  <div class="col-sm-3">
  <h6 class="mb-0">Số Điện Thoại</h6>
  </div>
  <div class="col-sm-9 text-secondary">
  <input v-if="edited1" type="text" class="form-control" v-model="basicA.phone">
  <a v-else>{{ basicA.phone }}</a>
  </div>
  </div>
  <div class="row mb-3">
  <div class="col-sm-3">
    <h6 class="mb-0">Địa Chỉ</h6>
  </div>
  <div v-if="edited1" class="col-sm-8 text-secondary">

   <!-- Dropdown for city -->
<select class="form-select form-select-sm mb-3" v-model="selectedCity1" @change="loadDistricts1(selectedCity1,basicA)">
  <option value="" selected>Chọn Thành Phố</option>
  <option v-for="city in cities1" :key="city.Id" :value="city">{{ city.Name }}</option>
</select>

<!-- Dropdown for district -->
<select class="form-select form-select-sm mb-3" v-model="selectedDistrict1" @change="loadWards1(selectedDistrict1,basicA)">
  <option value="" selected>Chọn Quận Huyện</option>
  <option v-for="district in districts1" :key="district.Id" :value="district">{{ district.Name }}</option>
</select>
<!-- Dropdown for ward -->
<select class="form-select form-select-sm mb-3" v-model="selectedWard1"  @change="saveWard(selectedWard1,basicA)">
  <option value="" selected>Chọn Phường Xá</option>
  <option v-for="ward in wards1" :key="ward.Id" :value="ward">{{ ward.Name }}</option>
</select>

<input type="hidden" ref="address" id="address" name="address" value="">
  </div>
  <a v-else  class="col-sm-8 text-secondary" >{{basicA.thanhPho}} - {{ basicA.quanHuyen }} - {{ basicA.phuongXa }}</a>

  <h2 id="result" style="display:none"></h2>


</div>
  <div class="row mb-3">
  
  <div class="col-sm-3">
  <h6 class="mb-0">Địa Chỉ Chi Tiết</h6>
  </div>
  <div class="col-sm-9 text-secondary">
  <input  v-if="edited1"  type="text" class="form-control" v-model="basicA.addressDetails">
  <a v-else >{{basicA.addressDetails}}</a>

  </div>
  </div>

<p class="mb-0 text-black font-weight-bold"> 
  <a  v-if="edited1"  @click="editAdress(basicA.addressId,basicA)" class="text-primary mr-3" data-toggle="modal" data-target="#add-address-modal" href="#">
    <i class="icofont-ui-edit">
    </i> Lưu</a>
    <a  v-else  @click="edit1(true)" class="text-primary mr-3" data-toggle="modal" data-target="#add-address-modal" href="#">
    <i class="icofont-ui-edit">
    </i> Chỉnh Sửa</a>
     <a class="text-danger" data-toggle="modal" data-target="#delete-address-modal" href="#">
     </a>
      <a  class="text-primary mr-3" 
      style="margin-left: 56%;" 
      data-toggle="modal
      " data-target="#add-address-modal" 
      @click="setDefault(address.addressId)">
      <i></i> Đặt Làm Mặc Định</a>
    </p>
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
</div>
</div>
</body>
<app-footer></app-footer>
</html>
</template>

<script>
import Header from '@/views/support/Header.vue';
import Footer from '@/views/support/Footer.vue';
import axios from 'axios';
import { baseWeb,baseURL } from '@/router/index';
import { mapGetters } from 'vuex';
import moment from 'moment';
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';
import { AccountService } from '@/core/service/accountservice';

export default {
  name: 'Profile',
  computed: {
    ...mapGetters(['loggedInUser']),
 },
created() {
  this.getListAddress();
  this.loadCities(); // Call the function to load cities on component creation

 },
 data() {
   return {
     baseUrl : baseURL,
     userInfo : {},
     addressUser:[], //full
     defaultA:{},//mac dinh
     basicA: {}, //thuong
     service: new AccountService(),
     token: localStorage.getItem('token'),
     edited: false,
     edited1: false,
     cities: [],
      districts: [],
      wards: [],
      selectedCity: '',
      selectedDistrict: '',
      selectedWard: '',


      cities1: [],
      districts1: [],
      wards1: [],
      selectedCity1: '',
      selectedDistrict1: '',
      selectedWard1: ''
   };
 },
 methods: {
  
  async loadCities() {
      try {
        const response = await axios.get('https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json');
        this.cities = response.data; // Lưu danh sách các tỉnh/thành phố vào biến cities
        this.cities1 = response.data; // Lưu danh sách các tỉnh/thành phố vào biến cities

     this.load();
     this.loadBasic();

      } catch (error) {
        console.error('Lỗi khi tải danh sách tỉnh/thành phố:', error);
      }
    },
    load(){
      if (this.defaultA && this.defaultA.thanhPho) {
      this.selectedCity = this.cities.find(city => city.Name === this.defaultA.thanhPho);
      const cityId = this.selectedCity?.Id;
      if (cityId && this.defaultA.quanHuyen) {
        this.districts = this.selectedCity.Districts;
        this.selectedDistrict = this.districts.find(district => district.Name === this.defaultA.quanHuyen);
      }
      if (this.selectedDistrict && this.defaultA.phuongXa) {
        this.wards = this.selectedDistrict.Wards;
        this.selectedWard = this.wards.find(ward => ward.Name === this.defaultA.phuongXa);
      }
    }
    },
    loadBasic(){
      if (this.basicA && this.basicA.thanhPho) {
      this.selectedCity1 = this.cities1.find(city => city.Name === this.basicA.thanhPho);
      const cityId = this.selectedCity1?.Id;
      if (cityId && this.basicA.quanHuyen) {
        this.districts1 = this.selectedCity1.Districts;
        this.selectedDistrict1 = this.districts1.find(district => district.Name === this.basicA.quanHuyen);
      }
      if (this.selectedDistrict1 && this.basicA.phuongXa) {
        this.wards1 = this.selectedDistrict1.Wards;
        this.selectedWard1 = this.wards1.find(ward => ward.Name === this.basicA.phuongXa);
      }
    }
    },
 loadDistricts(city,address) {
  if (city) {
    this.districts = city.Districts;
    address.thanhPho = city.Name;
    this.selectedDistrict = ''; // Reset selected district when city changes
    this.wards = []; // Clear wards when city changes
  }
},
loadDistricts1(city,address) {
  if (city) {
    this.districts1 = city.Districts;
    address.thanhPho = city.Name;
    this.selectedDistrict1 = ''; // Reset selected district when city changes
    this.wards1 = []; // Clear wards when city changes
  }
},
loadWards1(district,address) {
  if (district) {
    address.quanHuyen = district.Name;
    this.wards1 = district.Wards;
    this.selectedWard1 = ''; // Reset selected district when city changes
  }
},

loadWards(district,address) {
  if (district) {
    address.quanHuyen = district.Name;
    this.wards = district.Wards;
    this.selectedWard = ''; // Reset selected district when city changes
  }
},
saveWard(ward,address){
  address.phuongXa = ward.Name;
},
  getListAddress() {
  this.service.getAddress(this.token).then(response => {
     this.addressUser = response; 
     this.defaultA = response.find(address => address.isDefault === true);
     this.basicA = response.find(address => address.isDefault === false);
    }).catch(error => { }); 
     },
     edit(status) {
      this.edited = status;
     },
     edit1(status) {
      this.edited1 = status;
     },
  updateDate(event) { userInfo.date = event.target.value; },
  
  setDefault(id){ this.service.setDefaultAdress(id,this.token).then(response => { this.getListAddress(); })},

  editAdress(id,ad) {
    this.service.getAddress(this.token).then(fullList => {
    const addressToEdit = fullList.find(address => address.addressId === id);
    if (addressToEdit) {
      this.editAddress = addressToEdit;
      const addressData = {
        fullname: ad.fullname,
        phone: ad.phone,
        thanhPho: ad.thanhPho,
        quanHuyen: ad.quanHuyen,
        phuongXa: ad.phuongXa,
        addressDetails: ad.addressDetails
      };
      this.service.editAddress(id, addressData, this.token)
        .then(response => {
          this.getListAddress();
        })
        .catch(error => {
        });
    } else {
      console.error('Không tìm thấy địa chỉ để chỉnh sửa');
    }
  }) .catch(error => {
    console.error('Lỗi khi lấy danh sách địa chỉ:', error);
  });
  this.edited = false;
  this.edited1 = false;
},
  formatBirthDate(date) {
   if (!date) return ''; // Đảm bảo xử lý trường hợp ngày sinh không tồn tại
   return moment(date).format('YYYY-MM-DD');
   },

   formatCreatedAt(timestamp) {
     return moment(timestamp).format('DD/MM/YYYY HH:mm'); // Định dạng ngày tháng giờ phút theo ý muốn
 },

  },
  components: {
    'app-header': Header,
    'app-footer': Footer,
  }
 
}
</script>


<style scoped>


.col-sm-8.text-secondary {
    display: flex;
}
.col-md-9{
  zoom: 70%;
}
body{
      zoom: 170%;
    margin-top:20px;
    background:#eee;
}
.payments-item img.mr-3 {
    width: 47px;
}
.order-list .btn {
    border-radius: 2px;
    min-width: 121px;
    font-size: 13px;
    padding: 7px 0 7px 0;
}
.osahan-account-page-left .nav-link {
    padding: 18px 20px;
    border: none;
    font-weight: 600;
    color: #535665;
}
.osahan-account-page-left .nav-link i {
    width: 28px;
    height: 28px;
    background: #535665;
    display: inline-block;
    text-align: center;
    line-height: 29px;
    font-size: 15px;
    border-radius: 50px;
    margin: 0 7px 0 0px;
    color: #fff;
}
.osahan-account-page-left .nav-link.active {
    background: #f3f7f8;
    color: #282c3f !important;
}
.osahan-account-page-left .nav-link.active i {
    background: #282c3f !important;
}
.osahan-user-media img {
    width: 90px;
}
.card offer-card h5.card-title {
    border: 2px dotted #000;
}
.card.offer-card h5 {
    border: 1px dotted #daceb7;
    display: inline-table;
    color: #17a2b8;
    margin: 0 0 19px 0;
    font-size: 15px;
    padding: 6px 10px 6px 6px;
    border-radius: 2px;
    background: #fffae6;
    position: relative;
}
.card.offer-card h5 img {
    height: 22px;
    object-fit: cover;
    width: 22px;
    margin: 0 8px 0 0;
    border-radius: 2px;
}
.card.offer-card h5:after {
    border-left: 4px solid transparent;
    border-right: 4px solid transparent;
    border-bottom: 4px solid #daceb7;
    content: "";
    left: 30px;
    position: absolute;
    bottom: 0;
}
img.mb-3.rounded-pill.shadow-sm.mt-1 {
    border: 2px solid #007bff!important;
}
.card.offer-card h5:before {
    border-left: 4px solid transparent;
    border-right: 4px solid transparent;
    border-top: 4px solid #daceb7;
    content: "";
    left: 30px;
    position: absolute;
    top: 0;
}
.form-control {
    margin-top: -5px;
    display: block;
    width: 88%;
    height: calc(1.5em + 0.75rem + 2px);
    padding: 0.375rem 0.75rem;
    font-size: 1rem;
    font-weight: 400;
    line-height: 1.5;
    color: #495057;
    background-color: #fff;
    background-clip: padding-box;
    border: 1px solid #ced4da;
    border-radius: 0.25rem;
    transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out;
}
.payments-item .media {
    align-items: center;
}
.payments-item .media img {
    margin: 0 40px 0 11px !important;
}
.reviews-members .media .mr-3 {
    width: 56px;
    height: 56px;
    object-fit: cover;
}
.order-list img.mr-4 {
    width: 70px;
    height: 70px;
    object-fit: cover;
    box-shadow: 0 .125rem .25rem rgba(0, 0, 0, .075)!important;
    border-radius: 2px;
}
.osahan-cart-item p.text-gray.float-right {
    margin: 3px 0 0 0;
    font-size: 12px;
}
.osahan-cart-item .food-item {
    vertical-align: bottom;
}

.h1, .h2, .h3, .h4, .h5, .h6, h1, h2, h3, h4, h5, h6 {
    color: #000000;
}

.shadow-sm {
    box-shadow: 0 .125rem .25rem rgba(0,0,0,.075)!important;
}

.rounded-pill {
    border-radius: 50rem!important;
}
a:hover{
    text-decoration:none;
}
.gold-members.p-4:hover{
  border: none;
  border-radius: 2px;
}
</style>