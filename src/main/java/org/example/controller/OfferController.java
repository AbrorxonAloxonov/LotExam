package org.example.controller;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.example.dao.OfferDao;
import org.example.model.Message;
import org.example.model.User;
import org.example.service.OfferService;
import org.example.service.imp.OfferServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/offer/*")
public class OfferController extends HttpServlet {
    private final OfferService offerService = OfferServiceImpl.getOfferService();
    private final Gson gson = new Gson();

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        String lot_id = req.getParameter("lot_id");
        boolean b = offerService.check(Integer.valueOf(lot_id));
        if (b){
            String price = req.getParameter("price");
            boolean s = offerService.checkPrice(Integer.parseInt(lot_id), Double.valueOf(price));
            Message message;
            if(s){
                message = offerService.saveOffer(user, Integer.parseInt(lot_id), Double.valueOf(price));
                String json = gson.toJson(message);
                resp.getWriter().print(json);
            }else {
                message = new Message(0, "Siz kiritgan narx boshlang'ich narx kam ! Iltomos paslik qilmang !", null);
                String json = gson.toJson(message);
                resp.getWriter().print(json);
            }
            resp.getWriter().close();
        }else {
            Message message = new Message(0,"Bunday id dagi lot yo'q yoki tugatilgan",null);
            String json = gson.toJson(message);
            resp.getWriter().print(json);
            resp.getWriter().close();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(req.getRequestURI().contains("/all")){
            Message message = offerService.getAll();
            String json = gson.toJson(message);
            resp.getWriter().print(json);
            resp.getWriter().close();
        }else {
            String id = req.getParameter("id");
            Message message = offerService.getAllOfferId(Integer.parseInt(id));
            String json = gson.toJson(message);
            resp.getWriter().print(json);
            resp.getWriter().close();
        }

    }
}
