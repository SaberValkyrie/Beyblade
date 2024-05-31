import axios from 'axios';
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';
import { baseWeb,baseURL } from '@/router/index';

export class GameService {
    async checkCoint(token, bey,type) {
        const response = await axios.get(`${baseURL}/game/spin/${token}/${bey}/${type}`,);
        return response.data.data;
    }
    
  spin(token,boss,type) {
    const response =  axios.post(`${baseURL}/game/spin/${token}/${type}`,boss);
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
setKQ(option) {
    const response =  axios.post(`${baseURL}/game/setKQ`,option);
    return response;
}
getKQ(option) {
    const response =  axios.post(`${baseURL}/game/getKQ`,option);
    return response;
}
updateBoss(option,token) {
    const response =  axios.post(`${baseURL}/game/updateBoss/${token}`,option);
    return response;
}

async getItemShop() {
    const response = await axios.get(`${baseURL}/game/shop`);
    return response;
}
async getTop() {
    const response = await axios.get(`${baseURL}/game/top`);
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
buyBUFF(token,x,type) {
    const response =  axios.put(`${baseURL}/game/buffHP/${token}/${x}/${type}`);
    return response;
}


buyItem(token,item) {
    const response =  axios.post(`${baseURL}/game/buyItem/${token}`,item);
    return response;
}

checkGC(token,code) {
    const response =  axios.post(`${baseURL}/game/checkCode/${token}/${code}`);
    return response;
}

diemdanh(token,item) {
    const response =  axios.post(`${baseURL}/checkIn/${token}/${item}`);
    return response;
}

 getUserTop(token,top) {
    const response = axios.post(`${baseURL}/game/thachdau/${token}`,top);
    return response;
}
setPK(kethu) {
    const response = axios.post(`${baseURL}/game/setKeThu`,kethu);
    return response;
}

async getBeyDefault(token) {
    const response = await axios.get(`${baseURL}/game/getBeyDefault/${token}`);
    return response;
}

async getBeyByType(type) {
    const response = await axios.get(`${baseURL}/game/getBey/${type}`);
    return response;
}
async getMyBeyByType(token,type) {
    const response = await axios.get(`${baseURL}/game/getBey/${type}/${token}`);
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
async getBosssTG() {
    const response = await axios.get(`${baseURL}/game/getBossTG`);
    return response;
}

}