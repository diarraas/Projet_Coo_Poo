package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.text.DateFormat;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import com.sun.jdi.connect.spi.Connection;

import Data.*;

class DatabaseTest {
	LocalUser exemple1 ;
	LocalUser exemple1bis ;
	LocalUser exemple2 ;
	Message msg1, msg2,msg3 ;
	Connection con ;
	Statement stmt ;
	ResultSet set ;
	
	@Before
	void init() throws IOException{
		exemple1 = new LocalUser("");
		exemple1.setIpAddress(InetAddress.getLocalHost());
		exemple1.setLogin("exemple1");
		
		exemple1bis = new LocalUser();
		exemple1bis.setIpAddress(InetAddress.getLocalHost());
		exemple1bis.setLogin("exemple1bis");
		
		exemple2 = new LocalUser();
		exemple2.setIpAddress(InetAddress.getLocalHost());
		exemple2.setLogin("exemple avec espace");
		
		msg1 = new Message("exemple2","exemple1bis",(DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT)).format(new Date(System.currentTimeMillis())), "exemple de message");
		msg2 = new Message("exemple2","exemple1bis",(DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT)).format(new Date(System.currentTimeMillis())), "exemple de message avec caractère spécial\' !");
		msg3 = new Message("exemple1bis","exemple2",(DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT)).format(new Date(System.currentTimeMillis())), "");

	}
	
	@Test
	void testStartNewConnection() {
		Database.startNewConnection();
		System.out.println("CONNEXION OK");
	}
	
	@Test
	void testAddUser() throws SQLException, IOException {
		Database.addUser(exemple1bis);
		Database.addUser(exemple2);
		con = (Connection) Database.startNewConnection();
		stmt = ((java.sql.Connection) con).createStatement();
		set = stmt.executeQuery("SELECT * FROM User");
		set.next();
		assertTrue(set.getString("login").contentEquals(exemple1bis.getLogin()) ||(set.getString("login").contentEquals(exemple2.getLogin())));
		con.close();
		stmt.close();
		set.close();
		
	}
	

	@Test
	void testIsSaved() throws UnknownHostException {
		/**			Cas:normal		**/
		assertTrue(Database.isSaved(InetAddress.getLocalHost().getHostAddress()));
		assertFalse(Database.isSaved("10.12.14.13"));
		
		/**		Pas une adresse		**/
		assertFalse(Database.isSaved("pas une adresse"));
		assertFalse(Database.isSaved(null));

	}
	
	@Test
	void testFindLogin() throws UnknownHostException {
		/**		Cas : null		**/
		assertTrue(Database.findLogin("")== null);
		
		/**		Cas : normal	**/
		assertTrue(Database.findLogin(InetAddress.getLocalHost().getHostAddress()).contentEquals("exemple1bis")||Database.findLogin(InetAddress.getLocalHost().getHostAddress()).contentEquals("exemple2"));
	
		/**		Cas : pas une adresse		**/
		assertTrue(Database.findLogin("pas une adresse ip")== null);
	}
	
	@Test
	void testFindIpAddress() throws UnknownHostException {
		assertTrue(Database.findIpAddress("exemple1bis").contentEquals(Database.findIpAddress("exemple2")) && Database.findIpAddress("exemple2").contentEquals(InetAddress.getLocalHost().getHostAddress()));
	}
	
	@Test
	void testUpdateLogin() throws SQLException, IOException {
		/** 	Pas ajouté à la BDD		**/
		assertFalse(Database.updateLogin(exemple1.getIpAddress().getHostAddress(), "NouveauLoginExemple1"));
		
		
		/**		Après ajout à la BDD 	**/
		assertTrue(Database.updateLogin(exemple1bis.getIpAddress().getHostAddress(), "NouveauLoginExemple1"));
	
		/**		Verification changement 	**/
		con = (Connection) Database.startNewConnection();
		stmt = ((java.sql.Connection) con).createStatement();
		set = stmt.executeQuery("SELECT ipAddress FROM User WHERE login =\'" + "NouveauLoginExemple1"+"\'");
		assertTrue(set.next());
		con.close();
		stmt.close();
		set.close();
		
	}

	@Test
	void testAddMessage() {
		Database.addMessage(msg1);
		Database.addMessage(msg2);
		Database.addMessage(msg3);
	}

	@Test
	void testGetHistory() {
		fail("Not yet implemented");
	}


}
