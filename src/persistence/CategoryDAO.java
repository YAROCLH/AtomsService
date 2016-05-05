package persistence;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.Category;

public class CategoryDAO {
		ResultSet rs;
		PreparedStatement pstmt;
		Statement stmt;
		Connection con;
		Connector connector;
		
		public CategoryDAO(){
			connector=new Connector();
		}
	
		
		
		
		public ArrayList<Category> getCategories(){
			Category category;
			ArrayList<Category>categories=new ArrayList<Category>(); 
			try {
				con=connector.CreateConnection();
				pstmt = con.prepareStatement("SELECT IdCategory,Name,Description FROM atomsdb.Categories"); 
				rs = pstmt.executeQuery();
				while (rs.next()) {
					category=new Category();
					category.setId(rs.getInt("IdCategory"));
					category.setName(rs.getString("Name"));
					category.setDescription(rs.getString("Description"));
					categories.add(category);
				}      
				connector.CloseConnection(con);
				return categories;
			}catch (Exception e) {
				e.printStackTrace();
				connector.CloseConnection(con);
				return null;
			}
		}
		
		
		
		public boolean createCategory(Category category){
			try{
				
				con=connector.CreateConnection();
				String Query="INSERT INTO atomsdb.CATEGORIES (NAME,DESCRIPTION,VIEWPOSITION,IMAGEBADGEURL,LEVEL01,LEVEL02,LEVEL03,LEVEL04) VALUES(?,?,?,?,?,?,?,?)"; 
				pstmt = con.prepareStatement(Query); 
				pstmt.setString(1, category.getName());
				pstmt.setString(2,category.getDescription());
				pstmt.setInt(3, 0);
				pstmt.setString(4, "None");
				pstmt.setInt(5, category.getLvl1());
				pstmt.setInt(6, category.getLvl2());
				pstmt.setInt(7, category.getLvl3());
				pstmt.setInt(8,category.getLvl4());
				pstmt.executeUpdate();
				connector.CloseConnection(con);
				return true;
			}catch(Exception e){
				e.printStackTrace();
				connector.CloseConnection(con);
				return false;
			}
		}
		
		public Category getCategory(int id){
			Category category=new Category();
			try {
				con=connector.CreateConnection();
				pstmt = con.prepareStatement("SELECT * FROM atomsdb.CATEGORIES WHERE IDCATEGORY  = ?"); 
				pstmt.setInt(1,id);
				rs = pstmt.executeQuery();
				if(!rs.next()){	 
					category.setId(0);
				}else{  
					category.setId(rs.getInt("IDCATEGORY"));
					category.setName(rs.getString("NAME"));
					category.setDescription(rs.getString("DESCRIPTION"));
					int l1=rs.getInt("LEVEL01");
					int l2=rs.getInt("LEVEL02");
					int l3=rs.getInt("LEVEL03");
					int l4=rs.getInt("LEVEL04");
					category.setLvl(l1, l2, l3, l4);
				}
				connector.CloseConnection(con);
				return category;
			} catch (Exception e) {
				e.printStackTrace();
				connector.CloseConnection(con);
				return null;
			}
		}

		public boolean setUpdateCategory(Category category){
				try{
					con=connector.CreateConnection();
					String Query="UPDATE atomsdb.CATEGORIES SET NAME=?,DESCRIPTION=?,LEVEL01=?,LEVEL02=?,LEVEL03=?,LEVEL04=?"
								+"WHERE IDCATEGORY=?"; 
					pstmt = con.prepareStatement(Query); 
					pstmt.setString(1,category.getName());
					pstmt.setString(2,category.getDescription());
					pstmt.setInt(3,category.getLvl1());
					pstmt.setInt(4,category.getLvl2());
					pstmt.setInt(5,category.getLvl3());
					pstmt.setInt(6,category.getLvl4());
					pstmt.setInt(7,category.getId());
					pstmt.executeUpdate();
					connector.CloseConnection(con);
					return true;
				}catch(Exception e){
					e.printStackTrace();
					connector.CloseConnection(con);
					return false;
				}
			}

			public boolean setDeleteCategory(){
				return false;
			} 
}
