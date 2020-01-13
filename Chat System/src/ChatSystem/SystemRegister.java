package ChatSystem;
import java.net.*;
import java.util.* ;
public class SystemRegister {
	 	public static List<Profile> onliners = new ArrayList<Profile>() ;
	    
	    public static List<Profile> users = new ArrayList<Profile>();
	    
	    public static List<Message> user_messages  = new ArrayList<Message>() ;
	    
	    public static void update_messages(Message msg) {
	    	user_messages.add(msg);
	    }
	    
	    public static List<Message> retrieve_messages(Profile exp, Profile dest){
	    	List<Message> retrieved = new ArrayList<Message>();
	    	ListIterator<Message> iterator = user_messages.listIterator() ;
	    	Message current_msg ;
	        while(iterator.hasNext()){
	        	current_msg = iterator.next();
	        	if(current_msg.exp == exp.getIp_address() && current_msg.dest == dest.getIp_address()) retrieved.add(current_msg);
	        }
	    	return retrieved ;
	    }
	    
	    public static void add_user (Profile user){
	    	if(users.size() != 0 && !users.contains(user)) users.add(user);
	    	if(users.size() == 0) users.add(user);
	    }
	    
	    public static void add_online_user(Profile user){
	    	if(onliners.size() != 0 && !onliners.contains(user)) onliners.add(user);
	    	if(onliners.size() == 0) onliners.add(user);
	    }
	    
	    public static Profile findProfileByLogin(String log){
            Profile temp = null ;
	    	boolean found = false ;
	        ListIterator<Profile> iterator = users.listIterator() ;
	        System.out.println("Begin research by login...");
	        while(iterator.hasNext() && !found){
	        	temp = iterator.next() ;
	            found = temp.getLogin().equalsIgnoreCase(log) ;
	        }
	        if(temp == null || !found)	return null ;
	    	return temp ;
	    }
	    
	    public static void remove_user(Profile user, List<Profile> list){
	    	if(list.size() != 0) list.remove(user);
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
	    	Profile temp = null ;
	    	boolean found = false ;
	        ListIterator<Profile> iterator = users.listIterator() ;
	        while(!found && iterator.hasNext()){
	        	temp = iterator.next() ;
	            found = temp.getIp_address() == addr ;
	        }
	    	return temp ;
	    } 
}
