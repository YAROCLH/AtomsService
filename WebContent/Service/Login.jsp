<%@page import="Control.Services.Control"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%	
	response.addHeader("Access-Control-Allow-Origin", "*");
	String name=request.getParameter("UserName");
	String pass=request.getParameter("UserPass");
	Control con = new Control();
	String res=con.getLogin(con.Decode(name),con.Decode(pass));
	response.getWriter().write(res);
	response.getWriter().flush();
	response.getWriter().close();
%>
