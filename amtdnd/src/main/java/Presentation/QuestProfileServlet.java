package Presentation;

import datastore.exception.KeyNotFoundException;
import integration.IQuestDAO;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="QuestProfileServlet", urlPatterns = "/questprofile")
public class QuestProfileServlet extends HttpServlet {

    @EJB
    IQuestDAO questDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("quest", questDAO.findById((String) request.getAttribute("questname")));
        } catch (KeyNotFoundException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/WEB-INF/pages/questprofile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.getRequestDispatcher("/WEB-INF/pages/questprofile.jsp").forward(request, response);
    }
}
