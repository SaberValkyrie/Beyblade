import axios from 'axios';
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';
import { baseWeb,baseURL } from '@/router/index';

export class OrderService {
    async findVoucher(code,token) {
        const response = await axios.get(`${baseURL}/voucher/find/${code}/${token}`);
        return response;
  }
  order(token, payment,shipping, orderRequest) {
    return axios.post(`${baseURL}/order/${token}/create/${payment}/${shipping}`, orderRequest);
  }
    createReviews(token,id,key, request) {
    return axios.post(`${baseURL}/review/create/${token}/${id}/${key}`, request);
  }
  async getOrderByKey(key,id,token) {
    const response = axios.get(`${baseURL}/order/get/token=${token}/id=${id}/key=${key}`);
    return response;
}
async getAllOrdersByUser(token,status) {
  const response = axios.get(`${baseURL}/order/getAll/${token}/${status}/user`);
  return response;
}
async getAllOrdersBySeller(token,status) {
  const response = axios.get(`${baseURL}/order/getAll/${token}/${status}/seller`);
  return response;
}

async getDashBoardOrder(token) {
  const response = axios.get(`${baseURL}/order/dashboard/${token}/qua`);
  return response;
}
async getDashBoardOrderNow(token) {
  const response = axios.get(`${baseURL}/order/dashboard/${token}/nay`);
  return response;
}


async getPay(price) {
  const response = axios.get(`${baseURL}/payment/create/${price}`);
  return response;
}

 getItemsByOrder(key,id,token) {
  const response = axios.get(`${baseURL}/order/get/item/token=${token}/id=${id}/key=${key}`);
  return response;
}


update(key,id,token,type) {
  const response = axios.put(`${baseURL}/order/update/token=${token}/id=${id}/key=${key}/type=${type}`);
  return response;
}

    changeStatus(status,type) {
        let routeName = '';
        switch(status) {
          case 0:
                routeName = 'all';
                break;
            case 1:
                routeName = 'pay';
                break;
            case 2:
                routeName = 'pending';
                break;
            case 3:
                routeName = 'packing';
                break;
            case 4:
                routeName = 'shipping';
                break;
            case 5:
                routeName = 'sending';
                break;
            case 6:
                routeName = 'success';
                break;
            case 7:
                routeName = 'return';
                break;
            case 8:
                routeName = 'cancel';
                break;
        }

        if (type == 1) {
          window.location.href = '/seller/order/' + routeName;
        }else{
          window.location.href = '/order/' + routeName;
        }
       
    }

getStatusByID(id) {
  const response = axios.get(`${baseURL}/order/status/${id}`);
  return response;
}
getStatus() {
  const response = axios.get(`${baseURL}/order/status`);
  return response;
}
}