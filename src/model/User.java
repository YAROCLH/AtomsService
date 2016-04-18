package model;

public class User {
		int ID;
		String Name;
		String IntranetID;
		String Password;
		int Type;
		int Score;
		int Rank;
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
		
		public void setScore(int score){
			this.Score=score;
		}
		public int getScore(){
			return this.Score;
		}
		
		public void setRank(int rank){
			this.Rank=rank;
		}
		public int getRank(){
			return this.Rank;
		}

		
}
