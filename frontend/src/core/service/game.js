import axios from 'axios';
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';
import { baseWeb,baseURL } from '@/router/index';

export class GameService {
    async checkCoint(token, bey,type) {
        const response = await axios.get(`${baseURL}/game/spin/${token}/${bey}/${type}`,);
        return response.data.data;
    }
    
  spin(token,boss) {
    const response =  axios.post(`${baseURL}/game/spin/${token}`,boss);
    return response;
}

pst(boss) {
    const response =  axios.post(`${baseURL}/game/attack`,boss);
    return response;
}

checkSpin(option) {
    const response =  axios.post(`${baseURL}/game/spin/check`,option);
    return response;
}

async getItemShop() {
    const response = await axios.get(`${baseURL}/game/shop`);
    return response;
}
async getItem(code) {
    const response = await axios.get(`${baseURL}/game/item/${code}`);
    return response;
}
  async getType() {
    const response = await axios.get(`${baseURL}/game/getAllTypes`);
    return response;
}
async getItems(token) {
    const response = await axios.get(`${baseURL}/game/getItemsBag/${token}`,);
    return response;
}
setItem(token,item) {
    const response =  axios.put(`${baseURL}/game/setItem/${token}`,item);
    return response;
}

buyItem(token,item) {
    const response =  axios.post(`${baseURL}/game/buyItem/${token}`,item);
    return response;
}
async getBeyByType(type) {
    const response = await axios.get(`${baseURL}/game/getBey/${type}`);
    return response;
}
async getBeyByID(id) {
    const response = await axios.get(`${baseURL}/game/get/${id}`);
    return response;
}
async getBosss() {
    const response = await axios.get(`${baseURL}/game/getBosses`);
    return response;
}


}