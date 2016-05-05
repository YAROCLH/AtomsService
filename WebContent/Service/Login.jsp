<%@page import="Control.Services.Control"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%	
	response.addHeader("Access-Control-Allow-Origin", "*");
	Control con=new Control();
	String intranetID=con.Decode(request.getParameter("intranetID"));
	String res=con.getLogin(intranetID);
	response.getWriter().write(res);
	response.getWriter().flush();
	response.getWriter().close();
%>
