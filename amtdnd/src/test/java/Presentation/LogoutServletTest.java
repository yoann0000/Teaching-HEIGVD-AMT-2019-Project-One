package Presentation;
import datastore.exception.DuplicateKeyException;
import datastore.exception.KeyNotFoundException;
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
public class LogoutServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession httpSession;

    LogoutServlet logoutServlet;

    @Test
    void doGet() throws ServletException, IOException, DuplicateKeyException, SQLException, KeyNotFoundException {
        MockitoAnnotations.initMocks(this);
        logoutServlet = new LogoutServlet();
        when(request.getSession()).thenReturn(httpSession);
        logoutServlet.doGet(request, response);
        verify(httpSession, atLeastOnce()).invalidate();
    }

}