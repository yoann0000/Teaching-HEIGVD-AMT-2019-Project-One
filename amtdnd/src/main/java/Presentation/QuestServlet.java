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
    IGuildAdventurerDAO guildAdventurerDAO;
    @EJB
    IPartyAdventurerDAO partyAdventurerDAO;
    @EJB
    IQuestPartyGuildDAO questPartyGuildDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Guild guild = guildAdventurerDAO.findAdventurerGuild(request.getSession().getAttribute("adventurer").toString());
            List<PairQG> pairQGList = new ArrayList<>();
            for (Party party :partyAdventurerDAO.findPlayerPartiesById(request.getSession().getAttribute("adventurer").toString())) {
                pairQGList.addAll(questPartyGuildDAO.getQuestsDoneByParty(party));
            }
            request.setAttribute("guild", guild);
            request.setAttribute("userQuests", pairQGList);
        } catch (KeyNotFoundException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/WEB-INF/pages/quest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String user = request.getSession().getAttribute("adventurer").toString();
        PairQG qg = (PairQG) request.getAttribute("quest");
        Guild guild = qg.getGuild();
        Quest quest = qg.getQuest();
        List<Adventurer> adventurers = partyAdventurerDAO.findPartyMembersById(user);
        for (Adventurer a: adventurers) {
            a.addExp(quest.getExp());
            a.addGold(quest.getGold());
        }
        guild.addReputation(quest.getExp());

        request.getRequestDispatcher("/WEB-INF/pages/quest.jsp").forward(request, response);
    }
}
