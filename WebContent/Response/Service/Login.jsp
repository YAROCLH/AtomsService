<%@page import="control.services.Control"%>
<%@page import="utils.Utils"%>Control"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%	
	response.addHeader("Access-Control-Allow-Origin", "*");
	response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
	Control con=new Control();
	Utils utils=new Utils();
	String intranetID=utils.Decode(request.getParameter("intranetID"));
	String res=con.getLogin(intranetID);
	response.getWriter().write(res);
	response.getWriter().flush();
	response.getWriter().close();
%>
