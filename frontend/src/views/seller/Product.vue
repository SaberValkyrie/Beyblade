<template>
 
    <html lang="vi">
    <app-header ></app-header>
    <br>    <br>    <br>    <br>
      <body>
        <div class="app">
  
       <div class="app__container">
            <div class="grid wide">
              <div class="row sm-gutter app__content">
                <!-- Category -->
                <!-- <div class="col l-10 m-18 c-2"> -->
                  <div class="item">
  
                  <!-- Filter -->
                  <!-- <div class="home-filter hide-on-mobile-tablet">
                    <div class="select-input">
                      <span class="home-filter__label">Lọc Theo Danh mục</span>
                      <i class="select-input__icon fas fa-angle-down"></i>
                      <ul class="select-input__list">
                        <ul class="category-list">
                      <ul class="category-list">
                        <li v-for="(category, index) in categories" :key="index" :class="{ 'category-item--active': isSelected(category.id) }">
    <a  @click.prevent="selectCategory(category.id)" class="category-item__link">
      {{ category.name }}
      <span v-if="isSelected(category.id) && getCategoryProductLength(category.id) > 0">
        ({{ getCategoryProductLength(category.id) }})
      </span>
    </a>
  </li>
  
  
                  </ul>
            </ul>
                      </ul>
                    </div>
                    <div class="select-input">
                      
                      <span class="home-filter__label">Sắp xếp theo</span>
                      <i class="select-input__icon fas fa-angle-down"></i>
                      <ul class="select-input__list">
    <button :class="{ 'home-filter__btn btn btn--primary': currentStatus === 'moinhat', 'home-filter__btn btn': currentStatus !== 'moinhat' }" @click="filterByStatus('moinhat')">Mới nhất</button>
    <button :class="{ 'home-filter__btn btn btn--primary': currentStatus === 'banchay', 'home-filter__btn btn': currentStatus !== 'banchay' }" @click="filterByStatus('banchay')">Bán chạy</button>
    
                        <button :class="{ 'home-filter__btn btn btn--primary': currentStatus === 'giaCaoDenThap', 'home-filter__btn btn': currentStatus !== 'giaCaoDenThap' }" @click="filterByStatus('giaCaoDenThap')">Giá: Cao đến Thấp</button>
    
                        <button :class="{ 'home-filter__btn btn btn--primary': currentStatus === 'giaThapDenCao', 'home-filter__btn btn': currentStatus !== 'giaThapDenCao' }" @click="filterByStatus('giaThapDenCao')">Giá: Thấp đến Cao</button>
    
                      </ul>
                    </div>
                    <div class="home-filter__page">
                      <span class="home-filter__page-num">
                        <span class="home-filter__page-current">{{currentPage }}</span>/{{ totalPages }}
                      </span>
                    </div>
                  </div> -->
         <!-- Mobile Category -->
         <!-- <nav class="mobile-category">
                    <ul class="mobile-category__list">
                      <li v-for="(category, index) in categories" :key="index"
                      class="mobile-category__item">
                      <a @click.prevent="selectCategory(category.id)"
                      class="mobile-category__link">
                        {{ category.name }} ({{ category.products ? category.products.length : 0 }})</a>
                    </li>
                    </ul>
                  </nav> -->
            
    <!-- Product -->
    <div class="home-product">
      <br>
      <button class="add" @click="goto('seller/add/product')">Thêm Sản Phẩm</button>

      <div class="row sm-gutter">
        
        <!-- Product item -->
        <div v-for="(product, index) in paginatedOrders" :key="index" class="product-item">
          <a :href="'/seller/edit/' + product.productId" class="home-product-item">
            <div class="home-product-item__img" :style="'background-image: url(' + baseUrl + '/files/' + product.images + ');'"></div>
            <h4 class="home-product-item__name">{{ product.name }}</h4>
            <div class="home-product-item__price">
              <span class="home-product-item__price-old " style="text-decoration: line-through;" v-if="product.saleOff > 0">{{ convert(product.beforePrice)}}đ
              </span>
              <span class="home-product-item__price-current">{{ convert(product.price) }} đ</span>   
                 </div>
    
            <div class="home-product-item__action">
              <span class="home-product-item__like home-product-item__like--liked">
                <i class="home-product-item__like-icon-empty far fa-heart"></i>
                <i class="home-product-item__like-icon-fill fas fa-heart"></i>
              </span>
              <div class="home-product-item__rating">
                              <i
                              class="home-product-item__star--gold fas fa-star"></i>
                              <i
                                class="home-product-item__star--gold fas fa-star" ></i>
                              <i
                                class="home-product-item__star--gold fas fa-star"  ></i>
                              <i
                                class="home-product-item__star--gold fas fa-star"  ></i>
                              <i class="fas fa-star"></i>
                            </div>
              <div class="home-product-item__sold">Doanh Số {{ product.sold }}</div>
            </div>
            <div class="home-product-item__origin">
                            <span class="home-product-item__brand">{{ product.category.name }}</span>
                            <!-- <span class="home-product-item__origin-name"
                              >{{ product.seller.username }}</span
                            > -->
                          </div>
            <div class="home-product-item__sale-off" v-if="product.saleOff > 0">
                            <span class="home-product-item__sale-off-percent">{{convert(product.saleOff)}}%</span>
                            <br>
                            <span class="home-product-item__sale-off-label">GIẢM</span>
                          </div>
          </a>
        </div>
      </div>
    </div>
    
 
    
    
                </div>
              </div>
            </div>
          
          </div>
        
        </div>

         
        <div class="row"  v-if="products.length > 0">
        <div class="mt-4 pt-2 col-lg-12">
            <nav aria-label="Page navigation example">
  <ul class="pagination justify-content-center">
    <li class="page-item" :class="{ disabled: currentPage === 1 }">
      <a class="page-link" @click="prevPage" tabindex="-1">&laquo;</a>
    </li>
    <li class="page-item" v-for="page in totalPages" :key="page" :class="{ active: currentPage === page }">
      <a class="page-link" @click="goToPage(page)">{{ page }}</a>
    </li>
    <li class="page-item" :class="{ disabled: currentPage === totalPages }">
      <a class="page-link" @click="nextPage">&raquo;</a>
    </li>
  </ul>
</nav>

        </div>
        </div>

      </body>

  

      <app-footer></app-footer>
    </html>
    
    </template>
    <script>
    import axios from 'axios'; // Import Axios
    import { baseURL } from '@/router/index';
    import HeaderDashboard from '/src/views/support/HeaderDashboard.vue'
    import Foooter from '/src/views/support/Footer.vue'
    import { toast } from 'vue3-toastify';
    import {ProductService} from '@/core/service/productservice';
  import 'vue3-toastify/dist/index.css';
  
    
    export default {
      components: {
        'app-header': HeaderDashboard,
        'app-footer': Foooter,
      },
  
      data() {

        return {
          service: new ProductService(),
          baseUrl: baseURL,
          products: [],
          categories: [],
          selectedCategoryId: null,
          totalPages: 0,
          itemsPerPage: 20,
          currentPage: 1,
          pageSize: 10, // Số lượng đơn hàng trên mỗi trang
          currentStatus: null,
          token : localStorage.getItem('token'),
          status: this.$route.params.status,
        };
      },
      computed: {
  paginatedOrders() {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    return this.products.slice(startIndex, endIndex);
  },

  totalPages() {
    return Math.ceil(this.products.length / this.pageSize);
  },
  },
      created() {
      this.fetchProducts(); 
        this.listCategory();
      },
      methods: {
        fetchProducts() {
          this.service.getAllProductBySeller(this.token,this.status).then(response => {
          toast.success(response.data.message);
          this.products = response.data.data;
        })
        .catch(error => {
          toast.success('Không tìm thấy sản phẩm');
        });
  },

  goToPage(page) {
    this.currentPage = page;
  },
  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  },
  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
  }},
  goto(link){
window.location.href = '/' + link;
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
        listCategory() {
         this.service.getAllCategory().then(response => {
              this.categories = response.data.data;
              this.categories.forEach(category => {
                this.service.getProductByCategoryId(category.id)
                  .then(response => {
                    category.products = response.data.data;
                  })
                  .catch(error => {
                    console.error('Error fetching products for category:', error);
                  });
              });
            })
            .catch(error => {
              console.error('Error fetching categories:', error);
            });
        },
  
      }
    };
    </script>
    <style scoped>
    ul.pagination.justify-content-center {
    zoom: 150%;
}
    body{
    
      text-decoration: none;
      background-color: #f6f6f6;
    }
    .product-item {
      width: 180px;
      margin-bottom: 3px;
      padding-right: 10px;
    }
    .app {
      overflow: hidden;
      margin-left: 53px;
      zoom: 110%;
      /* background-color: #e0e0e0; */
  }


  button.add:hover {
    border: 1px solid #b8b8b8;
    zoom: 150%;
    background-color: rgb(0, 0, 0);
    color: white;
    border-radius: 1rem;
}
button.add {
    border: 1px solid #818181;
    zoom: 150%;
    background-color: rgb(245, 245, 245);
    color: rgb(0, 0, 0);
    border-radius: 1rem;
}

  .row.sm-gutter {
      margin-left: -5px;
      margin-right: -5px;
      margin: 27px;
  }
  .home-filter {
      /* padding: 18px; */
      background-color: rgb(226 226 226 / 4%);
      display: flex;
      align-items: center;
      padding: 9px 111px;
      margin-bottom: -35px;
      border-radius: 2px;
  }
  .select-input {
      text-align: center;
      font-size: 20px;
      min-width: 200px;
      height: 34px;
      padding: 0 12px;
      border-radius: 2px;
      background-color: var(--white-color);
      display: flex;
      justify-content: space-between;
      align-items: center;
      position: relative;
  }
  .select-input {
    margin-right: -11px;
      margin-left: 24px;
  }
  button.home-filter__btn.btn {
      font-size: 15px;
  }
  button.home-filter__btn.btn:hover {
      font-size: 16px;
      color: rgb(2, 2, 2);
  }
  .home-product {
      margin-bottom: 10px;
      margin-left: 109px;
  }
  </style>
  
  
    