package server;

public class ObstaclePoint {
	MapObstacle obstacle;
	int posX;
	int posY;
	int probabilityNeed; // probabilidad de que algo aparezca del 0 al 100
	boolean exits = false;
	int actualProbability;
	
	public ObstaclePoint(MapObstacle obstacle, int posX, int posY) {
		this.obstacle = obstacle;
		this.posX = posX;
		this.posY = posY;
		actualProbability = (int) (Math.random() *100);
		if(actualProbability > probabilityNeed) {
			exits = true;
		}
	}
	
	
	
	
	
}
