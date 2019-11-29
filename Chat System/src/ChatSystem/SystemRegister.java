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
	        boolean is_unique = users.get(0).getLogin().equalsIgnoreCase(login) ;
	        if(is_unique){
	            ListIterator<Profile> iterator = users.listIterator(1) ;
	            Profile temp = null ;
	            while(is_unique && iterator.hasNext()){
	                temp = iterator.next() ;
	                is_unique = temp.getLogin().equalsIgnoreCase(login) ;
	            }
	        }
	        return is_unique ;        
	    }
	    
}
