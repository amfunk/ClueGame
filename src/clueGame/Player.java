package clueGame;

import java.awt.Color;

public abstract class Player {

	private String name;
	private Color color;
	protected int row;
	protected int column;
	private Card[] hand;
	
	public boolean equals(Player target) {
		if (target.getName().equals(this.name) && target.getColor().equals(this.color)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void updateHand(Card card) {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Card[] getHand() {
		return hand;
	}

	public void setHand(Card[] hand) {
		this.hand = hand;
	}
}
