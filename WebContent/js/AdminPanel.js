		var url="http://127.0.0.1:14826/liberty-HelloWorld"
		var url_findUser=url+"/Admin/FindUser";
		var url_user=url+"/Admin/User";
		var url_category=url+"/Admin/Category";
		var url_findCategory=url+"/Admin/FindCategory";
		var url_challenge=url+"/Admin/Challenge"
		var url_findChallenge=url+"/Admin/FindChallenge"
		
		$(document).ready(function(){
			show("User");
		});
		
		
		function show(value){
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
		
		
	