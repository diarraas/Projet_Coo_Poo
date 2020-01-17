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
    
   
    public List<Message> getHistory(){
    	return Database.getHistory(exp,dest);
    }
   

    public void addMessage(Message msg) {
    	if(this!= null)	{
    		sentMessages.add(msg);
	        Database.addMessage(msg);
	     // Need to callout to Chat window to print a received message to screen -- using getHistory maybe
    	}
    	System.out.println("Sent Messages :  \n" + sentMessages.toString());
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
