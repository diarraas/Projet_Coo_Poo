package ChatSystem;
import java.time.LocalDateTime;

public class Message {
	Profile exp ;
	Profile dest ;
	String date ;
	String body ;
	
	public Message(Profile sender, Profile receiver, String text) {
		exp = sender ;
		dest = receiver ;
		date = LocalDateTime.now().toString();		
	}
}
