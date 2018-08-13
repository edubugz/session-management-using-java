package com.example.servlet;

import com.example.dblogic.DBlogic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "deleteUser", urlPatterns = "/deleteUser")
public class DeleteUser extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response){

        HttpSession session = request.getSession(false);

        if(session.getAttribute("admin")!=null)
        {
            DBlogic toDB = new DBlogic();
            String toBeDeleted = request.getParameter("id");

            try {
                String statusFromDB = toDB.deleteUser(toBeDeleted);
                System.out.println(statusFromDB);
                request.getSession(false).setAttribute("statusFromDB", statusFromDB);
                request.getRequestDispatcher("adminOperations.jsp").forward(request, response);
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

        else
        {
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
