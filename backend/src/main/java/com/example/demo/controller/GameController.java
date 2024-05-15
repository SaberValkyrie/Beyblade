package com.example.demo.controller;

import com.example.demo.dto.BeyBattle;
import com.example.demo.dto.BeyBoss;
import com.example.demo.dto.ResponseOpject;
import com.example.demo.entity.*;
import com.example.demo.service.BeyService;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import com.example.demo.support.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Vui Lòng Chờ " + timeleft + " giây nữa" , null);
        }
        st = System.currentTimeMillis();
        if (accountToken.coint < dongia) {
            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Bạn không đủ BeyPoint,vui lòng nạp thêm để tiếp tục", null);
        }
        return Util.checkStatusRes(HttpStatus.OK, "Quay Tay Nào !!!!", userFromToken);
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
        return attack(battle);
    }
    public ResponseEntity<ResponseOpject> attack(BeyBattle battle){
        int tlBurst = (5 - battle.boss.bey.type.id) / 2;
        short tlne = battle.boss.bey.tiLeNeDon;
        short tlCrit = battle.boss.bey.crit;
        int dame = battle.me.power;
        String text = "Bạn đã gây được " + Util.numberToMoney(dame) + " dame";
        if (Util.isTrue(tlne,100)){
            dame = 0;
        }
        if (Util.isTrue(tlCrit,100)){
            dame *= Util.nextInt(2,4);
            text = "Bạn đã gây được " + Util.numberToMoney(dame) + " dame chí mạng với tỉ lệ là "+tlCrit +"%";
        }
        if (Util.isTrue(tlBurst,100)){
            dame = (int) battle.boss.hp;
        }
        return dame >= battle.boss.hp ? Util.checkStatusRes(HttpStatus.OK, "Đối Thủ Đã Bị Đánh Burst", dame)
                : (dame <= 0 ? Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Đối thủ đã né được đòn của bạn bằng tỉ lệ "+ tlne + "%" , dame)
                : Util.checkStatusRes(HttpStatus.OK,  text, dame));
    }

    @PostMapping("/attack")
    public ResponseEntity<ResponseOpject> BossAttack(
            @RequestBody BeyBattle battle
    ) {
        int tlBurst = (5 - battle.boss.bey.type.id) / 2;
        short tlne = battle.me.tiLeNeDon;
        short tlCrit = battle.me.crit;
        int dame = battle.boss.dame;
        String text = "Đối thủ đã gây được " + Util.numberToMoney(dame) + " dame";
        if (Util.isTrue(tlne,100)){
            dame = 0;
        }
        if (Util.isTrue(tlCrit,100)){
            dame *= Util.nextInt(2,4);
            text = "Đối thủ đã gây được " + Util.numberToMoney(dame) + " dame chí mạng với tỉ lệ là "+tlCrit +"%";
        }
        if (Util.isTrue(tlBurst,100)){
            dame = (int) battle.me.hp;
        }
        return dame >= battle.me.hp ? Util.checkStatusRes(HttpStatus.OK, "Bạn đã bị đánh Burst", dame)
                : (dame <= 0 ? Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Bạn đã né được đòn của đối thủ bằng tỉ lệ "+ tlne + "%" , dame)
                : Util.checkStatusRes(HttpStatus.OK,  text, dame));

    }

    public void addPrize(){
    }
}


