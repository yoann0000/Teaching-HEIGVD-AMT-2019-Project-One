package Presentation;

import Model.Adventurer;
import Model.Party;
import datastore.exception.DuplicateKeyException;
import datastore.exception.KeyNotFoundException;
import integration.IPartyAdventurerDAO;
import integration.IPartyDAO;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
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
        partyPage(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Adventurer moi = (Adventurer)req.getSession().getAttribute("adventurer");
        if(moi == null){
            resp.sendRedirect(req.getContextPath() + "/login");
        }
        String join = req.getParameter("join");
        String quit = req.getParameter("quit");
        if(join != null){
            try {
                partyAdventurerDAO.create((Adventurer)req.getSession().getAttribute("adventurer"),
                        partyDAO.findById(join));
            } catch (KeyNotFoundException | DuplicateKeyException e) {
                req.setAttribute("error", e.getMessage( ));
            }
        }else if(quit != null){
            try {
                partyAdventurerDAO.deleteById((Adventurer)req.getSession().getAttribute("adventurer"),
                        partyDAO.findById(quit));
            } catch (KeyNotFoundException e) {
                req.setAttribute("error", e.getMessage( ));
            }
        }
        String party = req.getParameter("party");
        if(party == null){
            if(quit == null){
                party = join;
            }else{
                party = quit;
            }
        }
        if (party.equals("")) {
            try {
                partyDAO.create(Party.builder( ).name(req.getParameter("newParty")).members(new LinkedList<>())
                        .build( ));
                party = req.getParameter("newParty");
            } catch (DuplicateKeyException e) {
                req.setAttribute("errorMessage", true);
                req.setAttribute("error", e.getMessage( ));
                partyPage(req, resp);
                return;
            }
        }
        List<Adventurer> members = partyAdventurerDAO.findPartyMembersById(party);
        boolean isMember = false;
        for(Adventurer a : members){
            if(a.getName().equals(moi.getName())){
                isMember = true;
                break;
            }
        }
        try {
            req.setAttribute("party", partyDAO.findById(party));
        } catch (KeyNotFoundException e) {
            req.setAttribute("errorMessage", true);
            req.setAttribute("error", e.getMessage( ));
            partyPage(req, resp);
            return;
        }
        req.setAttribute("isMember", isMember);
        req.setAttribute("memberList", members);
        req.getRequestDispatcher("/WEB-INF/pages/partyprofile.jsp").forward(req, resp);
    }

    private void partyPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int page = 1;
        int recordsPerPage = 50;
        if(req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));
        List<Party> parties = partyDAO.findAll((page-1)*recordsPerPage,
                recordsPerPage);
        int noOfRecords = partyDAO.nbOfRecord();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("partydList", parties);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.getRequestDispatcher("/WEB-INF/pages/party.jsp").forward(req, resp);
    }
}
