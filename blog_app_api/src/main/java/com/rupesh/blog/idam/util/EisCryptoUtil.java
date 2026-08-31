package com.rupesh.blog.idam.util;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Arrays;
import java.util.Base64;

public class EisCryptoUtil {

	private static final int AES_KEY_LENGTH = 32;
	private static final int GCM_IV_LENGTH = 12;
	private static final int GCM_TAG_LENGTH = 128;

	/**
	 * Converts 32-character ASCII/UTF-8 secret into AES-256 key.
	 */
	public static SecretKeySpec createAesKey(String secretKey) {
		if (secretKey == null) {
			throw new IllegalArgumentException("Secret key cannot be null");
		}
		byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
		if (keyBytes.length != AES_KEY_LENGTH) {
			throw new IllegalArgumentException("AES secret key must be exactly 32 bytes");
		}
		return new SecretKeySpec(keyBytes, "AES");
	}

	/**
	 * AES-256-GCM encryption.
	 *
	 * IV = first 12 bytes of AES secret key.
	 *
	 * Returns Base64(ciphertext + GCM authentication tag)
	 */
	public static String encryptPayload(String plainPayload, String secretKey) throws Exception {

		SecretKeySpec aesKey = createAesKey(secretKey);
		byte[] keyBytes = aesKey.getEncoded();
		// First 12 bytes of secret key are used as IV
		byte[] iv = Arrays.copyOfRange(keyBytes, 0, GCM_IV_LENGTH);
		GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, aesKey, gcmSpec);
		byte[] encrypted = cipher.doFinal(plainPayload.getBytes(StandardCharsets.UTF_8));
		return Base64.getEncoder().encodeToString(encrypted);
	}

	/**
	 * RSA-OAEP encryption of AES secret key.
	 */
	public static String encryptSecretKey(String secretKey, PublicKey eisPublicKey) throws Exception {

		byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
		if (keyBytes.length != AES_KEY_LENGTH) {
			throw new IllegalArgumentException("Secret key must be exactly 32 bytes");
		}
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, eisPublicKey);
		byte[] encrypted = cipher.doFinal(keyBytes);
		return Base64.getEncoder().encodeToString(encrypted);
	}

	/**
	 * SHA256withRSA digital signature.
	 *
	 * IMPORTANT: Sign the PLAIN request, not the encrypted request.
	 */
	public static String signPayload(String plainPayload, PrivateKey channelPrivateKey) throws Exception {

		Signature signature = Signature.getInstance("SHA256withRSA");
		signature.initSign(channelPrivateKey);
		signature.update(plainPayload.getBytes(StandardCharsets.UTF_8));
		byte[] signed = signature.sign();
		return Base64.getEncoder().encodeToString(signed);
	}

	/**
	 * RSA signature verification.
	 */
	public static boolean verifySignature(String plainPayload, String base64Signature, PublicKey channelPublicKey)
			throws Exception {

		Signature signature = Signature.getInstance("SHA256withRSA");
		signature.initVerify(channelPublicKey);
		signature.update(plainPayload.getBytes(StandardCharsets.UTF_8));
		byte[] signatureBytes = Base64.getDecoder().decode(base64Signature);
		return signature.verify(signatureBytes);
	}

	/**
	 * AES-256-GCM decryption.
	 */
	public static String decryptPayload(String base64EncryptedPayload, String secretKey) throws Exception {

		SecretKeySpec aesKey = createAesKey(secretKey);
		byte[] keyBytes = aesKey.getEncoded();
		byte[] iv = Arrays.copyOfRange(keyBytes, 0, GCM_IV_LENGTH);
		GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, aesKey, gcmSpec);
		byte[] encrypted = Base64.getDecoder().decode(base64EncryptedPayload);
		byte[] decrypted = cipher.doFinal(encrypted);
		return new String(decrypted, StandardCharsets.UTF_8);
	}
}
