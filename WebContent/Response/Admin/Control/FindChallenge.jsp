<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="control.admin.ChallengeControl"%>
<%@page import="model.Challenge"%>
<%

	if(session.getAttribute("Admin")==null){response.sendRedirect("index.html");}
	else{
	ChallengeControl con=new ChallengeControl();
	String id="";
	String res;
	String action=request.getParameter("action");
	String criteria=request.getParameter("criteria");
	String name=request.getParameter("name");
	String category=request.getParameter("category");
	id=request.getParameter("id");
	if(action.equals("find")){
		System.out.println("Find: "+category+name+criteria);
		if(criteria.equals("byname")||id==null){
			
			res=con.findChallengebyName(name,Integer.parseInt(category));
		}else{
			res=con.listChallengesbyCatego(Integer.parseInt(id));
		}
	}else{
		res=con.getChallenge(Integer.parseInt(id));
	}
		response.getWriter().write(res);
		response.getWriter().flush();
		response.getWriter().close();
	}
%>