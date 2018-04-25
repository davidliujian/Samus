package com.sdu.samus.util;

public class StringUtil {
	public static boolean isEmpty(String data) {
		if (data == null || data.equals("")) {
			return true;
		}
		return false;
	}
}
