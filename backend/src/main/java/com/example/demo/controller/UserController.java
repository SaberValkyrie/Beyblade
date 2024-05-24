package com.example.demo.controller;

import com.example.demo.dto.AccountResponse;
import com.example.demo.dto.MessageRespone;
import com.example.demo.dto.UserResponse;
import com.example.demo.service.BeyService;
import com.example.demo.support.Util;
import com.example.demo.dto.ResponseOpject;
import com.example.demo.entity.Account;
import com.example.demo.entity.*;
//import com.example.demo.service.CartService;
import com.example.demo.service.NotifyService;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

@RestController
public class UserController {

    @Autowired
    private  UserService userService;

    @Autowired
    private BeyService beyService;


    @Autowired
    private TokenService tokenService;
//=========================================LOGIN/LOGOUT==========================================================================

    @PostMapping("/login")
    public ResponseEntity<ResponseOpject> login(@RequestBody Account loginRequest, HttpServletRequest request) {
        Account account = userService.authenticate(loginRequest);
        if (account == null){
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Sai tên đăng nhập hoặc mật khẩu", null);
        }
        User user = userService.getUserByAccountID(account.accountId);
        if(user == null){
            user = new User();
            user.accountId = account.accountId;
            user.avatar = "user_avatar.png";
            user.username = account.username;
            user.active = false;
            user.createdAt = new Timestamp(System.currentTimeMillis());
        }
        user.code = UUID.randomUUID().toString();
        userService.saveUser(user);


        Token token = tokenService.getTokenByUser(user);
        if (token == null) {
                 token = tokenService.generateToken(user);
        }
        AccountResponse accountResponse = new AccountResponse();

        accountResponse.token = token.code;
        accountResponse.user = user;



        TOP top = beyService.getTopByUser(user);
        if (top == null) {
            beyService.addTOP(user);
        }

        return Util.checkStatusRes(HttpStatus.OK, "Xác Minh Thành Công : " + user.username, accountResponse);
    }

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    @PostMapping("/register")
    public ResponseEntity<ResponseOpject> register(@RequestBody Account loginRequest) {
        Account acc = userService.authenticate(loginRequest);
        if (acc != null){
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Tài Khoản Đã Tồn Tại", null);
        }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        Account account = new Account();
        account.username = loginRequest.username;
        account.password = userService.mahoa(loginRequest.password);
        account.ban = false;
        account.coint = 0;
        account.createdAt = new Timestamp(System.currentTimeMillis());
        userService.saveAccount(account);
        //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        User user = new User();
            user.accountId = account.accountId;
            user.avatar = "user_avatar.png";
            user.username = account.username;
            user.createdAt = new Timestamp(System.currentTimeMillis());
            userService.saveUser(user);
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        Token token = tokenService.getTokenByUser(user);
        if (token == null) {
            token = tokenService.generateToken(user);
        }

        return Util.checkStatusRes(HttpStatus.OK, "Đăng Ký Thành Công : " + user.username, token);
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @PostMapping("/logout/{token}")
    public ResponseEntity<ResponseOpject> logout(HttpServletRequest request,@PathVariable String token) {
        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null) {
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Token sai", null);
        }
        tokenService.deleteToken(token);
        request.getSession().invalidate();
        return Util.checkStatusRes(HttpStatus.OK, "Đăng xuất thành công", null);
    }

    @GetMapping("/user/{token}")
    public ResponseEntity<ResponseOpject> getUser(@PathVariable String token) {
        User userFromToken = tokenService.getUserFromToken(token);
        if (userFromToken == null) {
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Token sai", null);
        }
        return Util.checkStatusRes(HttpStatus.OK, "Tìm thành công: " + userFromToken.username, userFromToken);
    }


    @GetMapping("/info/{username}")
    public ResponseEntity<ResponseOpject> getuser(@PathVariable String username) {
        User userFromToken = userService.getUserByUsername(username);
        if (userFromToken == null) {
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "User không tồn tại", null);
        }
        UserResponse response = new UserResponse(userFromToken.username,userFromToken.avatar,userFromToken.rank,userFromToken.createdAt);
        return Util.checkStatusRes(HttpStatus.OK, "Tìm thành công: " + userFromToken.username, response);
    }

    @GetMapping("/account/{token}")
    public ResponseEntity<ResponseOpject> getAccount(@PathVariable String token) {
        User userFromToken = tokenService.getUserFromToken(token);
        if (userFromToken == null) {
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Token sai", null);
        }
        Account account = userService.getAccountByUser(userFromToken.username);
        return account != null ? Util.checkStatusRes(HttpStatus.OK, "Tìm thành công: " + account.username, account)
                : Util.checkStatusRes(HttpStatus.NOT_FOUND, "không tìm thấy account với username là " + userFromToken.username, null);
    }

    @PutMapping("/user/edit/{token}")
    public ResponseEntity<ResponseOpject> editUser(@PathVariable String token,@RequestBody User user) {
        User userFromToken = tokenService.getUserFromToken(token);
        if (userFromToken == null) {
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Token sai", null);
        }
            userFromToken.avatar = user.avatar;
            userService.saveUser(userFromToken);

        return Util.checkStatusRes(HttpStatus.OK, "Sửa thông tin thành công : " + userFromToken.username, userFromToken);
    }




    @PostMapping("/chat/{username}/{token}")
    public ResponseEntity<ResponseOpject> sendChat(@PathVariable String username,
                                                   @PathVariable String token,
                                                   @RequestBody Map<String, String> requestBody
    ) {
        User userFromToken = tokenService.getUserFromToken(token);
        if (userFromToken == null) {
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Token sai", null);
        }
        User orther = userService.getUserByUsername(username);
        if (orther == null){
            return Util.checkStatusRes(HttpStatus.NOT_FOUND, "Không tìm thấy người dùng có username " + username, null);
        }
        if (orther.equals(userFromToken)){
            return Util.checkStatusRes(HttpStatus.FORBIDDEN, "Không thể nhắn với chính mình", null);
        }
        String text = requestBody.get("new_message");

        ChatGlobal messages = new ChatGlobal();
        messages.text = text;
        messages.status = false;
        messages.violate = false;
        messages.timeChat = new Timestamp(System.currentTimeMillis());
        transformText(messages);
        userService.saveMessage(messages);
        return Util.checkStatusRes(HttpStatus.OK, "Gửi tin nhắn thành công : " + text, messages);
    }
//===============================================NOTIFY====================================================================

    private String transformText(ChatGlobal chat) {
        String text = chat.text;
        text = text.replaceAll("admin", "***")
                .replaceAll("địt", "***")
                .replaceAll("lồn", "***")
                .replaceAll("buồi", "***")
                .replaceAll("cc", "***")
                .replaceAll(".mobi", "***")
                .replaceAll(".online", "***")
                .replaceAll(".info", "***")
                .replaceAll(".tk", "***")
                .replaceAll(".ml", "***")
                .replaceAll(".ga", "***")
                .replaceAll(".gq", "***")
                .replaceAll(".io", "***")
                .replaceAll(".club", "***")
                .replaceAll("http", "***")
                .replaceAll(".com", "***")
                .replaceAll("cltx", "***")
                .replaceAll("ôm cl", "***")
                .replaceAll("địt mẹ", "***")
                .replaceAll("như lồn", "***")
                .replaceAll("như cặc", "***");
        return chat.text = text;
    }



    //=======================================PROFILE============================================================================
    @GetMapping("/profile/{token}")
public ResponseEntity<ResponseOpject> getProfile(@PathVariable String token) {
        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null) {
            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
        }
        UserInfo userInfo = userService.getInfoByUser(userToken.userId);

        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.userId = userToken.userId;
            userInfo.fullname = "Họ Và Tên";
            userInfo.phone = "01234556789";
            userInfo.date = new Timestamp(System.currentTimeMillis());
            userInfo.shopeeName = "Nhập Tên Shopee của bạn (nếu có)";
            userInfo.email = "beyblade@gmail.com";
            userInfo.gender = "Khác";
            userInfo.updatedTime = new Timestamp(System.currentTimeMillis());
            userService.save(userInfo);
        }
        return Util.checkStatusRes(HttpStatus.OK, "Đã tìm được " + userInfo.fullname + "", userInfo);
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
@PutMapping("/profile/edit/{token}")
public ResponseEntity<ResponseOpject> editProfile(@PathVariable String token,
                                                  @RequestBody UserInfo profile) {
    User userToken = tokenService.getUserFromToken(token);
    if (userToken == null) {
        return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
    }
    UserInfo userInfo = userService.getInfoByUser(userToken.userId);
    userInfo.fullname = profile.fullname;
    userInfo.phone = profile.phone;
    userInfo.date = profile.date;
    userInfo.shopeeName = profile.shopeeName;
    userInfo.email = profile.email;
    userInfo.gender = profile.gender;
    userInfo.updatedTime = new Timestamp(System.currentTimeMillis());
    userService.save(userInfo);
    return Util.checkStatusRes(HttpStatus.OK, "Sửa thông tin thành công " + profile.fullname + "", userInfo);
}
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
@PostMapping("/profile/create/{token}")
public ResponseEntity<ResponseOpject> createProfile(@PathVariable String token,
                                                  @RequestBody UserInfo profile) {
    User userToken = tokenService.getUserFromToken(token);
    if (userToken == null) {
        return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
    }
    UserInfo userInfo = userService.getInfoByUser(userToken.userId);
    if (userInfo != null){
        return Util.checkStatusRes(HttpStatus.OK, "Đã có thông tin trước đó,không thể tạo", userInfo);
    }
    UserInfo info = new UserInfo();
    info.userId = userToken.userId;
    info.fullname = profile.fullname;
    info.shopeeName = profile.shopeeName;
    info.phone = profile.phone;
    info.date = profile.date;
    info.email = profile.email;
    info.gender = profile.gender;
    info.updatedTime = new Timestamp(System.currentTimeMillis());
    userService.save(info);
    return Util.checkStatusRes(HttpStatus.OK, "Thêm thành công " + info.fullname + "", info);
}
//===================================================================================================================
@GetMapping("/checkAuthen/{token}/{code}")
public ResponseEntity<ResponseOpject> check(@PathVariable String token,
                                                    @PathVariable String code
) {
    User userToken = tokenService.getUserFromToken(token);
    if (userToken == null) {
        return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
    }
    if (!userToken.code.equals(code)){

        return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Vui Lòng Đăng Nhập Lại", null);
    }
//    System.out.println(userToken.code + '/' + code);

    return Util.checkStatusRes(HttpStatus.OK, "Get Account Success", userToken);
}
//===================================================================================================================

    @PostMapping("/changePass/{token}/{codeXN}/{oldPass}")
    public ResponseEntity<ResponseOpject> changepass(
            @PathVariable String token,
             @PathVariable String codeXN,
            @PathVariable String oldPass,
            @RequestBody Account account
    ) {
        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null) {
            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
        }
        if (!userToken.code.equals(codeXN)){
            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Vui lòng login lại", null);
        }


        Account accountToken = userService.getAccountByUser(userToken.username);

        boolean pass = userService.matches(oldPass,accountToken.password);

        if (!pass){
            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Mật khẩu cũ không chính xác", null);
        }

        if (account.password.length() <= 6) {
            return Util.checkStatus(HttpStatus.BAD_REQUEST, "Mật khẩu mới phải có ít nhất 7 ký tự", null);
        }

        accountToken.password = userService.mahoa(account.password);
        userService.saveAccount(accountToken);

        userToken.code = UUID.randomUUID().toString();
        userService.saveUser(userToken);


        return Util.checkStatusRes(HttpStatus.OK, "Đổi Mật Khẩu Thành Công", accountToken);
    }
}
