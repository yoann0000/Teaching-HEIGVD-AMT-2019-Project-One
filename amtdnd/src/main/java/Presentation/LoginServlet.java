package Presentation;

import Model.Adventurer;
import business.AuthenticationService;
import datastore.exception.KeyNotFoundException;
import integration.IAdventurerDAO;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @EJB
    IAdventurerDAO adventurerDAO;

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
            adventurer = adventurerDAO.findById(user);
            pwdHash = adventurer.getPassword();
        } catch (KeyNotFoundException e) {
            invalidError(request, response);
        }

        if(pwdHash != null && new AuthenticationService().checkPassword(password, pwdHash)) {
            request.getSession().setAttribute("adventurer", adventurer);
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            invalidError(request, response);
        }
    }

    private void invalidError(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", "Invalid userName or Password");
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }
}
