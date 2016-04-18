package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import model.*;


public class ServiceDAO {
	ResultSet rs;
	PreparedStatement pstmt;
	Statement stmt;
	Connection con;
	Connector connector;
	
	public ServiceDAO(){
	   connector=new Connector();
	}
	
	
	/**
	 * Get Name and Pass and returning id and Display name if validated
	 * @param Name
	 * @param Pass
	 * @return 
	 */
		public User Login(String Name,String Pass){
			try {
				User user=new User();
				con=connector.CreateConnection();
				stmt=con.createStatement();
				stmt.execute("SET encryption password = 'AtomsPassword'");
				pstmt = con.prepareStatement("SELECT idUser,DisplayName FROM users WHERE IntranetID=? AND Password= ENCRYPT(?)"); 
				pstmt.setString(1,Name);
				pstmt.setString(2, Pass);
				rs = pstmt.executeQuery();
				if(!rs.next()){	 
					user.setId(0);
					user.setName("Not Found");
				}else{  
					user.setId(rs.getInt("idUser"));
					user.setName(rs.getString("DisplayName")); 
				}
				connector.CloseConnection(con);
				return user;
			} catch (Exception e) {
				e.printStackTrace();
				connector.CloseConnection(con);
				return null;
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
					position=rs.getInt("RANK");
				}	
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
		public User myRank(int idUser){
			try{
				User user=new User();
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
					user.setRank(0);
					user.setName("No Completed Challenges");
					user.setScore(0);
				}else{	
					user.setRank(rs.getInt("RANK"));
					user.setScore(rs.getInt("TOTALSCORE"));
					user.setName(rs.getString("DisplayName"));
				}
				connector.CloseConnection(con);
				return user;
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
		
		public ArrayList<User> getTop10(){
			ArrayList<User> top10=new ArrayList<User>();
			User user;
			try{
				con=connector.CreateConnection();
				String Query= "SELECT USERS.DISPLAYNAME, SUM (POINTS)\"TOTALSCORE\" FROM "
							+ "(COMPLETEDCHALLENGES INNER JOIN CHALLENGES ON COMPLETEDCHALLENGES.IDCHALLENGES=CHALLENGES.IDCHALLENGES) "
							+ "INNER JOIN USERS ON USERS.IDUSER=COMPLETEDCHALLENGES.IDUSER "
							+ "GROUP BY USERS.IDUSER,USERS.DISPLAYNAME ORDER BY TOTALSCORE DESC FETCH FIRST 10 ROWS ONLY"; 
				pstmt = con.prepareStatement(Query);
				rs=pstmt.executeQuery();
				while (rs.next()) {      
					user=new User();
					user.setName(rs.getString("DISPLAYNAME"));
					user.setScore(rs.getInt("TOTALSCORE"));
					top10.add(user);
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
		public ArrayList<Challenge>getCompletedChallenges(int idUser, int Category){
			ArrayList<Challenge> Challenges=new ArrayList<Challenge>();
			Challenge challenge;
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
					challenge=new Challenge();
					challenge.setId(rs.getInt("idChallenges"));
					challenge.setName(rs.getString("Name"));      
					challenge.setShort(rs.getString("ShortDescription"));      
					challenge.setLong(rs.getString("LongDescription"));  
					challenge.setPoints(rs.getInt("Points"));
					Challenges.add(challenge);
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
		public ArrayList<Challenge>getIncompleteChallenges(int idUser,int Category){
			ArrayList<Challenge> Challenges=new ArrayList<Challenge>();
			Challenge challenge;
			try {
				con=connector.CreateConnection();
				String Query= "SELECT * FROM CHALLENGES LEFT JOIN (SELECT * FROM COMPLETEDCHALLENGES WHERE IDUSER=?)TEMP "
						    + "ON CHALLENGES.IDCHALLENGES=TEMP.IDCHALLENGES WHERE TEMP.IDCHALLENGES IS NULL AND IDCATEGORY=?";
				pstmt = con.prepareStatement(Query); 
				pstmt.setInt(1,idUser);
				pstmt.setInt(2,Category);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					challenge=new Challenge();
					challenge.setId(rs.getInt("idChallenges"));
					challenge.setName(rs.getString("Name"));      
					challenge.setShort(rs.getString("ShortDescription"));      
					challenge.setLong(rs.getString("LongDescription"));  
					challenge.setPoints(rs.getInt("Points"));
					Challenges.add(challenge);
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
		public  ArrayList<Category> getCategoryScore(int idUser){// STATUS SERVICE  //
			ArrayList<Category> Scores=new ArrayList<Category>();
			Category category;
			try {
				int auxCounter=1;
				con=connector.CreateConnection();
				String Query= "SELECT IDCATEGORY,SUM(POINTS)AS CATEGORYSCORE FROM "
							+ "COMPLETEDCHALLENGES INNER JOIN CHALLENGES "
							+ "ON COMPLETEDCHALLENGES.IDCHALLENGES=CHALLENGES.IDCHALLENGES WHERE IDUSER=? "
							+ "GROUP BY IDUSER,IDCATEGORY";
				pstmt = con.prepareStatement(Query); 
				pstmt.setInt(1,idUser);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					 category=new Category();
					 int idCat=rs.getInt("IDCATEGORY");
					 if(auxCounter==idCat){
						 category.setId(idCat);
						 category.setUserScore(rs.getInt("CATEGORYSCORE"));
					 }else{
						 category.setId(auxCounter);
						 category.setUserScore(0);
					 }
					 Scores.add(category);
					 auxCounter++;
				}      
				connector.CloseConnection(con);
				return Scores;
			}catch (Exception e) {
				e.printStackTrace();
				connector.CloseConnection(con);
				return null;
			} 
			
		}

		public ArrayList<Category>getTotalScore(){
			ArrayList<Category> Totals=new ArrayList<Category>();
			Category category;
			try {
				con=connector.CreateConnection();
				String Query= "SELECT DISTINCT IDCATEGORY, SUM(POINTS) AS TOTAL FROM CHALLENGES GROUP BY IDCATEGORY";
				pstmt = con.prepareStatement(Query); 
				rs = pstmt.executeQuery();
				while (rs.next()) {
					 category=new Category();
					 category.setId(rs.getInt("IDCATEGORY"));
					 category.setTotalScore(rs.getInt("TOTAL"));
					 Totals.add(category);
				}      
				connector.CloseConnection(con);
				return Totals;
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
		public ArrayList<Category> getCategories(){
			ArrayList<Category> Categories=new ArrayList<Category>();
			Category category;
			try {
				con=connector.CreateConnection();
				pstmt = con.prepareStatement("SELECT IDCATEGORY,NAME FROM Categories"); 
				rs = pstmt.executeQuery();
				while (rs.next()) {
					 category=new Category();
					 category.setId(rs.getInt("IDCATEGORY"));
					 category.setName(rs.getString("NAME"));   
					 Categories.add(category);
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
		
		
		public Category getBadgesPoints(){
			try{
				Category category=new Category();
				con=connector.CreateConnection();
				String Query="SELECT LEVEL01,LEVEL02,LEVEL03,LEVEL04 FROM CATEGORIES FETCH FIRST 1 ROW ONLY";
				pstmt = con.prepareStatement(Query); 
				rs=pstmt.executeQuery();
				while (rs.next()) {
					 int l1=rs.getInt("LEVEL01");
					 int l2=rs.getInt("LEVEL02");
					 int l3=rs.getInt("LEVEL03");
					 int l4=rs.getInt("LEVEL04");
					 category.setLvl(l1, l2, l3, l4);
				}      
				connector.CloseConnection(con);
				return category;
			}catch(Exception e){
				e.printStackTrace();
				connector.CloseConnection(con);
				return null;
			}
		}
	
}//END OF CLASS
