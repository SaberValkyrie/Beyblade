package com.example.demo.repository.product;

import com.example.demo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CateRepository.
 *
 * @author Nguyễn Hải
 * Created 09/03/2024
 */
    @Repository
    public interface CateRepository extends JpaRepository<Category,Byte> {

        @Query("select c from Category c order by c.id asc ")
        List<Category> findCategories(); // mục tìm kiếm

    @Query("select c from Category c where c.id =:id")
    Category findById(byte id);
}