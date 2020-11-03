package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.Solution;

public class gameSetupTests {
	
	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}
	
	@Test
	public void testLoadPlayers() {
		ArrayList<Player> players = board.getPlayers();
		assertEquals(6, players.size());	// make sure that 6 players are loaded in
		// checks the data that was loaded into the edge cases
		assertTrue(players.get(0).getName().equals("Colonel Mustard"));
		assertTrue(players.get(0).getColor().equals(Color.yellow));
		assertTrue(players.get(5).getName().equals("Mrs. Peacock"));
		assertTrue(players.get(5).getColor().equals(Color.blue));
	}
	
	@Test
	public void testLoadDeck() {
		ArrayList<Card> deck = board.getDeck();
		assertEquals(20, deck.size());
		assertTrue(deck.get(0).getCardName().equals("Locker Room"));
		assertTrue(deck.get(0).getType().equals(CardType.ROOM));
		assertTrue(deck.get(7).getCardName().equals("Wine Cellar"));
		assertTrue(deck.get(7).getType().equals(CardType.ROOM));
		assertTrue(deck.get(8).getCardName().equals("Colonel Mustard"));
		assertTrue(deck.get(8).getType().equals(CardType.PERSON));
		assertTrue(deck.get(13).getCardName().equals("Mrs. Peacock"));
		assertTrue(deck.get(13).getType().equals(CardType.PERSON));
		assertTrue(deck.get(14).getCardName().equals("Knife"));
		assertTrue(deck.get(14).getType().equals(CardType.WEAPON));
		assertTrue(deck.get(19).getCardName().equals("Poleax"));
		assertTrue(deck.get(19).getType().equals(CardType.WEAPON));
	}
	
	@Test
	public void testTheAnswer() {
		Solution theAnswer = Solution.getAnswer();
		assertTrue(theAnswer.person.getType().equals(CardType.PERSON));
		assertTrue(theAnswer.room.getType().equals(CardType.ROOM));
		assertTrue(theAnswer.weapon.getType().equals(CardType.WEAPON));

	}
}
