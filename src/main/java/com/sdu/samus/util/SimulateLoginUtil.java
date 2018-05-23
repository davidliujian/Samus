package com.sdu.samus.util;

import com.sdu.samus.enums.ResultCode;
import com.sdu.samus.exception.ParameterException;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模拟用户登录教务系统
 */
public class SimulateLoginUtil {

	private static final Logger logger = LoggerFactory.getLogger(SimulateLoginUtil.class);

	public static String simulate(String userId,String passwd,String url) throws IOException,ParameterException {
		logger.info("------------------模拟登陆教务系统--------------------------");
		ParameterException pe  =new ParameterException();
		if(StringUtil.isEmpty(url)){
			pe.addError(ResultCode.URL_EMPTY);
		}
		if(pe.hasErrors()){
			throw pe;
		}
		passwd = MD5Util.getMD5(passwd);
		// 第一次请求
		Connection con = Jsoup.connect(url);// 获取连接
		con.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");// 配置模拟浏览器
		Response rs = con.execute();// 获取响应
			System.out.println(rs.cookies());
		Document d1 = Jsoup.parse(rs.body());// 转换为Dom树
		List<Element> et = d1.select("#loginDivId");// 获取form表单，可以通过查看页面源码代码得知
		// 获取，cooking和表单属性，下面map存放post时的数据
		Map<String, String> datas = new HashMap<>();
			for (Element e : et.get(0).getAllElements()) {
			if (e.attr("name").equals("j_username")) {
				e.attr("value", userId);// 设置用户名
			}
			if (e.attr("name").equals("j_password")) {
				e.attr("value", passwd); // 设置用户密码
			}
			if (e.attr("name").length() > 0) {// 排除空值表单属性
				datas.put(e.attr("name"), e.attr("value"));
			}
		}

		for(Map.Entry<String,String> entry : datas.entrySet()){
			System.out.println(entry.getKey()+" : "+entry.getValue());
		}

		/**
		 * 第二次请求，post表单数据，以及cookie信息
		 *
		 * **/
		Connection con2 = Jsoup.connect("http://bkjws.sdu.edu.cn/b/ajaxLogin");
		con2.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36");
		// 设置cookie和post上面的map数据
		Response login = con2.ignoreContentType(true).method(Method.POST).data(datas).cookies(rs.cookies()).execute();
		// 打印，登陆成功后的信息
		System.out.println(login.statusCode());
		System.out.println(login.body());
		// 登陆成功后的cookie信息，可以保存到本地，以后登陆时，只需一次登陆即可
		Map<String, String> map = login.cookies();
		for (String s : map.keySet()) {
			System.out.println(s + "      " + map.get(s));
		}
		return login.body();
}
	public static void main(String args[])throws Exception{
		String passwd = MD5Util.getMD5("222517");
		SimulateLoginUtil.simulate("201400301193",passwd,"http://bkjws.sdu.edu.cn/");
	}
}
