package Control.Services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import model.Category;
import model.Challenge;
import model.User;
import persistence.ServiceDAO;

public class Control {
		ServiceDAO dao;
		String ERROR="{\"records\":[]}";
		String FAIL="{\"records\":[{\"STATUS\":\"-1\"}]}";
		String SUCCESS="{\"records\":[{\"STATUS\":\"1\"}]}";
		String DONE="{\"records\":[{\"STATUS\":\"0\"}]}";
		boolean NoDeadLock=false;
		public Control(){
			dao=new ServiceDAO();
		}
		
		public String getLogin(String intranetID){
			String Json;
			String LoginFail="{\"records\":[{\"id\":\""+"-1"+"\"}]}";	
			User user=dao.Login(intranetID);
			if(user==null||NoDeadLock){
				return LoginFail;
			}else{
				int id=user.getId();
				if(id==0){
					if(dao.CreateUser(intranetID)){
							Json=this.getLogin(intranetID);
							NoDeadLock=true;
					}else{	Json=LoginFail;}
				}else{
				 Json="{\"records\":[{\"id\":\""+user.getId()+"\",\"DisplayName\":\""+user.getName()+"\"}]}";	}
			}	
			return Json;
		}
		
		public String getSerial(String intranetID){
			return dao.getBlueId(intranetID);
		}
		
		public String getUserScore(int id){
			int Position=dao.getPosition(id);
			int Completed=dao.CompletedChallenge(id);
			if(Position==-1||Completed==-1){return ERROR;}
			else{
				String Json="{\"records\":[{\"Completed\":\""+Completed+"\",\"Position\":\""+Position+"\"}]}";
				return Json;	}
		}
		
		public String getRank(int id){
			User user=dao.myRank(id);
			String Json="{\"records\":[{\"myPosition\":\""+user.getRank()+"\",\"Score\":\""+user.getScore()+"\",\"NAME\":\""+user.getName()+"\"}]}";
			return Json;
		}
		
		public String getMyScores(int idUser){// STATUS //
			String Json="{\"records\":[";
			Category category,total;
			ArrayList<Category> Scores=new ArrayList<Category>();
			ArrayList<Category> Totals=new ArrayList<Category>();
			Scores=dao.getCategoryScore(idUser);
			Totals=dao.getTotalScore();
			for(int i=0;i<Scores.size();i++){
				category=Scores.get(i);
				total=Totals.get(i);
				Json=Json+"{\"id\":\""+category.getId()+"\",\"Score\":\""+category.getUserScore()+"\",\"Total\":\""+total.getTotalScore()+"\"}";
				if(i<(Scores.size()-1)){Json=Json+",";}
			}
			Json=Json+"]}";
			return Json;
		}
		
		public String getBadges(){
			String Json="{\"records\":[";
			Category category;
			category=dao.getBadgesPoints();	
			if(category==null){return ERROR;}
			else{
				Json=Json+"{\"L1\":\""+category.getLvl1()+"\",\"L2\":\""+category.getLvl2()+"\""
						+ ",\"L3\":\""+category.getLvl3()+"\",\"L4\":\""+category.getLvl4()+"\"}]}";
				return Json;
			}
		}
		
		public String getTop10(){
			User user;
			String Json="{\"records\":[";
			ArrayList<User> Users=dao.getTop10();
			if(Users==null){return ERROR;}
			else{
				for(int i=0;i<Users.size();i++){
					user=Users.get(i);
					Json=Json+"{\"Position\":\""+(i+1)+"\",\"INTRANET\":\""+user.getIntranetId()+"\",\"Score\":\""+user.getScore()+"\",\"Name\":\""+user.getName()+"\"}";
					if(i<(Users.size()-1)){Json=Json+",";	
				}
			}
			Json=Json+"]}";
			return Json;}
		}
		
		public String getChallenges(int User,int Category){
			ArrayList<Challenge> Completed,NonCompleted;
			Challenge challenge;
			String Json="{\"records\":[";
			Completed=dao.getCompletedChallenges(User,Category);
			NonCompleted=dao.getIncompleteChallenges(User,Category);			
			for(int i=0;i<NonCompleted.size();i++){
				challenge=NonCompleted.get(i);
				Json=Json+"{\"id\":\""+challenge.getId()+"\",\"Name\":\""+challenge.getName()+"\","
						 +"\"Short\":\""+challenge.getShort()+"\",\"Long\":\""+challenge.getLong()+"\","
						 +"\"Points\":\""+challenge.getPoints()+"\",\"Status\":\"false\"}";
				if(i<(NonCompleted.size()-1)){Json=Json+",";}
			}
			if(NonCompleted.size()>0&&Completed.size()>0){	Json=Json+",";	}
			for(int i=0;i<Completed.size();i++){
				challenge=Completed.get(i);
				Json=Json+"{\"id\":\""+challenge.getId()+"\",\"Name\":\""+challenge.getName()+"\","
						 +"\"Short\":\""+challenge.getShort()+"\",\"Long\":\""+challenge.getLong()+"\","
						 +"\"Points\":\""+challenge.getPoints()+"\",\"Status\":\"true\"}";
				if(i<(Completed.size()-1)){Json=Json+",";}
			}
			Json=Json+"]}";
			return Json;
			
		}
		
		public String getCategories(){
			String Json="{\"records\":[";
			ArrayList<Category> Categories=dao.getCategories();
			Category category;
			if(Categories==null){return ERROR;}
			else{
				for(int i=0;i<Categories.size();i++){
					category=Categories.get(i);
					Json=Json+"{\"id\":\""+category.getId()+"\",\"Name\":\""+category.getName()+"\"}";
					if(i<(Categories.size()-1)){	Json=Json+",";	
				}
			}
			Json=Json+"]}";
			return Json;}
		}
		
		public String SubmitChallenge(int idUser,int idChallenge,String Attach,String Photo){
			int Done=dao.AlreadyDone(idUser, idChallenge);
			if(Done==0){
				boolean Result=dao.SubmitChallenge(idUser, idChallenge, Attach, Photo);
				if(Result){	return SUCCESS; 	}
				else{		return DONE;		}
			}else{
				if(Done==1){return FAIL;		}
				else{		return FAIL;		}
			}
		}
		
		public String Decode(String Encoded){
			try{
			String Decoded=new String(Base64.getDecoder().decode(Encoded));
			return Decoded;
			}catch(Exception e){
				System.err.println("Failed To Decode base64");
				return "-1";
			}
		}
		
		public String completeChallenge(File file,String idUser,String idChallenge,String Text){
            try{
            	FileInputStream imageInFile = new FileInputStream(file);
                byte imageData[] = new byte[(int) file.length()];
				imageInFile.read(imageData);
				String Photo=encodeFile(imageData);
				imageInFile.close();
				DeleteTempFile(file);
				boolean res=dao.SubmitChallenge(Integer.parseInt(idUser),Integer.parseInt(idChallenge), Text, Photo);
				if(res){	return "1"; }
				else   {	return "-1";	}
			}catch(IOException e) {
				e.printStackTrace();
				return "-1";
			}
		}
		
		public String encodeFile(byte[] imageByteArray){
			return Base64.getEncoder().encodeToString(imageByteArray);
		}
		public void DeleteTempFile(File file){
			if(file.delete()){System.out.println("File Successfuly Deleted");}
			else{System.out.println("Failed To Delete File");}
		}
		
		public  String isInternetReachable(){
			return "Disabled";
		}
		
}//END OF CLASS


