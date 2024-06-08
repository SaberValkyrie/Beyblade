import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Search from '@/views/support/Search.vue';
const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/product',
    name: 'product.list',
    component: () => import('../views/products/List.vue')
  },
  {
    path: '/checkIn',
    name: 'game.checkIn',
    component: () => import('../views/game/DiemDanh.vue')
  },
  {
    path: '/profile',
    name: 'user.profile',
    component: () => import('../views/info/UserInfo.vue')
  },
  {
    path: '/seller/add/product',
    name: 'seller.product.add',
    component: () => import('../views/seller/ProductForm.vue')
  },
  {
    path: '/seller/edit/:id',
    name: 'seller.edit',
    component: () => import('../views/seller/EditProduct.vue')
  },
  {
    path: '/voucher',
    name: 'user.voucher',
    component: () => import('../views/info/Voucher.vue')
  },
  {
    path: '/dashboard',
    name: 'user.dashboard',
    component: () => import('../views/dashboard/DashBoard.vue')
  },
  {
    path: '/order/details',
    name: 'order.details',
    component: () => import('../views/order/OrderDetails.vue')
  },
  {
    path: '/checkout',
    name: 'order.checkout',
    component: () => import('../views/order/Checkout.vue')
  },
  {
    path: '/address',
    name: 'user.address',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import('../views/info/Address.vue')
  },
  {
    path: '/footer',
    name: 'product.footer',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import('../views/support/Footer.vue')
  },
  {
    path: '/product/:id',
    name: 'product.detail',
    component: () => import('../views/products/ProductDetail.vue'),
    props: true
  },
 
  {
    path: '/chat/:username',
    name: 'user.chat',
    component: () => import('../views/support/Chat.vue'),
    props: true
  },
  {
    path: '/seller/order/:status',
    name: 'seller.orders',
    component: () => import('../views/seller/Orders.vue'),
    props: true
  },
  {
    path: '/order/:status',
    name: 'order',
    component: () => import('../views/order/Orders.vue'),
    props: true
  },
  {
    path: '/header/dashboard',
    name: 'header/seller',
    component: () => import('../views/support/HeaderDashboard.vue'),
    props: true
  },
  {
    path: '/seller/product/:status',
    name: 'seller/product',
    component: () => import('../views/seller/Product.vue'),
    props: true
  },
  {
    path: '/naptien/',
    name: 'order.payment',
    component: () => import('../views/order/Payment.vue')
  },
  {
    path: '/review/:id/:order_key/',
    name: 'order.review',
    component: () => import('../views/order/Review.vue')
  },
  {
    path: '/order/:id/:order_key',
    name: 'order.detail',
    component: () => import('../views/order/OrderDetails.vue'),
    props: true
  },
  {
    path: '/cart',
    name: 'cart',
    component: () => import('../views/order/Cart.vue'),
    props: true
  },
  {
    path: '/search',
    name: 'SearchPage',
    component: Search,
    props: (route) => ({ keyword: route.query.keyword }) // Truyền tham số keyword vào props của component
  },
  {
    path: '/login',
    name: 'user.login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'user.register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/changePassword',
    name: 'user.changepassword',
    component: () => import('../views/info/ChangePassWord.vue')
  },
  {
    path: '/test',
    name: 'test',
    component: () => import('../views/Test.vue')
  },
  {
    path: '/game/Boss',
    name: 'game.boss',
    component: () => import('../views/game/BattleBoss.vue')
  },

  {
    path: '/luyentap',
    name: 'luyentap',
    component: () => import('../views/game/BattleFriend.vue')
  },
  // {
  //   path: '/luyentap/friend',
  //   name: 'luyentap.friend',
  //   component: () => import('../views/game/battle/friend.vue')
  // },
  // {
  //   path: '/luyentap/bot',
  //   name: 'luyentap.bot',
  //   component: () => import('../views/game/battle/bot.vue')
  // },

  {
    path: '/myBag',
    name: 'game.itemBag',
    component: () => import('../views/game/Bag.vue')
  },
  {
    path: '/shop',
    name: 'game.shop',
    component: () => import('../views/game/Shop.vue')
  },
  {
    path: '/shop/item/:code',
    name: 'game.shop.item',
    component: () => import('../views/game/ItemDetails.vue')
  },
  {
    path: '/game/sinhton',
    name: 'game.sinhton',
    component: () => import('../views/game/SinhTon.vue')
  },
  {
    path: '/game/sinhton/battle',
    name: 'game.sinhton.battle',
    component: () => import('../views/game/BattleSinhTon.vue')
  },
  {
    path: '/history',
    name: 'game.history',
    component: () => import('../views/game/HistoryTop.vue')
  },
  {
    path: '/huongdan',
    name: 'game.huongdan',
    component: () => import('../views/game/HD.vue')
  },
  {
    path: '/game/top',
    name: 'game.top',
    component: () => import('../views/game/BattleTop.vue')
  },
  {
    path: '/game/top/battle',
    name: 'game.battle',
    component: () => import('../views/game/BattleTopCheck.vue')
  },
  {
    path: '/user/bag',
    name: 'game.myItems',
    component: () => import('../views/game/Bag.vue')
  },
  {
    path: '/header',
    name: 'product.header',
    component: () => import('../views/support/Header.vue')
  },
  {
    path: '/admin',
    name: 'product.nap',
    component: () => import('../views/support/NapTien.vue')
  }
]
// index.js
// export const baseURL = 'http://localhost:8080';
// export const baseWeb = 'http://localhost';
export const baseURL = 'http://beybladegame.online:8080';
export const baseWeb = 'http://beybladegame.online';
const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
