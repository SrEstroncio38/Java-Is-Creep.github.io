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
	public float velocidad;
	public int maxStamina;
	public int maxVelocidad;
	public float aceleracion;
	LastMovement lastMovement;
	GenericPowerUp powerUp;
	

	@Override
	public String toString() {
		return "SnailInGame [collider=" + collider + ", colliderOfsetX=" + colliderOfsetX + ", colliderOfsetY="
				+ colliderOfsetY + ", posX=" + posX + ", posY=" + posY + ", stamina=" + stamina + ", MAXSTAMINA="
				+ MAXSTAMINA + ", MAXVELOCIDAD=" + MAXVELOCIDAD + ", ACELERACION=" + ACELERACION + ", maxStamina="
				+ maxStamina + ", maxVelocidad=" + maxVelocidad + ", aceleracion=" + aceleracion + ", lastMovement="
				+ lastMovement + ", powerUp=" + powerUp + "]";
	}

	public SnailInGame() {
		posX = 0;
		posY = 0;
		velocidad =0;
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
		velocidad +=ACELERACION;
		if(velocidad > maxVelocidad) {
			velocidad = maxVelocidad;
		}
		posX+= velocidad;
	}

}
