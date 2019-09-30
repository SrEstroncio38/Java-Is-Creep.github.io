package server;

public class SnailInGame {
	SquareCollider collider;
	final int colliderOfsetX = 50;
	final int colliderOfsetY = 50;
	float posX;
	float posY;
	int stamina;
	// Valores maximos que pueden ser cambiado con power ups momentameamente
	public final int MAXSTAMINA = 100;
	public final int MAXVELOCIDAD = 5;
	public final float ACELERACION = 0.5f;

	//
	public int maxStamina;
	public int maxVelocidad;
	public float aceleracion;
	LastMovement lastMovement;
	GenericPowerUp powerUp;

	public SnailInGame() {
		posX = 0;
		posY = 0;
		maxStamina = MAXSTAMINA;
		maxVelocidad = MAXVELOCIDAD;
		aceleracion = ACELERACION;

		stamina = MAXSTAMINA;
		collider = new SquareCollider(colliderOfsetX, colliderOfsetY);
	}

	public void restoreValues() {
		maxStamina = MAXSTAMINA;
		maxVelocidad = MAXVELOCIDAD;
		aceleracion = ACELERACION;
	}

	public void usePowerUp() {
		if (powerUp != null) {
			powerUp.consumirPowerUp();
			powerUp = null;
		}
	}

	public void updateSnail() {
		posX+= ACELERACION;
	}

}
