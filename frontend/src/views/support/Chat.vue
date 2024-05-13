<template>
 <!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">


<title>Cuộc Trò Chuyện</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.min.css" rel="stylesheet">

	</head>
<body>
<main class="content">
<div class="container p-0">
<div class="card">
<div class="row g-0">
<div class="col-12 col-lg-5 col-xl-3 border-right user-list-container">

<div class="px-4 d-none d-md-block">
<div class="d-flex align-items-center">
<div class="flex-grow-1">
<input type="text" class="form-control my-3" placeholder="Search...">
</div>
</div>
</div>
    <a :href="'/chat/' + user.username" class="list-group-item list-group-item-action border-0"  v-for="user in listUser">
<div class="badge bg-success float-right"></div>
<div :class="[user.username == $route.params.username ? 'd-flex align-items-start okm' : 'd-flex align-items-start']">
<img :src="baseUrl + '/files/' + user.avatar" 
class="rounded-circle mr-1" alt="Vanessa Tucker" width="40" height="40">
<div class="flex-grow-1 ml-3">{{ user.username }} 
<div class="small">
  <span class="fas fa-circle chat-online"></span> Đang Hoạt Động
  <br>
 <span :class="[!user.status ? 'bold' : '']"> {{ user.newMessage }}</span>

 
</div>
</div>
</div>
</a>



<hr class="d-block d-lg-none mt-1 mb-0">
</div>
<div class="col-12 col-lg-7 col-xl-9">
<div class="py-2 px-4 border-bottom d-none d-lg-block">
<div class="d-flex align-items-center py-1">
<div class="position-relative">

    

<img :src="baseUrl + '/files/' + another.avatar" class="rounded-circle mr-1" alt="Sharon Lessman" width="40" height="40">
</div>
<div class="flex-grow-1 pl-3">
<strong>{{ $route.params.username }}</strong>
<div class="text-muted small"><em>Typing...</em></div>
</div>
<div>
<button class="btn btn-primary btn-lg mr-1 px-3"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-phone feather-lg"><path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z"></path></svg></button>
<button class="btn btn-info btn-lg mr-1 px-3 d-none d-md-inline-block"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-video feather-lg"><polygon points="23 7 16 12 23 17 23 7"></polygon><rect x="1" y="5" width="15" height="14" rx="2" ry="2"></rect></svg></button>
<button class="btn btn-light border btn-lg px-3"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-more-horizontal feather-lg"><circle cx="12" cy="12" r="1"></circle><circle cx="19" cy="12" r="1"></circle><circle cx="5" cy="12" r="1"></circle></svg></button>
</div>
</div>
</div>

<div class="messages" id="messages">
        <div class="message-container">
         
          <h1 class="error" v-if="connection_error"> Lỗi Kết Nối! </h1>

<!-- phần đã chạy được -->

       


        </div>
      </div>
<div class="position-relative">
<div class="chat-messages p-4" >

<div v-for="m in OldMessage" :key="m.messageId" style="clear:both">
            <div :class="m.other.username === $route.params.username ? 'chat-message-right pb-4' : 'chat-message-left pb-4'">  
               <div>
<img :src=" m.other.username === $route.params.username ? baseUrl + '/files/' + loggedInUser.avatar : baseUrl + '/files/' + another.avatar"
 class="rounded-circle mr-1" alt="Chris Wood" width="40" height="40">
<div class="text-muted small text-nowrap mt-2"></div>
</div>
<div class="flex-shrink-1 bg-light rounded py-2 px-3 mr-3">
{{ m.text }}
<br><br>

<small class="font-weight-100 mb-1">{{ formatCreatedAt(m.timeChat) }}</small>
</div>
 </div>
</div>
          <div v-for="(m,idx) in messages" :key="'m-' + idx" style="clear:both"  >
           <div :class=" m.from=='me' ? 'chat-message-right pb-4': 'chat-message-left pb-4' " >

            <img :src=" m.from === 'me' ? baseUrl + '/files/' + loggedInUser.avatar : baseUrl + '/files/' + another.avatar"
 class="rounded-circle mr-1" alt="Chris Wood" width="40" height="40">
<div class="flex-shrink-1 bg-light rounded py-2 px-3 mr-3">
<div class="font-weight-bold mb-1">
</div>
{{ m.message }}
</div>
          </div>


          </div> 



</div>
</div>
<div class="flex-grow-0 py-3 px-4 border-top">
<div class="input-group">
<input  t v-model="new_message" type="text" placeholder="Nhập Tin Nhắn" @keyup.enter="send_message">
<button  class="btn btn-primary"   @click="send_message" >Enter</button>
</div>
</div>
</div>
</div>
</div>
</div>
</main>

	
</body>
</html>
  <div class="container">
     
  </div>
</template>

<script>
import Header from '@/views/support/Header.vue';
import Footer from '@/views/support/Footer.vue';
import { mapGetters } from 'vuex';
import { baseURL } from '@/router/index';
import { toast } from 'vue3-toastify';
import axios from 'axios';
import 'vue3-toastify/dist/index.css';
import { AccountService } from '@/core/service/accountservice';

import moment from 'moment';
export default {
  name: 'HomeView',
  components: {
  'app-header': Header,
  'app-footer': Footer,
},
computed: {
  ...mapGetters(['cartProducts']),
  ...mapGetters(['loggedInUser']),
  ...mapGetters(['addressUser']),
  ...mapGetters(['userInfo']),
  ...mapGetters(['notifications']),
  displayedMessages() {
      // Hiển thị 5 tin nhắn gần nhất
      return this.messages.slice(-5);
    },
},
  data(){
    return {
      service: new AccountService(),
      OldMessage:[],
      connection_ready: false,
      connection_error: false,
      nickname: "",
      websocket: null,
      new_message: "",
      messages: [],
      baseUrl:baseURL,
      messageLimit: 5, // Số lượng tin nhắn gần nhất để hiển thị
      listUser:[],
      another:{},
     token : localStorage.getItem('token')
    }
  },
  methods:{
    saveMessage(new_message){

      const ortherUser = this.$route.params.username;
  axios.post(`${baseURL}/chat/${ortherUser}/${this.token}`, { new_message: new_message })
      .then(response => {
          })
      .catch(error => {
      });
    
    },
    getAll(){
      
  this.service.getAllUserChat(this.token)
      .then(response => {
        this.listUser = response.data.data;
          })
      .catch(error => {
      });
    
    },


    getUser(){
      const ortherUser = this.$route.params.username;
       axios.get(`${baseURL}/info/${ortherUser}`)
         .then(response => {
           this.another = response.data.data;
         })
         .catch(error => {    
            toast.error(error.response.data.message);
      
         });
    },
    getChat(){
      const ortherUser = this.$route.params.username;
      const token = localStorage.getItem('token'); // Lấy token từ localStorage
       axios.get(`${baseURL}/chat/${ortherUser}/${token}`)
         .then(response => {
           this.OldMessage = response.data.data;
         })
         .catch(error => {    
            toast.error(error.response.data.message);
      
         });
    },
    formatHour(timestamp) {
      return moment(timestamp).format('HH:mm'); // Định dạng giờ phút
  },   
    formatCreatedAt(timestamp) {
     return moment(timestamp).format('DD/MM/YYYY HH:mm'); // Định dạng ngày tháng giờ phút theo ý muốn
 },
    init_chat() {
      const ortherUser = this.$route.params.username;

    this.nickname = this.loggedInUser.username;
    const socket = new WebSocket(`ws://localhost:8080/ws?username=${ortherUser}`); // Thêm username vào URL của WebSocket
    socket.onopen = this.onSocketOpen;
    socket.onmessage = this.onSocketMessage;
    socket.onerror = this.onSockerError;
    this.websocket = socket;
},

    onSocketOpen(evt){
      this.connection_ready = true;
    },
    onSockerError(evt){
      this.connection_error = true;
    },

    onSocketMessage(evt) {
  // Parse tin nhắn từ chuỗi JSON
  const received = JSON.parse(evt.data);
  // Thêm tin nhắn mới vào danh sách
  this.messages.push({ from: received.from, message: received.message });
},

send_message() {
  // Thêm tin nhắn mới vào danh sách tin nhắn trước khi gửi
  this.messages.push({ from: "me", message: this.new_message });

    

  var to_send = { from: this.nickname, message: this.new_message, recipientUsername: this.$route.params.username };
  console.log(to_send)
  if (to_send) {
    this.websocket.send(JSON.stringify(to_send));
  }

  this.saveMessage(this.new_message);


  this.getAll();
  this.new_message = "";

},


  },

  created(){
    this.getAll();
    this.init_chat();
    this.getChat();
    this.getUser();
  },
  mounted() {

  }
}
</script>




<style scoped>
body{
top:0;
}
.input-group {
      zoom: 92%;
    margin-left: 52%;
    border: none;
    position: fixed;
    display: flex;
    right: 0;
    flex-wrap: wrap;
    align-items: stretch;
    width: 63%;
}

.d-flex.align-items-start.okm {
    box-shadow: 0px 1px 7px rgba(0, 0, 0, 0.1);
    border-radius: 2%;
    background-color: #b5dcff;
}
main.content {
    background-color: #beaf94;
}
.btn-info:hover {
    color: #fff;
    background-color: #9e0000;
    border-color: #ffffff;
}
.btn-info{
    color: #fff;
    background-color: #9e0000;
    border-color: #ffffff;
}
.btn.btn-primary:hover {
    background-color: #4f0000;
    color: var(--light-color);
}
.btn.btn-primary {
    background-color: #4f0000;
    color: var(--light-color);
}
.bold{
  font-weight: bold;
}
.user-list-container {
    height: 600px; /* Set a fixed height for the user list container */
    overflow-y: auto; /* Enable vertical scrolling */
    border-right: 1px solid #dee2e6; /* Add a border to separate from chat messages */
}
    	body{margin-top:20px;}

.chat-online {
    color: #34ce57
}
.row {
    display: -ms-flexbox;
    display: flex;
    zoom: 110%;
    -ms-flex-wrap: wrap;
    flex-wrap: wrap;
    margin-right: -15px;
    margin-left: -15px;
}
.chat-offline {
    color: #e4606d
}

.chat-messages {
    display: flex;
    flex-direction: column;
    max-height: calc(100vh - 200px); /* Giới hạn chiều cao của phần chat */
    overflow-y: auto; /* Tạo thanh cuộn nếu nội dung vượt quá chiều cao */
    padding-bottom: 15px; /* Khoảng cách dưới để tránh bị che bởi nút gửi tin nhắn */
}


.chat-message-left,
.chat-message-right {
    display: flex;
    flex-shrink: 0
}

.chat-message-left {
    margin-right: auto
}

.chat-message-right {
    flex-direction: row-reverse;
    margin-left: auto
}
.py-3 {
    padding-top: 1rem!important;
    padding-bottom: 1rem!important;
}
.px-4 {
    padding-right: 1.5rem!important;
    padding-left: 1.5rem!important;
}
.flex-grow-0 {
    flex-grow: 0!important;
}
.border-top {
    border-top: 1px solid #dee2e6!important;
}
    </style>