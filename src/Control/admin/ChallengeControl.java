package Control.admin;
import java.util.ArrayList;

import model.Challenge;
import model.CompletedChallenge;
import persistence.ChallengeDAO;
public class ChallengeControl {
	
		ChallengeDAO dao;
		public ChallengeControl(){
			dao= new ChallengeDAO();
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

		public boolean deleteChallenge(Challenge challenge){
			return dao.setDeleteChallenge();
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
		
		public String CompletedToTag(ArrayList<CompletedChallenge> challenges){
			String Buffer="";
			CompletedChallenge challenge;
			for(int a=0; a<challenges.size();a++){
				challenge=challenges.get(a);
				Buffer=Buffer+"<button type=\"button\" class=\"list-group-item select\" "
							 + "onclick='setImage(this)' value='"+challenge.getIdCompletedChallenge()+"'>"+challenge.getChallengeName()
							 +": "+challenge.getDescription()+" - "+challenge.getUserName()+"</button>";

			}
			return Buffer;
		}
		
		public String getImage(int idCompleted){
			return dao.getSelectedImage(idCompleted);
		}
		
		public Challenge SanitizeChallenge(Challenge challenge){
			challenge.setName(Sanitize(challenge.getName()));
			challenge.setLong(Sanitize(challenge.getLong()));
			challenge.setShort(Sanitize(challenge.getShort()));
			return challenge;
		}
		
		public String Sanitize(String text){
			text=text.replaceAll("\n", " ");
			return text;
		}
}
