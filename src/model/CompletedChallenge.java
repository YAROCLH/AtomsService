package model;

public class CompletedChallenge {
	int IdCompletedChallenge;
	int IdChallenge;
	int IdUser;
	int idCategory;
	String ChallengeName;
	String UserName;
	String Date;
	String Time;
	public CompletedChallenge(){
		
	}
	
	public void setIdCompletedChallenge(int id){
		this.IdCompletedChallenge=id;
	}
	public int getIdCompletedChallenge(){
		return this.IdCompletedChallenge;
	}
	
	public void setIdChallenge(int id){
		this.IdChallenge=id;
	}
	public int getIdChallenge(){
		return this.IdChallenge;
	}
	
	public void setIdUser(int id){
		this.IdUser=id;
	}
	public int getIdUser(){
		return this.IdUser;
	}
	
	public void setIdCategory(int id){
		this.idCategory=id;
	}
	public int getIdCategory(){
		return this.idCategory;
	}
	
	public void setChallengeName(String name){
		this.ChallengeName=name;
	}
	public String getChallengeName(){
		return this.ChallengeName;
	}
	
	public void setUserName(String name){
		this.UserName=name;
	}
	public String getUserName(){
		return this.UserName;
	}
	
	public void setDate(String date){
		this.Date=date;
	}
	public String getDate(){
		return this.Date;
	}
	
	public void setTime(String time){
		this.Time=time;
	}
	public String getTime(){
		return this.Time;
	}
}
