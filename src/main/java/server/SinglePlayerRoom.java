package server;

import java.io.IOException;
import java.util.ArrayList;
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
		
		ArrayList <Integer> posX = new ArrayList<>();
		ArrayList <Integer> posY = new ArrayList<>();
		ArrayList <Integer> height = new ArrayList<>();
		ArrayList <Integer> width = new ArrayList<>();
		ArrayList <type> myType = new ArrayList<>();

		for(MapObject obj : map.map){
			posX.add(obj.posX);
			posY.add(obj.posY);
			height.add(obj.height);
			width.add(obj.width);
			myType.add(obj.myTipe);
		}


		Gson gson = new Gson();
		String posXArray = gson.toJson(posX);
		String posYArray = gson.toJson(posY);
		String heightArray = gson.toJson(height);
		String widthArray = gson.toJson(width);
		String myTypeArray = gson.toJson(myType);

		
		JsonObject person = new JsonObject();
		person.addProperty("event","DRAWMAP");
		person.addProperty("posX", posXArray);
		person.addProperty("posY", posYArray);
		person.addProperty("height", heightArray);
		person.addProperty("width", widthArray);
		person.addProperty("myType", myTypeArray);
		System.out.println(person.toString()); 
		try {
			player.getSession().sendMessage(new TextMessage(person.toString()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createMap() {
		// MAPA 1
		/*
		map.addMapObject(new MapGround(300,20,0,0,type.GROUND));
		map.addMapObject(new MapGround(200,20,300,0,type.GROUND));
		map.addMapObject(new MapWall(20,400,500,0,type.WALL));
		map.addMapObject(new MapGround(100,20,500,400,type.GROUND));
		map.addMapObject(new MapGround(300,20,800,400,type.GROUND));
		map.addMapObject(new MapGround(300,20,600,200,type.GROUND));
		map.addMapObject(new MapWall(20,200,800,200,type.WALL));
		*/
		
		//Mapa2
		map.addMapObject(new MapGround(100,20,0,0,type.GROUND));
		map.addMapObject(new MapWall(20,400,100,0,type.WALL));
		map.addMapObject(new MapGround(100,20,100,400,type.GROUND));
		map.addMapObject(new MapSlope(300,Math.toRadians(-30),200,400,type.SLOPE)); // seria de 300 por 173
		map.addMapObject(new MapGround(300,20,480,220,type.GROUND));
		//map.addMapObject(new MapWall(20,200,900,193,type.WALL));

		
		

	}
	
	public void checkCollisions() {
		boolean groundCollision = false;
		boolean wallCollision = false;
		boolean slopeCollision = false;
		double slopeRadians = 0;
		
		for(MapObject object : map.map) {
			if(object.collider.hayColision(player)) {
				switch(object.myTipe) {
				case GROUND:
					groundCollision = true;
					break;
				case WALL:
					wallCollision = true;
					break;
				case SLOPE:
					MapSlope auxSlope = (MapSlope) object;
					slopeCollision=true;
					slopeRadians = auxSlope.radians;
					break;
				default:
					System.out.println("COLISION RARA");
				}

			}
		}
		player.mySnail.isOnFloor = groundCollision;
		player.mySnail.isOnWall = wallCollision;
		player.mySnail.isOnSlope = slopeCollision;
		player.mySnail.slopeRadians = slopeRadians;
		
		
		System.out.println(" collision con suelo es: " + groundCollision);
		System.out.println(" collision con pared es: " + wallCollision);
		System.out.println(" collision con slope es: " + slopeCollision);
		
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


