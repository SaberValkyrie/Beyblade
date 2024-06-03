package com.example.demo.service;

import com.example.demo.dto.BeyBoss;
import com.example.demo.dto.ItemShop;
import com.example.demo.dto.ResponseOpject;
import com.example.demo.entity.*;
import com.example.demo.repository.TopRepository;
import com.example.demo.repository.giftcode.GiftcodeRepo;
import com.example.demo.repository.giftcode.HistoryRepo;
import com.example.demo.repository.product.BeyRepository;
import com.example.demo.support.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
    @Autowired
    private GiftcodeRepo giftcodeRepository;


    @Autowired
    private HistoryRepo historyRepository;
    public List<ItemShop> item_shop = new ArrayList<>();
    public List<TOP> topList = new ArrayList<>();

    public BeyBoss boss = new BeyBoss();

    public int hour = 0;

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
            userclone.avatar = getAvatar();
            if (Util.isTrue(50,100)){
                userclone.avatar = getAnhRand();
            }
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
    public String getAvatar(){
        String[] chats = {
                "https://ae01.alicdn.com/kf/Hbc0b1bc2b16344a68e94ab86f49a1430O/GENUINE-Takara-Tomy-Burst-Dynamite-Random-Booster-Vol-26-Beyblade-B-186-B-194-random-1.jpg",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t1.6435-1/62518278_2777110628970165_1808986228746354688_n.jpg?stp=dst-jpg_p200x200&_nc_cat=101&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeEznmqxEB7ZlKcElwglVinxnodYTQ9OMdyeh1hND04x3IEOXpFxm4O0wFbTh4WLXwB8pQkm_vkgwuwiaJFZE70f&_nc_ohc=3Zq0wq9zj4UQ7kNvgF7Tepb&_nc_ht=scontent.fhan18-1.fna&oh=00_AYAPFGW1FD1W3kybH3RqPEGIL9IhCi9-cgCVrNAjHEUhjg&oe=667F35A5",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-1/272987273_3195999760631396_8436721140091447044_n.jpg?stp=c33.0.200.200a_dst-jpg_p200x200&_nc_cat=100&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeEsYRg_aGZcROSmkpyRbe0yeMSPoQon0iB4xI-hCifSIHWy7p5MOrZ-F3ycGCIfxFQ0EPhu99ylssYUFq5UyEXu&_nc_ohc=Ghi2nYdZfNoQ7kNvgHAVj4X&_nc_ht=scontent.fhan18-1.fna&oh=00_AYDGK631r1cYKuAPdj49eqW42wYz_LqZDdBqenAJlzvpBQ&oe=665D9F12",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t1.18169-1/15622013_191690564634509_6870705448815691484_n.jpg?_nc_cat=101&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeGzCbTCEUH50bvvPnWS1bpRisaoavNz02eKxqhq83PTZxXLv-rbV0VE1O4Ugf0ChVZfIKREVqqfft4giPZe2Wnu&_nc_ohc=4cLpS9mvHEoQ7kNvgEczWWb&_nc_ht=scontent.fhan18-1.fna&oh=00_AYBXLWi9mBrii-EXJlXJ4GlZvGnU96osdLmU8aPQ0070oA&oe=667F4C51",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-1/286076004_346273534280812_6678306348016976248_n.jpg?stp=dst-jpg_p200x200&_nc_cat=100&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeHO8xC1mGdh37AcQfv-NuioaMhGuFA1M-toyEa4UDUz6yUOnCzXKbALmxD5OcyhUbE5XpgLadVNkxVmVOmVtl44&_nc_ohc=T8LrNkXDdZUQ7kNvgHDpKuk&_nc_ht=scontent.fhan18-1.fna&oh=00_AYDTI3840LOID29Xi8Jqpu7_VUxpnhxQqvAPY9KCRujqhA&oe=665D7FEF",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/271129033_249820537259446_8925005849118255910_n.jpg?_nc_cat=109&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeEvH3uVC47fGd35jCliJk_VtbQQ-H-qlVu1tBD4f6qVW2E1JuFzcNW92ZE8PCCLGpp0QtChIEPd18l4OQThG4To&_nc_ohc=rhPDqM7wE_UQ7kNvgE9ZRhG&_nc_ht=scontent.fhan18-1.fna&oh=00_AYCe56D3dsem1mmRzXawgqu4mVpIjjOYzHDonLeCnlDdlQ&oe=665DA088",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/371895136_1380658139150361_5702240115975390527_n.jpg?_nc_cat=105&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeHRB6x3ZrWx8TyXRWLcMhj-dgfvvPqzOXB2B--8-rM5cMJGXerszRP20B2P6QIeDmPyfyCchlecDBC6QRQTsV1g&_nc_ohc=dqbnHY8ekhkQ7kNvgESCdDV&_nc_ht=scontent.fhan18-1.fna&oh=00_AYCY5gxCM9F0T6uKl6hehClc4UQmidoThLezB_ybQ5r96A&oe=665D8998",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/348277685_1818879475174258_5550683042154250402_n.jpg?_nc_cat=105&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeF5eCu2mCdAcUOI6l42mWRjqGuJa5gtyCeoa4lrmC3IJ8_cA9Sda2aCviN2YuOQ42uAuCGUSf6N6ArJY5F-VM_R&_nc_ohc=K6mArD6UD_sQ7kNvgEWb1tf&_nc_ht=scontent.fhan18-1.fna&oh=00_AYBiUdgnsUs5a5x7iLJ2Vg4_jGedGP0OlSe6OSRDmysJlw&oe=665DB0E0",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/445185643_1000454705413998_7741573721933650373_n.jpg?stp=dst-jpg_s600x600&_nc_cat=111&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeHbDyeRbiVvsrvZRpqHZjGZflRGyV7O_jh-VEbJXs7-OA5GyHwcGl4Jhe2_PWEzuPFOSLJPXHQK2ed6BBGuJ_Kw&_nc_ohc=aU6epTPmowoQ7kNvgE-iUyu&_nc_ht=scontent.fhan18-1.fna&oh=00_AYC0LbMepeHjFG-bsR4A2zGvIapMgSOUa8rI0Qj50PKNnA&oe=665D9AE8",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-1/436280302_934545911648016_9064665806563516929_n.jpg?stp=dst-jpg_s200x200&_nc_cat=111&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeHR9Ham2lPlgLPh82ipX6vlh8vpOvU6inuHy-k69TqKe-mWmA_tFRKVMwbi5eaMGli2NRXaOP6M3R-w2oKUqRTR&_nc_ohc=FE_GWpb4SBwQ7kNvgGWPiVb&_nc_ht=scontent.fhan18-1.fna&oh=00_AYDoem5ELK84xhUgjexKajqEaq_twkvhrg2C-JMucbmgOg&oe=665D9AE5",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/288860733_539459601156651_5972477597797429775_n.jpg?_nc_cat=104&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeHTfXc4yaxzWTJoZ9iLsH0q_2ZESZ1vjWT_ZkRJnW-NZGivZQ_mPZc_TaEF2VnNQ_lUEy-eR73aN0HWQl4TTTqs&_nc_ohc=NFBe07sceWAQ7kNvgEE3hni&_nc_ht=scontent.fhan18-1.fna&oh=00_AYCMkb-Hgk2Xh-xLzCYc8mswFkFTDTIpQrAi-4J0KSC-OQ&oe=665DAA81",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-1/312444981_842395300448807_4479667903774925150_n.jpg?stp=cp0_dst-jpg_p24x24&_nc_cat=102&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeGcDnFD9i2rtNC9wTNCOYrHK3HBdpW18QIrccF2lbXxApdGJzuZvOLaqNUNP-Mg7_1q2Pi_8ZDfpNmwmGsfHru6&_nc_ohc=OqRjbXsMdeoQ7kNvgH5ISxc&_nc_ht=scontent.fhan18-1.fna&oh=00_AYAAP2pDJAcP44YQLBNTXbQxTSq0bJX78LP8p9_Rkdr3rw&oe=665DA961",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/441462842_3866006973721586_8520899533383666907_n.jpg?stp=dst-jpg_p526x296&_nc_cat=109&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeHjGJfIDhftMcwGHSW8VfHgu9MpJLfAUoO70ykkt8BSgxpZMIa65r-l5qsmvVVQGSbMKLMsSBXV0speHJLV8eyv&_nc_ohc=qx7iz1LDFhIQ7kNvgFXdgLS&_nc_ht=scontent.fhan18-1.fna&oh=00_AYBvy0B1uA-VwciO3FbPpU4HR2RViJgKoHBq_mG8PtZ4LA&oe=665DB34C",
                "https://scontent.xx.fbcdn.net/v/t1.15752-9/430745492_1423214301619294_8886243951812194302_n.png?_nc_cat=111&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeH0uF6DfebnnRkSUEJ9Jz_pn9a4iAW0QzOf1riIBbRDMwV2CMmBQrToXLoyQgr_V8m5dRwOgP_52PHL29kVrxLu&_nc_ohc=4a19WZkHYncQ7kNvgEr3Q4V&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=03_Q7cD1QGhJFxfceOBEpYyB3KOkuu2Pfcf5yvJHZ1wg9WKS7oV4Q&oe=667F2AC3",
                "https://scontent.xx.fbcdn.net/v/t1.15752-9/432644504_929545858487118_4015629610298036670_n.jpg?_nc_cat=107&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeHBl1-LX6emIJaBY6un4DtxsT7-N6ZYbQ2xPv43plhtDaor1UVWS8n_4jG0axEBViUhBr_YjH1M5-IRnL4PozAE&_nc_ohc=ogzoo_5F_Z8Q7kNvgFaRD5O&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=03_Q7cD1QH1VjdsGybX-k8Akk_mh6BnXTJkLMXS0oPBOy-axhQ-6Q&oe=667F438B",
                "https://scontent.xx.fbcdn.net/v/t1.15752-9/382700568_1667455143665026_3905014653348640954_n.jpg?_nc_cat=100&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeG_3KWTlSEcsHubkx7N2eFlRrc8yj9dMKJGtzzKP10wolcn8ExEWOwUnWBAqgHrJOQMoiEnJ2QXeRAH8qJ9gy5b&_nc_ohc=VyotBgIbgCkQ7kNvgGiGhGL&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=03_Q7cD1QH-WHje9USp5FT5FI_QmwxEMFZ0KDnofvMBR5p_e5I7_Q&oe=667F49F6",
                "https://scontent.xx.fbcdn.net/v/t1.15752-9/382584338_711951064232855_3071277401076121213_n.jpg?_nc_cat=106&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeG31dVU3_Ubbx5jhbrHdTuAyQnbJ_KRxrDJCdsn8pHGsLeooBVAshf-Ie1aUDE-v3XxgQeIdO2LMXJoXwG62RlA&_nc_ohc=EXl7To90r_QQ7kNvgHv8V88&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=03_Q7cD1QFhCaD2jOD0pyxuPdMnh-WPCEjNgY2bP7qDX8qtX0WFZA&oe=667F2859",
"https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/422615123_4625892767636158_5075218024376430265_n.jpg?stp=cp6_dst-jpg&_nc_cat=101&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeHtpVRxUfAmKYtvXhcFvNk7ghFxmPfYY6eCEXGY99hjp9l0UclcljY8mQ4LGd-VO6vKfDbIEsvd__mHAEhcXupt&_nc_ohc=7P0YwoE_ZxQQ7kNvgGhXiUF&_nc_ht=scontent.fhan18-1.fna&oh=00_AYD5isRSFPdaLYjBEDTSL8SG0r5SgaY7I3nqlKva3jJokw&oe=665D8AEF",
                "https://vtv1.mediacdn.vn/k:2015/khoanh-khac-anh-ngau-nhien-1-25315-1427271342486/bat-ngo-truoc-nhung-khoanh-khac-anh-ngau-nhien.jpg",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/415080378_2629199853901249_6259701693040383704_n.jpg?_nc_cat=104&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeHvDgbstLtvAZgZ_rMq58mM3D5gi4pA_5jcPmCLikD_mI7c1S_VxE0Qm7iYJSCWp9X7xKgl1MALOiPT_M4nM2Mf&_nc_ohc=4j9ktQ0o0PAQ7kNvgGj6zn0&_nc_ht=scontent.fhan18-1.fna&oh=00_AYCeZT7aW-8YcGydMBQFamSzpYTY-AEjWV28B97za4iFdw&oe=665DB491",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-1/432778944_384164644536368_5485744634482929737_n.jpg?stp=dst-jpg_p200x200&_nc_cat=102&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeGEPvnoIzEeA6b5GZfHNXxOr6z0QyPrl-OvrPRDI-uX45PsNEKh8QwbxlCrIPtJC_4QlGuQxQRQsk5Muv0bfxT2&_nc_ohc=9EC0jKlOQd8Q7kNvgHUZUuA&_nc_ht=scontent.fhan18-1.fna&oh=00_AYAb274FbT0aKYLQ81GltAJ0UWGPUOQzxxy7mIlOczLRRQ&oe=665DA048",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/442439866_122175894002018099_8300093027989795396_n.jpg?stp=dst-jpg_p168x128&_nc_cat=103&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeFDetKzPjXNdJYn7o9d213Shr9QXU_Ro7uGv1BdT9Gju0CJb0BUfrlYNPSsRZUSt2st3JaZUcjE_oCGQ8jh5fY-&_nc_ohc=FIqe6iSU4x0Q7kNvgEAULtg&_nc_ht=scontent.fhan18-1.fna&oh=00_AYB_VBKCNDI7WHPm3-6zvkfDvGsjeosBzNOD3R5zDeTaAg&oe=665D85AD",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/445374615_384791474559378_5661950630067704506_n.jpg?_nc_cat=104&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeHnR-VgNXCRByDW39lUgaNbtsOQuO5Q_vi2w5C47lD--DxgTZVZ5AmOiM82YiNAcgXVQ0a735uC_i-W_tGVGfFD&_nc_ohc=hj-juD-OEfIQ7kNvgFjmlrG&_nc_ht=scontent.fhan18-1.fna&oh=00_AYB1uAyRMHK07lBOm4OuDZ-_IJazZVai_XByOolqgS8QcA&oe=665DB1F3",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-1/427760394_926569162450250_8016807215925932325_n.jpg?stp=dst-jpg_p200x200&_nc_cat=103&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeHWHSLZhCH7KGNhy_S6fhfmVgwEaAeaH19WDARoB5ofX3HGFhVpqIPQt_PSIRxSP5IXAxza0rLtOC9qQmhdCwsN&_nc_ohc=zvLMioHXziYQ7kNvgFplJXh&_nc_ht=scontent.fhan18-1.fna&oh=00_AYDxZEVgthTCLOpojPmt1CAF3bAw_5mXcE0kq3CDnH95GA&oe=665D8EF1",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-1/293222805_1683497245360973_2696752739216113496_n.jpg?stp=dst-jpg_p200x200&_nc_cat=103&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeFehXxk4fs1y2VKXNiVSGBKZMxviCngfQdkzG-IKeB9B0Nx5UI--KvaPbb7bOfHMw9yCA5yfqgKQk7K0ISLK0wu&_nc_ohc=Ph3SM1EYJS4Q7kNvgFC9zvq&_nc_ht=scontent.fhan18-1.fna&oh=00_AYBLNr-EiQJSBAYFgMkVMXyrT_e_1yAf6Hd4kJu3ra621g&oe=665DACAB",
"https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-1/433444279_122124538934227705_2246280142565508355_n.jpg?stp=c78.0.200.200a_dst-jpg_p200x200&_nc_cat=102&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeHsj0Kievdzg4djQDwagHViWSgO9fx9TJ9ZKA71_H1Mn9tol62QU8H1lLLBrdopmd8nyngmUikY-VlHjElu46Ne&_nc_ohc=jGxugDXMmG0Q7kNvgGpWbma&_nc_ht=scontent.fhan18-1.fna&oh=00_AYAnVn6UddeeD5DQ7b3cEPAva8Jy2uHotI2Fkrna6YFdgg&oe=665D860D",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-1/308458318_5927848337225525_3365216703784960829_n.jpg?stp=dst-jpg_p200x200&_nc_cat=102&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeGSNwT6ZInfJYA9neZ0wu7I30d0ryBMU0zfR3SvIExTTJjWvIAIfEXy1c4p9mqv2W-cBA8zsEcdxsAj1Gkak3TW&_nc_ohc=9Onx6PxFp4YQ7kNvgHMrMAV&_nc_ht=scontent.fhan18-1.fna&oh=00_AYCB7EFFow1fJk1uCicwmn7S93qNd9qp5qvIUszgA-srcA&oe=665D939A",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-1/362948644_256773253796098_8825225825116369910_n.jpg?stp=dst-jpg_s200x200&_nc_cat=105&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeFtM03ZezQx0rS8gVs18Rmth3VOg4VGxReHdU6DhUbFF6d_eVp1unSrx3CUJUTXTz648HgodQ6eXLononWm6kx7&_nc_ohc=Pf9WRVKQ6CUQ7kNvgGSjfEt&_nc_ht=scontent.fhan18-1.fna&oh=00_AYAF6v3N6I-XzAno45BVioy8p6qE7fhSmy-5PcF3n6Snbg&oe=665D8846",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/337555010_568401972021513_759337058612551867_n.jpg?stp=dst-jpg_p526x296&_nc_cat=109&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeGj09cWcL0G49q9S5gOrTw5I81s8tvP-EojzWzy28_4Si0Vz4MaugIX765cTpZUrUJuX99P75Sa41-jzC7A9Liy&_nc_ohc=S53D93tF2N8Q7kNvgGFu_BQ&_nc_ht=scontent.fhan18-1.fna&oh=00_AYDUBTqTor9LeqhrYphVIUl0oryRAWJDvnSP15lqCEtQ6Q&oe=665DB6B7",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-1/274475531_7358395200867970_8285281564708289630_n.jpg?stp=dst-jpg_p200x200&_nc_cat=108&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeHOzok6-j7VjVWNZBNohLiGVGqXRKjLSTtUapdEqMtJOz2xgaYEDBBdVh2Qpqy9uGoQ29hdjk5zvlyHx-YD1YdG&_nc_ohc=G0IvB2UikOgQ7kNvgEg9cQv&_nc_ht=scontent.fhan18-1.fna&oh=00_AYBaiGDYnBiTFnTQCNFQ_cmUHEJ1Ow6b5pU-A_6IT0TzFA&oe=665DA89A",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-1/442485912_959240115688480_3053684127394420543_n.jpg?stp=dst-jpg_p200x200&_nc_cat=106&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeE4thi3JlPZq4r1eFWvvLwvtf7kdlVojt-1_uR2VWiO375ScZBqOZm_O2FD4CNJsV_ZtzQoZ4QEzdW4XfQBZ_UL&_nc_ohc=k1C8VDdY6jMQ7kNvgGm35UY&_nc_ht=scontent.fhan18-1.fna&oh=00_AYDBhu7PqGRt9QnQUZS7zAdwZDfguX11_1Kx8Nyuh5EtCw&oe=665D8351",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/241199262_376913570587807_4230248302992245937_n.jpg?_nc_cat=108&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeGsiJSbY1gVAqAMX2zWbs_50tM7FHguuijS0zsUeC66KOQ3M0oA8Ldl8BOH90e1NXXTh6QwIGmNYfcKdB2VrUzJ&_nc_ohc=qstJwXNBGx4Q7kNvgFRWEkK&_nc_ht=scontent.fhan18-1.fna&oh=00_AYBsm495BsJSK0_zWAYKGSf-jlXHpmOZHwZZfhinxxYQRA&oe=665D9E0C",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/274259934_471735901105573_8687480556702567156_n.jpg?_nc_cat=107&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeEL26oRh4py7W0TJ-5JRdVlDtBH_6Lr8YgO0Ef_ouvxiCWd0MfO2BWROws1vTnqb5Fh_inLHmWZQVXe8GG8UjQM&_nc_ohc=KW2q5RkWNNMQ7kNvgElnsY5&_nc_ht=scontent.fhan18-1.fna&oh=00_AYCPBzQ5704cFIgHezEDVkS9mXSdZrGs_98szgTb0KXuNw&oe=665DA4DB",
"https://scontent.fhan18-1.fna.fbcdn.net/v/t1.6435-9/129648591_203912901221209_8886777173130198782_n.jpg?_nc_cat=102&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeHJpOymuNQ64FkW_SI3da5nqZXRC6Brk5OpldELoGuTkx2nYhopUnHc5W1A3Fz0j0BVBQCFmowOPnAmK3GVN4hL&_nc_ohc=ZzxUEICMwI4Q7kNvgGi-V-X&_nc_ht=scontent.fhan18-1.fna&oh=00_AYC1bdSo2xV1KuLNlL29iLugHERhoYNO1WTiTV7l-UMFQQ&oe=667F5524",
                "https://scontent.fhan18-1.fna.fbcdn.net/v/t1.6435-1/40084262_2031701316893293_5713244245364572160_n.jpg?stp=dst-jpg_p200x200&_nc_cat=103&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeH654f_QXjcA_P3j3nmBMTt6wgCzkKdmt3rCALOQp2a3fnIULfSioq-3c4-TXb2dCjY8LOOV3xxflOgk0M4fQq9&_nc_ohc=vWEIE1QcXukQ7kNvgGN8QmW&_nc_ht=scontent.fhan18-1.fna&oh=00_AYBum_aawGtURp8o-Z0tHGiindBHttutwXQMrlKlrlpL1w&oe=667F3EE8",
"https://scontent.fhan18-1.fna.fbcdn.net/v/t1.6435-1/125370302_2797536653898345_4374923300068537541_n.jpg?stp=c143.0.240.240a_dst-jpg_p240x240&_nc_cat=100&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeFU92I59FPYnl26mThfyU1arrFuW1WiiumusW5bVaKK6YCsjf-0LoXzRq0KK2YGXaluCAqYms_fFvpCKlqGLAuY&_nc_ohc=bMVJra4JZ_EQ7kNvgEyZtnv&_nc_ht=scontent.fhan18-1.fna&oh=00_AYChcr1YlvyXRxG0WmVCySpjrg1mnvsvugI2z7SiYtQ8oQ&oe=667F2A84",
      "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-1/436185251_722243480078031_6703529749888399758_n.jpg?stp=dst-jpg_p200x200&_nc_cat=107&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeH59fyfBBYQmnVkN3w6atEkK1zcUbTAgaMrXNxRtMCBo0P_rUUaEYp54TC-jPA2AigD0ovUMfvReIt2ebDDntqq&_nc_ohc=5-K_q6CBJGgQ7kNvgFfLvls&_nc_ht=scontent.fhan18-1.fna&oh=00_AYAUO9US3mJhX059MEECb1kRjl6WU4LT_XV9nvqipb8u_A&oe=665E621E",
        "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-1/428605274_1061398748358475_9091291635206744174_n.jpg?stp=c0.13.200.200a_dst-jpg_p200x200&_nc_cat=104&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeGdNlatQzfBXPV8ExRiAdmH9ae57wDEgsz1p7nvAMSCzFed7xRVaG36llxNbYl7uVw8Cj_BnjGQ8g8uBUCFZyRm&_nc_ohc=zjmHD-11oVUQ7kNvgEJSTGB&_nc_ht=scontent.fhan18-1.fna&oh=00_AYD0XRgH-Z4_7LpmANwWgcw01F27_CwrMqdFF8w7l-mLMQ&oe=665E7E7C",
        "https://scontent.fhan18-1.fna.fbcdn.net/v/t1.6435-9/144389437_350769606088063_6895404197860571469_n.jpg?_nc_cat=105&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeGU53wm1tQ-rJYJIKmbP0WwARM-mosfTxUBEz6aix9PFeoWjjRtGEBaRRFCws35sfS678b8kfxcSlItln-8mkR0&_nc_ohc=90jw8L0SIHMQ7kNvgG6sNIt&_nc_ht=scontent.fhan18-1.fna&oh=00_AYBB3F_TeyJ7V-z8EP_ZuANIfTpE_9iRyyIBSMSYQ3KtTg&oe=667FF87D",
        "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/437763786_1661294311372190_2198671048539560857_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeHrDIm5CiulxnMykTfgSip7ZhZpMK_xVD5mFmkwr_FUPlWchrSq3w4GEfOC2TroBu9NdHhFBlc2U5yY1cyiDTgu&_nc_ohc=rkZMzopd4s4Q7kNvgFRUlhg&_nc_ht=scontent.fhan18-1.fna&oh=00_AYD74r6EStqM9i8Sdn35Mz6FOZvxH8W5s7__thXl4qAafw&oe=665E7192",
        "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-1/440933957_966313555105859_6808259265606447380_n.jpg?stp=dst-jpg_p100x100&_nc_cat=105&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeGxnJcE_yZoBIe9X9cXMcfnEzPsSLL8aWQTM-xIsvxpZIXLOsqAbGQRavVZn4zQDJC-X0ClyxpmDpM9TiCkpVl6&_nc_ohc=nvSKX1BQz50Q7kNvgHRCXmn&_nc_ht=scontent.fhan18-1.fna&oh=00_AYBBGGbtdS7k0u9OF-GTfiag0e9tPoRb1vukhSW9FrtbZA&oe=665E5FD6",
        "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-1/440461287_1459129764989258_8853955848686389050_n.jpg?stp=dst-jpg_p200x200&_nc_cat=109&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeEfZrsu5YEPeDNCNmuTkv5Z2p2_IGSXOG_anb8gZJc4b6iITXO_O_SZX9dfMC9a2dLmsu8FMlKxlBopFcFVytKE&_nc_ohc=2iX0hVqpMw8Q7kNvgHvckNa&_nc_ht=scontent.fhan18-1.fna&oh=00_AYASTwvM7zrg7B9UbS28VXnfivTaHgNx6bkp2BCR-vX3iQ&oe=665E5179",
        "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-1/415883058_1524258828356474_3595457889432293818_n.jpg?stp=c2.1.199.198a_dst-jpg_p200x200&_nc_cat=107&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeHEOiSioe9j59u69-NK1uPqR9JwBNIsrjFH0nAE0iyuMU2XCYgcuvCWJKwwhVusDrI3aXdFp3iHytD3yRLDsmQ9&_nc_ohc=hT6-RaTDXY8Q7kNvgF7q9C7&_nc_ht=scontent.fhan18-1.fna&oh=00_AYD60cqv_eluY98g4nmGuKXjsLNSfQnDGiJK0gSWw8dS3w&oe=665E817B",
        "https://scontent.fhan18-1.fna.fbcdn.net/v/t1.18169-1/27657478_140237140119984_5185399927281521002_n.jpg?stp=dst-jpg_p200x200&_nc_cat=103&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeFbmhdWTEqqRLrJgD3vKSTyP--d6C7chFU_753oLtyEVXgd7E6HUOAyRLyhzIQW-dDUDfXogapgE-rj28jnvTIy&_nc_ohc=j-tBJxIHmgsQ7kNvgEJMgwV&_nc_ht=scontent.fhan18-1.fna&oh=00_AYBZSuP_EEpGW5Uds5L5KPY17Ocxpvs4mEYqpD8osHn5wQ&oe=667FF409",
        "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-1/445034420_1828890270926522_136118773441533619_n.jpg?stp=dst-jpg_p200x200&_nc_cat=106&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeE93plhZEUh2Gfy3E1hTURBRbpOiuziFCdFuk6K7OIUJ8ZzYjCruba030Ra5C8AG5ccCciEI8pLeAr_dg1OZ498&_nc_ohc=_9P9lFuyW5AQ7kNvgGBBvRi&_nc_ht=scontent.fhan18-1.fna&oh=00_AYBkx0aKRWxVw7nbIeSFF0Qy74JbHXDSA4eud7B67bCr-Q&oe=665E82F1",
        "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-1/332567096_882978019471732_8825746545881267309_n.jpg?stp=dst-jpg_p200x200&_nc_cat=111&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeFot3a7-hMevbWAyWL1FxXA_0XV7Eh0biD_RdXsSHRuIOYEjEvpR9JfYkOD1dgSn9wtWtO0HJNe8tul8fwQnMaY&_nc_ohc=u-4pCehiOykQ7kNvgHVGaTa&_nc_ht=scontent.fhan18-1.fna&oh=00_AYB9yDtzwjJkRnS8Oz0w3xcywgfLIBE-3hY497ACLfVQQA&oe=665E77D3",
        "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-1/426490600_422005150251236_4944607599550719846_n.jpg?stp=c0.0.200.200a_dst-jpg_p200x200&_nc_cat=105&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeENXZx3pN4as0E20lq5-knNrRCx0RdyGMutELHRF3IYyzsRfEVkcUz3XnsfxyifkDEbmEy5ynF06XEX2QLr1nf9&_nc_ohc=0fQeWUgG82cQ7kNvgHTHaGb&_nc_ht=scontent.fhan18-1.fna&oh=00_AYDa9ioQW-JhPSCLWbuXPBLyfYy-xrbZ7VLlRzxdhC7dGw&oe=665E57E3",
        "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/429568660_434963615622056_2385596529137594954_n.jpg?_nc_cat=105&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeFnGSylFdUeFjgoqnZtEEDx3Lb0UcXorM_ctvRRxeiszwSH4GL8HPOwk-382eUrH1dsNYW4g0CjikICuvgPDa2E&_nc_ohc=PxXPiieD3iMQ7kNvgE_qtSO&_nc_ht=scontent.fhan18-1.fna&oh=00_AYCd5mpQYm0vE0IwEEzGY7MHaP5uW_494lg2A-Vf_E6Cyg&oe=665E5919",
        "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-1/340848093_1385617858905641_7738230222358588710_n.jpg?stp=dst-jpg_s200x200&_nc_cat=108&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeG_Fxxke0RErb3g1877vn8zFj4Gw2R3AQEWPgbDZHcBAdGQHx0WBTjZPML-TMfjLQEpgGjZ5SNB9Ym2n-rW0KX2&_nc_ohc=Ra8rnv363jAQ7kNvgG7nmYO&_nc_ht=scontent.fhan18-1.fna&oh=00_AYBQ70cKHnEGvMoQM2ec_Yjcx74UEFJIclOqAm1Hgoyk-g&oe=665E75C3",

                "https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/441077331_410701771930884_6741066087439713870_n.jpg?_nc_cat=106&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeELXgSRcTkuarYMODG5ooXM-YkGEogdVC35iQYSiB1ULZ2DKBdxSjh6ltzqIZIcaWScmd72rSlX4ZP6LNPAkFXF&_nc_ohc=S7Y4C38SaWkQ7kNvgEs_pAX&_nc_ht=scontent.fhan18-1.fna&oh=00_AYDwZSRnlIZ5SRmeWhO8Y8kmqlHkPv8HbYn5zwsQBPjMbA&oe=665EF45B",

        };

        Random random = new Random();
        int index = random.nextInt(chats.length);
        return chats[index];
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

//                if (itemOld != null){
//                    long millisecondsInADay = TimeUnit.DAYS.toMillis(Util.nextInt(1,3));
//                    itemOld.ngayhethan  = new Timestamp(itemOld.ngayhethan.getTime() + millisecondsInADay);
//                    myPrize.soluong -= 1;
//                    userService.saveItem(itemOld);
//                    userService.saveMyPrize(myPrize);
//                    return Util.checkStatus(HttpStatus.OK, t ,itemAdd);
//                }
                userService.saveItem(itemAdd);
                myPrize.soluong -= 1;
                userService.saveMyPrize(myPrize);
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

                String v = "Chúc mừng bạn vừa nhận được beyblade " + itemVIP.beyBlade.name + (itemVIP.vinhvien ? "  [Vĩnh Viễn] " : " ---Hạn Sử Dụng");
                return Util.checkStatus(HttpStatus.OK,v,itemVIP);

            case 14://hộp top
            case 11://hộp boss
            case 12://hộp shopee
                BeyBlade bey = item.id == 11 ? getRandomBeyBasic() : getRandomBey();
                if (Util.isTrue(10,100)){
                    bey= getRandomBeyBoss();
                }
                Items itemVIP1 = addNewBey(user,bey);
                itemVIP1.vinhvien = true;
                userService.saveItem(itemVIP1);

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
                if (Util.isTrue(10,100)){
                    item.vinhvien = true;
                }
                userService.saveItem(item);
                txt += beyBlade.name + (item.vinhvien ? " Vĩnh Viễn" : " Hạn sử dụng");
                break;
            case 3: //type 3: code chào mừng
                BeyBlade beyBlade1 = getRandomBey();
                Items it = createBey(userToken, (int) beyBlade1.id);
                if (Util.isTrue(10,100)){
                    it.vinhvien = true;
                }
                userService.saveItem(it);
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
}
