package server;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.WebSocketSession;

public class SnailGame {
	//hay que hacer un mapa 
	ConcurrentHashMap<WebSocketSession,JugadorConectado> jugadoresConectados = new ConcurrentHashMap<WebSocketSession, JugadorConectado>();
	
	public void conectarJugador(JugadorConectado jugador) {
		jugadoresConectados.putIfAbsent(jugador.getSession(), jugador);
	}
	
	public JugadorConectado bucarJugadorConectado(WebSocketSession session) {
		return jugadoresConectados.get(session);
	}

}
