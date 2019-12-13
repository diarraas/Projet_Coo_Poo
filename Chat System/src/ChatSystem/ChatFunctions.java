//package ChatSystem;

public interface ChatFunctions {
	
	public Profile create_account() ;
    
    public void change_login () ;
    
    public void authentify() ;
    
    public void show_users();
    
    public void send_message(Profile dest);
    
    public void end_session();
}
