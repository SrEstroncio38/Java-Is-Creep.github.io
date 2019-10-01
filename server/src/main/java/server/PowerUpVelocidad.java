package server;

public class PowerUpVelocidad extends GenericPowerUp {
	
	public final int AUMENTOVELOCIDAD = 20;
	public final float AUMENTOACELERACION = 0.5f;
	
	public PowerUpVelocidad(SnailInGame snail) {
		super(snail);
	}

	public void consumirPowerUp() {
		System.out.println("CONSUMIDO POWER UP VELOCIDAD");
		snailTarget.maxVelocidad += AUMENTOVELOCIDAD;
		snailTarget.maxVelocidad += AUMENTOACELERACION;
		}

}
