//package com.example.demo.service;
//
//import com.example.demo.entity.Cart;
//import com.example.demo.repository.product.CartRepository;
//import com.example.demo.entity.Product;
//import com.example.demo.entity.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * CartService.
// *
// * @author Nguyễn Hải
// * Created 09/03/2024
// */
//@Service
//public class CartService {
//    @Autowired
//    private CartRepository cartRepository;
//
//    public List<Cart> findByUser(User user) {
//        return cartRepository.findAllCartByUser(user);
//    }
//
//    public List<User> getAllSellerForCart(User loggedInUser) {
//        List<User> sellers = new ArrayList<>();
//        List<Cart> cartItems = getCartItemByUserIdOrderByCreatedAtDesc(loggedInUser);
//
//        for (Cart cartItem : cartItems) {
//            User seller = cartItem.product.seller;
//            if (!sellers.contains(seller)) {
//                sellers.add(seller);
//            }
//        }
//
//        return sellers;
//    }
//
//    public List<Cart> getCartItemByUserIdOrderByCreatedAtDesc(User user) {
//        List<Cart> cartItems = findByUser(user);
//        cartItems.removeIf(cartItem -> cartItem.product == null
//                || cartItem.product.quantityStock == 0
//                || cartItem.quantityCart == 0
//                || cartItem.product.seller.equals(user));
//        return cartItems;
//    }
//
//    public Cart findById(long id) {
//        return cartRepository.findCartByID(id);
//    }
//
//    public void delete(Cart cartItem) {
//        cartRepository.delete(cartItem);
//    }
//
//    public Cart getCartByProduct(Product product,User user) {
//        return cartRepository.getCartByProduct(product,user);
//    }
//
//    public List<Cart> findAll() {
//        return cartRepository.findAll();
//    }
//
//    public void save(Cart cartItem) {
//        cartRepository.save(cartItem);
//    }
//
//
//
//    public List<Product> getAllProductInSeller(User user) {
//        return cartRepository.getAllProduct(user);
//    }
//
////    public CartDetails getDetailsCartByProduct(Product product) {
////        return cartRepository.getDetails(product);
////    }
//}
