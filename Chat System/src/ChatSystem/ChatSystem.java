package ChatSystem;


public class ChatSystem {

	public static void main(String[] args) throws InterruptedException {
			Profile user = Profile.create_account();
			user.authentify();
			System.out.println(SystemRegister.users.toString());
			user.send_message(user.getLogin());
			Thread.sleep(2000);
			System.out.println(SystemRegister.user_messages.toString());
			user.end_session();
			user.send_message(user.getLogin());

	}

}
