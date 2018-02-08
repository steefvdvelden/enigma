package Enigma_machine;

public class Reflector extends Encoding_part {
	String reflector = "ABCDEFGDIJKGMKMIEBFTCVVJAT";					// Test 1     --- De reflector is van te voren vastgelegd.

	public int encode(int input_position, boolean rollover){			// Test 1
		char reflector_in = reflector.charAt(input_position);
		int first = reflector.indexOf(reflector_in);
		int last = reflector.lastIndexOf(reflector_in);
		if (input_position == first)
			return last;
		else 
			return first;
	}
	public String get_window(){											// Test 5
		return "";
	}
}
