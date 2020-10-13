package clueGame;

public class BadConfigFormatException extends Exception {
	
	BadConfigFormatException() {
		super("Bad format: Error occurred");
	}
	
	BadConfigFormatException(String message) {
		super("Error occurred: " + message);
	}
}
