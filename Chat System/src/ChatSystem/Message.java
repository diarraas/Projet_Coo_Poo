package ChatSystem;
import java.text.SimpleDateFormat;

public class Message {
	Profile exp ;
	Profile dest ;
	String date ;
	String body ;
	
	public Message(Profile sender, Profile receiver, String text) {
		exp = sender ;
		dest = receiver ;
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		date = formatter.format(System.currentTimeMillis());		
	}
}
