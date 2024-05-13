import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Products',
      component: ProductsComponent
    },
    // Thêm các route khác nếu cần thiết
  ]
});
