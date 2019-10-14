package Presentation;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");

        String user = request.getParameter("UserName");
        String password = request.getParameter("UserPsw");

        //ToDo Connexion a ce qui se connecte à la DB

        //ToDo Changer les valeurs
        if(user.equals("")&&password.equals("java4s"))
            pw.println("Login Success...!");
        else
            pw.println("Login Failed...!");
        pw.close();

    }
}
