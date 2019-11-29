package ChatSystem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Profile implements ChatFunctions {

static int justAthought = 0 ;
    
    private int id ;
    
    private String login ;
    
	private boolean status ;
    
    private Socket client_socket ;
    
    Chat_server server_socket ;
    
    private InetAddress server_address ;

	private int server_port ;
    
    public Profile(String log) throws IOException{
        login = log ;
        
        // id = find unique random number
         id = justAthought ;
         
         justAthought ++ ;
                 
        status = true ;
        
        client_socket = new Socket(server_address,server_port);
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
    
    public void authentify (){
        if(SystemRegister.users.contains(this)){
            status = true ;
        } else {
            System.out.println("Erreur d'authentification");
        }
    
    }
        
    public void show_users() {
    
    }
    
    public void send_message(Profile dest){
    	
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
	
    public InetAddress getServer_address() {
		return server_address;
	}

	public void setServer_address(InetAddress server_address) {
		this.server_address = server_address;
	}


	public int getServer_port() {
		return server_port;
	}


	public void setServer_port(int server_port) {
		this.server_port = server_port;
	}
    
}
