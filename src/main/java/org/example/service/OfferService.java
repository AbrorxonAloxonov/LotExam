package org.example.service;

import org.example.model.Message;
import org.example.model.User;

import java.sql.SQLException;

public interface OfferService {
    boolean check(Integer lot_id);
    Message saveOffer(User user,Integer lot_id,Double price) throws SQLException;
    Message close(Integer lot_id);
    boolean checkPrice(Integer lot_id,Double price);

    Message getAllOfferId(int offer_id);

    Message getAll();
}
