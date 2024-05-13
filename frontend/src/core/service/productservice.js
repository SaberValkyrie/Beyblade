import axios from 'axios';
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';
import { baseWeb,baseURL } from '@/router/index';

export class ProductService {
    async searchProduct(keyword) {
        const response = axios.get(`${baseURL}/products/search/query=${keyword}`);
        return response;
  }


  async getAllProductBySeller(token,status) {
    const response = axios.get(`${baseURL}/products/seller/${token}/${status}`);
    return response;
}

  async searchByStatus(status) {
    const response = axios.get(`${baseURL}/products/search/status=${status}`);
    return response;
}


async getAllCategory() {
    const response = axios.get(`${baseURL}/products/category`);
    return response;
}
async getCategoryByID(id) {
    const response = axios.get(`${baseURL}/products/get/category/${id}`);
    return response;
}

async getProductByCategoryId(id) {
    const response = axios.get(`${baseURL}/products/category/${id}`);
    return response;
}

async getReviewsDemo(productId) {
    const response = axios.get(`${baseURL}/products/reviews/limit/${productId}`);
    return response;
}

async getReviews(productId) {
    const response = axios.get(`${baseURL}/products/reviews/limit/${productId}`);
    return response;
}


addProduct(token, orderRequest) {
    return axios.post(`${baseURL}/products/add/${token}`, orderRequest);
  }


  editProduct(id,token, orderRequest) {
    return axios.put(`${baseURL}/products/edit/${id}/${token}`, orderRequest);
  }

async addToCart(productId,token) {
    const response = axios.post(`${baseURL}/cart/add/productId=${productId}/${token}`);
    return response;
}
async getProductByID(productId,token) {
    const response = axios.get(`${baseURL}/products/edit/${productId}/${token}`);
    return response;
}
async getProductById(productId) {
    const response = axios.get(`${baseURL}/products/${productId}`);
    return response;
}


async getAllVouchers(token) {
    const response = axios.get(`${baseURL}/voucher/${token}`);
    return response;
}


async updateQuantityCart(id,token,action) {
    const response = axios.put(`${baseURL}/cart/quantity/productId=${id}/${token}/${action}`);
    return response;
}

}