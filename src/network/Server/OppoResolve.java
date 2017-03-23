package network.Server;

import engine.GameObject;

/**
 * This class is used to determine what action should be done after received the command
 * @author Weifeng
 */
public class OppoResolve {
	
	private OppoAction action;
	
	public OppoResolve(GameObject _gameObject,Player _opponent,Player _me){
		this.action = new OppoAction(_gameObject,_opponent,_me);
	}
	
	
	public void resolve(String _readline){
	
		if(_readline.length() >= 5)	{
			String keyword = _readline.substring(0,5);

			if       (keyword.equals("NAME:")){
				action.setName(_readline.substring(5));
			}else if (keyword.equals("READ:")){
				action.setReady();
			}else if (keyword.equals("SCOR:")){
				action.updateScore(_readline.substring(5));
			}else if (keyword.equals("OVER:")){
				action.gameOver();
			}else if (keyword.equals("PREE:")){
				action.receivedPressedKey(_readline.substring(5));
			}else if (keyword.equals("RELE:")){
				action.receivedReleasedKey(_readline.substring(5));
			}else if (keyword.equals("POWE:")){
				action.receivedPower(_readline.substring(5));
			}else if (keyword.equals("COMB:")){
				action.receivedCombo(_readline.substring(5));
			}else if (keyword.equals("TEXT:")){
				action.receivedText(_readline.substring(5));
			}
			
			else {
				action.invalidMsg();
			}
		}
		
	}
}
