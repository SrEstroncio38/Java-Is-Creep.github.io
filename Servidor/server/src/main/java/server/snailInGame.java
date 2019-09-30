package server;

public class snailInGame {
	SquareCollider collider;
	final int colliderOfsetX = 50;
	final int colliderOfsetY = 50;
	int posX;
	int posY;
	int stamina;
	//Valores maximos que pueden ser cambiado con power ups momentameamente
	public final int MAXSTAMINA = 100;
	public final int MAXVELOCIDAD = 5;
	public final float ACELERACION = 0.5f;
	
	
	//
	public int maxStamina;
	public int maxVelocidad;
	public float aceleracion;
	LastMovement lastMovement;
	GenericPowerUp powerUp;
	
	
	public void snailGame() {
		posX = 0;
		posY = 0;
		maxStamina = MAXSTAMINA;
		maxVelocidad = MAXVELOCIDAD;
		aceleracion = ACELERACION;
		
		stamina = MAXSTAMINA;
		collider = new SquareCollider(colliderOfsetX,colliderOfsetY);
	}
	
	public void restoreValues() {
		maxStamina = MAXSTAMINA;
		maxVelocidad = MAXVELOCIDAD;
		aceleracion = ACELERACION;
	}
	
	public void cosumirPowerUp() {
		if(powerUp != null) {
			powerUp.consumirPowerUp();
			powerUp = null;
		}
	}
	
	
	
}
