package clueGame;

public class Solution {
	public Card person = new Card();
	public Card room = new Card();
	public Card weapon = new Card();
	
	private static Solution theAnswer = new Solution();
	
	public Solution() {
		
	}
	
	public static Solution getAnswer() {
		return theAnswer;
	}
}
