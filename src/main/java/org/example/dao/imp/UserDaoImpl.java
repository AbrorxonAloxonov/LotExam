package org.example.dao.imp;

import org.example.dao.PostgresConnection;
import org.example.dao.UserDao;
import org.example.model.User;

import javax.swing.text.html.Option;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static UserDao userDao;
    private final Connection con = PostgresConnection.getInstance();

    public static UserDao getUserDao(){
        if (userDao==null){
            userDao = new UserDaoImpl();
        }
        return userDao;
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        try {
            PreparedStatement statement = con.prepareStatement("select id,name,last_name,phone_number,role from user_lot where username = ? and password = ?");
            statement.setString(1,username);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Integer id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String last_name = resultSet.getString(3);
                String phone_number = resultSet.getString(4);
                boolean role = resultSet.getBoolean(5);
                return Optional.of(new User(id,name,last_name,username,phone_number,role));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public User findByUserId(Integer id) {
        User user = new User();
        try {
            PreparedStatement statement =
                    con.prepareStatement("select id,name,last_name,username,phone_number from user_lot where id = ?");
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
             if (resultSet.next()){
                Integer id_user = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String last_name = resultSet.getString(3);
                String username = resultSet.getString(4);
                String phone_number = resultSet.getString(5);
                 return new User(id_user,name,last_name,username,phone_number);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;

    }

    @Override
    public boolean saveDB(User user) throws SQLException {
        return false;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }
}
