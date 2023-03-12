package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.authentication.AuthenticationProvider;
import gr.aueb.cf.schoolapp.dto.UserDTO;
import gr.aueb.cf.schoolapp.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String adminPassword = System.getenv("TS_ADMIN_PASSWORD");
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(email);
        userDTO.setPassword(password);

        if ((email.equals("admin@gmail.com")) && (password.equals("admin"))) {
            HttpSession session = request.getSession(true);
            session.setMaxInactiveInterval(60 * 15);

            Cookie cookie = new Cookie("JSESSIONID", session.getId());
            cookie.setMaxAge(session.getMaxInactiveInterval());
            response.addCookie(cookie);

            response.sendRedirect(request.getContextPath() + "/schoolapp/usersmenu");
        } else {
            User user = AuthenticationProvider.authenticate(userDTO);
            if (user == null) {
                response.sendRedirect((request.getContextPath() + "/login"));
            }

            HttpSession session = request.getSession(true);
            assert user != null;


            session.setMaxInactiveInterval(60 * 15);

            Cookie cookie = new Cookie("JSESSIONID", session.getId());
            cookie.setMaxAge(session.getMaxInactiveInterval());
            response.addCookie(cookie);

            session.setAttribute("username", user.getUsername());
            response.sendRedirect(request.getContextPath() + "/schoolapp/menu");
        }


    }
}
