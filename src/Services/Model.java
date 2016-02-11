package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Model {
	ResultSet rs;
	PreparedStatement pstmt;
	Statement stmt;
	Connection con;
	Connector connector;
	
	public Model(){
	   connector=new Connector();
	}
	
	
	/**
	 * Get Name and Pass and returning id and Display name if validated
	 * @param Name
	 * @param Pass
	 * @return String: idUser(integer),DisplayName(String)
	 */
		public String Login(String Name,String Pass){
			try {
				String User;
				con=connector.CreateConnection();
				stmt=con.createStatement();
				stmt.execute("SET encryption password = 'AtomsPassword'");
				pstmt = con.prepareStatement("SELECT idUser,DisplayName FROM users WHERE IntranetID=? AND Password= ENCRYPT(?)"); 
				pstmt.setString(1,Name);
				pstmt.setString(2, Pass);
				rs = pstmt.executeQuery();
				if(!rs.next()){	 
					User="0<,>NotFound"; 
				}else{  
					User = rs.getInt("idUser")+"<,>"+rs.getString("DisplayName"); }
				connector.CloseConnection(con);
				return User;
			} catch (Exception e) {
				e.printStackTrace();
				connector.CloseConnection(con);
				return "-1<,>Failed";
			}
		}
		
	
	/**
	 * Get the id and return the number of completed challenges
	 * @param idUser
	 * @return CompletedChallenges(Integer)
	 */
		public int CompletedChallenge(int idUser){
			try{
				int completed;
				con=connector.CreateConnection();
				pstmt = con.prepareStatement("SELECT count(idChallenges)\"Completed\" FROM completedchallenges WHERE idUser=?"); 
				pstmt.setInt(1,idUser);
				rs = pstmt.executeQuery();
				if(!rs.next()){	
					completed=0;
				}else{ 
					completed = rs.getInt("Completed");	}	
				connector.CloseConnection(con);
				return completed;
			}catch(Exception e){
				e.printStackTrace();
				connector.CloseConnection(con);
				return -1;
			}
		}
		
		
		/**
		 *  Get id and return the user current position in global rank
		 * @param idUser
		 * @return GlobalPosition(Integer);
		 */
		public int getPosition(int idUser){
			try{
				int position;
				con=connector.CreateConnection();
				String Query="SELECT RANK FROM( "
						+ "SELECT IDUSER,ROW_NUMBER() OVER(ORDER BY TOTALSCORE DESC) AS RANK, DISPLAYNAME, TOTALSCORE FROM( "
						+ "SELECT USERS.IDUSER,USERS.DISPLAYNAME, SUM (POINTS)\"TOTALSCORE\" FROM "
						+ "(COMPLETEDCHALLENGES INNER JOIN CHALLENGES ON COMPLETEDCHALLENGES.IDCHALLENGES=CHALLENGES.IDCHALLENGES) "
						+ "INNER JOIN USERS ON USERS.IDUSER=COMPLETEDCHALLENGES.IDUSER "
						+ "GROUP BY USERS.IDUSER,USERS.DISPLAYNAME))WHERE IDUSER=?";
				pstmt = con.prepareStatement(Query); 
				pstmt.setInt(1,idUser);
				rs = pstmt.executeQuery();
				if(!rs.next()){
					position=0;
				}else{	
					position=rs.getInt("RANK");}	
				connector.CloseConnection(con);
				return position;
			}catch(Exception e){
				e.printStackTrace();
				connector.CloseConnection(con);
				return -1;
			}
			
		}
		
		
		/**
		 *	Get id and return Rank Data
		 * @param idUser
		 * @return String: GlobalPosition(integer),DisplayName(String),GlobalScore(integer)
		 */
		public String myRank(int idUser){
			try{
				String Rank;
				con=connector.CreateConnection();
				String Query="SELECT RANK,DISPLAYNAME,TOTALSCORE FROM( "
						+ "SELECT IDUSER,ROW_NUMBER() OVER(ORDER BY TOTALSCORE DESC) AS RANK, DISPLAYNAME, TOTALSCORE FROM( "
						+ "SELECT USERS.IDUSER,USERS.DISPLAYNAME, SUM (POINTS)\"TOTALSCORE\" FROM "
						+ "(COMPLETEDCHALLENGES INNER JOIN CHALLENGES ON COMPLETEDCHALLENGES.IDCHALLENGES=CHALLENGES.IDCHALLENGES) "
						+ "INNER JOIN USERS ON USERS.IDUSER=COMPLETEDCHALLENGES.IDUSER "
						+ "GROUP BY USERS.IDUSER,USERS.DISPLAYNAME))WHERE IDUSER=?";
				pstmt = con.prepareStatement(Query);
				pstmt.setInt(1,idUser);
				rs=pstmt.executeQuery();
				if(!rs.next()){
					Rank = "0<,>Complete a Challenge First<,>0";
				}else{	
					Rank = rs.getInt("RANK")+"<,>"+rs.getString("DisplayName")+"<,>"+rs.getInt("TOTALSCORE"); }
				connector.CloseConnection(con);
				return Rank;
			}catch(Exception e){
				e.printStackTrace();
				connector.CloseConnection(con);
				return null;
			}
			
		}
		
		
		/**
		 * Return the top10  Rank Data
		 * @return ArrayList<String>
		 * 			 Each String: DisplayName(String),Score(Integer)
		 */   
		public ArrayList<String> getTop10(){
			ArrayList<String> top10=new ArrayList<String>();
			try{
				con=connector.CreateConnection();
				String Query= "SELECT USERS.DISPLAYNAME, SUM (POINTS)\"TOTALSCORE\" FROM "
							+ "(COMPLETEDCHALLENGES INNER JOIN CHALLENGES ON COMPLETEDCHALLENGES.IDCHALLENGES=CHALLENGES.IDCHALLENGES) "
							+ "INNER JOIN USERS ON USERS.IDUSER=COMPLETEDCHALLENGES.IDUSER "
							+ "GROUP BY USERS.IDUSER,USERS.DISPLAYNAME ORDER BY TOTALSCORE DESC FETCH FIRST 10 ROWS ONLY"; 
				pstmt = con.prepareStatement(Query);
				rs=pstmt.executeQuery();
				while (rs.next()) {       
					String DisplayName=rs.getString("DISPLAYNAME");
					int Score=rs.getInt("TOTALSCORE");
					top10.add(DisplayName+"<,>"+Score);
				}
				connector.CloseConnection(con);
				return top10;
			}catch(Exception e){
				e.printStackTrace();
				connector.CloseConnection(con);
				return null;
			}
		}
		
		
		/**
		 * Get an idCategory and return all challenges of this category
		 * @param Category
		 * @param idUser
		 * @return ArrayList<String>
		 * 		   Each String: idChallenge(Integer),Name(String),ShortDescription(String),LongDescription(String)
		 */
		public ArrayList<String>getCompletedChallenges(int idUser, int Category){
			ArrayList<String> Challenges=new ArrayList<String>();
			try {
				con=connector.CreateConnection();
				String Query= "SELECT * FROM CHALLENGES INNER JOIN COMPLETEDCHALLENGES "
							+ "ON CHALLENGES.IDCHALLENGES=COMPLETEDCHALLENGES.IDCHALLENGES WHERE IDUSER=? "
							+ "AND IDCATEGORY=?";
				pstmt = con.prepareStatement(Query); 
				pstmt.setInt(1,idUser);
				pstmt.setInt(2,Category);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					int id= rs.getInt("idChallenges");
					String Name = rs.getString("Name");      
					String Short= rs.getString("ShortDescription");      
					String Long = rs.getString("LongDescription");  
					int Points=rs.getInt("Points");
					Challenges.add(id+"<,>"+Name+"<,>"+Short+"<,>"+Long+"<,>"+Points);
				}      
				connector.CloseConnection(con);
				return Challenges;
			}catch (Exception e) {
				e.printStackTrace();
				connector.CloseConnection(con);
				return null;
			}
		}
		
		/**
		 * Get an idCategory and return all challenges of this category
		 * @param Category
		 * @param idUser
		 * @return ArrayList<String>
		 * 		   Each String: idChallenge(Integer),Name(String),ShortDescription(String),LongDescription(String)
		 */
		public ArrayList<String>getIncompleteChallenges(int idUser,int Category){
			ArrayList<String> Challenges=new ArrayList<String>();
			try {
				con=connector.CreateConnection();
				String Query= "SELECT * FROM CHALLENGES LEFT JOIN (SELECT * FROM COMPLETEDCHALLENGES WHERE IDUSER=?)TEMP "
						    + "ON CHALLENGES.IDCHALLENGES=TEMP.IDCHALLENGES WHERE TEMP.IDCHALLENGES IS NULL AND IDCATEGORY=?";
				pstmt = con.prepareStatement(Query); 
				pstmt.setInt(1,idUser);
				pstmt.setInt(2,Category);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					 int id= rs.getInt("idChallenges");
					 String Name = rs.getString("Name");      
					 String Short= rs.getString("ShortDescription");      
					 String Long = rs.getString("LongDescription");  
					 int Points=rs.getInt("Points");
					 Challenges.add(id+"<,>"+Name+"<,>"+Short+"<,>"+Long+"<,>"+Points);
				}      
				connector.CloseConnection(con);
				return Challenges;
			}catch (Exception e) {
				e.printStackTrace();
				connector.CloseConnection(con);
				return null;
			}
		}
		
		/**
		 * Get User Score per Category
		 * @param idUser
		 * @return ArrayList<String>
		 * 		Each String: idCategory(Integer),CategoryScore(Integer)
		 */
		public  ArrayList<String> getBadges(int idUser){
			ArrayList<String> Badges=new ArrayList<String>();
			int idCategory=0;int CategoryScore=0;
			try {
				con=connector.CreateConnection();
				String Query= "SELECT IDCATEGORY,SUM(POINTS)AS CATEGORYSCORE FROM "
							+ "COMPLETEDCHALLENGES INNER JOIN CHALLENGES "
							+ "ON COMPLETEDCHALLENGES.IDCHALLENGES=CHALLENGES.IDCHALLENGES WHERE IDUSER=? "
							+ "GROUP BY IDUSER,IDCATEGORY";
				pstmt = con.prepareStatement(Query); 
				pstmt.setInt(1,idUser);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					 idCategory= rs.getInt("IDCATEGORY");
					 CategoryScore= rs.getInt("CATEGORYSCORE");
					 Badges.add(idCategory+"<,>"+CategoryScore);
				}      
				connector.CloseConnection(con);
				return Badges;
			}catch (Exception e) {
				e.printStackTrace();
				connector.CloseConnection(con);
				return null;
			} 
			
		}
		
		
		/**
		 * Return all Categories
		 * @return ArrayList<String> Each String: idCategory(Integer),Name(String)
		 */
		public ArrayList<String> getCategories(){
			ArrayList<String> Categories=new ArrayList<String>();
			try {
				con=connector.CreateConnection();
				pstmt = con.prepareStatement("SELECT IdCategory,Name FROM Categories"); 
				rs = pstmt.executeQuery();
				while (rs.next()) {
					 int id= rs.getInt(1);
					 String Name = rs.getString(2);       
					 Categories.add(id+"<,>"+Name);
				}      
				connector.CloseConnection(con);
				return Categories;
			}catch (Exception e) {
				e.printStackTrace();
				connector.CloseConnection(con);
				return null;
			}
		}
		
		
		/**
		 * Submit a Completed Challenge
		 * @param idUser
		 * @param idChallenge
		 * @param Text
		 * @param Photo
		 * @return true if update succeeded  otherwise false
		 */
		public boolean SubmitChallenge(int idUser,int idChallenge,String Text,String Photo){
			try{
				con=connector.CreateConnection();
				String Query="INSERT INTO COMPLETEDCHALLENGES (IDCHALLENGES,IDUSER,ATTACHTEXT,IMAGEURL) VALUES(?,?,?,?)";
				pstmt = con.prepareStatement(Query); 
				pstmt.setInt(1,idChallenge);
				pstmt.setInt(2,idUser);
				pstmt.setString(3,Text);
				pstmt.setString(4,Photo);
				pstmt.executeUpdate();
				connector.CloseConnection(con);
				return true;
			}catch(Exception e){
				e.printStackTrace();
				connector.CloseConnection(con);
				return false;
			}
		}
		
		public int AlreadyDone(int idUser,int idChallenge){
			try {
				int result=0;// 0: Incomplete, 1: Done , -1: Failed
				con=connector.CreateConnection();
				stmt=con.createStatement();
				pstmt = con.prepareStatement("SELECT idUser FROM completedchallenges WHERE idUser=? AND idChallenges=?"); 
				pstmt.setInt(1,idUser);
				pstmt.setInt(2, idChallenge);
				rs = pstmt.executeQuery();
				if(!rs.next()){	 
					result=0;	}
				else{  
					result=1;	}
				connector.CloseConnection(con);
				return result;
			}catch (Exception e) {
				e.printStackTrace();
				connector.CloseConnection(con);
				return -1;	}
			
		}
		
		
	
	
	
}//END OF CLASS
