package gr.aueb.cf.schoolapp.controller.usercontrollers;

import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.dao.UserDAOImpl;
import gr.aueb.cf.schoolapp.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolapp.dto.UserDTO;
import gr.aueb.cf.schoolapp.service.IUserService;
import gr.aueb.cf.schoolapp.service.UserServiceImpl;
import gr.aueb.cf.schoolapp.service.exceptions.UserNotFoundException;
import gr.aueb.cf.schoolapp.validation.Validator;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "UpdateUserController", value = "/schoolapp/userupdate")
public class UpdateUserController extends HttpServlet {
    private static final  long serialVersion = 1L;
    IUserDAO userDAO = new UserDAOImpl();
    IUserService userService = new UserServiceImpl(userDAO);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/schoolapp/static/usertemplates/userupdate.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        int id = Integer.parseInt(request.getParameter("id").trim());
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        String message = "";
        request.setAttribute("updatedUser", userDTO);

        try {
            String error = Validator.userValidate(userDTO);
            if (!error.equals("")) {
                request.setAttribute("error", error);
                request.getRequestDispatcher("/schoolapp/static/usertemplates/usersmenu.jsp")
                        .forward(request, response);
            }
            userService.updateUser(userDTO);
            request.setAttribute("user",userDTO);
            request.getRequestDispatcher("/schoolapp/static/usertemplates/userupdated.jsp")
                    .forward(request, response);
        } catch (UserDAOException | UserNotFoundException e) {
            message = e.getMessage();
            request.setAttribute("isError", true);
            request.setAttribute("user", userDTO);
            request.getRequestDispatcher("/schoolapp/static/usertemplates/userupdated.jsp").forward(request, response);
        }
    }

}
