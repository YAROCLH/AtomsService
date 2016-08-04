package control.admin;
import java.util.ArrayList;


import model.*;
import persistence.CategoryDAO;
import utils.Utils;

public class CategoryControl {
	
	
		CategoryDAO dao;
		Utils utils;
		public CategoryControl(){
			dao=new CategoryDAO();
			utils=new Utils();
		}
		
		public String ListALLCategories(){
			String options="",buffer;
			Category category;
			ArrayList<Category>categories=new ArrayList<Category>();
			categories=dao.getCategories();
			for(int a=0;a<categories.size();a++){
				category=categories.get(a);
				buffer="<option value='"+category.getId()+"'>"+category.getName()+"</option>";
				options=options+buffer;
			}
			return options;
		}
		
		public boolean NewCategory(Category category){
			return dao.createCategory(SanitizeCategory(category));
		}
		
		public String getCategory(int id){
			Category category;
			String Json;
			category=dao.getCategory(id);
			Json="{\"records\":[{\"Name\":\""+category.getName()+"\",\"Description\":\""+category.getDescription()+"\",\"Id\":\""+category.getId()+"\","
					+"\"l1\":\""+category.getLvl1()+"\",\"l2\":\""+category.getLvl2()+"\",\"l3\":\""+category.getLvl3()+"\","+"\"l4\":\""+category.getLvl4()+"\"}]}";
			return Json;
		}

		public boolean UpdateCategory(Category category){
			return dao.setUpdateCategory(SanitizeCategory(category));
		}

		public boolean DeleteCategory(){
			return dao.setDeleteCategory();
		}
		public Category SanitizeCategory(Category category){
			category.setName(utils.Sanitize(category.getName()));
			category.setDescription(utils.Sanitize(category.getDescription()));
			return category;
		}
		
}
