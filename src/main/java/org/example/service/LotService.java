package org.example.service;

import org.example.model.Lot;
import org.example.model.Message;
import org.example.model.User;

import java.sql.SQLException;

public interface LotService {
    boolean checkAdmin(Integer role);
    Message saveLot(Integer user_id, String start_price,String desc,String model) throws SQLException;
    boolean checkUser(Integer id);
    Message getAll();
    Message updateLot(Integer user_id, Double start_price,String desc,String model,Integer lot_id);

    Message getAllId(Integer lot_id);

    Message allOfferByLotId(int lot_id);
}
