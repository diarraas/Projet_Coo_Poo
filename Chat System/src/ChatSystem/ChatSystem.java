package ChatSystem;

public class ChatSystem {

	public static void main(String[] args) {
		try{
			Profile user = Profile.create_account();
			user.authentify();
			user.send_message("e");
			user.end_session();
	    }catch(Exception e){
	       
	    }
	}

}
