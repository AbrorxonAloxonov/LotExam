package org.example.dao;

import org.example.model.Offer;

import java.util.List;

public interface OfferDao extends BasicDao<Offer>{
    boolean checkLot(Integer lot_id);
    boolean close(Integer lot_id);
    Offer maxPrice(Integer lot_id);
    boolean checkPrice(Integer lot_id ,Double price);

    List<Offer> getAllOfferId(int offer_id);

    List<Offer> allOfferLotId(int lot_id);

}
