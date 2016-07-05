<%@page import="Control.Services.Control"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%	
	response.addHeader("Access-Control-Allow-Origin", "*");
	response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
	Control con=new Control();
	String limit=con.Decode(request.getParameter("Limit"));
	System.out.print(limit);
	if(limit==null){limit="1";}
	String res=con.getTimeLine(Integer.parseInt(limit));
	response.getWriter().write(res);
	response.getWriter().flush();
	response.getWriter().close();
%>