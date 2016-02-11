<%@page import="Services.ToJson"%>
<%@page import="Services.Decrypt"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%	
	response.addHeader("Access-Control-Allow-Origin", "*");
	ToJson tj=new ToJson();
	Decrypt dec=new Decrypt();
	//String idCategory=dec.Decode(request.getParameter("idCategory"));
	//String idUser=dec.Decode(request.getParameter("idUser"));
	String idUser=request.getParameter("idUser");
	String idCategory=request.getParameter("idCategory");
	String res=tj.getChallenges(Integer.parseInt(idUser),Integer.parseInt(idCategory));
	response.getWriter().write(res);
	response.getWriter().flush();
	response.getWriter().close();
%>