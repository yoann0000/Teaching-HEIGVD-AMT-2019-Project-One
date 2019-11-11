package Presentation;

import Model.Adventurer;
import Model.Guild;
import datastore.exception.DuplicateKeyException;
import datastore.exception.KeyNotFoundException;
import integration.IGuildDAO;
import integration.IGuildAdventurerDAO;
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
public class GuildServletTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession httpSession;

    @Mock
    RequestDispatcher requestDispacher;

    @Mock
    IGuildDAO guildDAO;

    @Mock
    IGuildAdventurerDAO guildAdventurerDAO;

    private GuildServlet guildServlet;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        guildServlet = new GuildServlet();
        guildServlet.guildDAO = guildDAO;
        guildServlet.guildAdventurerDAO = guildAdventurerDAO;
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispacher);
    }

    @Test
    void doGet() throws ServletException, IOException {
        guildServlet.doGet(request, response);
        verify(guildDAO, atLeastOnce()).findAll(anyInt(), anyInt());
    }

    @Test
    void doPostCreateGuild() throws ServletException, IOException, KeyNotFoundException, DuplicateKeyException {
        when(request.getSession()).thenReturn(httpSession);
        when(guildDAO.findById(anyString())).thenReturn(Guild.builder().name("Test").members(new LinkedList<>()).build());
        when(guildDAO.create(any())).thenReturn(Guild.builder().name("Test").members(new LinkedList<>()).build());
        when(guildAdventurerDAO.findMembersById(any())).thenReturn(Arrays.asList(Adventurer.builder().name("Test").build()));
        when((Adventurer)httpSession.getAttribute("adventurer")).thenReturn(Adventurer.builder().name("Test").build());
        when(request.getParameter("newGuild")).thenReturn("Test");
        when(request.getParameter("guild")).thenReturn("");
        when(request.getParameter("join")).thenReturn(null);
        when(request.getParameter("quit")).thenReturn(null);
        guildServlet.doPost(request, response);
        verify(guildDAO, atLeastOnce()).findById("Test");
    }

    @Test
    void doPostGoToGuild() throws KeyNotFoundException, DuplicateKeyException, ServletException, IOException {
        when(request.getSession()).thenReturn(httpSession);
        when(guildDAO.findById(anyString())).thenReturn(Guild.builder().name("Test").members(new LinkedList<>()).build());
        when(guildAdventurerDAO.findMembersById(any())).thenReturn(Arrays.asList(Adventurer.builder().name("Test").build()));
        when((Adventurer)httpSession.getAttribute("adventurer")).thenReturn(Adventurer.builder().name("Test").build());
        when(request.getParameter("guild")).thenReturn("Test");
        when(request.getParameter("join")).thenReturn(null);
        when(request.getParameter("quit")).thenReturn(null);
        guildServlet.doPost(request, response);
        assertTrue(request.getParameter("guild").equals("Test"));
    }

    @Test
    void doPostJoinAGuild() throws KeyNotFoundException, DuplicateKeyException, ServletException, IOException {
        when(request.getSession()).thenReturn(httpSession);
        when(guildDAO.findById(anyString())).thenReturn(Guild.builder().name("Test").members(new LinkedList<>()).build());
        when(guildAdventurerDAO.findMembersById(anyString())).thenReturn(Arrays.asList(Adventurer.builder().name("Test").build()));
        when((Adventurer)httpSession.getAttribute("adventurer")).thenReturn(Adventurer.builder().name("Test").build());
        when(request.getParameter("guild")).thenReturn("Test");
        when(request.getParameter("join")).thenReturn("Test");
        when(request.getParameter("quit")).thenReturn(null);
        guildServlet.doPost(request, response);
        verify(guildAdventurerDAO, atLeastOnce()).addGuildToAdventurer((Adventurer)httpSession.getAttribute("adventurer"),
                guildDAO.findById("Test"));
    }

    @Test
    void doPostQuitAGuild() throws KeyNotFoundException, ServletException, IOException {
        when(request.getSession()).thenReturn(httpSession);
        when(guildDAO.findById(anyString())).thenReturn(Guild.builder().name("Test").members(Arrays.asList(
                Adventurer.builder().name("Test").build())).build());
        when(guildAdventurerDAO.findMembersById(anyString())).thenReturn(
                Arrays.asList(Adventurer.builder().name("Test").build()));
        when((Adventurer)httpSession.getAttribute("adventurer"))
                .thenReturn(Adventurer.builder().name("Test").build());
        when(request.getParameter("guild")).thenReturn("Test");
        when(request.getParameter("join")).thenReturn(null);
        when(request.getParameter("quit")).thenReturn("Test");
        guildServlet.doPost(request, response);
        verify(guildAdventurerDAO, atLeastOnce()).removeGuildFromAdventurer(Adventurer.builder().name("Test").build());
    }
}