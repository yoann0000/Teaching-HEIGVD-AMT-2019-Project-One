package Presentation;

import Model.Adventurer;
import Model.Guild;
import Model.Party;
import Model.Quest;
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
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
public class QuestServletTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession httpSession;

    @Mock
    RequestDispatcher requestDispacher;

    @Mock
    IPartyAdventurerDAO partyAdventurerDAO;

    @Mock
    IGuildAdventurerDAO guildAdventurerDAO;

    @Mock
    IQuestPartyGuildDAO questPartyGuildDAO;

    @Mock
    IPartyDAO partyDAO;

    @Mock
    IQuestDAO questDAO;

    private QuestServlet questServlet;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        questServlet = new QuestServlet();
        questServlet.partyAdventurerDAO = partyAdventurerDAO;
        questServlet.guildAdventurerDAO = guildAdventurerDAO;
        questServlet.questPartyGuildDAO = questPartyGuildDAO;
        questServlet.partyDAO = partyDAO;
        questServlet.questDAO = questDAO;
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispacher);
        when(request.getSession()).thenReturn(httpSession);
        when((Adventurer)httpSession.getAttribute("adventurer")).thenReturn(Adventurer.builder().name("Test").build());
    }

    @Test
    public void doGet() throws ServletException, IOException, KeyNotFoundException {
        when(guildAdventurerDAO.findAdventurerGuild(anyString())).thenReturn(Guild.builder().name("Test").members(
                new LinkedList<>()).build());
        when(partyAdventurerDAO.findPlayerPartiesById(anyString())).thenReturn(new LinkedList<>());
        questServlet.doGet(request, response);
        verify(guildAdventurerDAO, atLeastOnce()).findAdventurerGuild(anyString());
        verify(partyAdventurerDAO, atLeastOnce()).findPlayerPartiesById(anyString());
    }

    @Test
    public void doPostDoIt() throws ServletException, IOException, KeyNotFoundException, DuplicateKeyException {
        Quest test = Quest.builder().objective("test").exp(20).gold(10).build();
        Guild gtest = Guild.builder().name("test").reputation(30).build();
        when(request.getParameter("party")).thenReturn("TestParty");
        when(request.getParameter("doit")).thenReturn("test");
        when(request.getParameter("quest")).thenReturn(null);
        Party ptest = Party.builder().name("TestParty").reputation(10).build();
        when(questDAO.findById(anyString())).thenReturn(test);
        when(guildAdventurerDAO.findAdventurerGuild(anyString())).thenReturn(gtest);
        when(partyDAO.findById(anyString())).thenReturn(ptest);
        List<Adventurer> adventurers = Arrays.asList(Adventurer.builder().name("a1").experience(0).gold(0).build(), Adventurer.builder().name("a2").experience(0).gold(0).build());
        when(partyAdventurerDAO.findPartyMembersById(anyString())).thenReturn(adventurers);
        questServlet.doPost(request, response);
        for (Adventurer a: adventurers) {
            assertEquals(10 ,a.getGold());
            assertEquals(20, a.getExperience());
        }
        assertEquals(31, gtest.getReputation());
        assertEquals(11, ptest.getReputation());
        verify(questDAO, atLeastOnce()).findById(anyString());
        verify(guildAdventurerDAO, atLeastOnce()).findAdventurerGuild(anyString());
        verify(partyDAO, atLeastOnce()).findById(anyString());
        verify(questPartyGuildDAO, atLeastOnce()).create(any(), any(), any());
        verify(request.getRequestDispatcher(anyString()), atLeastOnce()).forward(request, response);
    }

    @Test
    public void doPostQuest() throws ServletException, IOException, KeyNotFoundException {
        when(request.getParameter("party")).thenReturn("TestParty");
        when(request.getParameter("doit")).thenReturn(null);
        when(request.getParameter("quest")).thenReturn("test");
        questServlet.doPost(request, response);
        verify(questDAO, atLeastOnce()).findById(any());
        verify(questPartyGuildDAO, atLeastOnce()).getPartiesAndGuildsWhoHasDoneQuest(any());
        verify(request.getRequestDispatcher(anyString()), atLeastOnce()).forward(request, response);
    }
}
