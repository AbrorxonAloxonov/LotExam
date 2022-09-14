package org.example.dao.imp;

import org.example.dao.OfferDao;
import org.example.dao.PostgresConnection;
import org.example.dao.UserDao;
import org.example.model.Offer;
import org.example.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OfferDaoImpl implements OfferDao {
    private final UserDao userDao = UserDaoImpl.getUserDao();
    Connection con = PostgresConnection.getInstance();
    private static OfferDao offerDao;

    public static OfferDao getOfferDao() {
        if (offerDao == null) {
            offerDao = new OfferDaoImpl();
        }
        return offerDao;
    }

    @Override
    public boolean saveDB(Offer offer)  {
        try {
            PreparedStatement statement = statement = con.prepareStatement("insert into offer(lot_id,user_id,price) values (?,?,?)");
            statement.setInt(1, offer.getLot_id());
            statement.setInt(2, offer.getUser().getId());
            statement.setDouble(3, offer.getPrice());
            int i = statement.executeUpdate();
            return i > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Offer> getAll() {
        List<Offer> offerList = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement("select id,lot_id,user_id,price,date from offer order by id");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Integer id = resultSet.getInt(1);
                Integer lot_id = resultSet.getInt(2);
                Integer user_id = resultSet.getInt(3);
                Double price = resultSet.getDouble(4);
                String date = resultSet.getString(5);
                User user = userDao.findByUserId(user_id);
                offerList.add(new Offer(id,lot_id,user,price,date));
            }
            return offerList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Offer> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    public boolean update(Offer offer) {
        return false;
    }

    @Override
    public boolean checkLot(Integer lot_id) {
        try {
            PreparedStatement statement = con.prepareStatement("select id from lot where id = ? and sold = true");
            statement.setInt(1, lot_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean close(Integer lot_id) {
        try {
            PreparedStatement statement = con.prepareStatement("update lot set sold = false where id = ?");
            statement.setInt(1,lot_id);
            int i = statement.executeUpdate();
            return i>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Offer maxPrice(Integer lot_id) {
        try {
            PreparedStatement statement = con.prepareStatement("select id,user_id,price,date from offer where price = (select max(price) from offer where lot_id = ?)");
            statement.setInt(1,lot_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                Integer id = resultSet.getInt(1);
                Integer user_id = resultSet.getInt(2);
                Double price = resultSet.getDouble(3);
                String date = resultSet.getString(4);
                User user = userDao.findByUserId(user_id);
                return new Offer(id,lot_id,user,price,date);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean checkPrice(Integer lot_id,Double price) {
        try {
            PreparedStatement statement = con.prepareStatement("select * from lot where id = ? and start_price <= ?");
            statement.setInt(1,lot_id);
            statement.setDouble(2,price);
            ResultSet resultSet =  statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Offer> getAllOfferId(int offer_id) {
        List<Offer> offerList = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement("select id,lot_id,user_id,price,date from offer where id = ?");
            statement.setInt(1,offer_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                Integer id = resultSet.getInt(1);
                Integer lot_id = resultSet.getInt(2);
                Integer user_id = resultSet.getInt(3);
                Double price = resultSet.getDouble(4);
                String date = resultSet.getString(5);
                User user = userDao.findByUserId(user_id);
                offerList.add(new Offer(id,lot_id,user,price,date));
            }
            return offerList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Offer> allOfferLotId(int lot_id) {
        List<Offer> offerList = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement(" select id,user_id,price,date from offer where lot_id = ?");
            statement.setInt(1,lot_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Integer id = resultSet.getInt(1);
                Integer user_id = resultSet.getInt(2);
                Double price = resultSet.getDouble(3);
                String date = resultSet.getString(4);
                User user = userDao.findByUserId(user_id);
                offerList.add(new Offer(id,lot_id,user,price,date));
            }
            return offerList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
