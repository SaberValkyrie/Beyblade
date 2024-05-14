package com.example.demo.controller;

import com.example.demo.dto.ResponseOpject;
import com.example.demo.entity.Account;
import com.example.demo.entity.User;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import com.example.demo.support.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/game")
public class GameController {


    @Autowired
    private UserService userService;



    @Autowired
    private TokenService tokenService;


    private long st;
    @GetMapping("/spin/{token}")
    public ResponseEntity<ResponseOpject> getSpin(@PathVariable String token) {
        User userFromToken = tokenService.getUserFromToken(token);
        if (userFromToken == null) {
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Token sai", null);
        }
        Account accountToken = userService.getAccountByUser(userFromToken.username);
        short time = 2000;
        short dongia = 1000;
        long timeleft = (time - (System.currentTimeMillis() - st))/1000;
        if (!Util.canDoWithTime(st,time)){
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Vui Lòng Chờ " + timeleft + " giây nữa" , null);
        }
        st = System.currentTimeMillis();
        if (accountToken.coint < dongia) {
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Bạn không đủ tiền,vui lòng nạp thêm để tiếp tục", null);
        }
        return Util.checkStatusRes(HttpStatus.OK, "Quay Tay Nào !!!!", userFromToken);
    }



    @PostMapping("/spin/{token}")
    public ResponseEntity<ResponseOpject> spinNow(@PathVariable String token) {
        User userFromToken = tokenService.getUserFromToken(token);
        if (userFromToken == null) {
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Token sai", null);
        }
        Account accountToken = userService.getAccountByUser(userFromToken.username);
        short dongia = 1000;

        accountToken.coint -= dongia;
        userService.saveAccount(accountToken);

        if (Util.isTrue(90,100)){
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Chúc bạn may mắn lần sau!", null);
        }


        return Util.checkStatusRes(HttpStatus.OK, "Chúc Mừng Bạn Đã Trúng Prize", userFromToken);
    }
}
