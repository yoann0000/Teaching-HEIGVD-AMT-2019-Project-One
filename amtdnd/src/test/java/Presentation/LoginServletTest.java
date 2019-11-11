package Presentation;
import Model.Adventurer;
import datastore.exception.KeyNotFoundException;
import integration.IAdventurerDAO;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginServletTest {

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

    LoginServlet loginServlet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        loginServlet = new LoginServlet();
        loginServlet.adventurerDAO = adventurerDAO;
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispacher);
    }

    @Test
    void doGet() throws ServletException, IOException {
        loginServlet.doGet(request, response);
        verify(request, atLeastOnce()).getRequestDispatcher("/WEB-INF/pages/login.jsp");
    }

    @Test
    void doPost() throws KeyNotFoundException, IOException, ServletException {
        when(request.getParameter("username")).thenReturn("monNom");
        when(request.getParameter("password")).thenReturn("monPassword");
        when(adventurerDAO.findById(anyString())).thenReturn(Adventurer.builder().name("monNom").build());
        loginServlet.doPost(request, response);
        verify(adventurerDAO, atLeastOnce()).findById(anyString());
    }

}