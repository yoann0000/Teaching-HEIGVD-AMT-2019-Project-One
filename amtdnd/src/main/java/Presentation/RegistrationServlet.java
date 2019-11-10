package Presentation;

import Model.Adventurer;
import datastore.exception.DuplicateKeyException;
import integration.AdventurerDAO;
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
    IClassDAO classDAO;

    @EJB
    IRaceDAO raceDAO;

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
                .password(password)
                .dex(10)
                .con(10)
                .wis(10)
                .cha(10)
                .inte(10)
                .str(10)
                .gold(0)
                .klass(_class)
                .race(race)
                .experience(0)
                .spendpoints(0)
                .quests(new ArrayList<>())
                .build();
        try {
            new AdventurerDAO().create(adventurer);
            request.getSession().setAttribute("adventurer", adventurer);
            response.sendRedirect(request.getContextPath() + "/home");
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            request.setAttribute("error", "username already exists");
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
    }
}
