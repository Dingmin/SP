package com.sso.client.validation;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

//对URL参数解密
@SuppressWarnings("restriction")
public class EncodeUrl {
	
	public final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
        "6", "7", "8", "9", "a", "b", "c", "d", "e", "f","g","h","i","j" };
	
	
	public final static String way = "DES";
	
    public static String byteToArrayString(byte[] bByte) {
       String result = "";
       int val=0;
        // System.out.println("iRet="+iRet);
        for(int i=0;i<bByte.length;i++){
        	 if (bByte[i] < 0) {
        		val = bByte[i]& 0xff;
             }
             int iD1 =val / 20;
             int iD2 = val % 20;
            result+= strDigits[iD1] + strDigits[iD2];
        }
       
        return result;
    }
	/*
	 * 解密
	 */
	 public static String decrypt(String s)  
     {  
       try  
       {  
         String result = new String();  
         java.util.StringTokenizer st=new java.util.StringTokenizer(s,"*");  
         while (st.hasMoreElements()) {  
           int asc =  Integer.parseInt((String)st.nextElement()) - 40;  
           result = result + (char)asc;  
         }  
 
         return result;  
       }catch(Exception e)  
       {  
         e.printStackTrace() ;  
         return null;  
       }  
     }  
 
     /** 
      *对URL加密
     */  
     public static String encrypt(String s)  
     {  
       try  
       {  
         byte[] byte_s = s.getBytes("utf-8");  
         String result = new String();  
        // char[] _ssoToken = ssoToken.toCharArray();  
         for (int i = 0; i < byte_s.length; i++) {  
             int asc = byte_s[i];  
            
             result = result + (asc + 40) + "*";  
         }  
         return result;  
       }catch(Exception e)  
       {  
         e.printStackTrace() ;  
         return null;  
       }  
     }  
    
     
     /**
      * Description 根据键值进行加密
      * @param data 
      * @param key  加密键byte数组
      * @return
      * @throws Exception
      */
     public static String encrypt(String data, String key) throws Exception {
         byte[] bt = encrypt(data.getBytes(), key.getBytes());
         String strs = new BASE64Encoder().encode(bt);
         return strs;
     }
  
     /**
      * Description 根据键值进行解密
      * @param data
      * @param key  加密键byte数组
      * @return
      * @throws IOException
      * @throws Exception
      */
     public static String decrypt(String data, String key) throws IOException,
             Exception {
         if (data == null)
             return null;
         BASE64Decoder decoder = new BASE64Decoder();
         byte[] buf = decoder.decodeBuffer(data);
         byte[] bt = decrypt(buf,key.getBytes());
         return new String(bt);
     }
  
     /**
      * Description 根据键值进行加密
      * @param data
      * @param key  加密键byte数组
      * @return
      * @throws Exception
      */
     private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
         // 生成一个可信任的随机数源
         SecureRandom sr = new SecureRandom();
  
         // 从原始密钥数据创建DESKeySpec对象
         DESKeySpec dks = new DESKeySpec(key);
  
         // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
         SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(way);
         SecretKey securekey = keyFactory.generateSecret(dks);
  
         // Cipher对象实际完成加密操作
         Cipher cipher = Cipher.getInstance(way);
  
         // 用密钥初始化Cipher对象
         cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
  
         return cipher.doFinal(data);
     }
      
      
     /**
      * Description 根据键值进行解密
      * @param data
      * @param key  加密键byte数组
      * @return
      * @throws Exception
      */
     private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
         // 生成一个可信任的随机数源
         SecureRandom sr = new SecureRandom();
  
         // 从原始密钥数据创建DESKeySpec对象
         DESKeySpec dks = new DESKeySpec(key);
  
         // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
         SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(way);
         SecretKey securekey = keyFactory.generateSecret(dks);
  
         // Cipher对象实际完成解密操作
         Cipher cipher = Cipher.getInstance(way);
  
         // 用密钥初始化Cipher对象
         cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
  
         return cipher.doFinal(data);
     }
     
     /*
      * RSA
     
     public static String encripyRSA(String str,PublicKey publickey)
     {
         String result=null;                              //get the encoded string    
         byte[]plainByte=null;                              //get the byte[] of the string which is going to encode
         byte[]cipherByte;                                                       
         Cipher cipher =null;
         try{          
         plainByte=str.getBytes("ISO-8859-1"); 
         cipher=Cipher.getInstance("RSA");
         cipher.init(Cipher.ENCRYPT_MODE,publickey); 
         cipherByte=cipher.doFinal(plainByte);        
         result=new String(cipherByte,"ISO-8859-1"); 
          }catch(Exception err){
             err.printStackTrace();
             System.out.println("error in encode: "+err.toString());
         }
         return result;
     }
     
     public String decripyRSA(String str,PrivateKey privateKey)
     {
          byte[] cipherByte =null;                                    
          byte[] plainByte   =null;                             //the byte[] of the answer
          String   result    =null;                            //get the answer
          Cipher   cipher      =null;                            //加密用;
         try{
             cipherByte       =str.getBytes("ISO-8859-1");    // comfirm a encode method
             cipher =Cipher.getInstance("RSA");
             cipher.init(Cipher.DECRYPT_MODE,privateKey);
             plainByte=cipher.doFinal(cipherByte);
             result=new String(plainByte,"ISO-8859-1");         
         }catch(Exception err)
         {
        	 System.out.println("RSA加密出错");
             return "";
         }
         return result;
     }
     */
     
     public String getToken(String str){
    	 MessageDigest mdInst;
		try {
			mdInst = MessageDigest.getInstance("MD5");
			 return byteToArrayString(mdInst.digest(str.getBytes()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("md5加密失败");
			return "";
		}
   
     }
}
