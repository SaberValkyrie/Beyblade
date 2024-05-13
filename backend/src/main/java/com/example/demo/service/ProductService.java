package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.product.CateRepository;
import com.example.demo.repository.product.ProductRepository;
import com.example.demo.repository.product.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CateRepository cateRepository;

    @Autowired
    private ReviewsRepository reviewsRepository;
    public List<Product> findAll() {
        return repository.findAllProduct();
    }


    public List<Product> findAllProducts() {
        return repository.findAll();
    }

    public Product findByID(long productId){
        return repository.findByProductId(productId);
    }
    public  List<Product> findByCategory(byte categoryId){
        return repository.findByCategoryIdOrderByProductIdDesc(categoryId);
    }
    public  List<Product> findByQuery(String query){
        return repository.findquery(query);
    }
    public  List<Product> findBySold(){
        return repository.findbySold();
    }
    public  List<Product> Cao(){
        return repository.cao();
    }

    public  List<Product> Thap(){
        return repository.thap();
    }

    public List<Category> findCategory() {
        return cateRepository.findCategories();
    }
    public Category findCategory(byte id) {
        return cateRepository.findById(id);
    }
    public List<Review> getAllReviewsByProduct(long productId) {
        return reviewsRepository.getReview(productId);
    }


    public Product save(Product product){
        return repository.save(product);
    }









    public List<Product> findAllByUser(User user) {
        return repository.findAllProduct(user);
    }


    public Product getProductByIdAndUser(int id, User userToken) {
        return repository.getProductEdit(id,userToken);
    }
}
