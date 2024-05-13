package com.example.demo.service;

import com.example.demo.entity.Review;
import com.example.demo.repository.product.CartRepository;
import com.example.demo.repository.product.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

        @Autowired
        private ReviewsRepository reviewsRepository;


        public void save(Review review){
                reviewsRepository.save(review);
        }

}
