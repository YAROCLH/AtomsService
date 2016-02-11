<%@page import="Services.ToJson"%>
<%@page import="Services.Decrypt"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%	
	response.addHeader("Access-Control-Allow-Origin", "*");
	ToJson tj=new ToJson();
	Decrypt dec=new Decrypt();
	String id=dec.Decode(request.getParameter("idUser"));
	//String id=request.getParameter("idUser");
	String res=tj.getRank(Integer.parseInt(id));
	response.getWriter().write(res);
	response.getWriter().flush();
	response.getWriter().close();
%>