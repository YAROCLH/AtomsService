<%@page import="Control.admin.ChallengeControl"%>
<%@page import="Control.admin.CategoryControl"%>

<%		
		CategoryControl con=new CategoryControl();
		String options=con.ListALLCategories();
 %>
 
 		<h2>CATEGORY</h2><br>
 		<button onclick="categoryCreateMode()">Create</button><button onclick="categoryEditMode()">Edit</button><br>
		<h3 id="categoryMode">CREATE A NEW CATEGORY</h3>
 		<select onchange="findCategory()" id="editCategories">
 			<option>CATEGORIES</option>
		  <%=options %>
		</select><br>
		<input type="text" placeholder="Name" id="categoryName" class="editCatego"><br>
		<textarea placeholder=" Description" required="required" id="categoryDescription" class="editCatego"></textarea><br>
		<label>Points</label><br>
		<label>LVL 1  </label><input type="number" id="categoryl1" onkeyup="isNumber(this)" class="editCatego"><br>
		<label>LVL 2  </label><input type="number" id="categoryl2" onkeyup="isNumber(this)" class="editCatego"><br>
		<label>LVL 3  </label><input type="number" id="categoryl3" onkeyup="isNumber(this)" class="editCatego"><br>
		<label>LVL 4  </label><input type="number" id="categoryl4" onkeyup="isNumber(this)" class="editCatego"><br>
		<input type="button" value="SAVE" onclick="categorySave()">