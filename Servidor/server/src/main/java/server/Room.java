package server;


import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.web.socket.WebSocketSession;

public class Room {
	String nombre;
	public final int MAXNUMPLAYERS = 4;
	int numPlayers = 0;
	boolean hasStart = false;
	ReentrantLock lock = new ReentrantLock();
	HashMap<WebSocketSession,JugadorConectado> jugadoresEnSala = new HashMap<WebSocketSession, JugadorConectado>();
	
	
	public Room( String nombre,JugadorConectado jug) {
		this.nombre = nombre;
		lock.lock();
		jugadoresEnSala.putIfAbsent(jug.getSession(), jug);
		numPlayers++;
		lock.unlock();
		
		
	}
	
	public void anadirJugador(JugadorConectado jug) {
		lock.lock();
		if(jugadoresEnSala.putIfAbsent(jug.getSession(), jug)!= null) {
			numPlayers++;
		};
		if(numPlayers == MAXNUMPLAYERS) {
			hasStart = true;
		}
		lock.unlock();
	}
	
	public void quitarJugador(JugadorConectado jug) {
		lock.lock();
		if(jugadoresEnSala.remove(jug.getSession())!= null) {
			numPlayers--;
		};
		lock.unlock();
	}
	
}
