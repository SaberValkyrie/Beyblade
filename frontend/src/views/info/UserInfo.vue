<template>
  <!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Thông tin cá nhân</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<app-header></app-header>
<br><br><br>

<body>
<div class="container">
<div class="main-body">
<div class="row">
<div class="col-lg-4">
<div class="card">

<div class="card-body">
<div class="d-flex flex-column align-items-center text-center">
  <img 
    alt="Admin" 
    class="rounded-circle p-1 bg-primary" 
    width="110"
      height="110"
    :src="baseUrl + '/files/' +  loggedInUser.avatar"
>
<div class="mt-3">
<h4 v-if="userInfo">{{ userInfo.fullname }}</h4>
<a class="change"></a>
<p class="text-muted font-size-sm"></p>
<input type="file" style="display: none" ref="imageInput" @change="uploadImage">
<button class="btn btn-primary" @click="chooseImage">Đổi Ảnh</button>
<button class="btn btn-outline-primary" @click="goToAddress">Thiết Lập Địa Chỉ</button>
</div>
</div>
<hr class="my-4">
<ul class="list-group list-group-flush">

<li class="list-group-item d-flex justify-content-between align-items-center flex-wrap" @click="goto('changePassWord')">
<h6 class="mb-0">
<img style="width: 2vw;" src="https://cdn2.iconfinder.com/data/icons/user-interface-outlined-2020/48/change_password-512.png">
Đổi Mật Khẩu</h6>
<span class="text-secondary"></span>
</li>

<li class="list-group-item d-flex justify-content-between align-items-center flex-wrap" @click="logout">
<h6 class="mb-0" >
  <img style="width: 2vw;" src="https://static.vecteezy.com/system/resources/previews/032/058/220/original/exit-icon-logout-3d-illustration-rendering-transparent-png.png">
Đăng Xuất</h6>
<span class="text-secondary"></span>
</li>
</ul>
</div>
</div>
</div>


<div class="col-lg-8">
<div class="card">
<div class="card-body">

 

<div class="row mb-3">
  
<div class="col-sm-3">
<h6 class="mb-0">Họ và Tên</h6>
</div>
<div class="col-sm-9 text-secondary">
<input type="text" class="form-control" v-model="userInfo.fullname">
</div>
</div>

<div class="row mb-3">
<div class="col-sm-3">
<h6 class="mb-0">Email</h6>
</div>
<div class="col-sm-9 text-secondary">
  <input type="email" class="form-control" v-model="userInfo.email">
</div>
</div>

<div class="row mb-3">
<div class="col-sm-3">
<h6 class="mb-0">Số Điện Thoại</h6>
</div>
<div class="col-sm-9 text-secondary">
<input type="number" class="form-control" v-model="userInfo.phone">
</div>
</div>

<div class="row mb-3">
<div class="col-sm-3">
<h6 class="mb-0">Ngày Sinh </h6>
</div>
<div class="col-sm-9 text-secondary">
  <input type="date" class="form-control" :value="formatBirthDate(userInfo.date)" @input="updateDate">
</div>
</div>

<div class="row mb-3">
  <div class="col-sm-3">
    <h6 class="mb-0">Giới Tính</h6>
  </div>

  <div class="col-sm-9 text-secondary">
    <select class="form-control" v-model="userInfo.gender">
      <option value="Nam">Nam</option>
      <option value="Nữ">Nữ</option>
      <option value="Khác">Khác</option>
    </select>
  </div>


  
</div>
<div class="row mb-3">
  
  <div class="col-sm-3">
  <h6 class="mb-0">Tên Shopee</h6>
  </div>
  <div class="col-sm-9 text-secondary">
  <input type="text" class="form-control" v-model="userInfo.shopeeName">
  </div>
  </div>

<div class="row">
<div class="col-sm-3"></div>
<div class="col-sm-9 text-secondary">
  <input type="button" class="btn btn-primary px-4" value="Lưu Thông Tin" @click="saveProfile(null)">
</div></div></div></div>



<div class="row">
<div class="col-sm-12">
<div class="card">
<div class="card-body">
<h5 class="d-flex align-items-center mb-3">Trạng Thái</h5>
<p>Lần Cập Nhật Cuối : {{ formatCreatedAt(userInfo.updatedTime) }}</p>
<div class="progress mb-3" style="height: 5px">
<div class="progress-bar bg-primary" role="progressbar" style="width: 100%" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"></div>
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
 import HelloWorld from '@/components/HelloWorld.vue'
 import Header from '@/views/support/Header.vue';
 import Footer from '@/views/support/Footer.vue';
import axios from 'axios';
import { baseWeb,baseURL } from '@/router/index';
 import { mapGetters } from 'vuex';
import moment from 'moment';
import store from '@/store';
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';
import { AccountService } from '@/core/service/accountservice';

 export default {
   name: 'Profile',
   computed: {
    ...mapGetters(['loggedInUser']),
  },
 created() {
  this.getInfo();
  this.loadUser();
  },
  data() {
    return {
      baseUrl : baseURL,
      token: localStorage.getItem('token'),
      accountService: new AccountService(),
      userInfo: {},
      newProfile: {
        fullname: '',
        email: '',
        phone: '',
        shopeeName: '',
        date: '',
        imageAvatar: ''
        },
    };
  },

  methods: {


    async getInfo() {
  try {
    this.userInfo = await this.accountService.getUserInfo(this.token);
    
  } catch (error) {
    toast.success('Hãy cập nhật thông tin của bạn');
    window.location.href ='/';
    console.error('Error fetching:', error);
  }
},

   chooseImage() {
    if (this.$refs.imageInput) {
      this.$refs.imageInput.click();
    }
  },
  goto(link){
window.location.href = '/' + link;
  },
  uploadImage(event) {
    this.accountService.upload(event).then(response => {
    this.saveProfile(response);
          setTimeout(() => {
        window.location.href ='/profile';
      }, 1000);
    })
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
    updateDate(event) {
    this.userInfo.date = event.target.value;
  },
    saveProfile(newImageName) {

  const birthDate = new Date(this.userInfo.date);
  const timestamp = birthDate.getTime();
  let updateImg = {}; // Khai báo đối tượng trước để sau đó có thể gán giá trị mới
  if (newImageName) {
    updateImg = { avatar: newImageName };
    this.loggedInUser.avatar = newImageName; // Cập nhật avatar của người dùng hiện tại
}else{
  updateImg = this.loggedInUser.avatar; // Cập nhật avatar của người dùng hiện tại
}
  // Tạo một đối tượng chứa thông tin người dùng để gửi lên server
  let updatedProfile = {
    fullname: this.userInfo.fullname,
    email: this.userInfo.email,
    phone: this.userInfo.phone,
    shopeeName: this.userInfo.shopeeName,
    gender: this.userInfo.gender,
    date: timestamp, // Sử dụng timestamp thay vì ngày tháng
    address: this.userInfo.address // Thêm địa chỉ nếu cần
  };
 
  // Gửi yêu cầu PUT tới API để cập nhật thông tin người dùng
  axios.put(`${baseURL}/user/edit/${this.token}`, updateImg).then(response => {}).catch(error => { 
    if (error.response.status === 415) {  } 
   });

  axios.put(`${baseURL}/profile/edit/${this.token}`, updatedProfile).then(response => {
      toast.success('Thông tin đã được cập nhật thành công!');
      this.loadUser();
          setTimeout(() => {
          window.location.href ='/profile';
				}, 1000);
    })
    .catch(error => {
      if (error.response.status === 500) {
      toast.error('Server không hỗ trợ chức năng này');
    }
    });
},

loadUser(){
   this.accountService.getUserLogin(this.token).then(res => {})
},
    getImageUrl(filename) {
        return `${baseURL}/files/upload/${filename}`;
      },
  // Phương thức cho chức năng chuyển hướng địa chỉ
  goToAddress() {
    this.$router.push('/address');
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

     HelloWorld
   }
  
 }
 </script>

<style scoped>
body{
background: #f7f7ff;
margin-top:20px;

zoom: 130%;
}
.card
.d-flex flex-column align-items-center text-center{
  width: 100%;
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
.card-body {
    -ms-flex: 1 1 auto;
    flex: 1 1 auto;
    min-height: 1px;
    padding: 1.25rem;
    margin-top: 17px;
}
input.btn.btn-primary.px-4 {
  background-color: #3e0a00;
    border: 1px solid blanchedalmond;
}
input.btn.btn-primary.px-4:hover{  
  background-color: rgb(51 51 51);
    border: none;
}
button.btn.btn-primary {
  background-color: #891700;
    border: 1px solid rgb(254 254 254);
}

    button.btn.btn-outline-primary {
      color: #000000;
    border: 1px solid rgb(211, 14, 0);
}
button.btn.btn-outline-primary:hover {
      color: #f3f3f3;
    border: 1px solid rgb(0, 0, 0);
}
button.btn.btn-primary:hover,
.btn-outline-primary:hover {
    color: #fff;
    background-color: #000000;
    border: 1px solid rgb(211, 14, 0);
}
</style>

 