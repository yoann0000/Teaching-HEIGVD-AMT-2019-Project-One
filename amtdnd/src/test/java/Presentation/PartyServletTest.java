package Presentation;

import Model.Adventurer;
import Model.Party;
import datastore.exception.DuplicateKeyException;
import datastore.exception.KeyNotFoundException;
import integration.IPartyAdventurerDAO;
import integration.IPartyDAO;
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
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
public class PartyServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession httpSession;

    @Mock
    RequestDispatcher requestDispacher;

    @Mock
    IPartyDAO partyDAO;

    @Mock
    IPartyAdventurerDAO partyAdventurerDAO;

    PartyServlet partyServlet;

    @BeforeEach
    public void setup() throws IOException, DuplicateKeyException, KeyNotFoundException {
        MockitoAnnotations.initMocks(this);
        partyServlet = new PartyServlet();
        partyServlet.partyDAO = partyDAO;
        partyServlet.partyAdventurerDAO = partyAdventurerDAO;
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispacher);
    }

    @Test
    void doGet() throws ServletException, IOException, DuplicateKeyException, SQLException, KeyNotFoundException {
        partyServlet.doGet(request, response);
        verify(partyDAO, atLeastOnce()).findAll(anyInt(), anyInt());
    }

    @Test
    void doPostCreateGuild() throws ServletException, IOException, KeyNotFoundException, DuplicateKeyException {
        when(request.getSession()).thenReturn(httpSession);
        when(partyDAO.findById(anyString())).thenReturn(Party.builder().name("Test").members(new LinkedList<>()).build());
        when(partyDAO.create(any())).thenReturn(Party.builder().name("Test").members(new LinkedList<>()).build());
        when(partyAdventurerDAO.findPartyMembersById(any())).thenReturn(Arrays.asList(Adventurer.builder().name("Test").build()));
        when((Adventurer)httpSession.getAttribute("adventurer")).thenReturn(Adventurer.builder().name("Test").build());
        when(request.getParameter("newParty")).thenReturn("Test");
        when(request.getParameter("party")).thenReturn("");
        when(request.getParameter("join")).thenReturn(null);
        when(request.getParameter("quit")).thenReturn(null);
        partyServlet.doPost(request, response);
        verify(partyDAO, atLeastOnce()).findById("Test");
    }

    @Test
    void doPostGoToParty() throws KeyNotFoundException, DuplicateKeyException, ServletException, IOException {
        when(request.getSession()).thenReturn(httpSession);
        when(partyDAO.findById(anyString())).thenReturn(Party.builder().name("Test").members(new LinkedList<>()).build());
        when(partyAdventurerDAO.findPartyMembersById(any())).thenReturn(Arrays.asList(Adventurer.builder().name("Test").build()));
        when((Adventurer)httpSession.getAttribute("adventurer")).thenReturn(Adventurer.builder().name("Test").build());
        when(request.getParameter("party")).thenReturn("Test");
        when(request.getParameter("join")).thenReturn(null);
        when(request.getParameter("quit")).thenReturn(null);
        partyServlet.doPost(request, response);
        assertTrue(request.getParameter("party").equals("Test"));
    }

    @Test
    void doPostJoinAParty() throws KeyNotFoundException, DuplicateKeyException, ServletException, IOException {
        when(request.getSession()).thenReturn(httpSession);
        when(partyDAO.findById(anyString())).thenReturn(Party.builder().name("Test").members(new LinkedList<>()).build());
        when(partyAdventurerDAO.findPartyMembersById(anyString())).thenReturn(Arrays.asList(Adventurer.builder().name("Test").build()));
        when((Adventurer)httpSession.getAttribute("adventurer")).thenReturn(Adventurer.builder().name("Test").build());
        when(request.getParameter("party")).thenReturn("Test");
        when(request.getParameter("join")).thenReturn("Test");
        when(request.getParameter("quit")).thenReturn(null);
        partyServlet.doPost(request, response);
        verify(partyAdventurerDAO, atLeastOnce()).create((Adventurer)httpSession.getAttribute("adventurer"),
                partyDAO.findById("Test"));
    }

    @Test
    void doPostQuitAParty() throws KeyNotFoundException, ServletException, IOException {
        when(request.getSession()).thenReturn(httpSession);
        when(partyDAO.findById(anyString())).thenReturn(Party.builder().name("Test").members(Arrays.asList(
                Adventurer.builder().name("Test").build())).build());
        when(partyAdventurerDAO.findPartyMembersById(anyString())).thenReturn(
                Arrays.asList(Adventurer.builder().name("Test").build()));
        when((Adventurer)httpSession.getAttribute("adventurer"))
                .thenReturn(Adventurer.builder().name("Test").build());
        when(request.getParameter("party")).thenReturn("Test");
        when(request.getParameter("join")).thenReturn(null);
        when(request.getParameter("quit")).thenReturn("Test");
        partyServlet.doPost(request, response);
        verify(partyAdventurerDAO, atLeastOnce()).deleteById(Adventurer.builder().name("Test").build(),
                partyDAO.findById("Test"));
    }
}