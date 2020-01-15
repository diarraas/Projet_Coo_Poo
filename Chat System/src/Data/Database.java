package Data;

import java.sql.*;



public class Database {
	private static String login = "tpservlet_09";
	private static String psw = "fee6Ooth";
	private static String url = "mysql://srv-bdens.insa-toulouse.fr:3306";
	
	public static void launchDatabase() {
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con= DriverManager.getConnection("jdbc:"+url+"/"+login,login,psw);  
			Statement stmt=con.createStatement();

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		launchDatabase();
	}
}
