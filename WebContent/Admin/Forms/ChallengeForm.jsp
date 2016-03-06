<%@page import="Control.admin.ChallengeControl"%>
<%@page import="Control.admin.CategoryControl"%>

<%		
		CategoryControl con=new CategoryControl();
		String options=con.ListALLCategories();
 %>

		<h2>CHALLENGE</h2><br>
		<button onclick="challengeCreateMode()">Create</button><button onclick="challengeEditMode()">Edit</button><br>
		<h3 id="challengeMode">CREATE A NEW CHALLENGE</h3>
		<label>Name</label><br>
		<input type="text" placeholder="Name of the Challenge" id="challengeName" class="editChallenge"><br>
		<label>Short Description</label><br>
		<input type="text" placeholder="Short Description" id="challengeShort" class="editChallenge"><br>
		<label>Long Description</label><br>
		<textarea placeholder="Long Description" id="challengeLong" class="editChallenge"></textarea>  <br>
		<label>Points</label><br>
		<input type="number" onkeyup="isNumber(this)" id="challengePoints" class="editChallenge"><br>
		<label>Category</label><br>
		<select id="challengeCategory">
		  <%=options %>
		</select><br>
		<button onclick="challengeSave()" >SAVE</button>
		<br><br>
		
		<div id="listContainer">
		<h2>List Challenges</h2>
		<select id="ChallengesbyCat" onchange="findChallenges()">
		  <%=options %>
		</select><button onclick="findChallenges()">Refresh</button><br>
		<div class="challenges_list">
		<ul ></ul>
		</div>
		</div>