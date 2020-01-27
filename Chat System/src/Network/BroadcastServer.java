package Network;

import java.net.*;

import Data.ChatSession;
import Data.LocalUser;
import Data.RemoteUser;
import GraphicUserInterface.ChatWindow;

public class BroadcastServer extends Thread {
	
    public static int BROADCAST_PORT = 4445 ;
    private DatagramSocket broadcastSocket; 
	private LocalUser localHost ;
	private boolean running ;

	public BroadcastServer(LocalUser user) {
		localHost = user ;
		
		try{
			
			broadcastSocket = new DatagramSocket(BROADCAST_PORT);
			
		}catch(Exception e){
			
    		System.out.println("Erreur de creation du serveur UDP en raison de : \t " + e.getMessage());
			
		}
	}
	
	public void run() {
		while(isRunning()){
			try{
				byte[] buf = new byte[65535];
	        	DatagramPacket packet = new DatagramPacket(buf, buf.length);
	        	broadcastSocket.receive(packet);
	            String received = new String(packet.getData(), 0, packet.getLength());
	            String[] infos = received.split(" ");
	         	if(infos[1].contentEquals("login")) {
	         		InetAddress address = packet.getAddress();
		            int port = packet.getPort();
		            String myinfos = localHost.getLogin() + " login " + localHost.getIpAddress().getHostAddress() + " " + localHost.getServerPort() ;
		            buf = myinfos.getBytes();
	         		packet = new DatagramPacket(buf, buf.length, address, port);
		            broadcastSocket.send(packet);
		            address = InetAddress.getByName(infos[2]);	
	         		//ChatWindow.notificationMessage(infos[0] + " est connecté");   	
		            System.out.println("Nouvelle connexion, maj de la liste des onliners\t" );
		            RemoteUser newUser = new RemoteUser(infos[0],address);
		            synchronized(this){
		               localHost.addUser(newUser);
		            } 
		            System.out.println("New onliners list \t" + localHost.getOnliners().toString() );
	         	}else if(infos[1].contentEquals("logoff")) {
	         		ChatWindow.notificationMessage(infos[0] + " est deconnecté");   	
		            synchronized(this){
	         			localHost.removeUser(localHost.findUserByLogin(infos[0]));
		            }
	         		System.out.println("New onliners list \t" + localHost.getOnliners().toString() );

		        }else if(infos[1].contentEquals("change")) {
		        	ChatWindow.notificationMessage(infos[0] + " est maintenant "+ infos[3]);
		           	localHost.updateOnliners(infos[0],infos[3]);
		           	ChatSession session = (localHost.findSessionWith(infos[0]));
		           	if(session != null)
		           		session.setDest(infos[3]);
		            System.out.println("New onliners list \t" + localHost.getOnliners().toString() );
		            
		        }else if(infos[1].contentEquals("request")) {
		        	System.out.println("Demande d'information pour \t"+ infos[0]);
		        	if(localHost.findUserByLogin(infos[0]) != null) {
		        		InetAddress address = packet.getAddress();
			            int port = packet.getPort();
			            buf = "used".getBytes();
		         		packet = new DatagramPacket(buf, buf.length, address, port);
			            broadcastSocket.send(packet);
		        	}
		            
		        }
		        
			}catch(Exception e){
				if(!isRunning()) {
					System.out.println("Serveur arr�t�");
				}else {
					System.out.println("Erreur de lancement du serveur UDP en raison de : \t " + e.getMessage());
					e.printStackTrace();
				}
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