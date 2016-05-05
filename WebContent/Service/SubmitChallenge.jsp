<%@page import="Control.Services.Control"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%	response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
	response.addHeader("Access-Control-Allow-Origin", "*");
	Control con=new Control();
	String idChallenge=con.Decode(request.getParameter("idChallenge"));
	String idUser=con.Decode(request.getParameter("idUser"));
	String Attach=con.Decode(request.getParameter("Attach"));
	String Photo=request.getParameter("Photo");
	String res=con.SubmitChallenge(Integer.parseInt(idUser), Integer.parseInt(idChallenge), Attach, Photo);
	response.getWriter().write(res);
	response.getWriter().flush();
	response.getWriter().close();
%>