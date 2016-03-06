
//URL'S
	var Login="Admin/Login",
		Admin="Admin/AdminPanel"

	$(document).ready(function () {
		
		$("#login").on("click",function(){
			var uname=$("#name").val();
			var upass=$("#pass").val();
			$.post(Login,{Name:uname,Pass:upass},function( data ) {
			if(data==1){
				window.location.replace(Admin);
			}else{
				alert("Failed");
			}
						    
			});
		});

	});


	