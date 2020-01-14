package Data;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.net.*;
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private User exp ;
	private User dest ;
	private String date ;
	private String body ;
	
	public Message(User sender, User receiver, String text) {
		exp = sender ;
		dest = receiver ;
		date = LocalDateTime.now().toString();	
		body = text ;
	}
	
	public String toString(){
		return ("\n De : \t" + exp + "\n A : \t " + dest + "\n Le : \t" + date + "\n Message : \t" + body);
	}
}
