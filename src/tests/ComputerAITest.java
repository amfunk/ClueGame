package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Room;

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

		//makes sure that when multiple persons not seen, card is randomly chosen from unseen deck
		assertTrue(board.getDeck().contains(player.createSuggestion().person));
		assertFalse(player.getSeenCards().contains(player.createSuggestion().person));
		assertFalse(player.getHand().contains(player.createSuggestion().person));
		
		//makes sure that when multiple weapons not seen, card is randomly chosen from unseen deck
		assertTrue(board.getDeck().contains(player.createSuggestion().weapon));
		assertFalse(player.getSeenCards().contains(player.createSuggestion().weapon));
		assertFalse(player.getHand().contains(player.createSuggestion().weapon));
		
		//makes sure that room suggestion matches current room, the armory
		assertTrue(player.createSuggestion().room.equals(armoryCard));
		
		player.getSeenCards().add(whiteCard);
		player.getSeenCards().add(poisonCard);
		//adds the remaining cards to players seen cards, should now always suggest Miss Scarlet and the icepick
		assertEquals(scarletCard, player.createSuggestion().person);
		assertEquals(icepickCard, player.createSuggestion().weapon);

	}
	
	@Test
	public void testSelectTargets() {
		Player player = new ComputerPlayer();
		BoardCell start;
		BoardCell test;
		List<Room> seenRooms = player.getSeenRooms();
		player.setRow(8);
		player.setColumn(23);
		start = board.getCell(8, 23);
		board.calcTargets(start, 5);
		//makes sure that it always picks one of two rooms available
		test = player.selectTargets();
		assertTrue(board.getCell(3, 22).equals(test) || board.getCell(14, 21).equals(test));
		assertTrue(board.getTargets().contains(test));

		//makes sure that when a room isn't available it picks a boardcell from target list
		board.calcTargets(start, 3);
		assertTrue(board.getTargets().contains(player.selectTargets()));
		
		//adds all available rooms to seenRooms list
		seenRooms.add(board.getCell(3, 22).getRoom());
		seenRooms.add(board.getCell(14, 21).getRoom());
		
		board.calcTargets(start, 5);
		//this testing ensures that the rooms are picked a portion of the time and the other targets are picked as well
		int totalTargets = board.getTargets().size();
		int wineCounter = 0;
		int armoryCounter = 0;
		int walkwayCounter = 0;
		for (int i = 0; i < 200; i++) {
			test = player.selectTargets();

			if(board.getCell(3, 22).equals(test)) {
				wineCounter++;
			} else if(board.getCell(14, 21).equals(test)) {
				armoryCounter++;
			} else {
				walkwayCounter++;
			}
		}
		if (wineCounter < 5) {
			fail("Didn't pick wine cellar: " + wineCounter);
		}
		if (armoryCounter < 5) {
			fail("Didn't pick armory: " + armoryCounter);
		}
		if (walkwayCounter < 150) {
			fail("Didn't pick walkway: " + walkwayCounter);
		}
	}
}
