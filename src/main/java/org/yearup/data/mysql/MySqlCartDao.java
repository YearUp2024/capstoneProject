package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlCartDao extends MySqlDaoBase implements ShoppingCartDao {
    private DataSource dataSource;
    private ProductDao productDao;

    @Autowired
    public MySqlCartDao(DataSource dataSource, ProductDao productDao) {
        super(dataSource);
        this.dataSource = dataSource;
        this.productDao = productDao;
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        ShoppingCart shoppingCart = new ShoppingCart();

        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM easyshop.shopping_cart WHERE user_id = ?;");
        ){
            preparedStatement.setInt(1, userId);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    int productId = resultSet.getInt(1);
                    int quantity = resultSet.getInt(2);

                    Product product = productDao.getById(productId);
                    ShoppingCartItem shoppingCartItem = new ShoppingCartItem(product, quantity, BigDecimal.ZERO);
                    shoppingCart.add(shoppingCartItem);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return shoppingCart;
    }
}
