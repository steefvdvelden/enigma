package Enigma_machine;

public class Input_Output extends Encoding_part {
	Input_Output(){	}
	
	Input_Output(String types, String window){									// Test 5
		part_to_left = connect_parts(types, window);
	}
	public int encode(int input_position, boolean rollover) {					// Test 6
		return part_to_left.encode(input_position, rollover);
	}
	public String get_window(){													// Test 5
		return part_to_left.get_window();
	}
	public Encoding_part connect_parts(String types, String window){			// Test 5
		Encoding_part left_part;
		int lengte = types.length();
		if (lengte == 1) 	
			left_part = new Reflector();
		else 				
			left_part = connect_parts(types.substring(0, lengte-1),window.substring(0, lengte-1));
		return new Rotor(types.substring(lengte-1), window.substring(lengte-1) , left_part);
	}
	public String encode(String input_string){							// Test 7				
		String result = "";
		int input_position;
		int output_position;
		for (int i=0; i < input_string.length(); i++){
			if (input_string.charAt(i) == ' ') 
				result += " ";
			else {
				input_position = links.indexOf(input_string.charAt(i));
				output_position = encode(input_position, true);
				result += links.substring(output_position,output_position+1);						// Test 6
			}
		}
		return result;
	}
}
