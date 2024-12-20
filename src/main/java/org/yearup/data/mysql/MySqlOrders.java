//package org.yearup.data.mysql;
//
//import org.apache.tomcat.jni.Address;
//import org.springframework.stereotype.Component;
//import org.yearup.data.OrdersDao;
//import org.yearup.models.Order;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//@Component
//public class MySqlOrders extends MySqlDaoBase implements OrdersDao {
//    public MySqlOrders(DataSource dataSource) {
//        super(dataSource);
//    }
//
//    @Override
//    public Order getOrderById(int id) {
//        String sql = "SELECT * \n" +
//                "FROM easyshop.orders\n" +
//                "WHERE order_id = ?; ";
//
//        try(
//                Connection connection = getConnection();
//                PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        ){
//            preparedStatement.setInt(1, id);
//
//
//
//        }catch(SQLException e){
//            e.printStackTrace();
//        }
//    }
//
//    private Order mapRow(ResultSet resultSet) {
//        try {
//            Order order = new Order();
//            order.setOrderId(resultSet.getInt("order_id"));
//            order.setUserId(resultSet.getInt("user_id"));
//            order.setOrderDate(resultSet.getDate("date"));
//            order.setShippingAddress(new Address(resultSet.getString("address"), resultSet.getString("city"), resultSet.getString("state"), resultSet.getString("zip"))));
//            order.setShippingAmount(resultSet.getBigDecimal("shipping_amount"));
//            return order;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
