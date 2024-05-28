package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.ItemsRepository;
import com.example.demo.repository.product.MyVoucherRepository;
import com.example.demo.repository.product.VoucherRepository;
import com.example.demo.repository.user.*;
import com.example.demo.support.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    private MyVoucherRepository myVoucherRepository;
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







    public void addPrize(User user){
        byte id = 1;
        if (Util.isTrue(20,100)){
            id = 2;
        }
        if (Util.isTrue(5,100)){
            id = 3;
        }
        Prize prize = voucherRepository.getPrizeByID(id);
        MyPrize myPrize = new MyPrize();
        myPrize.user = user;
        myPrize.status = false;
        myPrize.prize = prize;
        myPrize.timeSave = new Timestamp(System.currentTimeMillis());
        saveMyPrize(myPrize);

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
        return userRepository.getAccount(username);
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
        myVoucherRepository.save(myVc);
    }



    public void saveAccount(Account account){
        accountRepository.save(account);
    }

    public ArrayList<Items> getItemsByUser(User userToken) {
        return userRepository.getItemByUser(userToken);
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

    public void saveItem(Items itemEdit) {
         itemsRepository.save(itemEdit);
    }

    public void deteleItem(Items items) {
        itemsRepository.delete(items);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
