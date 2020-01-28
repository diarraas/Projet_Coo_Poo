package Network;

import java.io.IOException;
import java.net.* ;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import Data.* ;

public class Notifier {
	
	private DatagramSocket senderSocket ;
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
			 
    		System.out.println("Erreur de creation du NotificationSender en raison de : \t " + e.getMessage());
			e.printStackTrace();
    	
    	}
		
	}
	
	public void notifyLoginChange(String newLog){
		try {
			byte[] buf = new byte[65535];
			String infos = localHost.getLogin() + " change " + localHost.getIpAddress().getHostAddress() + " " + newLog + " " + localHost.getServerPort() ;  
			buf = infos.getBytes();
			DatagramPacket packet = new DatagramPacket(buf,buf.length,broadcastAddr,BroadcastServer.BROADCAST_PORT);
			senderSocket.send(packet);
		} catch(Exception e) {
			 
    		System.out.println("Erreur de notification changement de login raison de : \t " + e.getMessage());
			e.printStackTrace();
    	
    	}
	}


	public void notifyAuthentification() {
		try {
			byte[] buf = new byte[65535];
			String infos = localHost.getLogin() + " login " + localHost.getIpAddress().getHostAddress() + " " + localHost.getServerPort() ;  
			buf = infos.getBytes();
			DatagramPacket packet = new DatagramPacket(buf,buf.length,broadcastAddr,BroadcastServer.BROADCAST_PORT);
			senderSocket.send(packet);
			byte[] response = new byte [65535];
			DatagramPacket RespondingPacket = new DatagramPacket(response,response.length);								
			senderSocket.receive(RespondingPacket);
			String received = new String(RespondingPacket.getData(), 0, packet.getLength());

			while(received != null){
			    String newInfo[] = received.split(" ");
			    InetAddress address;
			    address = InetAddress.getByName(newInfo[2]);
			    if(!newInfo[0].contentEquals(localHost.getLogin())) {
			    	RemoteUser newUser = new RemoteUser(newInfo[0],address);
			    	synchronized(this){
			    		localHost.addUser(newUser);
			    	}
			    }
			    response = new byte [65535];
				RespondingPacket = new DatagramPacket(response,response.length);								
			    senderSocket.setSoTimeout(1500);
			    senderSocket.receive(RespondingPacket);
			    received = new String(RespondingPacket.getData(), 0, RespondingPacket.getLength());
		    }
			
		}catch(IOException e) {	}
		
	}

	
	public void notifyDisconnection(){
		try {
			byte[] buf = new byte[65535];
			String infos = localHost.getLogin() + " logoff " + localHost.getIpAddress().getHostAddress() + " " + localHost.getServerPort() ;  
			buf = infos.getBytes();
			DatagramPacket packet = new DatagramPacket(buf,buf.length,broadcastAddr,BroadcastServer.BROADCAST_PORT);
			senderSocket.send(packet);
		} catch(Exception e) {
			 
    		System.out.println("Erreur de notification de deconnexion en raison de : \t " + e.getMessage());
    	
    	}
		close();
	}
	
	public boolean requestData(String log) {
		boolean unic = true ;
		try {
			byte[] buf = new byte[65535];
			System.out.println("Demande d'info");
			String infos = log + " request "  + "  this is a padding  ";  
			buf = infos.getBytes();
			DatagramPacket packet = new DatagramPacket(buf,buf.length,broadcastAddr,BroadcastServer.BROADCAST_PORT);
			senderSocket.send(packet);
			byte[] response = new byte [65535];
			DatagramPacket RespondingPacket = new DatagramPacket(response,response.length);		
		    senderSocket.setSoTimeout(3000);
			senderSocket.receive(RespondingPacket);
			String received = new String(RespondingPacket.getData(), 0, RespondingPacket.getLength());
		    
			if(RespondingPacket!=null) {
		    	unic = false ;
		    }
			System.out.println("Got received : " +received + unic );
		}catch(IOException e) {	
			unic = true ;
		}
		close();
		return unic ;
	}
	
	public void close() {
		senderSocket.close();
	}
}			    