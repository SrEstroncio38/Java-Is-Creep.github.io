package server;

public class snailInGame {
	SquareCollider collider;
	final int colliderOfsetX = 50;
	final int colliderOfsetY = 50;
	int posX;
	int posY;
	int stamina;
	public final int MaxStamina = 100;
	LastMovement lastMovement;
	
	
	public void snailGame() {
		posX = 0;
		posY = 0;
		stamina = MaxStamina;
		collider = new SquareCollider(colliderOfsetX,colliderOfsetY);
	}
	
	
	
}
