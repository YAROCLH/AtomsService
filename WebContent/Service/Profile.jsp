<%@page import="Control.Services.Control"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%	
	response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
	response.addHeader("Access-Control-Allow-Origin", "*");
	Control con=new Control();
	String profile_pic="";
	String IntranetID=con.Decode(request.getParameter("IntranetID"));
	
	profile_pic="http://images.w3ibm.mybluemix.net/image/"+IntranetID+"?s=110";
	byte[]imageInByte=con.getPic(profile_pic);
	String b64=con.encodeFile(imageInByte);
	out.print(b64);
%>
