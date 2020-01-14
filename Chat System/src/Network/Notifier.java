package Network;

import java.net.* ;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class Notifier {
	
	private DatagramSocket senderSocket ;
	private InetAddress ipAddress ;
	private byte[] buf = new byte[256];
	private int port = 12245 ;
	public static InetAddress broadcastAddr;
	
	
	public Notifier(){
		try{
			
			senderSocket = new DatagramSocket();
			senderSocket.setBroadcast(true);
			senderSocket.connect(InetAddress.getLocalHost(), port);
			ipAddress = senderSocket.getLocalAddress();
			InetAddress broadcast = null ;	    	
	    	Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
    		
    		while (en.hasMoreElements()) {
    			NetworkInterface ni = en.nextElement();
    			List<InterfaceAddress> list = ni.getInterfaceAddresses();
    			Iterator<InterfaceAddress> it = list.iterator();
    			while (it.hasNext()) {
    				InterfaceAddress ia = it.next();
    				broadcast = ia.getBroadcast();
    				if(broadcast != null){
    					broadcastAddr = broadcast ;
    				}
    				
    			}
    		}
			
			
		} catch(Exception e) {
			 
    		System.out.println("Erreur de création du NotificationSender en raison de : \t " + e.getMessage());
    	
    	}
		
	}
	
	public void notifyLoginChange(){
		try {
			
			buf = "change".getBytes();
			DatagramPacket packet = new DatagramPacket(buf,buf.length,broadcastAddr,BroadcastServer.BROADCAST_PORT);
			senderSocket.send(packet);

		} catch(Exception e) {
			 
    		System.out.println("Erreur de notification changement de login raison de : \t " + e.getMessage());
    	
    	}
	}


	public void notifyAuthentification(){
		try {
			
			buf = "login".getBytes();
			DatagramPacket packet = new DatagramPacket(buf,buf.length,broadcastAddr,BroadcastServer.BROADCAST_PORT);
			senderSocket.send(packet);

		} catch(Exception e) {
			 
    		System.out.println("Erreur de notification de connexion en raison de : \t " + e.getMessage());
    	
    	}
	}

	
	public void notifyDisconnection(){
		try {
			
			buf = "off".getBytes();
			DatagramPacket packet = new DatagramPacket(buf,buf.length,broadcastAddr,BroadcastServer.BROADCAST_PORT);
			senderSocket.send(packet);

		} catch(Exception e) {
			 
    		System.out.println("Erreur de notification de deconnexion en raison de : \t " + e.getMessage());
    	
    	}
	}
	
	public void close() {
		senderSocket.close();
	}
}
