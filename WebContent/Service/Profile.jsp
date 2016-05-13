<%@page import="Control.Services.Control"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%	
	
	Control con=new Control();
	/*String profile_pic="";
	String IntranetID=con.Decode(request.getParameter("IntranetID"));
	String serial=con.getSerial(IntranetID);
	if(serial!=null){
		profile_pic="http://dpev027.innovate.ibm.com:10001/image/"+serial+".jpg?s=55";
	}
	
	out.print("<img src='"+profile_pic+"'>");*/
	out.print(con.isInternetReachable());
%>
