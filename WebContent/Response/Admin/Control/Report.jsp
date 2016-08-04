<%@page import="control.admin.ReportControl"%>
<%@ page import="java.io.*" %>

	<%	
	
	if(session.getAttribute("Admin")==null){
	System.out.print("Not permited");
	response.sendRedirect("../../index.html");}
	else{
		ReportControl con=new ReportControl();
		response.setContentType("application/csv");
		response.setHeader("content-disposition","filename=Atoms.csv"); // Filename
		PrintWriter outx = response.getWriter();
		outx.println(con.getFullReport());
		outx.flush();
		outx.close();
	}
	%>