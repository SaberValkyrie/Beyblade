//package com.example.demo.controller;
//
//import com.example.demo.dto.CartReponse;
//import com.example.demo.dto.OrderRequest;
//import com.example.demo.dto.ResponseOpject;
//import com.example.demo.entity.*;
//import com.example.demo.service.*;
//import com.example.demo.entity.Product;
//import com.example.demo.support.Util;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.sql.Timestamp;
//import java.util.*;
//
//
//@RestController
//@RequestMapping(path = "/order")
//public class OrderController {
//
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private NotifyService notifyService;
////    @Autowired
////    private CartService cartService;
//    @Autowired
//    private TokenService tokenService;
//    @Autowired
//    private ProductService productService;
//
//    @Autowired
//    private OrderService service;
//
//
////    @PutMapping("update/token={token}/id={id}/key={key}/type={type}")
////    public ResponseEntity<ResponseOpject> update(@PathVariable String token,
////            @PathVariable int id, @PathVariable String key, @PathVariable String type) {
////
////        User userToken = tokenService.getUserFromToken(token);
////        if (userToken == null) {
////            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
////        }
////        Order order = service.getOrderByKey(key);
////
////        if (order == null || order.orderId != id){
////            return Util.checkStatusRes(HttpStatus.NOT_FOUND, "Không tìm thấy đơn hàng ", null);
////        }
////
////        Account accountToken = userService.getAccountByUser(userToken.username);
////
////        boolean check = order.user.equals(userToken)//user
////                || order.seller.equals(userToken) //seller
////                || accountToken.role == 2;//admin
////
////        if (!check){
////            return Util.checkStatus(HttpStatus.BAD_REQUEST, "Không thể xem đơn hàng này vì bạn không đủ quyền", null);
////        }
////        switch (type){
////            case "start": //Đang Gói Hàng => Đang Vận Chuyển
////                if (order.status > 4){
////                    return Util.checkStatus(HttpStatus.BAD_REQUEST, "Trạng thái hiện tại không thể update", null);
////                }
////                if (!order.seller.equals(userToken)){
////                    return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Chỉ có người bán mới được xác nhận", null);
////                }
////                order.status += 1;
////                break;
////            case "end":
////                if (order.status != 5){ //Đang Giao Hàng => Hoàn Thành
////                    return Util.checkStatus(HttpStatus.BAD_REQUEST, "Trạng thái hiện tại không thể update", null);
////                }
////                if (!order.user.equals(userToken)){
////                    return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Chỉ có người mua mới được xác nhận", null);
////                }
////                order.status = 6;
////                updateCointSeller(order);
////
////                break;
////            case "return":
////                if (order.status != 5){ //Hoàn Trả
////                    return Util.checkStatus(HttpStatus.BAD_REQUEST, "Trạng thái hiện tại không thể update", null);
////                }
////                if (!order.user.equals(userToken)){
////                    return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Chỉ có người mua mới được xác nhận", null);
////                }
////                order.status = 7;
////
////                break;
////            case "cancel":
////                if (order.status != 2){ //hủy đơn
////                    return Util.checkStatus(HttpStatus.BAD_REQUEST, "Trạng thái hiện tại không thể update", null);
////                }
////                if (!order.user.equals(userToken) && !order.seller.equals(userToken)){
////                    return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Không được hủy đơn hàng của người khác", null);
////                }
////                order.status = 8;
////                break;
////        }
////        service.save(order);
////
////        return Util.checkStatusRes(HttpStatus.OK, "Cập nhật đơn hàng thành công", order);
////    }
////
//
//    @GetMapping("get/token={token}/id={id}/key={key}")
//    public ResponseEntity<ResponseOpject> getOrderByKey(
//            @PathVariable String token,
//            @PathVariable int id,
//            @PathVariable String key
//    ) {
//        User userToken = tokenService.getUserFromToken(token);
//        if (userToken == null) {
//            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
//        }
//        Order order = service.getOrderByKey(key);
//
//        if (order == null || order.orderId != id){
//          return Util.checkStatusRes(HttpStatus.NOT_FOUND, "Không tìm thấy đơn hàng ", null);
//        }
//
//        Account accountToken = userService.getAccountByUser(userToken.username);
//
//        boolean check = order.user.equals(userToken)//user
//                || accountToken.role == 2;//admin
//
//        if (!check){
//            return Util.checkStatus(HttpStatus.BAD_REQUEST, "Không thể xem đơn hàng này vì bạn không đủ quyền", null);
//        }
//
//        return Util.checkStatusRes(HttpStatus.OK, "Đã tìm được 1 đơn hàng", order);
//    }
//
//
//    @GetMapping("getAll/{token}/{status}/{type}")
//    public ResponseEntity<ResponseOpject> getAllOrders(
//            @PathVariable String token,
//            @PathVariable byte status,
//             @PathVariable String type
//    ) {
//        User userToken = tokenService.getUserFromToken(token);
//        if (userToken == null) {
//            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
//        }
//        List<Order> orders = new ArrayList<>();
//        switch (type){
//            case "user":
//                if (status == 0){
//                    orders = service.getAllOrder(userToken);
//                    break;
//                }
//                orders = service.getOrderByToken(userToken,status);
//
//                break;
//            case "seller":
//                if (status == 0){
//                    orders = service.getAllOrderBySeller(userToken);
//                    break;
//                }
//                orders = service.getOrderBySeller(userToken,status);
//                break;
//        }
//        OrderStatus status1 = service.getst(status);
//
//        String st = status1 != null ? status1.status : "Tất Cả";
//
//
//
//        return orders.size() > 0 ? Util.checkStatusRes(HttpStatus.OK, "Đã tìm được " +orders.size() + " đơn hàng với trạng thái :" + st, orders)
//                : Util.checkStatusRes(HttpStatus.NOT_FOUND, " Không tìm thấy đơn hàng có trạng thái :" + st, null);
//    }
//
//
//    @GetMapping("dashboard/{token}/{type}")
//    public ResponseEntity<ResponseOpject> getAllOrdersByTime(
//            @PathVariable String token,
//            @PathVariable String type
//    ) {
//        User userToken = tokenService.getUserFromToken(token);
//        if (userToken == null) {
//            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
//        }
//
//        List<Order> orders = service.getOrderBySellerToDay(userToken);
//
//        List<Order> response = new ArrayList<>();
//
//     for (Order order : orders){
//                    switch (type) {
//                        case "nay":
//                            if (Util.nay(order)) {
//                                response.add(order);
//                            }
//                            break;
//                        case "qua":
//                            if (Util.qua(order)) {
//                                response.add(order);
//                            }
//                            break;
//                    }
//                }
//        return response.size() > 0 ? Util.checkStatusRes(HttpStatus.OK, "Đã tìm được " + response.size()+" đơn hàng" , response)
//                : Util.checkStatusRes(HttpStatus.NOT_FOUND, "Không tìm thấy đơn hàng ", null);
//    }
//
//
//    @GetMapping("get/item/token={token}/id={id}/key={key}")
//    public ResponseEntity<ResponseOpject> getItem(
//            @PathVariable String token,
//            @PathVariable int id,
//            @PathVariable String key
//    ) {
//        User userToken = tokenService.getUserFromToken(token);
//        if (userToken == null) {
//            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
//        }
//        Order order = service.getOrderByKey(key);
//
//        if (order == null || order.orderId != id){
//            return Util.checkStatusRes(HttpStatus.NOT_FOUND, "Không tìm thấy đơn hàng ", null);
//        }
//
//
//        Account accountToken = userService.getAccountByUser(userToken.username);
//
//        boolean check = order.user.equals(userToken)//user
//                || accountToken.role == 2;//admin
//
//        if (!check){
//            return Util.checkStatus(HttpStatus.BAD_REQUEST, "Không thể xem đơn hàng này vì bạn không đủ quyền", null);
//        }
//        List<OrderItem> orderItem = service.getItemByOrder(order);
//
//        return orderItem != null ? Util.checkStatusRes(HttpStatus.OK, "Đã tìm được "+orderItem.size() +" sản phẩm", orderItem)
//                : Util.checkStatusRes(HttpStatus.NOT_FOUND, "Không thấy Sản Phẩm", null) ;
//    }
//
//
//    @PostMapping("/{token}/create/{paymentMethod}/{shippingMethod}")
//    public ResponseEntity<ResponseOpject> createOrder(
//            @PathVariable String token,
//            @RequestBody OrderRequest orderRequest,
//            @PathVariable String shippingMethod,
//            @PathVariable String paymentMethod
//    ) {
//        User userToken = tokenService.getUserFromToken(token);
//        if (userToken == null) {
//            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
//        }
//
//        List<Order> orders = new ArrayList<>(); // Ds các đơn hàng được tạo
//
//        AddressInfo addressInfo = orderRequest.addressInfo;
//        List<CartReponse> orderItems = orderRequest.orderItems;
//        long voucherID = orderRequest.voucher;
//        String info = info(addressInfo);
//
//        Timestamp now = new Timestamp(System.currentTimeMillis());
//        Voucher voucher = userService.findVoucherByID(voucherID);
//        Account accountToken = userService.getAccountByUser(userToken.username);
//
//
//
//
//
//
//        Product product = null;
//        int soluong = 0;
//
//
//        for (CartReponse cartResponse : orderItems) {
//            Map<User, List<OrderItem>> orderItemsBySeller = new HashMap<>();
//            for (Cart cartItem : cartResponse.cartDetails) {
//                soluong = cartItem.quantityCart;
//               product = productService.findByID(cartItem.product.productId);
////                OrderItem orderItem = newOrderItem(soluong, cartItem.product, cartItem.product.price * cartItem.quantityCart);
//            }
//            for (Map.Entry<User, List<OrderItem>> entry : orderItemsBySeller.entrySet()) {
//                User seller = entry.getKey();
//                List<OrderItem> sellerOrderItems = entry.getValue();
////                double totalAmount = sellerOrderItems.stream().mapToDouble(OrderItem::getTotal_monney).sum();
////                double percent_Off = (voucher != null) ? voucher.percent : 0.0;
////                double total_price = totalAmount - (percent_Off * totalAmount / 100);
////                double tiennhan = totalAmount - (5 * totalAmount / 100) ;
//
//
//                if (voucher != null){
//                    if (voucher.endTime.before(now)){
//                        return Util.checkStatus(HttpStatus.BAD_REQUEST, "Voucher đã hết hạn", null);
//                    }
//                    if (voucher.startTime.after(now)){
//                        return Util.checkStatus(HttpStatus.BAD_REQUEST, "Voucher chưa tới thời gian sử dụng", null);
//                    }
//                    if (voucher.countLeft <= 0){
//                        return Util.checkStatus(HttpStatus.BAD_REQUEST, "Voucher đã hết số lượng sử dụng", null);
//                    }
////                    if (voucher.giaToiThieu > totalAmount){
////                        return Util.checkStatus(HttpStatus.BAD_REQUEST, "Tổng giá sản phẩm phải lớn hơn " + Util.numberToMoney((long) voucher.giaToiThieu), null);
////                    }
//                }
//
//
//
//
//
//
////                if (paymentMethod.equals("Coin")){
////                    if (accountToken.coint < total_price){
////                        return Util.checkStatus(HttpStatus.BAD_REQUEST, "Bạn còn thiếu " + Util.numberToMoney((long) (total_price - accountToken.coint)) + " Coin,vui lòng nạp thêm" , null);
////                    }
////                    accountToken.coint -= total_price;
////                }
//
//                String key = Util.key(token);
//
////                OrderDetails details = newOrderDetails(paymentMethod, shippingMethod, info, total_price, tiennhan,voucher);
////                Order order = newOrder(details, seller, userToken, now,key);
////                service.save(details);
////                service.save(order);
//
//                //lap ds item r luu vao dh
////                for (OrderItem orderItem : sellerOrderItems) {
////                    orderItem.setOrder(order);
////                    service.save(orderItem);
////                }
////                updateCartAndItems(cartResponse.cartDetails,product,soluong);
////
////
////
////                orders.add(order);
//            }
//
//        }
//
//        if (voucher != null){
//            MyVoucher myVoucher = userService.getMyVoucherByVoucher(voucher,userToken);
//
//            voucher.countLeft -= 1;
//            userService.saveVoucher(voucher);
//
//            if(myVoucher != null){
//                myVoucher.status = false;
//                userService.saveMyVoucher(myVoucher);
//            }
//        }
//
//        return !orders.isEmpty() ? Util.checkStatusRes(HttpStatus.OK, "Đặt Hàng Thành Công", orders)
//                    : Util.checkStatus(HttpStatus.BAD_REQUEST, "Không thể tạo đơn hàng", null);
//
//    }
//
//
//    @GetMapping("/status/{id}")
//    public ResponseEntity<ResponseOpject> getstatus(@PathVariable byte id) {
//        OrderStatus status = service.getst(id);
//        return  Util.checkStatusRes(HttpStatus.OK, "Tìm Thành Công", status);
//    }
//
//
//    @GetMapping("/status")
//    public ResponseEntity<ResponseOpject> getStatus() {
//        List<OrderStatus> status = service.getAllStatus();
//        return  Util.checkStatusRes(HttpStatus.OK, "Tìm Thành Công", status);
//    }
//
//    private void updateCartAndItems(List<Cart> carts,Product product,int soluong){
//
//
//    }
//
//    private String info(AddressInfo addressInfo){
//        String info = "";
//        info += "\nHọ Và Tên:" + addressInfo.fullname;
//        info += ",\nSố Điện Thoại:" + addressInfo.phone;
//        info += ",\nNơi Ở:" + addressInfo.phuongXa + " " + addressInfo.quanHuyen + " " + addressInfo.thanhPho;
//        info += ",\nĐịa Chỉ Chi Tiết:" + addressInfo.addressDetails;
//        info += "";
//        return info;
//    }
//
//
//
//
//
//
//
//
//
//
//    @PostMapping("/total/{product}/{percent}")
//    public ResponseEntity<ResponseOpject> test(
//            @PathVariable int product,
//            @PathVariable int percent
//    ) {
//        return Util.checkStatus(HttpStatus.OK,
//                -percent  + " /" + product ,
//                product - (percent * product / 100) );
//    }
//
//
//
//
//}