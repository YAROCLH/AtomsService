package model;

public class Report {
		int IdUser;
		String UserName;
		String Intranet;
		int Total;
		int ByCatego[];
		int C1,C2,C3,C4,C5;
		String LastChallenge;
		String LastChallengeDate;
		String LastLogin;
		public Report(){
			
		}
		public void setIdUser(int iduser){
			this.IdUser=iduser;
		}
		public int getIduser(){
			return this.IdUser;
		}
		public void setUserName(String username){
			this.UserName=username;
		}
		public String getUserName(){
			return this.UserName;
		}
		
		public void setIntranet(String intranet){
			this.Intranet=intranet;
		}
		public String getIntranet(){
			return this.Intranet;
		}
		
		public void setTotal(int total){
			this.Total=total;
		}
		public int getTotal(){
			return this.Total;
		}
		
		public int[] getByCatego(){
			return this.ByCatego;
		}
		
		public void setLast(String last){
			this.LastChallenge=last;
		}
		public String getLast(){
			return this.LastChallenge;
		}
		
		public void setLastDate(String timestamp){
			this.LastChallengeDate=timestamp;
		}
		public String getLastDate(){
			return this.LastChallengeDate;
		}
		
		public void setLastLogin(String last){
			this.LastLogin=last;
		}
		public String getLastLogin(){
			return this.LastLogin;
		}
		
		public void setByCatego(int c1,int c2,int c3,int c4,int c5){
			this.C1=c1;
			this.C2=c2;
			this.C3=c3;
			this.C4=c4;
			this.C5=c5;
		}
		public int getC1(){
			return this.C1;
		}
		public int getC2(){
			return this.C2;
		}
		public int getC3(){
			return this.C3;
		}
		public int getC4(){
			return this.C4;
		}
		public int getC5(){
			return this.C5;
		}
		
		
		
		
		
		
		
		
		
}