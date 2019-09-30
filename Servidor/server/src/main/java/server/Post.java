package server;

import java.util.ArrayList;

public class Post { // en caso de que no coincidan
	String event;
	String message;
	String remitente;
	String nombreJugador;
	ArrayList <Integer> datos;
	public Post(String event, String message, String remitente, String nombreJugador, ArrayList<Integer> datos) {
		super();
		this.event = event;
		this.message = message;
		this.remitente = remitente;
		this.nombreJugador = nombreJugador;
		this.datos = datos;
	}
	@Override
	public String toString() {
		return "Post [event=" + event + ", message=" + message + ", remitente=" + remitente + ", nombreJugador="
				+ nombreJugador + ", datos=" + datos + "]";
	}



}
