import { createStore } from 'vuex';
import Vue from 'vue';
import Vuex from 'vuex';
import axios from 'axios';
import { baseURL } from '@/router/index';
export default createStore({
  state: {
    cartProducts: [],

  },
  mutations: {

    setCartProducts(state, products) {
      state.cartProducts = products;
    },

    removeFromCart(state, productId) {
      state.cartProducts = state.cartProducts.filter(product => product.id !== productId);
    },

    SET_LOGGED_IN_USER(state, user) {
      // Lưu thông tin user vào localStorage
      localStorage.setItem('loggedInUser', JSON.stringify(user));
    },
    CLEAR_LOGGED_IN_USER(state) {
      // Xóa thông tin user khỏi localStorage
      localStorage.removeItem('loggedInUser');
    }
  },
  actions: {

    deleteCartItem({ commit }, { id, token }) {
      return axios.delete(`${baseURL}/cart/delete/cartId=${id}/${token}`)
        .then(response => {
          commit('removeFromCart', id);
          return response;
        });
    },
    // Đổi SET_LOGGED_IN_USER để sử dụng mutation mới
    setLoggedInUser({ commit }, user) {
      commit('SET_LOGGED_IN_USER', user);
    },
    // Thêm action để clear logged in user
    clearLoggedInUser({ commit }) {
      commit('CLEAR_LOGGED_IN_USER');
    }
  },
  getters: {
    account: state => {
      return JSON.parse(localStorage.getItem('account'));
    },
    codeXN: state => {
      return JSON.parse(localStorage.getItem('codeXN'));
    },
    codeXN: kethuTop => {
      return JSON.parse(localStorage.getItem('kethuTop'));
    },
    searchHistory: state => {
      return JSON.parse(localStorage.getItem('searchHistory'));
    },
    loggedInUser: state => {
      return JSON.parse(localStorage.getItem('loggedInUser'));
    },
    cartProducts: state => {
      return JSON.parse(localStorage.getItem('cartProducts'));
    },
    productBuy: state => {
      return JSON.parse(localStorage.getItem('productBuy'));
    },
    orders: state => {
      return JSON.parse(localStorage.getItem('orders'));
    },
    totalProductPrice: state => {
      return JSON.parse(localStorage.getItem('totalProductPrice'));
    },
    userInfo: state => {
      return JSON.parse(localStorage.getItem('userInfo'));
    },
    addressUser: state => {
      // Lấy thông tin user từ localStorage
      return JSON.parse(localStorage.getItem('addressUser'));
    },
    notifications: state => {
      // Lấy thông tin user từ localStorage
      return JSON.parse(localStorage.getItem('notifications'));
    }
  }
});
