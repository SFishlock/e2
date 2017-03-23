package network;


/**
 * This class is to solve messages
 * @author Weifeng
 */
public class Message {
	private final String text;
	
	public Message(String _text){
		this.text = _text;
	}
	
	public String getMessage(){
		return this.text;
	}
}
