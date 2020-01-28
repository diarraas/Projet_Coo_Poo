package Data;

import java.net.InetAddress;

public class User {
	
    
    private String login ;
    
    private boolean status ;
                
    public static int serverPort = 12537 ;
                    
    private InetAddress ipAddress ;
    
    public User() {
    	login = "no name";
    }
    
    public User(String log) {
    	//Basic id data
    	login = log ;
        status = true ;
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
	
	public InetAddress getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(InetAddress ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public String toString() {
		return ("User : \t " + login + "   @Ip : \t" + ipAddress.toString() );
	}
}
