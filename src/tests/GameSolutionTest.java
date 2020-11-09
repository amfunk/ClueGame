package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameSolutionTest {
	
	private static Board board;

	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		board.initialize();
	}

	@Test
	public void testCheckAccusation() {
		Solution theAnswer = Solution.getAnswer();
		Solution suggestion1 = new Solution(); // all cards match answer
		Solution suggestion2 = new Solution(); // room doesn't match answer
		Solution suggestion3 = new Solution(); // weapon doesn't match answer
		Solution suggestion4 = new Solution(); // person doesn't match answer
		
		//This sets theAnswer to known cards to allow me to test checkAccusation
		Card player = new Card();
		player.setType(CardType.PERSON);
		player.setCardName("Colonel Mustard");
		theAnswer.person = player;
		suggestion1.person = player;
		suggestion2.person = player;
		suggestion3.person = player;
		Card weapon = new Card();
		weapon.setType(CardType.WEAPON);
		weapon.setCardName("Poleax");
		theAnswer.weapon = weapon;
		suggestion1.weapon = weapon;
		suggestion2.weapon = weapon;
		suggestion4.weapon = weapon;
		Card room = new Card();
		room.setType(CardType.ROOM);
		room.setCardName("Bathroom");
		theAnswer.room = room;
		suggestion1.room = room;
		suggestion3.room = room;
		suggestion4.room = room;
		
		//sets the wrong cards for the suggestions
		Card wrongPlayer = new Card();
		wrongPlayer.setType(CardType.PERSON);
		wrongPlayer.setCardName("Professor Plum");
		suggestion4.person = wrongPlayer;
		Card wrongWeapon = new Card();
		wrongWeapon.setType(CardType.WEAPON);
		wrongWeapon.setCardName("Poison");
		suggestion3.weapon = wrongWeapon;
		Card wrongRoom = new Card();
		wrongRoom.setType(CardType.ROOM);
		wrongRoom.setCardName("Control Center");
		suggestion2.room = wrongRoom;
		
		assertTrue(board.checkAccusation(suggestion1));
		assertFalse(board.checkAccusation(suggestion2));
		assertFalse(board.checkAccusation(suggestion3));
		assertFalse(board.checkAccusation(suggestion4));
	}
	
	@Test
	public void testDisproveSuggestion() {
		Player player = new HumanPlayer();
		//sets suggestion for testing
		Solution suggestion = new Solution();
		Card person = new Card();
		person.setType(CardType.PERSON);
		person.setCardName("Colonel Mustard");
		suggestion.person = person;
		Card weapon = new Card();
		weapon.setType(CardType.WEAPON);
		weapon.setCardName("Poleax");
		suggestion.weapon = weapon;
		Card room = new Card();
		room.setType(CardType.ROOM);
		room.setCardName("Bathroom");
		suggestion.room = room;
		
		player.updateHand(person);
		
		person = new Card();
		person.setType(CardType.PERSON);
		person.setCardName("Professor Plum");
		player.updateHand(person);
		weapon = new Card();
		weapon.setType(CardType.WEAPON);
		weapon.setCardName("Poison");
		player.updateHand(weapon);
		room = new Card();
		room.setType(CardType.ROOM);
		room.setCardName("Control Center");
		player.updateHand(room);
		
		// disproveSuggestion() should return the Colonel Mustard card
		assertEquals(suggestion.person, player.disproveSuggestion(suggestion));
	}
}
