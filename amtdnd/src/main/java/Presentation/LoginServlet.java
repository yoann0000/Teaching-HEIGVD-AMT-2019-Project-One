package Presentation;

import Model.Adventurer;
import business.AuthenticationService;
import datastore.exception.KeyNotFoundException;
import integration.AdventurerDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String user = request.getParameter("username");
        String password = request.getParameter("password");
        Adventurer adventurer = null;
        String pwdHash = null;
        try {
            adventurer = new AdventurerDAO().findById(user);
            pwdHash = adventurer.getPassword();
        } catch (KeyNotFoundException e) {
            e.printStackTrace();
        }

        if(pwdHash != null && new AuthenticationService().checkPassword(password, pwdHash)) {
            request.getSession().setAttribute("adventurer", adventurer);
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            request.setAttribute("error", "Invalid userName or Password");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        }
    }
}
