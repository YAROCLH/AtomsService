

	var SelectedChallenge;
		function initValidate(){
			TosetUser=false;
			console.log("Validate Form");
		}
	
		function ValFindUser(){
			var find=$("#Validate_User").val();
			$.post(url_findUser, { name: find, action:"find" })
			.done(function(data) {
				$('.hintV ul').html(data);
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
		
		
		function setImage(completed,attach){
			clear();
			var selected=$(completed).val();
			console.log("SetImage"+selected)
			$.post(url_completed, {idCompleted:selected,action:"PHOTO"})
			.done(function(data) {
				//console.log(data);
				if(data=="PHOTO DISABLED"||data=="NO PHOTO"){
					$("#NoPhoto").text("NO PHOTO");
				}else{
					if(data=="PHOTO NOT REQUIRED"){
						$("#NoPhoto").text("PHOTO NOT REQUIRED");
					}
					var picture="data:image/png;base64,"+data;
					$("#Completed_Pic").attr('src',picture);
				}
				SelectedChallenge=selected;
				$("#Attach").text(attach);
				$("#DeleteChanllengesPanel").css("display","inline");
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
			$.post(url_completed, {idCompleted:SelectedChallenge,action:"DELETE"})
			.done(function(data) {
				console.log(data);
				if(data=="1"){alert("SUCCESS");	}
				else{	alert("FAILED");	}
			})
			.fail(function(e) {
				console.log("Failed "+e);
				alert("FAILED");
			});
		}
		
		function clear(){
			$("#Completed_Pic").attr('src',"");
			$("#NoPhoto").text("");
			$("#Attach").text("");
		}
		function clearAll(){
			clear();
			$(".validate_list ul").empty();
		}
	
	