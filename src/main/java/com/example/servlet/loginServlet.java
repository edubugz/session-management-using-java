package com.example.servlet;

import com.example.dblogic.DBlogic;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "loginServlet", urlPatterns = "/login")


// this servlet is called when a user submits a login form
public class loginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {


        try
        {


            PrintWriter toBrowser = response.getWriter();
            //initialize an object of type DBlogic
            DBlogic dbcon = new DBlogic();

            //get the parameters that were sent with the form
            String name = request.getParameter("name");
            String id = request.getParameter("id");

            //calll the readFromDB method wich returns an array of the status of the login
            String[] oneStudent = dbcon.readFromDB(id,name);

            //the array returned will be of length 3
            if(oneStudent.length==3) {
                // create a session since it is login
                HttpSession session = request.getSession(true);

                //all the elements in the array are set as attributes and attached to the session
                session.setAttribute("admin", oneStudent[1]);
                session.setAttribute("id",oneStudent[2]);
                session.setAttribute("status","");
                session.setMaxInactiveInterval(0);

                // after the request is forwaded to the determinant servlet for more processing
                request.getRequestDispatcher("/DeteminantServlet").forward(request,response);
            }






        }
        catch (SQLException e)
        {
            e.printStackTrace();
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
