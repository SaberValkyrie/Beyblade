package com.example.demo.repository.product;

import com.example.demo.entity.Product;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM Product p" +
            " order by p.productId desc ")
    List<Product> findAllProduct(); //san pham kha dung de tim kiem


    Product findByProductId (long productId); // details

    @Query("SELECT p FROM Product p" +
            " order by p.productId desc ")
    List<Product> findByCategoryIdOrderByProductIdDesc(byte categoryId);// tim theo danh muc

    @Query("SELECT p FROM Product p" +
            " ORDER BY p.productId DESC")
    List<Product> findquery(@Param("query") String query);// tìm sản phẩm theo từ khóa

    @Query("SELECT p FROM Product p" +
            " ORDER BY p.category.id DESC")
    List<Product> findbySold();

    @Query("SELECT p FROM Product p" +

            " ORDER BY p.price DESC")
    List<Product> cao();


    @Query("SELECT p FROM Product p" +
            " ORDER BY p.price asc")
    List<Product> thap();


    @Query("SELECT p FROM Product p" +
            " order by p.productId desc ")
    List<Product> findAllProduct(User user);

    @Query("SELECT p FROM Product p" +
            " where p.productId =:id " +
            " and p.userPrize =:userToken")
    Product getProductEdit(int id, User userToken);
}
