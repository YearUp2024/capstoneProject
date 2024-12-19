package org.yearup.data.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yearup.models.Profile;
import org.yearup.data.ProfileDao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlProfileDao extends MySqlDaoBase implements ProfileDao {
    private static final Logger log = LoggerFactory.getLogger(MySqlProfileDao.class);

    public MySqlProfileDao(DataSource dataSource){
        super(dataSource);
    }

    @Override
    public Profile create(Profile profile) {
        String sql = "INSERT INTO profiles (user_id, first_name, last_name, phone, email, address, city, state, zip) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, profile.getUserId());
            ps.setString(2, profile.getFirstName());
            ps.setString(3, profile.getLastName());
            ps.setString(4, profile.getPhone());
            ps.setString(5, profile.getEmail());
            ps.setString(6, profile.getAddress());
            ps.setString(7, profile.getCity());
            ps.setString(8, profile.getState());
            ps.setString(9, profile.getZip());
            ps.executeUpdate();

            log.info("Created profile for {}", profile);
            return profile;
        }
        catch (SQLException e)
        {
            log.error("There was an error while trying to create a profile", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Profile getByUserId(int userId){
        String sql = "SELECT * FROM easyshop.profiles WHERE user_id = ?;";

        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, userId);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return mapToRow(resultSet);
                }
            }
        }catch(SQLException e){
            log.error("There was an error while trying to retrieve profile for user: " + userId, e);
            throw new RuntimeException("An error occurred", e);
        }
        return null;
    }

    @Override
    public List<Profile> getAllProfile() {
        List<Profile> profile = new ArrayList<>();
        String sql = "SELECT * FROM easyshop.profiles;";

        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
        ){
            while(resultSet.next()){
                profile.add(mapToRow(resultSet));
            }
            return profile;
        }catch(SQLException e){
            log.error("Error while trying to retrieve all profiles", e);
            throw new RuntimeException("Error while trying to retrieve data", e);
        }
    }

    @Override
    public Profile updateProfile(Profile profile) {
        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE easyshop.profiles \n" +
                        "SET first_name = ?, last_name = ?, phone = ?, email = ?, address = ?, city = ?, state = ?, zip = ?\n" +
                        "WHERE user_id = ?; ");
        ){
            preparedStatement.setString(1, profile.getFirstName());
            preparedStatement.setString(2, profile.getLastName());
            preparedStatement.setString(3, profile.getPhone());
            preparedStatement.setString(4, profile.getEmail());
            preparedStatement.setString(5, profile.getAddress());
            preparedStatement.setString(6, profile.getCity());
            preparedStatement.setString(7, profile.getState());
            preparedStatement.setString(8, profile.getZip());
            preparedStatement.setInt(9, profile.getUserId());

            int rows = preparedStatement.executeUpdate();
            if(rows > 0){
                log.info("Profile updated: {}", profile);
                return profile;
            }else{
                return null;
            }
        }catch(SQLException e){
            log.error("There was an error while trying to update profile ", e);
            e.printStackTrace();
        }
        return null;
    }

    public Profile mapToRow(ResultSet rs) throws SQLException {
        Profile profile = new Profile();
        profile.setUserId(rs.getInt("user_id"));
        profile.setFirstName(rs.getString("first_name"));
        profile.setLastName(rs.getString("last_name"));
        profile.setPhone(rs.getString("phone"));
        profile.setEmail(rs.getString("email"));
        profile.setAddress(rs.getString("address"));
        profile.setCity(rs.getString("city"));
        profile.setState(rs.getString("state"));
        profile.setZip(rs.getString("zip"));
        return profile;
    }
}
