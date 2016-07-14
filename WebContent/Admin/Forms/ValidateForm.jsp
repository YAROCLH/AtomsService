<%@page import="Control.admin.*"%>


<%		
		if(session.getAttribute("Admin")==null){
		out.print("Session Expired");
	}
	else{
		CategoryControl con=new CategoryControl();
		String options=con.ListALLCategories();
 %>
 
 <html>
 	<body>
 		<input type="text" placeholder="User Name" id="Validate_User" autocomplete="off" onkeyup="ValFindUser()"> 
 		<select id="validateCategory">
			  <%=options %>
		</select>
 		<input type="button" value="Find" onclick="findCompletedChallenges()"> <br>
 		<div class="hint">
            <ul id="userList" class="form-control"></ul>
        </div><br>
       <div class="list-group validate_list">
            <ul></ul>
       </div>
       <img id="Completed_Pic">      
       <div>
       		<label>REJECT CHALLENGE</label>
       		<input type="checkbox" id="prevent_validate" onclick="PreventDelete()">
       		<input type="button"  value="Delete" onclick="DeleteCompleted()" class="deleteChallenge"> 
       </div>
 	</body>
 
 
 </html>


	<% } %>