<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="control.admin.UserControl"%>
<%@page import="model.User"%>
<%

	if(session.getAttribute("Admin")==null){response.sendRedirect("index.html");}
	else{
	UserControl con=new UserControl();
	String user="";
	String action=request.getParameter("action");
	user=request.getParameter("name");
	response.addHeader("Access-Control-Allow-Origin", "*");
	if(action.equals("find")){
		String res=con.FindUser(user);
		out.print(res);
	}else{
		String res=con.getUserbyIntranet(user);
		response.getWriter().write(res);
		response.getWriter().flush();
		response.getWriter().close();
	}
	}
%>