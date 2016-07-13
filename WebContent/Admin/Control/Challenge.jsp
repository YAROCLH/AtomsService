<%@page import="Control.admin.ChallengeControl"%>
<%@page import="model.Challenge"%>

<%
	if(session.getAttribute("Admin")==null){response.sendRedirect("../index.html");}
	else{
			ChallengeControl con= new ChallengeControl();
			Challenge challenge = new Challenge();
			String action=request.getParameter("action");
			challenge.setName(request.getParameter("name"));
			challenge.setIdCategory(Integer.parseInt(request.getParameter("idCategory")));
			challenge.setShort(request.getParameter("short"));
			challenge.setLong(request.getParameter("long"));
			challenge.setPoints(Integer.parseInt(request.getParameter("points")));
			challenge.setId(Integer.parseInt(request.getParameter("idChallenge")));
			challenge.setType(Integer.parseInt(request.getParameter("type")));
			challenge.setPosition(1);
			boolean res=false;
		if(action.equals("create")){
			res=con.newChallenge(challenge);
		}else{
		  if(action.equals("update"))	{
			res=con.updateChallenge(challenge);
		  }else if(action.equals("delete")){
		  	res=con.deleteChallenge(challenge);
		  }		
		}
	if(res){out.print(1);}
	else{out.print(-1);}
		
	}
%>