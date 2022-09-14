package org.example.controller;

import com.google.gson.Gson;
import org.example.dao.OfferDao;
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

@WebServlet("/close")
public class CloseController extends HttpServlet {
    private final Gson gson = new Gson();
    private final LotService lotService = LotServiceImpl.getLotService();
    private final OfferService offerService = OfferServiceImpl.getOfferService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        //User user = (User) session.getAttribute("user");
            String lot_id = req.getParameter("lot_id");
            boolean n = offerService.check(Integer.valueOf(lot_id));
            if(n){
                Message message = offerService.close(Integer.valueOf(lot_id));
                String json = gson.toJson(message);
                resp.getWriter().print(json);
                resp.getWriter().close();
            }else {
                Message message = new Message(400,"Bundan lot_id mavjud emas yoki allaqachon tugatilgan",null);
                String json = gson.toJson(message);
                resp.getWriter().print(json);
                resp.getWriter().close();
            }
        }
}
