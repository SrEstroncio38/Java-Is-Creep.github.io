package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@SpringBootApplication
@EnableWebSocket
public class Application implements WebSocketConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
		
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(serverHandler(), "/snail").setAllowedOrigins("*");
	}

	@Bean
	public WebsocketSnailHandler serverHandler() {
		return new WebsocketSnailHandler();
	}

	
}
