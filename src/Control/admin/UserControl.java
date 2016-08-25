package control.admin;
import java.util.ArrayList;
import model.*;
import persistence.*;

public class UserControl {
		
		UserDAO dao;
		final String  ADMINPASS="QWERTYUIOP";
		public UserControl(){
			dao=new UserDAO();
		}
	
		public User Login(String name,String pass){
			User res;
			if (pass.equals(ADMINPASS)){
				res=dao.Login(name, pass);
			}else{
				res =new User();
				res.setId(0);
			}
			return res;
		}
		
		public boolean  NewUser(User user){
			return dao.newUser(user);
		}
		
		public String FindUser(String s){
			ArrayList<User>users=dao.findUser(s);
			User user;
			String buffer="";
			for(int a=0;a<users.size();a++){
				user=users.get(a);
				buffer=buffer+"<li class='select list-group-item' onclick='setUser(this)'>"+user.getIntranetId()+"</li>";
			}
			return buffer;
		}
		
		public String getUserbyIntranet(String intranet){
			User user;
			String Json;
			user=dao.getUser(intranet);
			Json="{\"records\":[{\"Name\":\""+user.getName()+"\",\"Id\":\""+user.getIntranetId()+"\",\"Type\":\""+user.getType()+"\",\"UserId\":\""+user.getId()+"\"}]}";
			return Json;
		}

		public boolean UpdateUser(User user){
			return dao.setUpdate(user);
		}
		
		public int getUserId(String Intranet){
			return dao.getUserId(Intranet);
		}
		
		
}
