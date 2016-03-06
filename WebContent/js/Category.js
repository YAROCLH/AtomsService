
	
// action param  1:create 2:update 3:delete
	var categoryCreate;
	var currentCategory;



		function initCategory(){

		}

		function categoryCreateMode(){
			categoryCreate=true;
			$("#editCategories").hide();
			clearCategory();

		}	

		function categoryEditMode(){
			categoryCreate=false;
			$("#editCategories").show();
		}

		function clearCategory(){
			$(".editCatego").val("");
			
		}

		function updateCategory(){
			console.log("update category")
			var name=$("#categoryName").val();
			var description=$("#categoryDescription").val();
			var l1=$("#categoryl1").val();
			var l2=$("#categoryl2").val();
			var l3=$("#categoryl3").val();
			var l4=$("#categoryl4").val();
			console.log(currentCategory)
			$.post(url_category, { action:2,name:name,description:description,l1:l1,l2:l2,l3:l3,l4:l4,idCategory:currentCategory })
			.done(function(data) {
				if(data==1){
					alert("Success");
				}else{alert("Failed");}
			})
			.fail(function(e) {console.log("Failed "+e);});
		}

		function newCategory(){
			console.log("new category")
			var name=$("#categoryName").val();
			var description=$("#categoryDescription").val();
			var l1=$("#categoryl1").val();
			var l2=$("#categoryl2").val();
			var l3=$("#categoryl3").val();
			var l4=$("#categoryl4").val();
			console.log(l1)
			$.post(url_category, { action:1,name:name,description:description,l1:l1,l2:l2,l3:l3,l4:l4,idCategory:0})
			.done(function(data) {
				if(data==1){
					alert("Success");
				}else{alert("Failed");}
			})
			.fail(function(e) {console.log("Failed "+e);});
		}
		
		
		
		function findCategory(){
			var selected=$("#editCategories").val();
			$(".editCatego").prop( "disabled", true );
			$.when(get_Data(url_findCategory,"idCategory="+selected)).then(function(json){
				currentCategory=json[0].Id;
				$("#categoryName").val(json[0].Name);
				$("#categoryDescription").val(json[0].Description);
				$("#categoryl1").val(json[0].l1);
				$("#categoryl2").val(json[0].l2);
				$("#categoryl3").val(json[0].l3);
				$("#categoryl4").val(json[0].l4);
				$(".editCatego").prop( "disabled", false );
			});
		}
		
		function categorySave(){
			if (categoryCreate){
				newCategory();
			}else{
				updateCategory();
			}
		}
		
		