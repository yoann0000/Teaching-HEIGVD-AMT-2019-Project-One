package Presentation;

import Model.Adventurer;
import Model.Guild;
import Model.Party;
import datastore.exception.KeyNotFoundException;
import integration.IGuildAdventurerDAO;
import integration.IPartyAdventurerDAO;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name="Home", urlPatterns = "/home")
public class HomeServlet extends HttpServlet {
    @EJB
    IGuildAdventurerDAO guildAdventurerDAO;
    @EJB
    IPartyAdventurerDAO partyAdventurerDAO;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Adventurer moi = (Adventurer)request.getSession().getAttribute("adventurer");
        if(moi == null){
            response.sendRedirect(request.getContextPath() + "/login");
        }
        Guild guild = null;
        try {
            guild = guildAdventurerDAO.findAdventurerGuild(moi.getName( ));
        }catch (KeyNotFoundException e){

        }
        List<Party> parties = partyAdventurerDAO.findPlayerPartiesById(moi.getName());
        request.setAttribute("guild", guild);
        request.setAttribute("parties", parties);
        request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }

}