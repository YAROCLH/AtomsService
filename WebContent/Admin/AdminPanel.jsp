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
    <link rel="stylesheet" href="../css/bootstrap.min.css" />
    <link rel="stylesheet" href="../css/bootstrap-theme.min.css" />
    <link rel="stylesheet" href="../css/style.css" />
    
    <script type="text/javascript" src="../js/JQuery-2.1.4.js"></script>
    <script type="text/javascript" src="../js/bootstrap.js"></script>
    <script type="text/javascript" src="../js/AdminPanel.js"></script>
    <script type="text/javascript" src="../js/FormControllers/User.js"></script>
    <script type="text/javascript" src="../js/FormControllers/Category.js"></script>
    <script type="text/javascript" src="../js/FormControllers/Challenge.js"></script>
    <script type="text/javascript" src="../js/FormControllers/Validate.js"></script>
    <title>Atoms -  Admin Panel</title>
</head>

<body>
	<nav class="MenuBackground backgroundMain navbar navbar-default">
		<div class="container-fluid MenuBackground">
		    <div class="navbar-header">
		      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
			      	<span class="sr-only">Toggle navigation</span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
		      </button>
		      <img src="../images/logo.png" height="70px" class="navbar-brand img-responsive main" />
		    </div>
		
		    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		      <ul class="nav navbar-nav navbar-right">
		        <li class=""><a class="menuOptions" value="User" onclick="show('User')" >Users</a></li>
		        <li class=""><a class="menuOptions" value="Challenge" onclick="show('Challenge')" >Challenges</a></li>
		        <li class=""><a class="menuOptions" value="Category" onclick="show('Category')">Category</a></li>
		        <li class=""><a class="menuOptions" value="Validate" onclick="show('Validate')">Validate</a></li>
		        <li class=""><a class="menuOptions logout" href="Logout"><%=User%> Log Out</a></li>
		      </ul>
		    </div>
	  </div>
	</nav>

 
    <div id="container" align="center" class="container-centered loginContainer">
	    <div class="row-centered">
		    <div class="centering">
			    <div class="row">
			    	<div class="col-lg-offset-4 col-lg-4 text-center">
			    			<img src="../images/logo-color.png" height="100%" class="img-responsive main" />
			    	</div>
			    </div>
		    </div>
	    </div>
    </div>
	    
    
</body>

</html>