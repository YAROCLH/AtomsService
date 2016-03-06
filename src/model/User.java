package model;

public class User {
		int ID;
		String Name;
		String IntranetID;
		String Password;
		int Type;
		String PrevId;
		public User(){
			
		}
	
		public void setId(int id){
			this.ID=id;
		}
		public int getId(){
			return this.ID;
		}
		
		public void setName(String name){
			this.Name=name;
		}
		public String getName(){
			return this.Name;
		}
		
		public void setIntranetId(String intranetid){
			this.IntranetID=intranetid;
		}
		public String getIntranetId(){
			return this.IntranetID;
		}
		
		public void setPassword(String password){
			this.Password=password;
		}
		public String getPassword(){
			return this.Password;
		}
		
		public void setType(int type){
			this.Type=type;
		}
		public int getType(){
			return this.Type;
		}

		public void setPrevId(String previd){
			this.PrevId=previd;
		}
		public String getPrevId(){
			return this.PrevId;
		}
		
	
	
	
		
}
