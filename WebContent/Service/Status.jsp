<%@page import="Control.Services.Control"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%	
	response.addHeader("Access-Control-Allow-Origin", "*");
	Control con=new Control();
	String idUser=con.Decode(request.getParameter("idUser"));
	String res=con.getMyScores(Integer.parseInt(idUser));
	response.getWriter().write(res);
	response.getWriter().flush();
	response.getWriter().close();
%>