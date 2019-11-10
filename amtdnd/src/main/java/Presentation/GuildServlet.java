package Presentation;

import Model.Adventurer;
import Model.Guild;
import datastore.exception.DuplicateKeyException;
import datastore.exception.KeyNotFoundException;
import integration.IGuildAdventurerDAO;
import integration.IGuildDAO;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name="GuildServlet", urlPatterns = "/guild")
public class GuildServlet extends HttpServlet {

    @EJB
    IGuildDAO guildDAO;

    @EJB
    IGuildAdventurerDAO guildAdventurerDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //source : https://stackoverflow.com/questions/31410007/how-to-do-pagination-in-jsp
        guildPage(req, resp);
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
                guildAdventurerDAO.addGuildToAdventurer((Adventurer)req.getSession().getAttribute("adventurer"),
                        guildDAO.findById(join));
            } catch (KeyNotFoundException e) {
                req.setAttribute("error", e.getMessage( ));
            }
        }else if(quit != null){
            try {
                guildAdventurerDAO.removeGuildFromAdventurer((Adventurer)req.getSession().getAttribute("adventurer"));
            } catch (KeyNotFoundException e) {
                req.setAttribute("error", e.getMessage( ));
            }
        }
        String guild = req.getParameter("guild");
        if (guild.equals("")) {
            try {
                guildDAO.create(Guild.builder( ).name(req.getParameter("newGuild")).members(new LinkedList<>())
                        .build( ));
                guild = req.getParameter("newGuild");
            } catch (DuplicateKeyException e) {
                req.setAttribute("errorMessage", true);
                req.setAttribute("error", e.getMessage( ));
                guildPage(req, resp);
                return;
            }
        }
        List<Adventurer> members = guildAdventurerDAO.findMembersById(guild);
        boolean isMember = false;
        for(Adventurer a : members){
            if(a.getName().equals(moi.getName())){
                isMember = true;
                break;
            }
        }
        try {
            req.setAttribute("guild", guildDAO.findById(guild));
        } catch (KeyNotFoundException e) {
            req.setAttribute("isMember", isMember);
            req.setAttribute("errorMessage", true);
            req.setAttribute("error", e.getMessage( ));
            guildPage(req, resp);
            return;
        }
        req.setAttribute("memberList", members);
        req.getRequestDispatcher("/WEB-INF/pages/guildprofile.jsp").forward(req, resp);
    }

    private void guildPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int page = 1;
        int recordsPerPage = 50;
        if(req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));
        List<Guild> guilds = guildDAO.findAll((page-1)*recordsPerPage,
                recordsPerPage);
        int noOfRecords = guildDAO.nbOfRecord();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("guildList", guilds);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.getRequestDispatcher("/WEB-INF/pages/guild.jsp").forward(req, resp);
    }
}
