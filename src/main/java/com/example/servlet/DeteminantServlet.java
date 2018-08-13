package com.example.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;


// determinat servlet decides if the user should be sent to the normal user operations page
// or the admin operations page

@WebServlet(name = "DeteminantServlet",urlPatterns ="/DeteminantServlet")
public class DeteminantServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {

                    // this array status is not utilized in this servlet but it will be utilized in the
                    // userOperations and adminOperations page as attributes
                    String [] status = new String [3];
                    // get the session
                    HttpSession forwaded = request.getSession(false);
                    forwaded.setAttribute("status", status = null);

        try {
            // if the user is an admin send them to the adminOperarons jsp
            if (forwaded.getAttribute("admin").equals("yes")) {


                request.getRequestDispatcher("adminOperations.jsp").forward(request, response);

                //if the user is a normal user, send them to the userOperations page
            } else if (forwaded.getAttribute("admin").equals("no")) {
                request.getRequestDispatcher("userOperations.jsp").forward(request, response);

            }
        }

        catch (ServletException e)
        {
            System.out.println(e.getMessage());
        }

        catch (IOException e)
        {
            System.out.println(e.getMessage());

        }


    }

}
