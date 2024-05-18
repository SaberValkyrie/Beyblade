import axios from 'axios';
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';
import { baseWeb,baseURL } from '@/router/index';

export class GameService {
    async checkCoint(token, bey) {
        const response = await axios.get(`${baseURL}/game/spin/${token}/${bey}`,);
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

  async getType() {
    const response = await axios.get(`${baseURL}/game/getAllTypes`);
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