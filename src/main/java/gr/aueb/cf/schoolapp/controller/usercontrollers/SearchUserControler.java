package gr.aueb.cf.schoolapp.controller.usercontrollers;

import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.dao.UserDAOImpl;
import gr.aueb.cf.schoolapp.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolapp.dto.UserDTO;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.service.IUserService;
import gr.aueb.cf.schoolapp.service.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchUserControler", value = "/schoolapp/searchuser")
public class SearchUserControler extends HttpServlet {
    private static final long serialVersionUID = 1L;
    IUserDAO userDAO = new UserDAOImpl();
    IUserService userService = new UserServiceImpl(userDAO);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/schoolapp/static/usertemplates/usersmenu.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String username = request.getParameter("username").trim();
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        String message = "";

        try {
            List<User> users = userService.getUserByUsername(userDTO.getUsername());
            if (users.size() == 0) {
                request.setAttribute("usersNotFound", true);
                request.getRequestDispatcher("/schoolapp/static/usertemplates/usersmenu.jsp").forward(request, response);
            }

            request.setAttribute("users", users);
            request.getRequestDispatcher("/schoolapp/static/usertemplates/users.jsp").forward(request, response);
        } catch (UserDAOException e) {
            message = e.getMessage();
            request.setAttribute("isError", true);
            request.setAttribute("Error Message", message);
            request.getRequestDispatcher("/schoolapp/static/usertemplates/usersmenu.jsp").forward(request, response);

        }
    }
}
