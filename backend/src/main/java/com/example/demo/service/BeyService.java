package com.example.demo.service;

import com.example.demo.dto.BeyBoss;
import com.example.demo.dto.ItemShop;
import com.example.demo.dto.ResponseOpject;
import com.example.demo.entity.*;
import com.example.demo.repository.TopRepository;
import com.example.demo.repository.product.BeyRepository;
import com.example.demo.support.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * CartService.
 *
 * @author Nguyễn Hải
 * Created 09/03/2024
 */
@Service
public class BeyService {

    public static BeyService i;

    public static BeyService gI() {
        if (i == null) {
            i = new BeyService();
        }
        return i;
    }

    @Autowired
    public UserService userService;

    public List<ItemShop> item_shop = new ArrayList<>();
    public List<TOP> topList = new ArrayList<>();

    public BeyBoss boss = new BeyBoss();

    public int hour = 0;
    public boolean loadShop = true;
    public boolean loadBoss = true;
    public boolean loadTop = true;
    public void loadItemShop(){
        if (!loadShop){
            return;
        }

        BeyBlade index1 = getRandomBeyBoss();

        ItemShop itemShop = new ItemShop();
        itemShop.code = String.valueOf(UUID.randomUUID());
        itemShop.beyBlade = index1;
        itemShop.quantity = Util.nextInt(1,5);
        itemShop.price = index1.price *  Util.nextInt(1,5);
        itemShop.day = 0;
        itemShop.stt = 0;
        item_shop.add(itemShop);

        for (int i = 1; i <= 9; i++){
            List<BeyBlade> beyBlades = getBasicBey();
            int randomIndex1 = new Random().nextInt(beyBlades.size());
            int randomId1 = (int) beyBlades.get(randomIndex1).id;  // Fixed line
            BeyBlade indexend = getBeyByID(randomId1);
            ItemShop itemShop1 = new ItemShop();
            itemShop1.stt = i;
            itemShop1.code = String.valueOf(UUID.randomUUID());
            itemShop1.beyBlade = indexend;
            itemShop1.quantity = Util.nextInt(1,5);
            itemShop1.price = indexend.price *  Util.nextInt(1,5);
            itemShop1.day = 0;
            if (Util.isTrue(50,100)){
                itemShop1.day = Util.nextInt(1,3);
                itemShop1.price /= 5;
            }
            item_shop.add(itemShop1);
        }

        hour = LocalTime.now().getHour();
        loadShop = false;
        System.out.println("Load Item Shop Thành Công! " + hour);
    }

    public void addTOP(User user){
        TOP top = new TOP();
        top.user = user;
        top.win = 0;
        top.buff = 0;
        top.lost = 0;
        top.top = 101;
        top.createdAt = new Timestamp(System.currentTimeMillis());
        top.endBuff = top.createdAt;
        saveTop(top);
    }
    public void loadTop(){
       if (!loadTop){
           return;
       }
        topList.clear();
        for (int i = 1; i <= 100; i++){
            User userclone = new User();
            userclone.username = "user" + Util.generateRandomText(5) + i;
            userclone.userId = 0;
            userclone.accountId = 0;
            userclone.code ="";
            userclone.createdAt = new Timestamp(System.currentTimeMillis());
            userclone.avatar ="https://ae01.alicdn.com/kf/Hbc0b1bc2b16344a68e94ab86f49a1430O/GENUINE-Takara-Tomy-Burst-Dynamite-Random-Booster-Vol-26-Beyblade-B-186-B-194-random-1.jpg";
            userclone.diem = i * 2;
            userclone.active = true;
            userclone.rank= (byte) i;

            BeyBlade bey = getRandomBey();
            TOP top = new TOP();
            top.user = userclone;
            top.win = (short) Util.nextInt(1,10);
            top.buff = 5;
            top.selectBey = bey;
            top.lost = (short) Util.nextInt(1,10);
            top.top = i;
            top.createdAt = new Timestamp(System.currentTimeMillis());
            top.endBuff = top.createdAt;
            topList.add(top);
        }
        loadTop = false;
        System.out.println("Load TOP Thành Công! " + topList.size());
    }


    public List<TOP> getTopAll(List<TOP> topBOT,List<TOP> topDB){
        List<TOP> topAll = new ArrayList<>(topBOT);

//         Lặp qua danh sách từ cơ sở dữ liệu
        for (TOP topDBItem : topDB) {
            // Lặp qua danh sách kết hợp để thay thế tất cả các phần tử có cùng giá trị "top"
            for (int i = 0; i < topAll.size(); i++) {
                TOP topAllItem = topAll.get(i);
                if (topDBItem.top == topAllItem.top) {
                    // Thay thế phần tử từ cơ sở dữ liệu vào danh sách kết hợp
                    topAll.set(i, topDBItem);
                }
            }
        }

        // Sắp xếp danh sách kết hợp theo thuộc tính top
        topAll.sort(Comparator.comparing(TOP::getTop));
        return topAll;
    }

    public BeyBlade getRandomBey(){
        List<BeyBlade> list0 = getBoss();
        List<BeyBlade> list1 = getBasicBey();
        List<BeyBlade> list = new ArrayList<>();
        list.addAll(list0);
        list.addAll(list1);
        int randomIndex = new Random().nextInt(list.size());
        int randomId = (int) list.get(randomIndex).id;
        return getBeyByID(randomId);

    }

    public BeyBlade getRandomBeyBoss(){
        List<BeyBlade> list = getBoss();
        int randomIndex = new Random().nextInt(list.size());
        int randomId = (int) list.get(randomIndex).id;
        return getBeyByID(randomId);

    }

    public void loadBoss(){
        if (!loadBoss){
            return;
        }
        BeyBlade beyBlade = getRandomBeyBoss();
        boss.bey = beyBlade;
        boss.time = (byte) LocalTime.now().getHour();
        boss.buff = (byte) Util.nextInt(20,120);
        boss.dame = boss.bey.power;
        boss.hp = 10_000_000
                + boss.buff * boss.bey.hp * (8 - boss.bey.season)
        ;
        boss.playerKill = null;
        boss.die = false;
        loadBoss = false;
        System.out.println("Load Boss Thành Công! " + boss.bey.name);
    }

    @Autowired
    private TopRepository topRepository;

    public void saveTop(TOP top){
        topRepository.save(top);
    }

    @Autowired
    private BeyRepository beyRepository;

    public List<TypeBey> getAllTypes() {
        return beyRepository.getAllTypes();
    }

    public List<BeyBlade> getBeyByTypeID(byte id) {
        return beyRepository.getBeyByTypeID(id);
    }

    public BeyBlade getBeyByID(long id) {
        return beyRepository.getBeyById(id);
    }

    public List<BeyBlade> getBoss() {
        return beyRepository.getbosses();
    }
    public List<BeyBlade> getBasicBey() {
        List<BeyBlade> list = findAll();
        List<BeyBlade> basicBeys = new ArrayList<>();

        for (BeyBlade beyBlade : list) {
            if (!beyBlade.isBoss()) {
                basicBeys.add(beyBlade);
            }
        }
        return basicBeys;
    }

    public List<BeyBlade> findAll() {
        return beyRepository.getAll();
    }


    public ItemShop getItemShopByCode(String code) {
        for (ItemShop itemShop : item_shop){
            if (itemShop.code.equals(code)){
                return itemShop;
            }
        }
        return null;
    }


    public ResponseEntity<ResponseOpject> useItem(User user, MyPrize myPrize){
        Prize item = myPrize.prize;
        if (myPrize.soluong <= 0){
            return Util.checkStatus(HttpStatus.BAD_REQUEST,"Đã hết Lượt Mở",null);
        }
        switch (item.id){
            case 4://hộp dd
                Items itemAdd = addNewBey(user,getRandomBey());
                itemAdd.vinhvien = false;
                userService.saveItem(itemAdd);

                myPrize.soluong -= 1;
                userService.saveMyPrize(myPrize);

                String t = "Chúc mừng bạn vừa nhận được beyblade " + itemAdd.beyBlade.name + (itemAdd.vinhvien ? " Vĩnh Viễn " : " Hạn Sử Dụng");

                return Util.checkStatus(HttpStatus.OK, t ,itemAdd);
            case 5://hộp dd vip

                BeyBlade b = getRandomBey();
                if (Util.isTrue(10,100)){
                    b= getRandomBeyBoss();
                }
                Items itemVIP = addNewBey(user,b);
                itemVIP.vinhvien = true;
                userService.saveItem(itemVIP);

                myPrize.soluong -= 1;
                userService.saveMyPrize(myPrize);

                String v = "Chúc mừng bạn vừa nhận được beyblade " + itemVIP.beyBlade.name + (itemVIP.vinhvien ? " Vĩnh Viễn " : " Hạn Sử Dụng");

                return Util.checkStatus(HttpStatus.OK,v,itemVIP);
            default:
                if (item.id > 0 && item.id <= 3 || item.id > 5){
                    return Util.checkStatus(HttpStatus.BAD_REQUEST,"Sản Phẩm này dùng để đổi Beyblade thật,vui lòng liên hệ admin để đổi nhé!",null);
                }
                return Util.checkStatus(HttpStatus.NOT_FOUND,"Item này chưa có chức năng",null);
        }
    }

    public Items addNewBey(User user,BeyBlade beyBlade){

        Items items = new Items();
        items.beyBlade = beyBlade;
        items.user = user;
        items.selectedBey = false;
        items.create_time = new Timestamp(System.currentTimeMillis());
        long millisecondsInADay = TimeUnit.DAYS.toMillis(Util.nextInt(1,3));
        items.ngayhethan = new Timestamp(items.create_time.getTime() + millisecondsInADay);
        return items;
    }
    public TOP getTopByUser(User user) {
        return topRepository.getTopByUser(user);
    }
    public TOP getTopByUserName(String user) {
        return topRepository.getTopByUserName(user);
    }

    public User getUserByTop(int top) {
        return topRepository.getUserByTop(top);
    }
    public List<TOP> getTop() {
        return topRepository.getTop();
    }


}
