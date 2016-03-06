<%@page import="Control.admin.CategoryControl"%>
<%@page import="model.Category"%>

	<%	
	
	if(session.getAttribute("Admin")==null){
	System.out.print("Not permited");
	response.sendRedirect("../../index.html");}
	else{
		CategoryControl con=new CategoryControl();
		Category category=new Category();
		int action=Integer.parseInt(request.getParameter("action"));
		//action: 1=create 2=update 3= delete
		int l1=Integer.parseInt(request.getParameter("l1"));
		int l2=Integer.parseInt(request.getParameter("l2"));
		int l3=Integer.parseInt(request.getParameter("l3"));
		int l4=Integer.parseInt(request.getParameter("l4"));
		int id=Integer.parseInt(request.getParameter("idCategory"));
		category.setName(request.getParameter("name"));
		category.setDescription(request.getParameter("description"));
		category.setLvl(l1, l2, l3, l4);
		category.setId(id);
		boolean res=false;
		switch(action){
		case 1:
			 res=con.NewCategory(category);
		break;
		case 2:
			res=con.UpdateCategory(category);
		break;
		case 3:

		break;
		}
		if(res){out.print(1);}
		else{out.print(-1);}
		
	}
	%>