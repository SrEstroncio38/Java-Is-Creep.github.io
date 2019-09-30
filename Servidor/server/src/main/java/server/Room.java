package server;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.JsonObject;

public class Room {
	String nombre;
	public final int MAXNUMPLAYERS = 4;
	int numPlayers = 0;
	boolean hasStart = false;
	ReentrantLock lock = new ReentrantLock();
	HashMap<WebSocketSession, JugadorConectado> jugadoresEnSala = new HashMap<WebSocketSession, JugadorConectado>();

	ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

	public Room(String nombre) {
		this.nombre = nombre;
		lock.lock();
		numPlayers++;
		lock.unlock();
	}

	/*
	 * public void tick() { lock.lock(); for(JugadorConectado jug :
	 * jugadoresEnSala.values()) {
	 * 
	 * } lock.unlock(); }
	 */

	public void anadirJugador(JugadorConectado jug) {
		lock.lock();
		if (jugadoresEnSala.putIfAbsent(jug.getSession(), jug) != null) {
			numPlayers++;
		}
		;
		if (numPlayers == MAXNUMPLAYERS) {
			hasStart = true;
			Runnable task = () -> {
				lock.lock();
				for (JugadorConectado jug2 : jugadoresEnSala.values()) {

					JsonObject msg = new JsonObject();
					msg.addProperty("event", "tick");
					msg.addProperty("date", new Date().toString());
					try {
						jug2.getSession().sendMessage(new TextMessage(msg.toString()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				lock.unlock();
			};
			executor.scheduleAtFixedRate(task, 1, 1, TimeUnit.SECONDS);
		}
		lock.unlock();
	}

	public void quitarJugador(JugadorConectado jug) {
		lock.lock();
		if (jugadoresEnSala.remove(jug.getSession()) != null) {
			numPlayers--;
		}
		;
		lock.unlock();
	}

}
