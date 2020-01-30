package com.mjgallery.mjgallery.rsa;


import android.text.TextUtils;
import android.util.Base64;

import com.mjgallery.mjgallery.app.utils.Utils;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class RSAEncrypt {

    /**
     * 加密方式，android的
     */
    public static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    /**
     * 字节数据 转字符串集合
     */
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    /**加密方式，标准jdk的*/
//    public static final String TRANSFORMATION = "RSA/None/PKCS1Padding";

//    /**
//     * 加载私钥-公钥 字符串
//     */
//    public static String loadStringKey(String filename){
//        try {
//            Resource resource = new ClassPathResource(filename);
//            InputStreamReader streamReader = new InputStreamReader(resource.getInputStream());
//            BufferedReader reader = new BufferedReader(streamReader);
//            String readLine = null;
//            StringBuilder sb = new StringBuilder();
//            while ((readLine = reader.readLine()) != null){
//                sb.append(readLine);
//            }
//            reader.close();
//            return sb.toString();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return "";
//    }

    /**
     * 从字符串加载公钥
     *
     * @param filename 公钥 文件名
     */
    public static RSAPublicKey loadPublicKey(String filename) throws Exception {
        if (TextUtils.isEmpty(filename)) {
            return null;
        }
        try {
            byte[] buffer = Base64.decode(Utils.getFromAssets(filename), Base64.NO_WRAP);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 从字符串加载私钥
     *
     * @param filename 私钥 文件名
     */
    public static RSAPrivateKey loadPrivateKey(String filename) throws Exception {
        if (TextUtils.isEmpty(filename)) {
            return null;
        }
        try {
            byte[] buffer = Base64.decode(Utils.getFromAssets(filename), Base64.NO_WRAP);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 公钥加密
     *
     * @param publicKey   公钥
     * @param encryptData 加密数据
     * @throws Exception
     */
    public static byte[] publicKeyEncrypt(RSAPublicKey publicKey, byte[] encryptData) throws Exception {
        if (null == publicKey) {
            throw new NullPointerException("加密公钥为空！");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(encryptData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 公钥解密
     *
     * @param publicKey   解密公钥
     * @param encryptData 解密数据
     * @throws Exception
     */
    public static byte[] publicKeyDecrtypt(RSAPublicKey publicKey, byte[] encryptData) throws Exception {
        if (null == encryptData) {
            throw new NullPointerException("解密公钥为空！");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(encryptData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    /**
     * 私钥加密
     *
     * @param privateKey  私钥
     * @param encryptData 加密数据
     * @throws Exception
     */
    public static byte[] privateKeyEncrypt(RSAPrivateKey privateKey, byte[] encryptData) throws Exception {
        if (null == privateKey) {
            throw new NullPointerException("加密私钥为空！");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(encryptData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }

    }

    /**
     * 私钥解密
     *
     * @param encryptData 解密数据
     * @throws Exception
     */
    public static byte[] privateKeyDecrypt(byte[] encryptData) throws Exception {
        RSAPrivateKey privateKey = loadPrivateKey("privateKey.keystore");
        if (null == privateKey) {
            throw new NullPointerException("解密私钥为空！");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(encryptData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }

    }


    /**
     * 字节流数据转十六进制字符串
     *
     * @param byteData
     * @throws Exception
     */
    public static String byteToString(byte[] byteData) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            // 取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
            stringBuilder.append(HEX_CHAR[(byteData[i] & 0xf0) >>> 4]);
            // 取出字节的低四位 作为索引得到相应的十六进制标识符
            stringBuilder.append(HEX_CHAR[(byteData[i] & 0x0f)]);
            if (i < byteData.length - 1) {
                stringBuilder.append(' ');
            }
        }
        return stringBuilder.toString();
    }


    public static void mainAA() throws Exception {
//        String encrypt = "ihep_公钥加密私钥解密";
//        String token = UUID.randomUUID().toString();
//        RSAPublicKey publicKey = loadPublicKey("publicKey.keystore");
//        RSAPrivateKey privateKey = loadPrivateKey("privateKey.keystore");
//        Base64.encode(privateKeyEncrypt(privateKey, token.getBytes()));
//        String ac=  Base64.encode(RSAEncrypt.privateKeyEncrypt(privateKey,token.getBytes()));
////
//        System.out.println("token::::::"+ac);
////        // 公钥 加密
////        byte[] bytes = publicKeyEncrypt(publicKey, token.getBytes());
////        String encryptEncode = Base64.encode(bytes);
////        System.out.println("加密后字符串数据:::"+encryptEncode);
//       String ab= Base64.encode(publicKeyEncrypt(publicKey, publicKeyDecrtypt(publicKey, Base64.decode("Q68++3MGLJIteihK/dgf0zsHH931AQ9fElLYCuEvsPJqQKKIjCgN5k/g/ehPM2vaPL56VflcQWyLpo48wJ4mjurcstNfU4oq/5RRRn4RoBU5I35nhvT2mR6hH4qerUdP5ZNm56AfMtp0i93LnRkxSecsmCGxH4eJ+gFVvTXely8="))));
////
////
////        System.out.println("加密后字符串数据:::"+encryptEncode);
////        // 私钥 解密
//        byte[] decrypt = RSAEncrypt.privateKeyDecrypt(Base64.decode(token));
//        System.out.println("解密后字符串数据:::"+new String(decrypt));
    }

}
