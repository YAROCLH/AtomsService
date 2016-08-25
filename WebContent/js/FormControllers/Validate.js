

	var SelectedChallenge;
		function initValidate(){
			TosetUser=false;
			console.log("Validate Form");
		}
	
		function Validate_FindUser(){
			var find=$("#Validate_User").val();
			if(find.length>2){
				$('.hintV ul').empty();
				$.post(url_findUser, { name: find, action:"find" })
				.done(function(data) {
				$('.hintV ul').html(data);
				})
				.fail(function(e) {console.log("Failed "+e);});
			}else{
				console.log("less than 3");
			}
		}			

		function Validate_FindChallenge(){
			var find=$("#Validate_Challenge").val();
			var category=$("#validateCategory").val();
			if(find.length>2){
				$('.hintV ul').empty();
				console.log(category);
				$.post(url_findChallenge, { name: find, action:"find", criteria:"byname",category:category})
				.done(function(data) {
					$('.hintV ul').html(data);
				})
				.fail(function(e) {console.log("Failed "+e);});
			}else{
				console.log("less than 3");
			}
		}
		function setChallenge(challenge){
			clearAll();
			var selected=$(challenge).text();
			$("#Validate_Challenge").val(selected);
			$("#Validate_User").val("");
		}


		function findCompletedChallenges(){	
			clearAll();
			var find=$("#Validate_User").val();
			var catego_find=$("#validateCategory").val();
			var challenge_find=$("#Validate_Challenge").val();
			console.log("find"+find+"  "+catego_find);
			$.post(url_findCompleted, {category:catego_find,intranet:find,challenge:challenge_find })
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
				if(data=="PHOTO DISABLED"||data=="NO PHOTO"){
					$("#NoPhoto").text("NO PHOTO");
				}else{
					if(data=="PHOTO NOT REQUIRED"){$("#NoPhoto").text(data);}
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
			$('.hintV ul').empty();
		}
	
	