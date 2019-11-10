package Presentation;

import Model.Guild;
import Model.Party;
import datastore.exception.KeyNotFoundException;
import integration.*;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name="QuestServlet", urlPatterns = "/quest")
public class QuestServlet extends HttpServlet {

    @EJB
    IQuestPartyGuildDAO questPartyGuildDAO;
    @EJB
    IAdventurerDAO partyDAO;
    @EJB
    IQuestDAO questDAO;
    @EJB
    IGuildAdventurerDAO guildAdventurerDAO;
    @EJB
    IPartyAdventurerDAO partyAdventurerDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Guild guild = guildAdventurerDAO.findAdventurerGuild(req.getSession().getAttribute("adventurer").toString());
            List<Party> parties = partyAdventurerDAO.findPlayerPartiesById(req.getSession().getAttribute("adventurer").toString());
            req.setAttribute("guild", guild);
            req.setAttribute("guildQuests", questPartyGuildDAO.getQuestsDoneByGuild(guild));
            //req.setAttribute("userQuests", questPartyGuildDAO.getQuestsDoneByParty(parties));
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
                questPartyGuildDAO.getQuestsDoneByGuild(guildAdventurerDAO.findAdventurerGuild(req.getSession().getAttribute("adventurer").toString()));
            } else {
                //questPartyGuildDAO.getQuestsDoneByParty(partyAdventurerDAO.findPlayerPartiesById(req.getSession().getAttribute("adventurer").toString()));
            }
        } catch (KeyNotFoundException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/WEB-INF/pages/quest.jsp").forward(req, resp);
    }
}
