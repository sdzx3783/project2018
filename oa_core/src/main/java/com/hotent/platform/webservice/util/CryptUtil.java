package com.hotent.platform.webservice.util;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptUtil {

	private static final CryptUtil instance = new CryptUtil();

	public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyz";

	Logger log = LoggerFactory.getLogger(this.getClass());

	private CryptUtil() {

	}

	public static CryptUtil getInstance() {
		return instance;
	}

	public String getSha1(byte[] pwd) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(pwd);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		byte[] result = md.digest();
		StringBuffer sha1 = new StringBuffer();
		for (byte b : result) {
			int i = b & 0xff;
			if (i < 0xf) {
				sha1.append(0);
			}
			sha1.append(Integer.toHexString(i));
		}
		return sha1.toString();
	}

	private Key initKeyForAES(String key) throws NoSuchAlgorithmException {
		if (null == key || key.length() == 0) {
			throw new NullPointerException("key not is null");
		}
		SecretKeySpec key2 = null;
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			// 对于不同平台（windows,linux)一致的设置）
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(key.getBytes());

			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			key2 = new SecretKeySpec(enCodeFormat, "AES");
		} catch (NoSuchAlgorithmException ex) {
			throw new NoSuchAlgorithmException();
		}
		return key2;

	}

	public String encryptAES(String content, String key) {
		try {
			SecretKeySpec secretKey = (SecretKeySpec) initKeyForAES(key);
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return asHex(result); // 加密
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String decryptAES(String content, String key) {
		try {
			SecretKeySpec secretKey = (SecretKeySpec) initKeyForAES(key);
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, secretKey);// 初始化
			byte[] result = cipher.doFinal(asBytes(content));
			return new String(result); // 加密
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String asHex(byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;
		for (i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10)
				strbuf.append("0");
			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}
		return strbuf.toString();
	}

	public byte[] asBytes(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	// 生成16-32位小写字母数字混合字符串
	public String appKeyGenerator() {
		StringBuffer sb = new StringBuffer();

		Random random = new Random();

		int length = 16 + random.nextInt(17);

		for (int i = 0; i < length; i++) {

			sb.append(allChar.charAt(random.nextInt(allChar.length())));

		}

		return sb.toString();
	}

	/**
	 * 对字符串进行MD5签名
	 * 
	 * @param text
	 *            明文
	 * 
	 * @return 密文
	 */
	public static String md5(String text) {

		return DigestUtils.md5Hex(getContentBytes(text, "UTF-8"));

	}

	/**
	 * @param content
	 * @param charset
	 * @return
	 * @throws SignatureException
	 * @throws UnsupportedEncodingException
	 */
	private static byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}

		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
		}
	}

	public static void main(String[] args) {
		/*
		 * CryptUtil crypt = CryptUtil.getInstance(); for (int i = 0; i <50;
		 * i++) { String x = crypt.appKeyGenerator();
		 * System.out.println(x.length()+":"+x); }
		 */
		/*
		 * String x = "www.icaifu.com";
		 * System.out.println(x+"--md5-->"+CryptUtil.md5(x)); x = "i财富理财网";
		 * System.out.println(x+"--md5-->"+CryptUtil.md5(x));
		 */

		CryptUtil crypt = CryptUtil.getInstance();
		/*
		 * String content ="32010119690901023X"; String econtent =
		 * crypt.(content, Constants.CRYPT_KEY); System.out.println(econtent);
		 * String dcontent = crypt.decryptAES(econtent, Constants.CRYPT_KEY);
		 * System.out.println(dcontent);
		 */

		/*
		 * System.out.println(crypt.encryptAES("6222024000035990668",
		 * Constants.CRYPT_KEY));
		 * System.out.println(crypt.getSha1(("88888"+crypt.getSha1("eeeeee".
		 * getBytes())).getBytes()));
		 * System.out.println(crypt.getSha1(("66666"+crypt.getSha1("eeeeee".
		 * getBytes())).getBytes()));
		 */
		System.out.println(crypt.getSha1("sherwin666888".getBytes())); // 老密码
		System.out.println(crypt.getSha1("zhaoxuanzx8888".getBytes()));

		System.out.println(
				crypt.decryptAES("5d5dd88cfcf3c2fe3bf7882d9663cbde841e7ef6c2bd4674f01aee1eed0342d1", "icaifu8"));

		System.out.println(CryptUtil.getInstance()
				.getSha1(("54555" + CryptUtil.getInstance().getSha1("123456".getBytes())).getBytes()));

	}
}