package Network;

import java.net.*;

import Data.LocalUser;
import Data.RemoteUser;

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
			
    		System.out.println("Erreur de cr�ation du serveur UDP en raison de : \t " + e.getMessage());
			
		}
	}
	
	public void run() {
		while(isRunning()){
			try{
	        	DatagramPacket packet = new DatagramPacket(buf, buf.length);
	        	broadcastSocket.receive(packet);
	            String received = new String(packet.getData(), 0, packet.getLength());
	            String[] infos = received.split(" ");
	         	if(infos[1].contentEquals("login")) {
	         		InetAddress address = packet.getAddress();
		            int port = packet.getPort();
	         		packet = new DatagramPacket(buf, buf.length, address, port);
		            broadcastSocket.send(packet);
		            address = InetAddress.getByName(infos[2]);
		            port = Integer.parseInt(infos[3].trim());
		            System.out.println("Nouvelle connexion, maj de la liste des onliners\t" );
		            localHost.addUser(new RemoteUser(infos[0],address,port));
		            System.out.println("New onliners list \t" + localHost.getOnliners().toString() );
	         	}else if(infos[1].contentEquals("logoff")) {
		           	System.out.println("Nouvelle deconnexion, maj de la liste des onliners\t");   	
		            localHost.removeUser(localHost.findUserByLogin(infos[0]));
		            System.out.println("New onliners list \t" + localHost.getOnliners().toString() );

		        }else if(infos[1].contentEquals("change")) {
		           	System.out.println("Changement de login retenu, new login is \t"+ infos[0]);
		           	localHost.findUserByAddress(InetAddress.getByName(infos[2])).setLogin(infos[0]);
		            System.out.println("New onliners list \t" + localHost.getOnliners().toString() );

		        }   	
		        
			}catch(Exception e){
	    		System.out.println("Erreur de lancement du serveur UDP en raison de : \t " + e.getMessage());
			}
		}
		broadcastSocket.close();

     }
		

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
}