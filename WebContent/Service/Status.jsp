<%@page import="Control.Services.ToJson"%>
<%@page import="Control.Services.Decrypt"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%	
	response.addHeader("Access-Control-Allow-Origin", "*");
	ToJson tj=new ToJson();
	Decrypt dec=new Decrypt();
	String idUser=dec.Decode(request.getParameter("idUser"));
	//String idUser=request.getParameter("idUser");
	String res=tj.getMyBadges(Integer.parseInt(idUser));
	response.getWriter().write(res);
	response.getWriter().flush();
	response.getWriter().close();
%>