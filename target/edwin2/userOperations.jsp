<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 8/8/2018
  Time: 8:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>user side <DB></DB> operations</title>
</head>

<body style="margin-left: auto; margin-right: auto; text-align: center ">
<center>


<%--this code checks if a session exist if not it won't display the body of the jsp file--%>
<%if(request.getSession(false).getAttribute("admin")!=null ) { %>


<%--if a session exist, this code checks if the session is for a normal user . if not
the user is directed to log in as a normal user--%>

<%if(request.getSession(false).getAttribute("admin").toString().equals("no")) { %>
<h1> user operations page</h1>
<br/>

<%-- diplays the form which allows a user to edit thei details --%>
edit your details section: <br/>
<form action="editUser" method="post">

    new id <input type="text" name="newId"/>  :or:
    new name <input type="text" name="newName"/> <br/><br/>
    <input type="submit">

</form>
    <br/>

<%--once the user is edited, the editUser sends back a status report held in an array
that array is parsed and its items displayed here--%>

<%--its the same used in admin operations--%>
<% String [] status =(String[]) request.getSession(false).getAttribute("status");%>
<% if( status !=null && status.length >0) {%>
<br/>

<%=status[0]   %> <br/>
<%=status[1]   %> <br/>
<%=status[2]   %> <br/>

<%-- the array is set to null so that if the page is if refreshed it wont redisplay --%>

<%request.getSession(false).setAttribute("status",status = null);%>

<%}%>

<br/>

<%} else{%>
<br/>

<%--this message is displayed if user has a session which is not of a normal user -  which is admin --%>
 you can't operate on this page while logged in with admin privileges,
    please log out below and log in as a normal user

<br/>

<%}%>
======================
<a href="logout.jsp">LogOut</a>
======================
<%} else {%>

<%--this message is displayed if the user doesn't have a session--%>
<br/> you need to be logged in to operate on this page.
please proceed to <a href=login.jsp > login</a>

<% }%>
</center>
</body>
</html>
