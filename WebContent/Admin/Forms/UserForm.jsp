

		<h2>User</h2>
		<button onclick="userCreateMode()">Create</button><button id="userEdit" onclick="userEditMode()">Edit</button><br>
		<h3 id="userMode">CREATE A NEW USER</h3>
		<label id="userLabel">NEW USER INTRANET ID</label><br>
		<input type="text" placeholder="Intranet Id" id="userId" onkeyup="findUser()" class="editUser"><br>
		<label>User Name</label><br>
		<input type="text" id="userName" class="editUser"><br>
		<label>User Pass</label><br>
		<input type="password" placeholder="Default Password" required="required" id="userPass"><br>
		<label>Admin</label>
		<input type="checkbox" id="isAdmin" class="editUser"><br>
		<label class="toDelete">Delete User</label>
		<input type="checkbox" id="toDelete" class="toDelete" onclick="toDelete()"><br>
		<button id="UserSave" onclick="userSave()">SAVE</button>
		<div class="hint"><ul id="userList"></ul></div><br>
		