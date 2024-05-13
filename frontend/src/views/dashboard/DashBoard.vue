<template>
    <!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Kênh Bán Hàng</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css">


</head>
<body>
  <app-header></app-header>
  <br><br><br><br><br>

  <div class="container mt-6">
    <h1>Phân Tích Bán Hàng</h1>
  <canvas id="myChartData"></canvas>
  <hr>  
</div>
<br><br><br><br><br>

  <div class="container mt-4">
    <canvas id="myChart"></canvas>
    
   
</div>

<br><br><br><br><br>

<app-footer></app-footer>
</body>
</html>
</template>

<script>
import HeaderDashboard from '@/views/support/HeaderDashboard.vue';
import Footer from '@/views/support/Footer.vue';
import { mapGetters } from 'vuex';
import { baseURL } from '@/router/index';
import { toast } from 'vue3-toastify';
import axios from 'axios';
import 'vue3-toastify/dist/index.css';
import { AccountService } from '@/core/service/accountservice';
import { OrderService } from '@/core/service/orderservice';
import Chart from 'chart.js/auto';
import moment from 'moment';

export default {
  name: '',
  components: {
    'app-header': HeaderDashboard,
    'app-footer': Footer,
  },
  data() {
    return {
      service: new OrderService(),
      token: localStorage.getItem('token'),
      orders: [],
      chartData: {},
      a: 0,
   b: 0,
    }
  },
  created() {
    this.getData();
    this.get();
  },
  mounted() {
const fetchData = async () => {
  try {
    const [a, b] = await Promise.all([
      this.service.getDashBoardOrder(this.token),
      this.service.getDashBoardOrderNow(this.token)
    ]);

    const labels = ['Hôm Qua', 'Hôm Nay'];

    this.a = a.data.data.length;
    this.b = b.data.data.length;

    this.renderChartJS(labels, [this.a, this.b]);
  } catch (error) {
    this.renderChartJS(['Hôm Qua', 'Hôm Nay'], [this.a, this.b]);
  }
};

fetchData();
},
    methods: {
      renderChartJS(labels, data) {
  // Vẽ biểu đồ bằng Chart.js
  const ctx = document.getElementById('myChartData').getContext('2d');
  const myChart = new Chart(ctx, {
    type: 'bar',
    data: {
      labels: labels,
      datasets: [{
        label: 'Đơn Hàng',
        data: data,
        backgroundColor: [
          'rgba(255, 206, 86, 0.2)',
          'rgba(54, 162, 235, 0.2)',
        ],
        borderColor: [
          'rgba(255, 206, 86, 1)',
          'rgba(54, 162, 235, 1)',
        ],
        borderWidth: 1
      }]
    },
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });
},

      get(){
      this.service.getDashBoardOrder(this.token)
      .then(response => {
        this.a = response.data.data.length
      })
      .catch(error => {
      });


      this.service.getDashBoardOrderNow(this.token)
      .then(response => {
        this.b = response.data.data.length
      })
      .catch(error => {
      });
},




      async getByS(st) {
    try {
      const response = await this.service.getAllOrdersBySeller(this.token, st);
      return response.data.data.length;
    } catch (error) {
      console.error("Error fetching data:", error);
      return 0; // Trả về 0 nếu có lỗi xảy ra
    }
  },


      async getData() {
    try {
      const response = await this.service.getAllOrdersBySeller(this.token, 0);
      const data = response.data.data;
      
      // Tạo một đối tượng để lưu trữ độ dài của mỗi trạng thái
      const statusLengths = {
        'Chờ Xác Nhận': await this.getByS(2),
        'Hoàn Thành': await this.getByS(6),
        'Khiếu Nại': await this.getByS(7),
        'Đã Hủy Đơn': await this.getByS(8)
      };
      
      // Đếm số lượng cho mỗi trạng thái và cập nhật độ dài
      data.forEach(order => {
        if (order.status in statusLengths) {
          statusLengths[order.status]++;
        }
      });

      // Cập nhật các giá trị 0 bằng độ dài của status tương ứng
      Object.keys(statusLengths).forEach(status => {
        if (statusLengths[status] === 0) {
          statusLengths[status] = data.filter(order => order.status === status).length;
        }
      });

      // Tính tổng số lượng đơn hàng
      const totalCount = Object.values(statusLengths).reduce((acc, cur) => acc + cur, 0);
      
      // Vẽ biểu đồ
      this.renderChart(statusLengths, totalCount);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  },

    renderChart(counts, totalCount) {
      const ctx = document.getElementById('myChart').getContext('2d');
      const myChart = new Chart(ctx, {
        type: 'pie',
        data: {
          labels: Object.keys(counts),
          datasets: [{
            label: 'Đơn Hàng',
            data: Object.values(counts),
            backgroundColor: [
              'rgba(255, 99, 132, 0.6)',
              'rgba(54, 162, 235, 0.6)',
              'rgba(255, 206, 86, 0.6)',
              'rgba(75, 192, 192, 0.6)'
            ],
            borderColor: [
              'rgba(255, 99, 132, 1)',
              'rgba(54, 162, 235, 1)',
              'rgba(255, 206, 86, 1)',
              'rgba(75, 192, 192, 1)'
            ],
            borderWidth: 1
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            legend: {
              position: 'top',
            },
            title: {
              display: true,
              text: 'Biểu đồ tỷ lệ phần trăm trạng thái đơn hàng',
            }
          },
          tooltips: {
            callbacks: {
              label: function (tooltipItem, data) {
                const label = data.labels[tooltipItem.index];
                const value = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
                const percentage = ((value / totalCount) * 100).toFixed(2);
                return `${label}: ${percentage}% (${value} trên tổng ${totalCount})`;
              }
            }
          }
        }
      });
    }
  }
}
</script>

<style>
  .vertical-line {
            border-right: 1px solid #dee2e6;
            height: 100%;
        }

        .status-list {
            list-style: none;
            padding: 100px;
            display: flex;
            flex-direction: column;
            align-items: flex-start;
            font-style: italic;
        }

        .status-list-item {
            text-align: right;
            margin-left: 189px;
            margin-top: 0px;
            width: 182px;
            display: flex;
        }
        .container.mt-6 {
    zoom: 85%;
    padding: 0rem;
    width: 50%;
    
    border-radius: 43px;
}
.container.mt-4 {
    box-shadow: 0 -1px 11px #626262;
    border-radius: 43px;
    width: 50%;
}
        .chart-container {
            display: flex;
            justify-content: space-between;
        }
        .cc {
            width: 172px;
            display: revert;
            margin-left: 566px;
            margin-top: -329px;
            font-style: italic;
            border-radius: 10px;
        }
        .chart-container>div {
            width: 48%; /* Đặt chiều rộng để cân đối */
        }

        /* Đặt chiều rộng và màu nền cho sidebar */
        ul.nav.flex-column {
            margin-top: -24px;
            width: 255px;
            background-color: #343a40!important;
            color: #fff;
            padding: 22px;
            height: 826px;
            margin-left: -128px;
        }

        /* Tùy chỉnh các mục trong sidebar */
        ul.nav.flex-column li.nav-item {
            margin-bottom: 10px; /* Khoảng cách giữa các mục */
        }

        /* Tùy chỉnh các liên kết trong sidebar */
        ul.nav.flex-column li.nav-item a.nav-link {
            color: #fff; /* Màu chữ trắng cho liên kết */
            text-decoration: none; /* Loại bỏ gạch chân mặc định của liên kết */
        }

        /* Hover màu liên kết khi rê chuột lên */
        ul.nav.flex-column li.nav-item a.nav-link:hover {
            color: #00f; /* Màu chữ khi hover (ví dụ: màu xanh) */
        }
        </style>

