//package ChatSystem;

import java.io.*;


public class ChatSystem {

	public static void main(String[] args) {
		try{
			Profile user = Profile.create_account();
			Thread.sleep(5000);
			user.authentify();
	    }catch(Exception e){
	       
	    }
	}

}
