package com.example.demo.controller;

import com.example.demo.support.Util;
import com.example.demo.dto.ResponseOpject;
import com.example.demo.entity.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import com.example.demo.entity.User;

@RestController
@RequestMapping(path = "/address")
public class AddressController {
    @Autowired
    private UserService userService;
    @Autowired
    private NotifyService notifyService;
//    @Autowired
//    private CartService cartService;
    @Autowired
    private TokenService tokenService;
//    @Autowired
//    private ProductService productService;



    //=========================================ADDRESS==========================================================================
    @GetMapping("/{token}")
    public ResponseEntity<ResponseOpject> getAdress(@PathVariable String token) {
        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null){
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Lỗi token ", null);
        }

        UserInfo userInfo = userService.getInfoByUser(userToken.userId);
        List<AddressInfo> addressInfoList = userService.getListAddress(userToken);
        if (addressInfoList.size() < 2){
          createAddress(userToken,userInfo,true);
          createAddress(userToken,userInfo,false);
        }

        return  Util.checkStatusRes(HttpStatus.OK, "Đã tìm được " + addressInfoList.size() + " địa chỉ", addressInfoList);
    }

    private void createAddress(User user,UserInfo userInfo,boolean isDefault){
        AddressInfo addressInfo = new AddressInfo();
        addressInfo.fullname = userInfo.fullname;
        addressInfo.isDefault = isDefault;
        addressInfo.phuongXa = "Chọn Phường Xá";
        addressInfo.quanHuyen = "Chọn Quận Huyện";
        addressInfo.thanhPho = "Chọn Thành Phố";
        addressInfo.phone = userInfo.phone;
        addressInfo.user = user;
        addressInfo.addressDetails = "Địa Chỉ Chi Tiết";
        userService.saveAddress(addressInfo);
    }
//===================================================================================================================
@PutMapping("/setDefault={id}/{token}")
public ResponseEntity<ResponseOpject> setdefault(@PathVariable long id,@PathVariable String token) {
    User userToken = tokenService.getUserFromToken(token);
    if (userToken == null){
        return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Lỗi token ", null);
    }

    AddressInfo addressByID = userService.getAddressByID(id);
    if (addressByID == null){
        return Util.checkStatusRes(HttpStatus.NOT_FOUND, "không tìm thấy địa chỉ có id " + id, null);
    }
    if (!addressByID.user.equals(userToken)){
        return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Bạn không có quyền ", null);
    }

    List<AddressInfo> addressInfoList = userService.getListAddress(userToken);

    for (AddressInfo addressInfo : addressInfoList){
        addressInfo.isDefault = false;
        userService.saveAddress(addressInfo);
    }
    addressByID.isDefault = true;
    userService.saveAddress(addressByID);

    return  Util.checkStatusRes(HttpStatus.OK, "Sửa thành công ", addressInfoList);
}
    @PutMapping("/edit/{id}/{token}")
    public ResponseEntity<ResponseOpject> edit(@PathVariable long id,@PathVariable String token,
                                               @RequestBody AddressInfo address) {
        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null){
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Lỗi token ", null);
        }

        AddressInfo addressByID = userService.getAddressByID(id);
        if (addressByID == null){
            return Util.checkStatusRes(HttpStatus.NOT_FOUND, "không tìm thấy địa chỉ có id " + id, null);
        }
        if (!addressByID.user.equals(userToken)){
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Bạn không có quyền ", null);
        }


        addressByID.addressDetails = address.addressDetails;
        addressByID.thanhPho = address.thanhPho;
        addressByID.fullname = address.fullname;
        addressByID.phuongXa = address.phuongXa;
        addressByID.quanHuyen = address.quanHuyen;
        addressByID.phone = address.phone;

        userService.saveAddress(addressByID);

        return  Util.checkStatusRes(HttpStatus.OK, "Sửa thành công ", addressByID);
    }
}
