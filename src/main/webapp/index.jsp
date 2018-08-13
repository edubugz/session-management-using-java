<html>
<body style="margin-left: auto; margin-right: auto; text-align: center ">
<h2> student login manager </h2>

<a href=login.jsp >login to the system </a> || <a href=register.jsp >register as a user</a>

<% if(request.getAttribute("status") != null ){ %>
<br/> <%= request.getAttribute("status") %>
<% }else{ %>
<a> </a>
<%} %>

<br/>


</body>
</html>
