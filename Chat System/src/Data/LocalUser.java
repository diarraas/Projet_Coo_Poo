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
    
    private static Notifier broadcastDataRequest ;
    
    private List<RemoteUser> onliners ;
    
    private ChatSession ongoing ;
         
    public LocalUser() {
    	super();
    }
  
    public LocalUser(String log) {
    	super(log);      
    	
    }
   
    
    public static LocalUser createAccount(String log){
    	LocalUser newAccount = null;
		try {
			if(!log.contains(" ") ){
				broadcastDataRequest = new Notifier(newAccount);
				if(broadcastDataRequest.requestData(log)) {
					newAccount = new LocalUser(log);
					newAccount.setIpAddress(findIpAddress());

				}else {
					System.out.println("Pseudo utilisé");
				}
			}
		}catch(Exception e) {
			
		}
    	
    	/* if(Database.isUnic(log)) {
			newAccount = new LocalUser(log);
	        Database.addUser(newAccount); 
		}*/
    	return newAccount ;
    }

    // use maven
    //
    
    public boolean changeLogin(String newLog) {
    	boolean changed = false ; 
    	if(!newLog.contains(" ") && findUserByLogin(newLog) == null) {
    		System.out.println("Changing login of " + getLogin() + "to  " + newLog);
	       	changed = true ;
	        this.setLogin(newLog);
		    broadcastClient.notifyLoginChange();
    		ChatWindow.updateUsers(onliners);
	     }
    	return changed ;
    }
    
    public LocalUser authentify (String log) {
    	LocalUser retrieved = null ;
    	/* if(!Database.isUnic(log)) {
    		retrieved = Database.findUser(log);
    		retrieved.setIpAddress(findIpAddress());
	    	onliners = new ArrayList<RemoteUser>();
	        ongoing = new ArrayList<ChatSession>();  
	        retrieved.setOnliners(onliners);
	        retrieved.setOngoing(ongoing);
	    	broadcastServer = new BroadcastServer(retrieved);
	        broadcastClient = new Notifier(retrieved);
	        messageServer = new MessageListener(retrieved);
	        retrieved.setBroadcastClient(broadcastClient);
	        retrieved.setBroadcastServer(broadcastServer);
	        retrieved.setMessageClient(messageClient);
	        retrieved.setMessageServer(messageServer);
	        broadcastServer.setRunning(true);
	    	messageServer.setRunning(true);
	        broadcastServer.start();
	        retrieved.getMessageServer().start();
	    	broadcastClient.notifyAuthentification();
	    	setStatus(true);
    	}else {
    		System.out.println("Identifiant inconnu --- créez un compte");
    	}*/
    	try {
			broadcastDataRequest = new Notifier(retrieved);
			if(broadcastDataRequest.requestData(log)) {
		    		retrieved = new LocalUser(log);
		        	retrieved.setIpAddress(findIpAddress());
		        	onliners = new ArrayList<RemoteUser>();
		            ongoing = null;  
		            retrieved.setOnliners(onliners);
		            retrieved.setOngoing(ongoing);
		        	broadcastServer = new BroadcastServer(retrieved);
		            broadcastClient = new Notifier(retrieved);
		            messageServer = new MessageListener(retrieved);
		            retrieved.setBroadcastClient(broadcastClient);
		            retrieved.setBroadcastServer(broadcastServer);
		            retrieved.setMessageClient(messageClient);
		            retrieved.setMessageServer(messageServer);
		            broadcastServer.setRunning(true);
		        	messageServer.setRunning(true);
		            broadcastServer.start();
		            retrieved.getMessageServer().start();
		        	broadcastClient.notifyAuthentification();
		        	setStatus(true);
		    	
			}else {
				System.out.println("Pseudo utilis�");
			}
		}catch(Exception e) {
			
		}
    	
    	return retrieved;
    }
     
    
    public void disconnect() { 
    	broadcastClient.notifyDisconnection();
    	broadcastServer.setRunning(false);
    	messageServer.setRunning(false);
    	setStatus(false);

    }
    
    public void sendMessage(String msg){
	    messageClient.sendMessage(msg);	    
    }   
    
    public void sendFile(String path){
    	messageClient.sendFile(path);
    }
    
 /*   public void startSession(String dest){
    	RemoteUser user = findUserByLogin(dest);
    	if(user != null){
    		ongoing.add(new ChatSession(getLogin(),dest));
    		// Need to callout to Chat window to print a message to screen
    	} else{
    		System.out.println("Utilisateur offline ou non existant");
    	}
    }
  
  public void sendMessage(String dest,String msg){
    	RemoteUser remote = findUserByLogin(dest);
    	System.out.println("Sending to" + remote.toString());
    	InetAddress remoteAddr = remote.getIpAddress();
    	int remotePort = remote.getServerPort();
    	messageClient = new MessageSender(this,remoteAddr,remotePort);
	    messageClient.sendMessage(msg);
		messageClient.close();
	    
    }   
  
  */
    
    
    public void startSession(String dest){
    	RemoteUser remote = findUserByLogin(dest);
    	if(remote != null){
    		ongoing = new ChatSession(getLogin(),dest);
    		InetAddress remoteAddr = remote.getIpAddress();
        	int remotePort = remote.getServerPort();
        	messageClient = new MessageSender(this,remoteAddr,remotePort); 
    		// Need to callout to Chat window to print a message to screen
    	} else{
    		System.out.println("Utilisateur offline ou non existant");
    	}
    }
    
    
    public synchronized void addUser(RemoteUser user){
    	if((onliners.size() != 0 && !onliners.contains(user)) || onliners.size() == 0) {
    		onliners.add(user);
    		ChatWindow.updateUsers(onliners);
    	}

    }
    
    public void removeUser(RemoteUser user){
    	if(onliners.size() != 0 && onliners.contains(user)) {
    		onliners.remove(user);
    		ChatWindow.updateUsers(onliners);
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
/*
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
    	System.out.println("Fin de session avec : \t " + dest);
    	//Nouveau callout at chatWindow to clear pannel
	}
*/	
	public void endSession(String dest){
		System.out.println("End of session with:  " + dest);
		if(ongoing.getDest() == dest){
			ongoing = null ;
			messageClient.close();
			//System.out.println("End of session with:  " + dest);
		}
	}
	
	public List<RemoteUser> getOnliners() {
		return onliners;
	}

	public static InetAddress findIpAddress() {
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

	public ChatSession getOngoing() {
		return ongoing;
	}

	public void setOnliners(List<RemoteUser> onliners) {
		this.onliners = onliners;
	}

	public void setOngoing(ChatSession ongoing) {
		this.ongoing = ongoing;
	}

	public Notifier getBroadcastClient() {
		return broadcastClient;
	}

	public BroadcastServer getBroadcastServer() {
		return broadcastServer;
	}

	public void setBroadcastServer(BroadcastServer broadcastServer) {
		this.broadcastServer = broadcastServer;
	}

	public MessageListener getMessageServer() {
		return messageServer;
	}

	public void setMessageServer(MessageListener messageServer) {
		this.messageServer = messageServer;
	}

	public MessageSender getMessageClient() {
		return messageClient;
	}

	public void setMessageClient(MessageSender messageClient) {
		this.messageClient = messageClient;
	}

	public void setBroadcastClient(Notifier broadcastClient) {
		this.broadcastClient = broadcastClient;
	}


	   
}