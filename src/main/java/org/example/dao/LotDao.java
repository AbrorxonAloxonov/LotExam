package org.example.dao;

import org.example.model.Lot;
import org.example.model.User;

import java.util.List;
import java.util.Optional;

public interface LotDao extends BasicDao<Lot>{
    boolean findByRole(Integer id);
    boolean checkUser(Integer id);
    boolean updateLot(Integer user_id, Double start_price,String desc,String model,Integer lot_id);
    List<Lot> getAllId(Integer lot_id);
}
