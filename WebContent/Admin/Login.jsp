<%@page import="Control.admin.UserControl"%>
<%@page import="model.User"%>
<%

	UserControl con=new UserControl();
	String name=request.getParameter("Name");
	String pass=request.getParameter("Pass");
	User user=con.DoLogin(name, pass);
	
	if(user==null){
		out.print("-1");
	}else{
		if(user.getId()==0){	out.print("0");}
		else{
		session.setAttribute("Admin",user.getName());
		out.print("1"); }
	}
	
	
	
%>