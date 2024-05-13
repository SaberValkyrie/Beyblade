//package com.example.demo.config;
//
///**
// * GlobalControllerAdvice.
// *
// * @author Nguyễn Hải
// * Created 12/03/2024
// */
//
//import com.example.demo.entity.User;
//import com.example.demo.repository.NotificationRepository;
//import com.example.demo.service.ProductService;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ModelAttribute;
//
//import javax.servlet.http.HttpSession;
//
//@ControllerAdvice
//public class GlobalControllerAdvice {
//
//
////    private final CategoryService categoryService;
////    private final CartItemService cartItemService;
//    private final NotificationRepository notificationRepository;
//    private final ProductService productService;
//
//    public GlobalControllerAdvice(
////            CategoryService categoryService, CartItemService cartItemService,
//            NotificationRepository notificationRepository, ProductService productService) {
////        this.categoryService = categoryService;
////        this.cartItemService = cartItemService;
//        this.notificationRepository = notificationRepository;
//        this.productService = productService;
//    }
//
//    @ModelAttribute("loggedInUser")
//    public User getLoggedInUser(HttpSession session) {
//        return (User) session.getAttribute("loggedInUser");
//    }
//
////    @ModelAttribute("notifications")
////    public List<Notification> getNotifications(HttpSession session) {
////        User loggedInUser = (User) session.getAttribute("loggedInUser");
////        if (loggedInUser != null) {
////            return notificationRepository.findAllByUserUserIdOrderByCreatedAtDesc(loggedInUser.userId);
////        }
////        return Collections.emptyList();
////    }
//
////    @ModelAttribute("categories")
////    public List<Category> getCategories() {
////        return categoryService.getAllCategories();
////    }
//
////    @ModelAttribute("cartItems")
////    public List<CartItem> getCartItems(HttpSession session) {
////        User loggedInUser = (User) session.getAttribute("loggedInUser");
////        if (loggedInUser != null) {
////            return cartItemService.getCartItemByUserIdOrderByCreatedAtDesc(loggedInUser.userId);
////        }
////        return Collections.emptyList();
////    }
//}
