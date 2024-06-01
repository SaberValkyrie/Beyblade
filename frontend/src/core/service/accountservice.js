import axios from 'axios';
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';
import { baseWeb,baseURL } from '@/router/index';

export class AccountService {
    //-----------------------------------------------------------------------------------------------------------------

async getCart(token) {
      const response = await axios.get(`${baseURL}/cart/${token}`);
      return response.data.data;
}
    //-----------------------------------------------------------------------------------------------------------------

   async deleteCart(token,id) {
     const cc = axios.delete(`${baseURL}/cart/delete/cartId=${id}/${token}`)
     toast.success('Xóa sản phẩm thành công!'); 
     
   return (await cc).data.data;
}
    //-----------------------------------------------------------------------------------------------------------------


async getNotify(token) {
  const response = axios.get(`${baseURL}/notify/read=false/${token}`);
  return response.data.data;
}
    //-----------------------------------------------------------------------------------------------------------------

    async getUserInfo(token) {
        const response = await axios.get(`${baseURL}/profile/${token}`);
        return response.data.data;
    }
    
    //-----------------------------------------------------------------------------------------------------------------
    async upload(event) {
      const file = event.target.files[0];
      const formData = new FormData();
      formData.append('image', file);
  
      // Gửi yêu cầu POST để upload file ảnh lên server
      const a = axios.post(`${baseURL}/files/upload`, formData)
        return (await a).data.data;
  } 
  //-----------------------------------------------------------------------------------------------------------------
  async getUserLogin(token) {
    const response = await axios.get(`${baseURL}/user/${token}`);
    localStorage.setItem('loggedInUser', JSON.stringify(response.data.data));
    return response.data.data;
}
  //-----------------------------------------------------------------------------------------------------------------
  async getAccountLogin(token) {
    const response = await axios.get(`${baseURL}/account/${token}`);
    // localStorage.setItem('account', JSON.stringify(response.data.data));
    return response.data.data;
}
//-----------------------------------------------------------------------------------------------------------------
async getAddress(token) {
  const response =  axios.get(`${baseURL}/address/${token}`);
  return (await response).data.data;
}
//-----------------------------------------------------------------------------------------------------------------
async setDefaultAdress(id,token) {
  const response =  axios.put(`${baseURL}/address/setDefault=${id}/${token}`);
  toast.success("Đã sửa mặc định thành công!")
  return response;
}

   //-----------------------------------------------------------------------------------------------------------------
   async editAddress(id, addressData, token) {
    const response = axios.put(`${baseURL}/address/edit/${id}/${token}`, addressData);
    toast.success("Sửa thông tin thành công!")
    return response;
  }
  //-----------------------------------------------------------------------------------------------------------------
async getAllVoucher(token,loc) {
  const response =  axios.get(`${baseURL}/game/getVoucher/${token}/${loc}`);
  return response;
}
  
async getAllUserChat(token) {
  const response =  axios.get(`${baseURL}/chat/${token}`);
  return  response;
}
async loadGC(token,t) {
  const response =  axios.get(`${baseURL}/game/getcode/${token}/${t}`);
  return  response;
}
   //-----------------------------------------------------------------------------------------------------------------
    checkAuthenticate(token,code) {
    const response =  axios.get(`${baseURL}/checkAuthen/${token}/${code}`);
    return  response;
  }
   //-----------------------------------------------------------------------------------------------------------------
   changepassword(token,code,old,account) {
    const response =  axios.post(`${baseURL}/changePass/${token}/${code}/${old}`,account);
    return  response;
  }
  //-----------------------------------------------------------------------------------------------------------------
  useItem(token,item) {
    const response =  axios.post(`${baseURL}/game/useItem/${token}`,item);
    return  response;
  }
    //-----------------------------------------------------------------------------------------------------------------
    checkNapTien(token,item) {
      const response =  axios.post(`${baseURL}/game/naptien/${token}/${item}`);
      return  response;
    }
    setNapTien(token,user,item) {
      const response =  axios.post(`${baseURL}/game/setNap/${token}/${user}/${item}`);
      return  response;
    }
}
