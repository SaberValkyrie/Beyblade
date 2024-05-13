package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.TokenService;
import com.example.demo.support.Util;
//import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.dto.ResponseOpject;
import com.example.demo.entity.Review;
import com.example.demo.repository.product.ProductRepository;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/products")
public class ProductController {


    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductService service;

    @Autowired
    private TokenService tokenService;
    @GetMapping("")
    List<Product> getAllProducts(){
        return repository.findAll();
    }
//===================================================================================================================

    @GetMapping("/category")
    ResponseEntity<ResponseOpject> findCategory(){
        List<Category> products = service.findCategory();

        return products.size() > 0 ?
                Util.checkStatus(HttpStatus.OK, "Đã tìm được " + products.size() + " danh mục", products) :
                Util.checkStatus(HttpStatus.NOT_FOUND, "Không có sản phẩm", "");
    }
    //===================================================================================================================
    @GetMapping("/get/category/{id}")
    ResponseEntity<ResponseOpject> findCategoryByID(
            @PathVariable byte id){

        Category category = service.findCategory(id);

return  Util.checkStatus(HttpStatus.OK, "Đã tìm được " +" danh mục", category);
    }

    @GetMapping("/search")
    ResponseEntity<ResponseOpject> findAll(){
        List<Product> products = service.findAll();

        return products.size() > 0 ?
                Util.checkStatus(HttpStatus.OK, "Đã tìm được " + products.size() + " sản phẩm", products) :
                Util.checkStatus(HttpStatus.NOT_FOUND, "Không có sản phẩm", "");
    }

//===================================================================================================================

    @GetMapping("/search/status={status}")
    ResponseEntity<ResponseOpject> findBystatus(@PathVariable String status){
        List<Product> products ;
       switch (status){
           case "phobien":
           case "moinhat":
               products = service.findAll();
               break;
           case "banchay":
               products = service.findBySold();
               break;
           case "giaCaoDenThap":
               products = service.Cao();
               break;
           case "giaThapDenCao":
               products = service.Thap();
               break;
           default:
               products = new ArrayList<>();
       }
        return products.size() > 0 ?
                Util.checkStatus(HttpStatus.OK, "Đã tìm được " + products.size() + " sản phẩm", products) :
                Util.checkStatus(HttpStatus.NOT_FOUND, "Không có sản phẩm", "");
    }

//===================================================================================================================





//===================================================================================================================

    @GetMapping("/category/{categoryId}")
    ResponseEntity<ResponseOpject> findByCategory(@PathVariable byte categoryId){
        List<Product> products = service.findByCategory(categoryId);
        return products.size() > 0 ?
                Util.checkStatus(HttpStatus.OK, "Đã tìm được "+ products.size()+" sản phẩm " , products) :
                Util.checkStatus(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm", "");
    }

//===================================================================================================================


    @GetMapping("/search/query={query}")
    ResponseEntity<ResponseOpject> findByquery(@PathVariable String query){
        if (query.equals("")){
          return Util.checkStatus(HttpStatus.NOT_FOUND, "Không có từ khóa", "");
        }
        List<Product> products = service.findByQuery(query);
        return products.size() > 0 ?
                Util.checkStatus(HttpStatus.OK, "Đã tìm được "+ products.size()+" sản phẩm với từ khóa '" + query +"'" , products) :
                Util.checkStatus(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm", "");
    }
//===================================================================================================================

    @DeleteMapping("{id}")
    ResponseEntity<ResponseOpject> update (@PathVariable long id){
        boolean exits = repository.existsById(id); // id da ton tai
        if (exits){
            repository.deleteById(id);
        }
        return exits ? Util.checkStatus(HttpStatus.OK,"Xoa thanh cong id " + id,"")
  : Util.checkStatus(HttpStatus.NOT_FOUND,"Xoa that bai ,khong tim thay id " + id ,"");
    }
//===================================================================================================================

    @GetMapping("/reviews/{id}")
    ResponseEntity<ResponseOpject> getStatus(@PathVariable long id){
        //tìm danh sách đánh giá  theo product
        List<Review> reviews = service.getAllReviewsByProduct(id);

        return reviews.size() > 0 ?
                Util.checkStatus(HttpStatus.OK, "Đã tìm được "+ reviews.size()+" đánh giá " , reviews) :
                Util.checkStatus(HttpStatus.NOT_FOUND, "Không có đánh giá nào", "");
    }
//===================================================================================================================
@GetMapping("/reviews/limit/{id}")
ResponseEntity<ResponseOpject> getReviewsLimit(@PathVariable long id){
    //tìm danh sách đánh giá  theo product
    List<Review> all = service.getAllReviewsByProduct(id);

    List<Review> reviews = new ArrayList<>();
    for (int i = 0;i <= all.size() -1 ;i++){
        if (i < 2){
            reviews.add(all.get(i));
        }
    }
    return reviews.size() > 0 ?
            Util.checkStatus(HttpStatus.OK, "Đã tìm được "+ reviews.size()+" đánh giá " , reviews) :
            Util.checkStatus(HttpStatus.NOT_FOUND, "Không có đánh giá nào", "");
}
//===================================================================================================================
@GetMapping("/seller/{token}/{status}")
ResponseEntity<ResponseOpject> findBystatusBySeller(
        @PathVariable String status,
        @PathVariable String token){

        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null) {
            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
        }

    List<Product> products ;
    switch (status){
        case "all":
            products = service.findAllByUser(userToken);
            break;
        case "banchay":
            products = service.findBySold();
            break;
        case "giaCaoDenThap":
            products = service.Cao();
            break;
        case "giaThapDenCao":
            products = service.Thap();
            break;
        default:
            products = new ArrayList<>();
    }
    return products.size() > 0 ?
            Util.checkStatus(HttpStatus.OK, "Đã tìm được " + products.size() + " sản phẩm", products) :
            Util.checkStatus(HttpStatus.NOT_FOUND, "Không có sản phẩm", "");
}

//===================================================================================================================

    @PostMapping("/add/{token}")
    ResponseEntity<ResponseOpject> addProduct(
            @PathVariable String token,
            @RequestBody Product productBody){

        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null) {
            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
        }
        Product product = new Product();


        product.images = productBody.images;
        service.save(product);
        return Util.checkStatus(HttpStatus.OK, "Tạo Sản Phẩm Thành Công", product);

    }
//===================================================================================================================
@GetMapping("/{id}")
ResponseEntity<ResponseOpject> findbyID(@PathVariable Long id){
    Product product = service.findByID(id);
    return product != null ?
            Util.checkStatus(HttpStatus.OK, "Đã tìm được sản phẩm " , product) :
            Util.checkStatus(HttpStatus.NOT_FOUND, "Không có sản phẩm", "");
}
    @GetMapping("/edit/{id}/{token}")
ResponseEntity<ResponseOpject> getProduct(
        @PathVariable String token,
        @PathVariable int id

){
    User userToken = tokenService.getUserFromToken(token);
    if (userToken == null) {
        return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
    }

        Product product = service.findByID(id);
    if (product == null) {
        return Util.checkStatus(HttpStatus.NOT_FOUND, "Sản Phẩm Không Phù Hợp", null);
    }
    return Util.checkStatus(HttpStatus.OK, "Tìm Sản Phẩm Thành Công", product);
}
//===================================================================================================================
@PutMapping("/edit/{id}/{token}")
ResponseEntity<ResponseOpject> addProduct(
        @PathVariable String token,
        @PathVariable int id,
        @RequestBody Product productBody

){
    User userToken = tokenService.getUserFromToken(token);
    if (userToken == null) {
        return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
    }

    Product product = service.findByID(id);
    if (product == null) {
        return Util.checkStatus(HttpStatus.NOT_FOUND, "Sản Phẩm Không Phù Hợp", null);
    }
    String oldIMG = product.images;
    return Util.checkStatus(HttpStatus.BAD_REQUEST, "Chỉ được giảm từ 10 tới 50%", null);

}//===================================================================================================================

}
