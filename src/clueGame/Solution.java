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
	
	public boolean equals(Solution solution) {
		if (this.person.equals(solution.person) && this.room.equals(solution.room) && this.weapon.equals(solution.weapon)) {
			return true;
		} else {
			return false;
		}
	}
}
