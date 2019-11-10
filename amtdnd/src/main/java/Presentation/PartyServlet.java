package Presentation;

import Model.Adventurer;
import Model.Party;
import integration.IPartyAdventurerDAO;
import integration.IPartyDAO;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name="PartyServlet", urlPatterns = "/party")
public class PartyServlet extends HttpServlet {

    @EJB
    IPartyDAO partyDAO;

    @EJB
    IPartyAdventurerDAO partyAdventurerDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //source : https://stackoverflow.com/questions/31410007/how-to-do-pagination-in-jsp
        int page = 1;
        int recordsPerPage = 50;
        if(req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));
        List<Party> parties = partyDAO.findAll((page-1)*recordsPerPage,
                recordsPerPage);
        int noOfRecords = partyDAO.nbOfRecord();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("guildList", parties);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.getRequestDispatcher("/WEB-INF/pages/party.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String party = req.getParameter("party");
        List<Adventurer> members = partyAdventurerDAO.findPartyMembersById(party);
        req.setAttribute("guild", party);
        req.setAttribute("memberList", members);
        req.getRequestDispatcher("/WEB-INF/pages/partyprofile.jsp").forward(req, resp);
    }
}
