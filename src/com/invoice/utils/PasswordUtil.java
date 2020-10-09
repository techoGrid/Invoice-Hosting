package com.invoice.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;


public class PasswordUtil {
	private static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	private static final int RANDOM_STRING_LENGTH = 8;

	public String generatePassword() {
		StringBuffer randStr = new StringBuffer();
		for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
			int number = getRandomNumber();
			char ch = CHAR_LIST.charAt(number);
			randStr.append(ch);
		}		
		return randStr.toString();
	}

	private int getRandomNumber() {
		int randomInt = 0;
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(CHAR_LIST.length());
		if (randomInt - 1 == -1) {
			return randomInt;
		} else {
			return randomInt - 1;
		}
	}
	
	public String passwordEncode(String password) {
		byte[] encoded = Base64.encodeBase64(password.getBytes());
		return new String(encoded);
	}
	
	public String passwordDecode(String password) {
		byte[] decoded = Base64.decodeBase64(password); 
		return new String(decoded);
	}
	
	public String generateMD5(String passwordToHash) {
		String generatedPassword = null;
        MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");		
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return generatedPassword;
	}

}
