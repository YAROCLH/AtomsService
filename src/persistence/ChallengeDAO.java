package persistence;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.Challenge;
public class ChallengeDAO {
		ResultSet rs;
		PreparedStatement pstmt;
		Statement stmt;
		Connection con;
		Connector connector;
		
			public ChallengeDAO(){
				connector=new Connector();
			}
		
			public ArrayList<Challenge> getChallenges(int idCategory){
				Challenge challenge;
				ArrayList<Challenge> challenges=new ArrayList<Challenge>();
				try {
					con=connector.CreateConnection();
					pstmt = con.prepareStatement("SELECT * FROM CHALLENGES WHERE IDCATEGORY=?"); 
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
					String Query="INSERT INTO CHALLENGES (IDCATEGORY,NAME,SHORTDESCRIPTION,LONGDESCRIPTION,POINTS,VIEWPOSITION) VALUES(?,?,?,?,?,?)";
					pstmt = con.prepareStatement(Query); 
					pstmt.setInt(1, challenge.getIdCategory());
					pstmt.setString(2, challenge.getName());
					pstmt.setString(3, challenge.getShort());
					pstmt.setString(4, challenge.getLong());
					pstmt.setInt(5, challenge.getPoints());
					pstmt.setInt(6, challenge.getPosition());
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
					String query= "SELECT IDCHALLENGES,CHALLENGES.IDCATEGORY,CHALLENGES.NAME,SHORTDESCRIPTION,LONGDESCRIPTION,CATEGORIES.NAME AS CATEGORY,POINTS "
								+ "FROM CHALLENGES INNER JOIN CATEGORIES ON CHALLENGES.IDCATEGORY = CATEGORIES.IDCATEGORY WHERE IDCHALLENGES = ?";
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
					String Query="UPDATE CHALLENGES SET NAME=?,SHORTDESCRIPTION=?,LONGDESCRIPTION=?,POINTS=?,IDCATEGORY=?"
								+"WHERE IDCHALLENGES=?";
					pstmt = con.prepareStatement(Query); 
					pstmt.setString(1, challenge.getName());
					pstmt.setString(2, challenge.getShort());
					pstmt.setString(3, challenge.getLong());
					pstmt.setInt(4,challenge.getPoints());
					pstmt.setInt(5,challenge.getIdCategory());
					pstmt.setInt(6,challenge.getId());
					pstmt.executeUpdate();
					connector.CloseConnection(con);
					return true;
				}catch(Exception e){
					e.printStackTrace();
					connector.CloseConnection(con);
					return false;
				}
			}

			public boolean setDeleteChallenge(){
				return false;
			}
}
