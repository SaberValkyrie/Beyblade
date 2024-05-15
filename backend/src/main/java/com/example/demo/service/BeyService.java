package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.product.BeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * CartService.
 *
 * @author Nguyễn Hải
 * Created 09/03/2024
 */
@Service
public class BeyService {
    @Autowired
    private BeyRepository beyRepository;

    public List<TypeBey> getAllTypes(){
        return beyRepository.getAllTypes();
    }

    public List<BeyBlade> getBeyByTypeID(byte id){
        return beyRepository.getBeyByTypeID(id);
    }

    public BeyBlade getBeyByID(long id) {
        return beyRepository.getBeyById(id);
    }

    public List<BeyBlade> getBoss() {
        return beyRepository.getbosses();
    }

    public List<BeyBlade> findAll() {
        return beyRepository.getAll();
    }

//    public List<BeyBlade> getBeyByChar(String character){
//        return beyRepository.getBeyByChar(character);
//    }
}
