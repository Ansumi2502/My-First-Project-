/**
 * Created By Anamika Pandey
 */
package com.myapp.utilities;

//import org.apache.commons.codec.binary.Base64;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncryptionDecryption {
	public static String encodePassword(String password){
		String encryptedPassword = "";
		//encryptedPassword = new Base64().encodeToString(password.getBytes());
		BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
		encryptedPassword=bCryptPasswordEncoder.encode(password);
		return encryptedPassword;
	}
}
