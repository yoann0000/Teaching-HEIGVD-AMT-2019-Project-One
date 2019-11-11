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
import java.util.LinkedList;
import java.util.List;

@WebServlet(name="QuestServlet", urlPatterns = "/quest")
public class QuestServlet extends HttpServlet {

    @EJB
    IGuildAdventurerDAO guildAdventurerDAO;
    @EJB
    IPartyAdventurerDAO partyAdventurerDAO;
    @EJB
    IQuestPartyGuildDAO questPartyGuildDAO;
    @EJB
    IQuestDAO questDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Guild guild = guildAdventurerDAO.findAdventurerGuild(request.getSession().getAttribute("adventurer").toString());
            List<PairQG> pairQGList = new ArrayList<>();
            for (Party party :partyAdventurerDAO.findPlayerPartiesById(request.getSession().getAttribute("adventurer").toString())) {
                pairQGList.addAll(questPartyGuildDAO.getQuestsDoneByParty(party));
            }
            request.setAttribute("guild", guild);
            request.setAttribute("userParties", partyAdventurerDAO.findPlayerPartiesById(request.getSession().getAttribute("adventurer").toString()));
        } catch (KeyNotFoundException e) {
            e.printStackTrace();
        }
        questPage(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String user = request.getSession().getAttribute("adventurer").toString();
        PairQG qg = (PairQG)request.getAttribute("quest");
        Party userParty = (Party) request.getAttribute("party");
        Guild guild = qg.getGuild();
        Quest quest = qg.getQuest();
        List<Adventurer> adventurers = partyAdventurerDAO.findPartyMembersById(user);
        for (Adventurer a: adventurers) {
            a.addExp(quest.getExp());
            a.addGold(quest.getGold());
        }
        guild.addReputation(quest.getExp());
        userParty.addReputation(quest.getExp());

        request.getRequestDispatcher("/WEB-INF/pages/quest.jsp").forward(request, response);
    }

    private void questPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 50;
        if(req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));
        List<Quest> quests = questDAO.findAll((page-1)*recordsPerPage,
                recordsPerPage);
        int noOfRecords = questDAO.nbOfRecord();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("questList", quests);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.getRequestDispatcher("/WEB-INF/pages/quest.jsp").forward(req, resp);
    }
}
