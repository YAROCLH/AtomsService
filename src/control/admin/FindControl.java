package control.admin;

public class FindControl {

	
	public String Find(String find,String criteria,String tofind){
		String res="";
		switch(find){
			case "user":
				res=findUser(criteria,tofind);
			break;
			case "challenge":
				res=findChallenge(criteria,tofind);
			break;
			case "category":
				res=findCategory(criteria,tofind);
			break;
		}
		return res;
	}
	
	private String findCategory(String criteria,String tofind) {
		CategoryControl con=new CategoryControl();
		return con.getCategory(Integer.parseInt(tofind));// by now the only criteria is by id
	}
	private String findUser(String criteria,String tofind){
		UserControl con= new UserControl();
		String res="";
		switch(criteria){
			case "like":
				res=con.FindUser(tofind);
			break;
			case "byIntranet":
				res=con.getUserbyIntranet(tofind);
			break;	
		}
		return res;
	}
	
	private String findChallenge(String criteria,String tofind){
		
		return null;
	}
}
