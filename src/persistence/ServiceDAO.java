package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.Category;
import model.Challenge;
import model.CompletedChallenge;
import model.User;


public class ServiceDAO {
	ResultSet rs;
	PreparedStatement pstmt;
	Statement stmt;
	Connection con;
	Connector connector;
	String SCHEMA;

	String API_URL="http://bluepages.ibm.com/BpHttpApisv3/slaphapi?ibmperson/";
	public ServiceDAO(){
	   connector=new Connector();
	   SCHEMA=Connector.schema;
	}
	
	
	/**
	 * Get Name and Pass and returning id and Display name if validated
	 * @param Name
	 * @param Pass
	 * @return 
	 */
		public User Login(String intranetID){
			try {
				User user=new User();
				con=connector.CreateConnection();
				pstmt = con.prepareStatement("SELECT IDUSER,DISPLAYNAME FROM "+SCHEMA+".users WHERE IntranetID=? "); 
				pstmt.setString(1,intranetID);
				stmt=con.createStatement();
				rs = pstmt.executeQuery();
				if(!rs.next()){	 
					user.setId(0);
				}else{  
					user.setId(rs.getInt("idUser"));
					user.setName(rs.getString("DISPLAYNAME"));
				}
				connector.CloseConnection(con);
				return user;
			} catch (Exception e) {
				e.printStackTrace();
				connector.CloseConnection(con);
				return null;
			}
		}
		

		public boolean CreateUser(String intranetID,String name){
				//String name=getBlueName(intranetID);
			if(name.equals("-1")){
					System.out.println("FAILED TO GET BLUE NAME");
					return false;
			}else{
			try{
				con=connector.CreateConnection();
				String Query="INSERT INTO "+SCHEMA+".USERS (INTRANETID,TYPE,DISPLAYNAME) VALUES(?,?,?)";
				pstmt = con.prepareStatement(Query); 
				pstmt.setString(1, intranetID);
				pstmt.setInt(2,0);
				pstmt.setString(3,name);
				pstmt.executeUpdate();
				connector.CloseConnection(con);
				System.out.println("Create"+name);
				return true;
			}catch(Exception e){
				e.printStackTrace();
				connector.CloseConnection(con);
				return false;
			}}
		}
		
		public boolean LastLogin(int idUser,String timestamp){
			try{
				con=connector.CreateConnection();
				String Query="UPDATE "+SCHEMA+".USERS SET LASTLOGIN=? WHERE IDUSER=?";
				pstmt = con.prepareStatement(Query); 
				pstmt.setString(1,timestamp);
				pstmt.setInt(2,idUser);
				pstmt.executeUpdate();
				connector.CloseConnection(con);
				return true;
			}catch(Exception e){
				e.printStackTrace();
				connector.CloseConnection(con);
				return false;
			}			
		}

		
		public int CompletedChallenge(int idUser){
			try{
				int completed;
				con=connector.CreateConnection();
				pstmt = con.prepareStatement("SELECT count(idChallenges)\"Completed\" FROM "+SCHEMA+".completedchallenges WHERE idUser=?"); 
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
						+ "SELECT IDUSER,ROW_NUMBER() OVER(ORDER BY TOTALSCORE DESC) AS RANK, TOTALSCORE FROM( "
						+ "SELECT "+SCHEMA+".USERS.IDUSER, SUM (POINTS)\"TOTALSCORE\" FROM "
						+ "("+SCHEMA+".COMPLETEDCHALLENGES INNER JOIN "+SCHEMA+".CHALLENGES ON "+SCHEMA+".COMPLETEDCHALLENGES.IDCHALLENGES="+SCHEMA+".CHALLENGES.IDCHALLENGES) "
						+ "INNER JOIN "+SCHEMA+".USERS ON "+SCHEMA+".USERS.IDUSER="+SCHEMA+".COMPLETEDCHALLENGES.IDUSER "
						+ "GROUP BY "+SCHEMA+".USERS.IDUSER))WHERE IDUSER=?";
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
		
		
		/// ------ OPTIMIZAR QUERY-------///
		public User myRank(int idUser){
			try{
				User user=new User();
				con=connector.CreateConnection();
				String Query="SELECT RANK,DISPLAYNAME,TOTALSCORE FROM( "
						+ "SELECT IDUSER,ROW_NUMBER() OVER(ORDER BY TOTALSCORE DESC) AS RANK, "
						+ "DISPLAYNAME, TOTALSCORE "
						+ "FROM( SELECT "+SCHEMA+".USERS.IDUSER,"+SCHEMA+".USERS.DISPLAYNAME, SUM (POINTS)\"TOTALSCORE\" "
						+ "FROM ("+SCHEMA+".COMPLETEDCHALLENGES "
						+ "INNER JOIN "+SCHEMA+".CHALLENGES ON "+SCHEMA+".COMPLETEDCHALLENGES.IDCHALLENGES="+SCHEMA+".CHALLENGES.IDCHALLENGES) "
						+ "INNER JOIN "+SCHEMA+".USERS ON "+SCHEMA+".USERS.IDUSER="+SCHEMA+".COMPLETEDCHALLENGES.IDUSER "
						+ "GROUP BY "+SCHEMA+".USERS.IDUSER,"+SCHEMA+".USERS.DISPLAYNAME))"
						+ "WHERE IDUSER=?";
				pstmt = con.prepareStatement(Query);
				pstmt.setInt(1,idUser);
				rs=pstmt.executeQuery();
				if(!rs.next()){
					user.setRank(0);
					user.setScore(0);
				}else{	
					user.setRank(rs.getInt("RANK"));
					user.setScore(rs.getInt("TOTALSCORE"));
					user.setName(rs.getString("DISPLAYNAME"));
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
				String Query= "SELECT "+SCHEMA+".USERS.DISPLAYNAME,INTRANETID, SUM (POINTS)\"TOTALSCORE\" FROM "
							+ "("+SCHEMA+".COMPLETEDCHALLENGES INNER JOIN "+SCHEMA+".CHALLENGES ON "+SCHEMA+".COMPLETEDCHALLENGES.IDCHALLENGES="+SCHEMA+".CHALLENGES.IDCHALLENGES) "
							+ "INNER JOIN "+SCHEMA+".USERS ON "+SCHEMA+".USERS.IDUSER="+SCHEMA+".COMPLETEDCHALLENGES.IDUSER "
							+ "GROUP BY "+SCHEMA+".USERS.IDUSER,"+SCHEMA+".USERS.DISPLAYNAME,"+SCHEMA+".USERS.INTRANETID ORDER BY TOTALSCORE DESC FETCH FIRST 10 ROWS ONLY"; 
				pstmt = con.prepareStatement(Query);
				rs=pstmt.executeQuery();
				while (rs.next()) {      
					user=new User();
					user.setName(rs.getString("DISPLAYNAME"));
					user.setIntranetId(rs.getString("INTRANETID"));
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
				String Query= "SELECT * FROM "+SCHEMA+".CHALLENGES "
						    + "INNER JOIN "+SCHEMA+".COMPLETEDCHALLENGES "
							+ "ON "+SCHEMA+".CHALLENGES.IDCHALLENGES="+SCHEMA+".COMPLETEDCHALLENGES.IDCHALLENGES WHERE IDUSER=? "
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
					challenge.setType(rs.getInt("Type"));
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
		
		
		public ArrayList<Challenge>getIncompleteChallenges(int idUser,int Category){
			ArrayList<Challenge> Challenges=new ArrayList<Challenge>();
			Challenge challenge;
			try {
				con=connector.CreateConnection();
				String Query= "SELECT * FROM "+SCHEMA+".CHALLENGES "
						    + "LEFT JOIN (SELECT * FROM "+SCHEMA+".COMPLETEDCHALLENGES WHERE IDUSER=?)TEMP "
						    + "ON "+SCHEMA+".CHALLENGES.IDCHALLENGES=TEMP.IDCHALLENGES WHERE TEMP.IDCHALLENGES IS NULL "
						    + "AND IDCATEGORY=? AND STATUS=1 ORDER BY SHORTDESCRIPTION ASC";
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
					challenge.setType(rs.getInt("Type"));
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
		
	
		public  ArrayList<Category> getCategoryScore(int idUser){// STATUS SERVICE  //
			ArrayList<Category> Scores=new ArrayList<Category>();
			Category category;
			try {
				con=connector.CreateConnection();
				String Query= "SELECT IDCATEGORY,SUM(POINTS)AS CATEGORYSCORE FROM "
							+ ""+SCHEMA+".COMPLETEDCHALLENGES "
							+ "INNER JOIN "+SCHEMA+".CHALLENGES "
							+ "ON "+SCHEMA+".COMPLETEDCHALLENGES.IDCHALLENGES="+SCHEMA+".CHALLENGES.IDCHALLENGES WHERE IDUSER=? "
							+ "GROUP BY IDUSER,IDCATEGORY";
				pstmt = con.prepareStatement(Query); 
				pstmt.setInt(1,idUser);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					 category=new Category();
					 category.setId(rs.getInt("IDCATEGORY"));
					 category.setUserScore(rs.getInt("CATEGORYSCORE"));
					 Scores.add(category);
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
				String Query= "SELECT DISTINCT IDCATEGORY, SUM(POINTS) AS TOTAL FROM "+SCHEMA+".CHALLENGES GROUP BY IDCATEGORY";
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
		
		
	
		public ArrayList<Category> getCategories(){
			ArrayList<Category> Categories=new ArrayList<Category>();
			Category category;
			try {
				con=connector.CreateConnection();
				pstmt = con.prepareStatement("SELECT IDCATEGORY,NAME FROM "+SCHEMA+".Categories"); 
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
		
		
	
		public boolean SubmitChallenge(int idUser,int idChallenge,String Text,String Photo,String Date,String Time,int Post){
			try{
				con=connector.CreateConnection();
				String Query="INSERT INTO "+SCHEMA+".COMPLETEDCHALLENGES (IDCHALLENGES,IDUSER,ATTACHTEXT,IMAGEURL,DATE,TIME,POST) VALUES(?,?,?,?,?,?,?)";
				pstmt = con.prepareStatement(Query); 
				pstmt.setInt(1,idChallenge);
				pstmt.setInt(2,idUser);
				pstmt.setString(3,Text);
				pstmt.setString(4,Photo);
				pstmt.setString(5,Date);
				pstmt.setString(6,Time);
				pstmt.setInt(7, Post);
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
				pstmt = con.prepareStatement("SELECT idUser FROM "+SCHEMA+".completedchallenges WHERE idUser=? AND idChallenges=?"); 
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
				String Query="SELECT LEVEL01,LEVEL02,LEVEL03,LEVEL04 FROM "+SCHEMA+".CATEGORIES FETCH FIRST 1 ROW ONLY";
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
		
		public String getVersion(){
			String version="0";
			try {
				con=connector.CreateConnection();
				pstmt = con.prepareStatement("SELECT VERSION FROM "+SCHEMA+".APPVERSION"); 
				stmt=con.createStatement();
				rs = pstmt.executeQuery();
				if(!rs.next()){	 
					version=null;
				}else{  
					version=rs.getString("VERSION");
				}
				connector.CloseConnection(con);
				return version;
			} catch (Exception e) {
				e.printStackTrace();
				connector.CloseConnection(con);
				return null;
			}
		}
		
		public ArrayList<CompletedChallenge> getTimeLine(int page,String date){
			ArrayList<CompletedChallenge> Completed=new ArrayList<CompletedChallenge>();
			CompletedChallenge completed;
			int to=page*10;
			int from=to-9;
			try {
				con=connector.CreateConnection();
				String Query="SELECT * FROM ( "
							+"SELECT ROW_NUMBER() OVER(ORDER BY IDCOMPLETEDCHALLENGES DESC) AS ROW, "
							+ "IDCOMPLETEDCHALLENGES, "
							+ "DATE, "
							+ "TIME, "
							+ SCHEMA+".COMPLETEDCHALLENGES.IDCHALLENGES, "
							+ SCHEMA+".CHALLENGES.NAME AS CNAME, "
							+ SCHEMA+".CHALLENGES.IDCATEGORY,"
							+ SCHEMA+".USERS.DISPLAYNAME AS UNAME, "
							+ SCHEMA+".USERS.IDUSER "
							+ "FROM  "+SCHEMA+".COMPLETEDCHALLENGES "
							+ "INNER JOIN "+SCHEMA+".CHALLENGES ON "+SCHEMA+".COMPLETEDCHALLENGES.IDCHALLENGES="+SCHEMA+".CHALLENGES.IDCHALLENGES "
							+ "INNER JOIN "+SCHEMA+".USERS ON "+SCHEMA+".COMPLETEDCHALLENGES.IDUSER="+SCHEMA+".USERS.IDUSER "
							+ "WHERE "+SCHEMA+".COMPLETEDCHALLENGES.DATE=? "
							+ ") WHERE ROW BETWEEN ? AND ?";
				pstmt = con.prepareStatement(Query);
				pstmt.setString(1,date);
				pstmt.setInt(2,from);
				pstmt.setInt(3,to);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					completed= new CompletedChallenge();
					completed.setIdCompletedChallenge(rs.getInt("IDCOMPLETEDCHALLENGES"));
					completed.setIdChallenge(rs.getInt("IDCHALLENGES"));
					completed.setIdUser(rs.getInt("IDUSER"));
					completed.setChallengeName(rs.getString("CNAME"));
					completed.setUserName(rs.getString("UNAME"));
					completed.setIdCategory(rs.getInt("IDCATEGORY"));
					completed.setDate(rs.getString("DATE"));
					completed.setTime(rs.getString("TIME"));
					Completed.add(completed);
				}      
				connector.CloseConnection(con);
				return Completed;
			}catch (Exception e) {
				e.printStackTrace();
				connector.CloseConnection(con);
				return null;
			}
		}
		
		
	
}//END OF CLASS
