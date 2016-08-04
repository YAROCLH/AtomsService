<%@page import="control.services.Control"%>
<%@page import="utils.Utils"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%	response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
	response.addHeader("Access-Control-Allow-Origin", "*");
	Control con=new Control();
	Utils utils=new Utils();
	String idChallenge=utils.Decode(request.getParameter("idChallenge"));
	String idUser=utils.Decode(request.getParameter("idUser"));
	String Attach=utils.Decode(request.getParameter("Attach"));
	String Photo=request.getParameter("Photo");
	String res=con.SubmitChallenge(Integer.parseInt(idUser), Integer.parseInt(idChallenge), Attach, Photo);
	response.getWriter().write(res);
	response.getWriter().flush();
	response.getWriter().close();
%>