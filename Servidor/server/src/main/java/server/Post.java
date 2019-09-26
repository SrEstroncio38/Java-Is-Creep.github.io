package server;

public class Post {
 String event;
 String message;
 String Remitente;
public Post(String event, String message, String remitente) {
	this.event = event;
	this.message = message;
	Remitente = remitente;
}
@Override
public String toString() {
	return "Post [event=" + event + ", message=" + message + ", Remitente=" + Remitente + "]";
}
}
