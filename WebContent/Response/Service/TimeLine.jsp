<%@page import="control.services.Control"%>
<%@page import="utils.Utils"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%	
	response.addHeader("Access-Control-Allow-Origin", "*");
	response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
	Control con=new Control();
	Utils utils=new Utils();
	String pageN=utils.Decode(request.getParameter("Page"));
	String date=utils.Decode(request.getParameter("Date"));
	String res=con.getTimeLine(Integer.parseInt(pageN),date);
	response.getWriter().write(res);
	response.getWriter().flush();
	response.getWriter().close();
%>