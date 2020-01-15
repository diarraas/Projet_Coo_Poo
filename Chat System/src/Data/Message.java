package Data;
import java.time.LocalDateTime;
import java.io.Serializable;
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private String exp;
	private String dest ;
	private String date ;
	private String body ;
	
	public Message(String sender, String receiver, String text) {
		exp = sender ;
		dest = receiver ;
		date = LocalDateTime.now().toString();	
		body = text ;
	}
	
	public String toString(){
		return ("\n A  \t " + dest + "\n Le : \t" + date + "\n Message : \t" + body);
	}

	public String getDest() {
		return dest;
	}
	
	public String getExp() {
		return exp;
	}

	public String getDate() {
		return date;
	}

	public String getBody() {
		return body;
	}
	
}
