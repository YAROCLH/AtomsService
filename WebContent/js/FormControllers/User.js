
		var userCreate=true;
		var currentUser;
		
		function initUser(){
			TosetUser=true;
			userEditMode();
		}
		
		function userEditMode(){
			userCreate=false;
			$("#userMode").text("EDIT AN USER");
			$("#userLabel").text("Find a user by intranetID");
			userClear();
		}

		function userClear(){
			$(".editUser").val("");
			$("#isAdmin").prop('checked', false);
			$("#userList").empty();
			$(".editUser").prop( "disabled", false );
		}

		function toDelete(){
			if($("#toDelete").is(':checked')&&!userCreate){	
			alert("YOU ARE ABOUT TO DELETE AN USER! \n PRESS SAVE IF YOU WANT TO CONTINUE");
				$(".editUser").prop( "disabled", true );
			}else{
				$(".editUser").prop( "disabled", false );
			}
		}

		function findUser(){
			if(!userCreate){
				var find=$("#userId").val();
				$.post(url_findUser, { name: find, action:"find" })
				.done(function(data) {
					$('.hint ul').html(data);
				})
				.fail(function(e) {console.log("Failed "+e);});
			}else{console.log("Not Finding")}
		}
	
		function setUser_UserView(id){
			console.log("user: set user");
			$(".editUser").prop( "disabled", true );
			var json_data;
			var selected=$(id).text();
			$("#userId").val(selected);
			$.when(get_Data(url_findUser,"action=get&&name="+selected)).then(function(json){
				$("#userName").val(json[0].Name);
				$("#userPass").val("Default");
				currentUser=json[0].UserId;
				if(json[0].Type=="1"){
					$("#isAdmin").prop('checked', true);
				}else{
					$("#isAdmin").prop('checked', false);
				}
				$(".editUser").prop( "disabled", false );
				$(".toDelete").show();
			});
		}
		
		function newUser(){
			if(userCreate){
				var name=$("#userName").val();
				var id=$("#userId").val();
				var type;
				var pass=$("#userPass").val();
				if($("#isAdmin").is(':checked')){	type=1;	}
				else{	type=0;}
				$.post(url_user, { action: "create", name:name , id:id , type:type, userId:0, pass:pass})
				.done(function(data) {
					if(data==1){
						alert("Success");
					}else{alert("Failed");}
				})
				.fail(function(e) {console.log("Failed "+e);});
			}
			
		}

		function UpdateUser(){
				var name=$("#userName").val();
				var id=$("#userId").val();
				//var pass=$("#userPass").val();
				var type;
				if($("#isAdmin").is(':checked')){	type=1;	}
				else{	type=0;}
				$.post(url_user, { action: "update", name:name , id:id , type:type, userId:currentUser})
				.done(function(data) {
					if(data==1){
						alert("Success");
					}else{alert("Failed");}
				})
				.fail(function(e) {console.log("Failed "+e);});	
		}
		
		function userSave(){
			if(userCreate){
				newUser();
			}else{
				UpdateUser();
			}
		}
		

		function setUser(id){
			console.log("main: set user");
			if(TosetUser){
				console.log("set user : user");
				setUser_UserView(id)
			}else{
				console.log("set user : validate");
				var selected=$(id).text();
				$("#Validate_User").val(selected);
			}	
		}
		
		