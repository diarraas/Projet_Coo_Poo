package Data;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.net.*;
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private InetAddress exp ;
	private InetAddress dest ;
	private String date ;
	private String body ;
	
	public Message(InetAddress sender, InetAddress receiver, String text) {
		exp = sender ;
		dest = receiver ;
		date = LocalDateTime.now().toString();	
		body = text ;
	}
	
	/**
	public String toString(){
		return ("\n De : \t" + SystemRegister.findProfileByAddress(exp).toString() + "\n A : \t " + SystemRegister.findProfileByAddress(dest).toString() + "\n Le : \t" + date + "\n Message : \t" + body);
	}
	**/
}
