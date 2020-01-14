package Data;
import java.time.LocalDateTime;
import java.io.Serializable;
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private String dest ;
	private String date ;
	private String body ;
	
	public Message(String receiver, String text) {
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
}
