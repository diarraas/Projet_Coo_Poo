package Data;

import java.net.InetAddress;

public class RemoteUser extends User {
	
	public RemoteUser(String log, InetAddress ipAddress,int serverPort) {
		setLogin(log);
		setIpAddress(ipAddress);
		setServerPort(serverPort);
	}
}
