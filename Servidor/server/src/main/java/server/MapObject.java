package server;

public class MapObject { // clase principal de la que heredaran todos los objetos del juego
	int width;
	int height;
	int posX;
	int posY;
	SquareCollider collider;
	enum type {
		GROUND,WALL,OBSTACLE,POWERUP,OBSTACLEPOINT
	}
	
	type myTipe;

	public MapObject(int width, int height, int posX, int posY, type myTipe,int colliderOfsetX,int colliderOfsetY) {
		collider = new SquareCollider(colliderOfsetX,colliderOfsetY);
		this.width = width;
		this.height = height;
		this.posX = posX;
		this.posY = posY;
		this.myTipe = myTipe;
	}
}
