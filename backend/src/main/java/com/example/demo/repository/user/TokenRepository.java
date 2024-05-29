package com.example.demo.repository.user;

import com.example.demo.entity.Token;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * TokenRepository.
 *
 * @author Nguyễn Hải
 * Created 14/03/2024
 */
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token getTokenByUser(User user);

   @Query("select t.user from Token t where t.code = :token")
    User getUserFromToken(String token);

    @Query("select t.user from Token t where t.user = :user")
    User validate(User user);

    @Transactional
    @Modifying
    @Query("delete from Token t where t.code = :code")
    void deleteByCode(@Param("code") String code);
}
