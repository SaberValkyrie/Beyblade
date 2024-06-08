package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.repository.giftcode.GiftcodeRepo;
import com.example.demo.repository.giftcode.HistoryRepo;
import com.example.demo.service.BeyService;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import com.example.demo.support.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/game")
public class GameController {




    @Autowired
    private UserService userService;



    @Autowired
    private TokenService tokenService;

    @Autowired
    private BeyService service;







    @GetMapping("/getAllTypes")
    public ResponseEntity<ResponseOpject> getTypes() {
        List<TypeBey> list = service.getAllTypes();
        return Util.checkStatusRes(HttpStatus.OK, "Đã tìm được " + list.size() + " hệ",  list);
    }

    @PostMapping("/setNap/{token}/{name}/{amount}")
    public ResponseEntity<ResponseOpject> setNap(@PathVariable String token,@PathVariable String name,@PathVariable int amount) {
        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null){
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Lỗi token ", null);
        }
        Account accountToken = userService.getAccountByUser(userToken.username);

        if (accountToken.role != 1){
            return Util.checkStatusRes(HttpStatus.NOT_FOUND, "Mày ko phải admin",  null);
        }
        Account accnhan = userService.getAccountByUser(name);
        if (accnhan == null){
            return Util.checkStatusRes(HttpStatus.NOT_FOUND, "ko tồn tại "+ name,  null);
        }
        accnhan.tienmat += amount;
        userService.saveAccount(accnhan);

        return Util.checkStatusRes(HttpStatus.OK, "Nạp thành công", accnhan );
    }

    @GetMapping("/getBeyDefault/{token}")
    public ResponseEntity<ResponseOpject> getBeyDefault(@PathVariable String token) {
        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null){
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Lỗi token ", null);
        }
       Items item = userService.getItemMacDinhByUser(userToken);
        if (item == null){
            return Util.checkStatusRes(HttpStatus.NOT_FOUND, "Vui Lòng Vào Kho Đồ Chọn 1 Beyblade",  null);
        }
      return Util.checkStatusRes(HttpStatus.OK, "Đã tìm được " +item.beyBlade.name + "",  item.beyBlade);
    }

    @GetMapping("/getItemsBag/{token}")
    public ResponseEntity<ResponseOpject> getItems(@PathVariable String token) {
        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null){
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Lỗi token ", null);
        }
        Player player = new Player();
        player.user = userToken.username;
        player.avatar = userToken.avatar;
        player.items = userService.getItemsByUser(userToken);

        return Util.checkStatusRes(HttpStatus.OK, "Đã tìm được " +player.items.size() + " items",  player);
    }
    @PostMapping("/checkCode/{token}/{code}")
    public ResponseEntity<ResponseOpject> checkGiftCode(@PathVariable String token,
                                                        @PathVariable String code  ) {
        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null){
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Lỗi token ", null);
        }
        GIFTCODE giftcode = service.findGiftCodeByCode(code);

        if (giftcode == null){
            return Util.checkStatusRes(HttpStatus.NOT_FOUND, "Mã Quà Tặng Không Tồn Tại",  null);
        }
//        GiftcodeHistory mycode = service.getHistory(code);
        if (giftcode.used){
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Giftcode đã được sử dụng",  null);
        }
        GiftcodeHistory history = new GiftcodeHistory();
        history.giftcode = giftcode;
        history.user = userToken;

        service.saveHistory(history);
        giftcode.used = true;
        service.saveCode(giftcode);


        return service.useItemCode(userToken,giftcode);
    }

    @GetMapping("/getcode/{token}/{type}")
    public ResponseEntity<ResponseOpject> getcode(
            @PathVariable String token,
            @PathVariable int type

    ) {
        User userFromToken = tokenService.getUserFromToken(token);
        if (userFromToken == null) {
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Vui Lòng Đăng Nhập Lại", null);
        }
        Account accountToken = userService.getAccountByUser(userFromToken.username);


        if (accountToken.role != 1){
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "cút", null);
        }

        List<GIFTCODE> giftcodes = userService.getCodeKhaDung(type);


        return Util.checkStatusRes(HttpStatus.OK, "d", giftcodes);


    }
    @PutMapping("/setItem/{token}")
    public ResponseEntity<ResponseOpject> setItem(@PathVariable String token,
                                                  @RequestBody Items item) {
        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null){
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Lỗi token ", null);
        }
        if (item == null){
            return Util.checkStatusRes(HttpStatus.NOT_FOUND, "Beyblade đã hết hạn",  item);
        }
        List<Items> items = userService.getItemsByUser(userToken);
        for (Items edit : items){
            if (edit.selectedBey){
                edit.selectedBey = false;
            }
            if (item.equals(edit)){
                item.selectedBey = true;
            }
            userService.saveItem(userToken,edit);
            userService.saveItem(userToken,item);
        }
        TOP top = service.getTopByUser(userToken);
        top.selectBey = item.beyBlade;
        service.saveTop(top);
        return Util.checkStatusRes(HttpStatus.OK, "Chọn thành công " + item.beyBlade.name,  item);
    }



    @PutMapping("/buffHP/{token}/{x}/{type}")
    public ResponseEntity<ResponseOpject> buffHP(@PathVariable String token,
                                                 @PathVariable int x,@PathVariable int type
    ) {
        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null){
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Lỗi token ", null);
        }
        TOP top = service.getTopByUser(userToken);
        if (top.buff > 0){
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Không thể mua vì Buff x" + top.buff + " của bạn đang còn hiệu lực,vui lòng chờ tới " + top.endBuff, null);
        }

        int price = 0;
        switch (x){
            case 2:
                price = 3000;
                break;
            case 3:
                price = 4000;
                break;
            case 5:
                price = 5000;
                break;
            default:
                return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Không có dạng buff này ", null);
        }

        Account accountToken = userService.getAccountByUser(userToken.username);
        switch (type) {
            case 1 :
                price *= 20;
                if (accountToken.coint < price){
                    return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Bạn không đủ BeyPoint",  null);
                }
                accountToken.coint -= price;
                break;
            default:
                if (accountToken.tienmat < price){
                    return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Bạn không đủ số dư",  null);
                }
                accountToken.tienmat -= price;
        }
        userService.saveAccount(accountToken);

        top.buff = (byte) x;

        long millisecondsInADay = TimeUnit.DAYS.toMillis(1);
        top.endBuff = new Timestamp(System.currentTimeMillis() + millisecondsInADay);

        service.saveTop(top);
        return Util.checkStatusRes(HttpStatus.OK, "Bạn đã buff x" + x + " HP trên TOP thành công" ,  null);
    }


    @PostMapping("/buyItem/{token}")
    public ResponseEntity<ResponseOpject> buyItem(@PathVariable String token,
                                                  @RequestBody ItemShop item) {
        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null){
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Lỗi token ", null);
        }
        if (item == null){
            return Util.checkStatusRes(HttpStatus.NOT_FOUND, "Beyblade đã hết hạn",  item);
        }
        if (item.beyBlade.isBoss && !userToken.active){
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Beyblade ưu tiên chỉ bán cho những người đã mở thành viên,vui lòng chọn beyblade khác!",  null);
        }
        if (item.quantity <= 0) {
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Sản Phẩm Đã Hết Hàng", null);
        }
        if (item.beyBlade.id == 207) {
            Items hyperion = service.getItemsByID(userToken,203);
            Items helios = service.getItemsByID(userToken,204);

            if (hyperion == null){
                return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Hãy Sở Hữu Hyperion MR vĩnh viễn trước", null);
            }
            if (helios == null){
                return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Hãy Sở Hữu Helios MR vĩnh viễn trước", null);
            }
        }
        Items items = new Items();
        Account accountToken = userService.getAccountByUser(userToken.username);
        if (item.price > accountToken.coint){
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Bạn còn thiếu " + Util.numberToMoney((long) (item.price - accountToken.coint)) + " Beypoint",  null);
        }
        accountToken.coint -= item.price;
        items.user = userToken;
        items.beyBlade = item.beyBlade;
        items.vinhvien = item.day > 0 ? false : true ;
        items.selectedBey = false;

        // Create the creation timestamp
        items.create_time = new Timestamp(System.currentTimeMillis());

        long millisecondsInADay = TimeUnit.DAYS.toMillis(item.day);

        items.ngayhethan = new Timestamp(items.create_time.getTime() + millisecondsInADay);


        userService.saveAccount(accountToken);
        userService.saveItem(userToken,items);

        // Giảm số lượng của item trong danh sách item_shop
        service.item_shop.get(item.stt).quantity -= 1;


        return Util.checkStatusRes(HttpStatus.OK, "Mua Thành Công " + items.beyBlade.name,  items);
    }

    @PostMapping("/sellItem/{token}")
    public ResponseEntity<ResponseOpject> sell(@PathVariable String token,
                                                  @RequestBody Items item) {
        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null){
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Lỗi token ", null);
        }
        if (item == null){
            return Util.checkStatusRes(HttpStatus.NOT_FOUND, "Beyblade Không Tồn Tại",  null);
        }
        Items items = service.getItemsByItemID(userToken,item.id);
        if (items == null) {
            return Util.checkStatusRes(HttpStatus.NOT_FOUND, "Không tìm thấy Item", null);
        }
        if (!items.vinhvien){
            return Util.checkStatusRes(HttpStatus.NOT_FOUND, "Hãy chọn Beyblade Vĩnh Viễn",  null);
        }
        userService.deteleItem(items);
        Account accountToken = userService.getAccountByUser(userToken.username);

        int coint = Util.nextInt(item.beyBlade.price / 5,item.beyBlade.price / 2);

        accountToken.coint += coint;
        userService.saveAccount(accountToken);

        return Util.checkStatusRes(HttpStatus.OK, "Bán Thành Công " + items.beyBlade.name + " và nhận được " + Util.numberToMoney(coint) + " Beypoint",  items);
    }







    @GetMapping("/register")
    public ResponseEntity<ResponseOpject> getAllregister() {
        List<Register> list = service.topDangKy;
        return  Util.checkStatusRes(HttpStatus.OK, "Đã tìm được " + list.size() + "" ,  list);
    }
    @PostMapping("/register/{token}") //đăng kí và random bey
    public ResponseEntity<ResponseOpject> res(@PathVariable String token,@RequestBody TypeBey typeBey) {
        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null){
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Lỗi token ", null);
        }
        // Kiểm tra username đã tồn tại
        if (service.isUsernameTaken(userToken.username)) {
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Bạn đã đăng ký trước đó", null);
        }

        if (LocalTime.now().getHour() % 2 == 0){//0 2 4 6
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, " Chỉ được đăng kí vào các khung giờ lẻ , ví dụ 1h, 3h ,5h ,7h,..", null);
        }

        if (service.topDangKy.size() >= 12){
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Số lượng người đăng ký đã đạt giới hạn", null);
        }

        BeyBlade beyBlade = service.getRandomBeyBySS(typeBey.id);
        Register register = new Register();
        register.avatar = userToken.avatar;
        register.username = userToken.username;
        register.type = typeBey;
        register.createTime = new Timestamp(System.currentTimeMillis());
        register.isDie = false;
        register.bey = beyBlade;

        register.hp = (int) ((typeBey.id == 1 ? 1_000_000
                                                : (typeBey.id == 2 ? 2_000_000
                                                : (typeBey.id == 3 ? 3_000_000 : 1_000_000))
                                                ) + beyBlade.hp);

        register.buff = 5;
        service.topDangKy.add(register);

        return  Util.checkStatusRes(HttpStatus.OK, "Đăng Ký Thành Công Với username: " + userToken.username  ,  register);
    }

    @GetMapping("/checkBey/{token}")//check khi vào trang battle
    public ResponseEntity<ResponseOpject> randomBey(@PathVariable String token) {
        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null){
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Lỗi token ", null);
        }
        Register res = service.getByUserName(userToken.username);
        if (res == null){
            return Util.checkStatusRes(HttpStatus.NOT_FOUND, "Vui Lòng Đăng Ký Trước ", null);
        }
        int hour = LocalTime.now().getHour();
        if (hour % 2 != 0) {
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Vui lòng chờ tới " + (LocalTime.now().getHour() + 1 ) + "h 0p để bắt đầu" , null);
        }
        return Util.checkStatusRes(HttpStatus.OK, "Tìm được ", res);
    }
    @PostMapping("/setSinhTon")//check khi mình win battle
    public ResponseEntity<ResponseOpject> setSinhTon(@RequestBody BattleSinhTon battleSinhTon) {
        Register res = service.getByUserName(battleSinhTon.user1);
        if (res == null){
            return Util.checkStatusRes(HttpStatus.NOT_FOUND, "Vui Lòng Đăng Ký Trước ", null);
        }

       Register dich = service.getByUserName(battleSinhTon.user2);
        if (dich.isDie){
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Đã có người hạ đối thủ này trước bạn", null);
        }

        dich.isDie = true;

        int c = Util.nextInt(1000,3000);
        Account account = userService.getAccountByUser(battleSinhTon.user1);
        account.coint += c;
        userService.saveAccount(account);

        Register top1 = service.getTop1();
        if (top1 != null && top1.username.equals(account.username)){
            account.coint += 10000;
            userService.saveAccount(account);

            User u = userService.getUserByUsername(top1.username);

            userService.addVoucherInBag(u,14);
            userService.addVoucherInBag(u,15);

            service.addThongBao(battleSinhTon.user1 + " Đã đánh bại " + battleSinhTon.user2 + " và đạt chức vô địch ", (byte) 1);

            return Util.checkStatusRes(HttpStatus.OK, "Chúc mừng bạn đã vô địch,bạn nhận được " + Util.numberToMoney(c + 10000) + " Beypoint và phần quà bí mật", res);

        }
        service.addThongBao(battleSinhTon.user2 + " Đã bị hạ bởi " + battleSinhTon.user1, (byte) 1);

        return Util.checkStatusRes(HttpStatus.OK, "Chúc mừng bạn đã đánh bại được  "+ battleSinhTon.user2 + " và nhận được " + Util.numberToMoney(c) + " Beypoint", res);
    }
    @GetMapping("/thongbao/{type}")//get boss
    public ResponseEntity<ResponseOpject> thongbao(@PathVariable byte type) {
        return Util.checkStatusRes(HttpStatus.OK, "", service.getThongBao(type));
    }
    @PostMapping("/setBattle/{token}")//get boss
    public ResponseEntity<ResponseOpject> res(@PathVariable String token,
                                              @RequestBody Register register) {
        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null){
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Lỗi token ", null);
        }
        Register res = service.getByUserName(register.username);
        if (res == null){
            return Util.checkStatusRes(HttpStatus.NOT_FOUND, "Vui Lòng Đăng Ký Trước ", null);
        }
        if (res.isDie){
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, res.username + " đã bị hạ", null);
        }
        return  Util.checkStatusRes(HttpStatus.OK, "Hãy Chiến Đấu chống lại " + register.username + " nào!"  ,  res);
    }


    @GetMapping("/checkRegister/{token}")
    public ResponseEntity<ResponseOpject> regis(@PathVariable String token) {
        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null){
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Lỗi token ", null);
        }
        // Kiểm tra username đã tồn tại
        if (service.isUsernameTaken(userToken.username)) {
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Bạn đã đăng ký trước đó", true);
        }
        return  Util.checkStatusRes(HttpStatus.OK, "Đăng Ký Thành Công Với username: " + userToken.username  ,  false);
    }


    @GetMapping("/getBey/{type}")
    public ResponseEntity<ResponseOpject> getBey(@PathVariable byte type) {
        List<BeyBlade> list = service.getBeyByTypeID(type);
        String he = "";
        switch (type){
            case 1:
                he = "Tấn Công";
                break;
            case 2:
                he = "Phòng Thủ";
                break;
            case 3:
                he = "Bền Bỉ";
                break;
            case 4:
                he = "Cân Bằng";
                break;
        }
        if (type > 4){
         return Util.checkStatusRes(HttpStatus.NOT_FOUND, "Không tồn tại hệ này",  null);
        }
        return  Util.checkStatusRes(HttpStatus.OK, "Đã tìm được " + list.size() + " beyblade với hệ " + he,  list);
    }




    @GetMapping("/getVoucher/{token}/{status}")
    public ResponseEntity<ResponseOpject> getBeyByToken(@PathVariable String token,@PathVariable byte status) {
        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null){
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Lỗi token ", null);
        }
        List<MyPrize> myPrizes  = userService.getPrizeByStatus(userToken.username,status);

        return  Util.checkStatusRes(HttpStatus.OK, "Đã tìm được "  + " beyblade với hệ ",  myPrizes);
    }


    @PostMapping("/useItem/{token}")
    public ResponseEntity<ResponseOpject> useItem(@PathVariable String token
            ,@RequestBody MyPrize myPrize) {
        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null){
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Lỗi token ", null);
        }
        return service.useItem(userToken,myPrize);
    }



    @GetMapping("/getBey/{type}/{token}")
    public ResponseEntity<ResponseOpject> getBeyByToken(@PathVariable byte type,@PathVariable String token) {

        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null){
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Lỗi token ", null);
        }
        List<BeyBlade> list = userService.getItemsByUserAndType(userToken,type);

        String he = "";
        switch (type){
            case 1:
                he = "Tấn Công";
                break;
            case 2:
                he = "Phòng Thủ";
                break;
            case 3:
                he = "Bền Bỉ";
                break;
            case 4:
                he = "Cân Bằng";
                break;
        }

        if (type > 4){
            return Util.checkStatusRes(HttpStatus.NOT_FOUND, "Không tồn tại hệ này",  null);
        }

        return  Util.checkStatusRes(HttpStatus.OK, "Đã tìm được " + list.size() + " beyblade với hệ " + he,  list);
    }




    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseOpject> getBey(@PathVariable long id) {
        BeyBlade list = service.getBeyByID(id);
        if (list == null){
            return Util.checkStatusRes(HttpStatus.NOT_FOUND, "Không tồn tại",  null);
        }
        return  Util.checkStatusRes(HttpStatus.OK, "Đã tìm được " + list.name ,  list);
    }




    @GetMapping("/shop")
    public ResponseEntity<ResponseOpject> getShop() {
        List<ItemShop> beyBlades = service.item_shop;
        return Util.checkStatusRes(HttpStatus.OK, "Đã tìm được " + beyBlades.size() + " sản phẩm ", beyBlades);
    }

    @GetMapping("/item/{code}")
    public ResponseEntity<ResponseOpject> getItem(@PathVariable String code) {
       ItemShop itemShop = service.getItemShopByCode(code);
       if (itemShop == null){
           return Util.checkStatusRes(HttpStatus.NOT_FOUND, "Không tồn tại sản phẩm", null);
       }
        return Util.checkStatusRes(HttpStatus.OK, "Đã tìm được " + itemShop.beyBlade.name, itemShop);
    }




    @GetMapping("/spin/{token}/{bey}/{type}")
    public ResponseEntity<ResponseOpject> getSpin(
            @PathVariable String token,
            @PathVariable long bey,
            @PathVariable int type
    ) {
        User userFromToken = tokenService.getUserFromToken(token);
        if (userFromToken == null) {
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Token sai", null);
        }
        short time = 500;

        switch (type){
            case 0: //đấu boss
               if(!userFromToken.active){
                   if (userFromToken.diem > 100) {
                       return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Bạn chỉ được đánh 100 cú mỗi giờ,vui lòng mở thành viên để đánh vô hạn", null);
                   }
                   userFromToken.diem += 1;
                   userService.saveUser(userFromToken);
               }
                break;
        }
        return Util.checkStatusRes(HttpStatus.OK, "Quay Tay Nào !!!!", userFromToken);
    }


    @PostMapping("/updateBoss/{token}")
    public ResponseEntity<ResponseOpject> updateBoss(
            @RequestBody CheckOption option,
            @PathVariable String token
    ) {
        User userFromToken = tokenService.getUserFromToken(token);
        if (userFromToken == null) {
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Token sai", null);
        }

        service.boss.hp -= option.dameMe.dame;

        if (service.boss.playerKill != null){
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Boss đã bị hạ bởi " + service.boss.playerKill.username, service.boss.playerKill);
        }

        if (service.boss.hp <= 0){
            service.boss.playerKill = userFromToken;
            service.boss.die = true;
            //boss TG die
            service.addPrize(service.boss.playerKill);
        }
        return Util.checkStatusRes(HttpStatus.OK,  "update thành công", service.boss.hp);
    }
    @PostMapping("/naptien/{token}/{amount}")
    public ResponseEntity<ResponseOpject> naptien(
            @PathVariable String token,
            @PathVariable int amount
    ) {
        User userFromToken = tokenService.getUserFromToken(token);
        if (userFromToken == null) {
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Vui Lòng Đăng Nhập Lại", null);
        }
        Account accountToken = userService.getAccountByUser(userFromToken.username);

        if (accountToken.tienmat < amount){
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Tài khoản của bạn không đủ " + Util.numberToMoney(amount) + " Số Dư,vui lòng kiểm tra lại", null);
        }
        accountToken.tienmat -= amount;
        accountToken.coint += amount * 2;
        userService.saveAccount(accountToken);
        String t = "Đổi thành công "+ Util.numberToMoney(amount) + " số dư sang " + Util.numberToMoney(amount * 2) + " BeyPoint";

        if (amount >= 20000 && Util.isTrue(70,100)){
            userService.addVoucherInBag(userFromToken,5);
            t += "\n Và nhận được 1 Hộp Quà VIP";
        }
        return Util.checkStatusRes(HttpStatus.OK, t, null);


    }

    @PostMapping("/thachdau/{token}")
    public ResponseEntity<ResponseOpject> getTopByUser(
            @PathVariable String token,
           @RequestBody TOP topdich
    ) {
        User userFromToken = tokenService.getUserFromToken(token);
        if (userFromToken == null) {
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Vui Lòng Đăng Nhập Lại", null);
        }

        TOP topme = service.getTopByUser(userFromToken);

        if (userFromToken.equals(topdich.user)){
            return Util.checkStatusRes(HttpStatus.OK, "Hãy Buff chỉ số bản thân để tăng cơ hội giữ top nhé!", null);
        }

        if (topme.top < topdich.top){
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Top của bạn đã cao hơn đối thủ rồi", null);
        }
//
//        if (!userFromToken.active && userFromToken.diem >= 10){
//            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Ngày hôm nay bạn đã thách đấu đủ 10 lần,vui lòng mở thành viên để tăng hạn mức thách đấu!", null);
//        }


        int maxHangPK;
        if (topme.top == 101) { //top 51-100 thách đấu tối đa 5 bậc
            maxHangPK = 6;
        } else if (topme.top >= 51 && topme.top <= 100) { //top 51-100 thách đấu tối đa 5 bậc
            maxHangPK = 5;
        } else if (topme.top >= 31 && topme.top <= 50) { //top 31-50 chỉ thách đấu tối đa 4 bậc
            maxHangPK = 4;
        } else if (topme.top >= 21 && topme.top <= 30) { //top 21-30 chỉ thách đấu tối đa 3 bậc
            maxHangPK = 3;
        } else if (topme.top >= 11 && topme.top <= 20) { //top 11-20 chỉ thách đấu tối đa 2 bậc
            maxHangPK = 2;
        } else if (topme.top <= 10) { //top 1 tới 9 chỉ thách đấu tối đa 1 bậc
            maxHangPK = 1;
        } else {
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Thứ hạng của bạn không nằm trong khoảng cho phép thách đấu", null);
        }
        if (topme.top - topdich.top > maxHangPK) {
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, maxHangPK > 1? "Hạng của bạn chỉ có thể thách đấu từ hạng " + (topme.top -maxHangPK )  + " tới " + (topme.top - 1)
                    : "Hạng của bạn chỉ có thể thách đấu với hạng " + (topme.top -1 ), null);
        }


        return Util.checkStatusRes(HttpStatus.OK, "đã tìm được đối thủ ở hạng " +topdich.top , topdich);
    }

    @GetMapping("/getBosses")
    public ResponseEntity<ResponseOpject> getbosss() {
        List<BeyBlade> list = service.getBoss();

        int randomIndex = new Random().nextInt(list.size());
        int randomId = (int) list.get(randomIndex).id;

        BeyBoss beyBoss = new BeyBoss();
        beyBoss.bey = service.getBeyByID(randomId);
        beyBoss.buff = (byte) (beyBoss.bey.type.id + Util.nextInt(1,5) / 5);
        beyBoss.time = (byte) LocalTime.now().getHour();

        beyBoss.hp = beyBoss.buff * beyBoss.bey.hp;
        beyBoss.dame = beyBoss.buff * beyBoss.bey.power;

        return Util.checkStatusRes(HttpStatus.OK, "Đã tìm được " + beyBoss.bey.name + " với sức mạnh x" + beyBoss.buff, beyBoss);
    }


    @GetMapping("/getBossTG")
    public ResponseEntity<ResponseOpject> getbossTG() {
        BeyBoss boss = service.boss;
        return Util.checkStatusRes(HttpStatus.OK, "          HP:" + Util.numberToMoney(boss.hp) + "                ", boss);
    }


    @GetMapping("/top")
    public ResponseEntity<ResponseOpject> getTop() {
        List<TOP> topDB = service.getTop();
        List<TOP> topBOT = service.topList;
        List<TOP> topAll = service.getTopAll(topBOT, topDB);
        for (TOP top : topAll) {
            User user = top.user;
            if (user.userId != 0) {
                List<Items> itemsList = userService.getItemIDByUser(user, top.selectBey);
                boolean case2 = itemsList.stream()
                        .anyMatch(item -> item.beyBlade.equals(top.selectBey) && item.selectedBey);
                if (!case2) {
                    top.selectBey = service.getBeyByID(1);
                    service.saveTop(top);
                }
            }
        }
        return Util.checkStatusRes(HttpStatus.OK, "Quay Tay Nào !!!!", topAll);
    }




    @PostMapping("/setKeThu")
    public ResponseEntity<ResponseOpject> setKeThu(
            @RequestBody TOP top
    ) {
        BeyBoss beyBoss = new BeyBoss();
        beyBoss.dame =  top.selectBey.power;
        beyBoss.hp = top.selectBey.hp;
        beyBoss.buff = top.buff;
        beyBoss.time = (byte) LocalTime.now().getHour();
        beyBoss.bey = top.selectBey;
        beyBoss.die = false;
        beyBoss.playerKill = null;

        return Util.checkStatusRes(HttpStatus.OK, "Đã tìm được bey", beyBoss);
    }
    @PostMapping("/getKQ")
    public ResponseEntity<ResponseOpject> getKQ(@RequestBody CheckKQ battle) {
        List<TOP> topDB = service.getTop(); // Dữ liệu từ cơ sở dữ liệu
        List<TOP> topBOT = service.topList;

        // Kết hợp hai danh sách
        List<TOP> topAll = service.getTopAll(topBOT,topDB);

        User userByTopInDB = service.getUserByTop(battle.topUser2); //check top x có phải user này không?

        boolean case1 = userByTopInDB != null && !userByTopInDB.username.equals(battle.user2);

        TOP topcung = new TOP();
        for (TOP top : topAll){
            if (top.top == battle.topUser2){
               topcung = top;
               break;
            }
        }
        boolean case2 = topcung.user.username.equals(battle.user2) ;

        if (case1 || !case2){
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Đối thủ đã bị người khác đánh bại trước bạn", null);
        }
        return Util.checkStatusRes(HttpStatus.OK, "Đã tìm được bey", null);
    }


    @PostMapping("/setKQ")
    public ResponseEntity<ResponseOpject> setKQ(@RequestBody CheckKQ battle) {

        TOP topMe = service.getTopByUserName(battle.user1);
        TOP topAnother = null;

        List<TOP> topDB = service.getTop(); // Dữ liệu từ cơ sở dữ liệu
        List<TOP> topBOT = service.topList;

        String txt="Nhân Vật   "+ "⏫" + battle.user1 + "⏫" + "    Đã Đánh Bại     "+ "⏬" + battle.user2 + "⏬" +   "      và đạt TOP " + battle.topUser2 + "";
        // Kết hợp hai danh sách
        List<TOP> topAll = service.getTopAll(topBOT,topDB);
        int top = 0;
        for (TOP t : topAll){
            if (t.top == battle.topUser2){
                 topAnother = service.getTopByUserName(battle.user2);
                top = topAnother != null ? topAnother.top : t.top;
              if (topAnother != null){
                  topAnother.top = topMe.top;
                  topAnother.lost += 1;
                  service.saveTop(topAnother);
              }
                break;
            }
        }

        if (top != battle.topUser2) { // so sánh nếu top của another hiện tại khác top lúc thách đấu
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Đã có người đánh bại " + battle.user2 + " trước bạn", null);
        }


        topMe.top = top;
        topMe.win += 1;
        service.saveTop(topMe,txt);
        return Util.checkStatusRes(HttpStatus.OK, "Chúc Mừng Bạn đã đánh bại được " + battle.user2 + " và đạt top " + top, battle);
    }

    @PostMapping("/attack")
    public ResponseEntity<ResponseOpject> BossAttack(
            @RequestBody BeyBattle battle
    ) {
        return attack(battle.me,battle.boss.bey,battle.boss.dame, (byte) 0);
    }


    @PostMapping("/attackSinhTon")
    public ResponseEntity<ResponseOpject> attackSinhTon(
            @RequestBody SinhTon battle
    ) {
        return attack(battle.me,battle.boss,battle.boss.power, (byte) 0);
    }


    @PostMapping("/spin/{token}/{type}")
    public ResponseEntity<ResponseOpject> spinNow(
            @PathVariable String token,
            @PathVariable byte type,
            @RequestBody BeyBattle battle
    ) {
        User userFromToken = tokenService.getUserFromToken(token);
        if (userFromToken == null) {
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Token sai", null);
        }
        return attack(battle.boss.bey,battle.me,battle.me.power,type);
    }




public static int pb = 2_100_000_000;



    public ResponseEntity<ResponseOpject> attack(BeyBlade beyOrther,BeyBlade beyChinh,long dame,byte type){
        short tiLeNeDon = beyOrther.tiLeNeDon;
        short tileCrit = beyChinh.crit;

        BeyDame beyDame = new BeyDame();
        beyDame.hutdame = 0;
        beyDame.notung = false;
        String txtBurst = beyChinh.name + " đã đánh burst " + beyOrther.name;

        boolean isNe = false;
        String stringNe = beyOrther.name + " đã né được đòn của " + beyChinh.name + " bằng tỉ lệ "+ tiLeNeDon + "%";
        String text = beyChinh.name + " đã gây được " + Util.numberToMoney(dame) + " dame";
        boolean hut = false;


        dame += (dame * Util.nextInt(-20,20) / 100) ;


        if (Util.isTrue(tileCrit,100)){

            dame *= 2;
            if (beyChinh.isBoss){
                dame *=Util.nextInt(1,3);
            }
            text =  beyChinh.name + " đã gây được " + Util.numberToMoney(dame) + " dame chí mạng với tỉ lệ là "+tileCrit +"%";
        }

        long damehut = dame / 4;
        String hutt = beyOrther.name + " vừa hấp thụ " + Util.numberToMoney(damehut)  + " sức mạnh nhờ ngược chiều";
        if (isNguocChieu(beyOrther, beyChinh) && Util.isTrue(50,100)){
            if (isRubber(beyOrther)){
                damehut *= 2;
                hutt = beyOrther.name + " vừa hấp thụ " + Util.numberToMoney(damehut)  + " sức mạnh nhờ cao su";
            }
            beyDame.hutdame = damehut;
            hut = true;
        }
        if (type == 0 && Util.isTrue(8 - (beyOrther.season),100)){
            if (isBurstStopper(beyOrther) && Util.isTrue(70,100) || isLock(beyOrther) && Util.isTrue(90,100)){
                dame = 2;
                txtBurst = beyOrther.name + " đã chặn được cú Burst từ " + beyChinh.name;

            }else{
                dame = pb;
                beyDame.notung = true;
            }
        }

        if (Util.isTrue(tiLeNeDon,100)){
            dame = 1;
            isNe = true;
        }
        beyDame.name = beyChinh.name;
        beyDame.dame = dame;


        if (hut){
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST,  hutt, beyDame);
        }
        if (beyDame.notung){
            return  Util.checkStatusRes(HttpStatus.OK,  txtBurst, beyDame);
        }
        if (isNe){
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST,  stringNe, beyDame);
        }

        return Util.checkStatusRes(HttpStatus.OK,  text, beyDame);

    }

    @PostMapping("/spin/check")
    public ResponseEntity<ResponseOpject> checkout(
            @RequestBody CheckOption option
    ) {
        CheckOption newOption = new CheckOption();
        BeyBlade me = option.me;
        BeyBoss boss = option.boss;
        newOption.dameMe = option.dameMe;
        newOption.dameBoss = option.dameBoss;
        newOption.win = option.win;
        newOption.pointBoss = option.pointBoss;
        newOption.pointMe = option.pointMe;
        newOption.me = option.me;
        newOption.boss = option.boss;
        byte tiso = 3;
        String txt = "";
        byte pointMe = 0;
        byte pointBoss = 0;

        if (boss.bey == null){
            return Util.checkStatusRes(HttpStatus.NOT_FOUND,  "Không tìm thấy bey Boss", newOption);
        }

        if (option.dameMe.dame == pb){// me đánh boss burst
            pointMe = 2;
            txt = me.name + "Đã Đánh Burst " + boss.bey.name;
        }else {
            if (option.dameMe.dame >= boss.bey.hp) { // me đánh boss hết sta
                pointMe = 1;
                txt=boss.bey.name + "Đã Đánh Bại " + me.name;
            }
        }



        if (option.dameBoss.dame == pb){// boss đánh me burst
            pointBoss = 2;
            txt = boss.bey.name + "Đã Đánh Burst " + me.name;
        }else {
            if (option.dameBoss.dame >= me.hp) {// boss đánh me hết sta
                pointBoss = 1;
                txt = boss.bey.name + "Đã Đánh Bại " + me.name;
            }
        }

        newOption.pointMe = pointMe;
        newOption.pointBoss = pointBoss;



        return Util.checkStatusRes(HttpStatus.OK,  txt, newOption);

    }



    private boolean isBurstStopper(BeyBlade beyBlade){
        switch ((int) beyBlade.id){
            case 128:
            case 127:
            case 129:
            case 142:
            case 172:
            case 175:
            case 186:
            case 192:
            case 191:
                return true;
            default:
                return false;
        }
    }





    private boolean isLock(BeyBlade beyBlade){
        switch ((int) beyBlade.id){
            case 153:
            case 157:
                return true;
            default:
                return false;
        }

    }
private boolean isFafnir(BeyBlade beyBlade){
        switch ((int) beyBlade.id){
            case 79:
            case 122:
            case 139:
            case 167:
            case 185:
                return true;
            default:
                return false;
        }



}

    private boolean isRubber(BeyBlade beyBlade){
        if (isFafnir(beyBlade)){
            return true;
        }
        switch ((int) beyBlade.id){
            case 100:
            case 142:
            case 188:
            case 154:
            case 163:
            case 169:
            case 177:
            case 191:
            case 193:
            case 203:
            case 187:
            case 204:
            case 183:
            case 206:
            case 194:
            case 184:
            case 162:
            case 205:
            case 208:
                return true;
            default:
                return false;
        }



    }


    private boolean isNguocChieu(BeyBlade bey1,BeyBlade bey2){
        if (!bey1.spin.equals(bey2.spin) && !bey1.spin.equals("LR") && !bey2.spin.equals("LR")){
            return true;
        }
         return false;
    }



}


