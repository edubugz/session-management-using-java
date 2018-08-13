<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 8/8/2018
  Time: 8:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>

    <title>admin</title>

</head>
<body style="margin-left: auto; margin-right: auto; text-align: center ">

<%--this code checks if a session exist if not it won't display the body of the jsp file--%>

<%if(request.getSession(false).getAttribute("admin") !=null) { %>

<%--if a session exist, this code checks if the session has admin privileges. if not
the user is directed to log in as an admin --%>
<%if(request.getSession(false).getAttribute("admin").toString().equals("yes")) { %>


<h1> admin operations page </h1>

<%--this is a link to the seall servelet tha will display all the users in the db if clicked--%>
<a href="<%=request.getContextPath() %>/seeall ">  see/manage users  </a>

<br/>

<%--when the seall link above is clicked, the see all servelet will fetch all the users from the DB
and put them into a map with their id as the key and name and isAdmin both as values. then
forwad the attribute back to this page for displayin to the admin--%>

<%--check if the sent object is null--%>
<% if(request.getAttribute("allStudents") != null  ){ %>
<br/>
id &nbsp&nbsp&nbsp&nbsp&nbsp name &nbsp&nbsp&nbsp&nbsp&nbsp isadmin<br/>

<%--cast it to a map object--%>
<% Map<String,String> userList=(HashMap<String,String>) request.getAttribute("allStudents");

//loop through the map and diplay the items in it ( keys and values)
for (Map.Entry<String, String> entry : userList.entrySet())
    { %>

   <%= entry.getKey()%> <%=entry.getValue()%> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                             <%--this here is a link to deleteUser servlet.
                                             once a key and value pair is displayed, the delete
                                             link will be appended at the end so that the admin can be able to delete
                                             a single user.
                                             the id of the user is sent in the url too--%>
<a href="<%=request.getContextPath() %>/deleteUser?id=<%=entry.getKey()%>"> delete </a>  <br/>

------------------------------------------------------------------<<br/>

 <%   }%>


<br/>

<%--after the users are displayed. the admin is presented with a table to help in
editing the user which is serviced by the edit user servlet--%>
edit user section: <br/><br/>
    <form action="editUser" method="post">
    enter user to edit id from the list above <input type="text" name="identifier"/>

    new id <input type="text" name="newId"/>    :or:
    new name <input type="text" name="newName"/>  <br/><br/>

    <input type="submit">

    </form> <br/>
<%}%>
 <%--once the user is edited, the editUser sends back a status report held in an array
 that array is parsed and its items displayed here--%>
<% String [] status =(String[]) request.getSession(false).getAttribute("status");%>
<% if( status !=null && status.length >0) {%>
<br/>

<%=status[0]   %> <br/>
<%=status[1]   %> <br/>
<%=status[2]   %> <br/>

<%-- the array is set to null so that if the page is if refreshed it wont redisplay --%>
<%request.getSession(false).setAttribute("status",status = null);%>

<%}%>

<%--this attribute reports back on the status if a user is deleted --%>
<% if(request.getSession(false).getAttribute("statusFromDB") !=null){%>
<%=request.getSession(false).getAttribute("statusFromDB")%>
<%-- it is set to null so that if the page is if refreshed it wont redisplay --%>
<%request.getSession(false).setAttribute("statusFromDB","");%>
<%}%>

<%} else{%>
<br/>
<%--if the user doesn't have admin privileges, display this message--%>
you need admin privileges to be able to operate on this page
please logout below and login with admin privileges
<%}%>

<br/>
======================
<a href="logout.jsp">LogOut</a>
======================


<%} else {%>

<%--if the user doesn't have a session attached to them, display this message --%>
<br/> you need to be logged in  to operate on this page.
please proceed to <a href=login.jsp >login </a>

<%}%>

</body>
</html>
