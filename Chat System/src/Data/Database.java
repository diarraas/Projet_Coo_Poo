package Data;

import java.net.InetAddress;
import java.sql.*;
import java.text.SimpleDateFormat;
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
	
	public static boolean isUnic (String newLogin) {
		boolean isUnic = true;
		try {
			Connection con = startNewConnection();
			Statement stmt = con.createStatement();
			ResultSet set = stmt.executeQuery("SELECT COUNT(*) AS count FROM User WHERE login = \'" +newLogin+"\'" );
			set.next();
			if(set.getInt("count") !=0) {
				isUnic = false ;
			}
			set.close();
			con.close();
		}catch(Exception e) {
			System.out.println("Erreur de connection à la BDD en raison de \t" + e.getMessage());
			e.printStackTrace();
		}
		return isUnic ;
	}
	
	public static boolean updateLogin(User user,String newLogin) {
		boolean changed = false ;
		try {
			if(isUnic(newLogin)) {
				Connection con = startNewConnection();
				String query = "UPDATE User SET login = ? WHERE ipAddress LIKE ?";
			    PreparedStatement preparedStmt = con.prepareStatement(query);
			    preparedStmt.setString(1, newLogin);
			    preparedStmt.setString(2, user.getIpAddress().toString());
			    preparedStmt.executeUpdate();
				changed = true ;
				con.close();
			}
		}catch(Exception e) {
			System.out.println("Erreur de connection à la BDD en raison de \t" + e.getMessage());
			e.printStackTrace();
		}
		return changed ;
	}
	
	
	public static void addMessage(Message message) {
		int idSender = findId(message.getExp());
		int idRecipient = findId(message.getDest());
		int newId = findLastMessageId();
		try {
			Connection con = startNewConnection();
			String query = "INSERT INTO Message (exp,dest,date,body,id)" + " VALUES (?,?,?,?,?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, idSender);
			preparedStmt.setInt(2, idRecipient);
			java.sql.Date date = new Date(new SimpleDateFormat("dd/mm/yyyy hh:mm").parse(message.getDate()).getTime() );
			preparedStmt.setDate(3, date);
			preparedStmt.setString(4, message.getBody());
			preparedStmt.setInt(5, newId);
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
			int idSender = findId(sender);
			int idRecipient = findId(recipient);
			if(idSender == 0 || idRecipient == 0 ) return null ;
			ResultSet set1 = stmt.executeQuery("SELECT * FROM Message WHERE exp ="+idSender+" AND dest ="+idRecipient);
			ResultSet set2 = stmt.executeQuery("SELECT * FROM Message WHERE exp ="+idRecipient+" AND dest ="+idSender);
			System.out.println("");
			while (set1.next()) {
				retrieved.add(new Message(findLogin(set1.getInt("exp")),findLogin(set1.getInt("dest")),set1.getString("body")));
			}
			
			while (set2.next()) {
				retrieved.add(new Message(findLogin(set2.getInt("exp")),findLogin(set2.getInt("dest")),set2.getString("body")));
			}
			set1.close();
			set2.close();
			con.close();
		}catch(Exception e) {
			System.out.println("Erreur de connection à la BDD en raison de \t" + e.getMessage());
			e.printStackTrace();
		}
		return retrieved ;
	}
	
	public static String findLogin(int id) {
		String result = "";
		try {
			Connection con = startNewConnection();
			Statement stmt = con.createStatement();
			ResultSet set = stmt.executeQuery("SELECT login FROM User WHERE id = "+id);
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
	
	public static int findId(String login) {
		int id = 0 ;
		try {
			Connection con = startNewConnection();
			Statement stmt = con.createStatement();
			ResultSet set = stmt.executeQuery("SELECT id FROM User WHERE login = \'" +login+"\'");
			if(set.next()) {
				id = set.getInt("id");
			}
			set.close();
			con.close();
		}catch(Exception e) {
			System.out.println("Erreur de connection à la BDD en raison de \t" + e.getMessage());
			e.printStackTrace();
		}
		return id ;
	} 
	
	
	public static int findLastMessageId() {
		int id = 0 ;
		try {
			Connection con = startNewConnection();
			Statement stmt = con.createStatement();
			ResultSet set = stmt.executeQuery("SELECT MAX(id) FROM Message");
			if(set.next()) {
				id = set.getInt("id");
			}
			set.close();
			con.close();
		}catch(Exception e) {
			System.out.println("Erreur de connection à la BDD en raison de \t" + e.getMessage());
			e.printStackTrace();
		}
		return id ;
	} 
	
	public static LocalUser findUser(String login) {
		LocalUser localHost = null ;
		try {
			Connection con = startNewConnection();
			Statement stmt = con.createStatement();
			ResultSet set = stmt.executeQuery("SELECT * FROM User WHERE login = \'" +login+"\'");
			if(set.next()) {
				localHost = new LocalUser();
				localHost.setId(set.getInt("id"));
				localHost.setLogin(set.getString("login"));
				localHost.setIpAddress(InetAddress.getByName(set.getString("ipAddress")));
			}
			set.close();
			con.close();
		}catch(Exception e) {
			System.out.println("Erreur de connection à la BDD en raison de \t" + e.getMessage());
			e.printStackTrace();
		}
		
		return localHost ;
	}
	
	public static boolean addUser(User user) {
		boolean added = false ;
		try {
			Connection con = startNewConnection();
			if(isUnic(user.getLogin())) {
				String query = "INSERT INTO User (id,login,ipAddress)" + " VALUES (?,?,?)";
			    PreparedStatement preparedStmt = con.prepareStatement(query);
			    preparedStmt.setInt(1, user.getId());
			    preparedStmt.setString(2, user.getLogin());
			    preparedStmt.setString(3, user.getIpAddress().getHostAddress());
			    preparedStmt.execute();
			    added = true;
			    con.close();
			}
		}catch(Exception e) {
			System.out.println("Erreur de connection à la BDD en raison de \t" + e.getMessage());
			e.printStackTrace();
		}
		return added ;
	}
	
	
}
