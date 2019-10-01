package server;

public class SquareCollider { // el centro de los colliders sera la esquina de abajo izq
	int ofssetX;
	int ofssetY;
	int maxX;
	int maxY;
	int minX;
	int minY;
	
	public SquareCollider(int ofssetX, int ofssetY) {
		this.ofssetX = ofssetX;
		this.ofssetY = ofssetY;
	}
	
	public void recalculatePosition(int posX, int posY) {
		maxX = posX+ofssetX;
		minX = posX;
		maxY = posY+ofssetY;
		minY = posY;
	}
	
	
	public boolean hayColision(PlayerConected jugador) { // hay que intentar que solo se compruebe lo que es visible
		
		if((maxX > jugador.mySnail.collider.maxX) &&(minX< jugador.mySnail.collider.maxX)) { // comprobamos las maxX
			if((maxY > jugador.mySnail.collider.maxY) &&(minY< jugador.mySnail.collider.maxY)) { // comprobamos las maxY
				return true;
			} else if((minY < jugador.mySnail.collider.minY) &&(maxY> jugador.mySnail.collider.minY)) {//comprobamos las minY
				return true;
			}
		} else if((minX < jugador.mySnail.collider.minX)&& (maxX>jugador.mySnail.collider.minX)) { // comprobamos las minX
			if((maxY > jugador.mySnail.collider.maxY) &&(minY< jugador.mySnail.collider.maxY)) { // comprobamos las maxY
				return true;
			} else if((minY < jugador.mySnail.collider.minY) &&(maxY> jugador.mySnail.collider.minY)) {//comprobamos las minY
				return true;
			}
		}
		return false; // si no ha colisionado

	}

}
