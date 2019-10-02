package server;

import java.util.concurrent.locks.ReentrantLock;

public class SnailInGame {
	SquareCollider collider;
	final int colliderOfsetX = 50;
	final int colliderOfsetY = 50;
	float posX = 20;
	float posY = 0;
	int stamina;
	// valores iniciales
	public final int MAXSTAMINA = 100;
	public final int MAXVELOCIDADX = 10;
	public final int MAXVELOCIDADY = 10;
	public final float ACELERATIONX = 1f;
	public final float ACALERATIONY = 1f;
	public final float GRAVITY = 9.8f;
	public final float BREAKFORCE = 3;

	//Valores maximos que pueden ser cambiado con power ups momentameamente
	public int maxStamina;
	public int maxSpeed;
	public float acelerationX;
	public float acelerationY;
	public float breakForce;
	
	public boolean isOnFloor;
	public boolean isOnWall;
	
	
	
	public float speedX;
	public float speedY;
	LastMovement lastMovement;
	GenericPowerUp powerUp;
	
	
	//interaccion con el escenario 
	
	ReentrantLock lastMovementLock = new ReentrantLock(); // se puede tocar tanto en el manejador de mensajes como en el loop de la sala
	


	public SnailInGame() {
		speedX =0;
		maxStamina = MAXSTAMINA;
		maxSpeed = MAXVELOCIDADX;
		acelerationX = ACELERATIONX;
		acelerationY = ACALERATIONY;
		breakForce = BREAKFORCE;

		stamina = MAXSTAMINA;
		collider = new SquareCollider(colliderOfsetX, colliderOfsetY);
	}

	public void restoreValues() {
		maxStamina = MAXSTAMINA;
		maxSpeed = MAXVELOCIDADX;
		acelerationX = ACELERATIONX;
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
		}
		
		if(isOnWall) {
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
			
		if(speedX > maxSpeed) {
			speedX = maxSpeed;
		} else if(speedX < 0) {
			speedX = 0;
		}
		posX+= speedX;
	}
	
	public void updateMovement(boolean isStoping ,boolean useObject) {
		lastMovementLock.lock();
		lastMovement = new LastMovement(isStoping,useObject);
		lastMovementLock.unlock();
	}

}
