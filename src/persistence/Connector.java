package persistence;


import java.sql.*;

public class Connector {
	
	Connection connection;
    
    public static final int Test = 0;
	public static final String ProdSchema="ATOMSDB";
	public static final String TestSchema="ATOMSTEST";
	public static String schema=(Test == 0) ? ProdSchema: TestSchema;

	public Connection CreateConnection(){
		try{
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			String url = "jdbc:db2://169.54.229.15:50000/S0447899:clientRerouteAlternateServerName=169.55.248.232;clientRerouteAlternatePortNumber=50000;currentSchema="+schema+";";
			String user = "ininjfpv";
			String password = "nh1xy6imjjoe";
			connection= DriverManager.getConnection(url, user, password); 
			return connection;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public void CloseConnection(Connection con){
		try{
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}// END OF CLASS
