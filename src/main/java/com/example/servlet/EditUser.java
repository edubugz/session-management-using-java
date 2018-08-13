package com.example.servlet;

import com.example.dblogic.DBlogic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

// this servelt edits users in the db
// it is called from both the userOperatinos and adminOperations jsps

@WebServlet(name = "EditUser",urlPatterns ="/editUser")

public class EditUser extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {

        //get the session
        HttpSession session = request.getSession(false);

        // check if the attribute admin exist
        if(session.getAttribute("admin")!=null)
        {
            // to be used as an identifier in the DB    i.e WHERE ID = identifier
            String identifier="";

            // if the parameter identifier is not null
            // it can be null if  the request is coming from a normal user since a niormal
            // user doesn't need to specify their id since they can only edit themselves
            // so it will be set in the session as an attribute from where we can extract it

            if(request.getParameter("identifier")!=null)
            {
                identifier = request.getParameter("identifier");
            }
            else
            {
                // if it didn't come as a parameter, extract it from the session attributes

                identifier = request.getSession(false).getAttribute("id").toString();
            }

            DBlogic dbcon = new DBlogic();

            // get the new values for the user who will be edited
            String newId = request.getParameter("newId");
            String newName = request.getParameter("newName");
            try
            {
                //call the EditDB method which does the update which returns an array detailing the status of the update
                String[] status = dbcon.editDB(newId, newName, identifier);
                request.getSession(false).setAttribute("status", status);

                // if the request came from the admin, forwad to adminOperations JSP
                if(session.getAttribute("admin").toString().equals("yes"))
                {

                        request.getRequestDispatcher("adminOperations.jsp").forward(request, response);

                }
                else
                {
                    //else send to the userOperations page
                    request.getSession(false).setAttribute("id",status[2]);

                    request.getRequestDispatcher("userOperations.jsp").forward(request, response);

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

        else {
            //if the admin attribute doesnt exist invalidate the session and
            //send them to index.jsp
            session.invalidate();
            try
            {
                response.sendRedirect("index.jsp");
            }

            catch(IOException e)
            {
                System.out.println(e.getMessage());
            }
        }


    }

}
