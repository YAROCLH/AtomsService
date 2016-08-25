<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="control.admin.ChallengeControl"%>
<%@page import="model.Challenge"%>
<%

	if(session.getAttribute("Admin")==null){response.sendRedirect("index.html");}
	else{
		response.addHeader("Access-Control-Allow-Origin", "*");
		ChallengeControl con=new ChallengeControl();
		int idCompleted=Integer.parseInt(request.getParameter("idCompleted"));
		String res="";
		if(request.getParameter("action").equals("PHOTO")){
			System.out.println(idCompleted);
			res=con.getImage(idCompleted);
		}else{	
			if(con.RejectChallenge(idCompleted)){	res="1";	}
			else{	res="0";}		
		}
			response.getWriter().write(res);
			response.getWriter().flush();
			response.getWriter().close();
	}
%>