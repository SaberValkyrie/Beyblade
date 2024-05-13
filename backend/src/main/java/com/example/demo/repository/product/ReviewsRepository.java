package com.example.demo.repository.product;

import com.example.demo.entity.Product;
import com.example.demo.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;


@Repository
public interface ReviewsRepository extends JpaRepository<Review,Long> {
    @Query("select r from Review  r where r.product.productId = :productId order by r.createdAt desc")
    List<Review> getReview(long productId);


}
