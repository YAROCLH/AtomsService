package Services;

import java.util.ArrayList;

public class ToJson {
		Model model;
		String ERROR="{\"records\":[]}";
		String FAIL="{\"records\":[{\"STATUS\":\"-1\"}]}";
		String SUCCESS="{\"records\":[{\"STATUS\":\"1\"}]}";
		String DONE="{\"records\":[{\"STATUS\":\"0\"}]}";
		
		public ToJson(){
			model=new Model();
		}
		
		public String getLogin(String name,String pass){
			String Result=model.Login(name,pass);
			if(Result==null){
				return ERROR;
			}else{
				String[] RetrivedData=Result.split("<,>");
				String Json="{\"records\":[{\"id\":\""+RetrivedData[0]+"\",\"Name\":\""+RetrivedData[1]+"\"}]}";
				return Json;	}	
		}
		
		public String getUserScore(int id){
			int Position=model.getPosition(id);
			int Completed=model.CompletedChallenge(id);
			if(Position==-1||Completed==-1){return ERROR;}
			else{
				String Json="{\"records\":[{\"Completed\":\""+Completed+"\",\"Position\":\""+Position+"\"}]}";
				return Json;	}
		}
		
		public String getRank(int id){
			String buffer[];
			String Result=model.myRank(id);
			buffer=Result.split("<,>");		
			String Json="{\"records\":[{\"myPosition\":\""+buffer[0]+"\",\"Name\":\""+buffer[1]+"\",\"Score\":\""+buffer[2]+"\"}]}";
			return Json;
		}
		
		public String getMyBadges(int idUser){
			String buffer[];String Json="{\"records\":[";
			ArrayList<String> Result=new ArrayList<String>();
			Result=model.getBadges(idUser);
			for(int i=0;i<Result.size();i++){
				buffer=Result.get(i).split("<,>");
				Json=Json+"{\"id\":\""+buffer[0]+"\",\"Score\":\""+buffer[1]+"\"}";
				if(i<(Result.size()-1)){Json=Json+",";}
			}
			Json=Json+"]}";
			return Json;
		}
		
		public String getTop10(){
			String buffer[];
			String Json="{\"records\":[";
			ArrayList<String> Result=model.getTop10();
			if(Result==null){return ERROR;}
			else{
				for(int i=0;i<Result.size();i++){
					buffer=Result.get(i).split("<,>");
					Json=Json+"{\"Position\":\""+(i+1)+"\",\"Name\":\""+buffer[0]+"\",\"Score\":\""+buffer[1]+"\"}";
					if(i<(Result.size()-1)){Json=Json+",";	
				}
			}
			Json=Json+"]}";
			return Json;}
		}
		
		public String getChallenges(int User,int Category){
			String buffer[];ArrayList<String> Result,Result2;
			String Json="{\"records\":[";
			Result=model.getCompletedChallenges(User,Category);
			Result2=model.getIncompleteChallenges(User,Category);			
			for(int i=0;i<Result.size();i++){
				buffer=Result.get(i).split("<,>");
				Json=Json+"{\"id\":\""+buffer[0]+"\",\"Name\":\""+buffer[1]+"\","
						 +"\"Short\":\""+buffer[2]+"\",\"Long\":\""+buffer[3]+"\","
						 +"\"Points\":\""+buffer[4]+"\",\"Status\":\"true\"}";
				if(i<(Result.size()-1)){Json=Json+",";}
			}
			if(Result.size()>0&&Result2.size()>0){	Json=Json+",";	}
			for(int i=0;i<Result2.size();i++){
				buffer=Result2.get(i).split("<,>");
				Json=Json+"{\"id\":\""+buffer[0]+"\",\"Name\":\""+buffer[1]+"\","
						 +"\"Short\":\""+buffer[2]+"\",\"Long\":\""+buffer[3]+"\","
						 +"\"Points\":\""+buffer[4]+"\",\"Status\":\"false\"}";
				if(i<(Result2.size()-1)){Json=Json+",";}
			}
			Json=Json+"]}";
			return Json;
			
		}
		
		public String getCategories(){
			String buffer[];
			String Json="{\"records\":[";
			ArrayList<String> Result=model.getCategories();
			if(Result==null){return ERROR;}
			else{
				for(int i=0;i<Result.size();i++){
					buffer=Result.get(i).split("<,>");
					Json=Json+"{\"id\":\""+buffer[0]+"\",\"Name\":\""+buffer[1]+"\"}";
					if(i<(Result.size()-1)){	Json=Json+",";	}
			}
			Json=Json+"]}";
			return Json;}
		}
		
		public String SubmitChallenge(int idUser,int idChallenge,String Attach,String Photo){
			int Done=model.AlreadyDone(idUser, idChallenge);
			if(Done==0){
				boolean Result=model.SubmitChallenge(idUser, idChallenge, Attach, Photo);
				if(Result){	return SUCCESS; 	}
				else{		return FAIL;		}
			}else{
				if(Done==1){return DONE;		}
				else{		return FAIL;		}
			}
		}
		
}//END OF CLASS


