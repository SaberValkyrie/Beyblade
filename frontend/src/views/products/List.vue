<template>
    <div>
      <!-- Thanh thông báo -->
      <div class="alert alert-success alert-dismissible fade show" role="alert" v-if="showSuccessMessage">
        Thêm sản phẩm thành công!
        <button type="button" class="btn-close" @click="closeSuccessMessage" aria-label="Close"></button>
      </div>
  
      <!-- Danh sách sản phẩm -->
      <h2>Danh sách sản phẩm</h2>
      <div class="row">
        <div v-for="product in products" :key="product.id" class="col-lg-3 col-md-4 col-sm-6 mb-4">
          <div class="card">
            <img :src="getImageUrl(product.url)" class="card-img-top" alt="Product Image">
            <div class="card-body">
              <h5 class="card-title">{{ product.name }}</h5>
              <p class="card-text">Năm sản xuất: {{ product.Productyear }}</p>
              <p class="card-text">Giá: {{ product.price }}</p>
            </div>
          </div>
        </div>
      </div>
  
      <!-- Button thêm sản phẩm -->
      <button @click="openAddProductModal" class="btn btn-primary mt-3">Thêm sản phẩm</button>
  
      <!-- Modal thêm sản phẩm -->
      <div v-if="isAddProductModalOpen" class="modal">
        <div class="modal-content">
          <h2>Thêm sản phẩm mới</h2>
          <form @submit.prevent="addProduct">
            <div class="mb-3">
              <label for="name" class="form-label">Tên sản phẩm:</label>
              <input type="text" class="form-control" id="name" v-model="newProduct.name" required>
            </div>
            <div class="mb-3">
              <label for="year" class="form-label">Năm sản xuất:</label>
              <input type="number" class="form-control" id="year" v-model="newProduct.Productyear" required>
            </div>
            <div class="mb-3">
              <label for="price" class="form-label">Giá:</label>
              <input type="number" class="form-control" id="price" v-model="newProduct.price" required>
            </div>
            <div class="mb-3">
              <label for="image" class="form-label">Hình ảnh:</label>
              <input type="file" class="form-control" id="image" @change="onImageChange" accept="image/*" required>
            </div>
            <button type="submit" class="btn btn-primary">Thêm</button>
          </form>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        products: [],
        isAddProductModalOpen: false,
        newProduct: {
          name: '',
          Productyear: '',
          price: '',
          image: null
        },
        showSuccessMessage: false
      };
    },
    mounted() {
      axios.get('http://localhost:8080/api/v1/Products')
        .then(response => {
          this.products = response.data;
        })
        .catch(error => {
          console.error('Error fetching products:', error);
        });
    },
    methods: {
      getImageUrl(filename) {
        return `http://localhost:8080/api/v1/upload/files/${filename}`;
      },
      openAddProductModal() {
        this.isAddProductModalOpen = true;
      },
      addProduct() {
        let formData = new FormData();
        formData.append('name', this.newProduct.name);
        formData.append('Productyear', this.newProduct.Productyear);
        formData.append('price', this.newProduct.price);
        formData.append('image', this.newProduct.image);
  
        axios.post('http://localhost:8080/api/v1/Products/insert', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        })
          .then(response => {
            this.products.push(response.data);
            this.isAddProductModalOpen = false;
            this.newProduct = {
              name: '',
              Productyear: '',
              price: '',
              image: null
            };
            this.showSuccessMessage = true;
          })
          .catch(error => {
            console.error('Error adding product:', error);
          });
      },
      onImageChange(event) {
        this.newProduct.image = event.target.files[0];
      },
      closeSuccessMessage() {
        this.showSuccessMessage = false;
      }
    }
  };
  </script>
  
  <style scoped>
  /* Thêm CSS cho thông báo thành công */
  .alert {
    position: fixed;
    top: 20px;
    right: 20px;
    z-index: 1000;
  }
  
  .modal {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
  .modal-content {
    background-color: #fff;
    padding: 20px;
    border-radius: 10px;
  }
  
  .card {
    border-radius: 10px;
    overflow: hidden;
    display: -ms-flexbox;
    width: 200px ;
    height: 300px ;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  }
  
  .card-img-top {
    width: 100%;
    height: auto;
    object-fit: cover;
  }
  .row{
    width: 20px;
    background-color: black;
    display: block;
    padding: 20px;
  }
  
  </style>
  