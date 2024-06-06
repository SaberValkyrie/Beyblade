package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.ItemsRepository;
import com.example.demo.repository.product.BeyRepository;
import com.example.demo.repository.product.MyVoucherRepository;
import com.example.demo.repository.product.VoucherRepository;
import com.example.demo.repository.user.*;
import com.example.demo.support.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MesssageRepository messsageRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MyVoucherRepository myPrizeRepository;
    @Autowired
    private ItemsRepository itemsRepository;
    @Autowired
    private VoucherRepository voucherRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public String mahoa(String password) {
        return passwordEncoder.encode(password);
    }
    public Account authenticate(Account loginRequest) {
        Account user = userRepository.findUsername(loginRequest.username);
        if (user != null && passwordEncoder.matches(loginRequest.password, user.password)) {
            return user;
        }
        return null;
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }







    public void addVoucherInBag(User user, int id){
        Prize prize = voucherRepository.getPrizeByID(id);
        MyPrize myPrize = voucherRepository.getMYPrizeByID(user,id);
        if (myPrize == null){
             myPrize = new MyPrize();
            myPrize.soluong = 1;
            myPrize.user = user;
            myPrize.prize = prize;
            myPrize.status = false;
        }else{
            myPrize.soluong += 1;
        }
        myPrize.timeSave = new Timestamp(System.currentTimeMillis());
        saveMyPrize(myPrize);

    }

    public List<Prize> getAllPrize(User user){
        return userRepository.getAllPrize(user);
    }

    public List<MyPrize> findAllPrize(){
        return myPrizeRepository.findAll();
    }





    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }

    public User getUserByAccountID(int accountId) {
        User user = userRepository.findByAccountId(accountId);
        return user;
    }

    public UserInfo getInfoByUser(int userId) {
        return profileRepository.findByUser(userId);
    }

    public void save(UserInfo userInfo) {
        profileRepository.save(userInfo);
    }
    public void saveAddress(AddressInfo addressInfo) {
        addressRepository.save(addressInfo);
    }
    public void saveUser(User user) {
        userRepository.save(user);
    }
    public List<AddressInfo> getListAddress(User user) {
        return userRepository.getAddress(user);
    }

    public Account getAccountByUser(String username) {
        List<Account> accounts = userRepository.getAccount(username);
        if (accounts.isEmpty()) {
            throw new UsernameNotFoundException("No account found for username: " + username);
        } else if (accounts.size() > 1) {
            return accounts.get(0);
        }
        return accounts.get(0);
    }

    public void saveMessage(ChatGlobal messages) {
        messsageRepository.save(messages);
    }

    public AddressInfo getAddressByID(long addressId) {
        return addressRepository.getAddressInfoByAddressId(addressId);
    }

    public void saveVoucher(Prize voucher) {
         voucherRepository.save(voucher);
    }
    public void saveMyPrize(MyPrize myVc) {
        myPrizeRepository.save(myVc);
    }



    public void saveAccount(Account account){
        accountRepository.save(account);
    }

    public ArrayList<Items> getItemsByUser(User userToken) {
        ArrayList<Items> items = new ArrayList<>();
        ArrayList<Items> bossPermanentItems = new ArrayList<>();
        ArrayList<Items> nonBossPermanentItems = new ArrayList<>();
        ArrayList<Items> nonPermanentItems = new ArrayList<>();
        ArrayList<Items> allItems = userRepository.getItemByUser(userToken);
        // Phân loại items
        for (Items i : allItems) {
            if (i.vinhvien) {
                if (i.beyBlade.isBoss) {
                    bossPermanentItems.add(i);
                } else {
                    nonBossPermanentItems.add(i);
                }
            } else {
                nonPermanentItems.add(i);
            }
        }

        // Thêm các item vĩnh viễn và là boss vào trước
        items.addAll(bossPermanentItems);
        // Thêm các item vĩnh viễn nhưng không phải là boss
        items.addAll(nonBossPermanentItems);
        // Thêm các item không vĩnh viễn vào sau
        items.addAll(nonPermanentItems);

        return items;
    }



    public Items getItemMacDinhByUser(User userToken) {
        return userRepository.getItemMacDinhByUser(userToken);
    }
    public ArrayList<BeyBlade> getBeysByUser(User userToken) {
        return userRepository.getBeysByUser(userToken);
    }
    public ArrayList<BeyBlade> getItemsByUserAndType(User userToken,byte type) {
        return userRepository.getItemsByUserAndType(userToken, type);
    }
    public ArrayList<Items> getAllItems() {
        return userRepository.getAllItems();
    }

//    @Autowired
//    private BeyService beyService;


    public void saveItem(User user,Items itemEdit) {
//        switch ((int) itemEdit.beyBlade.id){
//            case 86://legend
//              saveLR(user,itemEdit,83,84);
//              break;
//            case 100://requiem
//                saveLR(user,itemEdit,93,94);
//                break;
//            case 128://requiem
//                saveLR(user,itemEdit,123,124);
//                break;
//        }
         itemsRepository.save(itemEdit);
    }
//    public Items addNewBey(User user,BeyBlade beyBlade){
//        Items items = new Items();
//        items.beyBlade = beyBlade;
//        items.user = user;
//        items.selectedBey = false;
//        items.create_time = new Timestamp(System.currentTimeMillis());
//        long millisecondsInADay = TimeUnit.DAYS.toMillis(Util.nextInt(1,3));
//        items.ngayhethan = new Timestamp(items.create_time.getTime() + millisecondsInADay);
//        return items;
//    }

//    @Autowired
//    private BeyRepository beyRepository;
//
//    public BeyBlade getBeyByID(long id) {
//        return beyRepository.getBeyById(id);
//    }
//    public void saveLR(User user,Items itmain, int id1){
//        Items L = addNewBey(user,getBeyByID(id1));
//        L.vinhvien = itmain.vinhvien;
//        L.ngayhethan = itmain.ngayhethan;
//        itemsRepository.save(L);
//    }
//   public void saveLR(User user,Items itmain, int id1,int id2){
//        Items L = addNewBey(user,getBeyByID(id1));
//        Items R =  addNewBey(user,getBeyByID(id2));
//        L.vinhvien = itmain.vinhvien;
//        R.vinhvien = itmain.vinhvien;
//        L.ngayhethan = itmain.ngayhethan;
//        R.ngayhethan = itmain.ngayhethan;
//        itemsRepository.save(L);
//        itemsRepository.save(R);
//    }
    public void deteleItem(Items items) {
        itemsRepository.delete(items);
    }
    public void deteleMyPrize(MyPrize items) {
        myPrizeRepository.delete(items);
    }
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public List<MyPrize> getPrizeByStatus(String username, byte i) {
        return myPrizeRepository.getMyPrizeByStatus(username,i);
    }

    public List<Items> getItemIDByUser(User user, BeyBlade beyBlade) {
        return myPrizeRepository.getItemIDByUser(user, beyBlade);
    }

//    public  Items getItemByUser(User user, BeyBlade beyBlade) {
//        Items it = null;
//        List<Items> items = myPrizeRepository.getItemsByUser(user, beyBlade);
//        for (Items items1 : items){
//            if (items1.vinhvien){
//                it = items1;
//                break;
//            }else{
//                it = items1;
//                break;
//            }
//        }
//        return it;
//    }
    public List<GIFTCODE> getCodeKhaDung(int type) {
        return userRepository.getCodeKhaDung(type);
    }


}
