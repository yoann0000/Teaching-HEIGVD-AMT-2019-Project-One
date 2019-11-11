package Presentation;
import Model.Adventurer;
import business.IAuthenticationService;
import datastore.exception.DuplicateKeyException;
import datastore.exception.KeyNotFoundException;
import integration.IAdventurerDAO;
import integration.IClassDAO;
import integration.IRaceDAO;
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
import java.util.LinkedList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RegistrationServletTest {

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
    IAuthenticationService authenticationService;

    @Mock
    IRaceDAO raceDAO;

    @Mock
    IClassDAO classDAO;

    RegistrationServlet registrationServlet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        registrationServlet = new RegistrationServlet();
        registrationServlet.adventurerDAO = adventurerDAO;
        registrationServlet.classDAO = classDAO;
        registrationServlet.raceDAO = raceDAO;
        registrationServlet.authenticationService = authenticationService;
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispacher);
    }

    @Test
    void doGet() throws ServletException, IOException {
        when(raceDAO.findAll()).thenReturn(new LinkedList<>());
        when(classDAO.findAll()).thenReturn(new LinkedList<>());
        registrationServlet.doGet(request, response);
        verify(raceDAO, atLeastOnce()).findAll();
        verify(classDAO, atLeastOnce()).findAll();
    }

    @Test
    void doPost() throws KeyNotFoundException, IOException, ServletException, DuplicateKeyException {
        when(request.getParameter("username")).thenReturn("monNom");
        when(request.getParameter("password")).thenReturn("monPass");
        when(request.getParameter("race")).thenReturn("Elf");
        when(request.getParameter("class")).thenReturn("Rogue");
        when(authenticationService.hashPassword(anyString())).thenReturn("monPass");
        when(adventurerDAO.create(any())).thenReturn(Adventurer.builder().name("monNom").build());
        when(request.getSession()).thenReturn(httpSession);
        registrationServlet.doPost(request, response);
        verify(adventurerDAO, atLeastOnce()).create(any());
    }

}