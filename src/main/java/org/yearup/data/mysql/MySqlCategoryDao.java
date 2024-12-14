package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao {
    private DataSource dataSource;

    @Autowired
    public MySqlCategoryDao(DataSource dataSource){
        super(dataSource);
        this.dataSource = dataSource;
    }

    @Override
    public List<Category> getAllCategories()
    {
        List<Category> categories = new ArrayList<>();

        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM easyshop.categories;");
                ResultSet resultSet = preparedStatement.executeQuery();
        ){
            while(resultSet.next()){
                int categoryId = resultSet.getInt(1);
                String categoryName = resultSet.getString(2);
                String categoryDescription = resultSet.getString(3);

                categories.add(new Category(categoryId, categoryName, categoryDescription));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category getById(int categoryId)
    {
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * \n" +
                        "FROM easyshop.categories\n" +
                        "WHERE category_id = ?; ");
        ){
            preparedStatement.setInt(1, categoryId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    int catId = resultSet.getInt(1);
                    String categoryName = resultSet.getString(2);
                    String categoryDescription = resultSet.getString(3);

                    return new Category(catId, categoryName, categoryDescription);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Category create(Category category)
    {
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO easyshop.categories(name, description)\n" +
                        "VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS)
        ){
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());

            int rowsEffected = preparedStatement.executeUpdate();
            if(rowsEffected > 0){
                ResultSet generatedKey = preparedStatement.getGeneratedKeys();
                if(generatedKey.next()){
                    int categoryId = generatedKey.getInt(1);
                    return getById(categoryId);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(int categoryId, Category category)
    {
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE easyshop.categories\n" +
                        "SET name = ?, description = ?\n" +
                        "WHERE category_id = ?;");
        ){
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setInt(3, categoryId);

            preparedStatement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int categoryId)
    {
        // delete category
    }

    private Category mapRow(ResultSet row) throws SQLException
    {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category()
        {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}
