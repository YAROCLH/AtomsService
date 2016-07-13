<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="Control.admin.ChallengeControl"%>
<%@page import="model.Challenge"%>
<%

	if(session.getAttribute("Admin")==null){response.sendRedirect("index.html");}
	else{
		response.addHeader("Access-Control-Allow-Origin", "*");
		ChallengeControl con=new ChallengeControl();
		String res="";
		String intranet=request.getParameter("intranet");
		String category=request.getParameter("category");
		if(intranet.equals(null)||intranet.equals("")){
			res=con.getAllChallengesbyCategory(Integer.parseInt(category));
		}else{
			res=con.getChallengesbyUserCategory(intranet,Integer.parseInt(category));
		}
		
			response.getWriter().write(res);
			response.getWriter().flush();
			response.getWriter().close();
	}
%>