package Presentation;
import Model.Adventurer;
import Model.Guild;
import Model.Party;
import business.IAuthenticationService;
import datastore.exception.DuplicateKeyException;
import datastore.exception.KeyNotFoundException;
import integration.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class HomeServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession httpSession;

    @Mock
    RequestDispatcher requestDispacher;

    @Mock
    IAdventurerDAO adventurerDAO;

    @Mock
    IGuildAdventurerDAO guildAdventurerDAO;

    @Mock
    IPartyAdventurerDAO partyAdventurerDAO;

    HomeServlet homeServlet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        homeServlet = new HomeServlet();
        homeServlet.adventurerDAO = adventurerDAO;
        homeServlet.guildAdventurerDAO = guildAdventurerDAO;
        homeServlet.partyAdventurerDAO = partyAdventurerDAO;
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("adventurer")).thenReturn(Adventurer.builder().name("monNom").build());
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispacher);
    }

    @Test
    void doGet() throws ServletException, IOException, KeyNotFoundException {
        when(guildAdventurerDAO.findAdventurerGuild(anyString())).thenReturn(Guild.builder().name("maGuilde").build());
        when(partyAdventurerDAO.findPlayerPartiesById(anyString())).thenReturn(new LinkedList<>());
        homeServlet.doGet(request, response);
        verify(guildAdventurerDAO, atLeastOnce()).findAdventurerGuild("monNom");
        verify(partyAdventurerDAO, atLeastOnce()).findPlayerPartiesById("monNom");
    }

    @Test
    void doPostDelete() throws ServletException, IOException, KeyNotFoundException {
        when(request.getParameter("delete")).thenReturn("delete");
        homeServlet.doPost(request, response);
        verify(adventurerDAO, atLeastOnce()).deleteById("monNom");
    }
}