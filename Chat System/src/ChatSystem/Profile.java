package ChatSystem;
import java.io.BufferedReader;
import java.io.*;
import java.io.InputStreamReader;
import java.net.*;

public class Profile implements ChatFunctions {
    
    private int id ;
    
    private String login ;
    
	private boolean status ;
    
    private Socket client_socket ;
    
    ChatServer server_socket ;
    
    
    
    public Profile(String log) {
        login = log ;
        
        // id = find unique random number
        id = 65555 *((int) Math.random()) ;
         
    }
    
    
    public Profile create_account() throws IOException{
       System.out.println("Choisissez un login");
        BufferedReader reader =
                   new BufferedReader(new InputStreamReader(System.in));
        String log = reader.readLine(); 
        boolean isUnic = SystemRegister.verify_unicity(log);
        while(!isUnic){
            //choose as many times as desired ....
            System.out.println("Login existant");
            System.out.println("Entrez un nouveau login");
            reader = new BufferedReader(new InputStreamReader(System.in));
            log = reader.readLine() ;
            isUnic = SystemRegister.verify_unicity(log);
        }
                
        return new Profile(log) ;
    }
    
    
    public void change_login()throws IOException {
        // test unicity oflogin ------ 
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

    }
    
    public void authentify () throws IOException {
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
	        
	            client_socket = new Socket(server_socket.getServer_address(),server_socket.getServer_port(),InetAddress.getLocalHost(),9000);
	            status = true ;
	    		SystemRegister.add_online_user(this);
        } else {
            System.out.println("Veuillez créer un compte");
        }
    
    }
        
    public void show_users() {
    
    }
    
    public void send_message(Profile dest) throws IOException {
    	System.out.println("Entrez un message");
    	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    	String msg = reader.readLine() ;
    	PrintWriter out = new PrintWriter(client_socket.getOutputStream(),true);
    	out.println(msg);
    	server_socket.receive_message(dest.client_socket);
    }
    
    public void end_session() throws IOException {
        server_socket.close_connection(client_socket);
    	status = false ;
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
    
}
