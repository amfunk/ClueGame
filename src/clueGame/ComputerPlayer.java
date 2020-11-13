package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {

	Board board = Board.getInstance();

	private Random num = new Random();

	public ComputerPlayer() {
		
	}

	public ComputerPlayer(String name, int row, int column, String color) {
		this.setName(name);
		this.row = row;
		this.column = column;
		if (color.equals("Yellow")) {
			this.setColor(Color.yellow);
		} else if (color.equals("Green")) {
			this.setColor(Color.green);
		} else if (color.equals("Blue")) {
			this.setColor(Color.cyan);
		} else if (color.equals("Red")) {
			this.setColor(Color.red);
		} else if (color.equals("Purple")) {
			this.setColor(Color.magenta);
		} else if (color.equals("White")) {
			this.setColor(Color.white);
		}
	}

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

	@Override
	public BoardCell selectTargets() {
		List<BoardCell> targets = new ArrayList<>();
		Set<BoardCell> targetList = board.getTargets();
		int index;
		int counter = 0;
		for (BoardCell cell : targetList) {
			if (cell.isRoomCenter() && !this.getSeenRooms().contains(cell.getRoom())) {
				targets.add(cell);
			}
		}
		if (!targets.isEmpty()) {
			Collections.shuffle(targets);
			return targets.get(0);
		} else {
			index = num.nextInt(targetList.size());
			for (BoardCell cell : targetList) {
				if (counter == index) {
					return cell;
				}
				counter++;
			}
		}
		return null;
	}
}
