package com.dev.spring.common.security;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;

public class SHA256 {
	
	public static void main(String args[]){
		try {
			String data = "test";
			System.out.println(encrypt(data));
			System.out.println("encrypt data...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public static String encrypt(String data) throws Exception {
    	byte[] textBytes = data.getBytes("UTF-8");
    	
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        return Base64.encodeBase64String(md.digest(textBytes));
    }
}
