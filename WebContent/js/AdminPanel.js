		//var url="http://localhost:9080/liberty-HelloWorld/Admin/"
		var url="http://atoms.w3ibm.mybluemix.net/Admin/";
		var url_findUser=url+"FindUser";
		var url_user=url+"User";
		var url_category=url+"Category";
		var url_findCategory=url+"FindCategory";
		var url_challenge=url+"Challenge"
		var url_findChallenge=url+"FindChallenge"
		
		$(document).ready(function(){
			show("User");
		});
		
		
		function show(value){
			console.log(value);
			$("#container").empty();
			switch (value){
			case "User":
				$("#container").load("Forms/User",function(){
					initUser();
				});
			break;
			case "Challenge":
				$("#container").load("Forms/Challenge",function(){
					initChallenge();
				});
			break;
			case "Category":
				$("#container").load("Forms/Category",function(){
					initCategory();
				});
			break;
			}
		}
	
		
		
		
		function get_Data(url_json,data){
			var json_data;
			return $.when( 
					$.ajax({
					url:url_json,
					dataType: 'json', 
				    data: data,
					success:function(json){
						json_data=$.map(json, function(elements) {return elements});},
					error:function(jqxhr, textStatus, error ){  
						var err = textStatus + ", " + error;
				        console.log( "Request Failed: " +url_json+" Error: "+ err );}
					})).then(function(){
				  return json_data;
			  });
		}
		
		function isNumber(imput){
			var sanitized = $(imput).val().replace(/[^0-9]/g, '');
			$(imput).val(sanitized);
		}
		
		
	
