<%@page import="Control.Services.ToJson"%>
<%@page import="Control.Services.Decrypt"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%	
	response.addHeader("Access-Control-Allow-Origin", "*");
	ToJson tj=new ToJson();
	String res=tj.getCategories();
	response.getWriter().write(res);
	response.getWriter().flush();
	response.getWriter().close();
%>