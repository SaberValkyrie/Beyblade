package com.example.demo.dto;

import com.example.demo.entity.Cart;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * CartReponse.
 *
 * @author Nguyễn Hải
 * Created 09/03/2024
 */

@Data
public class CartReponse {

    public String seller;
    public String avatarSeller;
    public List<Cart> cartDetails;

    public CartReponse(String seller, List<Cart> cart) {
        this.seller = seller;
        this.cartDetails = cart;
    }

    public CartReponse(String seller, String avatarSeller, List<Cart> cartDetails) {
        this.seller = seller;
        this.avatarSeller = avatarSeller;
        this.cartDetails = cartDetails;
    }

    public CartReponse() {

    }
}
