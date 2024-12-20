package org.yearup.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;
import java.sql.Connection;
import java.util.Optional;

// convert this class to a REST controller
// only logged in users should have access to these actions
@RestController
@RequestMapping("/cart")
@CrossOrigin
public class ShoppingCartController
{
    private static final Logger log = LoggerFactory.getLogger(ShoppingCartController.class);
    // a shopping cart requires
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProductDao productDao;

    @Autowired
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    // each method in this controller requires a Principal object as a parameter
    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public Optional<ShoppingCart> getCart(Principal principal) {
        try{
            // get the currently logged in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // use the shoppingcartDao to get all items in the cart and return the cart
            return Optional.of(shoppingCartDao.getByUserId(userId));
        }
        catch(Exception e){
            log.error("Error getting cart", e);
        }
        return null;
    }

    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be added
    @PostMapping("/products/{productId}")
    @PreAuthorize("isAuthenticated()")
   // @PreAuthorize("permitAll()")
   /*@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")*/
    public ResponseEntity<ShoppingCart> addProductToCart(Principal principal, @PathVariable int productId){
        try{
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();
            ShoppingCart result = shoppingCartDao.addProductToCart(userId, productId);
            return ResponseEntity.ok(result);
        }catch(Exception e){
            log.error("Error adding product to cart", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated
    @PutMapping("/products/{productId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ShoppingCart> updateCartItem(Principal principal, @PathVariable int productId, @RequestBody ShoppingCartItem quantity){
        try{
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            ShoppingCart result = shoppingCartDao.updateProductInCart(userId, productId, quantity.getQuantity());
            return ResponseEntity.ok(result);
        }catch(Exception e){
            log.error("Error updating product in cart", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart
    @DeleteMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ShoppingCart> deleteCartItem(Principal principal){
        try{
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            return ResponseEntity.ok(shoppingCartDao.clearCart(userId));
        }catch(Exception e){
            log.error("Error deleting shopping cart", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
