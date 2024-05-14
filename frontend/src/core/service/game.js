import axios from 'axios';
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';
import { baseWeb,baseURL } from '@/router/index';

export class GameService {
    async checkCoint(token) {
        const response = await axios.get(`${baseURL}/game/spin/${token}`);
        return response.data.data;
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

   spin(token) {
    const response =  axios.post(`${baseURL}/game/spin/${token}`);
    return response;
}

}