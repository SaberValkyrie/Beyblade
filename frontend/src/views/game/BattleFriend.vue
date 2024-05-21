<template>

<table width="100%" height="100%" bgcolor="#E4E6E9" cellspacing="0" cellpadding="0" border="0" v-if="!bot && !friend">
    <tbody>
        <tr>
            <td width="100%" align="center" valign="top" bgcolor="#E4E6E9" style="background-color:#E4E6E9; min-height: 200px;">
                <table>
                    <tbody>
                        <tr>
                            <td class="table-td-wrap" align="center" width="458">
                                <table class="table-space" height="18" style="height: 18px; font-size: 0px; line-height: 0; width: 450px; background-color: #e4e6e9;" width="450" bgcolor="#E4E6E9" cellspacing="0" cellpadding="0" border="0">
                                    <tbody>
                                        <tr>
                                            <td class="table-space-td" valign="middle" height="18" style="height: 18px; width: 450px; background-color: #e4e6e9;" width="450" bgcolor="#E4E6E9" align="left">&nbsp;</td>
                                        </tr>
                                    </tbody>
                                </table>
                                <table class="table-space" height="8" style="height: 8px; font-size: 0px; line-height: 0; width: 450px; background-color: #ffffff;" width="450" bgcolor="#FFFFFF" cellspacing="0" cellpadding="0" border="0">
                                    <tbody>
                                        <tr>
                                            <td class="table-space-td" valign="middle" height="8" style="height: 8px; width: 450px; background-color: #ffffff;" width="450" bgcolor="#FFFFFF" align="left">&nbsp;</td>
                                        </tr>
                                    </tbody>
                                </table>

                                <table class="table-row" width="450" bgcolor="#FFFFFF" style="table-layout: fixed; background-color: #ffffff;" cellspacing="0" cellpadding="0" border="0">
                                    <tbody>
                                        <tr>
                                            <td class="table-row-td" style="font-family: Arial, sans-serif; line-height: 19px; color: #444444; font-size: 13px; font-weight: normal; padding-left: 36px; padding-right: 36px;" valign="top" align="left">
                                                <table class="table-col" align="left" width="378" cellspacing="0" cellpadding="0" border="0" style="table-layout: fixed;">
                                                    <tbody>
                                                        <tr>
                                                            <td class="table-col-td" width="378" style="font-family: Arial, sans-serif; line-height: 19px; color: #444444; font-size: 13px; font-weight: normal; width: 378px;" valign="top" align="left">
                                                                <table class="header-row" width="378" cellspacing="0" cellpadding="0" border="0" style="table-layout: fixed;">
                                                                    <tbody>
                                                                        <tr>
                                                                            <td class="header-row-td" width="378" style="font-family: Arial, sans-serif; font-weight: normal; line-height: 19px; color: #478fca; margin: 0px; font-size: 18px; padding-bottom: 10px; padding-top: 15px;" valign="top" align="left">Bạn Muốn Chọn Chế Độ Luyện Tập Nào?</td>
                                                                        </tr>
                                                                    </tbody>
                                                                </table>
                                                                <div style="font-family: Arial, sans-serif; line-height: 20px; color: #444444; font-size: 13px;">
                                                                    <b style="color: brown;">Lưu ý: Kết quả luyện tập sẽ không tính vào kết quả chính thức,chỉ mang tính chất tham khảo</b>
                                                                    <br  style="color: greenyellow;"> Vui Lòng Chọn Để Tiếp Tục
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>

                                <table class="table-space" height="12" style="height: 12px; font-size: 0px; line-height: 0; width: 450px; background-color: #ffffff;" width="450" bgcolor="#FFFFFF" cellspacing="0" cellpadding="0" border="0">
                                    <tbody>
                                        <tr>
                                            <td class="table-space-td" valign="middle" height="12" style="height: 12px; width: 450px; background-color: #ffffff;" width="450" bgcolor="#FFFFFF" align="left">&nbsp;</td>
                                        </tr>
                                    </tbody>
                                </table>
                                <table class="table-space" height="12" style="height: 12px; font-size: 0px; line-height: 0; width: 450px; background-color: #ffffff;" width="450" bgcolor="#FFFFFF" cellspacing="0" cellpadding="0" border="0">
                                    <tbody>
                                        <tr>
                                            <td class="table-space-td" valign="middle" height="12" style="height: 12px; width: 450px; padding-left: 16px; padding-right: 16px; background-color: #ffffff;" width="450" bgcolor="#FFFFFF" align="center">&nbsp;
                                                <table bgcolor="#E8E8E8" height="0" width="100%" cellspacing="0" cellpadding="0" border="0">
                                                    <tbody>
                                                        <tr>
                                                            <td bgcolor="#E8E8E8" height="1" width="100%" style="height: 1px; font-size:0;" valign="top" align="left">&nbsp;</td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <table class="table-space" height="16" style="height: 16px; font-size: 0px; line-height: 0; width: 450px; background-color: #ffffff;" width="450" bgcolor="#FFFFFF" cellspacing="0" cellpadding="0" border="0">
                                    <tbody>
                                        <tr>
                                            <td class="table-space-td" valign="middle" height="16" style="height: 16px; width: 450px; background-color: #ffffff;" width="450" bgcolor="#FFFFFF" align="left">&nbsp;</td>
                                        </tr>
                                    </tbody>
                                </table>

                                <table class="table-row" width="450" bgcolor="#FFFFFF" style="table-layout: fixed; background-color: #ffffff;" cellspacing="0" cellpadding="0" border="0">
                                    <tbody>
                                        <tr>
                                            <td class="table-row-td" style="font-family: Arial, sans-serif; line-height: 19px; color: #444444; font-size: 13px; font-weight: normal; padding-left: 36px; padding-right: 36px;" valign="top" align="left">
                                                <table class="table-col" align="left" width="378" cellspacing="0" cellpadding="0" border="0" style="table-layout: fixed;">
                                                    <tbody>
                                                        <tr>
                                                            <td class="table-col-td" width="378" style="font-family: Arial, sans-serif; line-height: 19px; color: #444444; font-size: 13px; font-weight: normal; width: 378px;" valign="top" align="left">
                                                              
                                                                <div class="con">
                                                                    <div class="fox" @click="setBOT(true)">
                                                                    <a class="box">
                                                                    &nbsp; Đấu Ngẫu Nhiên &nbsp;</a>
                                                                </div>
                                                                <div class="fox"  @click="setFR(true)">
                                                                
                                                                    <a class="box">
                                                                    &nbsp; Đấu Với Bạn Bè Cụ Thể &nbsp;</a>
                                                                </div>

                                                                </div>
                                                              

                                                                <table class="table-space" height="16" style="height: 16px; font-size: 0px; line-height: 0; width: 378px; background-color: #ffffff;" width="378" bgcolor="#FFFFFF" cellspacing="0" cellpadding="0" border="0">
                                                                    <tbody>
                                                                        <tr>
                                                                            <td class="table-space-td" valign="middle" height="16" style="height: 16px; width: 378px; background-color: #ffffff;" width="378" bgcolor="#FFFFFF" align="left">&nbsp;</td>
                                                                        </tr>
                                                                    </tbody>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>

                                <table class="table-space" height="6" style="height: 6px; font-size: 0px; line-height: 0; width: 450px; background-color: #ffffff;" width="450" bgcolor="#FFFFFF" cellspacing="0" cellpadding="0" border="0">
                                    <tbody>
                                        <tr>
                                            <td class="table-space-td" valign="middle" height="6" style="height: 6px; width: 450px; background-color: #ffffff;" width="450" bgcolor="#FFFFFF" align="left">&nbsp;</td>
                                        </tr>
                                    </tbody>
                                </table>

                                <table class="table-space" height="1" style="height: 1px; font-size: 0px; line-height: 0; width: 450px; background-color: #ffffff;" width="450" bgcolor="#FFFFFF" cellspacing="0" cellpadding="0" border="0">
                                    <tbody>
                                        <tr>
                                            <td class="table-space-td" valign="middle" height="1" style="height: 1px; width: 450px; background-color: #ffffff;" width="450" bgcolor="#FFFFFF" align="left">&nbsp;</td>
                                        </tr>
                                    </tbody>
                                </table>
                                <table class="table-space" height="36" style="height: 36px; font-size: 0px; line-height: 0; width: 450px; background-color: #e4e6e9;" width="450" bgcolor="#E4E6E9" cellspacing="0" cellpadding="0" border="0">
                                    <tbody>
                                        <tr>
                                            <td class="table-space-td" valign="middle" height="36" style="height: 36px; width: 450px; background-color: #e4e6e9;" width="450" bgcolor="#E4E6E9" align="left">&nbsp;</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </td>
        </tr>
    </tbody>
</table>


<div v-if="bot">
    <app-bot></app-bot>
</div>
<div v-if="friend">
    <app-friend></app-friend>
</div>


  

  </template>
  
  
  <script>
  import BOT from '@/views/game/battle/bot.vue';
  import Friend from '@/views/game/battle/friend.vue';
  import { mapGetters } from 'vuex';
  import { toast } from 'vue3-toastify';
  import axios from 'axios';
  import 'vue3-toastify/dist/index.css';
  
  
  
  import moment from 'moment';
  export default {
  name: '',
  components: {
  'app-bot': BOT,
  'app-friend': Friend,

  },
  data(){
    return {
     token : localStorage.getItem('token'),
     bot: false,
     friend: false,
    }
  },

  methods: {
    setBOT(s){
        this.bot = s;
    },
    setFR(s){
        this.friend = s;
    },
  go(link){
      window.location.href = link;
  },
  
  
  }
  
  
  }
  </script>



<style>
.con {
    display: flex;
}
.fox{
    font-family: Arial, sans-serif; 
    line-height: 19px; color: #444444; 
    font-size: 13px; text-align: center;
}
.box {
    color: #ffffff;
    text-decoration: none;
    /* margin: 51px; */
    text-align: center;
    vertical-align: baseline;
    border: 1px solid #6fb3e0;
    padding: 4px 9px;
    font-size: 1vw;
    margin-right: 1vw;
    margin-left: 1vw;
    line-height: 2vw;
    background-color: #6fb3e0;
}
.box:hover {
    color: #ffffff;
    text-decoration: none;
    /* margin: 51px; */
    text-align: center;
    vertical-align: baseline;
    border: 1px solid #969696;
    padding: 4px 9px;
    font-size: 1vw;
    margin-right: 1vw;
    margin-left: 1vw;
    line-height: 2vw;
    background-color: #92000c;
}

td.table-td-wrap {
    zoom: 168%;
    box-shadow: 0 1px 2px #e0e0e0;
}
</style>