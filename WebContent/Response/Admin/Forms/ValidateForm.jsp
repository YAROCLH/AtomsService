<%@page import="control.admin.*"%>


<%		
		if(session.getAttribute("Admin")==null){
		out.print("Session Expired");
	}
	else{
		CategoryControl con=new CategoryControl();
		String options=con.ListALLCategories();
 %>
 
  <div class="container">
   <div class="row">
   		<h2 class="text-center AppColor"><strong>Validate Challenge</strong> </h2>
   		<hr>
   </div>
   <div class="row">
   		<div class="col-lg-offset-4 col-lg-4">
   			<input type="text" class="form-control" placeholder="Search by intranetID" id="Validate_User" autocomplete="off" onkeyup="ValFindUser()">
   			<br />
   			
   		</div>
   		
   		<div class="col-lg-offset-4 col-lg-4">
   			<select class="form-control" id="validateCategory">
				  <%=options %>
			</select>
			<br />
   		</div>
   		<div class="col-lg-offset-4 col-lg-4">
   			<div class="col-lg-offset-4 col-lg-4">
   				<input type="button" value="Find" class="form-control btn-danger" onclick="findCompletedChallenges()">
   			</div>
   			<br />
   		</div>
   </div>
	<div class="row">
   		<h3 class="text-center AppColor"><strong>Challenges List</strong> </h3>
   		<hr>
   </div>
   
   
 <div class="row">
<div class="col-lg-12 text-center">
	<h5 class="AppColor">Suggested user</h5>
</div>
	<div class="col-lg-offset-4 col-lg-4">
		<div class="hintV">
            <ul id="userList" class="list-group">
                
            </ul>
        </div>
	</div>
</div>
   
	<div class="row">
   		<div class="col-lg-6">
		   		<div class="list-group validate_list">
		          <ul>
		          </ul>
		     	</div>
   		</div>
   		
   		<div class="col-lg-6">
   				<div class="col-lg-12 text-center">
   					<img id="Completed_Pic" style="display:inline;" class="img-responsive"> 
   					<br />
   				</div>
   				<div class="col-lg-12" id="DeleteChanllengesPanel" style="display:none;">
	   				<div class="row">
	   						<div class="col-lg-offset-3 col-lg-6">
	   						<h2 id="NoPhoto"></h2>
	   						<h3 id="Attach"></h3>
	   							<div class="col-lg-12 text-left">
	   							    <h5><span class="AppColor"><strong>*</strong></span>Reject this challenges</h5>
	   							</div>
	   							<div class="col-lg-12 text-left">
		   							<div class="checkbox">
								      <label><input type="checkbox" id="prevent_validate" value="Confirm to delete" onclick="PreventDelete()">Confirm to delete</label>
								    </div>
	   							    
	   							</div>
	   							<div class="col-lg-12">
	   							    <input type="button" value="Delete" onclick="DeleteCompleted()" class="deleteChallenge form-control btn-danger">
	   							</div>
	   						</div> 
				     </div>
   				</div>
   				<br />
   		</div>
   </div>     
</div>


	<% } %>