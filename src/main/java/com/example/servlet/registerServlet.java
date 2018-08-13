package com.example.servlet;

import com.example.dblogic.DBlogic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static com.example.dblogic.DBlogic.getDBConnection;

@WebServlet(name = "registerServlet", urlPatterns = "/register")
public class registerServlet extends HttpServlet {

    DBlogic dbcon = new DBlogic();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getDBConnection();
        String id = request.getParameter("id");
        String name = request.getParameter("name");


            try
            {
                String [] dbStatus = dbcon.insertStudent(id,name);
                request.setAttribute("status",dbStatus[1]);

                request.getRequestDispatcher("index.jsp").forward(request,response);

            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

    }


}
