package com.devglan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Bean
	public SessionStore sessionStore() {
		return new SessionStore();
	}

	@Bean
	public SocketHandler socketHandler() {
		return new SocketHandler(sessionStore());
	}

	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(socketHandler(), "/message/{user}").addInterceptors(getInter());;
	}

	private HandshakeInterceptor getInter() {
		return new HandshakeInterceptor() {;
			@Override
			public boolean beforeHandshake(ServerHttpRequest serverHttpRequest,
										   ServerHttpResponse serverHttpResponse,
										   WebSocketHandler webSocketHandler,
										   Map<String, Object> map) throws Exception {
				System.out.println("Request ----------" + serverHttpRequest.getHeaders());

				String path = serverHttpRequest.getURI().getPath();
				System.out.println("PATH  " + path);
				int index = path.indexOf("/message");
				String user = path.substring(index + 9);
				map.put("user", user);
				return true;
			}

			@Override
			public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

			}
		};
	}

}