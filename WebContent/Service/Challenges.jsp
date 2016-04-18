<%@page import="Control.Services.Control"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%	
	response.addHeader("Access-Control-Allow-Origin", "*");
	Control con=new Control();
	String idCategory=con.Decode(request.getParameter("idCategory"));
	String idUser=con.Decode(request.getParameter("idUser"));
	String res=con.getChallenges(Integer.parseInt(idUser),Integer.parseInt(idCategory));
	response.getWriter().write(res);
	response.getWriter().flush();
	response.getWriter().close();
%>