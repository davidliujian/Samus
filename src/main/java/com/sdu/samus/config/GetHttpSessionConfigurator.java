package com.sdu.samus.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
/*
 * 获取HttpSession
 *
 */
public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {
	private static final Logger logger = LoggerFactory.getLogger(GetHttpSessionConfigurator.class);
	@Override
	public void modifyHandshake(ServerEndpointConfig sec,
								HandshakeRequest request, HandshakeResponse response) {
		// TODO Auto-generated method stub
		logger.info("----------------------进入了GetHttpSessionConfigurator---------------------------");
		HttpSession httpSession=(HttpSession) request.getHttpSession();
		logger.info("GetHttpSessionConfigurator里面的session:"+httpSession.getId());
		sec.getUserProperties().put(HttpSession.class.getName(),httpSession);
	}
}
