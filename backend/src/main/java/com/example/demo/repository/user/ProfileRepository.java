package com.example.demo.repository.user;

import com.example.demo.entity.User;
import com.example.demo.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * ProfileRepository.
 *
 * @author Nguyễn Hải
 * Created 16/03/2024
 */

@Repository
public interface ProfileRepository extends JpaRepository<UserInfo, Integer> {
    @Query("select u from UserInfo u where u.userId = :userId")
    UserInfo findByUser(int userId);
}
