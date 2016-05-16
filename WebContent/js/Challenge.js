
	var toEdit=false;
	var challengeCreate=true;
	var currentChallenge;
	var challenge_Name,challenge_Short,challenge_Long,challenge_Points,challenge_Category;

		function initChallenge(){
			challengeCreateMode();
		}
		function challengeCreateMode(){
			challengeCreate=true;
			$("#challengeMode").text("CREATE A NEW CHALLENGE");
			$("#listContainer").hide();
			clearChallenge();
		}
		function challengeEditMode(){
			challengeCreate=false;
			$("#challengeMode").text("EDIT AN EXISTING CHALLENGE");
			$("#listContainer").show();
			clearChallenge();

		}

		function clearChallenge(){
			$(".editChallenge").val("");
			$(".challenges_list ul").empty();
		}
		
		function validateFields(){
			console.log("VALIDATING...");
			challenge_Name=$("#challengeName").val();
			challenge_Short=$("#challengeShort").val();
			challenge_Long=$("#challengeLong").val();
			challenge_Points=$("#challengePoints").val();
			challenge_Category=$("#challengeCategory").val();
			console.log(challenge_Name,challenge_Short,challenge_Long,challenge_Points,challenge_Category);
			if(challenge_Name==""||challenge_Short==""||challenge_Long==""||challenge_Points==""||challenge_Category==""){
				console.log("nop");
				return false;
			}else{
				console.log("hue")
				return true;
			}
		}

		function updateChallenge(){
			console.log("edit Challenge");
			if(validateFields()){
				$.post(url_challenge, { action: "update", name:challenge_Name , short:challenge_Short , 
				    long:challenge_Long , points:challenge_Points ,idCategory:challenge_Category,idChallenge:currentChallenge})
				.done(function(data) {
					if(data==1){
						alert("Success");
						clearChallenge();
					}else{alert("Failed");}
				})
				.fail(function(e) {console.log("Failed "+e);});
			}else{
				alert("All Fields are Required");
			}
			
		}

		function newChallenge(){
			console.log("new Challenge");
			if(validateFields()){
				$.post(url_challenge, { action: "create", name:challenge_Name , short:challenge_Short , 
									    long:challenge_Long , points:challenge_Points ,idCategory:challenge_Category,idChallenge:0})
				.done(function(data) {
					if(data==1){
						alert("Success");
						clearChallenge();
					}else{alert("Failed");}
				})
				.fail(function(e) {console.log("Failed "+e);});
			}else{
				alert("All Fields are Required");
			}
			
		}
		
		
		function findChallenges(){
			var find=$("#ChallengesbyCat").val();
			console.log("find..."+find)
			$.post(url_findChallenge, { id: find, action:"find" })
			.done(function(data) {
				$('.challenges_list ul').html(data);
			})
			.fail(function(e) {console.log("Failed "+e);});
		}
		
		function setChallenge(li){
			$(".editChallenge").prop( "disabled", true );
			var json_data;
			var selected=$(li).val();
			$.when(get_Data(url_findChallenge,"action=get&&id="+selected)).then(function(json){
				currentChallenge=json[0].Id;
				$("#challengeName").val(json[0].Name);
				$("#challengeShort").val(json[0].Short);
				$("#challengeLong").val(json[0].Long);
				$("#challengePoints").val(json[0].Points);
				var text1 = json[0].CategoryName;
				$("#challengeCategory option").filter(function() {return $(this).text() == text1; }).prop('selected', true);
				$(".editChallenge").prop( "disabled", false );
			});
		}
		
		function challengeSave(){
			if(challengeCreate){
				newChallenge();
			}else{
				updateChallenge();
			}
		}