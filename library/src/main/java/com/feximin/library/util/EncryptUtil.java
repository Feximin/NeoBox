package com.feximin.library.util;
import android.util.Base64;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class EncryptUtil {

	  
	private EncryptUtil() {   }  
	
    private static byte[] desEncrypt(byte[] plainText, String desKey) throws Exception {
        SecureRandom sr = new SecureRandom();   
        byte rawKeyData[] = desKey.getBytes();  
        DESKeySpec dks = new DESKeySpec(rawKeyData);  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
        SecretKey key = keyFactory.generateSecret(dks);  
        Cipher cipher = Cipher.getInstance("DES");  
        cipher.init(Cipher.ENCRYPT_MODE, key, sr);  
        byte encryptedData[] = cipher.doFinal(plainText);  
        return encryptedData;  
    }  
  
    private static byte[] desDecrypt(byte[] encryptText, String desKey) throws Exception {  
        SecureRandom sr = new SecureRandom();  
        byte rawKeyData[] = desKey.getBytes();  
        DESKeySpec dks = new DESKeySpec(rawKeyData);  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
        SecretKey key = keyFactory.generateSecret(dks);  
        Cipher cipher = Cipher.getInstance("DES");  
        cipher.init(Cipher.DECRYPT_MODE, key, sr);  
        byte decryptedData[] = cipher.doFinal(encryptText);  
        return decryptedData;  
    }  
  
    public static String encrypt(String input, String key) throws Exception {  
        return Base64.encodeToString(desEncrypt(input.getBytes(), key), Base64.DEFAULT);  
    }  
  
    public static String decrypt(String input, String key) throws Exception {  
        byte[] result = Base64.decode(input, Base64.DEFAULT);  
        return new String(desDecrypt(result, key));  
    }  


    
    
}