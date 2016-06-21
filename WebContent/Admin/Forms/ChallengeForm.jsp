<%@page import="Control.admin.ChallengeControl"%>
<%@page import="Control.admin.CategoryControl"%>

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
    		<h2 class="text-center AppColor"><strong>Challenges</strong> </h2>
    </div>
    <br />
</div>

<div class="row">
	<div class="col-lg-offset-3 col-lg-6">
		<div class="btn-group btn-group-justified" role="group" aria-label="...">
			  <div class="btn-group " role="group">
			  	<button class="btn ActiveRed btn-default ActiveRed" onclick="challengeCreateMode()">Create</button>
			  </div>
			  <div class="btn-group " role="group">
			    <button class="btn ActiveRed btn-default ActiveRed" onclick="challengeEditMode()">Edit</button>
			  </div>
		</div>
	</div>
</div>

<div class="container">
	<div class="row">
    		<h2 class="text-center AppColor" id="challengeMode"><strong>Create new challenges</strong> </h2>
    </div>
    <br />
</div>

<div class="row">
   	<div class="col-lg-offset-4 col-lg-4">
   		<div class="form-group">
   			<label class="AppColor">Name:</label>
		  	<input type="text" placeholder="Name of the Challenge" id="challengeName" class="editChallenge form-control" />
		</div>
   	</div>
</div>

<div class="row">
   	<div class="col-lg-offset-4 col-lg-4">
   		<div class="form-group">
   			<label class="AppColor">Short Description:</label>
		  	<input type="text" placeholder="Short Description" id="challengeShort" class="editChallenge form-control" />
		</div>
   	</div>
</div>

<div class="row">
   	<div class="col-lg-offset-4 col-lg-4">
   		<div class="form-group">
   			<label class="AppColor">Long Description:</label>
		  	<textarea placeholder="Long Description" id="challengeLong" class="editChallenge form-control"></textarea>
		</div>
   	</div>
</div>

<div class="row">
   	<div class="col-lg-offset-4 col-lg-4">
   		<div class="form-group">
   			<label class="AppColor">Points:</label>
		  	<input type="number" onkeyup="isNumber(this)" id="challengePoints" class="editChallenge form-control" />
		</div>
   	</div>
</div>

<div class="row">
   	<div class="col-lg-offset-4 col-lg-4">
   		<div class="form-group">
   			<label class="AppColor">Category:</label>
		  	<select id="challengeCategory" class="form-control">
			  <%=options %>
			</select>
		</div>
   	</div>
</div>

<div class="row">
   	<div class="col-lg-offset-4 col-lg-4">
   		<div class="form-group">
   			<label class="AppColor">Type:</label>
		  	<select id="challengeType" class="form-control">
			  <option value='1'>Photo and Text</option>
			  <option value='2'>Photo</option>
			  <option value='3'>Text</option>
			</select>
		</div>
   	</div>
</div>

<div class="row">
	<div class="col-lg-offset-4 col-lg-4 text-center">
		<input type="button" value="Save Challenge" class="btn btn-group ActiveRed" onclick="challengeSave()">
	</div>	
</div>		
		
		
<div id="listContainer">
	<div class="row">
	   	<div class="col-lg-offset-4 col-lg-4 text-center">
	   		<div class="form-group">
	   			<label class="AppColor">List Challenges:</label>
			  	<select id="ChallengesbyCat" onchange="findChallenges()" class="form-control">
				  <%=options %>
				</select>
			</div>
			<div class="text-center">
				<button class="btn btn-group ActiveRed" onclick="findChallenges()">Refresh</button><br>
			</div>
	   	</div>
	</div>
	<div class="panel-container">
        <div class="list-group challenges_list">
            <ul>
            </ul>
        </div>
    </div>	
	
</div>

	<% } %>