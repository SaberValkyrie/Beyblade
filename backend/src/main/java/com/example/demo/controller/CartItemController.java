//package com.example.demo.controller;
//
//import com.example.demo.support.Util;
//import com.example.demo.dto.CartReponse;
//import com.example.demo.dto.ResponseOpject;
//import com.example.demo.entity.Cart;
//import com.example.demo.entity.Product;
//import com.example.demo.entity.User;
//import com.example.demo.service.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping(path = "/cart")
//public class CartItemController {
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private NotifyService notifyService;
//    @Autowired
//    private CartService cartService;
//    @Autowired
//    private TokenService tokenService;
//    @Autowired
//    private ProductService productService;
//
//
//    @GetMapping("/{token}")
//    public ResponseEntity<ResponseOpject> getCartItems(@PathVariable String token) {
//        User userToken = tokenService.getUserFromToken(token);
//        if (userToken == null) {
//            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
//        }
//
//        List<Cart> listCartItems = cartService.findByUser(userToken);
//
//
//
//
//        return listCartItems.size() > 0 ?
//                Util.checkStatusRes(HttpStatus.OK, "Đã tìm được " + Util.numberToMoney(slot) + " "
//                            , listCartItems) :
//                Util.checkStatusRes(HttpStatus.NOT_FOUND,
//                        "Không có sản phẩm trong giỏ hàng", null);
//    }
//
//    @PutMapping("/quantity/productId={id}/{token}/{action}")
//    public ResponseEntity<ResponseOpject> editCart(@PathVariable long id,
//                                                   @PathVariable String token,
//                                                   @PathVariable byte action
//    ) {
//        User userToken = tokenService.getUserFromToken(token);
//        if (userToken == null) {
//            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
//        }
//        Product product = productService.findByID(id);
//        Cart cartItem = cartService.getCartByProduct(product,userToken);
//        if (cartItem == null){
//            return Util.checkStatus(HttpStatus.NOT_FOUND, "Không tìm thấy giỏ hàng của bạn", null);
//        }
//        if (!cartItem.user.equals(userToken)){
//            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Không thể thay đổi sản phẩm của người khác", null);
//        }
//
//     switch (action){
//         case -1:
//             if (cartItem.quantityCart <= 1){
//                 return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Không thể giảm khi số lượng còn 1", null);
//             }
//             cartItem.quantityCart -= 1;
//             break;
//         case 1:
//             if(cartItem.quantityCart >= product.quantityStock){
//
//                 return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Không thể chứa quá " + product.quantityStock + " sản phẩm", null);
//             }
//             cartItem.quantityCart += 1;
//             break;
//     }
//        cartService.save(cartItem);
//
//        return Util.checkStatusRes(HttpStatus.OK, "Update số lượng Thành Công : " + cartItem.quantityCart, cartItem);
//    }
//
//
//    @PostMapping("/add/productId={id}/{token}")
//    public ResponseEntity<ResponseOpject> addCartItems(@PathVariable long id,@PathVariable String token) {
//        User userToken = tokenService.getUserFromToken(token);
//        if (userToken == null) {
//            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
//        }
//        Product product = productService.findByID(id);
//        Cart cartItem = cartService.getCartByProduct(product,userToken);
//
//        if (cartItem == null){
//            cartItem = new Cart();
//            cartItem.user = userToken;
//            cartItem.product = product;
//        }
//        if (product.seller.equals(userToken)){
//            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Không thể thêm sản phẩm của chính mình", null);
//        }
//        if(cartItem.quantityCart > product.quantityStock){
//            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Không thể chứa quá " + product.quantityStock + " sản phẩm", null);
//        }
//
//        cartItem.quantityCart += 1;
//        cartItem.createdAt = new Timestamp(System.currentTimeMillis());
//        cartService.save(cartItem);
//
//        return Util.checkStatusRes(HttpStatus.OK, "Thêm Sản Phẩm Thành Công", cartItem);
//    }
//    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//    @DeleteMapping("/delete/cartId={id}/{token}")
//    public ResponseEntity<ResponseOpject> deleteCartItems(@PathVariable long id,@PathVariable String token) {
//        User userToken = tokenService.getUserFromToken(token);
//        if (userToken == null) {
//            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
//        }
//        Cart cart = cartService.findById(id);
//        if(cart == null){
//            return Util.checkStatus(HttpStatus.NOT_FOUND, "Giỏ hàng không tồn tại", null);
//        }
//
//        if(!cart.user.equals(userToken)){
//            return Util.checkStatus(HttpStatus.NOT_FOUND, "Không thể xóa sản phẩm của người khác", null);
//        }
//        cartService.delete(cart);
//
//        return Util.checkStatusRes(HttpStatus.OK, "Xóa sản phẩm thành công", null);
//    }
//}
