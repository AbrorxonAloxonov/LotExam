package org.example.service.imp;

import org.example.dao.LotDao;
import org.example.dao.OfferDao;
import org.example.dao.imp.LotDaoImpl;
import org.example.dao.imp.OfferDaoImpl;
import org.example.model.Lot;
import org.example.model.Message;
import org.example.model.Offer;
import org.example.service.LotService;

import java.sql.SQLException;
import java.util.List;

public class LotServiceImpl implements LotService {
    public static LotService lotService;
    public final OfferDao offerDao = OfferDaoImpl.getOfferDao();
    private final LotDao lotDao = LotDaoImpl.getLotDao();

    public static LotService getLotService(){
        if (lotService==null){
            lotService = new LotServiceImpl();
        }
        return lotService;
    }

    @Override
    public boolean checkAdmin(Integer role) {
        return lotDao.findByRole(role);
    }

    @Override
    public Message saveLot(Integer user_id, String start_price, String desc, String model) throws SQLException {
        Lot lot = new Lot(user_id,model,desc,Double.parseDouble(start_price));
        boolean b = lotDao.saveDB(lot);
        if (b){
            return new Message(200,"Ok",lot);
        }else {
            return new Message(0,"not saved",null);
        }
    }

    @Override
    public boolean checkUser(Integer id) {
        return lotDao.checkUser(id);
    }

    @Override
    public Message getAll() {
        List<Lot> lotList = lotDao.getAll();
        if(!lotList.isEmpty()){
            return new Message(200,"ok",lotList);
        }
        return new Message(0,"Error lot table",null);
    }

    @Override
    public Message updateLot(Integer user_id, Double start_price,String desc,String model,Integer lot_id) {
        boolean v = lotDao.updateLot(user_id,start_price,desc,model,lot_id);
        if (v){
            return new Message(200,"updated",lot_id);
        }
        return new Message(400,"not updated",null);
    }

    @Override
    public Message getAllId(Integer lot_id) {
        List<Lot> list = lotDao.getAllId(lot_id);
        if (!list.isEmpty()){
            return new Message(200,"ok",list);
        }
        return new Message(400,"Bu id da lot yo'q",null);
    }

    @Override
    public Message allOfferByLotId(int lot_id) {
        List<Offer> offers = offerDao.allOfferLotId(lot_id);
        if (!offers.isEmpty()){
            return new Message(200,"ok",offers);
        }
        return new Message(400,"Bu lot yoki uning offerlari mavjud emas!",null);
    }

}
