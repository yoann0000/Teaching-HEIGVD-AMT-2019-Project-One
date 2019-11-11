package Presentation;

import Model.Adventurer;
import Model.Guild;
import Model.Party;
import Model.Quest;
import Model.util.PairQG;
import datastore.exception.KeyNotFoundException;
import integration.IGuildAdventurerDAO;
import integration.IPartyAdventurerDAO;
import integration.IQuestPartyGuildDAO;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestServletTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    IPartyAdventurerDAO partyAdventurerDAO;

    @Mock
    IGuildAdventurerDAO guildAdventurerDAO;

    @Mock
    IQuestPartyGuildDAO questPartyGuildDAO;

    private QuestServlet questServlet;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        questServlet = new QuestServlet();
        questServlet.partyAdventurerDAO = partyAdventurerDAO;
        questServlet.guildAdventurerDAO = guildAdventurerDAO;
        questServlet.questPartyGuildDAO = questPartyGuildDAO;
        when(request.getSession().getAttribute("adventurer").toString()).thenReturn("adventurerTest");
    }

    @Test
    public void doGetTest() throws ServletException, IOException, KeyNotFoundException {
        when(guildAdventurerDAO.findAdventurerGuild(anyString())).thenReturn(Guild.builder().name("Test").members(new LinkedList<>()).build());
        when(partyAdventurerDAO.findPlayerPartiesById(request.getSession().getAttribute("adventurer").toString())).thenReturn(
                new LinkedList<>()
        );
        questServlet.doGet(request, response);
        verify(request, atLeast(3)).setAttribute(anyString(), any());
        verify(request.getRequestDispatcher(anyString()), atLeastOnce()).forward(request, response);
    }

    @Test
    public void doPostTest() throws ServletException, IOException {
        Quest test = Quest.builder().objective("test").exp(20).gold(10).build();
        Guild gtest = Guild.builder().name("test").reputation(30).build();
        when(request.getAttribute("quest")).thenReturn(Arrays.asList(PairQG.builder().quest(test).guild(gtest).build()));
        Party ptest = Party.builder().name("TestParty").reputation(10).build();
        when(request.getAttribute("party")).thenReturn(ptest);
        List<Adventurer> adventurers = Arrays.asList(Adventurer.builder().name("a1").experience(0).gold(0).build(), Adventurer.builder().name("a2").experience(0).gold(0).build());
        when(partyAdventurerDAO.findPartyMembersById(anyString())).thenReturn(adventurers);
        for (Adventurer a: adventurers) {
            assertEquals(10 ,a.getGold());
            assertEquals(20, a.getExperience());
        }
        assertEquals(50, gtest.getReputation());
        assertEquals(30, ptest.getReputation());
        verify(request.getRequestDispatcher(anyString()), atLeastOnce()).forward(request, response);
    }
}
