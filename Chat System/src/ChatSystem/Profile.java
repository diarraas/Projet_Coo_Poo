package ChatSystem;
import java.io.BufferedReader;
import java.io.*;
import java.io.InputStreamReader;
import java.net.*;

public class Profile {
    
    private int id ;
    
    private String login ;
    
	private boolean status ;
    
    private Socket client_socket ;
    
    private ServerSocket server_socket ;
    
    private int server_port ;
    
    private InetAddress server_address ;
    
    
    
    public Profile(String log) {
        try {
	    	login = log ;
	        // id = find unique random number
	        id = 65535 *((int) Math.random()) ;
	        server_port = 4445 ;
	        server_address = InetAddress.getLocalHost();
	        server_socket = new ServerSocket(server_port);
        }catch(Exception e) {
        	System.out.println("Erreur creation socket serveur");
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
	        boolean isUnic = SystemRegister.verify_unicity(log);
	        while(!isUnic){
	            //choose as many times as desired ....
	            System.out.println("Login existant");
	            System.out.println("Entrez un nouveau login");
	            reader = new BufferedReader(new InputStreamReader(System.in));
	            log = reader.readLine() ;
	            isUnic = SystemRegister.verify_unicity(log);
	        }
	        
	        new_account = new Profile(log);
	        
	        SystemRegister.users.add(new_account);
	        	      
    	}catch (Exception e) {
			System.out.println("Erreur creation de compte");
			System.exit(-1);
    	}
    	
    	if(new_account != null)	SystemRegister.add_user(new_account);
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
	        
	        login = newLog ;
	
	    }catch( Exception e) {
			System.out.println("Erreur changement de login");
	    }
    }
    
    public void authentify () {
    	/*try {
	    	if(SystemRegister.users.contains(this)){
		    	System.out.println("Entrez un login pour se connecter");
		        BufferedReader reader =
		                   new BufferedReader(new InputStreamReader(System.in));
		        String log = reader.readLine();
		        
		        while(!this.login.equalsIgnoreCase(log)){
		        	System.out.println("Erreur de connexion");
		            reader =
		                       new BufferedReader(new InputStreamReader(System.in));
		            log = reader.readLine();
		        }
		            status = true ;
		    		SystemRegister.add_online_user(this);
	        } else {
	            System.out.println("Veuillez cr�er un compte");
	        }
    	} catch(Exception e) {
			System.out.println("Erreur de connexion");
    	}
    	*/
    	try {
	    	DatagramSocket socket = new DatagramSocket();
	        socket.setBroadcast(true);
	 
	        byte[] buffer = "online".getBytes();
	 
	        DatagramPacket packet 
	          = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("255.255.255.255"), 4445);
	        socket.send(packet);
	        socket.close();
    	}catch(Exception e) {
    		System.out.println("SOS");
    	}
    	
    }
    
    public void random() throws IOException{
    	Socket idc = null ;
    	while(true) {
    		idc = server_socket.accept();
    	}
    }
        
    public void show_users() {
    
    }
    
    public void send_message(String dest){
    	try {
    		Profile rec = SystemRegister.findProfileByLogin(dest);
            client_socket = new Socket(rec.getServer_address(),rec.getServer_port(),InetAddress.getLocalHost(),9000);
	    
            System.out.println("Entrez un message");
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	    	String msg = reader.readLine() ;
	    	
	    	PrintWriter out = new PrintWriter(client_socket.getOutputStream(),true);
	    	out.println(msg);
    	}catch (Exception e) {
			System.out.println("Erreur envoi de message");
    	}
    }
    
    public void receive_message() {
    	try {	    
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
	    	String msg = reader.readLine() ;
	    	System.out.println(msg);
    	}catch (Exception e) {
			System.out.println("Erreur reception de message");
    	}
    }
    
    public void end_session() {
    	try {
    		server_socket.close();
    		status = false ;
    	}catch(Exception e) {
    		System.out.println("Erreur fermeture de connexion");
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
    
}
