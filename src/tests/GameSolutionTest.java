package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameSolutionTest {
	
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
		
		//adds cards to the players hand
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
		
		//changes suggestion so that it doens't match any cards in the players hand
		person = new Card();
		person.setType(CardType.PERSON);
		person.setCardName("Mrs. Peacock");
		suggestion.person = person;
		
		// should return null since no cards match suggestion
		assertEquals(null, player.disproveSuggestion(suggestion));
		
		//adds two cards from suggestion to player hand
		player.updateHand(suggestion.person);
		player.updateHand(suggestion.room);
		
		Set<Card> matchCards = new HashSet<>();
		while (matchCards.size() < 2) {
			matchCards.add(player.disproveSuggestion(suggestion));
		}
		// set should contain both cards
		assertTrue(matchCards.contains(suggestion.person));
		assertTrue(matchCards.contains(suggestion.room));
	}
	
	@Test
	public void testHandleSuggestion() {
		Player player1 = new HumanPlayer();
		Player player2 = new ComputerPlayer();
		Player player3 = new ComputerPlayer();
		Player player4 = new ComputerPlayer();
		
		Solution suggestion = new Solution();
		suggestion.person = whiteCard;
		suggestion.weapon = handgunCard;
		suggestion.room = bathroomCard;
		
		List<Player> playerList = new ArrayList<>();
		playerList.add(player1);
		playerList.add(player2);
		playerList.add(player3);
		playerList.add(player4);
		board.setPlayers(playerList);

		player1.updateHand(mustardCard);
		player1.updateHand(knifeCard);
		player2.updateHand(plumCard);
		player2.updateHand(poisonCard);
		player2.updateHand(gymCard);
		player3.updateHand(icepickCard);
		player3.updateHand(armoryCard);
		player4.updateHand(scarletCard);
		player4.updateHand(wineCard);

		//no players can disprove this
		assertEquals(null, board.handleSuggestion(suggestion, player1));
		
		player3.updateHand(whiteCard);
		//only player3 can disprove this because they have the Mrs. White card; should still return null
		assertEquals(null, board.handleSuggestion(suggestion, player3));
		
		player4.updateHand(handgunCard);
		//although player 3 and player 4 can disprove this, it should return player 3s card, Mrs. White
		assertEquals(whiteCard, board.handleSuggestion(suggestion, player1));
	}
}
