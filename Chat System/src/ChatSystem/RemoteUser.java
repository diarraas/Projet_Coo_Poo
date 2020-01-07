package ChatSystem;

import java.net.InetAddress;

public class RemoteUser extends User {
	
	public RemoteUser(String log, InetAddress ipAddress) {
		setLogin(log);
		setIpAddress(ipAddress);
	}
}
