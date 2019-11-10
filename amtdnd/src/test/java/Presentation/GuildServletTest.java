package Presentation;

import datastore.exception.DuplicateKeyException;
import datastore.exception.KeyNotFoundException;
import integration.IGuildDAO;
import integration.IGuildAdventurerDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

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
    IGuildDAO guildDAO;

    @Mock
    IGuildAdventurerDAO guildAdventurerDAO;

    @Mock
    PrintWriter responseWriter;

    GuildServlet guildServlet;

    @BeforeEach
    public void setup() throws IOException{
        guildServlet = new GuildServlet();
        guildServlet.guildDAO = guildDAO;
        guildServlet.guildAdventurerDAO = guildAdventurerDAO;
        when(response.getWriter()).thenReturn(responseWriter);
    }

    @Test
    void doGet() throws ServletException, IOException, DuplicateKeyException, SQLException, KeyNotFoundException {
        guildServlet.doGet(request, response);
        verify(guildDAO, atLeastOnce()).findAll(anyInt(), anyInt());
    }
}