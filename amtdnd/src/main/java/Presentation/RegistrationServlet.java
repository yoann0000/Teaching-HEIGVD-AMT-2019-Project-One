package Presentation;

import Model.Adventurer;
import business.AuthenticationService;
import business.IAuthenticationService;
import datastore.exception.DuplicateKeyException;
import integration.AdventurerDAO;
import integration.IAdventurerDAO;
import integration.IClassDAO;
import integration.IRaceDAO;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name="RegistrationServlet", urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {

    @EJB
    IAuthenticationService authenticationService;

    @EJB
    IClassDAO classDAO;

    @EJB
    IRaceDAO raceDAO;

    @EJB
    IAdventurerDAO adventurerDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("classes", classDAO.findAll());
        request.setAttribute("races", raceDAO.findAll());
        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("username");
        String password = request.getParameter("password");
        String race = request.getParameter("race");
        String _class = request.getParameter("class");
        Adventurer adventurer = Adventurer.builder()
                .name(user)
                .password(authenticationService.hashPassword(password))
                .str(8)
                .inte(8)
                .cha(8)
                .wis(8)
                .con(8)
                .dex(8)
                .klass(_class)
                .race(race)
                .build();
        try {
            Adventurer adventurerLoad = adventurerDAO.create(adventurer);
            request.getSession().setAttribute("adventurer", adventurerLoad);
            response.sendRedirect(request.getContextPath() + "/home");
        } catch (DuplicateKeyException e) {
            request.getSession().setAttribute("classes", classDAO.findAll());
            request.getSession().setAttribute("races", raceDAO.findAll());
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
    }
}
