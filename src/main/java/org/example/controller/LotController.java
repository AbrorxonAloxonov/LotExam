package org.example.controller;

import com.google.gson.Gson;
import org.example.dao.LotDao;
import org.example.model.Lot;
import org.example.model.Message;
import org.example.model.User;
import org.example.service.LotService;
import org.example.service.OfferService;
import org.example.service.imp.LotServiceImpl;
import org.example.service.imp.OfferServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/lot/*")
public class LotController extends HttpServlet {
    private final Gson gson = new Gson();
    private final LotService lotService = LotServiceImpl.getLotService();
    private final OfferService offerService = OfferServiceImpl.getOfferService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
           String user_id = req.getParameter("user_id");
           boolean s = lotService.checkUser(Integer.valueOf(user_id));
           if (s){
               String model = req.getParameter("model");
               String desc = req.getParameter("description");
               String start_price = req.getParameter("start_price");
               try {
                   Message message = lotService.saveLot(Integer.valueOf(user_id), start_price, desc, model);
                   String json = gson.toJson(message);
                   resp.getWriter().print(json);
                   resp.getWriter().close();
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }else {
               Message message = new Message(0,"Bu id raqamli user mavjud emas",null);
               String json = gson.toJson(message);
               resp.getWriter().print(json);
               resp.getWriter().close();
           }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(req.getRequestURI().contains("/byLot")) {
            String lot_id = req.getParameter("id");
            Message message = lotService.allOfferByLotId(Integer.parseInt(lot_id));
            String json = gson.toJson(message);
            resp.getWriter().print(json);
            resp.getWriter().close();
        } else if (req.getRequestURI().contains("/all")) {
            Message message = lotService.getAll();
            String json = gson.toJson(message);
            resp.getWriter().print(json);
            resp.getWriter().close();
        } else {
            String id = req.getParameter("id");
            Message message = lotService.getAllId(Integer.valueOf(id));
            String json = gson.toJson(message);
            resp.getWriter().print(json);
            resp.getWriter().close();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession  session = req.getSession(false);
        Message messages;
        User user = (User) session.getAttribute("user");
        boolean b = lotService.checkAdmin(user.getId());
        if (b){
            String lot_id = req.getParameter("lot_id");
            boolean s = offerService.check(Integer.valueOf(lot_id));
            if(s){
                String user_id = req.getParameter("user_id");
                String model = req.getParameter("model");
                String desc = req.getParameter("description");
                String start_price = req.getParameter("start_price");
                messages = lotService.updateLot(Integer.valueOf(user_id),Double.parseDouble(start_price), desc, model, Integer.valueOf(lot_id));
                String json = gson.toJson(messages);
                resp.getWriter().print(json);
                resp.getWriter().close();
            }else {
                Message message = new Message(0,"Bunday id dagi lot yo'q yoki tugatilgan",null);
                String json = gson.toJson(message);
                resp.getWriter().print(json);
                resp.getWriter().close();
            }
        }else {
            Message message = new Message(0,"Siz ma'lumot o'zgartirish imkoniyatingiz yo'q",null);
            String json = gson.toJson(message);
            resp.getWriter().print(json);
            resp.getWriter().close();
        }
    }
}
