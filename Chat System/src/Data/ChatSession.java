package Data;

import java.util.ArrayList;
import java.util.List;

public class ChatSession {
	private String exp ;
	private String dest ;
    private List<Message> sentMessages ;
    private boolean active ;

    public ChatSession(String send, String rec) {
    	exp = send ;
    	dest = rec ;
    	sentMessages  = new ArrayList<Message>() ;
    	active = true ;
    }
    
    /**
    public List<Message> getHistory(){
    	return null ; // use BDD here
    }
    **/

    public void addMessage(Message msg) {
    	if(this!= null)	sentMessages.add(msg);
    }

	public String getExp() {
		return exp;
	}


	public String getDest() {
		return dest;
	}


	public List<Message> getSentMessages() {
		return sentMessages;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}


}
