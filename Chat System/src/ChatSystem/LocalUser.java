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
    
    private List<RemoteUser> users ;
    
    public static int BROADCAST_PORT = 4445 ;
    
    public static int MAX_LOG = 50 ;
        
        
    public LocalUser(String log) {
    	super(log);
    	try {
	    	//Basic id data
	        onliners = new ArrayList<RemoteUser>();
	        users = new ArrayList<RemoteUser>();
	        
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
				 	            
				 	            InetAddress address = packet.getAddress();
				 	            RemoteUser newUser =  new RemoteUser(name,address);
			 	            	int port = packet.getPort();
			 	            	if(received.contentEquals("online")) {
				 	            	packet = new DatagramPacket(buf, buf.length, address, port);
				 	            	socket.send(packet);
				 	            	System.out.println("Nouvelle connexion, maj de la liste des onliners\t" );
				 	            
			 	            	}else if(received.contentEquals("offline")) {
				 	            	System.out.println("Nouvelle deconnexion, maj de la liste des onliners\t");

				 	            }else if(received.contentEquals("login change")) {
				 	            	System.out.println("Changement de login, new user name is \t" + newUser.getLogin());
				 	   			
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
	    		DatagramSocket socket = new DatagramSocket(getClientPort(),getIpAddress());
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
    		DatagramSocket socket = new DatagramSocket(getClientPort(),getIpAddress());
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
    					setStatus(true) ;
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
    		client_socket = new Socket(dest,dest_port,getIpAddress(),getClientPort()); // Change needed : sending to self for now
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
    		DatagramSocket socket = new DatagramSocket(getClientPort(),getIpAddress());
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
    					packet = new DatagramPacket((getLogin() + "offline").getBytes(), "offline".getBytes().length, broadcast, BROADCAST_PORT);
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
    
}
