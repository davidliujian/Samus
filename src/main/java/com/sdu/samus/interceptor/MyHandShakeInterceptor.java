package com.sdu.samus.interceptor;

import com.sdu.samus.Constants;
import com.sdu.samus.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class MyHandShakeInterceptor extends  HttpSessionHandshakeInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(MyHandShakeInterceptor.class);

	@Override
	public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
//		System.out.println("Websocket:用户[ID:" + ((UserInfo)SessionUtil.getSession(Constants.USER)).getUserid() + "]已经建立连接");
		logger.info(serverHttpRequest.getRemoteAddress()+"");
		if (serverHttpRequest instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
			logger.info("-----------------MyHandShakeInterceptor--------------------");

			logger.info("MyHandShakeInterceptor --- [request.getContextPath()]     :"+servletRequest.getServletRequest().getContextPath());

			HttpSession session = servletRequest.getServletRequest().getSession(false);

			logger.info("MyHandShakeInterceptor --- [session.getId()]     :"+session.getId());

			// 标记用户
			UserInfo user =(UserInfo) session.getAttribute(Constants.USER);//(UserInfo)  SessionUtil.getSession(Constants.USER); //

//			/* sessionMap.put(session.getId(), session); */
//			UserInfo user =(UserInfo) session.getAttribute(Constants.USER);

			if(user!=null){
				map.put("uid", user.getUserid());//为服务器创建WebSocketSession做准备
				System.out.println("用户id："+user.getUserid()+" 被加入");
			}else{
				System.out.println("user为空");
				return false;
			}
		}
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
		logger.info("-----------------afterHandshake--------------------");
	}
}
