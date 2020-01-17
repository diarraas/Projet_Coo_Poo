package Data;
import Network.* ;
import GraphicUserInterface.* ;
import java.net.*;
import java.util.*;

public class LocalUser extends User {
	   
	private BroadcastServer broadcastServer ;
    
    private MessageListener messageServer ;
    
    private MessageSender messageClient ;
    
    private ChatWindow chatWindow ;
    
    private Notifier broadcastClient ;
    
    private List<RemoteUser> onliners ;
    
    private List<ChatSession> ongoing ;
         
    public LocalUser() {
    	super();
    }
  
    public LocalUser(String log) {
    	super(log);      
       
    	//Find non local ip address
        try {
        Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
        
        for (; n.hasMoreElements();) {
            NetworkInterface e = n.nextElement();
            Enumeration<InetAddress> a = e.getInetAddresses();
            for (; a.hasMoreElements();) {
                InetAddress addr = a.nextElement();
                if ((addr instanceof Inet4Address) && !addr.isLoopbackAddress()) {
                    setIpAddress(addr);
                }
            }
        }
        }catch(Exception e) {
        	System.out.println("Erreur creation de nouveau LocalUser en raison de : \t " + e.getMessage());
        }
    }
    
    public static LocalUser createAccount(String log){
    	LocalUser newAccount = null;
		if(Database.isUnic(log)) {
			newAccount = new LocalUser(log);
	        Database.addUser(newAccount); 
		}
    	return newAccount ;
    }

    // use maven
    //
    
    public boolean changeLogin(String newLog) {
    	boolean changed = false ; 
    	if(Database.isUnic(newLog)) {
	        this.setLogin(newLog);
	       	changed = Database.updateLogin(this, newLog);
		    broadcastClient.notifyLoginChange();
	     }
    	return changed ;
    }
    
    public LocalUser authentify (String log) {
    	LocalUser retrieved = null ;
    	if(!Database.isUnic(log)) {
    		retrieved = Database.findUser(log);
	    	onliners = new ArrayList<RemoteUser>();
	        ongoing = new ArrayList<ChatSession>();  
	        retrieved.setOnliners(onliners);
	        retrieved.setOngoing(ongoing);
	        //chatWindow = new ChatWindow(retrieved);
	    	broadcastServer = new BroadcastServer(retrieved);
	        broadcastClient = new Notifier(retrieved);
	        messageServer = new MessageListener(retrieved);
	        broadcastServer.setRunning(true);
	    	messageServer.setRunning(true);
	        broadcastServer.start();
	        messageServer.start();
	    	broadcastClient.notifyAuthentification();
	    	setStatus(true);
    	}else {
    		System.out.println("Identifiant inconnu --- créez un compte");
    	}
    	return retrieved;
    }
    
    public void sendMessage(String dest,String msg){
    	RemoteUser remote = findUserByLogin(dest);
    	InetAddress remoteAddr = remote.getIpAddress();
    	int remotePort = remote.getServerPort();
    	messageClient = new MessageSender(this,remoteAddr,remotePort);
	    messageClient.sendMessage(msg);
		messageClient.close();
	    
    }    
    
    public void disconnect() { //Dont disconnect just yet ---- synchronize
    	
    	broadcastClient.notifyDisconnection();
    	broadcastServer.setRunning(false);
    	messageServer.setRunning(false);
    	messageServer.close();
    	setStatus(false);

    }
    
    public void startSession(String dest){
    	RemoteUser user = findUserByLogin(dest);
    	if(user != null){
    		ongoing.add(new ChatSession(getLogin(),dest));
    	}else {
    		System.out.println("Utilisateur offline ou non existant");
    	}
    }
    
    public synchronized void addUser(RemoteUser user){
    	if((onliners.size() != 0 && !onliners.contains(user)) || onliners.size() == 0)  onliners.add(user) ;

    }
    
    public void removeUser(RemoteUser user){
    	if(onliners.size() != 0 && onliners.contains(user)) onliners.remove(user);
    }
    
	public RemoteUser findUserByAddress(InetAddress address) {
		RemoteUser temp = null ;
	   	boolean found = false ;
	    ListIterator<RemoteUser> iterator = onliners.listIterator() ;
	       
	    while(iterator.hasNext() && !found){
	     	temp = iterator.next() ;
	        found = (temp.getIpAddress().equals(address)) ;
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

	public ChatSession findSessionWith(String dest) {
		ChatSession retrieved = null ;
		boolean found = false;
		ListIterator<ChatSession> iterator = ongoing.listIterator() ;
	       
	    while(iterator.hasNext() && !found){
	     	retrieved = iterator.next() ;
	        found = retrieved.getDest().contentEquals(dest) ;
	    }
	        
	    if(!found)	return null ;
	    return retrieved;
	}
	
	public void endSession(String dest) {
		ChatSession session  = findSessionWith(dest) ;
		session.setActive(false);
		ongoing.remove(session);
	}
	
	public List<RemoteUser> getOnliners() {
		return onliners;
	}


	public List<ChatSession> getOngoing() {
		return ongoing;
	}

	public void setOnliners(List<RemoteUser> onliners) {
		this.onliners = onliners;
	}

	public void setOngoing(List<ChatSession> ongoing) {
		this.ongoing = ongoing;
	}

	   
}