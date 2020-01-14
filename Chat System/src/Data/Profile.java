package Data;
import java.io.BufferedReader;
import java.io.*;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;

public class Profile {}
    
/*    private int id ;
    
    private String login ;
    
    private boolean status ;
    
    private Socket client_socket ;
    
    private ServerSocket server_socket ;
        
    private int server_port ;
    
    private int login_port ;
    
    private int logout_port ;
    
    private int change_port ;
    
    private int client_port ;
    
    private InetAddress ip_address ;
    
    public static int BROADCAST_PORT = 4445 ;
    
    public static int MAX_LOG = 50 ;
        
        
    public Profile(String log) {
        try {
        	
	    	//Basic id data
        	login = log ;
	        id = ((int) Math.random()*65535) ;
	        server_port = 1024 + id ;
	        login_port = server_port + 50 ;
	        logout_port = login_port + 50 ;
	        change_port = logout_port + 50 ;
	        client_port = change_port + 50 ;
	        status = true ;	       
	        
	        //Ip address of the local host
	        ip_address = InetAddress.getLocalHost();	        
	        
	        System.out.println("New User created");
	        
	        //Continuously listens on to new login
	        Runnable login_listener = new Runnable() {
	            public void run() {
	            	
	            	try {
		                byte[] buf = new byte[256];
	         	        while (true) {
	         	        	if(status == true){
		         	        	DatagramSocket socket = new DatagramSocket(BROADCAST_PORT) ;// Sometimes the broadcast port is used;
		         	        	DatagramPacket packet = new DatagramPacket(buf, buf.length);
				 	            socket.receive(packet);
				 	            String received = new String(packet.getData(), 0, packet.getLength());
				 	            InetAddress address = packet.getAddress();
			 	            	int port = packet.getPort();
			 	            	Profile user = null ;
				 	            System.out.println(user.toString());
			 	            	if(received.contentEquals("online")) {
				 	            	packet = new DatagramPacket(buf, buf.length, address, port);
				 	            	socket.send(packet);
				 	            	System.out.println("Nouvelle connexion, maj de la liste des onliners\t" );
				 	            
			 	            	}else if(received.contentEquals("offline")) {
				 	            	System.out.println("Nouvelle deconnexion, maj de la liste des onliners\t");

				 	            }else if(received.contentEquals("login change")) {
				 	            	System.out.println("Changement de login, new user name is \t" + user.getLogin());
				 	   			
				 	            }
			 	            	socket.close();
	         	        	}
	         	        }
	         	        
	                  } catch ( Exception e) {
	                  		System.out.println("Erreur creation du serveur d'ecoute connexion en raison de :\t" + e.getMessage() );
	                  }
	                 
	            }
	        };
	        
	        //Continuously listens on to every message the host can receive from other connected users
	        Runnable listening_server = new Runnable() {
	        	public void run(){
	        		try {
	        		    Socket remote ;
	        		    server_socket = new ServerSocket(server_port,MAX_LOG,ip_address);
	        			while(true) {
	        				if(status == true){
		        				remote = server_socket.accept();
		        				BufferedReader in = new BufferedReader(new InputStreamReader(remote.getInputStream()));
		                        String input = in.readLine() ;
		                        System.out.println("Client sent : \t" + input);
		                        remote.close();
	        				}
	        			}
	        		}catch(Exception e) {
	        			System.out.println("Erreur creation du serveur message en raison de : \t " + e.getMessage());
	        		}
	        	}
	        };
	        
	        //Launching background tasks (listening servers)
	        new Thread(login_listener).start();
	        new Thread(listening_server).start();

        }catch(Exception e) {
        	System.out.println("Erreur creation de nouveau profile en raison de : \t " + e.getMessage());
        }
    }
    
    public static Profile create_account(){
    	String log = "" ;
    	Profile new_account = null;
  /*  	try {
	       System.out.println("Choisissez un login");
	       BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	       log = reader.readLine(); 
	       //boolean isUnic = SystemRegister.verify_unicity(log);
	       while(!isUnic){
	            //choose as many times as desired ....
	            System.out.println("Login existant");
	            System.out.println("Entrez un nouveau login");
	            reader = new BufferedReader(new InputStreamReader(System.in));
	            log = reader.readLine() ;
	            isUnic = SystemRegister.verify_unicity(log);
	       }
	        
	       new_account = new Profile(log);
	        
    	}catch (Exception e) {
			System.out.println("Erreur creation de compte en raison \t " + e.getMessage());
			System.exit(-1);
    	}
    	
    	return new_account ;
    }
   
    
    public void change_login() {
    	try {
	        // test unicity of login ------ 
	        System.out.println("Entrez un nouveau login");
	        BufferedReader reader =
	                   new BufferedReader(new InputStreamReader(System.in));
	        String newLog = reader.readLine(); 
	        boolean isUnic = SystemRegister.verify_unicity(newLog);
	        while(!isUnic){
	            //choose as many times as desired ....
	            System.out.println("Login existant");
	            System.out.println("Entrez un nouveau login");
	            reader = new BufferedReader(new InputStreamReader(System.in));
	            newLog = reader.readLine() ;
	            isUnic = SystemRegister.verify_unicity(newLog);
	        }
	        
	        this.setLogin(newLog);
	        
	        try {
	    		System.out.println("Notification changement de login");
	    		DatagramSocket socket = new DatagramSocket(change_port,ip_address);
		    	InetAddress broadcast = null ;
		    	socket.setBroadcast(true);
	    		DatagramPacket packet = null ;
	    		Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
	    		while (en.hasMoreElements()) {
	    			NetworkInterface ni = en.nextElement();
	    			List<InterfaceAddress> list = ni.getInterfaceAddresses();
	    			Iterator<InterfaceAddress> it = list.iterator();
	    			while (it.hasNext()) {
	    				InterfaceAddress ia = it.next();
	    				broadcast = ia.getBroadcast();
	    				if(broadcast !=null){
	    					packet = new DatagramPacket("login change".getBytes(), "login change".getBytes().length, broadcast, BROADCAST_PORT);
	    					socket.send(packet);
	    					socket.setBroadcast(false);
	    					status = true ;
	    				}
	    			}
	    		}
				
	    		socket.close();
		        
	    	} catch(Exception e) {
	 
	    		System.out.println("Erreur connexion en raison de : \t " + e.getMessage());
	    	
	    	}
	
	    }catch( Exception e) {
			System.out.println("Erreur changement de login en raison de : \t" + e.getMessage());
	    }
    }
    
    public void authentify1 () {
    	try {
    		System.out.println("Authentification");
    		DatagramSocket socket = new DatagramSocket(login_port,ip_address);
	    	InetAddress broadcast = null ;
	    	socket.setBroadcast(true);
    		DatagramPacket packet = null ;
    		Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
    		while (en.hasMoreElements()) {
    			NetworkInterface ni = en.nextElement();
    			List<InterfaceAddress> list = ni.getInterfaceAddresses();
    			Iterator<InterfaceAddress> it = list.iterator();
    			while (it.hasNext()) {
    				InterfaceAddress ia = it.next();
    				broadcast = ia.getBroadcast();
    				if(broadcast !=null){
    					packet = new DatagramPacket("online".getBytes(), "online".getBytes().length, broadcast, BROADCAST_PORT);
    					socket.send(packet);
    					socket.setBroadcast(false);
    					status = true ;
    				}
    			}
    		}
			
    		socket.close();
	        
    	} catch(Exception e) {
 
    		System.out.println("Erreur connexion en raison de : \t " + e.getMessage());
    	
    	}
    	
    }
    
    public void send_message(InetAddress dest,int dest_port){
    /*public void send_message(String dest_user){
    	try {
    		Profile dest = SystemRegister.findProfileByLogin(dest_user);
    		client_socket = new Socket(dest.getIp_address(),dest.getServer_port(),ip_address,client_port); // Change needed : sending to self for now
        	System.out.println("Entrez un message");
    		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    		String msg = reader.readLine() ;
    		PrintWriter out = new PrintWriter(client_socket.getOutputStream(),true);
    		out.println(msg);
    		client_socket.close();
    	} catch(Exception e) {
    		System.out.println("Erreur d'envoi en raison de : \t " + e.getMessage());
    	}
    	
    	try {
    		client_socket = new Socket(dest,dest_port,ip_address,client_port); // Change needed : sending to self for now
        	System.out.println("Entrez un message");
    		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    		String msg = reader.readLine() ;
    		PrintWriter out = new PrintWriter(client_socket.getOutputStream(),true);
    		out.println(msg);
    		client_socket.close();
    	} catch(Exception e) {
    		System.out.println("Erreur d'envoi en raison de : \t " + e.getMessage());
    	}
    	
    }    
    
    public void end_session() {
    	try {
    		System.out.println("Deconnexion");
    		DatagramSocket socket = new DatagramSocket(logout_port,ip_address);
	    	InetAddress broadcast = null ;
	    	socket.setBroadcast(true);
    		DatagramPacket packet = null ;
    		Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
    		while (en.hasMoreElements()) {
    			NetworkInterface ni = en.nextElement();
    			List<InterfaceAddress> list = ni.getInterfaceAddresses();
    			Iterator<InterfaceAddress> it = list.iterator();
    			while (it.hasNext()) {
    				InterfaceAddress ia = it.next();
    				broadcast = ia.getBroadcast();
    				if(broadcast != null){
    					packet = new DatagramPacket("offline".getBytes(), "offline".getBytes().length, broadcast, BROADCAST_PORT);
    					socket.send(packet);
    					socket.setBroadcast(false);
    					status = false ;
    				}
    			}
    		}
			
    		socket.close();
    		
    	} catch(Exception e) {
 
    		System.out.println("Erreur deconnexion en raison de : \t " + e.getMessage());
    	
    	}
    }
    
    public int getId(){
        return id ;
    }
    
    
    public String getLogin(){
        return login ;
    }
    
    public boolean getStatus(){
        return status ;
    }
    
    public void setLogin(String login) {
		this.login = login;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}


	public int getServer_port() {
		return server_port;
	}


	public void setServer_port(int server_port) {
		this.server_port = server_port;
	}


	public InetAddress getIp_address() {
		return ip_address;
	}


	public void setIp_address(InetAddress server_address) {
		this.ip_address = server_address;
	}

	public ServerSocket getServer_socket() {
		return server_socket;
	}
	
	public String toString(){
		return login ;
	}
}
*/
/**
 * 
 

package ChatSystem;
import java.io.BufferedReader;
import java.io.*;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;



public class LocalUser extends User {
   
    private Socket client_socket ;
    
    private ServerSocket server_socket ;
    
    private List<RemoteUser> onliners ;
    
    private List<ChatSession> ongoing ;
        
    public static int BROADCAST_PORT = 4445 ;
    
    public static int MAX_LOG = 50 ;
        
        
    public LocalUser(String log) {
    	super(log);
    	try {
	    	//Basic id data
	        onliners = new ArrayList<RemoteUser>();
	        ongoing = new ArrayList<ChatSession>();
	        
	        //Ip address of the local host
	        setIpAddress(InetAddress.getLocalHost());	        
	        
	        System.out.println("New User created");
	        
	        //Continuously listens on to new login
	        Runnable login_listener = new Runnable() {
	            public void run() {
	            	
	            	try {
		                byte[] buf = new byte[256];
	         	        while (true) {
	         	        	if(getStatus() == true){
		         	        	DatagramSocket socket = new DatagramSocket(BROADCAST_PORT) ;
		         	        	DatagramPacket packet = new DatagramPacket(buf, buf.length);
				 	            socket.receive(packet);
				 	            String received = new String(packet.getData(), 0, packet.getLength());
				 	            String infos[] = received.split(" ");
				 	            InetAddress address = packet.getAddress();
				 	            System.out.println("Ip sender : \t" + address.toString());
				 	            System.out.println("Ip receiver : \t" + getIpAddress().toString());
				 	            String name = infos[0];
				 	            RemoteUser newUser =  new RemoteUser(name,address);
			 	            	int port = packet.getPort();
				 	            newUser.setServerPort(port);
			 	            	if(infos[1].contentEquals("online")) {
				 	            	packet = new DatagramPacket(buf, buf.length, address, port);
				 	            	socket.send(packet);
				 	            	System.out.println("Nouvelle connexion, maj de la liste des onliners\t" );
				 	            	onliners.add(newUser);
				 	            
			 	            	}else if(infos[1].contentEquals("offline")) {
				 	            	System.out.println("Nouvelle deconnexion, maj de la liste des onliners\t");
				 	            	if(onliners.size() != 0)	onliners.remove(newUser);

				 	            }else if(infos[1].contentEquals("change")) {
				 	            	System.out.println("Changement de login, new user name is \t" + newUser.getLogin());
				 	            	(findUserByAddress(address)).setLogin(name);
				 	   			
				 	            }
			 	            	socket.close();
	         	        	}
	         	        }
	         	        
	                  } catch ( Exception e) {
	                  		System.out.println("Erreur creation du serveur d'ecoute connexion en raison de :\t" + e.getMessage() );
	                  }
	                 
	            }

	        };
	        
	        //Continuously listens on to every message the host can receive from other connected users
	        Runnable listening_server = new Runnable() {
	        	public void run(){
	        		try {
	        		    Socket remote ;
	        		    server_socket = new ServerSocket(getServerPort(),MAX_LOG,getIpAddress());
	        			while(true) {
	        				if(getStatus() == true){
		        				remote = server_socket.accept();
		        				BufferedReader in = new BufferedReader(new InputStreamReader(remote.getInputStream()));
		                        String input = in.readLine() ;
		                        System.out.println("Client sent : \t" + input);
		                        remote.close();
	        				}
	        			}
	        		}catch(Exception e) {
	        			System.out.println("Erreur creation du serveur message en raison de : \t " + e.getMessage());
	        		}
	        	}
	        };
	        
	        //Launching background tasks (listening servers)
	        new Thread(login_listener).start();
	        new Thread(listening_server).start();

        }catch(Exception e) {
        	System.out.println("Erreur creation de nouveau LocalUser en raison de : \t " + e.getMessage());
        }
    }
    
    public static LocalUser create_account(){
    	String log = "" ;
    	LocalUser new_account = null;
    	try {
	       System.out.println("Choisissez un login");
	       BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	       log = reader.readLine(); 
	       boolean isUnic = SystemRegister.verify_unicity(log);
	       while(!isUnic){
	            //choose as many times as desired ....
	            System.out.println("Login existant");
	            System.out.println("Entrez un nouveau login");
	            reader = new BufferedReader(new InputStreamReader(System.in));
	            log = reader.readLine() ;
	            isUnic = SystemRegister.verify_unicity(log);
	       }
	        
	       new_account = new LocalUser(log);
	       
	       
	        
    	}catch (Exception e) {
			System.out.println("Erreur creation de compte en raison \t " + e.getMessage());
			System.exit(-1);
    	}
    	
    	return new_account ;
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
	       /*
	        boolean isUnic = SystemRegister.verify_unicity(newLog);
	        while(!isUnic){
	            //choose as many times as desired ....
	            System.out.println("Login existant");
	            System.out.println("Entrez un nouveau login");
	            reader = new BufferedReader(new InputStreamReader(System.in));
	            newLog = reader.readLine() ;
	            isUnic = SystemRegister.verify_unicity(newLog);
	        }
	        
	        this.setLogin(newLog);
	        
	        try {
	    		System.out.println("Notification changement de login");
	    		DatagramSocket socket = new DatagramSocket(getServerPort());
	    		
		    	InetAddress broadcast = null ;
		    	socket.setBroadcast(true);
	    		DatagramPacket packet = null ;
	    		Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
	    		while (en.hasMoreElements()) {
	    			NetworkInterface ni = en.nextElement();
	    			List<InterfaceAddress> list = ni.getInterfaceAddresses();
	    			Iterator<InterfaceAddress> it = list.iterator();
	    			while (it.hasNext()) {
	    				InterfaceAddress ia = it.next();
	    				broadcast = ia.getBroadcast();
	    				if(broadcast !=null){
	    					packet = new DatagramPacket(("" + getLogin() + " change").getBytes(), ("" + getLogin() + " change").getBytes().length, broadcast, BROADCAST_PORT);
	    					socket.send(packet);
	    					socket.setBroadcast(false);
	    				}
	    			}
	    		}
				
	    		socket.close();
		        
	    	} catch(Exception e) {
	 
	    		System.out.println("Erreur connexion en raison de : \t " + e.getMessage());
	    	
	    	}
	
	    }catch( Exception e) {
			System.out.println("Erreur changement de login en raison de : \t" + e.getMessage());
	    }
    }
    
    public void authentify () {
    	try {
    		System.out.println("Authentification");
    		DatagramSocket socket = new DatagramSocket(getServerPort());
	    	InetAddress broadcast = null ;
	    	socket.setBroadcast(true);
    		DatagramPacket packet = null ;
    		Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
    		
    		while (en.hasMoreElements()) {
    			NetworkInterface ni = en.nextElement();
    			List<InterfaceAddress> list = ni.getInterfaceAddresses();
    			Iterator<InterfaceAddress> it = list.iterator();
    			while (it.hasNext()) {
    				InterfaceAddress ia = it.next();
    				broadcast = ia.getBroadcast();
    				if(broadcast != null){
    					packet = new DatagramPacket(("" + getLogin() + " online").getBytes(), (getLogin() + " online").getBytes().length, broadcast, BROADCAST_PORT);
    					socket.send(packet);
    					System.out.println("Packet sent");
    					socket.setBroadcast(false);
    					setStatus(true) ;
    				}
    				
    			}
    		}
			
    		socket.close();
	        
    	} catch(Exception e) {
 
    		System.out.println("Erreur connexion en raison de : \t " + e.getMessage());
    	
    	}
    	
    }
    
    public void send_message(String dest){
    /*public void send_message(String dest_user){
    	try {
    		LocalUser dest = SystemRegister.findUserByLogin(dest_user);
    		client_socket = new Socket(dest.getgetIpAddress()(),dest.getServerPort()(),getIpAddress(),getClientPort()); // Change needed : sending to self for now
        	System.out.println("Entrez un message");
    		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    		String msg = reader.readLine() ;
    		PrintWriter out = new PrintWriter(client_socket.getOutputStream(),true);
    		out.println(msg);
    		client_socket.close();
    	} catch(Exception e) {
    		System.out.println("Erreur d'envoi en raison de : \t " + e.getMessage());
    	}
    	
    	try {
    		RemoteUser remote = findUserByLogin(dest);
    		client_socket = new Socket(remote.getIpAddress(),remote.getServerPort(),getIpAddress(),getClientPort()); // Change needed : sending to self for now
        	System.out.println("Entrez un message");
    		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    		String msg = reader.readLine() ;
    		PrintWriter out = new PrintWriter(client_socket.getOutputStream(),true);
    		out.println(msg);
    		client_socket.close();
    	} catch(Exception e) {
    		System.out.println("Erreur d'envoi en raison de : \t " + e.getMessage());
    	}
    	
    }    
    
    public void disconnect() {
    	try {
    		System.out.println("Deconnexion");
    		DatagramSocket socket = new DatagramSocket(getServerPort(),getIpAddress());
	    	InetAddress broadcast = null ;
	    	socket.setBroadcast(true);
    		DatagramPacket packet = null ;
    		Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
    		while (en.hasMoreElements()) {
    			NetworkInterface ni = en.nextElement();
    			List<InterfaceAddress> list = ni.getInterfaceAddresses();
    			Iterator<InterfaceAddress> it = list.iterator();
    			while (it.hasNext()) {
    				InterfaceAddress ia = it.next();
    				broadcast = ia.getBroadcast();
    				if(broadcast != null){
    					packet = new DatagramPacket(("" + getLogin() + " offline").getBytes(), (getLogin() + " offline").getBytes().length, broadcast, BROADCAST_PORT);
    					socket.send(packet);
    					socket.setBroadcast(false);
    					setStatus(false) ;
    				}
    			}
    		}
			
    		socket.close();
    		
    	} catch(Exception e) {
 
    		System.out.println("Erreur deconnexion en raison de : \t " + e.getMessage());
    	
    	}
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


**/
