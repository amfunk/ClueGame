package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;

public class ComputerAITest {

	private static Board board;
	private static Card mustardCard = new Card("Colonel Mustard", CardType.PERSON);
	private static Card plumCard = new Card("Professor Plum", CardType.PERSON);
	private static Card whiteCard = new Card("Mrs. White", CardType.PERSON);
	private static Card scarletCard = new Card("Miss Scarlet", CardType.PERSON);
	private static Card knifeCard = new Card("Knife", CardType.WEAPON);
	private static Card poisonCard = new Card("Poison", CardType.WEAPON);
	private static Card icepickCard = new Card("Icepick", CardType.WEAPON);
	private static Card handgunCard = new Card("Handgun", CardType.WEAPON);
	private static Card bathroomCard = new Card("Bathroom", CardType.ROOM);
	private static Card gymCard = new Card("Gym", CardType.ROOM);
	private static Card armoryCard = new Card("Armory", CardType.ROOM);
	private static Card wineCard = new Card("Wine Cellar", CardType.ROOM);

	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		board.initialize();
	}

	@Test
	public void testCreateSuggestion() {
		Player player = new ComputerPlayer();
		List<Card> deck = new ArrayList<>();
		List<Card> hand = new ArrayList<>();
		List<Card> seenCards = new ArrayList<>();
		
		//sets player location to the armory
		player.setRow(14);
		player.setColumn(21);

		deck.add(mustardCard);
		deck.add(plumCard);
		deck.add(whiteCard);
		deck.add(scarletCard);
		deck.add(knifeCard);
		deck.add(poisonCard);
		deck.add(icepickCard);
		deck.add(handgunCard);
		deck.add(bathroomCard);
		deck.add(gymCard);
		deck.add(armoryCard);
		deck.add(wineCard);
		board.setDeck(deck);

		hand.add(mustardCard);
		hand.add(knifeCard);
		hand.add(armoryCard);
		player.setHand(hand);

		seenCards.add(plumCard);
		seenCards.add(handgunCard);
		seenCards.add(bathroomCard);
		player.setSeenCards(seenCards);

		assertTrue(board.getDeck().contains(player.createSuggestion().person));
		assertFalse(player.getSeenCards().contains(player.createSuggestion().person));
		assertFalse(player.getHand().contains(player.createSuggestion().person));

		assertTrue(board.getDeck().contains(player.createSuggestion().weapon));
		assertFalse(player.getSeenCards().contains(player.createSuggestion().weapon));
		assertFalse(player.getHand().contains(player.createSuggestion().weapon));
		
		assertTrue(player.createSuggestion().room.equals(armoryCard));

	}
}
