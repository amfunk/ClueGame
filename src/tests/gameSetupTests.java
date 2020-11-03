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
import clueGame.Player;

public class gameSetupTests {
	
	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout306.csv", "data/ClueSetup306.txt");		
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
		assertTrue(players.get(6).getName().equals("Mrs. Peacock"));
		assertTrue(players.get(6).getColor().equals(Color.blue));
	}
	
	
	
}
