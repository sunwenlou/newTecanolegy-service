package com.sun.wen.lou.newtec.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
/**
 * 
 * 类 名: WrapHelper<br/>
 * 描 述: 描述类完成的主要功能<br/>
 * 创 建： 2013年12月19日<br/>
 * 版 本：<br/>
 *
 * 历 史: (版本) 作者 时间 注释 <br/>
 */
public class WrapHelper {
	public static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; ++n) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1) {
				hs.append("0");
				hs.append(stmp);
			} else {
				hs.append(stmp);
			}
		}
		return hs.toString().toUpperCase();
	}

	public static String bin2hex(String bin) {
		char[] digital = "0123456789ABCDEF".toCharArray();
		StringBuffer sb = new StringBuffer("");
		byte[] bs = bin.getBytes();

		for (int i = 0; i < bs.length; ++i) {
			int bit = (bs[i] & 0xF0) >> 4;
			sb.append(digital[bit]);
			bit = bs[i] & 0xF;
			sb.append(digital[bit]);
		}
		return sb.toString();
	}

	public static byte[] hex2byte(String strkey) {
		int keylength = strkey.length() / 2;
		String strValue = "";
		byte[] key = new byte[keylength];
		for (int i = 0; i < keylength; ++i) {
			strValue = strkey.substring(2 * i, 2 * (i + 1));
			key[i] = Integer.valueOf(strValue, 16).byteValue();
		}
		return key;
	}

	public static Object pupuZipHexString2Object(String hexStr) throws IOException, ClassNotFoundException,
			DataFormatException {
		byte[] unzip = hex2byte(hexStr);
		return byte2Object(unzip);
	}

	public static String popuObject2ZipHexString(Object srcObject) throws IOException {
		byte[] ziped = object2Byte(srcObject);
		String tmp = byte2hex(ziped);

		return tmp;
	}

	public static Object byte2Object(byte[] srcByte) throws ClassNotFoundException, IOException {
		ByteArrayInputStream is = new ByteArrayInputStream(srcByte);
		ObjectInputStream ois = new ObjectInputStream(is);
		return ois.readObject();
	}

	public static byte[] object2Byte(Object srcObject) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(srcObject);
		return os.toByteArray();
	}

	public static byte[] compress(byte[] srcByte) {
		byte[] output = new byte[srcByte.length];
		Deflater compresser = new Deflater();
		compresser.setInput(srcByte);
		compresser.finish();
		int compressedDataLength = compresser.deflate(output);
		byte[] result = new byte[compressedDataLength];
		System.arraycopy(output, 0, result, 0, compressedDataLength);
		return result;
	}

	public static byte[] deCompress(byte[] compressedByte) throws DataFormatException {
		Inflater decompresser = new Inflater();
		decompresser.setInput(compressedByte, 0, compressedByte.length);
		byte[] output = new byte[2 * compressedByte.length];
		int resultLength = decompresser.inflate(output);
		decompresser.end();
		byte[] result = new byte[resultLength];
		System.arraycopy(output, 0, result, 0, resultLength);
		return result;
	}

	public static void main(String[] a) {
		String asc = "hello workd";
		byte[] b = asc.getBytes();
	}
}