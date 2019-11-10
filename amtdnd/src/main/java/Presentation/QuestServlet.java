package Presentation;

import datastore.exception.KeyNotFoundException;
import integration.IAdventurerDAO;
import integration.IGuildAdventurerDAO;
import integration.IGuildDAO;
import integration.IQuestDAO;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="QuestServlet", urlPatterns = "/quest")
public class QuestServlet extends HttpServlet {

    @EJB
    IAdventurerDAO adventurerDAO;
    @EJB
    IQuestDAO questDAO;
    @EJB
    IGuildDAO guildDAO;
    @EJB
    IGuildAdventurerDAO guildAdventurerDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("guild", guildAdventurerDAO.findAdventurerGuild(req.getSession().getAttribute("adventurer").toString()));
        try {
            req.setAttribute("guildQuests", guildDAO.findById(req.getSession().getAttribute("guild").toString()).getGuildQuests());
            req.setAttribute("userQuests", adventurerDAO.findById(req.getSession().getAttribute("adventurer").toString()).getQuests());
        } catch (KeyNotFoundException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/WEB-INF/pages/quest.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        doWork(req, resp);
    }

    private void doWork(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String work = req.getParameter("ac");
        try {
            if (work.equals("get")) {
                adventurerDAO.findById(req.getSession().getAttribute("adventurer").toString()).addQuest(questDAO.findById(req.getAttribute("quest").toString()));
            } else {
                adventurerDAO.findById(req.getSession().getAttribute("adventurer").toString()).removeQuest(questDAO.findById(req.getAttribute("quest").toString()));
            }
        } catch (KeyNotFoundException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/WEB-INF/pages/quest.jsp").forward(req, resp);
    }
}
