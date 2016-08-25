package persistence;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import model.*;
public class ReportDAO {
	ResultSet rs;
	PreparedStatement pstmt;
	Statement stmt;
	Connection con;
	Connector connector;
	String SCHEMA;
	public ReportDAO(){
		connector=new Connector();
		SCHEMA=Connector.schema;
	}
	
	public ArrayList<Report> getCompletedReport(){
		ArrayList<Report>FullReport=new ArrayList<Report>();
		Report report;
		try {
			String Query="SELECT INTRANETID,DISPLAYNAME,LASTLOGIN,USERS.IDUSER, "
					+ "MAX(CASE WHEN IDCATEGORY = 1 THEN COMPLETED END) CATEGORY1,"
					+ "MAX(CASE WHEN IDCATEGORY = 2 THEN COMPLETED END) CATEGORY2,"
					+ "MAX(CASE WHEN IDCATEGORY = 3 THEN COMPLETED END) CATEGORY3,"
					+ "MAX(CASE WHEN IDCATEGORY = 4 THEN COMPLETED END) CATEGORY4,"
					+ "MAX(CASE WHEN IDCATEGORY = 5 THEN COMPLETED END) CATEGORY5,"
					+ "MAX(CASE WHEN IDCATEGORY = 1 THEN SCORE END) SCORE_C1,"
					+ "MAX(CASE WHEN IDCATEGORY = 2 THEN SCORE END) SCORE_C2,"
					+ "MAX(CASE WHEN IDCATEGORY = 3 THEN SCORE END) SCORE_C3,"
					+ "MAX(CASE WHEN IDCATEGORY = 4 THEN SCORE END) SCORE_C4,"
					+ "MAX(CASE WHEN IDCATEGORY = 5 THEN SCORE END) SCORE_C5 "
					+ "FROM USERS INNER JOIN ( "
					+ "SELECT IDUSER,COUNT(IDCOMPLETEDCHALLENGES) AS COMPLETED, SUM (POINTS) AS SCORE,CHALLENGES.IDCATEGORY "
					+ "FROM COMPLETEDCHALLENGES INNER JOIN CHALLENGES ON COMPLETEDCHALLENGES.IDCHALLENGES=CHALLENGES.IDCHALLENGES "
					+ "GROUP BY COMPLETEDCHALLENGES.IDUSER,IDCATEGORY )T ON USERS.IDUSER=T.IDUSER "
					+ "GROUP BY INTRANETID,DISPLAYNAME,LASTLOGIN,USERS.IDUSER ";
			con=connector.CreateConnection();
			pstmt = con.prepareStatement(Query); 
			rs = pstmt.executeQuery();
			while (rs.next()) {
				report=new Report();
				report.setIdUser(rs.getInt("IDUSER"));
				report.setUserName(rs.getString("DISPLAYNAME"));
				report.setIntranet(rs.getString("INTRANETID"));
				report.setLastLogin(rs.getString("LASTLOGIN"));
				int c1=rs.getInt("CATEGORY1");
				int c2=rs.getInt("CATEGORY2");
				int c3=rs.getInt("CATEGORY3");
				int c4=rs.getInt("CATEGORY4");
				int c5=rs.getInt("CATEGORY5");
				int c1S=rs.getInt("SCORE_C1");
				int c2S=rs.getInt("SCORE_C2");
				int c3S=rs.getInt("SCORE_C3");
				int c4S=rs.getInt("SCORE_C4");
				int c5S=rs.getInt("SCORE_C5");
				report.setByCatego(c1, c2, c3, c4, c5);
				report.setByCategoScore(c1S, c2S, c3S, c4S, c5S);
				FullReport.add(report);
			}      
			connector.CloseConnection(con);
			return FullReport;
		}catch (Exception e) {
			System.out.println("Failed in completed report");
			e.printStackTrace();
			connector.CloseConnection(con);
			return null;
		}	
	}
	
	
	public Report getLast(Report report){
		try{
			con=connector.CreateConnection();
			String Query="SELECT CHALLENGES.NAME AS LAST, DATE "
					+ "FROM CHALLENGES INNER JOIN COMPLETEDCHALLENGES "
					+ "ON CHALLENGES.IDCHALLENGES=COMPLETEDCHALLENGES.IDCHALLENGES "
					+ "WHERE COMPLETEDCHALLENGES.IDUSER=? "
					+ "ORDER BY IDCOMPLETEDCHALLENGES DESC FETCH FIRST ROW ONLY";
			stmt=con.createStatement();
			pstmt = con.prepareStatement(Query);
			pstmt.setInt(1,report.getIduser());
			stmt=con.createStatement();
			rs = pstmt.executeQuery();
			if(!rs.next()){	 
				report.setLast("NONE");
				report.setLastDate("0/0/0");
			}else{  
				report.setLast(rs.getString("LAST"));
				report.setLastDate(rs.getString("DATE"));	
			}
			connector.CloseConnection(con);
			return report;
		}catch (Exception e) {
			System.out.println("Failed in get last");
			e.printStackTrace();
			connector.CloseConnection(con);
			report.setLast("FAILED");
			return report;
		}
	}
	
	public ArrayList<Report> getInactive(){
		ArrayList<Report> Inactive=new ArrayList<Report>();
		Report report;
		try {
			String Query="SELECT USERS.DISPLAYNAME,USERS.INTRANETID,USERS.LASTLOGIN "
						+ "FROM USERS LEFT JOIN COMPLETEDCHALLENGES ON COMPLETEDCHALLENGES.IDUSER=USERS.IDUSER "
						+ "WHERE COMPLETEDCHALLENGES.IDUSER IS NULL GROUP BY DISPLAYNAME,INTRANETID,LASTLOGIN";
			con=connector.CreateConnection();
			pstmt = con.prepareStatement(Query); 
			rs = pstmt.executeQuery();
			while (rs.next()) {
				report=new Report();
				//report.setIdUser(rs.getInt("IDUSER"));
				report.setUserName(rs.getString("DISPLAYNAME"));
				report.setIntranet(rs.getString("INTRANETID"));
				report.setLastLogin(rs.getString("LASTLOGIN"));
				report.setByCatego(0,0,0,0,0);
				report.setByCategoScore(0, 0, 0, 0, 0);
				report.setLast("NONE");
				report.setLastDate("0/0/0");
				Inactive.add(report);
			}      
			connector.CloseConnection(con);
			return Inactive;
		}catch (Exception e) {
			System.out.println("Failed in inactive report");
			e.printStackTrace();
			connector.CloseConnection(con);
			return null;
		}	
	}
			
			
			
			
			
			
}