/*package input;

import java.util.Scanner;
import javax.swing.JFrame;

//a class for testing
public class Test {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		InputHandler inputHandler = new InputHandler();
		
		//frame.add(inputHandler);
		
		frame.setSize(500, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Scanner in = new Scanner(System.in);
		char input = ' ';
		
		//make sure input 4 keys for playing and 3 keys for powers
		while(!inputHandler.containAllKey()){
			inputHandler.removeAllKey();
			System.out.println("Input Key.");
			for(int i = 0; i < 4; i++){
				System.out.print("Input play Key" + i + ": ");
				input = in.next().charAt(0);
				inputHandler.storePlayKey(input);
			}
			for(int i = 0; i < 3; i++){
				System.out.print("Input power Key" + i + ": ");
				input = in.next().charAt(0);
				inputHandler.storePowerKey(input);
			}
		}
		
		//for testing
		System.out.print("Command:");
		input = in.next().charAt(0);
		while(!(input=='0')){
			switch(input){
				//check the playing key status
				case '1':
					for(int i=0; i<4; i++){
						System.out.print(inputHandler.playKey_Status(i) + " ");
					}
					System.out.println();
					break;
				//check the power key status	
				case '2':
					for(int i=0; i<3; i++){
						System.out.print(inputHandler.powerKey_Status(i) + " ");
					}
					System.out.println();
					break;
				//reset the power keys status
				case '3':
					inputHandler.resetPowerKeyStatus();
					break;
				//remove all the stored keys
				case '4':
					inputHandler.removeAllKey();
					break;
					
			}
			
			System.out.print("Command:");
			input = in.next().charAt(0);
		}
		
		
	}

}*/

