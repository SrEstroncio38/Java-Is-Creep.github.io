package server;

import java.util.concurrent.locks.ReentrantLock;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;

public class WebsocketSnailHandler extends TextWebSocketHandler {
	public ReentrantLock lockSession = new ReentrantLock(); //lock que protege la session cuando se mandan mensajes.
	SnailGame game = new SnailGame();
	Room room1 = new Room("sala1");
	 

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		lockSession.lock();
		WebSocketSession newSession  = session;
		lockSession.unlock();
		Gson googleJson = new Gson();
		 Post post = googleJson.fromJson(message.getPayload(), Post.class);
		 
		 System.out.println(post.toString());
		
		switch(post.event) {
		case"DEBUG":
			System.out.println("Mensaje de debug");
			int aux = post.datos.get(0) + post.datos.get(1);
			System.out.println(" el numero es: " + aux);
			break;
		case "CONECTAR":
			JugadorConectado jug = new JugadorConectado(newSession,post.nombreJugador);
			System.out.println(" anadiendo jugador " + jug.getNombre());
			game.conectarJugador(jug);
			room1.anadirJugador(jug);
			break;
		default:
			
		}
		
		
		
		// prueba mensajes
		/*
		System.out.println("Mensaje recibido " + message.getPayload());
		String msg = "Mensaje recibido por el server: " + message.getPayload();
		session.sendMessage(new TextMessage(msg));
		*/

		// Create new JSON Object y prueba JSONS
		/*
		 * JsonObject person = new JsonObject(); person.addProperty("firstName",
		 * "Sergey"); person.addProperty("lastName", "Kargopolov");
		 * System.out.println(person.toString()); session.sendMessage(new
		 * TextMessage(person.toString()));
		 */
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		lockSession.lock();
		System.out.println("Alguien se ha conectado" + session);
		session.sendMessage(new TextMessage("Mensaje del servidor"));
		lockSession.unlock();
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("Adios bb");
	}

}
