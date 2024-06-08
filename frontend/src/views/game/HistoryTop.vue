<template>
    <div class="container">
        <div class="row">
            <div class="col-12 mb-3 mb-lg-5">
                <div class="position-relative card table-nowrap table-card">
                    <div class="card-header align-items-center">
                        <h5 class="mb-0">Lịch Sử Xếp Hạng</h5>
                    </div>
                    <div class="table-responsive">
                        <table class="table mb-0" >
                            <thead class="small text-uppercase bg-body text-muted">
                                <tr>
                                    <th>Thời Gian</th>
                                    <th>Chi Tiết</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr class="align-middle" v-for="tb in ThongBao">
                                    <td>{{ formatHour(tb.createAt) }}</td>
                                    <td>{{ tb.message }}</td>
                                
                                </tr>
                            </tbody>
                        </table>
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
        this.getThongBao()
    }, 1000);
    },
    
    methods: {
  
          
    
    
        
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
    this.gameService.getThongBao(2).then(res => {
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


    <style>
    body {
        margin-top: 20px;
        background: #FFF5EE;
    }
    
    .card {
        box-shadow: 0 20px 27px 0 rgb(0 0 0 / 5%);
    }
    
    .avatar.sm {
        width: 2.25rem;
        height: 2.25rem;
        font-size: .818125rem;
    }
    
    .table-nowrap .table td,
    .table-nowrap .table th {
        white-space: nowrap;
    }
    
    .table>:not(caption)>*>* {
        padding: 0.75rem 1.25rem;
        border-bottom-width: 1px;
    }
    
    table th {
        font-weight: 600;
        background-color: #eeecfd !important;
    }
    
    .fa-arrow-up {
        color: #00CED1;
    }
    
    .fa-arrow-down {
        color: #FF00FF;
    }
    
    /* Responsive styles */
    @media (max-width: 768px) {
        .card {
            margin: 0 10px;
        }
    
        .table-responsive {
            width: 100%;
            overflow-x: auto;
        }
    
        .table {
            width: 100%;
        }
    
        table th, table td {
            padding: 0.5rem;
        }
    
        table th {
            font-size: 0.875rem;
        }
    
        table td {
            font-size: 0.875rem;
        }
    
        .badge {
            font-size: 0.75rem;
        }
    }
    </style>
    