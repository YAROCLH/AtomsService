package Control.admin;
import java.util.ArrayList;

import model.Challenge;
import persistence.ChallengeDAO;
public class ChallengeControl {
	
		ChallengeDAO dao;
		public ChallengeControl(){
			dao= new ChallengeDAO();
		}
		
		public boolean newChallenge(Challenge challenge){
			return dao.newChallenge(challenge);						
		}
		
		public String listChallengesbyCatego(int idCategory){
			ArrayList<Challenge> challenges;
			Challenge challenge;
			String buffer="";
			challenges=dao.getChallenges(idCategory);
			for(int a=0;a<challenges.size();a++){
				challenge=challenges.get(a);
				buffer=buffer+"<button type=\"button\" class=\"list-group-item select\" onclick='setChallenge(this)' value='"+challenge.getId()+"'>"+challenge.getName()+": "+challenge.getShort()+"</button>";
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
			return dao.setUpdateChallenge(challenge);
		}

		public boolean deleteChallenge(Challenge challenge){
			return dao.setDeleteChallenge();
		}
}
