package clueGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.*;

public abstract class Player {

	private String name;
	private Color color;
	protected int row;
	protected int column;
	private List<Card> hand = new ArrayList<>();
	private List<Card> seenCards = new ArrayList<>();
	private List<Room> seenRooms = new ArrayList<>();
	private boolean isHuman;

	public boolean equals(Player target) {
		if (target.getName().equals(this.name) && target.getColor().equals(this.color)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void draw(Graphics g, Dimension cellSize) {
		int width = cellSize.width;
		int height = cellSize.height;
		int x = width * this.row + width/2;
		int y = height * this.column + height/2;
		g.setColor(this.color);
		g.fillOval(x, y, width, height);
	}

	public Card disproveSuggestion(Solution suggestion) {
		List<Card> matchCards = new ArrayList<>();
		for(int i = 0; i < hand.size(); i++) {
			if (suggestion.person.equals(hand.get(i))) {
				matchCards.add(hand.get(i));
			}
			if (suggestion.weapon.equals(hand.get(i))) {
				matchCards.add(hand.get(i));
			}
			if (suggestion.room.equals(hand.get(i))) {
				matchCards.add(hand.get(i));
			}
		}
		// will always return first index so long as the list isn't empty. Shuffles the list first to pseudo-randomly select a card
		if(!matchCards.isEmpty()) {
			Collections.shuffle(matchCards);
			return matchCards.get(0);
		} else {
			return null;
		}
	}

	public void updateHand(Card card) {
		this.hand.add(card);
	}

	public void updateSeenCards(Card seenCard) {
		this.seenCards.add(seenCard);
	}

	public List<Card> getSeenCards() {
		return seenCards;
	}

	public void setSeenCards(List<Card> seenCards) {
		this.seenCards = seenCards;
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

	public List<Card> getHand() {
		return hand;
	}

	public void setHand(List<Card> hand) {
		this.hand = hand;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public abstract Solution createSuggestion();

	public abstract BoardCell selectTargets();

	public List<Room> getSeenRooms() {
		return seenRooms;
	}

	public void setSeenRooms(List<Room> seenRooms) {
		this.seenRooms = seenRooms;
	}

	public boolean isHuman() {
		return isHuman;
	}

	public void setHuman(boolean isHuman) {
		this.isHuman = isHuman;
	}

	public void setPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
}
