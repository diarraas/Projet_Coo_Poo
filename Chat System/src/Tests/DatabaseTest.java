package Tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.text.DateFormat;

import org.junit.jupiter.api.Test;

import Data.*;


/**                                                  /!\                         EXPLICATIONS                            /!\ 

 CONTEXT : Les tests les plus importants portent sur les methodes d'ajout, de mise à jour et de récupération. Les adresse ip de la BDD sont uniques, on ne peut donc pas
 ajouter plusieurs fois une même addresse. Une fois le changement de login effectué, il faut rajouter l'utilisateur ou mettre à jour ses informations dans la BDD pour qu'elle
 en tienne compte. Ceci explique pourquoi certains tests ne portent que sur une variable ou le fait qu'ils portent sur des constantes au lieu des attributs des variables 
 
  
*/




class DatabaseTest {
	LocalUser exemple1;
	LocalUser exemple1bis ;
	LocalUser exemple2 ;
	LocalUser exemple3 ;
	Message msg1, msg2,msg3 ;
	
	
	void init() throws IOException{
		exemple1 = new LocalUser();
		exemple1.setIpAddress(InetAddress.getLocalHost());
		exemple1.setLogin("exemple1");
		
		exemple1bis = new LocalUser();
		exemple1bis.setIpAddress(InetAddress.getLocalHost());
		exemple1bis.setLogin("exemple1bis");
		
		exemple2 = new LocalUser();
		
		exemple2.setIpAddress(InetAddress.getLocalHost());
		exemple2.setLogin("exemple avec espace");
		
	

		
		msg1 = new Message(exemple2.getLogin(),exemple1.getLogin(),(DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT)).format(new Date(System.currentTimeMillis())), "exemple de message");
		msg2 = new Message(exemple2.getLogin(),exemple1.getLogin(),(DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT)).format(new Date(System.currentTimeMillis())), "exemple de message avec caractère spécial\' !");
		msg3 = new Message(exemple2.getLogin(),exemple1.getLogin(),(DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT)).format(new Date(System.currentTimeMillis())), "");
		System.out.println("INITIALIZATION OK");
	}
	
	@org.junit.Test
	void testStartNewConnection() {
		Database.startNewConnection();
		System.out.println("CONNEXION OK");
	}
	
	@Test
	void testAddUser() throws SQLException, IOException {
		init();
		Database.addUser(exemple1bis);
		Database.addUser(exemple2);
		java.sql.Connection con = Database.startNewConnection();
		Statement stmt = con.createStatement();
		ResultSet set = stmt.executeQuery("SELECT * FROM User WHERE login =\'" + exemple2.getLogin() + "\'");
		set.next();
		assertTrue(set.getString("login").contentEquals("exemple avec espace"));
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
	void testFindLogin() throws IOException {
		init();
		/**		Cas : null		**/
		assertTrue(Database.findLogin("")== null);
		
		/**		Cas : normal	**/
		assertEquals("NouveauLoginExemple1",Database.findLogin(InetAddress.getLocalHost().getHostAddress()));
		assertFalse(Database.findLogin(InetAddress.getLocalHost().getHostAddress()).equals(exemple1bis.getLogin()));

	
		/**		Cas : pas une adresse		**/
		assertTrue(Database.findLogin("pas une adresse ip")== null);
	}
	
	@Test
	void testFindIpAddress() throws IOException {
		init();
		assertTrue(Database.findIpAddress("exemple1bis") == null && Database.findIpAddress("NouveauLoginExemple1").equals(InetAddress.getLocalHost().getHostAddress()));
	}
	
	@Test
	void testUpdateLogin() throws SQLException, IOException {
		init();
		/** 	Pas ajouté à la BDD		**/
		boolean obtained = Database.updateLogin("pas une adresse", "NouveauLoginExemple1");
		assertFalse(obtained);
		
		obtained = Database.updateLogin(exemple1bis.getIpAddress().getHostAddress(), "NouveauLoginExemple1");
		/**		Après ajout à la BDD 	**/
		assertTrue(obtained);
	
		/**		Verification changement 	**/
		java.sql.Connection con = Database.startNewConnection();
		Statement stmt = con.createStatement();
		ResultSet set = stmt.executeQuery("SELECT ipAddress FROM User WHERE login =\'" + "NouveauLoginExemple1"+"\'");
		assertTrue(set.next());
		con.close();
		stmt.close();
		set.close();
		
	}

	@Test
	void testAddMessage() throws IOException {
		init();
		Database.addMessage(msg1);
		Database.addMessage(msg2);
		Database.addMessage(msg3);
	}


}
