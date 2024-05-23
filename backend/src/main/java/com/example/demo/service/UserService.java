package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.ItemsRepository;
import com.example.demo.repository.product.MyVoucherRepository;
import com.example.demo.repository.product.VoucherRepository;
import com.example.demo.repository.user.*;
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

    public List<Voucher> getVoucherByUser(User user) {
        return userRepository.getVoucher(user);
    }

    public Voucher getVoucherByCode(String code) {
        return userRepository.getVoucherByCode(code);
    }
    public MyVoucher getMyVoucherByVoucher(Voucher voucher,User user) {
        return myVoucherRepository.getMyVoucherByCode(voucher,user);
    }


    public Voucher findVoucherByID(long id){
        return  myVoucherRepository.findById(id);
    }
    public List<Voucher> getVoucherHetHan(User userToken) {
        List<Voucher> all =  myVoucherRepository.findByUser(userToken);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        List<Voucher> res = new ArrayList<>();
        for (Voucher voucher :all){
            if (voucher.endTime.before(currentTime)){
                res.add(voucher);
            };
        }
        return res;
    }

    public List<Voucher> getvoucherUsed(User userToken) {

        return myVoucherRepository.getVoucherUsed(userToken);
    }

    public void saveVoucher(Voucher voucher) {
         voucherRepository.save(voucher);
    }
    public void saveMyVoucher(MyVoucher myVc) {
        myVoucherRepository.save(myVc);
    }



    public void saveAccount(Account account){
        accountRepository.save(account);
    }

    public ArrayList<Items> getItemsByUser(User userToken) {
        return userRepository.getItemByUser(userToken);
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
}
