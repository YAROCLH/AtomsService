


	function iniValidate(){
		console.log("Validate Form")
	}

	function ValFindUser(){
		var find=$("#Validate_User").val();
		$.post(url_findUser, { name: find, action:"find" })
		.done(function(data) {
			$('.hint ul').html(data);
		})
		.fail(function(e) {console.log("Failed "+e);});
	}
	
	function findCompletedChallenges(){
		var find=$("#Validate_User").val();
		var catego_find=$("#validateCategory").val();
		console.log("find"+find+"  "+catego_find);
		$.post(url_findCompleted, {category:catego_find,intranet:find })
		.done(function(data) {
			$('.validate_list ul').html(data);
		})
		.fail(function(e) {console.log("Failed "+e);});
	}
	
	function setUser(id){
		var selected=$(id).text();
		$("#Validate_User").val(selected);
	}
	function setImage(completed){
		var selected=$(completed).val();
		console.log("SetImage"+selected)
		$.post(url_completed, {idCompleted:selected})
		.done(function(data) {
			var picture="data:image/png;base64,"+data;
			$("#Completed_Pic").attr('src',picture);
		})
		.fail(function(e) {console.log("Failed "+e);});
	}
	function PreventDelete(){
		console.log("prevent");
		if($("#prevent_validate").is(':checked')){	
			alert("YOU ARE ABOUT TO DELETE A COMPLETED CHALLENGE, NOW THERE IS NO WAY TO ADVISE THE USER OF THIS ACTION IF YOU WANT TO CONTINUE PRESS DELETE");
				$(".deleteChallenge").prop( "disabled", false );
			}else{
				$(".deleteChallenge").prop( "disabled", true );
			}
	}
	function DeleteCompleted(){
		
		
	}
	
	