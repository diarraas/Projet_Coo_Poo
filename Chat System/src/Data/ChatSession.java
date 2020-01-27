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
    	this.updateMessages();
    	if(this.sentMessages == null)	this.sentMessages = new ArrayList<Message>();
    	active = true ;
    }
    
   
    public List<Message> getHistory(){
    	return Database.getHistory(exp,dest);
    }
   
    public void updateMessages(){
    	this.sentMessages = Database.getHistory(exp,dest);
    }
    
    public void addMessage(Message msg) {
    	if(this!= null)	{
    		this.sentMessages.add(msg);
    	}
    	System.out.println(sentMessages.toString());

    	//System.out.println("Sent Messages :  \n" + getHistory().toString());
    }

	public String getExp() {
		return exp;
	}


	public String getDest() {
		return dest;
	}
	
	public void setDest(String dest) {
		this.dest = dest ;
		
	}
	
	public void setExp(String exp) {
		this.exp = exp ;
		
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
