package clueGame;

import java.awt.Color;

public class HumanPlayer extends Player {

public HumanPlayer() {
		
	}

	public HumanPlayer(String name, int row, int column, String color) {
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
		return null;
	}

	@Override
	public BoardCell selectTargets() {
		// TODO Auto-generated method stub
		return null;
	}

}
