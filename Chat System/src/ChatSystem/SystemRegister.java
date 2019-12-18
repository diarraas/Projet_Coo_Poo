package ChatSystem;
import java.net.*;
import java.util.* ;
public class SystemRegister {
	 	public static List<Profile> onliners ;
	    
	    public static List<Profile> users ;
	    
	    public static List<Message> user_messages ;
	    
	    public static void update_users(Profile user){
	    
	    }
	    
	    public static void update_online_users (Profile user){
	    	
	    }
	    
	    public static void update_messages(Message msg) {
	    	
	    }
	    
	    public static void add_user (Profile user){
	        users.add(user);
	    }
	    
	    public static void add_online_user(Profile user){
	    	if(!onliners.contains(user)) onliners.add(user);
	    }
	    
	    public static Profile findProfileByLogin(String log){
            Profile temp = null ;
	    	boolean found = users.get(0).getLogin().equalsIgnoreCase(log) ;
	        if(!found){
	            ListIterator<Profile> iterator = users.listIterator(1) ;
	            while(!found && iterator.hasNext()){
	                temp = iterator.next() ;
	                found = temp.getLogin().equalsIgnoreCase(log) ;
	            }
	        }
	    	return temp ;
	    }
	    
	    public static void remove_user(Profile user, List<Profile> list){
	    	Profile temp = null ;
		    boolean found = false;
		       if(!found){
		           ListIterator<Profile> iterator = users.listIterator(1) ;
		           while(!found && iterator.hasNext()){
		               temp = iterator.next() ;
		           }
		      }
	    }
	    
	    public static boolean verify_unicity(String login){
	    	boolean not_unic = false ;
	    	if(users.size() != 0) {
		    	not_unic = users.get(0).getLogin().equalsIgnoreCase(login) ;
		        if(not_unic){
		            ListIterator<Profile> iterator = users.listIterator(1) ;
		            Profile temp = null ;
		            while(not_unic && iterator.hasNext()){
		                temp = iterator.next() ;
		                not_unic = temp.getLogin().equalsIgnoreCase(login) ;
		            }
		        }
	        }
	        
	    	return !not_unic ;        
	    }
	    
	    public static Profile findProfileByAddress (InetAddress addr) {
	    	return null ;
	    } 
}
