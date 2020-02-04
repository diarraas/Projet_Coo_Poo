package Data;

import java.net.InetAddress;

public class RemoteUser extends User {
	/**
	 * 
	 * Utilisateur distant (interlocuteur de l'utilisateur local)
	 * 
	 * */
	
	public RemoteUser(String log, InetAddress ipAddress) {
		setLogin(log);
		setIpAddress(ipAddress);
	}
}
