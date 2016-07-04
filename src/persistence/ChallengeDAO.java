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
					String query= "SELECT IDCHALLENGES,"+SCHEMA+".CHALLENGES.IDCATEGORY,"+SCHEMA+".CHALLENGES.Type,"+SCHEMA+".CHALLENGES.NAME,SHORTDESCRIPTION,LONGDESCRIPTION,"+SCHEMA+".CATEGORIES.NAME AS CATEGORY,POINTS "
								+ "FROM "+SCHEMA+".CHALLENGES INNER JOIN "+SCHEMA+".CATEGORIES ON "+SCHEMA+".CHALLENGES.IDCATEGORY = "+SCHEMA+".CATEGORIES.IDCATEGORY WHERE IDCHALLENGES = ?";
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

			public boolean setDeleteChallenge(){
				return false;
			}
			
			public String getSelectedImage(int user,int challenge){
				try{
				String base="";
				con=connector.CreateConnection();
				String Query="SELECT IMAGEURL FROM COMPLETEDCHALLENGES WHERE IDUSER=? AND IDCHALLENGES=?";
				pstmt = con.prepareStatement(Query); 
				pstmt.setInt(1,user);
				pstmt.setInt(2,challenge);
				rs = pstmt.executeQuery();
				if(!rs.next()){	 
					base="nada";
				}else{  
					base=rs.getString("IMAGEURL");
				}
				return base;
				}catch(Exception e){
					e.printStackTrace();
					return null;
				}

			}
			
}
