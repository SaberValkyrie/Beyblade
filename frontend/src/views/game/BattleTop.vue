<template>


<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" />
<app-header></app-header>
<br>
<br>
<br>



<div class="event-schedule-area-two bg-color pad100" v-if="!info">
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
    :style="{ backgroundColor: player.user.username == loggedInUser.username ? 'blanchedalmond' : 'white' }"
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
                                                <img :src="player.user && player.user.userId != 0 ? baseUrl + '/files/' + player.user.avatar : 'https://i.pinimg.com/originals/4b/32/f7/4b32f753e28ef8f590e3c9356c16f117.jpg'" alt="" />
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
                                                <img :src="player.selectBey ? player.selectBey.images : 'https://static.wikia.nocookie.net/beyga/images/5/5c/B01ValkyrieWingAccel.jpg'" alt="" />
                                                <h3><a href="#">{{ player.selectBey ? truncated(player.selectBey.name) : 'Valkyrie Wing Accel' }}</a></h3>

                                            </div>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="primary-btn" >
                                                <a class="btn btn-primary" @click="setThachDau(player)">{{ loggedInUser.username != player.user.username ? 'Thách Đấu' : 'Buff Chỉ Số'}}</a>
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
<div v-else>

{{ kethu }}


</div>
</template>








<script>
import axios from 'axios'; // Import Axios
import { baseURL } from '@/router/index';
import Header from '/src/views/support/Header.vue'
import Foooter from '/src/views/support/Footer.vue'
import { toast } from 'vue3-toastify';
import {GameService} from '@/core/service/game';
import 'vue3-toastify/dist/index.css';
import moment from 'moment';
import { mapGetters } from 'vuex';

export default {
    name:'TOP',
  components: {
    'app-header': Header,
    'app-footer': Foooter,
  },
  computed: {
    ...mapGetters(['loggedInUser']),
   
  },

  data() {
    return {
      service: new GameService(),
      baseUrl: baseURL,
      players: [],
      token: localStorage.getItem('token'),
      info:false,
      kethu:{},

    };
  },
  created() {
    this.getTop()

  },
  methods: {

    setInfo(s){
        this.info = s;
    },

    setThachDau(top){
        this.getTop()
        this.service.getUserTop(this.token,top).then(res => {
this.kethu = res.data.data;
// this.setInfo(true)
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
    getTop() {
    this.service.getTop()
        .then(res => {
            this.players = res.data.data;
        })
        .catch(error => {
            console.error("There was an error fetching the top players:", error);
            toast.error("Failed to load top players!");
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
    /* font-size: 1.5vw; */
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


</style>