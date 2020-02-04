package Data;

import java.io.Serializable;

public class Message implements Serializable {
	/**
	 * Message echangé entre deux utilisateur
	 * */
	private static final long serialVersionUID = 1L;
	private String exp;
	private String dest ;
	private String date ;
	private String body ;
	
	public Message(String sender, String receiver,String time, String text) {

		if(text.contains("\"")) text = text.replace('\"', ' ');
		if(text.contains("\\")) text = text.replace('\\', ' ');
		if(text.contains("\'")) text = text.replace('\'', ' ');
		exp = sender ;
		dest = receiver ;
		date = time ;
		body = text ;
	}
	
	public String toString(){
		return("[ " + date +" ] "+ exp+" : \t"+ body+"\n") ;
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
