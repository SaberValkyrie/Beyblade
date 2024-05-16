package com.example.demo.controller;

import com.example.demo.dto.BeyBattle;
import com.example.demo.dto.BeyBoss;
import com.example.demo.dto.BeyDame;
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

    public ResponseEntity<ResponseOpject> attack(BeyBlade beyBlade,BeyBlade beyBlade1,long dame){
        int tlBurst = (5 - beyBlade.type.id) / 2;
        short tlne = beyBlade.tiLeNeDon;
        short tlCrit = beyBlade.crit;

        dame += (dame * Util.nextInt(-20,20) / 100) ;



        boolean isBurst = false;

        String text = beyBlade1.name + " đã gây được " + Util.numberToMoney(dame) + " dame";
        if (Util.isTrue(tlne,100)){
            dame = 0;
        }
        if (Util.isTrue(tlCrit,100)){
            dame *= Util.nextInt(2,4);
            text =  beyBlade1.name + " đã gây được " + Util.numberToMoney(dame) + " dame chí mạng với tỉ lệ là "+tlCrit +"%";
        }

        if (Util.isTrue(tlBurst,100)){
            dame =  beyBlade.hp;
            isBurst = true;
        }

        boolean hut = false;
        BeyDame beyDame = new BeyDame();
        beyDame.dame = dame;
        beyDame.hutdame = 0;
        long damehut = dame / 4;


        String hutt = beyBlade.name + " vừa hấp thụ " + Util.numberToMoney(damehut)  + " sức mạnh nhờ ngược chiều";
        if (isNguocChieu(beyBlade, beyBlade1) && Util.isTrue(50,100)){
            if (isRubber(beyBlade)){
                damehut = dame;
                hutt = beyBlade.name + " vừa hấp thụ " + Util.numberToMoney(damehut)  + " sức mạnh nhờ cao su";
            }
            beyDame.hutdame = damehut;
            hut = true;
        }




        return hut && dame > 0? Util.checkStatusRes(HttpStatus.BAD_REQUEST,  hutt, beyDame)
        : (isBurst ? Util.checkStatusRes(HttpStatus.OK,  beyBlade1.name + " đã chấn động gây ra " + Util.numberToMoney(dame) + " dame", beyDame)
                : (dame <= 0 ? Util.checkStatusRes(HttpStatus.BAD_REQUEST,  beyBlade.name + " đã né được đòn của " + beyBlade1.name + " bằng tỉ lệ "+ tlne + "%" , beyDame)
                : Util.checkStatusRes(HttpStatus.OK,  text, beyDame)));
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


