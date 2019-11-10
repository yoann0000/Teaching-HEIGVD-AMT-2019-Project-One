package Presentation;

import Model.Adventurer;
import Model.Guild;
import Model.Party;
import Model.Quest;
import Model.util.PairQG;
import datastore.exception.KeyNotFoundException;
import integration.*;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="QuestServlet", urlPatterns = "/quest")
public class QuestServlet extends HttpServlet {

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
            List<PairQG> pairQGList = new ArrayList<>();
            for (Party party :partyAdventurerDAO.findPlayerPartiesById(req.getSession().getAttribute("adventurer").toString())) {
                pairQGList.addAll(questPartyGuildDAO.getQuestsDoneByParty(party));
            }
            req.setAttribute("guild", guild);
            req.setAttribute("guildQuests", questPartyGuildDAO.getQuestsDoneByGuild(guild));
            req.setAttribute("userQuests", pairQGList);
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
        String user = req.getSession().getAttribute("adventurer").toString();
        try {
            if (work.equals("get")) {
                questPartyGuildDAO.getQuestsDoneByGuild(guildAdventurerDAO.findAdventurerGuild(user));
            } else {
                PairQG qg = (PairQG) req.getAttribute("quest");
                Guild guild = qg.getGuild();
                Quest quest = qg.getQuest();
                List<Adventurer> adventurers = partyAdventurerDAO.findPartyMembersById(user);
                for (Adventurer a: adventurers) {
                    a.addExp(quest.getExp());
                    a.addGold(quest.getGold());
                }
                guild.addReputation(quest.getExp());
                questDAO.deleteById(quest.toString());
                //TODO remove quest from party and guild
            }
        } catch (KeyNotFoundException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/WEB-INF/pages/quest.jsp").forward(req, resp);
    }
}
