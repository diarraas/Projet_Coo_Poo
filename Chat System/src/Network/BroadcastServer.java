package Network;

import java.net.*;

import Data.LocalUser;

public class BroadcastServer extends Thread {
	
    public static int BROADCAST_PORT = 4445 ;
    private DatagramSocket broadcastSocket;
	private byte[] buf = new byte[256];
	private LocalUser localHost ;
	private boolean running ;

	public BroadcastServer(LocalUser user) {
		localHost = user ;
		try{
			
			broadcastSocket = new DatagramSocket(BROADCAST_PORT);
			
		}catch(Exception e){
			
    		System.out.println("Erreur de création du serveur UDP en raison de : \t " + e.getMessage());
			
		}
	}
	
	public void run() {
		while(isRunning()){
			try{
	        	DatagramPacket packet = new DatagramPacket(buf, buf.length);
	        	broadcastSocket.receive(packet);
	            String received = new String(packet.getData(), 0, packet.getLength());
	          
	         	if(received.contentEquals("login")) {
	         		InetAddress address = packet.getAddress();
		            int port = packet.getPort();
	         		packet = new DatagramPacket(buf, buf.length, address, port);
		            broadcastSocket.send(packet);
		            System.out.println("Nouvelle connexion, maj de la liste des onliners\t" );
		            localHost.addUser(null);
	         	}else if(received.contentEquals("off")) {
		           	System.out.println("Nouvelle deconnexion, maj de la liste des onliners\t");   	
		            localHost.removeUser(null);
		        }else if(received.contentEquals("change")) {
		           	System.out.println("Changement de login retenu");
		           	
		        }
         	
	         	broadcastSocket.close();
     	
			}catch(Exception e){
	    		System.out.println("Erreur de lancement du serveur UDP en raison de : \t " + e.getMessage());
			}
		}
     }
		

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
}