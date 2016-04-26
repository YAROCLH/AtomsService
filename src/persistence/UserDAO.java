package persistence;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import model.User;
public class UserDAO {

			ResultSet rs;
			PreparedStatement pstmt;
			Statement stmt;
			Connection con;
			Connector connector;
		
			public UserDAO(){
				connector=new Connector();
			}

			public User Login(String Name,String Pass){
				User user=new User();
				try {
					con=connector.CreateConnection();
					stmt=con.createStatement();
					stmt.execute("SET encryption password = 'AtomsPassword'");
					pstmt = con.prepareStatement("SELECT idUser,DisplayName FROM atomsdb.users WHERE IntranetID=? AND Password= ENCRYPT(?) AND TYPE=1"); 
					pstmt.setString(1,Name);
					pstmt.setString(2, Pass);
					rs = pstmt.executeQuery();
					if(!rs.next()){	 
						user.setId(0);System.out.println("not found");
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
			
			public boolean newUser(User user){
				try{
					con=connector.CreateConnection();
					stmt=con.createStatement();
					stmt.execute("SET encryption password = 'AtomsPassword'");
					String Query="INSERT INTO atomsdb.USERS (DISPLAYNAME,INTRANETID,PASSWORD,TYPE) VALUES(?,?,ENCRYPT(?),?)";
					pstmt = con.prepareStatement(Query); 
					pstmt.setString(1, user.getName());
					pstmt.setString(2, user.getIntranetId());
					pstmt.setString(3,user.getPassword());
					pstmt.setInt(4, user.getType());
					pstmt.executeUpdate();
					connector.CloseConnection(con);
					return true;
				}catch(Exception e){
					e.printStackTrace();
					connector.CloseConnection(con);
					return false;
				}
			}
			
			public User getUser(String name){
				User user=new User();
				try {
					con=connector.CreateConnection();
					pstmt = con.prepareStatement("SELECT * FROM atomsdb.USERS WHERE INTRANETID  = ?"); 
					pstmt.setString(1,name);
					rs = pstmt.executeQuery();
					if(!rs.next()){	 
						user.setId(0);
					}else{  
						user.setName(rs.getString("DisplayName"));
						user.setIntranetId(rs.getString("IntranetId"));
						user.setType(rs.getInt("Type"));
						user.setId(rs.getInt("IDUSER"));
					}
					connector.CloseConnection(con);
					return user;
				} catch (Exception e) {
					e.printStackTrace();
					connector.CloseConnection(con);
					return null;
				}
			}
			
			public ArrayList<User> findUser(String s){
				User user;
				ArrayList<User> users =new ArrayList<User>();
				try {
					con=connector.CreateConnection();
					pstmt = con.prepareStatement("SELECT INTRANETID FROM atomsdb.USERS WHERE INTRANETID LIKE ?"); 
					pstmt.setString(1,s+"%");
					rs = pstmt.executeQuery();
					while(rs.next()){
						user=new User();
						user.setIntranetId(rs.getString("INTRANETID"));
						users.add(user);
					}
					connector.CloseConnection(con);
					return users;
				} catch (Exception e) {
					e.printStackTrace();
					connector.CloseConnection(con);
					return null;
				}
			}

			
			public boolean setUpdate(User user){
				try{
					con=connector.CreateConnection();
					String Query="UPDATE atomsdb.USERS SET DISPLAYNAME=?,INTRANETID=?,TYPE=? WHERE IDUSER=?";
					pstmt = con.prepareStatement(Query); 
					pstmt.setString(1, user.getName());
					pstmt.setString(2, user.getIntranetId());
					pstmt.setInt(3, user.getType());
					pstmt.setInt(4,user.getId());
					pstmt.executeUpdate();
					connector.CloseConnection(con);
					return true;
				}catch(Exception e){
					e.printStackTrace();
					connector.CloseConnection(con);
					return false;
				}
			}
			
			
	}
