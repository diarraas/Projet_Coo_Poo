package Network;

import java.net.* ;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import Data.* ;

public class Notifier {
	
	private DatagramSocket senderSocket ;
	private byte[] buf = new byte[256];
	private int port = 12245 ;
	private LocalUser localHost ;
	public static InetAddress broadcastAddr;
	
	
	public Notifier(LocalUser user){
		localHost = user ;
		try{
			
			senderSocket = new DatagramSocket(port);
			senderSocket.setBroadcast(true);
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
			 
    		System.out.println("Erreur de cr�ation du NotificationSender en raison de : \t " + e.getMessage());
    	
    	}
		
	}
	
	public void notifyLoginChange(){
		try {
			String infos = localHost.getLogin() + " change " + localHost.getIpAddress().getHostAddress() + " " + localHost.getServerPort() ; 
			buf = infos.getBytes();
			DatagramPacket packet = new DatagramPacket(buf,buf.length,broadcastAddr,BroadcastServer.BROADCAST_PORT);
			senderSocket.send(packet);

		} catch(Exception e) {
			 
    		System.out.println("Erreur de notification changement de login raison de : \t " + e.getMessage());
    	
    	}
	}


	public void notifyAuthentification(){
		try {
			
			String infos = localHost.getLogin() + " login " + localHost.getIpAddress().getHostAddress() + " " + localHost.getServerPort() ;  
			buf = infos.getBytes();
			DatagramPacket packet = new DatagramPacket(buf,buf.length,broadcastAddr,BroadcastServer.BROADCAST_PORT);
			senderSocket.send(packet);
			byte[] response = new byte [500];
			DatagramPacket RespondingPacket = new DatagramPacket(response,response.length);								
			senderSocket.receive(RespondingPacket);
			String received = new String(RespondingPacket.getData(), 0, packet.getLength());
			
			while(received != null){
			    String newInfo[] = received.split(" ");
			    InetAddress address; int port ;
			    address = InetAddress.getByName(newInfo[2]);
			    if(!newInfo[0].contentEquals(localHost.getLogin())) {
			    	port = Integer.parseInt(newInfo[3].trim());
			    	RemoteUser newUser = new RemoteUser(newInfo[0],address,port);
			    	synchronized(this){
			    		localHost.addUser(newUser);
			    	}
			    }
			    
		        senderSocket.receive(RespondingPacket);
			    received = new String(RespondingPacket.getData(), 0, packet.getLength());
		    }
			
		} catch(Exception e) {
			 
    		System.out.println("Erreur de notification de connexion en raison de : \t " + e.getMessage());
    	
    	}
	}

	
	public void notifyDisconnection(){
		try {
			
			String infos = localHost.getLogin() + " logoff " + localHost.getIpAddress().getHostAddress() + " " + localHost.getServerPort() ;  
			buf = infos.getBytes();
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