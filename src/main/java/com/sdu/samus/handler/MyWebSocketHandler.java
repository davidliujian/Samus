package com.sdu.samus.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdu.samus.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyWebSocketHandler implements WebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger(MyWebSocketHandler.class);

	public static final Map<String,WebSocketSession> userSocketSessionMap;
	static{
		userSocketSessionMap = new HashMap<String,WebSocketSession>();
	}

	//握手实现连接后
	@Override
	public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
		logger.info("-----------------afterConnectionEstablished--------------------");
		String uid = (String) webSocketSession.getAttributes().get("uid");
		if(userSocketSessionMap.get(uid) == null){
			userSocketSessionMap.put(uid , webSocketSession);
		}
	}

	//发送信息前的处理
	@Override
	public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
		logger.info("-----------------handleMessage--------------------");
		if(webSocketMessage.getPayloadLength() == 0){
			return;
		}
		logger.info("[服务端接收信息：]"+webSocketMessage.getPayload().toString());
		//得到Socket信道中的数据并转化为Message对象
		Message msg = new ObjectMapper().readValue(webSocketMessage.getPayload().toString(),Message.class);

		Timestamp now =new Timestamp(System.currentTimeMillis());
		msg.setMessageDate(now);

		//将信息保存至数据库

		//发送Socket信息
		this.sendMessageToUser(msg.getToId(),new TextMessage(new ObjectMapper().writeValueAsString(msg)));
	}

	@Override
	public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

	}

	@Override
	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
		System.out.println("WebSocket:"+webSocketSession.getAttributes().get("uid")+"close connection");
		userSocketSessionMap.remove(webSocketSession.getAttributes().get("uid"));
//		Iterator<Map.Entry<String,WebSocketSession>> iterator = userSocketSessionMap.entrySet().iterator();
//		while(iterator.hasNext()){
//			Map.Entry<String,WebSocketSession> entry = iterator.next();
//			if(entry.getValue().getAttributes().get("uid")==webSocketSession.getAttributes().get("uid")){
//				userSocketSessionMap.remove(webSocketSession.getAttributes().get("uid"));
//				System.out.println("WebSocket in staticMap:" + webSocketSession.getAttributes().get("uid") + "removed");
//			}
//		}
		System.out.println("WebSocket in staticMap:" + webSocketSession.getAttributes().get("uid") + "removed");
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	//发送信息的实现
	public void sendMessageToUser(String uid, TextMessage message) throws IOException {
		WebSocketSession session = userSocketSessionMap.get(uid);
		if (session != null && session.isOpen()) {
			session.sendMessage(message);
		}else{
			logger.info("用户不在线！！");
		}
	}
}
