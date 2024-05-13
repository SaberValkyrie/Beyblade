package com.example.demo.repository.product;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CartRepository.
 *
 * @author Nguyễn Hải
 * Created 09/03/2024
 */
@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {




    @Query("select c from Cart c where c.id = :id")
    Cart findCartByID(long id);

    @Query("select c from Cart c where c.product = :product AND c.user =:user order by c.createdAt desc ")
    Cart getCartByProduct(Product product, User user);

    @Query("select c.product from Cart c where c.product.userPrize = :user order by c.createdAt desc ")
    List<Product> getAllProduct(User user);


    @Query("select c from Cart c where c.user = :user order by c.createdAt desc ")
    List<Cart> findAllCartByUser(User user);


}
