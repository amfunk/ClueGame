package clueGame;

import java.awt.Color;
import java.util.*;

public abstract class Player {

	private String name;
	private Color color;
	protected int row;
	protected int column;
	private List<Card> hand = new ArrayList<>();
	private List<Card> seenCards = new ArrayList<>();
	
	public boolean equals(Player target) {
		if (target.getName().equals(this.name) && target.getColor().equals(this.color)) {
			return true;
		} else {
			return false;
		}
	}
	
	public Card disproveSuggestion() {
		return new Card();
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
}
