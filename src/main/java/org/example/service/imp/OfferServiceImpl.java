package org.example.service.imp;

import org.example.dao.OfferDao;
import org.example.dao.imp.OfferDaoImpl;
import org.example.model.Message;
import org.example.model.Offer;
import org.example.model.User;
import org.example.service.OfferService;

import java.sql.SQLException;
import java.util.List;

public class OfferServiceImpl implements OfferService {
    private static OfferService offerService;
    private final OfferDao offerDao = OfferDaoImpl.getOfferDao();
    public static OfferService getOfferService(){
        if (offerService == null){
            offerService = new OfferServiceImpl();
        }
        return offerService;
    }
    @Override
    public boolean check(Integer lot_id) {
        return offerDao.checkLot(lot_id);
    }

    @Override
    public Message saveOffer(User user, Integer lot_id, Double price) throws SQLException {
        Offer offer = new Offer(lot_id,user,price);
        boolean b = offerDao.saveDB(offer);
        if (b){
            return new Message(0,"ok",offer);
        }
        return new Message(1,"not saved",null);
    }

    @Override
    public Message close(Integer lot_id) {
        boolean b = offerDao.close(lot_id);
        if (b){
            Offer offer = offerDao.maxPrice(lot_id);
            return new Message(200,"ok",offer);
        }
        return new Message(0,"Lot_id mavjud emas",null);
    }

    @Override
    public boolean checkPrice(Integer lot_id,Double price) {
        return offerDao.checkPrice(lot_id,price);
    }

    @Override
    public Message getAllOfferId(int offer_id) {
        List<Offer> offers = offerDao.getAllOfferId(offer_id);
        if (!offers.isEmpty()){
            return new Message(200,"ok",offers);
        }
        return new Message(400,"Bu id da offer mavjud emas!",null);
    }

    @Override
    public Message getAll() {
        List<Offer> offers = offerDao.getAll();
        if (!offers.isEmpty()){
            return new Message(200,"ok",offers);
        }
        return new Message(400,"Offer mavjud emas!",null);
    }
}
