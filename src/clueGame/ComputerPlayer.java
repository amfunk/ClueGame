package clueGame;

public class ComputerPlayer extends Player {
	
	@Override
	public Solution createSuggestion() {
		return new Solution();
		
	}
	
	public BoardCell selectTargets() {
		return new BoardCell(0, 0);
	}
}
