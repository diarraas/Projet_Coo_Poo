package Data;

import java.util.ArrayList;
import java.util.List;

public class ChatSession {
	private User exp ;
	private User dest ;
    private List<Message> sent_messages ;

    public ChatSession(User send, User rec) {
    	exp = send ;
    	dest = rec ;
        sent_messages  = new ArrayList<Message>() ;
    }
    
    /**
    public List<Message> getHistory(){
    	return null ; // use BDD here
    }
    **/

    public void addMessage(Message msg) {
    	sent_messages.add(msg);
    }

	public User getExp() {
		return exp;
	}


	public User getDest() {
		return dest;
	}


	public List<Message> getSent_messages() {
		return sent_messages;
	}


}
