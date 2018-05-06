package com.sdu.samus.util;

import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

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
			return ImageIO.write(bi1, imageType, w2);// 不管输出什么格式图片，此处不需改动
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
