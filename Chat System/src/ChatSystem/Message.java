package ChatSystem;
import java.time.LocalDateTime;
import java.net.*;
public class Message {
	Socket exp ;
	Socket dest ;
	String date ;
	String body ;
	
	public Message(Socket sender, Socket receiver, String text) {
		exp = sender ;
		dest = receiver ;
		date = LocalDateTime.now().toString();		
	}
}
