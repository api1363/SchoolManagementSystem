<%
String username1=request.getParameter("username");
String password1=request.getParameter("password");
if(username1.equalsIgnoreCase("student")&& password1.equalsIgnoreCase("student"))
{
	response.sendRedirect("studentHome.jsp");
}
else
	response.sendRedirect("errorAdminLogin.html");
%>