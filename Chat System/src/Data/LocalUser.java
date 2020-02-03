package Data;
import Network.* ;
import GraphicUserInterface.* ;
import java.net.*;
import java.util.*;

public class LocalUser extends User {
	   
	private BroadcastServer broadcastServer ;
    
    private MessageListener messageServer ;
    
    private MessageSender messageClient ;
        
    private Notifier broadcastClient ;
    
    private  Notifier broadcastDataRequest ;
    
    private List<RemoteUser> onliners ;
    
    private List<ChatSession> ongoing ;
    
    public LocalUser() {
    	//LocalUser vide pour initialisation
    }
    
    public LocalUser(String log) {
		super(log);
		this.setIpAddress(findIpAddress());
		onliners = new ArrayList<RemoteUser>();
        ongoing = new ArrayList<ChatSession>(); 
        broadcastServer = new BroadcastServer(this);
        broadcastClient = new Notifier(this);
        messageServer = new MessageListener(this);
        broadcastServer.setRunning(true);
    	messageServer.setRunning(true);
        broadcastServer.start();
        messageServer.start();
	            
	}
    	
    
    public boolean changeLogin(String newLog) {
    	boolean changed = false ; 
    	if(findUserByLogin(newLog) == null) {
	       	changed = true ;
		    broadcastClient.notifyLoginChange(newLog);
	        this.setLogin(newLog);
	        updateChatSession(newLog);
	        Database.updateLogin(getIpAddress().getHostAddress(), newLog);
 
    	}
    	return changed ;
    }
    

	public LocalUser authentify (String log) {
		LocalUser authentified = null ;
		broadcastDataRequest = new Notifier(this);
		if(broadcastDataRequest.requestData(log)) {
	        authentified = new LocalUser(log);
			authentified.broadcastClient.notifyAuthentification();
	        setStatus(true);
	        Database.addUser(authentified);
		}
		return authentified ;
    }
     
    
    public void disconnect() { 
    	broadcastClient.notifyDisconnection();
    	broadcastServer.setRunning(false);
    	messageServer.setRunning(false);
    	setStatus(false);
    }
    
    public void sendMessage(String dest,String msg){
    	RemoteUser remote = findUserByLogin(dest);
    	if(findSessionWith(dest) != null) {
	    	if(remote != null){
	    		InetAddress remoteAddr = remote.getIpAddress();
	        	int remotePort = remote.getServerPort();
	        	messageClient = new MessageSender(this,remoteAddr,remotePort);
	        	messageClient.sendMessage(msg);
	    	}
    	}else {
    		new NotificationWindow("Pas de session avec " + dest);
    	}
    }   
    
    
    public void sendFile(String dest,String path){
    	RemoteUser remote = findUserByLogin(dest);
    	if(findSessionWith(dest) != null) {
	    	if(remote != null){
	    		InetAddress remoteAddr = remote.getIpAddress();
	        	int remotePort = remote.getServerPort();
	        	messageClient = new MessageSender(this,remoteAddr,remotePort);
	        	messageClient.sendFile(path);
	    	}
    	}else {
    		new NotificationWindow("Pas de session avec " + dest);
    	}
    }   
    
    
    public synchronized void startSession(String dest){
	    	RemoteUser remote = findUserByLogin(dest);
	    	if(remote != null){
	    		if(findSessionWith(dest) == null)		ongoing.add(new ChatSession(getLogin(),dest));
	    	} else{
	    		new NotificationWindow("Utilisateur offline ou non existant");
	    	}
    }
   
    
    public synchronized void addUser(RemoteUser user){
    	if((onliners.size() != 0 && !onliners.contains(user)) || onliners.size() == 0) {
    		onliners.add(user);
    	}

    }
    
    public synchronized void removeUser(RemoteUser user){
    	if(onliners.size() != 0 && onliners.contains(user)) {
    		onliners.remove(user);
    	}
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

	public synchronized void endSessionWith(String dest){
		sendMessage(dest,"end");
		if(ongoing.remove(findSessionWith(dest))){
			new NotificationWindow("Fin de session avec:  " + dest);
		}else {
			new NotificationWindow("Pas de session en cours avec " + dest);
		}
	}
	
	
	public ChatSession findSessionWith(String dest) {
		ChatSession retrieved = null ;
		RemoteUser user = findUserByLogin(dest);
		if(user != null) {
			boolean found = false ;
		    ListIterator<ChatSession> iterator = ongoing.listIterator() ;
	        while(iterator.hasNext() && !found){
	        	retrieved = iterator.next() ;
	            found = retrieved.getDest().equalsIgnoreCase(dest);
	        }
	        
	        if(!found)	return null ;
		}
		return retrieved ;
	}
	
	private void updateChatSession(String newLog) {
    	ListIterator<ChatSession> iterator = ongoing.listIterator() ;
    	ChatSession current ;
    	while(iterator.hasNext()){
        	 current = iterator.next() ;
            current.setExp(newLog);
        }
		
	}
	public void updateOnliners(String old, String newLog) {
		findUserByLogin(old).setLogin(newLog);
	}
	
	
	private static InetAddress findIpAddress() {
		InetAddress retrieved = null ;
		try {
	        Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
	        
	        for (; n.hasMoreElements();) {
	            NetworkInterface e = n.nextElement();
	            Enumeration<InetAddress> a = e.getInetAddresses();
	            for (; a.hasMoreElements();) {
	                InetAddress addr = a.nextElement();
	                if ((addr instanceof Inet4Address) && !addr.isLoopbackAddress()) {
	                    retrieved = addr;
	                }
	            }
	        }
	        
	        }catch(Exception e) {
	        	System.out.println("Erreur recouvrement adresse Ip locale en raison de : \t " + e.getMessage());
	        }
		return retrieved ;
	}
	

	public List<ChatSession> getOngoing() {
		return ongoing;
	}
	
	public List<RemoteUser> getOnliners() {
		return onliners;
	}


	public Notifier getBroadcastClient() {
		return broadcastClient;
	}

	public BroadcastServer getBroadcastServer() {
		return broadcastServer;
	}

	public MessageListener getMessageServer() {
		return messageServer;
	}

	public MessageSender getMessageClient() {
		return messageClient;
	}



	   
}