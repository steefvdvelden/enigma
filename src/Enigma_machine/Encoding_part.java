package Enigma_machine;

public abstract class Encoding_part {						// Refactoring gedaan met Test 5

	public String links = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";	
	public String rechts = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";												
	public Encoding_part part_to_left = null;
	
	public abstract int encode(int input_position, boolean rollover);
	public abstract String get_window();
}
