package server;

public class SquareCollider { // el centro de los colliders sera la esquina de abajo izq
	float ofssetX;
	float ofssetY;
	float maxX;
	float maxY;
	float minX;
	float minY;

	public SquareCollider(int ofssetX, int ofssetY, float posX, float posY) {
		this.ofssetX = ofssetX;
		this.ofssetY = ofssetY;
		recalculatePosition(posX, posY);
	}

	public void recalculatePosition(float posX, float posY) {
		maxX = posX + ofssetX;
		minX = posX;
		maxY = posY + ofssetY;
		minY = posY;
		if(maxX < minX){
			float aux = maxX;
			maxX = minX;
			minX = aux;
		}
		if(maxY < minY){
			float aux = maxY;
			maxY = minY;
			minY = aux;
		}
	}

	public boolean hayColision(PlayerConected jugador) { // hay que intentar que solo se compruebe lo que es visible
		// System.out.println("Collider info: maxX: " +maxX + " minX: " +minX + " maxY:
		// " +maxY + " minY: " + minY);
		// System.out.println("Player info: maxX: " +jugador.mySnail.collider.maxX + "
		// minX: " +jugador.mySnail.collider.minX + " maxY: "
		// +jugador.mySnail.collider.maxY + " minY: " + jugador.mySnail.collider.minY);
		if ((maxX >= jugador.mySnail.collider.maxX) && (minX <= jugador.mySnail.collider.maxX)) { // comprobamos las
																									// maxX
			if ((maxY >= jugador.mySnail.collider.maxY) && (minY <= jugador.mySnail.collider.maxY)) { // comprobamos las
																										// maxY
				return true;
			} else if ((minY <= jugador.mySnail.collider.minY) && (maxY >= jugador.mySnail.collider.minY)) {// comprobamos
																											// las minY
				return true;
			} else if ((maxY >= jugador.mySnail.collider.maxY) && (minY <= jugador.mySnail.collider.minY)) {
				return true;
			}
		} else if ((minX <= jugador.mySnail.collider.minX) && (maxX >= jugador.mySnail.collider.minX)) { // comprobamos
																											// las minX
			if ((maxY >= jugador.mySnail.collider.maxY) && (minY <= jugador.mySnail.collider.maxY)) { // comprobamos las
																										// maxY
				return true;
			} else if ((minY <= jugador.mySnail.collider.minY) && (maxY >= jugador.mySnail.collider.minY)) {// comprobamos
																											// las minY
				return true;
			} else if ((maxY >= jugador.mySnail.collider.maxY) && (minY <= jugador.mySnail.collider.minY)) {
				return true;
			} // si el collider es más grande que el caracol
		} else if ((maxX >= jugador.mySnail.collider.maxX) && (minX <= jugador.mySnail.collider.minX)) {
			if ((maxY >= jugador.mySnail.collider.maxY) && (minY <= jugador.mySnail.collider.maxY)) { // comprobamos las
																										// maxY
				return true;
			} else if ((minY <= jugador.mySnail.collider.minY) && (maxY >= jugador.mySnail.collider.minY)) {// comprobamos
																											// las minY
				return true;
			} else if ((maxY >= jugador.mySnail.collider.maxY) && (minY <= jugador.mySnail.collider.minY)) {
				return true;
			}
		}
		return false; // si no ha colisionado

	}

	@Override
	public String toString() {
		return "SquareCollider [maxX=" + maxX + ", maxY=" + maxY + ", minX=" + minX + ", minY=" + minY + ", ofssetX="
				+ ofssetX + ", ofssetY=" + ofssetY + "]";
	}

}
