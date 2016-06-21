package model;

public class Challenge {
		
		int ID;
		String Name;
		int IdCategory;
		String CategoryName;
		String ShortDescription;
		String LongDescription;
		int Points;
		int Position;
		int Type;
		public Challenge(){
			
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
		
		public void setIdCategory(int idcategory){
			this.IdCategory=idcategory;
		}
		public int getIdCategory(){
			return this.IdCategory;
		}
		
		public void setCategoryName(String categoryname){
			this.CategoryName=categoryname;
		}
		public String getCategoryName(){
			return this.CategoryName;
		}
		
		public void setShort(String shortdescription){
			this.ShortDescription=shortdescription;
		}
		public String getShort(){
			return this.ShortDescription;
		}
		
		public void setLong(String longdescription){
			this.LongDescription=longdescription;
		}
		public String getLong(){
			return this.LongDescription;
		}
		
		public void setPoints(int points){
			this.Points=points;
		}
		public int getPoints(){
			return this.Points;
		}
		
		public void setPosition(int position){
			this.Position=position;
		}
		public int getPosition(){
			return this.Position;
		}
		
		public void setType(int type){
			this.Type=type;
		}
		public int getType(){
			return this.Type;
		}
}


