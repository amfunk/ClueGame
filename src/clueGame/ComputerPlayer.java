package clueGame;

import java.util.Random;

public class ComputerPlayer extends Player {

	Board board = Board.getInstance();

	private Random num = new Random();


	@Override
	public Solution createSuggestion() {
		Solution suggestion = new Solution();
		Room room;
		int index = 0;
		Card card;
		do {
			index = num.nextInt(board.getDeck().size());
			card = board.getDeck().get(index);
		} while (!card.getType().equals(CardType.PERSON) || this.getSeenCards().contains(card) || this.getHand().contains(card));
		suggestion.person = card;
		do {
			index = num.nextInt(board.getDeck().size());
			card = board.getDeck().get(index);
		} while (!card.getType().equals(CardType.WEAPON) || this.getSeenCards().contains(card) || this.getHand().contains(card));
		suggestion.weapon = card;

		room = board.getCell(this.row, this.column).getRoom();
		for (int i = 0; i < board.getDeck().size(); i++) {
			if (board.getDeck().get(i).getCardName().equals(room.getName())) {
				suggestion.room = board.getDeck().get(i);
			}
		}
		return suggestion;
	}

	public BoardCell selectTargets() {
		return new BoardCell(0, 0);
	}
}
