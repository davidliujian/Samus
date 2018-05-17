package com.sdu.samus.config;

import com.sdu.samus.handler.MyWebSocketHandler;
import com.sdu.samus.interceptor.MyHandShakeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	@Autowired
	MyWebSocketHandler webSocketHandler;
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
		//前台 可以使用websocket环境  ,添加websocket处理器，添加握手拦截器
		webSocketHandlerRegistry.addHandler(webSocketHandler,"/ws").addInterceptors(new MyHandShakeInterceptor()).setAllowedOrigins("*");
		//前台 不可以使用websocket环境，则使用sockjs进行模拟连接
		webSocketHandlerRegistry.addHandler(webSocketHandler, "/ws/sockjs").addInterceptors(new MyHandShakeInterceptor()).withSockJS();
	}
}
