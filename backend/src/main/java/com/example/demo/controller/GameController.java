package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.service.BeyService;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import com.example.demo.support.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/game")
public class GameController {


    @Autowired
    private UserService userService;



    @Autowired
    private TokenService tokenService;

    @Autowired
    private BeyService service;
    private long st;



    @GetMapping("/getAllTypes")
    public ResponseEntity<ResponseOpject> getTypes() {
        List<TypeBey> list = service.getAllTypes();
        return Util.checkStatusRes(HttpStatus.OK, "Đã tìm được " + list.size() + " hệ",  list);
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


    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseOpject> getBey(@PathVariable long id) {
        BeyBlade list = service.getBeyByID(id);
        if (list == null){
            return Util.checkStatusRes(HttpStatus.NOT_FOUND, "Không tồn tại",  null);
        }
        return  Util.checkStatusRes(HttpStatus.OK, "Đã tìm được " + list.name ,  list);
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





    @GetMapping("/spin/{token}/{bey}")
    public ResponseEntity<ResponseOpject> getSpin(
            @PathVariable String token,
            @PathVariable long bey
    ) {
        User userFromToken = tokenService.getUserFromToken(token);
        if (userFromToken == null) {
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Token sai", null);
        }
        BeyBlade beyBlade = service.getBeyByID(bey);
        Account accountToken = userService.getAccountByUser(userFromToken.username);
        short time = 500;
        int dongia = beyBlade.price;
        long timeleft = (time - (System.currentTimeMillis() - st))/1000;
        if (!Util.canDoWithTime(st,time)){
            return Util.checkStatusRes(HttpStatus.NOT_IMPLEMENTED, "Vui Lòng Chờ " + timeleft + " giây nữa" , null);
        }
        st = System.currentTimeMillis();
        if (accountToken.coint < dongia) {
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Bạn không đủ BeyPoint,vui lòng nạp thêm để tiếp tục", null);
        }
        return Util.checkStatusRes(HttpStatus.OK, "Quay Tay Nào !!!!", userFromToken);
    }


    @PostMapping("/attack")
    public ResponseEntity<ResponseOpject> BossAttack(
            @RequestBody BeyBattle battle
    ) {
        return attack(battle.me,battle.boss.bey,battle.boss.dame);
    }
    @PostMapping("/spin/{token}")
    public ResponseEntity<ResponseOpject> spinNow(
            @PathVariable String token,
            @RequestBody BeyBattle battle
    ) {
        User userFromToken = tokenService.getUserFromToken(token);
        if (userFromToken == null) {
            return Util.checkStatusRes(HttpStatus.UNAUTHORIZED, "Token sai", null);
        }
        Account accountToken = userService.getAccountByUser(userFromToken.username);
        int dongia = battle.me.price;
        accountToken.coint -= dongia;
        userService.saveAccount(accountToken);

        return attack(battle.boss.bey,battle.me,battle.me.power);
    }
public static int pb = 2_100_000_000;
    public ResponseEntity<ResponseOpject> attack(BeyBlade beyOrther,BeyBlade beyChinh,long dame){
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

            dame *= Util.nextInt(2,4);
            text =  beyChinh.name + " đã gây được " + Util.numberToMoney(dame) + " dame chí mạng với tỉ lệ là "+tileCrit +"%";
        }

        long damehut = dame / 4;
        String hutt = beyOrther.name + " vừa hấp thụ " + Util.numberToMoney(damehut)  + " sức mạnh nhờ ngược chiều";
        if (isNguocChieu(beyOrther, beyChinh) && Util.isTrue(33,100)){
            if (isRubber(beyOrther)){
                damehut *= 2;
                hutt = beyOrther.name + " vừa hấp thụ " + Util.numberToMoney(damehut)  + " sức mạnh nhờ cao su";
            }
            beyDame.hutdame = damehut;
            hut = true;
        }


        if (Util.isTrue(20,100)){
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
            case 205:
            case 206:
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


    public void addPrize(){
    }
}


