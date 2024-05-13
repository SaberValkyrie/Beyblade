import axios from 'axios';
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';
import { baseWeb,baseURL } from '@/router/index';

export class GameService {
    async checkCoint(token) {
        const response = await axios.get(`${baseURL}/game/spin/${token}`);
        return response.data.data;
  }



   spin(token) {
    const response =  axios.post(`${baseURL}/game/spin/${token}`);
    return response;
}

}