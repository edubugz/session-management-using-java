package com.example.servlet;

import com.example.dblogic.DBlogic;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

// this servlet gets all the users from the db and returns them to the  adminOperations

@WebServlet(name = "SeeAll",urlPatterns="/seeall")
public class SeeAll extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        // get the session
        HttpSession session = request.getSession(false);

        // is the user an admin?
        if (null != session) {

            if (session.getAttribute("admin").toString().equals("yes")) {
                // create an object of DB logic
                DBlogic fetchAll = new DBlogic();

                try {
                    // call the readAll method from the DBlogic class and assign the return to a map
                    Map<String, String> all = fetchAll.readAll();

                    // set the attribute allStudents to the map all
                    request.setAttribute("allStudents", all);
                    request.setAttribute("statusFromDB", "");

                    //forwad the request
                    request.getRequestDispatcher("adminOperations.jsp").forward(request, response);
                } catch (SQLException e)

                {
                    e.printStackTrace();
                } catch (ServletException e)

                {
                    e.printStackTrace();
                } catch (IOException e)

                {
                    e.printStackTrace();
                }
            } else {
                //if user is not an admin, invalidate the session and they should log in
                session.invalidate();
                try {
                    response.sendRedirect("index.jsp");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }

        else
        {
            try {
                response.sendRedirect("index.jsp");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
