package server;

public class MapObject { // clase principal de la que heredaran todos los objetos del juego
	int width;
	int height;
	int posX;
	int posY;
	SquareCollider collider;
	enum type {
		GROUND,WALL,OBSTACLE,POWERUP
	}
	
	type myTipe;
}
