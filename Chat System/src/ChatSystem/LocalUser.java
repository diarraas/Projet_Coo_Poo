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
        
    public static int BROADCAST_PORT = 4445 ;
    
    public static int MAX_LOG = 50 ;
        
        
    public LocalUser(String log) {
    	super(log);
    	try {
	    	//Basic id data
	        onliners = new ArrayList<RemoteUser>();
	        
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
		         	        	DatagramSocket socket = new DatagramSocket(BROADCAST_PORT) ;// Sometimes the broadcast port is used;
		         	        	DatagramPacket packet = new DatagramPacket(buf, buf.length);
				 	            socket.receive(packet);
				 	            String received = new String(packet.getData(), 0, packet.getLength());
				 	            String infos[] = received.split(" ");
				 	            InetAddress address = packet.getAddress();
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
	        */
	        this.setLogin(newLog);
	        
	        try {
	    		System.out.println("Notification changement de login");
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
    					System.out.println("Login : " + getLogin());
    					packet = new DatagramPacket(("" + getLogin() + " online").getBytes(), (getLogin() + " online").getBytes().length, broadcast, BROADCAST_PORT);
    					System.out.println("Longueur byte : " + (getLogin() + " online").getBytes().length);
    					System.out.println("Port Serveur" + getServerPort());
    					System.out.println("Packet : \t" + packet.toString());
    					System.out.println("Addresse de broadcast \t" + broadcast.toString());
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
    	}*/
    	
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
    
    public void end_session() {
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
    
    
	   public RemoteUser findUserByAddress(InetAddress address) {
			RemoteUser temp = null ;
	    	boolean found = false ;
	        ListIterator<RemoteUser> iterator = onliners.listIterator() ;
	        while(iterator.hasNext() && !found){
	        	temp = iterator.next() ;
	            found = temp.getIpAddress() == address ;
	        }
	        if(temp == null || !found)	return null ;
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
	        
	        if(temp == null || !found)	return null ;
	    	return temp ;
		}
	   
}
