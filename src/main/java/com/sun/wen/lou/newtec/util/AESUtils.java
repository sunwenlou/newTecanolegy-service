package com.sun.wen.lou.newtec.util;

import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AESUtils {
	@SuppressWarnings("unused")
	private static final String AES = "AES";
	private static final String AES_CBC = "AES/CBC/PKCS5Padding";
	private static final String AES_ECB = "AES/ECB/PKCS5Padding";
	private static final String HMACSHA1 = "HmacSHA1";
	private static final int DEFAULT_HMACSHA1_KEYSIZE = 160;
	private static final int DEFAULT_AES_KEYSIZE = 128;
	private static final int DEFAULT_IVSIZE = 16;
	private static SecureRandom random = new SecureRandom();

	public static String queryRandomString(Integer length) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()?-=+";
		if (length.intValue() <= 0) {
			length = Integer.valueOf(16);
		}
		Random random = new Random();
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < length.intValue(); i++) {
			int num = random.nextInt(62);
			buf.append(str.charAt(num));
		}
		return buf.toString();
	}

	public static byte[] hmacSha1(byte[] input, byte[] key)
			throws GeneralSecurityException {
		try {
			SecretKey secretKey = new SecretKeySpec(key, "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(secretKey);
			return mac.doFinal(input);
		} catch (GeneralSecurityException e) {
			throw e;
		}
	}

	public static boolean isMacValid(byte[] expected, byte[] input, byte[] key)
			throws GeneralSecurityException {
		byte[] actual = hmacSha1(input, key);
		return Arrays.equals(expected, actual);
	}

	public static byte[] generateHmacSha1Key() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA1");
		keyGenerator.init(160);
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey.getEncoded();
	}

	private static Key generateKey(String key) throws Exception {
		try {
			return new SecretKeySpec(key.getBytes(), "AES");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static String AES_Encrypt(String keyStr, String input) {
		byte[] encrypt = null;
		try {
			Key key = generateKey(keyStr);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(1, key);
			encrypt = cipher.doFinal(input.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(Base64.encodeBase64(encrypt));
	}

	public static String AES_Decrypt(String keyStr, String input) {
		byte[] decrypt = null;
		try {
			Key key = generateKey(keyStr);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(2, key);
			decrypt = cipher.doFinal(Base64.decodeBase64(input));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(decrypt).trim();
	}

	public static byte[] aesEncrypt(byte[] input, byte[] key) {
		try {
			return aes(input, key, 1);
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] aesEncrypt(byte[] input, byte[] key, byte[] iv) {
		try {
			return aes(input, key, iv, 1);
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | InvalidAlgorithmParameterException
				| IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String aesDecrypt(byte[] input, byte[] key)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		byte[] decryptResult = aes(input, key, 2);
		return new String(decryptResult);
	}

	public static String aesDecrypt(byte[] input, byte[] key, byte[] iv) {
		byte[] decryptResult;
		try {
			decryptResult = aes(input, key, iv, 2);
			return new String(decryptResult);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	private static byte[] aes(byte[] input, byte[] key, int mode)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		SecretKey secretKey = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(mode, secretKey);
		return cipher.doFinal(input);
	}

	private static byte[] aes(byte[] input, byte[] key, byte[] iv, int mode)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException {
		SecretKey secretKey = new SecretKeySpec(key, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(mode, secretKey, ivSpec);
		return cipher.doFinal(input);
	}

	public static byte[] generateAesKey() {
		return generateAesKey(128);
	}

	public static byte[] generateAesKey(int keysize) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(keysize);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] generateIV() {
		byte[] bytes = new byte[16];
		random.nextBytes(bytes);
		return bytes;
	}
}