package Services;


import java.sql.*;

public class Connector {
	
	Connection connection;
	
	
	public Connection CreateConnection(){
		try{
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			String url = "jdbc:db2://75.126.155.153:50000/SQLDB";
			String user = "user14214";
			String password = "5v7l2FJEHrMF";
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
