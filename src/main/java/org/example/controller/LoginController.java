package org.example.controller;

import com.google.gson.Gson;
import org.example.model.Message;
import org.example.service.UserService;
import org.example.service.imp.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private final UserService userService = UserServiceImpl.getUserService();
    private final Gson gson = new Gson();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Message message = userService.checkUser(username, password);
        resp.setContentType("application/json");
        resp.getWriter().print(gson.toJson(message));
        resp.getWriter().close();
    }
}
