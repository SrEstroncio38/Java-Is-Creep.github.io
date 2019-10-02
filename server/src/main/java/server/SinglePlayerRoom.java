package server;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.web.socket.TextMessage;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import server.MapObject.type;

public class SinglePlayerRoom {
	String name;
	PlayerConected player;
	ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	Map map  = new Map(2000); // anchura de la camara sin definir aun
	final int MAPSIZE = 5;

	public SinglePlayerRoom(String name, PlayerConected player) {
		this.name = name;
		this.player = player;
		createMap();
		sendMap();
		
		tick();
		
		
	}
	
	public void sendMap() {

		Gson gson = new Gson();
		String array = gson.toJson(map.map);
		System.out.println(array);
		JsonObject person = new JsonObject();
		person.addProperty("event","DRAWMAP");
		person.addProperty("mapObjects", array);
		System.out.println(person.toString()); 
		try {
			player.getSession().sendMessage(new TextMessage(person.toString()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createMap() {
		map.addMapObject(new MapGround(300,100,0,0,type.GROUND));
		map.addMapObject(new MapGround(200,100,300,0,type.GROUND));
		map.addMapObject(new MapWall(100,400,500,0,type.WALL));
		map.addMapObject(new MapGround(300,100,600,350,type.GROUND));
	}
	
	public void checkCollisions() {
		boolean groundCollision = false;
		boolean wallCollision = false;
		
		for(MapObject object : map.map) {
			if(object.collider.hayColision(player)) {
				object.collisionInfo();
				switch(object.myTipe) {
				case GROUND:
					groundCollision = true;
					break;
				case WALL:
					wallCollision = true;
					break;
					
				default:
					System.out.println("COLISION RARA");
				}

			}
		}
		player.mySnail.isOnFloor = groundCollision;
		player.mySnail.isOnWall = wallCollision;
		System.out.println(" collision con suelo es: " + groundCollision);
		System.out.println(" collision con pared es: " + wallCollision);
	}
	
	
	public void tick() {
		Runnable task = () -> {
			
			checkCollisions();

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
		executor.scheduleAtFixedRate(task, 1000, 33, TimeUnit.MILLISECONDS);
	}
	
}


