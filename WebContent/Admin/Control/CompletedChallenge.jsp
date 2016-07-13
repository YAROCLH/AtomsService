<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="Control.admin.ChallengeControl"%>
<%@page import="model.Challenge"%>
<%

	if(session.getAttribute("Admin")==null){response.sendRedirect("index.html");}
	else{
		response.addHeader("Access-Control-Allow-Origin", "*");
		ChallengeControl con=new ChallengeControl();
		
		String idCompleted=request.getParameter("idCompleted");
		String res=con.getImage(Integer.parseInt(idCompleted));
			response.getWriter().write(res);
			response.getWriter().flush();
			response.getWriter().close();
	}
%>