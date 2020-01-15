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
	
	public static boolean isUnic (String newLogin) {
		boolean isUnic = true;
		try {
			Connection con = startNewConnection();
			Statement stmt = con.createStatement();
			ResultSet set = stmt.executeQuery("SELECT Count(*) AS count FROM USER WHERE login LIKE " +newLogin);
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
		try {
			
			Connection con = startNewConnection();
			String query = "INSERT INTO Message (exp,dest,date,body)" + " VALUES (?,?,?,?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, idSender);
			preparedStmt.setInt(2, idRecipient);
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
			int idSender = findId(sender);
			int idRecipient = findId(recipient);
			ResultSet set = stmt.executeQuery("SELECT * FROM Message WHERE exp ="+idSender+" AND dest ="+idRecipient);
			
			set.close();
			con.close();
		}catch(Exception e) {
			System.out.println("Erreur de connection à la BDD en raison de \t" + e.getMessage());
			e.printStackTrace();
		}
		return retrieved ;
	}
	
	public static String findLogin() {
		String login = "" ;
		
		return login ;
	} 
	
	public static int findId(String login) {
		int id = 0 ;
		try {
			Connection con = startNewConnection();
			Statement stmt = con.createStatement();
			ResultSet set = stmt.executeQuery("SELECT id FROM User WHERE login LIKE "+login);
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
	
	public static boolean addUser(User user) {
		boolean added = false ;
		try {
			Connection con = startNewConnection();
			if(isUnic(user.getLogin())) {
				String query = "INSERT INTO User (id,login,ipAddress)" + " VALUES (?,?,?)";
			    PreparedStatement preparedStmt = con.prepareStatement(query);
			    preparedStmt.setInt(1, user.getId());
			    preparedStmt.setString(2, user.getLogin());
			    preparedStmt.setString(3, user.getIpAddress().toString());
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
