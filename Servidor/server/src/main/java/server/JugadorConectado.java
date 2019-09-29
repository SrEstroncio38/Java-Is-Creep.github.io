package server;

import org.springframework.web.socket.WebSocketSession;

public class JugadorConectado {
	private WebSocketSession session;
	private String nombre;
	public snailInGame mySnail;
	public JugadorConectado(WebSocketSession session, String nombre) {
		this.session = session;
		this.nombre = nombre;
	}
	public WebSocketSession getSession() {
		return session;
	}
	public void setSession(WebSocketSession session) {
		this.session = session;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
