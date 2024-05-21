package com.example.demo.service;

import com.example.demo.dto.ItemShop;
import com.example.demo.dto.Player;
import com.example.demo.entity.*;
import com.example.demo.repository.product.BeyRepository;
import com.example.demo.support.Util;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.lang.model.type.UnionType;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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

    public List<ItemShop> item_shop = new ArrayList<>();

    public int hour = 0;
    public boolean loadShop = true;
    public void loadItemShop(){
        if (!loadShop){
            return;
        }
        List<BeyBlade> list = getBoss();
        int randomIndex = new Random().nextInt(list.size());
        int randomId = (int) list.get(randomIndex).id;
        BeyBlade index1 = getBeyByID(randomId);

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
}
