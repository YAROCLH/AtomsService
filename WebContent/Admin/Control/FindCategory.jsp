<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="Control.admin.CategoryControl"%>
<%@page import="model.Category"%>
<%

	if(session.getAttribute("Admin")==null){response.sendRedirect("../../index.html");}
	else{
		CategoryControl con=new CategoryControl();
		int id=Integer.parseInt(request.getParameter("idCategory"));
		String res=con.getCategory(id);
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.getWriter().write(res);
		response.getWriter().flush();
		response.getWriter().close();
	}
%>