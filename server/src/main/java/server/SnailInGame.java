package server;

import java.util.concurrent.locks.ReentrantLock;

public class SnailInGame {
	SquareCollider collider;
	final int colliderOfsetX = 50;
	final int colliderOfsetY = 50;

	// valores iniciales
	public final int MAXSTAMINA = 300; //151 de base son 5 seg, 300 dura 10 segundos, se resta 1 de estamina por segundo
	public final int MAXVELOCIDADX = 3;
	public final int MAXVELOCIDADY = 3;
	public final float ACELERATIONX = 0.05f;
	public final float ACALERATIONY = 0.05f;
	public final float GRAVITY = 0.3f;
	public final float BREAKFORCE = 0.1f;
	public final float STAMINALOSE = 1; // tarda 5 segundos en perder la stamina
	public final float STAMINANORMALRECOVER = 2.5f; // tarda 2 segundos en recargar la stamina
	public final float STAMINARUNOUTRECOVER = 1.5f; // tarda 3.33 segundos en recargar la stamina
	public final float MAXGRAVITYSPEED = -4;
	public final float MASS = 2;
	public final float SPEEDXLOSE = 1.02f;
	// OPERACION 151 / (1.5f * 30 fps)

	// Valores maximos que pueden ser cambiado con power ups momentameamente
	public int maxStamina;
	public int maxSpeedX;
	public int maxSpeedY;
	public float acelerationX;
	public float acelerationY;
	public float breakForce;
	public boolean runOutStamina;

	public boolean isOnFloor = true;
	public boolean isOnWall = false;

	public float speedX;
	public float speedY;
	public float stamina;
	public float posX = 20;
	public float posY = 20;
	LastMovement lastMovement;
	GenericPowerUp powerUp;

	// interaccion con el escenario

	ReentrantLock lastMovementLock = new ReentrantLock(); // se puede tocar tanto en el manejador de mensajes como en el
															// loop de la sala

	public SnailInGame() {
		speedX = 0;
		speedY = 0;
		runOutStamina = false;
		maxStamina = MAXSTAMINA;
		maxSpeedX = MAXVELOCIDADX;
		maxSpeedY = MAXVELOCIDADY;
		acelerationX = ACELERATIONX;
		acelerationY = ACALERATIONY;
		breakForce = BREAKFORCE;

		stamina = MAXSTAMINA;
		collider = new SquareCollider(colliderOfsetX, colliderOfsetY, posX, posY);
	}

	// Resetea los valores en caso de que hallas consumido algun power up
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

	// Actualizacion del movimiento y variables del caracol
	public void updateSnail() {
		lastMovementLock.lock();
		boolean isStopping = lastMovement.isStopping;
		boolean useObject = lastMovement.useObject;
		lastMovementLock.unlock();

		System.out.println(stamina);
		System.out.println(runOutStamina);
		// si tienes stamina haces funcionamiento normal
		if (!runOutStamina) {
			// comprobamos si aceleramos o no para perder o quitar stamina
			if (!isStopping) {
				stamina -= STAMINALOSE;
				if(stamina <= 0){
					runOutStamina = true;
				}
			} else {
				stamina += STAMINANORMALRECOVER;
			}


			// comprobamos si esta en el suelo para que avance
			if (isOnFloor) {
				System.out.println("estoy en suelo");
				if (!isStopping) {
					speedX += acelerationX;
				} else {
					speedX -= breakForce;
				}
				if(speedY <=0){
					speedY = 0;
				}

			} else {
				if (!isOnWall) {
					speedY -= GRAVITY;
				}
			}

			// si esta en la pared sube
			if (isOnWall) {
				speedX = 0;
				if (!isStopping) {
					speedY += acelerationY;
				} else {
					if (!isOnFloor) {

						speedY -= GRAVITY;

					} else {
						speedY = 0;
					}

				}
			}

			// si esta en el aire, ponemos la vel X al maximo para saltar las paredes
			// (PROVISIONAL)
			
			if ((!isOnFloor) && (!isOnWall)) {
				speedX = speedX/SPEEDXLOSE;
			}
		} else { // si te quedas sin estamina te quedas parado hasta que te recuperes, 
			stamina += STAMINARUNOUTRECOVER;
			if ((!isOnFloor) && (!isOnWall)) {
				speedX = speedX/SPEEDXLOSE;
			}
			if(!isOnFloor){
				speedY -= GRAVITY; 
			} else {
				speedY = 0;
				speedX = 0;
			}
			if(stamina >= maxStamina){
				runOutStamina = false;
				stamina = maxStamina;
			}
		}
		// Ajustamos las velocidades
		adjustSpeed();

		// actualizamos posiciones
		posX += speedX;
		posY += speedY;
		collider.recalculatePosition(posX, posY);
	}

	public void adjustSpeed(){
		if (speedX > maxSpeedX) {
			speedX = maxSpeedX;
		} else if (speedX < 0) {
			speedX = 0;
		}
		if (speedY > maxSpeedY) {
			speedY = maxSpeedY;
		} else if(speedY < MAXGRAVITYSPEED){
			speedY = MAXGRAVITYSPEED;
		}
	}

	// Cambia el movement anterior por la siguiente actualizacion
	public void updateMovement(boolean isStoping, boolean useObject) {
		lastMovementLock.lock();
		lastMovement = new LastMovement(isStoping, useObject);
		lastMovementLock.unlock();
	}

}
