package server;

import java.util.concurrent.locks.ReentrantLock;

public class SnailInGame {
	SquareCollider collider;
	final int colliderOfsetX = 50;
	final int colliderOfsetY = 50;
	float posX = 20;
	float posY = 20;
	int stamina;
	// valores iniciales
	public final int MAXSTAMINA = 100;
	public final int MAXVELOCIDADX = 3;
	public final int MAXVELOCIDADY = 3;
	public final float ACELERATIONX = 0.05f;
	public final float ACALERATIONY = 0.05f;
	public final float GRAVITY = 3f;
	public final float BREAKFORCE = 0.1f;

	//Valores maximos que pueden ser cambiado con power ups momentameamente
	public int maxStamina;
	public int maxSpeedX;
	public int maxSpeedY;
	public float acelerationX;
	public float acelerationY;
	public float breakForce;
	
	public boolean isOnFloor = true;
	public boolean isOnWall = false;
	
	
	
	public float speedX;
	public float speedY;
	LastMovement lastMovement;
	GenericPowerUp powerUp;
	
	
	//interaccion con el escenario 
	
	ReentrantLock lastMovementLock = new ReentrantLock(); // se puede tocar tanto en el manejador de mensajes como en el loop de la sala
	


	public SnailInGame() {
		speedX =0;
		speedY =0;
		maxStamina = MAXSTAMINA;
		maxSpeedX = MAXVELOCIDADX;
		maxSpeedY = MAXVELOCIDADY;
		acelerationX = ACELERATIONX;
		acelerationY = ACALERATIONY;
		breakForce = BREAKFORCE;

		stamina = MAXSTAMINA;
		collider = new SquareCollider(colliderOfsetX, colliderOfsetY,posX,posY);
	}

	public void restoreValues() {
		maxStamina = MAXSTAMINA;
		maxSpeedX = MAXVELOCIDADX;
		acelerationX = ACELERATIONX;
		acelerationY = ACALERATIONY;
		maxSpeedY = MAXVELOCIDADY;
	}

	public void usePowerUp() {
		if (powerUp != null) {
			powerUp.consumirPowerUp();
			powerUp = null;
		}
	}

	public void updateSnail() {
		
		
		if(isOnFloor) {
			lastMovementLock.lock();
			if(!lastMovement.isStopping) {
				speedX +=acelerationX;
			} else {
				speedX -= breakForce;
			}
			lastMovementLock.unlock();
		} else {
			if(!isOnWall) {
				speedY -= GRAVITY;
			}
		}
		
		if(isOnWall) {
			speedX = 0;
			lastMovementLock.lock();
			if(!lastMovement.isStopping) {
				speedY +=acelerationY;
			} else {
				if(!isOnFloor) {
					
					speedY -= GRAVITY;
					
				} else {
					speedY =0;
				}
				
			}
			lastMovementLock.unlock();
		}
			
		if(speedX > maxSpeedX) {
			speedX = maxSpeedX;
		} else if(speedX < 0) {
			speedX = 0;
		}
		if(speedY > maxSpeedY) {
			speedY = maxSpeedY;
		}
		posX+= speedX;
		posY+= speedY;
		collider.recalculatePosition(posX, posY);
	}
	
	public void updateMovement(boolean isStoping ,boolean useObject) {
		lastMovementLock.lock();
		lastMovement = new LastMovement(isStoping,useObject);
		lastMovementLock.unlock();
	}

}
