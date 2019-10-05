package server;

import java.util.concurrent.locks.ReentrantLock;

public class SnailInGame {
	SquareCollider collider;
	final int colliderOfsetX = 50;
	final int colliderOfsetY = 50;

	// valores iniciales
	public final int MAXSTAMINA = 300; // 151 de base son 5 seg, 300 dura 10 segundos, se resta 1 de estamina por
										// segundo
	public final int MAXVELOCIDADX = 3;
	public final int MAXVELOCIDADY = 3;
	public final float ACELERATIONX = 0.05f; // deberian ser la misma aceleracion.
	public final float ACALERATIONY = 0.05f;
	public final float GRAVITY = 0.3f;
	public final float BREAKFORCE = 0.1f;
	public final float STAMINALOSE = 1; // tarda 5 segundos en perder la stamina
	public final float STAMINANORMALRECOVER = 2.5f; // tarda 2 segundos en recargar la stamina
	public final float STAMINARUNOUTRECOVER = 1.5f; // tarda 3.33 segundos en recargar la stamina
	public final float MAXGRAVITYSPEED = -20;
	public final float MASS = 1;
	public final float SPEEDXLOSE = 1.02f;
	// OPERACION 151 / (1.5f * 30 fps)

	// Valores maximos que pueden ser cambiado con power ups momentameamente
	public int maxStamina;
	public float maxSpeedX;
	public float maxSpeedY;
	public float maxSpeedInSlopeX;
	public float maxSpeedInSlopeY;
	public float acelerationX;
	public float acelerationY;
	public float breakForce;
	public boolean runOutStamina;

	public boolean isOnFloor = true;
	public boolean isOnWall = false;
	public boolean isOnSlope = false;
	public double slopeRadians = 0;

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

		// si tienes stamina haces funcionamiento normal
		if (!runOutStamina) {
			// comprobamos si aceleramos o no para perder o quitar stamina
			if (!isStopping) {

				if ((!isOnFloor) && (!isOnWall) && (!isOnSlope)) { // si estas cayendo no pierdes stamina, sino que recuperas parte
					stamina += STAMINARUNOUTRECOVER;
				} else {
					stamina -= STAMINALOSE;
				}

				if (stamina <= 0) {
					runOutStamina = true;
				}
			} else {
				stamina += STAMINANORMALRECOVER;
			}

			// comprobamos si esta en el suelo para que avance
			if (isOnFloor) {
				if (!isStopping) {
					speedX += acelerationX * MASS;
				} else {
					speedX -= breakForce * MASS;
				}
				if (speedY <= 0) {
					speedY = 0;
				}

			} else {
				if(isOnSlope){

				} else if (!isOnWall) {
					speedY -= GRAVITY * MASS;
				}
			}

			if (isOnSlope) { // de momento no se contempla que una rampa llege a una escalera
				System.out.println("estamos en slope");
				maxSpeedInSlopeX = (float) (maxSpeedX * Math.cos(slopeRadians));
				maxSpeedInSlopeY = (float) (maxSpeedY * Math.sin(slopeRadians));
				System.out.println("maxima velocidad en cuestaX: " + maxSpeedX);
				System.out.println("maxima velocidad en cuestaY: " + maxSpeedY);

				if (!isStopping) {
					speedX += (acelerationX * MASS);
					speedY += (acelerationY * MASS);
					System.out.println("velocidad en cuestaX: " + speedX);
					System.out.println("velocidad en cuestaY: " + speedY);
				} else { // si estas frenando no te caes
					speedX -= breakForce * MASS ;
					speedY -= breakForce * MASS ;
					if (speedY < 0) { // si estoy frenando no puedo caerme
						speedY = 0;
					}
				}
			} else { // hago esto para comprobar que no se pase de velocidad
				maxSpeedX = MAXVELOCIDADX;
				maxSpeedY = MAXVELOCIDADY;
				slopeRadians = 0;
			}

			// si esta en la pared sube
			if (isOnWall) {
				speedX = 0;
				if (!isStopping) {
					speedY += acelerationY * MASS;
				} else {
					if (!isOnFloor) {

						speedY -= GRAVITY * MASS;

					} else {
						speedY = 0;
					}

				}
			}

			// si esta en el aire, ponemos la vel X al maximo para saltar las paredes
			// (PROVISIONAL)

			if ((!isOnFloor) && (!isOnWall) && (!isOnSlope)) {
				speedX = speedX / SPEEDXLOSE;
			}
		} else { // si te quedas sin estamina te quedas parado hasta que te recuperes,
			stamina += STAMINARUNOUTRECOVER;
			if ((!isOnFloor) && (!isOnWall) && (!isOnSlope)) {
				speedX = speedX / SPEEDXLOSE;
			}
			if ((!isOnFloor) && (!isOnSlope)) {
				speedY -= GRAVITY * MASS;
			} else {
				speedY = 0;
				speedX = 0;
			}
			if (stamina >= maxStamina) {
				runOutStamina = false;
				stamina = maxStamina;
			}
		}
		// Ajustamos las velocidades
		

		// actualizamos posiciones
		if(isOnFloor || isOnWall){
			adjustSpeed(maxSpeedX,maxSpeedY);
			posX += speedX;
			posY += speedY;
		} else if (isOnSlope){
			adjustSpeed(maxSpeedInSlopeX,maxSpeedInSlopeY);
				posX += speedX *Math.cos(slopeRadians);
				posY += speedX *Math.sin(slopeRadians);
		} else {
			adjustSpeed(maxSpeedX,maxSpeedY);
			posX += speedX;
			posY += speedY;
		}

		collider.recalculatePosition(posX, posY);
	}

	public void adjustSpeed(float maxX, float maxY) {
		
		if (speedX > maxX) {
			speedX = maxX;
		} else if (speedX < 0) {
			speedX = 0;
		}
		if (speedY > maxY) {
			speedY = maxY;
		} else if (speedY < MAXGRAVITYSPEED) {
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
