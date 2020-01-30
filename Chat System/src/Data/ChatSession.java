package Data;

import java.util.List;

import GraphicUserInterface.DiscussionWindow;

public class ChatSession {
	private String exp ;
	private String dest ;
    private List<Message> sentMessages ;
    private boolean active ;

    public ChatSession(String send, String rec) {
    	exp = send ;
    	dest = rec ;
    	this.updateMessages();
    	active = true ;
    }
    
    public void updateMessages(){
    	this.sentMessages = Database.getHistory(exp,dest);
    }
    
    public void addMessage(Message msg) {
		this.sentMessages.add(msg);
		DiscussionWindow.updateMessageDisplay(this.sentMessages);
    	System.out.println(sentMessages.toString());
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
