package Presentation;

import business.AuthenticationService;
import datastore.exception.KeyNotFoundException;
import integration.AdventurerDAO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


@WebServlet(name="LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private void doRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");

        String user = request.getParameter("UserName");
        String password = request.getParameter("UserPsw");

        //ToDo Connexion a ce qui se connecte à la DB

        String pwdHash = null;
        try {
            pwdHash = new AdventurerDAO().findById(user).getPassword();
        } catch (KeyNotFoundException e) {
            e.printStackTrace();
        }

        if(pwdHash != null && new AuthenticationService().checkPassword(password, pwdHash)) {
            pw.println("Login Success...!");
            request.getSession().setAttribute("user", user);
            response.sendRedirect("/index");
        } else {
            pw.println("Login Failed...!");
        }
        pw.close();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doRequest(request, response);
    }
}
