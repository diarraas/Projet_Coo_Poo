package Data;

import java.sql.*;
import java.util.ArrayList;

public class Database {
	private static String login = "tpservlet_09";
	private static String psw = "fee6Ooth";
	private static String url = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/"+login;
	
	
	public static Connection startNewConnection() {
		Connection con = null ;
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			con= DriverManager.getConnection(url,login,psw);  
		}catch(Exception e) {
			System.out.println("Erreur de connection à la BDD en raison de \t" + e.getMessage());
			e.printStackTrace();
		}
		return con ; 
	}

	
	public static boolean updateLogin(String userAddress,String newLogin) {
		boolean changed = false ;
		try {
			Connection con = startNewConnection();
			String query = "UPDATE User SET login = ? WHERE ipAddress LIKE ?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, newLogin);
			preparedStmt.setString(2, userAddress);
			preparedStmt.executeUpdate();
			changed = true ;
			con.close();
		}catch(Exception e) {
			System.out.println("Erreur de connection à la BDD en raison de \t" + e.getMessage());
			e.printStackTrace();
		}
		return changed ;
	}
	
	
	public static void addMessage(Message message) {
		String ipSender = findIpAddress(message.getExp());
		String ipRecipient = findIpAddress(message.getDest());
		try {
			Connection con = startNewConnection();
			String query = "INSERT INTO Message (exp,dest,date,body)" + " VALUES (?,?,?,?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, ipSender);
			preparedStmt.setString(2, ipRecipient);
			preparedStmt.setString(3, message.getDate());
			preparedStmt.setString(4, message.getBody());
			preparedStmt.execute();
			con.close();
			
		}catch(Exception e) {
			System.out.println("Erreur de connection à la BDD en raison de \t" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//Combine results and order by date
	public static ArrayList<Message> getHistory(String sender, String recipient){
		ArrayList<Message> retrieved = new ArrayList<Message>();
		try {
			Connection con = startNewConnection();
			Statement stmt = con.createStatement();
			String ipSender = findIpAddress(sender);
			String ipRecipient = findIpAddress(recipient);
			ResultSet set = stmt.executeQuery("SELECT DISTINCT * FROM Message WHERE ( exp =\'"+ipSender+"\' AND dest =\'"+ipRecipient+"\') OR ( exp =\'"+ipRecipient+"\' AND dest =\'"+ipSender+"\')" );
			while (set.next()) {
				retrieved.add(new Message(findLogin(set.getString("exp")),findLogin(set.getString("dest")),set.getString("date"),set.getString("body")));
			}
			set.close();
			con.close();
		}catch(Exception e) {
			System.out.println("Erreur de connection à la BDD en raison de \t" + e.getMessage());
			e.printStackTrace();
		}
		return retrieved ;
	}
	
	public static String findLogin(String ip) {
		String result = null;
		try {
			Connection con = startNewConnection();
			Statement stmt = con.createStatement();
			ResultSet set = stmt.executeQuery("SELECT login FROM User WHERE ipAddress =\'" +ip+"\'");
			if(set.next()) {
				result = set.getString("login");
			}
			set.close();
			con.close();
		}catch(Exception e) {
			System.out.println("Erreur de connection à la BDD en raison de \t" + e.getMessage());
			e.printStackTrace();
		}
		return result ;
	}
	
	public static String findIpAddress(String login) {
		String ip = null ;
		try {
			Connection con = startNewConnection();
			Statement stmt = con.createStatement();
			ResultSet set = stmt.executeQuery("SELECT ipAddress FROM User WHERE login = \'" +login+"\'");
			if(set.next()) {
				ip = set.getString("ipAddress");
			}
			set.close();
			con.close();
		}catch(Exception e) {
			System.out.println("Erreur de connection à la BDD en raison de \t" + e.getMessage());
			e.printStackTrace();
		}
		return ip ;
	} 
	
	
	public static void addUser(User user) {
		try {
			if(!isSaved(user.getIpAddress().getHostAddress())){
				Connection con = startNewConnection();
				String query = "INSERT INTO User (login,ipAddress)" + " VALUES (?,?)";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				preparedStmt.setString(1, user.getLogin());
			    preparedStmt.setString(2, user.getIpAddress().getHostAddress());
			    preparedStmt.execute();
			    con.close();
			}else {
				updateLogin(user.getIpAddress().getHostAddress(),user.getLogin());
			}
		}catch(Exception e) {
			System.out.println("Erreur de connection à la BDD en raison de \t" + e.getMessage());
			e.printStackTrace();
		}
	}
	

	public static boolean isSaved(String ipAddress) {
		boolean result = false ;
		try {
			Connection con = startNewConnection();
			Statement stmt = con.createStatement();
			ResultSet set = stmt.executeQuery("SELECT COUNT(*) AS count FROM User WHERE ipAddress = \'" +ipAddress+"\'" );
			set.next();
			if(set.getInt("count") !=0) {
				result = true ;
			}
			set.close();
			con.close();
		}catch(Exception e) {
			System.out.println("Erreur de connection à la BDD en raison de \t" + e.getMessage());
			e.printStackTrace();
		}
		
		return result ;
	}
	
	
}
