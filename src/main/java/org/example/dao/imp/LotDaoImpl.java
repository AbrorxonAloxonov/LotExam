package org.example.dao.imp;

import org.example.dao.LotDao;
import org.example.dao.PostgresConnection;
import org.example.dao.UserDao;
import org.example.model.Lot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LotDaoImpl implements LotDao {
    Connection con = PostgresConnection.getInstance();
    private static LotDao lotDao;
    public static LotDao getLotDao(){
        if (lotDao==null){
            lotDao = new LotDaoImpl();
        }
        return lotDao;
    }
    @Override
    public boolean saveDB(Lot lot){
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement("insert into lot(model,description,start_price,user_id) values (?,?,?,?)");
            statement.setString(1,lot.getModel());
            statement.setString(2,lot.getDescription());
            statement.setDouble(3,lot.getStart_price());
            statement.setInt(4,lot.getId());
            int i = statement.executeUpdate();
            return i >0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List getAll() {
        try {
            List<Lot> lotList = new ArrayList<>();
            PreparedStatement statement = con.prepareStatement("select * from lot order by id");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Integer id = resultSet.getInt(1);
                String model = resultSet.getString(2);
                String desc = resultSet.getString(3);
                Double price = resultSet.getDouble(4);
                String date = resultSet.getString(5);
                boolean sold = resultSet.getBoolean(7);
                lotList.add(new Lot(id,model,desc,price,date,sold));
            }
            return lotList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    public boolean update(Lot lot) {
        return false;
    }

    @Override
    public boolean findByRole(Integer id) {
        try {
            PreparedStatement statement = con.prepareStatement("select role from user_lot where id = ?");
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                boolean k = resultSet.getBoolean(1);
                return k;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean checkUser(Integer id) {
        try {
            PreparedStatement statement = con.prepareStatement("select id from user_lot where id = ?");
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateLot(Integer user_id, Double start_price, String desc, String model,Integer lot_id) {
        try {
            PreparedStatement statement = con.prepareStatement("update lot set model=?,description=?,start_price=?,user_id=? where id = ?");
            statement.setString(1,model);
            statement.setString(2,desc);
            statement.setDouble(3,start_price);
            statement.setInt(4,user_id);
            statement.setInt(5,lot_id);
            int v = statement.executeUpdate();
            return v>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Lot> getAllId(Integer lot_id) {
        try {
            List<Lot> lotList = new ArrayList<>();
            PreparedStatement statement = con.prepareStatement("select id,model,description,start_price,created_date,sold from lot where id = ?");
            statement.setInt(1,lot_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                lotList.add(new Lot(resultSet.getInt(1),resultSet.getString(2),
                        resultSet.getString(3),resultSet.getDouble(4),
                        resultSet.getString(5),resultSet.getBoolean(6)));
            }
            return lotList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
