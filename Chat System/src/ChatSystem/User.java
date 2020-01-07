package ChatSystem;

import java.net.InetAddress;

public class User {
private int id ;
    
    private String login ;
    
    private boolean status ;
                
    private int serverPort ;
                
    private int clientPort ;
    
    private InetAddress ipAddress ;
    
    public User() {
    	login = "no name";
    }
    
    public User(String log) {
    	//Basic id data
    	login = log ;
        id = 65535 *((int) Math.random()) ;
        serverPort = 1024 + id ;
        clientPort = serverPort + 50 ;
        status = true ;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public int getClientPort() {
		return clientPort;
	}

	public void setClientPort(int clientPort) {
		this.clientPort = clientPort;
	}

	public InetAddress getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(InetAddress ipAddress) {
		this.ipAddress = ipAddress;
	}
}
