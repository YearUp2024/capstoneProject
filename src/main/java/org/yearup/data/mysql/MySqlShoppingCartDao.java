package org.yearup.data.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {
    private static final Logger log = LoggerFactory.getLogger(MySqlShoppingCartDao.class);
    private ProductDao productDao;

    @Autowired
    public MySqlShoppingCartDao(DataSource dataSource, ProductDao productDao) {
        super(dataSource);
        this.productDao = productDao;
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        ShoppingCart shoppingCart = new ShoppingCart();

        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM easyshop.shopping_cart WHERE user_id = ?;");
        ){
            preparedStatement.setInt(1, userId);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    resultSet.getInt("user_id");
                    int productId = resultSet.getInt("product_id");
                    int quantity = resultSet.getInt("quantity");
                    shoppingCart.add(new ShoppingCartItem(productDao.getById(productId), quantity));
                }
            }
        }catch(SQLException e){
            log.error("Error occurred while getting shopping cart", e);
        }
        log.info("Retrieved shopping cart for user: {}", userId);
        return shoppingCart;
    }

    @Override
    public ShoppingCart addProductToCart(int userId, int productId) {
        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO easyshop.shopping_cart (user_id, product_id, quantity) VALUES(?, ?, 1);");
        ){
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);
            preparedStatement.executeUpdate();

            log.info("Added product to cart for user: {}", userId);
            return getByUserId(userId);
        }catch(SQLException e){
            log.error("An error occurred while trying to add the product to the cart.", e);
            return null;
        }
    }

    @Override
    public ShoppingCart updateProductInCart(int userId, int productId, int quantity) {
        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE easyshop.shopping_cart \n" +
                        "SET quantity = ? \n" +
                        "WHERE user_id = ? AND product_id = ?;");
        ){
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, productId);
            preparedStatement.executeUpdate();

            log.info("Updated product in cart for user: {}", userId);
            return getByUserId(userId);
        }catch(SQLException e){
            log.error("An error occurred while trying to product the product to the cart.", e);
            return null;
        }
    }

    @Override
    public ShoppingCart clearCart(int userId) {
        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "DELETE FROM easyshop.shopping_cart\n" +
                        "WHERE user_id = ?; ");
        ){
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

            log.info("Cleared cart for user: {}", userId);
            return new ShoppingCart();
        }catch(SQLException e){
            log.error("There was an error trying to clear cart, e");
            return null;
        }
    }
}
