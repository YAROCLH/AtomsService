<%@page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="Control.Services.Control" %>

<%			response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
		   String filePath = "/Temp/";
		   String IDUSER="",IDCHALLENGE="",TEXT="";
		   String contentType = request.getContentType();
		   if ((contentType.indexOf("multipart/form-data") >= 0)) {
			 try{
			 	Control con=new Control();
			 	File file=null;
	            List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
	            for(FileItem item : multiparts){
	            	if(item.isFormField()){//parameters
	            		String name = item.getFieldName();
	                    String value = item.getString();
	                    if(name.equals("idUser")){
        					IDUSER=value;
				        }else{
				        	if(name.equals("idChallenge")){
				        		IDCHALLENGE=value;	
				        	}else{
				        		if(name.equals("Attach")){
				        			TEXT=value;
				        		}
				        	}  
				        }
	            	}else{//file
	            		String name=item.getName();
	            		file = new File(filePath+name) ;
                		item.write( file ) ;
	            	}
	            }
	           String res= con.completeChallenge(file,con.Decode(IDUSER),con.Decode(IDCHALLENGE),con.Decode(TEXT));
	           out.print(res);
	        }catch (Exception ex) {System.out.println("Error:"+ex.getMessage());}          
        }else{
            System.out.print("Sorry this Servlet only handles file upload request");
        }
		    
%>