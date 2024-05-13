<template>
 
  <html lang="vi">
  <app-header ></app-header>
    <body>
      <div class="app">

     <div class="app__container">
          <div class="grid wide">
            <div class="row sm-gutter app__content">
              <!-- Category -->
              <!-- <div class="col l-10 m-18 c-2"> -->
                <div class="item">

                <!-- Filter -->
                <div class="home-filter hide-on-mobile-tablet">
                  <!-- <span class="home-filter__label">Sắp xếp theo</span> -->
                  <div class="select-input">
                    <span class="home-filter__label">Lọc Theo Danh mục</span>
                    <i class="select-input__icon fas fa-angle-down"></i>
                    <!-- List option -->
                    <ul class="select-input__list">
                      <ul class="category-list">
                    <ul class="category-list">
                      <li v-for="(category, index) in categories" :key="index"
                       :class="{ 'category-item--active': isSelected(category.id) }">
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
                    <!-- List option -->
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
                </div>
       <!-- Mobile Category -->
       <nav class="mobile-category">
                  <ul class="mobile-category__list">
                    <li v-for="(category, index) in categories" :key="index"
                    class="mobile-category__item">
                    <a @click.prevent="selectCategory(category.id)"
                    class="mobile-category__link">
                      {{ category.name }} ({{ category.products ? category.products.length : 0 }})</a>
                  </li>
                  </ul>
                </nav>
          
  <!-- Product -->
  <div class="home-product">
    <div class="row sm-gutter">
      <!-- Product item -->
      <div v-for="(product, index) in paginatedProducts()" :key="index" class="product-item">
        <a :href="'/product/' + product.productId" class="home-product-item">
          <div class="home-product-item__img" :style="'background-image: url(' + baseUrl + '/files/' + product.images + ');'"></div>
          <h4 class="home-product-item__name">{{ product.name }}</h4>
          <div class="home-product-item__price">
            <span class="home-product-item__price-old" style="text-decoration: line-through;" v-if="product.saleOff > 0">{{ product.beforePrice}}đ
            </span>
            <span class="home-product-item__price-current">{{ product.price }}đ</span>   
               </div>
  
          <div class="home-product-item__action">
            <span class="home-product-item__like home-product-item__like--liked">
              <i class="home-product-item__like-icon-empty far fa-heart"></i>
              <i class="home-product-item__like-icon-fill fas fa-heart"></i>
            </span>

            <!-- <div class="home-product-item__rating">
                            <i
                            class="home-product-item__star--gold fas fa-star"></i>
                            <i
                              class="home-product-item__star--gold fas fa-star" ></i>
                            <i
                              class="home-product-item__star--gold fas fa-star"  ></i>
                            <i
                              class="home-product-item__star--gold fas fa-star"  ></i>
                            <i class="fas fa-star"></i>
                          </div> -->


            <div class="home-product-item__sold">{{ product.sold }} Đã bán</div>
          </div>
          <div class="home-product-item__origin">
                          <span class="home-product-item__brand">{{ product.category.name }}</span>
                          <span class="home-product-item__origin-name"
                            >Việt Nam</span
                          >
                        </div>
          <div class="home-product-item__favourite">
            <i class="fas fa-check"></i>
            <span>Yêu thích</span>
          </div>
          <div class="home-product-item__sale-off"  v-if="product.saleOff > 0">
                          <span class="home-product-item__sale-off-percent" >{{convert(product.saleOff)}}%</span>
                          <br>
                          <span class="home-product-item__sale-off-label">GIẢM</span>
                        </div>
        </a>
      </div>
    </div>
  </div>
  
  
  <!-- Pagination -->
  <ul class="pagination home-product__pagination">
    <li class="pagination-item">
      <a href="#" class="pagination-item__link" @click.prevent="goToPage(currentPage - 1)">
        <i class="pagination-item__icon fas fa-angle-left"></i>
      </a>
    </li>
    <li v-for="page in totalPages" :key="page" :class="{ 'pagination-item pagination-item--active': currentPage === page }">
      <a href="#" class="pagination-item__link" @click.prevent="goToPage(page)">{{ page }}</a>
    </li>
    <li class="pagination-item">
      <a href="#" class="pagination-item__link" @click.prevent="goToPage(currentPage + 1)">
        <i class="pagination-item__icon fas fa-angle-right"></i>
      </a>
    </li>
  </ul>
  
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
  import axios from 'axios'; // Import Axios
  import { baseURL } from '@/router/index';
  import Header from '/src/views/support/Header.vue'
  import Foooter from '/src/views/support/Footer.vue'
  import { toast } from 'vue3-toastify';
  import {ProductService} from '@/core/service/productservice';
import 'vue3-toastify/dist/index.css';

  
  export default {
    components: {
      'app-header': Header,
      'app-footer': Foooter,
    },

    data() {
      return {
        service: new ProductService(),
        baseUrl: baseURL,
        products: [],
        categories: [],
        selectedCategoryId: null,
        currentPage: 1,
        totalPages: 0,
        itemsPerPage: 20,
        currentStatus: null,
        searchKeyword: '' // Thêm thuộc tính searchKeyword vào dữ liệu

      };
    },
    created() {
  // Lấy query parameter từ URL
  const params = new URLSearchParams(window.location.search);
    const keyword = params.get('keyword');

    // Nếu có keyword, gửi yêu cầu đến API để lấy dữ liệu
    if (keyword) {
    this.searchKeyword = keyword; // Cập nhật searchKeyword với keyword từ URL
    this.fetchProducts(keyword);
  } else {
    this.fetchProducts(); // Nếu không có keyword, lấy tất cả sản phẩm
  }
      this.listCategory();
    },
    methods: {
      fetchProducts(keyword) {
        this.service.searchProduct(keyword).then(response => {
        toast.success(response.data.message);
        this.products = response.data.data;
        this.calculateTotalPages();
      })
      .catch(error => {
        toast.success('Không tìm thấy sản phẩm');
      });
},
filterByKeyword(keyword) {
    if (!keyword) {
      this.fetchProducts('');
      return;
    }
    // Gửi yêu cầu đến API để lấy sản phẩm theo từ khóa
    this.service.searchProduct(keyword)
      .then(response => {
        this.products = response.data.data;
        this.calculateTotalPages();
      })
      .catch(error => {
      });
  },

  // Thêm hoặc sửa đổi trong phương thức filterByStatus để gọi filterByKeyword
  filterByStatus(status) {
  this.currentStatus = status;
  this.service.searchByStatus(status).then(response => {
      // Lọc kết quả theo danh mục đã chọn
      const filteredProducts = this.selectedCategoryId ?
        response.data.data.filter(product => product.category.id === this.selectedCategoryId) : response.data.data;
      // Lọc kết quả theo từ khóa
      const keyword = this.searchKeyword.trim().toLowerCase(); // Chuyển đổi từ khóa thành chữ thường
      if (keyword) {
        const regex = new RegExp(keyword, 'i');
        this.products = filteredProducts.filter(product => regex.test(product.name.toLowerCase())); // Kiểm tra từ khóa với tên sản phẩm
      } else {
        this.products = filteredProducts;
      }
      this.calculateTotalPages();
    })
    .catch(error => {
      console.error('Error filtering products by status:', error);
    });
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

      filterProducts(categoryId) {
        this.service.getProductByCategoryId(categoryId)
          .then(response => {
            this.products = response.data.data;
            this.calculateTotalPages();
          })
          .catch(error => {
            console.error('Error filtering products by category:', error);
          });
      },
    
      calculateTotalPages() {
        this.totalPages = Math.ceil(this.products.length / this.itemsPerPage);
      },
      isSelected(categoryId) {
        return this.selectedCategoryId === categoryId;
      },
      selectCategory(categoryId) {
  this.selectedCategoryId = categoryId;
  
  // Nếu có từ khóa tìm kiếm, lọc sản phẩm theo cả danh mục và từ khóa
  if (this.searchKeyword.trim() !== '') {
    this.filterByKeywordAndCategory(this.searchKeyword, categoryId);
  } else {
    // Nếu không có từ khóa, chỉ lọc sản phẩm theo danh mục
    this.filterProducts(categoryId);
  }
},
filterByKeywordAndCategory(keyword, categoryId) {
  this.service.searchProduct(keyword)
  // axios.get(`${baseURL}/products/search/query=${keyword}`)
    .then(response => {
      const filteredProducts = response.data.data.filter(product => product.category.id === categoryId);
      this.products = filteredProducts;
      this.calculateTotalPages();
    })
    .catch(error => {
    });
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

getCategoryProductLength(categoryId) {
    // Lọc sản phẩm theo danh mục
    const filteredProducts = this.products.filter(product => product.category.id === categoryId);
    // Trả về độ dài của danh sách sản phẩm sau khi đã lọc
    return filteredProducts.length;
  },
 goToPage(page) {
        if (page >= 1 && page <= this.totalPages) {
          this.currentPage = page;
        }
      },
      paginatedProducts() {
        const startIndex = (this.currentPage - 1) * this.itemsPerPage;
        const endIndex = startIndex + this.itemsPerPage;
        return this.products.slice(startIndex, endIndex);
      }
    }
  };
  </script>
  <style scoped>
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


  