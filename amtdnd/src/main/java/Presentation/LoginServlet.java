package Presentation;

import business.AuthenticationService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name="LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");

        String user = request.getParameter("UserName");
        String password = request.getParameter("UserPsw");

        //ToDo Connexion a ce qui se connecte à la DB
        String pwdHash = "";
        //ToDo Changer les valeurs
        if(new AuthenticationService().checkPassword(password, pwdHash))
            pw.println("Login Success...!");
        else
            pw.println("Login Failed...!");
        pw.close();

    }
}
