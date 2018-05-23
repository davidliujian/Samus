package com.sdu.samus.handler;

import com.sdu.samus.Constants;
import com.sdu.samus.config.GetHttpSessionConfigurator;
import com.sdu.samus.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/websocket" , configurator = GetHttpSessionConfigurator.class)
@Component
public class MyWebSocket {
	//与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	private HttpSession httpSession;
	private static final Logger logger = LoggerFactory.getLogger(MyWebSocket.class);
	/**
	 * 连接成功*/
	@OnOpen
	public void onOpen(Session session,EndpointConfig config){
		logger.info("----------------------进入了onOpen---------------------------");
		HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		logger.info("httpSession.getId:"+httpSession.getId());
		logger.info("Session.getId:"+session.getId());
		UserInfo user = (UserInfo)httpSession.getAttribute(Constants.USER);
		if(user != null){
			this.session = session;
			this.httpSession = httpSession;
		}else{
			//用户未登陆
			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {

	}

	/**
	 * 收到消息
	 *
	 * @param message
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("来自浏览器的消息:" + message);
//		//群发消息
//		for (MyWebSocket item : webSocketSet) {
//			try {
//				item.sendMessage(message);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
	}

	/**
	 * 发生错误时调用
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}

	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);//同步
		//this.session.getAsyncRemote().sendText(message);//异步
	}




}
