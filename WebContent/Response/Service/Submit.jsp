<%@page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@page import="control.services.Control"%>
<%@page import="utils.Utils"%>

<%			response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
		   String filePath = "./Temp";
		   String IDUSER="",IDCHALLENGE="",TEXT="";
		   boolean POST=false;
		   String contentType = request.getContentType();
		   if ((contentType.indexOf("multipart/form-data") >= 0)) {
			 try{
			 	Control con=new Control();
			 	Utils utils=new Utils();
			 	File file=null;
			 	File dir=null;
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
				        		}else{
				        			if(name.equals("connectionsPost")){
				        				System.out.println("connectionsPost"+value);
				        				POST=utils.toBoolean(value);
				        				System.out.print("res post "+POST);
				        			}else{
				        				System.out.println("Name: "+name);
				        			}
				        		}
				        	}  
				        }
	            	}else{//file
	            		String name=item.getName();
	            		dir=new File("./"+filePath);
	            		boolean res=dir.mkdirs();
	            		file = new File(filePath+"/"+name) ;
	            		System.out.println(res+" DA FILE:"+file.getAbsolutePath());
                		item.write( file ) ;
	            	}
	            }
	           String res= con.SubmitChallengeAll(file,utils.Decode(IDUSER),utils.Decode(IDCHALLENGE),utils.Decode(TEXT),POST);
	           out.print(res);
	        }catch (Exception ex) {System.out.println("Error:"+ex.getMessage());}          
        }else{
            System.out.print("Sorry this Servlet only handles file upload request");
        }
		    
%>