package server;

public class GameCollectable extends GameObject{
	public final int OFFSETX = 50;
	public final int OFFSETY = 50;

	
	public GameCollectable () {
		collider = new SquareCollider(OFFSETX,OFFSETY);
	}
	
	
	

}
