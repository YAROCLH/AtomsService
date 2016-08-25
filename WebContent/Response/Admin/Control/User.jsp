<%@page import="control.admin.UserControl"%>
<%@page import="model.User"%>

<%		if(session.getAttribute("Admin")==null){response.sendRedirect("../index.html");}
	else{
		String action=request.getParameter("action");
	 	if(action.equals("delete")){
			
		}else{
			String name=request.getParameter("name");
			String intranetid=request.getParameter("id");
			String type=request.getParameter("type");
			String userid=request.getParameter("userId");
			UserControl con=new UserControl();
			User user=new User();
			user.setName(name);
			user.setIntranetId(intranetid);
			user.setType(Integer.parseInt(type));
			user.setId(Integer.parseInt(userid));
			if(action.equals("create")){
				boolean res=false;
				if(res){out.print(1);}
				else{out.print(-1);}
			}else if(action.equals("update")){
				boolean res=con.UpdateUser(user);
				if(res){out.print(1);}
				else{out.print(-1);}
			}
		
		
		
		}
		
	}
%>