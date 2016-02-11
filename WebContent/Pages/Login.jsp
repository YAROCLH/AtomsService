

<%@page import="Services.ToJson"%>
<%@page import="Services.Decrypt"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%	
	response.addHeader("Access-Control-Allow-Origin", "*");
	String name=request.getParameter("UserName");
	String pass=request.getParameter("UserPass");
	ToJson tj=new ToJson();
	Decrypt dec=new Decrypt();
	//String res=tj.getLogin(name,pass);
	String res=tj.getLogin(dec.Decode(name),dec.Decode(pass));
	//String res=tj.getLogin(dec.DecryptData(name),dec.DecryptData(pass));
	response.getWriter().write(res);
	response.getWriter().flush();
	response.getWriter().close();
%>
