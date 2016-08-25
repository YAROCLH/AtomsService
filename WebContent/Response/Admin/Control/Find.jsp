<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="control.admin.FindControl"%>
<%@page import="model.Category"%>
<%

	if(session.getAttribute("Admin")==null){response.sendRedirect("../../index.html");}
	else{
		String find=request.getParameter("find");
		String criteria=request.getParameter("criteria");
		String toFind=request.getParameter("ToFind");
		String res=null;
		FindControl con=new FindControl();
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.getWriter().write(res);
		response.getWriter().flush();
		response.getWriter().close();
	}
%>