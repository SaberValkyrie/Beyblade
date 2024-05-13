package com.example.demo.service;

/**
 * TokenService.
 *
 * @author Nguyễn Hải
 * Created 14/03/2024
 */
import com.example.demo.entity.Token;
import com.example.demo.entity.User;
import com.example.demo.repository.user.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.Date;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public Token generateToken(User user) {

        String tokenbuild = UUID.randomUUID().toString() + new Timestamp(System.currentTimeMillis()) + UUID.randomUUID();

        Token token = new Token();
        token.setCode(tokenbuild);
        token.setUser(user);
        Date st = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(System.currentTimeMillis() + 3600 * 1000); // Thêm 1 giờ (3600 giây) vào thời gian hiện tại
        token.setExpiry(expiryDate);
        token.setUpdateTime(st);
        tokenRepository.save(token);
        return token;
    }

    public Token getTokenByUser(User user) {
        return tokenRepository.getTokenByUser(user);
    }

    public User getUserFromToken(String token) {
        return tokenRepository.getUserFromToken(token);
    }

    public User validate(User user) {
        return tokenRepository.validate(user);
    }

    public void deleteToken(String code) {
            tokenRepository.deleteByCode(code);
    }

}
