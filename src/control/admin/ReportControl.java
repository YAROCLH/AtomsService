package control.admin;

import persistence.*;
import java.util.ArrayList;
import model.*;
public class ReportControl{
		ReportDAO dao;
		String HEADER="Name,Intranet,Productivity,Social,Environment,Wellness,Generosity,Total,LastCompleted,DateLastCompleted,LastLogin\n";
		public ReportControl(){
			dao=new ReportDAO();
		}
	
		public String getFullReport(){
			String CSV=HEADER;
			ArrayList<Report>FullReport=dao.getCompletedReport();
			FullReport=getLast(FullReport);
			ArrayList<Report>Inactive=dao.getInactive();
			CSV=CSV+ToCSV(FullReport);
			CSV=CSV+ToCSV(Inactive);
			return CSV;
		}
		
		public String ToCSV(ArrayList<Report> Reports){
			Report report;
			String buffer="";
			for(int a=0;a<Reports.size();a++){
				report=Reports.get(a);
				if(report.getLastLogin()==null){report.setLastLogin("0/0/0");}
				buffer=buffer+report.getUserName()+","+report.getIntranet()+","+report.getC1()+","
				+report.getC2()+","+report.getC3()+","+report.getC4()+","+report.getC5()+","+report.getTotal()+","
				+report.getLast()+","+report.getLastDate()+","+report.getLastLogin()+"\n";
			}
			return buffer;
		}
		
		public ArrayList<Report> getLast(ArrayList<Report> reports){
			Report report;
			for(int a=0;a<reports.size();a++){
				report=reports.get(a);
				report=dao.getLast(report);
				if(report.getLastDate()==null){
					report.setLastDate("0/0/0");	}
				reports.set(a, report);
			}
			return reports;
		}
	
	
}