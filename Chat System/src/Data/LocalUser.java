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
	        
	        //Find non local ip address
	        
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
    
    public LocalUser createAccount(){
    	String log = "" ;
    	LocalUser newAccount = null;
    	System.out.println("Choisissez un login");
    	try {
    		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			log = reader.readLine();
		/*	while(findUserByLogin(log) != null) {		//Verify unicity eventually
	        	reader =
		                   new BufferedReader(new InputStreamReader(System.in));
	        	log = reader.readLine();
	        }*/
			newAccount = new LocalUser(log);
			
		} catch (Exception e) {
	    	System.out.println("Erreur de création de compte en raison de : \t " + e.getMessage());
		} 
    	return newAccount ;
    }

    // use maven
    //
    
    public void changeLogin() {
    	try {
	        System.out.println("Entrez un nouveau login");
	        BufferedReader reader =
	                   new BufferedReader(new InputStreamReader(System.in));
	        String newLog = reader.readLine(); 
	        while(findUserByLogin(newLog) != null) {
	        	reader =
		                   new BufferedReader(new InputStreamReader(System.in));
	        	newLog = reader.readLine();
	        }
	        this.setLogin(newLog);
	        broadcastClient.notifyLoginChange();
	    }catch( Exception e) {
			System.out.println("Erreur changement de login en raison de : \t" + e.getMessage());
	    }
    }
    
    public void authentify () {
    	broadcastServer = new BroadcastServer(this);
        messageServer = new MessageListener(this);
        broadcastServer.setRunning(true);
    	messageServer.setRunning(true);
        broadcastServer.start();
        messageServer.start();
        broadcastClient = new Notifier(this);
    	broadcastClient.notifyAuthentification();
    	setStatus(true);
    }
    
    public void sendMessage(String dest){
    	RemoteUser remote = findUserByLogin(dest);
    	InetAddress remoteAddr = remote.getIpAddress();
    	int remotePort = remote.getServerPort();
        System.out.println("Dest User " +remote.toString());
    	messageClient = new MessageSender(this,remoteAddr,remotePort);
    	
    	System.out.println("Entrer un message --- 0 pour arreter");
    	
    	try {
	    	BufferedReader reader =
	                new BufferedReader(new InputStreamReader(System.in));
	    	String msg = reader.readLine(); 
	    	while(!msg.equals("0")) {
	    		messageClient.sendMessage(msg);
	    		reader =
		              new BufferedReader(new InputStreamReader(System.in));
		    	msg = reader.readLine(); 
		    	messageClient.close();
		    	messageClient = new MessageSender(this,remoteAddr,remotePort);
	    	}
    	}catch(Exception e) {
    		System.out.println("Erreur d'écriture du message en raison de : \t" + e.getMessage());
    	}
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

	   
}