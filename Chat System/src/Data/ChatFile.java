package Data;

import java.io.Serializable;

public class ChatFile implements Serializable {

	/**
	 * 	Classe qui encapsule un fichier pour faciliter son envoi
	 * */
	
	private static final long serialVersionUID = 1L;
	private String name ;
	private byte[] content ;
	
	public ChatFile(String name, byte[] content) {
		this.name = name ;
		this.content = content ;
	}

	public String getName() {
		return name;
	}

	public byte[] getContent() {
		return content;
	}	
	
	

}
