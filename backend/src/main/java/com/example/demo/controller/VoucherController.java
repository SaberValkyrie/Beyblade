package com.example.demo.controller;

import com.example.demo.support.Util;
import com.example.demo.dto.ResponseOpject;
import com.example.demo.entity.MyVoucher;
import com.example.demo.entity.User;
import com.example.demo.entity.Voucher;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "/voucher")
public class VoucherController {
    @Autowired
    private UserService userService;
    @Autowired
    private NotifyService notifyService;
//    @Autowired
//    private CartService cartService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ProductService productService;

    @GetMapping("/{token}/{status}") //voucher của tôi
    public ResponseEntity<ResponseOpject> getVoucherByStatus(@PathVariable String token,
                                                      @PathVariable String status
    ) {
        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null) {
            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
        }
        List<Voucher> myVoucher = userService.getVoucherByUser(userToken);
        List<Voucher> listVoucher = new ArrayList<>();
        List<Voucher> hethan = userService.getVoucherHetHan(userToken);
        List<Voucher> used = userService.getvoucherUsed(userToken);

        switch (status){
            case "used":// đã sd
                listVoucher = used;
                break;
            case "hethan"://hết hạn
                  listVoucher = hethan;
                  break;
            case "khadung"://khả dụng
                addVoucherKhaDung(listVoucher,myVoucher);
                break;


        }
        return listVoucher(listVoucher);
    }
   private void addVoucherKhaDung(List<Voucher> khadung, List<Voucher> myVoucher){
       Timestamp currentTime = new Timestamp(System.currentTimeMillis());
              for (Voucher vc : myVoucher){
                  if (vc.countLeft > 0
                          && vc.startTime.before(currentTime)
                          && vc.endTime.after(currentTime)) {
                      khadung.add(vc);
                  }
              }
   }



   private ResponseEntity<ResponseOpject> listVoucher(List<Voucher> list){
       return list.size() > 0 ? Util.checkStatusRes(HttpStatus.OK, "Đã tìm được " + list.size() + " voucher", list)
               : Util.checkStatusRes(HttpStatus.NOT_FOUND, "Không tìm thấy voucher với trạng thái này", null);
   }
    @GetMapping("/find/{code}/{token}")//tìm voucher
    public ResponseEntity<ResponseOpject> getVouchers(@PathVariable String code,@PathVariable String token) {
        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null) {
            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
        }
        Voucher voucher = userService.getVoucherByCode(code);

        if (voucher == null){
            return Util.checkStatus(HttpStatus.NOT_FOUND, "Voucher không tồn tại", null);
        }
        if (voucher.countLeft <= 0){
            return Util.checkStatus(HttpStatus.BAD_REQUEST, "Voucher đã hết số lượt sử dụng", null);
        }

        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (voucher.endTime.before(now)){
            return Util.checkStatus(HttpStatus.BAD_REQUEST, "Voucher này đã hết hạn,vui lòng thử lại voucher khác", null);
        }
        if (voucher.startTime.after(now)){
            return Util.checkStatus(HttpStatus.BAD_REQUEST, "Voucher chưa tới thời gian sử dụng", null);
        }
        List<Voucher> myVoucher = userService.getVoucherByUser(userToken);
        MyVoucher m = userService.getMyVoucherByVoucher(voucher,userToken);
        if (m.status == false){
            return Util.checkStatus(HttpStatus.BAD_REQUEST, "Bạn đã dùng voucher này trước đó", null);
        }

        if (!myVoucher.contains(voucher)){
            MyVoucher myVc = new MyVoucher();
            myVc.user = userToken;
            myVc.voucher = voucher;
            myVc.status = true;
            myVc.timeSave = new Timestamp(System.currentTimeMillis());
            userService.saveMyVoucher(myVc);
        }
        return Util.checkStatusRes(HttpStatus.OK, "Đã tìm được voucher " + voucher.voucher_code, voucher);}
}