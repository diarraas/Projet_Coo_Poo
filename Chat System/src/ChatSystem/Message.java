package ChatSystem;
import java.time.LocalDateTime;
import java.net.*;
public class Message {
	InetAddress exp ;
	InetAddress dest ;
	String date ;
	String body ;
	
	public Message(InetAddress sender, InetAddress receiver, String text) {
		exp = sender ;
		dest = receiver ;
		date = LocalDateTime.now().toString();	
		body = text ;
	}
	
	public String toString(){
		return ("\n De : \t" + SystemRegister.findProfileByAddress(exp).toString() + "\n A : \t " + SystemRegister.findProfileByAddress(dest).toString() + "\n Le : \t" + date + "\n Message : \t" + body);
	}
}
