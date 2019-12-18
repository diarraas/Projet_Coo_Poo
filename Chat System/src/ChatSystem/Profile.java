package ChatSystem;
import java.io.BufferedReader;
import java.io.*;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;

public class Profile {
    
    private int id ;
    
    private String login ;
    
    private boolean status ;
    
    private Socket client_socket ;
    
    private ServerSocket server_socket ;
        
    private int server_port ;
    
    private InetAddress server_address ;
    
    public static int BROADCAST_PORT = 4445 ;
    
    public static int MAX_LOG = 50 ;
        
        
    public Profile(String log) {
        try {
	    	//Basic id data
        	login = log ;
	        id = 65535 *((int) Math.random()) ;
	        server_port = 1024 + id ;
	        
	        //Ip address of the local host
	        server_address = InetAddress.getLocalHost();

	        //Continuously listens on to new login
	        Runnable login_listener = new Runnable() {
	            public void run() {
	            	
	            	try {
		                byte[] buf = new byte[256];
	         	        while (true) {
	         	        	DatagramSocket socket = new DatagramSocket(BROADCAST_PORT + 22) ;// Sometimes the broadcast port is used;
	         	        	DatagramPacket packet = new DatagramPacket(buf, buf.length);
			 	            socket.receive(packet);
			 	            String received = new String(packet.getData(), 0, packet.getLength());
			 	            if(received.contentEquals("online")) {
			 	            	InetAddress address = packet.getAddress();
			 	            	int port = packet.getPort();
			 	            	packet = new DatagramPacket(buf, buf.length, address, port);
			 	            	socket.send(packet);
			 	            	System.out.println("Nouvelle connexion, màj de la liste des onliners\t" + received);
			 	            }else if(received.contentEquals("offline")) {
			 	            	System.out.println("Nouvelle deconnexion, màj de la liste des onliners\t" + received);
			 	            }
		            		socket.close();
	         	        }
	         	        
	                  } catch ( Exception e) {
	                  		System.out.println("Erreur création du serveur d'écoute connexion en raison de :\t" + e.getMessage() );
	                  }
	                 
	            }
	        };
	        
	        //Continuously listens on to every message the host can receive from other connected users
	        Runnable listening_server = new Runnable() {
	        	public void run(){
	        		try {
	        		    Socket remote ;
	        		    server_socket = new ServerSocket(server_port+333,MAX_LOG,server_address);
	        			while(true) {
	        				remote = server_socket.accept();
	        				BufferedReader in = new BufferedReader(new InputStreamReader(remote.getInputStream()));
	                        String input = in.readLine() ;
	                        System.out.println("Client sent : \t" + input);
	                        remote.close();
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
    	try {
	       System.out.println("Choisissez un login");
	       BufferedReader reader =
	                   new BufferedReader(new InputStreamReader(System.in));
	         log = reader.readLine(); 
	       /* boolean isUnic = SystemRegister.verify_unicity(log);
	        while(!isUnic){
	            //choose as many times as desired ....
	            System.out.println("Login existant");
	            System.out.println("Entrez un nouveau login");
	            reader = new BufferedReader(new InputStreamReader(System.in));
	            log = reader.readLine() ;
	            isUnic = SystemRegister.verify_unicity(log);
	        }*/
	        
	        new_account = new Profile(log);
	        
    	}catch (Exception e) {
			System.out.println("Erreur creation de compte");
			System.exit(-1);
    	}
    	
    	//if(new_account != null)	SystemRegister.add_user(new_account);
    	return new_account ;
    }
    
    
   /* public void change_login() {
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
	        
	        login = newLog ;
	
	    }catch( Exception e) {
			System.out.println("Erreur changement de login en raison de : \t" + e.getMessage());
	    }
    }*/
    
    public void authentify () {
    	try {
    		System.out.println("Authentification");
    		DatagramSocket socket = new DatagramSocket(server_port + 10);
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
    					packet = new DatagramPacket("online".getBytes(), "online".getBytes().length, broadcast, BROADCAST_PORT + 22);
    					socket.send(packet);
    					socket.setBroadcast(false);
    					status = true ;
    				}
    			}
    		}
			
    		socket.close();
	        
    	} catch(Exception e) {
 
    		System.out.println("Erreur connection en raison de : \t " + e.getMessage());
    	
    	}
    	
    }
    
    public void send_message(String dest){
    	/*	try {
    			Profile rec = SystemRegister.findProfileByLogin(dest);
	        	client_socket = new Socket(rec.getServer_address(),rec.getServer_port(),InetAddress.getLocalHost(),server_port);
	    
            	System.out.println("Entrez un message");
	    		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	    		String msg = reader.readLine() ;
	    	
	    		PrintWriter out = new PrintWriter(client_socket.getOutputStream(),true);
	    		out.println(msg);
	    		
    		}catch (Exception e) {
			System.out.println("Erreur envoi de message");
    	}*/
    	
    	try {
    		client_socket = new Socket(this.getServer_address(),this.getServer_port()+333); // Change needed : sending to self for now
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
    		System.out.println("Déconnexion");
    		DatagramSocket socket = new DatagramSocket(server_port + 20);
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
    					packet = new DatagramPacket("offline".getBytes(), "offline".getBytes().length, broadcast, BROADCAST_PORT + 22);
    					socket.send(packet);
    					socket.setBroadcast(false);
    					status = true ;
    				}
    			}
    		}
			
    		socket.close();
    		
    	} catch(Exception e) {
 
    		System.out.println("Erreur connection en raison de : \t " + e.getMessage());
    	
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


	public InetAddress getServer_address() {
		return server_address;
	}


	public void setServer_address(InetAddress server_address) {
		this.server_address = server_address;
	}

	public ServerSocket getServer_socket() {
		return server_socket;
	}

}


