package persistence;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.Challenge;
import model.CompletedChallenge;
public class ChallengeDAO {
		ResultSet rs;
		PreparedStatement pstmt;
		Statement stmt;
		Connection con;
		Connector connector;
		String SCHEMA;
			public ChallengeDAO(){
				connector=new Connector();
				SCHEMA=Connector.schema;
			}
		
			public ArrayList<Challenge> getChallenges(int idCategory){
				Challenge challenge;
				ArrayList<Challenge> challenges=new ArrayList<Challenge>();
				try {
					con=connector.CreateConnection();
					pstmt = con.prepareStatement("SELECT * FROM "+SCHEMA+".CHALLENGES WHERE IDCATEGORY=?"); 
					pstmt.setInt(1, idCategory);
					rs = pstmt.executeQuery();
					while (rs.next()) {
						challenge=new Challenge();
						challenge.setId(rs.getInt("IdChallenges"));
						challenge.setName(rs.getString("Name"));
						challenge.setIdCategory(rs.getInt("IdCategory"));
						challenge.setShort(rs.getString("ShortDescription"));
						challenge.setLong(rs.getString("LongDescription"));
						challenges.add(challenge);
					}      
					connector.CloseConnection(con);
					return challenges;
				}catch (Exception e) {
					e.printStackTrace();
					connector.CloseConnection(con);
					return null;
				}	
				
			}
			
			public boolean newChallenge(Challenge challenge){
				try{
					con=connector.CreateConnection();
					String Query="INSERT INTO "+SCHEMA+".CHALLENGES (IDCATEGORY,NAME,SHORTDESCRIPTION,LONGDESCRIPTION,POINTS,VIEWPOSITION,TYPE) VALUES(?,?,?,?,?,?,?)";
					pstmt = con.prepareStatement(Query); 
					pstmt.setInt(1, challenge.getIdCategory());
					pstmt.setString(2, challenge.getName());
					pstmt.setString(3, challenge.getShort());
					pstmt.setString(4, challenge.getLong());
					pstmt.setInt(5, challenge.getPoints());
					pstmt.setInt(6, challenge.getPosition());
					pstmt.setInt(7, challenge.getType());
					pstmt.executeUpdate();
					connector.CloseConnection(con);
					return true;
				}catch(Exception e){
					e.printStackTrace();
					connector.CloseConnection(con);
					return false;
				}
				
			}
			
			
			public Challenge getChallenge(int id){
				Challenge challenge=new Challenge();
				try {
					con=connector.CreateConnection();
					String query= "SELECT IDCHALLENGES,"+SCHEMA+".CHALLENGES.IDCATEGORY,"
								   +SCHEMA+".CHALLENGES.Type,"+SCHEMA+".CHALLENGES.NAME, "
								   +"SHORTDESCRIPTION,LONGDESCRIPTION,"
								   +SCHEMA+".CATEGORIES.NAME AS CATEGORY,POINTS "
								   +"FROM "+SCHEMA+".CHALLENGES "
								   +"INNER JOIN "+SCHEMA+".CATEGORIES ON "+SCHEMA+".CHALLENGES.IDCATEGORY = "+SCHEMA+".CATEGORIES.IDCATEGORY "
								   +"WHERE IDCHALLENGES = ?";
					pstmt = con.prepareStatement(query); 
					pstmt.setInt(1,id);
					rs = pstmt.executeQuery();
					if(!rs.next()){	 
						challenge.setId(0);
					}else{  
						challenge.setId(rs.getInt("IDCHALLENGES"));
						challenge.setName(rs.getString("NAME"));
						challenge.setIdCategory(rs.getInt("IDCATEGORY"));
						challenge.setShort(rs.getString("SHORTDESCRIPTION"));
						challenge.setLong(rs.getString("LONGDESCRIPTION"));
						challenge.setPoints(rs.getInt("POINTS"));
						challenge.setCategoryName(rs.getString("CATEGORY"));
						challenge.setType(rs.getInt("TYPE"));
					}
					connector.CloseConnection(con);
					return challenge;
				} catch (Exception e) {
					e.printStackTrace();
					connector.CloseConnection(con);
					return null;
				}
			}

			public boolean setUpdateChallenge(Challenge challenge){
				try{
					con=connector.CreateConnection();
					String Query="UPDATE "+SCHEMA+".CHALLENGES SET NAME=?,SHORTDESCRIPTION=?,LONGDESCRIPTION=?,POINTS=?,IDCATEGORY=?,TYPE=?"
								+"WHERE IDCHALLENGES=?";
					pstmt = con.prepareStatement(Query); 
					pstmt.setString(1, challenge.getName());
					pstmt.setString(2, challenge.getShort());
					pstmt.setString(3, challenge.getLong());
					pstmt.setInt(4,challenge.getPoints());
					pstmt.setInt(5,challenge.getIdCategory());
					pstmt.setInt(6,challenge.getType());
					pstmt.setInt(7,challenge.getId());
					pstmt.executeUpdate();
					connector.CloseConnection(con);
					return true;
				}catch(Exception e){
					e.printStackTrace();
					connector.CloseConnection(con);
					return false;
				}
			}

			public boolean DeleteCompletedChallenge(int idCompleted){
				try{
					con=connector.CreateConnection();
					String Query="DELETE FROM "+SCHEMA+".COMPLETEDCHALLENGES WHERE IDCOMPLETEDCHALLENGES=?";
					pstmt = con.prepareStatement(Query); 
					pstmt.setInt(1,idCompleted);
					pstmt.executeUpdate();
					connector.CloseConnection(con);
					return true;
				}catch(Exception e){
					e.printStackTrace();
					connector.CloseConnection(con);
					return false;
				}
			}
			
			public String getSelectedImage(int idCompleted){
				try{
					String base;
					con=connector.CreateConnection();
					String Query="SELECT IMAGEURL, ATTACHTEXT FROM " +SCHEMA+".COMPLETEDCHALLENGES WHERE IDCOMPLETEDCHALLENGES=?";
					pstmt = con.prepareStatement(Query); 
					pstmt.setInt(1,idCompleted);
					rs = pstmt.executeQuery();
					if(!rs.next()){	 
						base="NOT FOUND";
					}else{  
						base=rs.getString("IMAGEURL");
						if(base==null||base==" "||base==""||base=="\0"){
							base="NO PHOTO";
						}
					}
					return base;
				}catch(Exception e){
					System.out.println("Failed");
					e.printStackTrace();
					return "-1";
				}
			}
			public ArrayList<CompletedChallenge> getCompletedbyId(int idUser){
				CompletedChallenge challenge;
				ArrayList<CompletedChallenge> challenges=new ArrayList<CompletedChallenge>();
				try {
					con=connector.CreateConnection();
					String Query="SELECT "
									+SCHEMA+".COMPLETEDCHALLENGES.IDCOMPLETEDCHALLENGES,"
									+SCHEMA+".COMPLETEDCHALLENGES.ATTACHTEXT,"
									+SCHEMA+".CHALLENGES.NAME AS CHALLENGE,"
									+SCHEMA+".USERS.DISPLAYNAME AS USER,"
									+SCHEMA+".CHALLENGES.LONGDESCRIPTION AS DESCRIPTION "
									+"FROM "+SCHEMA+".COMPLETEDCHALLENGES "
									+"INNER JOIN "+SCHEMA+".CHALLENGES ON "+SCHEMA+".COMPLETEDCHALLENGES.IDCHALLENGES="+SCHEMA+".CHALLENGES.IDCHALLENGES "
									+"INNER JOIN "+SCHEMA+".USERS ON "+SCHEMA+".COMPLETEDCHALLENGES.IDUSER="+SCHEMA+".USERS.IDUSER "
									+"WHERE "+SCHEMA+".COMPLETEDCHALLENGES.IDUSER=? "
								    +"ORDER BY IDCOMPLETEDCHALLENGES DESC";
					pstmt = con.prepareStatement(Query); 
					pstmt.setInt(1, idUser);
					rs = pstmt.executeQuery();
					while (rs.next()){
						challenge=new CompletedChallenge();
						challenge.setIdCompletedChallenge(rs.getInt("IDCOMPLETEDCHALLENGES"));
						challenge.setChallengeName(rs.getString("CHALLENGE"));
						challenge.setUserName(rs.getString("USER"));
						challenge.setDescription(rs.getString("DESCRIPTION"));
						challenge.setAttach(rs.getString("ATTACHTEXT"));
						challenges.add(challenge);
					}      
					connector.CloseConnection(con);
					return challenges;
				}catch (Exception e) {
					e.printStackTrace();
					connector.CloseConnection(con);
					return null;
				}	
			}
			
			public ArrayList<CompletedChallenge> getCompletedbyCategory(int Category){
				CompletedChallenge challenge;
				ArrayList<CompletedChallenge> challenges=new ArrayList<CompletedChallenge>();
				try {
					con=connector.CreateConnection();
					String Query="SELECT "
									+SCHEMA+".COMPLETEDCHALLENGES.IDCOMPLETEDCHALLENGES,"
									+SCHEMA+".COMPLETEDCHALLENGES.ATTACHTEXT,"
									+SCHEMA+".CHALLENGES.NAME AS CHALLENGE,"
									+SCHEMA+".USERS.DISPLAYNAME AS USER,"
									+SCHEMA+".CHALLENGES.LONGDESCRIPTION AS DESCRIPTION, "
									+SCHEMA+".CHALLENGES.IDCATEGORY "
									+"FROM "+SCHEMA+".COMPLETEDCHALLENGES "
									+"INNER JOIN "+SCHEMA+".CHALLENGES ON "+SCHEMA+".COMPLETEDCHALLENGES.IDCHALLENGES="+SCHEMA+".CHALLENGES.IDCHALLENGES "
									+"INNER JOIN "+SCHEMA+".USERS ON "+SCHEMA+".COMPLETEDCHALLENGES.IDUSER="+SCHEMA+".USERS.IDUSER "
									+"WHERE "+SCHEMA+".CHALLENGES.IDCATEGORY=? "
									+"ORDER BY IDCOMPLETEDCHALLENGES DESC";
					pstmt = con.prepareStatement(Query); 
					pstmt.setInt(1, Category);
					rs = pstmt.executeQuery();
					while (rs.next()){
						challenge=new CompletedChallenge();
						challenge.setIdCompletedChallenge(rs.getInt("IDCOMPLETEDCHALLENGES"));
						challenge.setChallengeName(rs.getString("CHALLENGE"));
						challenge.setUserName(rs.getString("USER"));
						challenge.setDescription(rs.getString("DESCRIPTION"));
						challenge.setAttach(rs.getString("ATTACHTEXT"));
						challenges.add(challenge);
					}      
					connector.CloseConnection(con);
					return challenges;
				}catch (Exception e) {
					e.printStackTrace();
					connector.CloseConnection(con);
					return null;
				}	
			}
			
			public ArrayList<CompletedChallenge> getCompletedbyCategoryandId(int userId,int Category){
				CompletedChallenge challenge;
				ArrayList<CompletedChallenge> challenges=new ArrayList<CompletedChallenge>();
				try {
					con=connector.CreateConnection();
					String Query="SELECT "
									+SCHEMA+".COMPLETEDCHALLENGES.IDCOMPLETEDCHALLENGES,"
									+SCHEMA+".COMPLETEDCHALLENGES.ATTACHTEXT,"
									+SCHEMA+".CHALLENGES.NAME AS CHALLENGE,"
									+SCHEMA+".USERS.DISPLAYNAME AS USER,"
									+SCHEMA+".CHALLENGES.LONGDESCRIPTION AS DESCRIPTION, "
									+SCHEMA+".CHALLENGES.IDCATEGORY "
									+"FROM "+SCHEMA+".COMPLETEDCHALLENGES "
									+"INNER JOIN "+SCHEMA+".CHALLENGES ON "+SCHEMA+".COMPLETEDCHALLENGES.IDCHALLENGES="+SCHEMA+".CHALLENGES.IDCHALLENGES "
									+"INNER JOIN "+SCHEMA+".USERS ON "+SCHEMA+".COMPLETEDCHALLENGES.IDUSER="+SCHEMA+".USERS.IDUSER "
									+"WHERE "+SCHEMA+".CHALLENGES.IDCATEGORY=? AND "
									+SCHEMA+".COMPLETEDCHALLENGES.IDUSER=? "
									+"ORDER BY IDCOMPLETEDCHALLENGES DESC";
					pstmt = con.prepareStatement(Query); 
					pstmt.setInt(1, Category);
					pstmt.setInt(2, userId);
					rs = pstmt.executeQuery();
					while (rs.next()){
						challenge=new CompletedChallenge();
						challenge.setIdCompletedChallenge(rs.getInt("IDCOMPLETEDCHALLENGES"));
						challenge.setChallengeName(rs.getString("CHALLENGE"));
						challenge.setUserName(rs.getString("USER"));
						challenge.setDescription(rs.getString("DESCRIPTION"));
						challenge.setAttach(rs.getString("ATTACHTEXT"));
						challenges.add(challenge);
					}      
					connector.CloseConnection(con);
					return challenges;
				}catch (Exception e) {
					e.printStackTrace();
					connector.CloseConnection(con);
					return null;
				}	
			}
			
			public ArrayList<CompletedChallenge>getCompletedbyName(String challengeName){
				CompletedChallenge challenge;
				ArrayList<CompletedChallenge> challenges=new ArrayList<CompletedChallenge>();
				try {
					con=connector.CreateConnection();
					String Query="SELECT "
									+"COMPLETEDCHALLENGES.IDCOMPLETEDCHALLENGES,"
									+"COMPLETEDCHALLENGES.ATTACHTEXT,"
									+"CHALLENGES.NAME AS CHALLENGE,"
									+"USERS.DISPLAYNAME AS USER,"
									+"CHALLENGES.LONGDESCRIPTION AS DESCRIPTION, "
									+"CHALLENGES.IDCATEGORY "
									+"FROM "+SCHEMA+".COMPLETEDCHALLENGES "
									+"INNER JOIN CHALLENGES ON COMPLETEDCHALLENGES.IDCHALLENGES=CHALLENGES.IDCHALLENGES "
									+"INNER JOIN USERS ON COMPLETEDCHALLENGES.IDUSER=USERS.IDUSER "
									+"WHERE CHALLENGES.NAME=? "
									+"ORDER BY IDCOMPLETEDCHALLENGES DESC";
					pstmt = con.prepareStatement(Query); 
					pstmt.setString(1, challengeName);
					rs = pstmt.executeQuery();
					while (rs.next()){
						challenge=new CompletedChallenge();
						challenge.setIdCompletedChallenge(rs.getInt("IDCOMPLETEDCHALLENGES"));
						challenge.setChallengeName(rs.getString("CHALLENGE"));
						challenge.setUserName(rs.getString("USER"));
						challenge.setDescription(rs.getString("DESCRIPTION"));
						challenge.setAttach(rs.getString("ATTACHTEXT"));
						challenges.add(challenge);
					}      
					connector.CloseConnection(con);
					return challenges;
				}catch (Exception e) {
					e.printStackTrace();
					connector.CloseConnection(con);
					return null;
				}	
			}
			
			public ArrayList<Challenge> findChallengesbyName(String str,int idCategory){
				Challenge challenge;
				ArrayList<Challenge> challenges=new ArrayList<Challenge>();
				try {
					con=connector.CreateConnection();
					pstmt = con.prepareStatement("SELECT NAME,SHORTDESCRIPTION,IDCHALLENGES FROM "+SCHEMA+".CHALLENGES WHERE NAME LIKE ? AND IDCATEGORY=?"); 
					pstmt.setString(1, "%"+str+"%");
					pstmt.setInt(2, idCategory);
					rs = pstmt.executeQuery();
					while (rs.next()) {
						challenge=new Challenge();
						challenge.setId(rs.getInt("IDCHALLENGES"));
						challenge.setName(rs.getString("NAME"));
						challenge.setShort(rs.getString("SHORTDESCRIPTION"));
						challenges.add(challenge);
					}      
					connector.CloseConnection(con);
					return challenges;
				}catch (Exception e) {
					e.printStackTrace();
					connector.CloseConnection(con);
					return null;
				}	
			}
			
			
			
			
}
