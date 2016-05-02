
	var toEdit=false;
	var challengeCreate=true;
	var currentChallenge;


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

		function updateChallenge(){
			console.log("edit Challenge");
			var name=$("#challengeName").val();
			var short=$("#challengeShort").val();
			var long=$("#challengeLong").val();
			var points=$("#challengePoints").val();
			var category=$("#challengeCategory").val();
			console.log(name+short+long+points+category)
			$.post(url_challenge, { action: "update", name:name , short:short , long:long , points:points ,idCategory:category,idChallenge:currentChallenge})
			.done(function(data) {
				if(data==1){
					alert("Success");
					clearChallenge();
				}else{alert("Failed");}
			})
			.fail(function(e) {console.log("Failed "+e);});
		}

		function newChallenge(){
			console.log("new Challenge");
			var name=$("#challengeName").val();
			var short=$("#challengeShort").val();
			var long=$("#challengeLong").val();
			var points=$("#challengePoints").val();
			var category=$("#challengeCategory").val();
			console.log(name+short+long+points+category)
			$.post(url_challenge, { action: "create", name:name , short:short , long:long , points:points ,idCategory:category,idChallenge:0})
			.done(function(data) {
				if(data==1){
					alert("Success");
					clearChallenge();
				}else{alert("Failed");}
			})
			.fail(function(e) {console.log("Failed "+e);});
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