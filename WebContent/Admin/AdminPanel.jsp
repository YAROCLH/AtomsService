    
<%
	String User=null;
	if(session.getAttribute("Admin")==null){response.sendRedirect("../index.html");}
	else{
	 	User=session.getAttribute("Admin").toString();
	}
	

 %>
		<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<html>
		<head>
		<link rel="stylesheet" href="../style.css" />
		<script type="text/javascript" src="../js/JQuery-2.1.4.js"></script>
		<script type="text/javascript" src="../js/AdminPanel.js"></script>
		<script type="text/javascript" src="../js/User.js"></script>
		<script type="text/javascript" src="../js/Category.js"></script>
		<script type="text/javascript" src="../js/Challenge.js"></script>
		<title>Admin Panel</title>
		</head>
		<body>
			<h1><%=User%></h1><a href="Logout">Log Out</a>
			<button value="User" onclick="show(this.value)">User</button>
			<button value="Challenge" onclick="show(this.value)">Challenge</button>
			<button value="Category" onclick="show(this.value)">Category</button>
			<div id="container" align="center"></div><br>
		</body>
		
		
		</html>
		
		