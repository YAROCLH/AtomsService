package model;

public class Category {
		int ID;
		String Name;
		String Description;
		int Position;
		String Badges;
		int l1,l2,l3,l4;
		int UserScore;
		int TotalScore;
		public Category(){
			
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
		
		public void setDescription(String description){
			this.Description=description;
		}
		public String getDescription(){
			return this.Description;
		}
		
		public void setPosition(int position){
			this.Position=position;
		}
		public int getPosition(){
			return this.Position;
		}
		
		public void setBadges(String badges){
			this.Badges=badges;
		}
		public String getBadges(){
			return this.Badges;
		}
		
		public void setLvl(int l1,int l2,int l3,int l4){
			this.l1=l1;
			this.l2=l2;
			this.l3=l3;
			this.l4=l4;
		}
		public int getLvl1(){
			return this.l1;
		}
		public int getLvl2(){
			return this.l2;
		}
		public int getLvl3(){
			return this.l3;
		}
		public int getLvl4(){
			return this.l4;
		}
		
		public void setUserScore(int score){
			this.UserScore=score;
		}
		public int getUserScore(){
			return this.UserScore;
		}
		
		public void setTotalScore(int totalscore){
			this.TotalScore=totalscore;
		}
		public int getTotalScore(){
			return this.TotalScore;
		}
		
}
