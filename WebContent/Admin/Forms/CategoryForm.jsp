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
	    		<h2 class="text-center AppColor"><strong>Category</strong> </h2>
	    </div>
	    <br />
		<div class="row">
			<div class="col-lg-offset-3 col-lg-6">
				<div class="btn-group btn-group-justified" role="group" aria-label="...">
					  <div class="btn-group " role="group">
					  	<button class="btn ActiveRed btn-default ActiveRed" onclick="categoryCreateMode()">Create</button>
					  </div>
					  <div class="btn-group " role="group">
					    <button class="btn ActiveRed btn-default ActiveRed" onclick="categoryEditMode()">Edit</button>
					  </div>
				</div>
			</div>
		</div>
		
		
		<div class="row">
			<div class="col-lg-offset-4 col-lg-4">
				<h2 id="categoryMode" class="text-center AppColor"><strong>Create new category</strong> </h2>
			</div>	
	    </div>
	    
	    <div class="row">
	    	<div class="col-lg-offset-4 col-lg-4">
	    		<div class="form-group">
				  	<select onchange="findCategory()" id="editCategories" class="form-control">
					    <option>Categories</option>
					    <%=options %>
					</select>
				</div>
	    	</div>
	    </div>
	    
	    <div class="row">
	    	<div class="col-lg-offset-4 col-lg-4">
	    		<div class="form-group">
				  	<input type="text" placeholder="Name" id="categoryName" class="editCatego form-control">
				</div>
	    	</div>
	    </div>
		
		<div class="row">
	    	<div class="col-lg-offset-4 col-lg-4">
	    		<div class="form-group">
				  	<textarea placeholder=" Description" required="required" id="categoryDescription" class="editCatego form-control"></textarea>
				</div>
	    	</div>
	    </div>
	    
		<div class="row">
			<div class="col-lg-offset-4 col-lg-4">
				<h2 class="text-center AppColor"><strong>Points</strong> </h2>
			</div>	
	    </div>
	    
	    <div class="row">
			<div class="col-lg-offset-4 col-lg-4">
				<div class="row">
				  <div class="col-lg-6">
					    <div class="input-group">
						      <span class="input-group-btn">
						        <button class="btn btn-default" type="button">LVL 1</button>
						      </span>
						      <input type="number" id="categoryl1" onkeyup="isNumber(this)" class="editCategoL form-control">
					    </div>
				  </div>
				  <div class="col-lg-6">
					    <div class="input-group">
						      <input type="number" id="categoryl2" onkeyup="isNumber(this)" class="editCategoL form-control">
						      <span class="input-group-btn">
						        <button class="btn btn-default" type="button">LVL 2</button>
						      </span>
					    </div>
				  </div>
				</div>
			</div>	
	    </div>
	    
	    <div class="row">
			<div class="col-lg-offset-4 col-lg-4">
				<div class="row">
				  <div class="col-lg-6">
					    <div class="input-group">
						      <span class="input-group-btn">
						        <button class="btn btn-default" type="button">LVL 3</button>
						      </span>
						      <input type="number" id="categoryl3" onkeyup="isNumber(this)" class="editCategoL form-control">
					    </div>
				  </div>
				  <div class="col-lg-6">
					    <div class="input-group">
						      <input type="number" id="categoryl4" onkeyup="isNumber(this)" class="editCategoL form-control">
						      <span class="input-group-btn">
						        <button class="btn btn-default" type="button">LVL 4</button>
						      </span>
					    </div>
				  </div>
				</div>
			</div>	
	    </div>
	    <br />
	    <div class="row">
			<div class="col-lg-offset-4 col-lg-4 text-center">
				<input type="button" value="SAVE" class="btn btn-group ActiveRed" onclick="categorySave()">
			</div>	
	    </div>
		
		
    </div>
          
    <%	}	%>