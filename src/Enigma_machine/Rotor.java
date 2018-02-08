package Enigma_machine;

public class Rotor extends Encoding_part {
	private static final String[] ROTOR_CODERING = {"EKMFLGDQVZNTOWYHXUSPAIBRCJ","AJDKSIRUXBLHWTMCQGZNPYFVOE","BDFHJLCPRTXVZNYEIWGAKMUSQO"};
	private static final int[] ROTOR_ROLLOVER_POINT = {17,5,22};					// Test 3
	private static final int ASCII_VALUE_0 = 48;									// Test 3
	private int rollover_point = -1;												// Test 5
	private boolean rollover_done = true;											// Test 5		
	private int window_position = 0;												// Test 2
	
	Rotor(){}															
	
	Rotor(String rotor_type, String start_positie, Encoding_part left_part){ 		// Test 3							
		int rotor_type_volgnummer = (int)rotor_type.charAt(0) - ASCII_VALUE_0 - 1;
		rechts = ROTOR_CODERING[rotor_type_volgnummer];
		rollover_point = ROTOR_ROLLOVER_POINT[rotor_type_volgnummer];				// Test 5
		window_position = links.indexOf(start_positie);		
		part_to_left = left_part;													// Test 5
	}
	public boolean rollover(){
		boolean result = (window_position == rollover_point) && (!rollover_done);
		rollover_done = true;
		return result;
	}
	public void rotate_rotor(){														// Test 2					
		window_position = (window_position +1) % 26;
		rollover_done = false;
	}
	public int verbind(Boolean left_to_right, int input_position){					// Test 4
		int position = (input_position + window_position) % 26;
		int result;
		if (left_to_right) 	result = (rechts.indexOf(links.charAt(position)) -  window_position + 26) % 26;
		else result = (links.indexOf(rechts.charAt(position)) -  window_position + 26) % 26;
		return result;
	}
	public int encode(int input_position, boolean rollover){						// Test 6
		if (rollover) 		rotate_rotor();
		return verbind(true, part_to_left.encode(verbind(false,input_position), rollover()));
	}
	public String get_window(){														// Test 2
		return part_to_left.get_window() + links.charAt(window_position);			// Test 2 aangepast voor test 5
	}
}
