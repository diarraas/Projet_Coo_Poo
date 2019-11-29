package ChatSystem;
import java.util.* ;
public class SystemRegister {
	 public static List<Profile> onliners ;
	    
	    public static List<Profile> users ;
	    
	    public static void update_users(Profile user){
	        remove_user(user, users) ;
	    
	    }
	    
	    public static void update_online_users (){}
	    
	    public static void add_user (Profile user){
	        users.add(user.getId(), user);
	    }
	    
	    public static void add_online_user(Profile user){
	        onliners.add(user);
	    }
	    
	    public static void remove_user(Profile user, List<Profile> list){
	        list.remove(user.getId());
	    }
	    
	    public static boolean verify_unicity(String login){
	        boolean not_unic = users.get(0).getLogin().equalsIgnoreCase(login) ;
	        if(not_unic){
	            ListIterator<Profile> iterator = users.listIterator(1) ;
	            Profile temp = null ;
	            while(not_unic && iterator.hasNext()){
	                temp = iterator.next() ;
	                not_unic = temp.getLogin().equalsIgnoreCase(login) ;
	            }
	        }
	        return !not_unic ;        
	    }
	    
}
