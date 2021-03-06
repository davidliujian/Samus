package com.sdu.samus.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;

public class StringUtil {
	private static String string = "abcdefghijklmnopqrstuvwxyz";
	private static String integer = "0123456789";

	public static boolean isEmpty(String data) {
		if (data == null || data.equals("")) {
			return true;
		}
		return false;
	}

	public static ArrayList<String> split(String source ,String mark){
		ArrayList<String> list = new ArrayList<String>();
		if(!isEmpty(source)){
			int markLength = mark.length();
			StringBuffer sb = new StringBuffer(source);
			while(sb.indexOf(mark)!=-1){	//如果还有这个分割标志
				list.add(sb.substring(0,sb.indexOf(mark)));
				sb = sb.delete(0,sb.indexOf(mark)+markLength);
			}
			list.add(sb.toString());
		}
		return list;
	}

	public static String generateRandom(int length){
		StringBuffer sb = new StringBuffer();
		int len = string.length();
		for (int i = 0; i < length; i++) {
			sb.append(string.charAt(getRandom(len-1)));
		}
		return sb.toString();
	}

	public static Integer generateIntRandom(int length){
		StringBuffer sb = new StringBuffer();
		int len = integer.length();
		for (int i = 0; i < length; i++) {
			sb.append(integer.charAt(getRandom(len-1)));
		}
		return Integer.parseInt(sb.toString());
	}

	private static int getRandom(int count){
   		return (int) Math.round(Math.random() * (count));
	}

}
