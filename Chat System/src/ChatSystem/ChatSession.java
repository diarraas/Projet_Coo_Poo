package ChatSystem;

import java.util.ArrayList;
import java.util.List;

public class ChatSession {
	Profile exp ;
	Profile dest ;
    List<Message> sent_messages ;

    public ChatSession(Profile send, Profile rec) {
    	exp = send ;
    	dest = rec ;
        sent_messages  = new ArrayList<Message>() ;
    }
    
    public List<Message> getHistory(){
    	return SystemRegister.retrieve_messages(exp,dest);
    }
    
    public void addMessage(Message msg) {
    	SystemRegister.update_messages(msg);
    	sent_messages.add(msg);
    }
}
