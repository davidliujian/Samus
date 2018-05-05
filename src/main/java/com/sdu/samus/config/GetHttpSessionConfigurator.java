package com.sdu.samus.config;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
/*
 * 获取HttpSession
 *
 */
public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {
	@Override
	public void modifyHandshake(ServerEndpointConfig sec,
								HandshakeRequest request, HandshakeResponse response) {
		// TODO Auto-generated method stub
		HttpSession httpSession=(HttpSession) request.getHttpSession();
		sec.getUserProperties().put(HttpSession.class.getName(),httpSession);
	}
}
