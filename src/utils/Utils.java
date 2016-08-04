package utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.imageio.ImageIO;

public class Utils {
	
			/**
			 * Get json from faces api and return it
			 * @param id (Intranet id)
			 * @return  Json (Employee info)
			 * @throws IOException
			 */
			public String getBlueJson(String id) throws IOException{
				String JsonUrl="http://faces.w3ibm.mybluemix.net/api/find/?q=email:\""+id+"\"";
				BufferedReader reader = null;
			    try {
			        URL url = new URL(JsonUrl);
			        reader = new BufferedReader(new InputStreamReader(url.openStream()));
			        StringBuffer buffer = new StringBuffer();
			        int read;
			        char[] chars = new char[2048];
			        while ((read = reader.read(chars)) != -1){
			            buffer.append(chars, 0, read); 
			        }
			        return buffer.toString();
				}finally {
			        if (reader != null)
			            reader.close();
			    }
			}
			
			/**
			 * Get json string or record and return the selected string
			 * @param json (full json)
			 * @param str  (criteria to find string)
			 * @return	   (value of the selected criteria)
			 */
			public String getFromJson(String json,String str){
				int l=str.length()+3;
				String aux1=json.substring(json.indexOf(str)+l,json.length());//Delete 0 to name
				String aux2=aux1.substring(0,aux1.indexOf("\","));// Delete name to length
				return aux2;
			}
			/**
			 * Delete the file
			 * @param file
			 */
			public void DeleteTempFile(File file){
				if(file.delete()){System.out.println("File Successfuly Deleted");}
				else{System.out.println("Failed To Delete File");}
			}
			
			/**
			 * Encode the file in byte array to base64
			 * @param imageByteArray
			 * @return base64
			 */
			public String encodeFile(byte[] imageByteArray){
				return Base64.getEncoder().encodeToString(imageByteArray);
			}
			
			/**
			 * Get Image in url and Return it in ByteArray
			 * @param url
			 * @return image in ByteArray
			 */
			public byte[] UrlToByte(String url){
				try{
				URL Url = new URL(url);
				BufferedImage image = ImageIO.read(Url);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write( image, "jpg", baos );
				baos.flush();
				byte[] imageInByte = baos.toByteArray();
				baos.close();
				return imageInByte;
				}catch(Exception e){
					e.printStackTrace();
					return null;
				}
			}
			
			/**
			 * Descode base64 String
			 * @param Encoded String 
			 * @return Decoded String or -1 if failed
			 */
			public String Decode(String Encoded){
				try{
				String Decoded=new String(Base64.getDecoder().decode(Encoded));
				return Decoded;
				}catch(Exception e){
					System.out.println("Failed To Decode base64");
					return "-1";
				}
			}
			
			/**
			 * Return the Current EST Date in dd/mm/yy format
			 * @return Date
			 */
			public String getDate(){
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
				dateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
				Date date = new Date();
				return dateFormat.format(date);
			}
			
			/**
			 * Return current EST Time in hh:mm format
			 * @return Time
			 */
			public String getTime(){
				DateFormat dateFormat = new SimpleDateFormat("HH:mm");
				dateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
				Date date = new Date();
				return dateFormat.format(date);
			}
			
			/**
			 * Return n last days
			 * @param n (last days)
			 * @return string in json format 
			 */
			public String getLastDays(int n ){
				String Json="{\"records\":[";
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
				dateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
				for(int a=0;a<n;a++){
					Calendar cal = Calendar.getInstance();
				    cal.add(Calendar.DATE, -a); 
				    Json=Json+"{\"Date\":\""+dateFormat.format(cal.getTime())+"\"}";
				    if(a<4){Json=Json+",";}
				}		
				return Json+"]}";
			}
			/**
			 * Remove /n and "
			 * @param text
			 * @return sanitized text
			 */
			public String Sanitize(String text){
				text=text.replaceAll("\n", " ");
				text=text.replaceAll("\"", "'");
				return text;
			}

  }
