<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="Control.admin.ChallengeControl"%>
<%@page import="model.Challenge"%>
<%

	if(session.getAttribute("Admin")==null){response.sendRedirect("index.html");}
	else{
	ChallengeControl con=new ChallengeControl();
	String id="";
	String action=request.getParameter("action");
	id=request.getParameter("id");
	response.addHeader("Access-Control-Allow-Origin", "*");
	if(action.equals("find")){
		String res=con.listChallengesbyCatego(Integer.parseInt(id));
		out.print(res);
	}else{
		String res=con.getChallenge(Integer.parseInt(id));
		response.getWriter().write(res);
		response.getWriter().flush();
		response.getWriter().close();
	}
	}
%>