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
public interface BeyRepository extends JpaRepository<BeyBlade,Long> {


    @Query("select t from TypeBey t")
    List<TypeBey> getAllTypes();


    @Query("select b from BeyBlade b where b.type.id =:id")
    List<BeyBlade> getBeyByTypeID(byte id);

    @Query("select b from BeyBlade b where b.id =:id")
    BeyBlade getBeyById(long id);

    @Query("select b from BeyBlade b where b.isBoss = true")
    List<BeyBlade> getbosses();

    @Query("select b from BeyBlade b")
    List<BeyBlade> getAll();


    @Query("select i from Items i where i.user=:userToken and i.beyBlade.id=:beyBladeId and i.vinhvien = true")
    List<Items> finItem(User userToken, long beyBladeId);
}
