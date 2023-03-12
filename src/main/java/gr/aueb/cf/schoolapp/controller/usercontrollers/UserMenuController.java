package gr.aueb.cf.schoolapp.controller.usercontrollers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/schoolapp/usersmenu")
public class UserMenuController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.getRequestDispatcher("/schoolapp/static/usertemplates/usersmenu.jsp").forward(request, response);
    }

}
