<%@page import="Control.Services.Control"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%	
	response.addHeader("Access-Control-Allow-Origin", "*");
	response.getWriter().write(1);
	response.getWriter().flush();
	response.getWriter().close();
%>
