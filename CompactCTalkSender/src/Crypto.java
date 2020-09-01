import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class Crypto {
     
    private static Cipher cipher;
    private static SecretKeySpec cipherKey;
    private static IvParameterSpec iv;

    public Crypto(String sKey, String sIv) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException{
    	this.cipher = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
    	this.cipherKey = new SecretKeySpec(lPad(sKey, 16, "@").getBytes("UTF-8"), "Blowfish");
    	this.iv = new IvParameterSpec(lPad(sIv, 8, "$").getBytes("UTF-8"));
    }
    
    public byte[] decode(byte[] bytes) throws Exception{
         
        cipher.init(Cipher.DECRYPT_MODE, cipherKey, iv);
         
        return cipher.doFinal(bytes);
    }
     
    public byte[] encode(byte[] bytes) throws Exception{
 
        cipher.init(Cipher.ENCRYPT_MODE, cipherKey, iv);
         
        return cipher.doFinal(bytes);
    }
    
    public String decode(String encoded) throws Exception{
    	byte[] encodedbytes = Base64.decode(encoded);
    	byte[] bytes =  decode(encodedbytes);
    	return new String(bytes, "UTF-8");
    }
     
    public String encode(String s) throws Exception{
    	byte[] bytes = s.getBytes("UTF-8");
    	byte[] encodedbytes = encode(bytes);
    	return Base64.encode(encodedbytes);
    }
     
     
	private String lPad (String inputString, int length, String padString) {
	    if (inputString.length() >= length) {
	        return inputString.substring(0, length);
	    }
	    StringBuilder sb = new StringBuilder();
	    while (sb.length() < length - inputString.length()) {
	        sb.append(padString);
	    }
	    sb.append(inputString);
	 
	    return sb.toString();
	}
     
}
