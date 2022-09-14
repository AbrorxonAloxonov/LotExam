package org.example.component;

import com.google.gson.Gson;
import org.example.dao.UserDao;
import org.example.dao.imp.UserDaoImpl;
import org.example.model.Message;
import org.example.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@WebFilter("/*")
public class AuthFilter implements Filter {
    private final Gson gson = new Gson();
    private final UserDao userDao = UserDaoImpl.getUserDao();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType("application/json");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        if (uri.startsWith("/login")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String key = request.getHeader("key");
            if (key == null || key.isEmpty() || key.isBlank()) {
                response.getWriter().print(gson.toJson(new Message(401, "Unauthorized", null)));
                response.getWriter().close();
            }else {
                String decode = new String(Base64.getDecoder().decode(key.getBytes()));
                String[] split = decode.split("&");
                if (split.length==3){
                    Optional<User> optionalUser = userDao.findByUsernameAndPassword(split[0], split[1]);
                    if (optionalUser.isPresent()){
                        HttpSession session = request.getSession();
                        session.setAttribute("user", optionalUser.get());
                        if (request.getMethod().equalsIgnoreCase("Get") || request.getRequestURI().contains("/offer")){
                            filterChain.doFilter(servletRequest,servletResponse);
                        } else if (checkUri(request.getRequestURI()) &&
                                (request.getMethod().equalsIgnoreCase("Post") || request.getMethod().equalsIgnoreCase("Put"))
                                && optionalUser.get().isRole()){
                            filterChain.doFilter(servletRequest,servletResponse);
                        } else {
                            response.getWriter().print(gson.toJson(new Message(400,"Faqat admin kirishi mumkin !",null)));
                        }
                    }else {
                        response.getWriter().print(gson.toJson(new Message(401, "Unauthorized", null)));
                        response.getWriter().close();
                    }
                }else {
                    response.getWriter().print(gson.toJson(new Message(401, "Unauthorized", null)));
                    response.getWriter().close();
                }
            }
        }
    }
    public boolean checkUri(String uri){
        switch (uri){
            case "/lot":
            case "/close":
                return true;
            default:
                return false;

        }
    }
}
