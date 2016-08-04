package control.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import model.Category;
import model.Challenge;
import model.CompletedChallenge;
import model.User;
import persistence.ServiceDAO;
import utils.Utils;


	public class Control {
			ServiceDAO dao;
			Utils utils;
			String EMPTY="{\"records\":[]}";
			String FAIL="{\"records\":[{\"STATUS\":\"-1\"}]}";
			String SUCCESS="{\"records\":[{\"STATUS\":\"1\"}]}";
			String DONE="{\"records\":[{\"STATUS\":\"0\"}]}";
			
			public Control(){
				dao=new ServiceDAO();
				utils=new Utils();
			}
			
			public String getLogin(String intranetID) throws IOException{
				String LoginFail="{\"records\":[{\"id\":\""+"-1"+"\"}]}";	
				boolean Retry=false;
				String Json;
				User user=dao.Login(intranetID);
				if(user==null||Retry){
					return LoginFail;
				}else{
					int id=user.getId();
					if(id==0){
						String BlueName=utils.getFromJson(utils.getBlueJson(intranetID),"name");
						if(dao.CreateUser(intranetID,BlueName)){
								Json=this.getLogin(intranetID);
								Retry=true;
						}else{	Json=LoginFail;}
					}else{
					 this.setLastLogin(user.getId());
					 Json="{\"records\":[{\"id\":\""+user.getId()+"\",\"DisplayName\":\""+user.getName()+"\"}]}";	}
				}	
				return Json;
			}
			
			public void setLastLogin(int iduser){
				 String timestamp=utils.getDate()+" - "+utils.getTime();
				 if(!dao.LastLogin(iduser, timestamp)){
					 System.out.println("Last Login Timestamp failed");
				 }
			}
			
			public String getUserScore(int id){
				int Position=dao.getPosition(id);
				int Completed=dao.CompletedChallenge(id);
				if(Position==-1||Completed==-1){
					return EMPTY;
				}else{
					return "{\"records\":[{\"Completed\":\""+Completed+"\",\"Position\":\""+Position+"\"}]}";
				}
			}
			
			public String getRank(int id){
				User user=dao.myRank(id);
				return "{\"records\":[{\"myPosition\":\""+user.getRank()+"\",\"Score\":\""+user.getScore()+"\",\"NAME\":\""+user.getName()+"\"}]}";
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
					total=Totals.get(category.getId()-1);
					Json=Json+"{\"id\":\""+category.getId()+"\",\"Score\":\""+category.getUserScore()+"\",\"Total\":\""+total.getTotalScore()+"\"}";
					if(i<(Scores.size()-1)){Json=Json+",";}
				}
				return Json+"]}";
			}
			
			public String getBadges(){
				String Json="{\"records\":[";
				Category category;
				category=dao.getBadgesPoints();	
				if(category==null){
					return EMPTY;
				}else{
					Json=Json+"{\"L1\":\""+category.getLvl1()+"\",\"L2\":\""+category.getLvl2()+"\""
							+ ",\"L3\":\""+category.getLvl3()+"\",\"L4\":\""+category.getLvl4()+"\"}]}";
					return Json;
				}
			}
			
			public String getTop10(){
				User user;
				String Json="{\"records\":[";
				ArrayList<User> Users=dao.getTop10();
				if(Users==null){
					return EMPTY;
				}else{
					for(int i=0;i<Users.size();i++){
						user=Users.get(i);
						Json=Json+"{\"Position\":\""+(i+1)+"\",\"INTRANET\":\""+user.getIntranetId()+"\",\"Score\":\""+user.getScore()+"\",\"Name\":\""+user.getName()+"\"}";
						if(i<(Users.size()-1)){Json=Json+",";}
					}
					return Json+"]}";
				}
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
							 +"\"Points\":\""+challenge.getPoints()+"\",\"Status\":\"false\",\"Type\":\""+challenge.getType()+"\"}";
					if(i<(NonCompleted.size()-1)){Json=Json+",";}
				}
				if(NonCompleted.size()>0&&Completed.size()>0){	Json=Json+",";	}
				for(int i=0;i<Completed.size();i++){
					challenge=Completed.get(i);
					Json=Json+"{\"id\":\""+challenge.getId()+"\",\"Name\":\""+challenge.getName()+"\","
							 +"\"Short\":\""+challenge.getShort()+"\",\"Long\":\""+challenge.getLong()+"\","
							 +"\"Points\":\""+challenge.getPoints()+"\",\"Status\":\"true\",\"Type\":\""+challenge.getType()+"\"}";
					if(i<(Completed.size()-1)){Json=Json+",";}
				}
				return Json+"]}";
				
			}
			
			public String getCategories(){
				String Json="{\"records\":[";
				ArrayList<Category> Categories=dao.getCategories();
				Category category;
				if(Categories==null){
					return EMPTY;
				}else{
					for(int i=0;i<Categories.size();i++){
						category=Categories.get(i);
						Json=Json+"{\"id\":\""+category.getId()+"\",\"Name\":\""+category.getName()+"\"}";
						if(i<(Categories.size()-1)){	Json=Json+",";	}
					}
					return Json+"]}";
				}
			}
			//minimum fix
			public String SubmitChallenge(int idUser,int idChallenge,String Attach,String Photo){
				int Done=dao.AlreadyDone(idUser, idChallenge);
				if(Done==0){
					boolean Result=dao.SubmitChallenge(idUser, idChallenge, Attach, Photo,utils.getDate(),utils.getTime(),0);
					if(Result){	return SUCCESS; 	}
					else{		return FAIL;		}
				}else{
					if(Done==1){return DONE;		}
					else{		return FAIL;		}
				}
			}
			
			public String completeChallenge(File file,String idUser,String idChallenge,String Text,boolean post){
	            try{
	            	int ToPost=0;
	            	FileInputStream imageInFile = new FileInputStream(file);
	                byte imageData[] = new byte[(int) file.length()];
					imageInFile.read(imageData);
					String Photo=utils.encodeFile(imageData);
					imageInFile.close();
					utils.DeleteTempFile(file);
					if(post){ToPost=1;}else{ToPost=0;}
					boolean res=dao.SubmitChallenge(Integer.parseInt(idUser),Integer.parseInt(idChallenge), Text, Photo,utils.getDate(),utils.getTime(),ToPost);
					if(res){	return "1"; }
					else   {	return "-1";	}
				}catch(IOException e) {
					e.printStackTrace();
					return "-1";
				}
			}
					
			public String getVersion(){
				String version=dao.getVersion();
				String Json="{\"records\":[{\"Version\":\""+version+"\"}]}";
				return Json;
			}
			
			public String getTimeLine(int page,String date){
				ArrayList<CompletedChallenge> Completed;
				CompletedChallenge challenge;
				String Json="{\"records\":[";
				Completed=dao.getTimeLine(page,date);	
				for(int i=0;i<Completed.size();i++){
					String Date,Time;
					challenge=Completed.get(i);
					Date=challenge.getDate();
					Time=challenge.getTime();
					if(Date==null){Date="0/0/0";}
					if(Time==null){Time="0:0:0";}
					Json=Json+"{\"Id\":\""+challenge.getIdCompletedChallenge()+"\",\"Cname\":\""+challenge.getChallengeName()+"\","
							 +"\"Uname\":\""+challenge.getUserName()+"\",\"Date\":\""+Date+"\",\"Time\":\""+Time+"\","
							 +"\"Category\":\""+challenge.getIdCategory()+"\"}";
					if(i<(Completed.size()-1)){Json=Json+",";}
				}
				return Json+"]}";
			}
			
			public String getWeek(){
				return utils.getLastDays(5);
			}
			
			public byte[] getPic(String profile_pic){
				try{
				URL url = new URL(profile_pic);
				BufferedImage image = ImageIO.read(url);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write( image, "jpg", baos );
				baos.flush();
				byte[] imageInByte = baos.toByteArray();
				baos.close();
				return imageInByte;
				}catch(Exception e){
					e.printStackTrace();
					return null;
				}
			}
			
			
				
	}//END OF CLASS
	
	
