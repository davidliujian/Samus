package com.sdu.samus.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;

public class Base64Util {
	public static boolean base64StringToImage(String base64String,String toImagePath,String imageType) {
		try {
			BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			byte[] bytes1 = decoder.decodeBuffer(base64String);

			ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
			RenderedImage bi1 = ImageIO.read(bais);
			File w2 = new File(toImagePath);// 可以是jpg,png,gif格式
			if(!w2.exists()){
				File fileParent = w2.getParentFile();
				if(!fileParent.exists()){
					fileParent.mkdirs();
				}
				w2.createNewFile();
			}
			bais.close();
			return ImageIO.write(bi1, imageType, w2);// 不管输出什么格式图片，此处不需改动
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @Description: 根据图片地址转换为base64编码字符串
	 * @Author:
	 * @CreateTime:
	 * @return
	 */
	public static String ImageToBase64String(String imgFile) {
		byte[] data = null;
		try {
			BufferedImage image = ImageIO.read(new File(imgFile));
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ImageIO.write(image,"jpg",byteOut);
			data = byteOut.toByteArray();
			byteOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
//
//		InputStream inputStream = null;
//		byte[] data = null;
//		try {
//			inputStream = new FileInputStream(imgFile);
//			data = new byte[inputStream.available()];
//			inputStream.read(data);
//			inputStream.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		// 加密
		BASE64Encoder encoder = new BASE64Encoder();
		String base64 = encoder.encode(data).trim();//转换成base64串
		base64 = base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
		return "data:image/jpeg;base64,"+base64;
	}
}
