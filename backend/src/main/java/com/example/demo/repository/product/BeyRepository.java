package com.example.demo.repository.product;

import com.example.demo.entity.*;
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
public interface BeyRepository extends JpaRepository<Cart,Long> {


    @Query("select t from TypeBey t")
    List<TypeBey> getAllTypes();


    @Query("select b from BeyBlade b where b.type.id =:id")
    List<BeyBlade> getBeyByTypeID(byte id);

    @Query("select b from BeyBlade b where b.id =:id")
    BeyBlade getBeyById(long id);
}
