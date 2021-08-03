<%@page import ="connection.ConnectionProvider"%>
<%@page import ="java.sql.*"%>
<%
String course=request.getParameter("course");
String studentid=request.getParameter("id");
String name=request.getParameter("name");
try{
	Connection con=ConnectionProvider.getCon();
	Statement st=con.createStatement();
	st.executeUpdate("insert into student values('"+course+"','"+studentid+"','"+name+"')");
	response.sendRedirect("adminHome.jsp");
}
catch(Exception e)
{
	out.println(e);
}
%>
