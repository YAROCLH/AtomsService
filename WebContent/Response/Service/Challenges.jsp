<%@page import="control.services.Control"%>
<%@page import="utils.Utils"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%	
	response.addHeader("Access-Control-Allow-Origin", "*");
	response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
	Control con=new Control();
	Utils utils=new Utils();
	String idCategory=utils.Decode(request.getParameter("idCategory"));
	String idUser=utils.Decode(request.getParameter("idUser"));
	String res=con.getChallenges(Integer.parseInt(idUser),Integer.parseInt(idCategory));
	response.getWriter().write(res);
	response.getWriter().flush();
	response.getWriter().close();
%>