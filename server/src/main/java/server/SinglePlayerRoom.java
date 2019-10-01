package server;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.web.socket.TextMessage;

import com.google.gson.JsonObject;

public class SinglePlayerRoom {
	String name;
	PlayerConected player;
	ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	Map mapa  = new Map(300); // anchura d ela camara sin definir aun

	public SinglePlayerRoom(String name, PlayerConected player) {
		this.name = name;
		this.player = player;
		tick();
		
		
	}
	
	
	public void tick() {
		Runnable task = () -> {

			player.mySnail.updateSnail();
			JsonObject msg = new JsonObject();
			msg.addProperty("event", "TICK");
			msg.addProperty("posX", player.mySnail.posX);
			msg.addProperty("posY", player.mySnail.posY);
			
			try {
				player.getSession().sendMessage(new TextMessage(msg.toString()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		executor.scheduleAtFixedRate(task, 1, 1, TimeUnit.SECONDS);
	}
	
}


