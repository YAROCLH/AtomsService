package control.admin;
import java.util.ArrayList;
import persistence.ChallengeDAO;
import utils.Utils;
import model.*;
public class ChallengeControl {
	
		ChallengeDAO dao;
		Utils utils;
		public ChallengeControl(){
			dao= new ChallengeDAO();
			utils=new Utils();
		}
		
		public boolean newChallenge(Challenge challenge){
			return dao.newChallenge(SanitizeChallenge(challenge));						
		}
		
		public String listChallengesbyCatego(int idCategory){
			ArrayList<Challenge> challenges;
			Challenge challenge;
			String buffer="";
			challenges=dao.getChallenges(idCategory);
			for(int a=0;a<challenges.size();a++){
				challenge=challenges.get(a);
				buffer=buffer+"<button type=\"button\" class=\"list-group-item select\" "
							 + "onclick='setChallenge(this)' value='"+challenge.getId()+"'>"+challenge.getName()+": "+challenge.getShort()+"</button>";
			}
			return buffer;
		}

		public String getChallenge(int  id){
			Challenge challenge;
			String Json;
			challenge=dao.getChallenge(id);
			Json="{\"records\":[{\"Name\":\""+challenge.getName()+"\",\"Category\":\""+challenge.getIdCategory()+"\",\"Id\":\""+challenge.getId()+"\","
					+"\"Short\":\""+challenge.getShort()+"\",\"Long\":\""+challenge.getLong()+"\",\"Points\":\""+challenge.getPoints()+"\","
					+ "\"CategoryName\":\""+challenge.getCategoryName()+"\",\"Type\":\""+challenge.getType()+"\"}]}";
			return Json;
		}

		public boolean updateChallenge(Challenge challenge){
			return dao.setUpdateChallenge(SanitizeChallenge(challenge));
		}
		
		public boolean RejectChallenge(int idCompleted){
			return dao.DeleteCompletedChallenge(idCompleted);
		}

		public String findChallengebyName(String str,int idCategory){
			ArrayList<Challenge> challenges=dao.findChallengesbyName(str, idCategory);
			Challenge challenge;
			String buffer="";
			for(int a=0;a<challenges.size();a++){
				challenge=challenges.get(a);
				buffer=buffer+"<li class='select list-group-item' onclick='setChallenge(this)'>"+challenge.getName()+"</li>";
			}
			return buffer;
		}	
		
		public String  getUserChallengesbyIntranet(String Intranet){
			UserControl user=new UserControl();
			int idUser= user.getUserId(Intranet);
			return CompletedToTag(dao.getCompletedbyId(idUser));	
		}
		public String getAllChallengesbyCategory(int Category){
			return CompletedToTag(dao.getCompletedbyCategory(Category));
		}
		public String getChallengesbyUserCategory(String intranet,int Category){
			UserControl user=new UserControl();
			int idUser= user.getUserId(intranet);
			return CompletedToTag(dao.getCompletedbyCategoryandId(idUser,Category));
		}
		public String getChallengesbyName(String name){
			return CompletedToTag(dao.getCompletedbyName(name));
		}
		
		public String CompletedToTag(ArrayList<CompletedChallenge> challenges){
			String Buffer="";
			CompletedChallenge challenge;
			for(int a=0; a<challenges.size();a++){
				challenge=challenges.get(a);
				Buffer=Buffer+"<button type=\"button\" class=\"list-group-item select\" "
							 + "onclick='setImage(this,\""+challenge.getAttach()+"\")' value='"+challenge.getIdCompletedChallenge()+"'>"+challenge.getChallengeName()
							 +": "+challenge.getDescription()+" - "+challenge.getUserName()+"</button>";

			}
			return Buffer;
		}
		
		public String getImage(int idCompleted){
			String image=dao.getSelectedImage(idCompleted);
			return image;		
		}
		
		public Challenge SanitizeChallenge(Challenge challenge){
			challenge.setName(utils.Sanitize(challenge.getName()));
			challenge.setLong(utils.Sanitize(challenge.getLong()));
			challenge.setShort(utils.Sanitize(challenge.getShort()));
			return challenge;
		}
		
}
