<%@page import="control.services.Control"%>
<%@page import="utils.Utils"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%	response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
	response.addHeader("Access-Control-Allow-Origin", "*");
	Control con=new Control();
	String res=con.getTop10();
	response.getWriter().write(res);
	response.getWriter().flush();
	response.getWriter().close();
%>