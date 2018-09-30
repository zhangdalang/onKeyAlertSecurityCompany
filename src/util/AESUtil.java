package util;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
	private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法
    private static final String password = "keson-123";

    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @param password 加密密码
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器

            byte[] byteContent = content.getBytes("UTF-8");

            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));// 初始化为加密模式的密码器

            byte[] result = cipher.doFinal(byteContent);// 加密
            return new String(Base64.getEncoder().encode(result),"UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * AES 解密操作
     *
     * @param content
     * @param password
     * @return
     */
    public static String decrypt(byte[] content) {
    	
        try {
        	content = Base64.getDecoder().decode(new String(content,"UTF-8"));

            //瀹炰緥鍖�
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //返回生成指定算法密钥生成器的 KeyGenerator 对象
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));

            //解密
            byte[] result = cipher.doFinal(content);

            return new String(result, "UTF-8");
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        
        return null;
    }

    /**
     * 生成加密秘钥
     *
     * @return
     */
    private static SecretKeySpec getSecretKey(final String password) {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = null;

        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            
            //AES 要求密钥长度为 128
           
            SecureRandom random=null;
            try {
            random = SecureRandom.getInstance("SHA1PRNG","SUN");
            random.setSeed(password.getBytes());
            } catch (NoSuchProviderException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            }
            
            kg.init(128, random);
            
            //生成一个密钥
            SecretKey secretKey = kg.generateKey();

            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
        } catch (NoSuchAlgorithmException ex) {
        	ex.printStackTrace();
        }

        return null;
    }
}
