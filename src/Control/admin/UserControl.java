package Control.admin;
import java.util.ArrayList;

import model.*;
import persistence.*;

public class UserControl {
		
		UserDAO dao;
		public UserControl(){
			dao=new UserDAO();
		}
	
		public User DoLogin(String name,String pass){
			System.out.println("Login with "+name+pass);
			User res=dao.Login(name, pass);
			return res; 
		}
		
		public boolean  NewUser(User user){
			boolean res=dao.newUser(user);
			return res;
		}
		
		public String DoFind(String s){
			ArrayList<User>users=dao.findUser(s);
			User user;
			String buffer="";
			for(int a=0;a<users.size();a++){
				user=users.get(a);
				buffer=buffer+"<li class='select'onclick='setUser(this)'>"+user.getIntranetId()+"</li>";
			}
			return buffer;
		}
		
		public String getUser(String name){
			User user;
			String Json;
			user=dao.getUser(name);
			Json="{\"records\":[{\"Name\":\""+user.getName()+"\",\"Id\":\""+user.getIntranetId()+"\",\"Type\":\""+user.getType()+"\",\"UserId\":\""+user.getId()+"\"}]}";
			return Json;
		}

		public boolean UpdateUser(User user){
			return dao.setUpdate(user);
		}
		
		
}
