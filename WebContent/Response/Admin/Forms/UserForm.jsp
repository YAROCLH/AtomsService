<%
	if(session.getAttribute("Admin")==null){
		out.print("Session Expired");
	}
	else{
		
 %>	    

<div class="row">
   		<h2 class="text-center AppColor"><strong>Manage users</strong> </h2>
   		<br />
</div>

<div class="row">
   	<div class="col-lg-offset-4 col-lg-4">
   		<div class="form-group">
   			<label id="userLabel" class="AppColor">New user intranet ID:</label>
		  	<input type="text" placeholder="Intranet Id" id="userId" onkeyup="findUser()" class="editUser form-control">
		</div>
   	</div>
</div>


<div class="row">
   	<div class="col-lg-offset-4 col-lg-4">
   		<div class="form-group">
   			<label id="userLabel" class="AppColor">User Name:</label>
		  	<input type="text" placeholder="User Name" id="userName" class="editUser form-control">
		</div>
   	</div>
</div>

<div class="row">
   	<div class="col-lg-offset-4 col-lg-4">
   		<div class="row">
		  <div class="col-lg-6">
		    <div class="input-group">
		      <span class="input-group-addon">
		        <input type="checkbox" id="toDelete" class="toDelete" onclick="toDelete()" aria-label="...">
		      </span>
		      <input type="text" readOnly value="Delete User" class="form-control" aria-label="...1">
		      
		    </div><!-- /input-group -->
		  </div><!-- /.col-lg-6 -->
		  <div class="col-lg-6">
		    <div class="input-group">
		      <span class="input-group-addon">
		        <input type="checkbox" id="isAdmin" class="editUser" aria-label="...1" >
		      </span>
		      <input type="text" class="form-control" readOnly value="Admin" aria-label="...">
		    </div><!-- /input-group -->
		  </div><!-- /.col-lg-6 -->
		</div><!-- /.row -->
   	</div>
   	
</div>
<br />
<div class="row">
	<div class="col-lg-offset-4 col-lg-4 text-center">
		<input id="UserSave" type="button" value="Save changes" class="btn btn-group ActiveRed" onclick="userSave()">
	</div>	
</div>	
<br />


<div class="row">
<div class="col-lg-12 text-center">
	<h5 class="AppColor">Suggested user</h5>
</div>
	<div class="col-lg-offset-4 col-lg-4">
		<div class="hint">
            <ul id="userList" class="list-group">
                
            </ul>
        </div>
	</div>
</div>
		
    <br />
		
		<% } %>
		