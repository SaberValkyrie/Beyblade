
<template>
  
  <div v-if="menuBuff">
    <menu_buff></menu_buff>
    </div>
<div class="event-schedule-area-two bg-color pad100" v-if="!info && !thachdau && !menuBuff">
    <app-header></app-header>
   
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" />

    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="section-title text-center">
                    <div class="title-text">
                        <h2>Bảng Xếp Hạng</h2>
                    </div>
                    <p>
                     Dưới đây là top {{ players.length }} người đang có mặt trên bảng xếp hạng<br />
            
                    </p>
                </div>
            </div>
            <!-- /.col end-->
        </div>
        <div class="row">
            <div class="col-lg-12">
            
                <div class="tab-content" id="myTabContent">
                    <div class="tab-pane fade active show" id="home" role="tabpanel">
                        <div class="table-responsive">
                            <table class="table">
                                <thead >
                                    <tr>
                                        <th class="text-center" scope="col">Hạng</th>
                                        <th scope="col">Người Dùng</th>
                                        <th scope="col">Chỉ Số</th>
                                        <th scope="col">Beyblade</th>
                                        <th class="text-center" scope="col">Hành Động</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr 
    class="inner-box" 
    v-for="player in players"
    :style="{ backgroundColor: loggedInUser && loggedInUser.username && player.user.username == loggedInUser.username ? 'blanchedalmond' : 'white' }"
    >
                             <th scope="row">
                                            <div v-if="player.top > 6" class="event" > 
                                                <span>{{ player.top}}</span>
                                            </div>
                                            <div v-else class="top">
                                                <img  class="imgtop" :src="'https://nrowin.club/image/28102022/bxh/' + (player.top)  + '.gif'">


</div>

                                        </th>
                                        <td>
                                            <div class="event-img">
                                                <img :class="player.selectBey.isBoss ? 'boss' : ''" :src="player.user && player.user.userId != 0 ? baseUrl + '/files/' + player.user.avatar : player.user.avatar" alt="" />
                                                <h3><a href="#">{{ player.user.username }}</a></h3>

                                            </div>
                                            
                                        </td>
                                        <td>
                                            <div class="event-wrap">
                                                <div class="meta">

                                                    <div class="organizers">
                                                        <a v-if="player.selectBey" href="#">{{ player.buff > 0 ? convert(player.selectBey.hp * player.buff) :  convert(player.selectBey.hp) }} HP</a>
                                                        <a v-else href="#">{{ player.buff > 0 ? convert(100000 * player.buff) :  100000 }}</a>

                                                    </div>

                                                    <div class="categories" v-if="player.buff > 0">
                                                        <a href="#">Buff X {{ player.buff }}</a>
                                                    </div>

                                                    <div class="time">
                                                      <span style="font-size: 1.5vw;">  Tỉ Lệ K/D: </span> 
                                                      <span v-if="player.lost == 0"  class="text-success"> {{ player.win + '/' + player.lost }}</span>

                                                      <span v-else :class="(player.win / player.lost ) > 1 ? 'text-success' : 'text-danger'">  {{ convert(player.win / player.lost) }}</span>
 </div>

                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="r-no">
                                                
                                                <div class="event-img">
                                                <img :class="player.selectBey.isBoss ? 'boss' : ''" :src="player.selectBey ? player.selectBey.images : 'https://static.wikia.nocookie.net/beyga/images/5/5c/B01ValkyrieWingAccel.jpg'" alt="" />
                                                <h3><a href="#">{{ player.selectBey ? truncated(player.selectBey.name) : 'Valkyrie Wing Accel' }}</a></h3>
                                       
                                            </div>
                           
                                            </div>
                             
                                        </td>
                                        <td>
                                            <div class="primary-btn" v-if="loggedInUser">
                                                <a class="btn" @click="setThachDau(player)">{{ loggedInUser.username != player.user.username ? 'Thách Đấu' : 'Buff Chỉ Số'}}</a>
                                            </div>
                                            <div class="primary-btn" v-else>
                                                <a class="btn">Không Có Hành Động</a>
                                            </div>
                                        </td>
                                    </tr>
                             
                                    
                                </tbody>
                            </table>
                        </div>
                    </div>
        
                </div>
                <div class="primary-btn text-center">
                    <a href="/game/boss" class="btn btn-primary">Bạn chưa đủ mạnh ? Hãy Tham Chiến Boss Ngay !!!!</a>
                </div>
            </div>
            <!-- /col end-->
        </div>
        <!-- /row end-->
    </div>
</div>
<div  class="event-schedule-area-two bg-color pad100" v-if="info">
    <section class="section about-section gray-bg" id="about">
            <div class="container">
                <div class="row align-items-center flex-row-reverse">
                    <div class="col-lg-6">
                        <div class="about-text go-to">
                            <h3 class="dark-color">Thông tin đối thủ</h3>
                    
                         
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <div class="about-avatar">
                            <img  class="avatar" :src="kethu.user.userId == 0 ? kethu.user.avatar : baseUrl + '/files/' + kethu.user.avatar" title="" alt="">
                        </div>
                        
                    </div>
                </div>
                <br>
                <div class="counter">
                    <div class="row">
                        <div class="col-6 col-lg-3">
                            <div class="count-data text-center">
                                <h6 class="count h2" data-to="500" data-speed="500">Top {{ kethu.top }}</h6>
                                <p class="m-0px font-w-600">Người Xuất Sắc</p>
                            </div>
                        </div>
                        <div class="col-6 col-lg-3">
                            <div class="count-data text-center">
                                <h6 class="count h2" data-to="150" data-speed="150">{{ kethu.win }}</h6>
                                <p class="m-0px font-w-600">Trận Thắng</p>
                            </div>
                        </div>
                        <div class="col-6 col-lg-3">
                            <div class="count-data text-center">
                                <h6 class="count h2" data-to="850" data-speed="850">{{convert(kethu.selectBey.hp * (kethu.buff > 0 ? kethu.buff : 1))}}</h6>
                                <p class="m-0px font-w-600">HP</p>
                            </div>
                        </div>
                        <div class="col-6 col-lg-3">
                            <div class="count-data text-center">
                                <h6 class="count h2" data-to="850" data-speed="850">{{convert(kethu.selectBey.power)}}</h6>
                                <p class="m-0px font-w-600">Dame</p>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <div class="concu">
                    <button @click="back()">Quay Lại</button>
                    <button @click="thachdauNgay(kethu)">Thách Đấu Ngay</button>
                </div>
               
            </div>
        </section>
    
</div>
<div class="thachdau" v-if="thachdau">
<app-top></app-top>
</div>

</template>
<script>
import axios from 'axios'; // Import Axios
import { baseURL } from '@/router/index';
import Header from '/src/views/support/Header.vue'
import Foooter from '/src/views/support/Footer.vue'
import TOP from '/src/views/game/battle/top.vue'
import BUFF from '/src/views/game/battle/menubuff.vue'
import { toast } from 'vue3-toastify';
import {GameService} from '@/core/service/game';

import 'vue3-toastify/dist/index.css';
import moment from 'moment';
import { mapGetters } from 'vuex';

export default {
    name:'TOPBattle',
  components: {
    'app-header': Header,
    'app-footer': Foooter,
    'app-top': TOP,
    'menu_buff': BUFF,
  },
  computed: {
    ...mapGetters(['loggedInUser']),
    wheelStyle() {
 return {
   '--item-count': this.items.length,
   '--spin-duration': `${this.spinDuration}s`,
 };
},
  },

  data() {
    return {
        gameService: new GameService(),
      baseUrl: baseURL,
      players: [],
      token: localStorage.getItem('token'),
      info:false,
      menuBuff:false,
      kethu:{},
      banthan:{},
      thachdau: false,
    };
  },
  created() {
    this.getTop()
    setInterval(() => {
        this.getTop()
}, 1000);
  },
  methods: {

//--------------------------------------------------


    thachdauNgay(kethu){
        this.info = false;
        this.thachdau = true;
        localStorage.setItem('kethuTop', JSON.stringify(kethu));    
    },







    setInfo(s){
        this.info = s;
    },

    setThachDau(top){
        this.getTop()
        
        if (this.loggedInUser.username === top.user.username) {
        this.menuBuff = true;
            return
        }


        this.gameService.getUserTop(this.token,top).then(res => {
this.kethu = res.data.data;
this.setInfo(true)
toast.success(res.data.message)
})    .catch(error => {
toast.error(error.response.data.message);
});



    },

    truncated(name) {
    const max = 18;
          if (name.length > max) {
              return name.substring(0, max) + '...';
          }
          return name;
      },
      back(){
        this.info = false
      },
    getTop() {
    this.gameService.getTop()
        .then(res => {
            this.players = res.data.data;
            this.banthan = this.players.find(player => player.user.username === this.loggedInUser.username);
        })
        .catch(error => {
    
        });
},


    formatCreatedAt(timestamp) {
      return moment(timestamp).format('DD/MM/YYYY HH:mm'); // Định dạng ngày tháng giờ phút theo ý muốn
  },   
  
 

convert(power) {
  const formatter = new Intl.NumberFormat('vi-VN', { maximumFractionDigits: 1 });

  if (power >= 1e9) {
      return formatter.format(power / 1e9) + 'Tỷ';
  } else if (power >= 1e6) {
      return formatter.format(power / 1e6) + 'Tr';
  } else if (power >= 1e3) {
      return formatter.format(power / 1e3) + 'K';
  } else {
      return formatter.format(power);
  }
},

go(link){
window.location.href = link;
}
  }
};
</script>




<style scoped>




body {
    margin-top: 20px;
}

.event-schedule-area .section-title .title-text {
    margin-bottom: 50px;
}

.event-schedule-area .tab-area .nav-tabs {
    border-bottom: inherit;
}

.event-schedule-area .tab-area .nav {
    border-bottom: inherit;
    display: flex;
    flex-direction: column;
    margin-top: 80px;
}

.event-schedule-area .tab-area .nav-item {
    margin-bottom: 75px;
}

.event-schedule-area .tab-area .nav-item .nav-link {
    text-align: center;
    font-size: 2vw;
    color: #333;
    font-weight: 600;
    border-radius: inherit;
    border: inherit;
    padding: 0px;
    text-transform: capitalize !important;
}

.event-schedule-area .tab-area .nav-item .nav-link.active {
    color: #4125dd;
    background-color: transparent;
}

.event-schedule-area .tab-area .tab-content .table {
    margin-bottom: 0;
    width: 100%;
}

.event-schedule-area .tab-area .tab-content .table thead td,
.event-schedule-area .tab-area .tab-content .table thead th {
    border-bottom-width: 1px;
    font-size: 2vw;
    font-weight: 600;
    color: #252525;
}

.event-schedule-area .tab-area .tab-content .table td,
.event-schedule-area .tab-area .tab-content .table th {
    border: 1px solid #b7b7b7;
    padding-left: 1vw;
}

.event-schedule-area .tab-area .tab-content .table tbody th .heading,
.event-schedule-area .tab-area .tab-content .table tbody td .heading {
    font-size: 1.6vw;
    text-transform: capitalize;
    margin-bottom: 1.6vw;
    font-weight: 500;
    color: #252525;
    margin-bottom: 0.6vw;
}

.event-schedule-area .tab-area .tab-content .table tbody th span,
.event-schedule-area .tab-area .tab-content .table tbody td span {
    color: #4125dd;
    font-size: 1.8vw;
    text-transform: uppercase;
    margin-bottom: 0.6vw;
    display: block;
}

.event-schedule-area .tab-area .tab-content .table tbody th span.date,
.event-schedule-area .tab-area .tab-content .table tbody td span.date {
    color: #656565;
    font-size: 1.4vw;
    font-weight: 500;
    margin-top: 1.5vw;
}

.event-schedule-area .tab-area .tab-content .table tbody th p {
    font-size: 1.4vw;
    margin: 0;
    font-weight: normal;
}

.event-schedule-area-two .section-title .title-text h2 {
    margin: 0 0 1.5vw;
}

.event-schedule-area-two ul.custom-tab {
    justify-content: center;
    border-bottom: 1px solid #dee2e6;
    margin-bottom: 3vw;
}

.event-schedule-area-two ul.custom-tab li {
    margin-right: 7vw;
    position: relative;
}

.event-schedule-area-two ul.custom-tab li a {
    color: #252525;
    font-size: 2.5vw;
    line-height: 2.5vw;
    font-weight: 600;
    text-transform: capitalize;
    padding: 3.5vw 0;
    position: relative;
}

.event-schedule-area-two ul.custom-tab li a:hover:before {
    width: 100%;
}

.event-schedule-area-two ul.custom-tab li a:before {
    position: absolute;
    left: 0;
    bottom: 0;
    content: "";
    background: #4125dd;
    width: 0;
    height: 0.2vw;
    transition: all 0.4s;
}

.event-schedule-area-two ul.custom-tab li a.active {
    color: #4125dd;
}

.event-schedule-area-two .primary-btn {
    margin-top: 4vw;
}

.event-schedule-area-two .tab-content .table {
    box-shadow: 0 0.1vw 3vw rgba(0, 0, 0, 0.1);
    margin-bottom: 0;
}

.event-schedule-area-two .tab-content .table thead {
    background-color: #007bff;
    color: #fff;
    font-size: 2vw;
}

.event-schedule-area-two .tab-content .table thead tr th {
    padding: 2vw;
    border: 0;
}

.event-schedule-area-two .tab-content .table tbody {
    background: #fff;
}

.event-schedule-area-two .tab-content .table tbody tr.inner-box {
    border-bottom: 1px solid #dee2e6;
}

.event-schedule-area-two .tab-content .table tbody tr th {
    border: 0;
    padding: 3vw 2vw;
    vertical-align: middle;
}

.event-schedule-area-two .tab-content .table tbody tr th .event-date {
    color: #252525;
    text-align: center;
}

.event-schedule-area-two .tab-content .table tbody tr th .event-date span {
    font-size: 5vw;
    line-height: 5vw;
    font-weight: normal;
}

.event-schedule-area-two .tab-content .table tbody tr td {
    padding: 3vw 2vw;
    vertical-align: middle;
}

.event-schedule-area-two .tab-content .table tbody tr td .r-no span {
    color: #252525;
}

.event-schedule-area-two .tab-content .table tbody tr td .event-wrap h3 a {
    font-size: 2vw;
    line-height: 2vw;
    color: #cf057c;
    transition: all 0.4s;
}

.event-schedule-area-two .tab-content .table tbody tr td .event-wrap h3 a:hover {
    color: #4125dd;
}

.event-schedule-area-two .tab-content .table tbody tr td .event-wrap .categories {
    display: inline-flex;
    margin: 1vw 0;
}

.event-schedule-area-two .tab-content .table tbody tr td .event-wrap .categories a {
    color: #252525;
    font-size: 1.6vw;
    margin-left: 1vw;
    transition: all 0.4s;
}

.event-schedule-area-two .tab-content .table tbody tr td .event-wrap .categories a:before {
    content: "\f07b";
    font-family: fontawesome;
    padding-right: 0.5vw;
}

.event-schedule-area-two .tab-content .table tbody tr td .event-wrap .time span {
    color: #252525;
}

.event-schedule-area-two .tab-content .table tbody tr td .event-wrap .organizers {
    display: inline-flex;
    margin: 1vw 0;
}

.event-schedule-area-two .tab-content .table tbody tr td .event-wrap .organizers a {
    color: #4125dd;
    font-size: 1.6vw;
    transition: all 0.4s;
}

.event-schedule-area-two .tab-content .table tbody tr td .event-wrap .organizers a:hover {
    color: #4125dd;
}

.event-schedule-area-two .tab-content .table tbody tr td .event-wrap .organizers a:before {
    content: "\f007";
    font-family: fontawesome;
    padding-right: 0.5vw;
}

.event-schedule-area-two .tab-content .table tbody tr td .primary-btn {
    margin-top: 0;
    text-align: center;
}

.event-schedule-area-two .tab-content .table tbody tr td .event-img img {
    width: 10vw;
    height: 10vw;
    border-radius: 50%;
}

.event-date {
    background-color: antiquewhite;
    border: 0.2vw solid #5c5c5c;
    border-radius: 50%;
    width: 5vw;
    height: 5vw;
    display: flex;
    align-items: center;
    justify-content: center;
}

img.imgtop {
    width: 5vw;
}

a.btn.btn-primary {

      zoom: 150%;
    padding: 1rem;
    border: 1px solid;

}

a.btn.btn-primary:hover {
   background-color: #656565;
}

span {
    font-size: 2vw;
}

table.table {
    /* width: 84vw; */
    zoom: 60%;
}

a.btn {
    background-color: #f3f3f3;
    zoom: 145%;
    color: red;
    padding: 1rem;
    border-radius: 14px;
    border: 1px solid #d10000;
}
a.btn:hover {
    background-color: #a3ff9b;
    zoom: 145%;
    color: red;
    padding: 1rem;
    border-radius: 14px;
    border: 1px solid #ffffff;
}



















.section {
    zoom: 80%;
    padding: 100px 0;
    position: relative;
}
.gray-bg {
    background-color: #f5f5f5;
}

.avatar {
    vertical-align: middle;
    border-style: none;
    width: 26vw;
    height: 26vw;
    border-radius: 50%;
    border: 9px solid #080178;
}
/* About Me 
---------------------*/
.about-text h3 {
  font-size: 45px;
  font-weight: 700;
  margin: 0 0 6px;
}
/* @media (max-width: 767px) {
  .about-text h3 {
    font-size: 35px;
  }
} */
.about-text h6 {
  font-weight: 600;
  margin-bottom: 15px;
}
/* @media (max-width: 767px) {
  .about-text h6 {
    font-size: 18px;
  }
} */
.about-text p {
  font-size: 18px;
  max-width: 450px;
}
.about-text p mark {
  font-weight: 600;
  color: #20247b;
}

.about-list {
  padding-top: 10px;
}
.about-list .media {
  padding: 5px 0;
}
.about-list label {
  color: #20247b;
  font-weight: 600;
  width: 88px;
  margin: 0;
  position: relative;
}
.about-list label:after {
  content: "";
  position: absolute;
  top: 0;
  bottom: 0;
  right: 11px;
  width: 1px;
  height: 12px;
  background: #20247b;
  -moz-transform: rotate(15deg);
  -o-transform: rotate(15deg);
  -ms-transform: rotate(15deg);
  -webkit-transform: rotate(15deg);
  transform: rotate(15deg);
  margin: auto;
  opacity: 0.5;
}
.about-list p {
  margin: 0;
  font-size: 15px;
}

/* @media (max-width: 991px) {
  .about-avatar {
    margin-top: 30px;
  }
} */

.about-section .counter {
  padding: 22px 20px;
  background: #ffffff;
  border-radius: 10px;
  box-shadow: 0 0 30px rgba(31, 45, 61, 0.125);
}
.about-section .counter .count-data {
  margin-top: 10px;
  margin-bottom: 10px;
}
.about-section .counter .count {
  font-weight: 700;
  color: #20247b;
  margin: 0 0 5px;
}
.about-section .counter p {
  font-weight: 600;
  margin: 0;
}
mark {
    background-image: linear-gradient(rgba(252, 83, 86, 0.6), rgba(252, 83, 86, 0.6));
    background-size: 100% 3px;
    background-repeat: no-repeat;
    background-position: 0 bottom;
    background-color: transparent;
    padding: 0;
    color: currentColor;
}
.theme-color {
    color: #fc5356;
}
.dark-color {
    color: #20247b;
}


button {
    margin-left: 0.5vw;
    margin-right: 0.5vw;
    background-color: white;
    border: 1px solid darkred;
    border-radius: 9px;
}
button:hover {
    margin-left: 0.5vw;
    margin-right: 0.5vw;
    color:white;
    background-color: rgb(92, 0, 0);
    border: 1px solid rgb(255, 255, 255);
    border-radius: 9px;
}


.concu button {
    zoom: 200%;
}




@keyframes glowing-border {
  1% {
    border-color: #ac2a02;
    box-shadow: 0 0 5px #8f1100;
  }
  50% {
    border-color: #3400c4;
    box-shadow: 0 0 20px #242c02;
  }
  100% {
    border-color: #6b6035;
    box-shadow: 0 0 5px #31018b;
  }
}
.boss {
    padding: 0vw;
    border: 3px solid;
    /* border-radius: 50%; */
    animation: glowing-border 3s infinite;
}





</style>