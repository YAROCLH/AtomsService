<%@page import="Control.Services.Control"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%	
	response.addHeader("Access-Control-Allow-Origin", "*");
	String res="1";
	response.getWriter().write(res);
	response.getWriter().flush();
	response.getWriter().close();
%>