package clueGame;

public class Card {
	
	private CardType type;
	private String cardName;
	
	public Card () {
		type = CardType.NONE;
	}
	
	public Card (String cardName, CardType type) {
		this.cardName = cardName;
		this.type = type;
	}
	
	public String toString() {
		return "Name: " + cardName + ", Type: " + type;
	}
	
	public boolean equals(Card target) {
		return (this.type.equals(target.type) && this.cardName.equals(target.cardName));
	}

	public CardType getType() {
		return type;
	}

	public void setType(CardType type) {
		this.type = type;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
}
