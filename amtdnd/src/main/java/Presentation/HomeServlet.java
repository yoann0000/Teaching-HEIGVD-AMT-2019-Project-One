package Presentation;

import Model.Adventurer;
import Model.Guild;
import Model.Party;
import datastore.exception.KeyNotFoundException;
import integration.IAdventurerDAO;
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
    @EJB
    IAdventurerDAO adventurerDAO;
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
        Adventurer moi = (Adventurer)request.getSession().getAttribute("adventurer");
        if(moi == null){
            response.sendRedirect(request.getContextPath() + "/login");
        }
        String delete = request.getParameter("delete");
        String stat = request.getParameter("stat");
        if("delete".equals(delete)){
            deleteUserAccount(moi, request, response);
        }else if("str".equals(stat)){
            moi.addStr();
        }else if("dex".equals(stat)){
            moi.addDex();
        }else if("con".equals(stat)){
            moi.addCon();
        }else if("inte".equals(stat)){
            moi.addInt();
        }else if("wis".equals(stat)){
            moi.addWis();
        }else if("cha".equals(stat)){
            moi.addCha();
        }
        try {
            adventurerDAO.update(moi);
        } catch (KeyNotFoundException e) {
            e.printStackTrace( );
        }
        doGet(request, response);
    }

    private void deleteUserAccount(Adventurer adventurer, HttpServletRequest request, HttpServletResponse response){
        try {
            adventurerDAO.deleteById(adventurer.getName());
            response.sendRedirect(request.getContextPath() + "/logout");
        } catch (KeyNotFoundException | IOException e) {
            e.printStackTrace( );
        }
    }
}