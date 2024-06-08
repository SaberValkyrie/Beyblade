<template>
    <!DOCTYPE html>
    <html lang="en">
    <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <app-header></app-header>
    <body>
  
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"  rel="stylesheet"  />
   
   
    <section id="team" class="team_area section-padding" v-if="buoc == 1 " >
        
    <div class="container">
    <h2 class="title_spectial">Danh Sách Đăng Ký</h2>
     <h2 @click="goto('/huongdan')">Hướng Dẫn Cách Chơi</h2>
     <br>
    <div class="row text-center">
    

           <div v-if="!dangky" class="col-lg-2 col-sm-4 col-xs-12 wow fadeInUp" 
     data-wow-duration="1s" data-wow-delay="0.1s"
      data-wow-offset="0" style="
      border-radius:20px;visibility: visible;
       animation-duration: 1s; animation-delay: 0.1s;
        animation-name: fadeInUp;">
    
    <div class="boss our-team" @click="setbuoc(2)">
    <div class="single-team">
    <img class="boss" src="https://png.pngtree.com/png-vector/20190615/ourlarge/pngtree-swordfencingsportsweapon-blue-dotted-line-line-icon-png-image_1484203.jpg" alt>
    <h3>Đăng Ký</h3>
    </div>
    <ul class="social">
    <li><a href="#" class="boss fa fa-user"></a></li>
    </ul>
    <br>
    </div>
    </div>



    <div v-for="user in listResgiser" class="col-lg-2 col-sm-4 col-xs-12 wow fadeInUp"
     data-wow-duration="1s" data-wow-delay="0.1s"
      data-wow-offset="0" style="visibility: visible;
       animation-duration: 1s; animation-delay: 0.1s;
        animation-name: fadeInUp;">
    
    <div  @click="battle(user)" class="our-team" :class="{'dead': user.isDie}">
    <div class="single-team">
    <img :src="user.is ? user.avatar: baseUrl +'/files/' + user.avatar" :class="['img-fluid', user.isDie ? 'dead-img' : 'boss']" alt>
    <h3>{{truncatedProductName(user.username)}}</h3>
    <h3>{{user.type.name}}</h3>
    </div>
    <ul class="social">
    <li><a href="#" class="fa fa-user"></a></li>
    </ul>



    
    <br>
    </div>
    </div>
    

 



    </div>
    </div>
    
    </section>

    <section id="bod" class="bod_area" v-if="buoc == 2">
    <div class="container">
    <h2 class="title_spectial">Chọn Hệ Beyblade Cần Đăng Kí</h2>
    
    <div class="row text-center">
    
    <div v-for="type in types" class="col-lg-3 col-sm-3 col-xs-12 wow fadeInLeft"
     data-wow-duration="1s" data-wow-delay="0.1s" 
     data-wow-offset="0" style="visibility: visible; 
     animation-duration: 1s; animation-delay: 0.1s; animation-name: fadeInLeft;"
    >
    <div class="our-bod">
    <div class="single-bod">
    <img :src="type.images" @click="select(type)" :class="selectedType.id == type.id ? 'boss img-fluid':'img-fluid'" alt>
    <h3>{{type.name}}</h3>
    <p>Chọn</p>
    </div>
    
    </div>
    </div>
    
    </div>
    </div>

    </section>
    


    <section id="bod" class="bod_area">
    <div class="container">
    <h2 class="title_spectial">Lịch Sử Đấu</h2>
    
    <div class="row text-center">
    
    <div v-for="type in ThongBao">
    <div class="our">
    <div class="single">
    <h3> {{formatHour(type.createAt)}} ✌  {{type.message}} ✌</h3>
    </div>
    
    </div>
    </div>
    
    </div>
    </div>

    </section>
    <app-footer></app-footer>
    </body>
    </html>
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
import { mapGetters } from 'vuex';

export default {
  name: 'BOT',
  computed: {
    ...mapGetters(['loggedInUser']),
   
  },
components: {
'app-header': Header,
'app-footer': Footer,
},
data() {

return {
 token : localStorage.getItem('token'),
 gameService: new GameService(),
 types:[],
 listResgiser:[],
 selectedType:{},
 kethu:{},
buoc:1,
dangky:false,
baseUrl : baseURL,
ThongBao:[],
};
},
created(){
this.getType()
this.check()


setInterval(() => {
    this.getUsers()
    this.getThongBao()
}, 1000);
},

methods: {
    battle(kethu){
        if(!this.dangky){
            toast.warning('Vui Lòng Đăng Ký Để Tiếp Tục')
            return
        }
        this.kethu = kethu;
        console.log(this.kethu)
        if(this.kethu.username === this.loggedInUser.username){
            toast.warning('Không thể đánh chính mình')
            return
        }
        if(this.kethu.isDie){
            toast.error('Không thể đánh bại người đã bị hạ')
            return
        }
    
this.gameService.checkBey(this.token).then(res => {
    localStorage.setItem('dichsinhton', JSON.stringify(kethu));   
     window.location.href = '/game/sinhton/battle';
}).catch(error => {
 toast.warning(error.response.data.message)
});
      


    },

    goto(s){

        window.location.href = s;
    },
    formatHour(timestamp) {
     return moment(timestamp).format('HH:mm'); // Định dạng ngày tháng giờ phút theo ý muốn
 },
    truncatedProductName(name) {
    const max = 20;
          if (name.length > max) {
              return name.substring(0, max) + '...';
          }
          return name;
      },
    select(t){
        this.selectedType = t
        this.buoc = 1;
        this.register()
    },
    setbuoc(s){
        this.buoc = s;
        this.dangky = true;
    },

    getThongBao(){
this.gameService.getThongBao(1).then(res => {
this.ThongBao = res.data.data 
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

check(){
this.gameService.checkRegister(this.token).then(res => {
this.dangky = false; 

}).catch(error => {
    this.dangky = true; 
});
},


register(){
this.gameService.register(this.token,this.selectedType).then(res => {
    toast.success(res.data.message)

}).catch(error => {
 toast.warning(error.response.data.message)
});
},


getUsers(){
this.gameService.listRes().then(res => {
this.listResgiser = res.data.data 
}).catch(error => {
 toast.warning(error.response.data.message)
});
},
},
}
</script>
<style scoped>
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
    animation: glowing-border 3s infinite;
}

.dead {
    background-color: #333;
    position: relative;
}

.dead-img {
    opacity: 0.5;
}

.dead h3::after {
    content: "Đã Chết";
    color: red;
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1.5rem;
    background-color: rgba(0, 0, 0, 0.7);
}
/* Other existing CSS */
body {
    margin-top: 20px;
    background-color: black;
    color: #9aa9c1;
}
a {
    text-decoration: none;
}
.team_title {
    color: #fff;
    margin-bottom: 60px;
    text-align: center;
    text-transform: capitalize;
    font-weight: 600;
}

.single-team {
    margin-bottom: 10px;
}
.single-team img {
    margin-bottom: 15px;
    width: 120px;
    border-radius: 50%;
    height: 120px;
    border: 10px solid rgba(255, 255, 255, 0.1);
}
.single-team h3 {
    margin-bottom: 0px;
    font-size: 18px;
    font-weight: 600;
    color: #fff;
}
.single-team p {
    margin-bottom: 0px;
}
.our-team .social {
    list-style: none;
    padding: 0;
    margin: 0;
    text-align: center;
    transition: all 0.3s ease 0s;
}
.our-team .social li {
    display: inline-block;
}
.our-team .social li a {
    display: block;
    width: 35px;
    height: 35px;
    line-height: 35px;
    font-size: 15px;
    color: #fff;
    position: relative;
    transition: all 0.3s ease-in-out 0s;
    border-radius: 30px;
    margin: 3px;
}
.our-team:hover .social li:nth-child(1) a {
    transition-delay: 0.3s;
}
.our-team:hover .social li:nth-child(2) a {
    transition-delay: 0.2s;
}
.our-team:hover .social li:nth-child(3) a {
    transition-delay: 0.1s;
}
.our-team:hover .social li:nth-child(4) a {
    transition-delay: 0s;
}
.our-team .social li a:hover {
    transition-delay: 0s;
}
.facebook {
    background: #1C58A1;
}
.facebook:hover {
    background: #fff;
    color: #1C58A1 !important;
}
.twitter {
    background: #0CBCE3;
}
.twitter:hover {
    background: #fff;
    color: #0CBCE3 !important;
}
.google {
    background: #F04537;
}
.google:hover {
    background: #fff;
    color: #F04537 !important;
}
/*START BOARD DIRECTOR*/
.bod_area {
    padding-bottom: 80px;
}
.our-bod {}
@media only screen and (max-width: 768px) {
    .our-bod {
        margin-bottom: 30px;
    }
}
.single-bod {
    margin-bottom: 10px;
}
.single-bod img {
    margin-bottom: 15px;
    width: 120px;
    border-radius: 50%;
    height: 120px;
    border: 10px solid rgba(255, 255, 255, 0.1);
}
.single-bod h3 {
    margin-bottom: 0px;
    font-size: 18px;
    font-weight: 600;
    color: #fff;
}
.single-bod p {
    margin-bottom: 0px;
}
.our-bod .social {
    list-style: none;
    padding: 0;
    margin: 0;
    text-align: center;
    transition: all 0.3s ease 0s;
}
.our-bod .social li {
    display: inline-block;
}
.our-bod .social li a {
    display: block;
    width: 35px;
    height: 35px;
    line-height: 35px;
    font-size: 15px;
    color: #fff;
    position: relative;
    transition: all 0.3s ease-in-out 0s;
    border-radius: 30px;
    margin: 3px;
}
.our-bod:hover .social li:nth-child(1) a {
    transition-delay: 0.3s;
}
.our-bod:hover .social li:nth-child(2) a {
    transition-delay: 0.2s;
}
.our-bod:hover .social li:nth-child(3) a {
    transition-delay: 0.1s;
}
.our-bod:hover .social li:nth-child(4) a {
    transition-delay: 0s;
}
.our-bod .social li a:hover {
    transition-delay: 0s;
}
.title_spectial {
    color: #fff;
    text-align: center;
    font-weight: 600;
    position: relative;
    margin-bottom: 60px;
    text-transform: uppercase;
    font-size: 24px;
}
.bod_area {
    padding-bottom: 80px;
    background-color: black;
}
.section-padding {
    padding: 80px 0;
    background-color: black;
    padding-top: 100px;
}
.our-team {
    background-color: black;
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
    animation: glowing-border 3s infinite;
}

.dead {
    background-color: #333;
    position: relative;
}

.dead-img {
    opacity: 0.5;
}

.dead h3::after {
    content: "Đã Chết";
    color: red;
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1.5rem;
    background-color: rgba(0, 0, 0, 0.7);
}

</style>
