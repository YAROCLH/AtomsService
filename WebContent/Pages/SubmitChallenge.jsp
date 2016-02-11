<%@page import="Services.ToJson"%>
<%@page import="Services.Decrypt"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%	
	response.addHeader("Access-Control-Allow-Origin", "*");
	ToJson tj=new ToJson();
	Decrypt dec=new Decrypt();
	/*String idChallenge=dec.Decode(request.getParameter("idChallenge"));
	String idUser=dec.Decode(request.getParameter("idUser"));
	String Attach=dec.Decode(request.getParameter("Attach"));*/
	String idChallenge=request.getParameter("idChallenge");
	String idUser=request.getParameter("idUser");
	String Attach=request.getParameter("Attach");
	String Photo=request.getParameter("Photo");
	String res=tj.SubmitChallenge(Integer.parseInt(idUser), Integer.parseInt(idChallenge), Attach, Photo);
	response.getWriter().write(res);
	response.getWriter().flush();
	response.getWriter().close();
%>