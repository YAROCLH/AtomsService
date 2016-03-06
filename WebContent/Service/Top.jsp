<%@page import="Control.Services.ToJson"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%	
	response.addHeader("Access-Control-Allow-Origin", "*");
	ToJson tj=new ToJson();
	String res=tj.getTop10();
	response.getWriter().write(res);
	response.getWriter().flush();
	response.getWriter().close();
%>