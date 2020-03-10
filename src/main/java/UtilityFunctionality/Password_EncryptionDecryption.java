package UtilityFunctionality;

import org.apache.commons.codec.binary.Base64;

public class Password_EncryptionDecryption {
	public static void main(String[] args) {
			
			String text = "VGVzdEAxMjM0";
			//String Activity = "Encrypt";
			String Activity = "Decrypt";
			
			if(Activity == "Encrypt") {
			  byte[] encodedPassword = Base64.encodeBase64(text.getBytes());
			  System.out.println("Encoded Password is :"+ new String(encodedPassword));
			}
			else if(Activity == "Decrypt") {
			  byte[] decodedPassword = Base64.decodeBase64(text);
			  System.out.println("Decoded Password is :"+ new String(decodedPassword));
			}
	}
}
