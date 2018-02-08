package Enigma_machine;

import static org.junit.Assert.*;

import org.junit.Test;

public class TDD {
	private Input_Output enigma;
	
	@Test
	public void test_0(){ 											// We beginnen met het Input_Output deel
		Input_Output input_output = new Input_Output();
		input_output.part_to_left = new Mock_Machine();
		assertEquals(input_output.encode("E"),"Q");					
	}
	@Test
	public void test_1(){ 											// Dan de reflector maken
		Reflector reflector = new Reflector();
		assertEquals(reflector.encode(4, false),16);				// Reflector codering ligt vast
		assertEquals(reflector.encode(16, false),4);
	}
	@Test
	public void test_2(){ 											// Dan een rotor maken
		Rotor rotor = new Rotor();									// Om vervolgens de rotor rond te draaien 
		rotor.part_to_left = new Mock_Machine();					// Het window moet dan steeds één ophogen
		assertEquals(rotor.get_window(),"A");
		rotor.encode(1, true);
		assertEquals(rotor.get_window(),"B");
		rotor.encode(1, false);										// Nu mag de rotor niet draaien
		assertEquals(rotor.get_window(),"B");		
		for (int i = 2; i<26; i++){									// Een rondje draaien
			rotor.encode(1,true);
			assertEquals(rotor.get_window().charAt(0),"ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(i));
		}
		rotor.encode(1,true);										// Helemaal rond
		assertEquals(rotor.get_window(),"A");		
	}
	@Test
	public void test_3(){ 											// Rotortypes liggen vast en de startpositie moet kunnen worden bepaald
		Rotor rotor = new Rotor("1","K", null);							 
		rotor.part_to_left = new Mock_Machine();						
		assertEquals(rotor.get_window(),"K");
		assertEquals(rotor.rechts,"EKMFLGDQVZNTOWYHXUSPAIBRCJ");	
	}
	@Test
	public void test_4(){ 											// Een rotor laten coderen, maak gebruik van rotor_III met de 'K' in het window
		Rotor rotor = new Rotor("3","K",new Mock_Machine());		// De linker kant moet verbonden zijn met de rechterkant, volgens de codering
		assertEquals(rotor.verbind(false,5),20);					// positie 5 rechts is 'E', 'E' in de linkerkolom staat op pos 20
		assertEquals(rotor.verbind(true,14),4);						// positie 14 links is 'Y', rechts staat die op de positie 4
		rotor.encode(1, true);										// Draai de rotor een slag, 'L' staat nu in het window (=positie 0)
		assertEquals(rotor.verbind(false,5),23);					// positie 5 rechts is nu 'I', 'I' in de linkerkolom staat nu op pos 23
		assertEquals(rotor.verbind(true,14),1);						// positie 14 links is 'Z', rechts staat die op de positie 1
		rotor.encode(1, false);										// Encoderen zonder draaien
		assertEquals(rotor.verbind(false,5),23);					// positie 5 rechts is nu 'I', 'I' in de linkerkolom staat nu op pos 23
		assertEquals(rotor.verbind(true,14),1);						// positie 14 links is 'Z', rechts staat die op de positie 1
	}
	@Test
	public void test_5() { 											// Alles aan elkaar zetten
		enigma = new Input_Output("123", "MCK");					// Type rotors en startpositie moeten worden meegegeven
		assertEquals(enigma.get_window(),"MCK");					// Ga uit van de setup I-II-III en codering MCK
		enigma.encode("A");											// Kijken of het draaien werkt
		assertEquals(enigma.get_window(),"MCL");
		for (int i = 12; i < 22; i++){								// Rotor 2 tot aan het rollover punt draaien
			enigma.encode("A");
		}
		assertEquals(enigma.get_window(),"MCV");
		enigma.encode("A");											// Rollover op rotor 1
		assertEquals(enigma.get_window(),"MDW");
		for (int i = 23; i < 27; i++)								// rotor 2 tot aan de laatste positie + 1 draaien
			enigma.encode("A");
		assertEquals(enigma.get_window(),"MDA");
		for (int i = 0; i < 52; i++)								// rollover op rotor 0 (en dus twee keer op rotor 1)
			enigma.encode("A");
		assertEquals(enigma.get_window(),"NFA");
	}
	@Test
	public void test_6() { 											// Volledige codering van een letter
		enigma = new Input_Output("123", "MCK");
		assertEquals(enigma.encode("E"),"Q");						// Controleer de output
		assertEquals(enigma.get_window(),"MCL");
		assertEquals(enigma.encode("N"),"M");						// Volgende letter
		assertEquals(enigma.get_window(),"MCM");
	}
	@Test
	public void test_7(){			 								// Een string coderen
		enigma = new Input_Output("123", "MCK");
		assertEquals(enigma.encode("ENIGMA REVEALED"),"QMJIDO MZWZJFJR");
	}
	public class Mock_Machine extends Encoding_part {
		public int encode(int input_position, boolean rollover){
			return 16;
		}
		public String get_window() {
			return "";
		}
	}
	
}
