package Presentation;

import Model.Adventurer;
import Model.Guild;
import Model.Party;
import Model.Quest;
import datastore.exception.DuplicateKeyException;
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
    IGuildAdventurerDAO guildAdventurerDAO;
    @EJB
    IPartyDAO partyDAO;
    @EJB
    IGuildDAO guildDAO;
    @EJB
    IAdventurerDAO adventurerDAO;
    @EJB
    IPartyAdventurerDAO partyAdventurerDAO;
    @EJB
    IQuestPartyGuildDAO questPartyGuildDAO;
    @EJB
    IQuestDAO questDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Adventurer moi = (Adventurer)request.getSession().getAttribute("adventurer");
        if(moi == null){
            response.sendRedirect(request.getContextPath() + "/login");
        }
        try {
            Guild guild = guildAdventurerDAO.findAdventurerGuild(request.getSession().getAttribute("adventurer").toString());
            request.setAttribute("guild", guild);
            request.setAttribute("userParties", partyAdventurerDAO.findPlayerPartiesById(request.getSession().getAttribute("adventurer").toString()));
        } catch (KeyNotFoundException e) {
            request.setAttribute("error", e.getMessage( ));
        }
        questPage(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Adventurer moi = (Adventurer)request.getSession().getAttribute("adventurer");
        String party = request.getParameter("parties");
        String quest = request.getParameter("quest");
        String doit = request.getParameter("doit");
        try {
            Party userParty = partyDAO.findById(party);
            Guild userGuild = guildAdventurerDAO.findAdventurerGuild(moi.getName());
            if(doit != null){
                try {
                    Quest doneQuest = questDAO.findById(doit);
                    questPartyGuildDAO.create(doneQuest, userParty, userGuild);
                    userGuild.addReputation(1);
                    guildDAO.update(userGuild);
                    userParty.addReputation(1);
                    partyDAO.update(userParty);
                    for(Adventurer member : partyAdventurerDAO.findPartyMembersById(userParty.getName())){
                        member.addExp(doneQuest.getExp());
                        member.addGold(doneQuest.getGold());
                        adventurerDAO.update(member);
                    }
                    request.getSession().setAttribute("adventurer", adventurerDAO.findById(moi.getName()));
                    doGet(request, response);
                } catch (KeyNotFoundException e) {
                    request.setAttribute("error", e.getMessage( ));
                    doGet(request, response);
                }
            }else if (quest != null){
                Quest thisQuest = questDAO.findById(quest);
                request.setAttribute("quest", thisQuest);
                request.getRequestDispatcher("/WEB-INF/pages/questprofile.jsp").forward(request, response);
            }
        } catch (KeyNotFoundException | DuplicateKeyException e) {
            request.setAttribute("error", e.getMessage( ));
            doGet(request, response);
        }
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
