package Data;

import java.io.Serializable;
import java.util.List;

public class UserList implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<RemoteUser> userList ;
	
	
	public UserList(List<RemoteUser> users) {
		userList = users;	
	}

	public List<RemoteUser> getUserList() {
		return userList;
	}
	
}
