package Services;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Decrypt {
		final String PASSPHRASE="BB85B8C1336755EAD02368931499380D";
		final String IV="31fbf42ee1d26041";
		public String DecryptData(String EncryptedData){
			try{
				System.out.println("Original: "+EncryptedData);
				Key key = new SecretKeySpec(PASSPHRASE.getBytes(), "AES");
				AlgorithmParameterSpec iv = new IvParameterSpec(IV.getBytes());
	        	Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
				c.init(Cipher.DECRYPT_MODE, key,iv);
				byte[] decodedValue =Base64.getDecoder().decode(EncryptedData.getBytes("UTF-8"));
				System.out.println("Decoded:"+decodedValue);
		        byte[] decValue = c.doFinal(decodedValue);
		        String DecryptedData = new String(decValue);
		        return DecryptedData;
			}catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | 
					IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException | UnsupportedEncodingException e) {
				e.printStackTrace();
				return "Failed";
			}	
		}
		
		public String Decode(String Encoded){
			String Decoded=new String(Base64.getDecoder().decode(Encoded));
			System.out.println(Decoded);
			return Decoded;
		}
		
		
}
