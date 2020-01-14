package Data;
import Network.* ;

import java.io.*;
import java.net.*;
import java.util.*;

public class LocalUser extends User {
   
    private BroadcastServer broadcastServer ;
    
    private MessageListener messageServer ;
    
    private MessageSender messageClient ;
    
    private Notifier broadcastClient ;
    
    private List<RemoteUser> onliners ;
    
    private List<ChatSession> ongoing ;
         
        
    public LocalUser(String log) {
    	super(log);
    	try {
	    	//Basic id data
	        onliners = new ArrayList<RemoteUser>();
	        ongoing = new ArrayList<ChatSession>();
	        
	        //Ip address of the local host
	        setIpAddress(InetAddress.getLocalHost());	        
	        
	        //Creation and launch of servers
	        broadcastServer = new BroadcastServer(this);
	        messageServer = new MessageListener(this);
	        broadcastClient = new Notifier();
	        
	        broadcastServer.start();
	        messageServer.start();
	        
	        System.out.println("New User created");
	        
        }catch(Exception e) {
        	System.out.println("Erreur creation de nouveau LocalUser en raison de : \t " + e.getMessage());
        }
    }
    
    public static LocalUser create_account(){
    	String log = "" ;
    	LocalUser newAccount = null;
    	System.out.println("Choisissez un login");
    	try {
    		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			log = reader.readLine();
			newAccount = new LocalUser(log);
		} catch (IOException e) {
			e.printStackTrace();
		} 
    	return newAccount ;
    }
   
    
    
    // use maven
    //
    
    public void change_login() {
    	try {
	        // test unicity of login ------ 
	        System.out.println("Entrez un nouveau login");
	        BufferedReader reader =
	                   new BufferedReader(new InputStreamReader(System.in));
	        String newLog = reader.readLine(); 
	      
	        this.setLogin(newLog);
	        
	    }catch( Exception e) {
			System.out.println("Erreur changement de login en raison de : \t" + e.getMessage());
	    }
    }
    
    public void authentify () {
    	
    }
    
    public void send_message(String dest){
    
    }    
    
    public void disconnect() {
    	
    }
    
    public void startSession(RemoteUser user){
    	ongoing.add(new ChatSession(this,user));
    }
    
    public void addUser(RemoteUser user){
    	onliners.add(user) ;
    }
    
    public void removeUser(RemoteUser user){
    	onliners.remove(user);
    }
    
	public RemoteUser findUserByAddress(InetAddress address) {
		RemoteUser temp = null ;
	   	boolean found = false ;
	    ListIterator<RemoteUser> iterator = onliners.listIterator() ;
	       
	    while(iterator.hasNext() && !found){
	     	temp = iterator.next() ;
	        found = temp.getIpAddress() == address ;
	    }
	        
	    if(!found)	return null ;
	   	return temp ;
	}
	   
	public RemoteUser findUserByLogin(String log) {
		RemoteUser temp = null ;
	   	boolean found = false ;
	    ListIterator<RemoteUser> iterator = onliners.listIterator() ;
        while(iterator.hasNext() && !found){
        	temp = iterator.next() ;
            found = temp.getLogin().equalsIgnoreCase(log);
        }
        
        if(!found)	return null ;
    	return temp ;
	}
	   
}
