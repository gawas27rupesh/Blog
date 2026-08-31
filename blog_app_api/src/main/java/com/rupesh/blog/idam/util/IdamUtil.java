package com.rupesh.blog.idam.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

@Component
public class IdamUtil {


    public static final Provider provider = new BouncyCastleProvider();



    public String encrypt(String plaintext, String key) throws Exception {
        byte[] keybytes = key.getBytes("UTF-8");
        byte[] ivkey = Arrays.copyOf(keybytes, 16);
        IvParameterSpec ivSpec = new IvParameterSpec(ivkey);
        SecretKeySpec keySpec = new SecretKeySpec(keybytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] cipherText = cipher.doFinal(plaintext.getBytes("UTF-8"));
        String encvalue = org.apache.commons.codec.binary.Base64
                .encodeBase64String(cipherText)
                .replaceAll("\\r\\n", "");
        return encvalue;
    }

    public String decrypt(String cipherText, String Key) throws Exception {
        byte[] keybytes = Key.getBytes("UTF-8");
        byte[] ivkey = Arrays.copyOf(keybytes, 16);
        IvParameterSpec ivSpec = new IvParameterSpec(ivkey);
        byte[] encvalue = org.apache.commons.codec.binary.Base64.decodeBase64(cipherText);
        SecretKeySpec keySpec = new SecretKeySpec(keybytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decryptedText = cipher.doFinal(encvalue);

        return new String(decryptedText);
    }

    public String signRequest(String plainText, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes());
        byte[] signature = privateSignature.sign();
        return org.apache.commons.codec.binary.Base64
                .encodeBase64String(signature)
                .replaceAll("\\r\\n", "");
    }

    public boolean signVerify(String data, String signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(data.getBytes());
        byte[] signatureBytes = org.apache.commons.codec.binary.Base64.decodeBase64(signature);
        return publicSignature.verify(signatureBytes);
    }

    public String encryptSHA256Message(String plainText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPadding", provider);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return org.apache.commons.codec.binary.Base64
                .encodeBase64String(cipher.doFinal(plainText.getBytes()));
    }

    public PrivateKey getPrivateKey(String alias, char[] password, KeyStore keystore)
            throws InvalidCertificateKeyException {
        PrivateKey privateKey;
        try {
            privateKey = (PrivateKey) keystore.getKey(alias, password);
        } catch (Exception e) {
            throw new InvalidCertificateKeyException(
                    "Invalid alias/password." + e.getLocalizedMessage());
        }
        return privateKey;
    }

    public PublicKey getPublicKey(String alias, char[] password, KeyStore ks)
            throws InvalidCertificateKeyException, NoSuchAlgorithmException,
            CertificateException, FileNotFoundException, IOException, KeyStoreException {

        PublicKey publicKey = null;


        X509Certificate certificate = (X509Certificate) ks.getCertificate(alias);
        publicKey = (PublicKey) certificate.getPublicKey();

        return publicKey;
    }

    public KeyStore getKeystore(String privateKeyFilePath, char[] password)
            throws InvalidCertificateKeyException {

        KeyStore keystore;
        try {
            keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(new FileInputStream(privateKeyFilePath), password);
        } catch (Exception e) {
            throw new InvalidCertificateKeyException(
                    "Invalid private key certificate." + e.getLocalizedMessage());
        }
        return keystore;
    }

    public String decryptSHA256Message(String encryptedText, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPadding", provider);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(
                org.apache.commons.codec.binary.Base64.decodeBase64(encryptedText)));
    }

    
    public String encrypt_GCM(String plaintext, String key) throws Exception {
        try {
            byte[] keybytes = key.getBytes("UTF-8");
            byte[] ivkey = Arrays.copyOf(keybytes, 12);
            SecretKeySpec seckey = new SecretKeySpec(keybytes, "AES");
            Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, ivkey);

            c.init(Cipher.ENCRYPT_MODE, seckey, gcmParameterSpec);
            byte[] encvalue = c.doFinal(plaintext.getBytes("UTF-8"));
            String encryptedvalue = java.util.Base64.getEncoder()
                    .encodeToString(encvalue)
                    .replaceAll("\\r\\n", "");

            return encryptedvalue;

        } catch (InvalidKeyException e) {
            return "JavaError" + e.toString();
        } catch (UnsupportedEncodingException e) {
            return "JavaError" + e.toString();
        } catch (NoSuchAlgorithmException e) {
            return "JavaError" + e.toString();
        } catch (NoSuchPaddingException e) {
            return "JavaError" + e.toString();
        } catch (InvalidAlgorithmParameterException e) {
            return "JavaError" + e.toString();
        } catch (IllegalBlockSizeException e) {
            return "JavaError" + e.toString();
        } catch (BadPaddingException e) {
            return "JavaError" + e.toString();
        }
    }

    public String decrypt_GCM(String cipherText, String Key) throws Exception {
        byte[] keybytes = Key.getBytes("UTF-8");
        byte[] ivkey = Arrays.copyOf(keybytes, 12);
        IvParameterSpec ivSpec = new IvParameterSpec(ivkey);
        byte[] encvalue = org.apache.commons.codec.binary.Base64.decodeBase64(cipherText);
        SecretKeySpec keySpec = new SecretKeySpec(keybytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decryptedText = cipher.doFinal(encvalue);

        return new String(decryptedText);
    }
    
    public static void main(String[] args) throws NoSuchAlgorithmException {
//    	String aesKey = IdamUtil.generateAesKey();
//    	String secretKey = "Abcdefgh12345678Xyz98765432Pqrsy";
//    	byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
//    	System.out.print(keyBytes.length);
	}
}