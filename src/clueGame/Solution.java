package clueGame;

public class Solution {
	public Card person;
	public Card room;
	public Card weapon;
	
	private static Solution theAnswer = new Solution();
	
	private Solution() {
		
	}
	
	public static Solution getAnswer() {
		return theAnswer;
	}
}
