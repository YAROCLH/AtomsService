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
			String Query="SELECT IDUSER,INTRANETID,DISPLAYNAME,LASTLOGIN,CATEGORY1,CATEGORY2,CATEGORY3,CATEGORY4,CATEGORY5, "
					+ "SUM(CASE WHEN CATEGORY1 IS NULL THEN 0 ELSE CATEGORY1 END+"
					+ "CASE WHEN CATEGORY2 IS NULL THEN 0 ELSE CATEGORY2 END+"
					+ "CASE WHEN CATEGORY3 IS NULL THEN 0 ELSE CATEGORY3 END+"
					+ "CASE WHEN CATEGORY4 IS NULL THEN 0 ELSE CATEGORY4 END+"
					+ "CASE WHEN CATEGORY5 IS NULL THEN 0 ELSE CATEGORY5 END) "
					+ "AS TOTAL FROM (SELECT "+SCHEMA+".USERS.INTRANETID,"+SCHEMA+".USERS.DISPLAYNAME,"+SCHEMA+".USERS.LASTLOGIN,T.IDUSER,"
					+ "MAX(CASE WHEN IDCATEGORY = 1 THEN COMPLETED END) CATEGORY1,MAX(CASE WHEN IDCATEGORY = 2 THEN COMPLETED END) CATEGORY2, "
					+ "MAX(CASE WHEN IDCATEGORY = 3 THEN COMPLETED END) CATEGORY3, MAX(CASE WHEN IDCATEGORY = 4 THEN COMPLETED END) CATEGORY4, "
					+ "MAX(CASE WHEN IDCATEGORY = 5 THEN COMPLETED END) CATEGORY5 "
					+ "FROM "+SCHEMA+".USERS INNER JOIN (SELECT IDUSER,COUNT (IDCOMPLETEDCHALLENGES) "
					+ "AS COMPLETED,"+SCHEMA+".CHALLENGES.IDCATEGORY FROM "+SCHEMA+".COMPLETEDCHALLENGES "
					+ "INNER JOIN "+SCHEMA+".CHALLENGES ON "+SCHEMA+".COMPLETEDCHALLENGES.IDCHALLENGES="+SCHEMA+".CHALLENGES.IDCHALLENGES "
					+ "GROUP BY "+SCHEMA+".COMPLETEDCHALLENGES.IDUSER,IDCATEGORY)T ON "+SCHEMA+".USERS.IDUSER=T.IDUSER "
					+ "GROUP BY T.IDUSER,INTRANETID,DISPLAYNAME,LASTLOGIN)GROUP BY IDUSER,INTRANETID,LASTLOGIN,DISPLAYNAME,CATEGORY5,CATEGORY4,CATEGORY3,CATEGORY2,CATEGORY1 "
					+ "ORDER BY TOTAL DESC";
			con=connector.CreateConnection();
			pstmt = con.prepareStatement(Query); 
			rs = pstmt.executeQuery();
			while (rs.next()) {
				report=new Report();
				report.setIdUser(rs.getInt("IDUSER"));
				report.setUserName(rs.getString("DISPLAYNAME"));
				report.setIntranet(rs.getString("INTRANETID"));
				report.setLastLogin(rs.getString("LASTLOGIN"));
				report.setTotal(rs.getInt("TOTAL"));
				int c1=rs.getInt("CATEGORY1");
				int c2=rs.getInt("CATEGORY2");
				int c3=rs.getInt("CATEGORY3");
				int c4=rs.getInt("CATEGORY4");
				int c5=rs.getInt("CATEGORY5");
				report.setByCatego(c1, c2, c3, c4, c5);
				FullReport.add(report);
			}      
			connector.CloseConnection(con);
			return FullReport;
		}catch (Exception e) {
			e.printStackTrace();
			connector.CloseConnection(con);
			return null;
		}	
	}
	
	
	public Report getLast(Report report){
		try{
			con=connector.CreateConnection();
			String Query="SELECT "+SCHEMA+".CHALLENGES.NAME AS LAST, DATE "
					+ "FROM "+SCHEMA+".CHALLENGES INNER JOIN "+SCHEMA+".COMPLETEDCHALLENGES "
					+ "ON "+SCHEMA+".CHALLENGES.IDCHALLENGES="+SCHEMA+".COMPLETEDCHALLENGES.IDCHALLENGES "
					+ "WHERE "+SCHEMA+".COMPLETEDCHALLENGES.IDUSER=? "
					+ "ORDER BY IDCOMPLETEDCHALLENGES DESC FETCH FIRST ROW ONLY";
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
			String Query="SELECT "+SCHEMA+".USERS.DISPLAYNAME,"+SCHEMA+".USERS.INTRANETID,"+SCHEMA+".USERS.LASTLOGIN "
						+ "FROM "+SCHEMA+".USERS LEFT JOIN "+SCHEMA+".COMPLETEDCHALLENGES ON "+SCHEMA+".COMPLETEDCHALLENGES.IDUSER="+SCHEMA+".USERS.IDUSER "
						+ "WHERE "+SCHEMA+".COMPLETEDCHALLENGES.IDUSER IS NULL GROUP BY DISPLAYNAME,INTRANETID,LASTLOGIN";
			con=connector.CreateConnection();
			pstmt = con.prepareStatement(Query); 
			rs = pstmt.executeQuery();
			while (rs.next()) {
				report=new Report();
				//report.setIdUser(rs.getInt("IDUSER"));
				report.setUserName(rs.getString("DISPLAYNAME"));
				report.setIntranet(rs.getString("INTRANETID"));
				report.setLastLogin(rs.getString("LASTLOGIN"));
				report.setTotal(0);
				report.setByCatego(0,0,0,0,0);
				report.setLast("NONE");
				report.setLastDate("0/0/0");
				Inactive.add(report);
			}      
			connector.CloseConnection(con);
			return Inactive;
		}catch (Exception e) {
			e.printStackTrace();
			connector.CloseConnection(con);
			return null;
		}	
	}
			
			
			
			
			
			
}