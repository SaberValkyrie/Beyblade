package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.repository.TopRepository;
import com.example.demo.repository.giftcode.GiftcodeRepo;
import com.example.demo.repository.giftcode.HistoryRepo;
import com.example.demo.repository.product.BeyRepository;
import com.example.demo.support.Util;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    @Autowired
    private GiftcodeRepo giftcodeRepository;


    @Autowired
    private HistoryRepo historyRepository;
    public List<ItemShop> item_shop = new ArrayList<>();
    public List<TOP> topList = new ArrayList<>();
    public List<Register> topDangKy = new ArrayList<>();

    public List<Notify> thongbao = new ArrayList<>();
    public BeyBoss boss = new BeyBoss();

    public String code = "";
    public int hour = 0;

    public void addThongBao(String text,byte type){
        Notify notify = new Notify(text,type);
        thongbao.add(notify);
    }

    public List<Notify> getThongBao(byte type) {
        return thongbao.stream()
                .filter(register -> register.getType() == type)
                .sorted(Comparator.comparing(Notify::getCreateAt).reversed())
                .collect(Collectors.toList());
    }

    public void loadCode(){
     for (int i = 1;i <= 9;i++){
         Register register = new Register();
         register.avatar = getAnhRand();
         register.hp = 1000000;
         register.buff = 5;

         List<TypeBey> list = getAllTypes();

        for (TypeBey typeBey : list){
             if (Util.isTrue(50,100)){
                 register.type = typeBey;
             }else{
                 register.type = typeBey;
             }
            register.bey = getRandomBeyBySS(typeBey.id);
        }
         register.createTime = new Timestamp(System.currentTimeMillis());
         register.username = Util.generateRandomText(6);
         register.is = true;
         register.hp = 1000000;
         register.buff = 5;
         topDangKy.add(register);
     }
    }




    public void loadItemShop(){
        item_shop.clear();

        BeyBlade index1 = getRandomBeyBoss();
        ItemShop itemShop = new ItemShop();
        itemShop.code = String.valueOf(UUID.randomUUID());
        itemShop.beyBlade = index1;
        itemShop.quantity = Util.nextInt(1,5);
        itemShop.price = index1.price *  Util.nextInt(1,10);
        itemShop.day = 0;
        itemShop.stt = 0;
        item_shop.add(itemShop);

        for (int i = 1; i <= 9; i++){
            BeyBlade indexend = getBeyByID(getRandomBey().id);
            ItemShop itemShop1 = new ItemShop();
            itemShop1.stt = i;
            itemShop1.code = String.valueOf(UUID.randomUUID());
            itemShop1.beyBlade = indexend;
            itemShop1.quantity = Util.nextInt(1,5);
            itemShop1.price = indexend.price *  Util.nextInt(1,5);
            itemShop1.day = 0;
            if (Util.isTrue(80,100)){
                itemShop1.day = Util.nextInt(1,3);
                itemShop1.price /= Util.nextInt(1,5);
            }
            item_shop.add(itemShop1);
        }

        hour = LocalTime.now().getHour();
        System.out.println("Load Item Shop Thành Công! " + hour);
    }
    public boolean isUsernameTaken(String username) {
        return topDangKy.stream().anyMatch(register -> register.username.equals(username));
    }
    public Register getByUserName(String username) {
        Optional<Register> optionalRegister = topDangKy.stream()
                .filter(register -> register.username.equals(username))
                .findFirst();
        return optionalRegister.orElse(null);
    }

    public Register getTop1() {
        List<Register> aliveRegisters = topDangKy.stream()
                .filter(register -> !register.isDie())
                .collect(Collectors.toList());
        if (aliveRegisters.size() == 1) {
            return aliveRegisters.get(0);
        } else {
            return null;
        }
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
    @PostConstruct
    public void init() {
        loadTop();
        loadItemShop();
        loadBoss();
//        loadCode();
    }



    public void loadTop(){
        topList.clear();
        for (int i = 1; i <= 100; i++){
            User userclone = new User();
            userclone.username = randomTen() + Util.nextInt(1,1000);
            userclone.userId = 0;
            userclone.accountId = 0;
            userclone.code ="";
            userclone.createdAt = new Timestamp(System.currentTimeMillis());
            userclone.avatar = "http://" + host + ":8080/files/user_avatar.png";
            userclone.diem = i * 2;
            userclone.active = true;
            userclone.rank= (byte) i;

            BeyBlade bey = getRandomBey();
            TOP top = new TOP();
            top.user = userclone;
            top.win = (short) Util.nextInt(40,70);
            top.buff = (byte) (Util.isTrue(20,100) ? 5 :(Util.isTrue(50,100) ? 3 : 2));
            if(Util.isTrue(50,100)){
                top.buff = 0;
            }
            top.selectBey = bey;
            top.lost = (short) Util.nextInt(40,70);
            top.top = i;
            top.createdAt = new Timestamp(System.currentTimeMillis());
            top.endBuff = top.createdAt;
            topList.add(top);
        }
        System.out.println("Load TOP Thành Công! " + topList.size());
    }

    public String randomTen(){
        return randomName() + randomLastName();
    }
    private String randomName() {
        String[] names = {
                "hai", "huy", "nguyen", "nam", "khoa", "chim", "meo", "namdz", "long", "tool",
                "anh", "tuan", "thao", "trang", "hoa", "thu", "linh", "duy", "minh", "phuong",
                "quynh", "lan", "linh", "hoang", "trung", "linh", "minh", "thu", "bao", "quoc",
                "quan", "hanh", "thanh", "huong", "hieu", "mai", "tam", "phuc", "hieu", "hong",
                "khanh", "thien", "thanh", "phat", "linh", "lan", "duong", "hiep", "hien", "hang",
                "thuy", "an", "nhan", "ngoc", "truc", "trung", "tu", "tien", "vinh", "thi",
                "khanh", "thao", "tuan", "hanh", "hien", "hieu", "tam", "truong", "thinh", "tuan",
                "thuy", "thanh", "thang", "nhan", "nghia", "nhu", "ngoc", "hoan", "hoang", "hong",
                "van", "viet", "vu", "ngoc", "duy", "duc", "dung", "linh", "ly", "sinh", "phong",
                "phu", "phuong", "thuc", "thanh", "truc", "trung", "tu", "tuan", "van", "vi"
        };

        Random random = new Random();
        int index = random.nextInt(names.length);
        return names[index];
    }
    private String randomLastName() {
        String[] lastNames = {
                "dzai", "tran", "le", "pham", "hoang", "huynh", "phung", "do", "ngo", "vu",
                "vo", "ly", "truong", "ngo", "dinh", "ngo", "lam", "vuong", "bui", "ma", "vu",
                "phan", "vu", "luu", "nguyen", "cao", "huynh", "game", "luong", "ho", "pho",
                "nguyen", "truong", "bui", "pho", "tran", "vu", "duong", "ma", "nguyen", "trinh",
                "vu", "vien", "tran", "dang", "pho", "le", "truong", "tran", "vu", "le",
                "le", "ho", "bui", "dept", "bucu", "ngo", "vo", "luu", "ho", "dinh",
                "hoang", "tran", "pham", "kkkk", "ma", "do", "nguyen", "dang", "bui", "ngo",
                "pham", "do", "dang", "bui", "le", "luu", "ngo", "vo", "ly", "nguyen", "le",
                "pham", "vuong", "tran", "bui", "le", "ho", "hoang", "truong", "pham", "ma"
        };

        Random random = new Random();
        int index = random.nextInt(lastNames.length);
        return lastNames[index];
    }

@Autowired
private ImageService imageService;
    public String getAnhRand() {
        String directoryPath = imageService.storageFolder.toString(); // Sử dụng đường dẫn từ storageFolder
        String randomImageName = Util.getRandomImageName(directoryPath);
        return "http://"+host +":8080/files/" + randomImageName;
    }


    private static String host = "beybladegame.online";
//    private static String host = "localhost";

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

    public BeyBlade getRandomBeyBySS(byte typeBey) {
        byte ss = getSS();
        List<BeyBlade> list = beyRepository.getRandomBeyBySS(ss,typeBey);
        int randomIndex = new Random().nextInt(list.size());
        int randomId = (int) list.get(randomIndex).id;
        return getBeyByID(randomId);
    }

    private byte getSS(){
        byte ss;
        switch (LocalTime.now().getHour()){
            case 0:
            case 1:
            case 2:
            case 3:
                ss = 2;
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                ss = 3;
                break;

            case 8:
            case 9:
            case 10:
            case 11:
                ss = 4;
                break;

            case 12:
            case 13:
            case 14:
            case 15:
                ss = 5;
                break;

            case 16:
            case 17:
            case 18:
            case 19:
                ss = 6;
                break;

            case 20:
            case 21:
            case 22:
            case 23:
                ss = 7;
                break;

            default :
                ss = 1;
                break;
        }
        return ss;
    }
    public BeyBlade getRandomBeyBoss(){
        List<BeyBlade> list = getBoss();
        int randomIndex = new Random().nextInt(list.size());
        int randomId = (int) list.get(randomIndex).id;
        return getBeyByID(randomId);

    }
    public BeyBlade getRandomBeyBasic(){
        List<BeyBlade> list = getBasicBey();
        int randomIndex = new Random().nextInt(list.size());
        int randomId = (int) list.get(randomIndex).id;
        return getBeyByID(randomId);

    }

    public void loadBoss(){

        BeyBlade beyBlade = getRandomBeyBoss();
        boss.bey = beyBlade;
        boss.time = (byte) LocalTime.now().getHour();
        boss.buff = (byte) Util.nextInt(90,120);
        boss.dame = boss.bey.power;
        boss.hp = 10_000_000
                + boss.buff * boss.bey.hp * (8 - boss.bey.season)
        ;
        boss.playerKill = null;
        boss.die = false;
        System.out.println("Load Boss Thành Công! " + boss.bey.name);
    }

    @Autowired
    private TopRepository topRepository;

    public void saveTop(TOP top,String txt){
        addThongBao(txt, (byte) 2);
        topRepository.save(top);
    }
    public void saveTop(TOP top){
        topRepository.save(top);
    }
    public void saveHistory(GiftcodeHistory top){
        historyRepository.save(top);
    }

    public void addPrize(User user){
        userService.addVoucherInBag(user,11);
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



    public void traoqua(User user){
        TOP top = getTopByUser(user);
        if (top != null && top.top <= 100){
            int cointAdd = 0;

            switch (top.top){
                case 1:
                    userService.addVoucherInBag(user,13);
                    cointAdd = 20000;
                    userService.addVoucherInBag(user,14);
                    userService.addVoucherInBag(user,15);
                    break;
                case 2:
                    userService.addVoucherInBag(user,14);
                    cointAdd = 10000;
                    break;
                case 3:
                    userService.addVoucherInBag(user,14);
                    cointAdd = 5000;
                    break;
                default:
                    if (top.top >= 4 && top.top <= 10){
                        cointAdd = 5000;
                    }
                    if (top.top >= 11 && top.top <= 50){
                        cointAdd = 3000;
                    }
                    if (top.top >= 51){
                        cointAdd = 1000;
                    }
            }

            Account accountToken = userService.getAccountByUser(user.username);
            accountToken.coint += cointAdd;
            userService.saveAccount(accountToken);

        }
    }


    public ResponseEntity<ResponseOpject> useItem(User user, MyPrize myPrize){
        Prize item = myPrize.prize;
        if (myPrize.soluong <= 0){
            return Util.checkStatus(HttpStatus.BAD_REQUEST,"Đã hết Lượt Mở",null);
        }
        switch (item.id){
            case 4://hộp dd
                Items itemAdd = addNewBey(user,getRandomBeyBasic());
//                Items itemOld = userService.getItemByUser(user,itemAdd.beyBlade);
                itemAdd.vinhvien = false;
                if (Util.isTrue(20,100)){
                    itemAdd.vinhvien = true;
                }
                String t = "Chúc mừng bạn vừa nhận được beyblade " + itemAdd.beyBlade.name + (itemAdd.vinhvien ? " [Vĩnh Viễn] " : " ---Hạn Sử Dụng");




                userService.saveItem(user,itemAdd);
                myPrize.soluong -= 1;
                userService.saveMyPrize(myPrize);
                return Util.checkStatus(HttpStatus.OK, t ,itemAdd);
            case 5://hộp dd vip

                BeyBlade b = getRandomBey();
                if (Util.isTrue(10,100)){
                    b= getRandomBeyBoss();
                    if (b.id == 207){
                        Items hyperion = getItemsByID(user,203);
                        Items helios = getItemsByID(user,204);
                        if (hyperion == null || helios == null){
                            b = getRandomBey();
                        }
                    }

                }
                Items itemVIP = addNewBey(user,b);
                itemVIP.vinhvien = true;
                userService.saveItem(user,itemVIP);

                myPrize.soluong -= 1;
                userService.saveMyPrize(myPrize);


                String v = "Chúc mừng bạn vừa nhận được beyblade " + itemVIP.beyBlade.name + (itemVIP.vinhvien ? "  [Vĩnh Viễn] " : " ---Hạn Sử Dụng");
                return Util.checkStatus(HttpStatus.OK,v,itemVIP);

            case 14://hộp top
            case 11://hộp boss
            case 12://hộp shopee
                BeyBlade bey = item.id == 11 ? getRandomBeyBasic() : getRandomBey();
                if (Util.isTrue(10,100)){
                    bey= getRandomBeyBoss();
                    if (bey.id == 207){
                        Items hyperion = getItemsByID(user,203);
                        Items helios = getItemsByID(user,204);
                        if (hyperion == null || helios == null){
                            bey = getRandomBey();
                        }
                    }
                }
                Items itemVIP1 = addNewBey(user,bey);
                itemVIP1.vinhvien = true;
                userService.saveItem(user,itemVIP1);

                myPrize.soluong -= 1;
                userService.saveMyPrize(myPrize);

                String vv = "Chúc mừng bạn vừa nhận được beyblade " + itemVIP1.beyBlade.name + (itemVIP1.vinhvien ? "  [Vĩnh Viễn] " : " ---Hạn Sử Dụng");

               if (Util.isTrue(50,100)){
                   Account accountToken = userService.getAccountByUser(user.username);
                   int cc =Util.nextInt(1,10) * 1000;
                   accountToken.coint += cc;
                   userService.saveAccount(accountToken);
                   vv += " và " + Util.numberToMoney(cc) + " Beypoint";
               }
                return Util.checkStatus(HttpStatus.OK,vv,itemVIP1);

            case 13:
                if (!this.boss.die){
                    return Util.checkStatus(HttpStatus.BAD_REQUEST,"Boss còn đang sống,không thể hồi sinh lúc này",null);
                }
                myPrize.soluong -= 1;
                userService.saveMyPrize(myPrize);
                loadBoss();
                boss.hp = 10_000_000;
                return Util.checkStatus(HttpStatus.OK,"Hồi Sinh Boss Mới Thành Công : " + boss.bey.name ,boss);

                case 15:
                myPrize.soluong -= 1;
                userService.saveMyPrize(myPrize);
                loadItemShop();
                return Util.checkStatus(HttpStatus.OK,"Reset Shop Thành Công ,Prize  : " + item_shop.get(0).beyBlade.name ,item_shop);

            default:
                if (item.type == 1){
                    return Util.checkStatus(HttpStatus.OK,"Sản Phẩm này dùng để đổi Beyblade thật,vui lòng liên hệ admin để đổi nhé!",null);
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


    public GIFTCODE findGiftCodeByCode(String code) {
        return giftcodeRepository.findCode(code);
    }

    public List<GiftcodeHistory> getAllHistory() {
        return historyRepository.findAll();
    }

    public GiftcodeHistory getHistory(String code) {
        return historyRepository.getHistoryByCodeAndUser(code);
    }

    public ResponseEntity<ResponseOpject> useItemCode(User userToken, GIFTCODE giftcode) {
        String txt ="Chúc mừng bạn nhận được :";
        Account accountToken = userService.getAccountByUser(userToken.username);

        switch (giftcode.type){
            case 1:        //type 1: code beta cho ng mới :20k BP
                accountToken.coint += 20000;
                txt += "20K BeyPoint";
                break;
            case 2:        //type 2:code bey random hsd-vv
                BeyBlade beyBlade = getRandomBey();
                Items item = createBey(userToken, (int) beyBlade.id);
                    item.vinhvien = true;

                userService.saveItem(userToken,item);
                txt += beyBlade.name + (item.vinhvien ? " Vĩnh Viễn" : " Hạn sử dụng");
                break;
            case 3: //type 3: code chào mừng
                BeyBlade beyBlade1 = getRandomBey();
                Items it = createBey(userToken, (int) beyBlade1.id);

                    it.vinhvien = true;

                userService.saveItem(userToken,it);
                txt += beyBlade1.name + (it.vinhvien ? " Vĩnh Viễn" : " Hạn sử dụng");
                int random = Util.nextInt(1,10);
                txt+= " và " + random + "K Beypoint";
                accountToken.coint += random * 1000;
                break;

            case 4: //code mua trên shopee
                txt+= " và " + 20 + "K Beypoint";
                accountToken.tienmat += 20 * 1000;
                userService.addVoucherInBag(userToken,12);
                txt += " kèm thêm Hộp Quà Shopee";
                break;
        }
        userService.saveAccount(accountToken);
        return Util.checkStatusRes(HttpStatus.OK, txt,  giftcode);
    }

    public Items createBey(User user,int id){
        Items items = new Items();
        items.beyBlade = getBeyByID(id);
        items.user = user;
        items.selectedBey = false;
        items.vinhvien = false;
        items.create_time = new Timestamp(System.currentTimeMillis());
        long millisecondsInADay = TimeUnit.DAYS.toMillis(Util.nextInt(1,3));
        items.ngayhethan = new Timestamp(items.create_time.getTime() + millisecondsInADay);
        return items;
    }

    public void saveCode(GIFTCODE giftcode) {
        giftcodeRepository.save(giftcode);
    }

//    public Items getItemsByID(User userToken, long i) {
//
//        return beyRepository.finItem(userToken,i).get(0);
//    }
    public Items getItemsByID(User userToken, long beyBladeId) {
        List<Items> items = beyRepository.finItem(userToken, beyBladeId);
        if (items.isEmpty()) {
            return null;
        }
        return items.get(0);
    }
    public Items getItemsByItemID(User userToken, int itemID) {
        return beyRepository.finItemBYID(userToken, itemID);
    }


}
