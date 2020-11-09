package clueGame;

public class Card {
	
	private CardType type = CardType.NONE;
	private String cardName;
	
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
