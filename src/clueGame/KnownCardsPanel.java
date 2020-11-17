package clueGame;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class KnownCardsPanel extends JPanel {
	
	KnownCardsPanel(Player player) {
		setLayout(new GridLayout(3, 0));
		setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));
		int counter = 0;
		
		//sets people section
		JPanel people = new JPanel();
		people.setLayout(new GridLayout(0, 1));
		people.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		JLabel peopleHand = new JLabel("In Hand:");
		people.add(peopleHand);
		displayInHand(player, people, CardType.PERSON);
		
		JLabel peopleSeen = new JLabel("Seen:");
		people.add(peopleSeen);
		displaySeen(player, people, CardType.PERSON);
		
		add(people);
		
		//sets room section
		JPanel rooms = new JPanel();
		rooms.setLayout(new GridLayout(0, 1));
		rooms.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		JLabel roomsHand = new JLabel("In Hand:");
		rooms.add(roomsHand);
		displayInHand(player, rooms, CardType.ROOM);
		
		JLabel roomsSeen = new JLabel("Seen:");
		rooms.add(roomsSeen);
		displaySeen(player, rooms, CardType.ROOM);

		add(rooms);
		
		//sets weapon section
		JPanel weapons = new JPanel();
		weapons.setLayout(new GridLayout(0, 1));
		weapons.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		JLabel weaponsHand = new JLabel("In Hand:");
		weapons.add(weaponsHand);
		displayInHand(player, weapons, CardType.WEAPON);
		
		JLabel weaponsSeen = new JLabel("Seen:");
		weapons.add(weaponsSeen);
		displaySeen(player, weapons, CardType.WEAPON);

		add(weapons);
	}
	
	// main class for testing KnownCardsPanel()
	
//	public static void main(String[] args) {
//		Board board = Board.getInstance();
//		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
//		board.initialize();
//		List<Player> players = board.getPlayers();
//		List<Card> seenCards = new ArrayList<>();
//		Player human = null;
//		Random num = new Random();
//		int index = 0;
//		for (Player player : players) {
//			if (player.isHuman()) {
//				human = player;
//			} else { //picks a card from each computer players hand to add to the humans list of seen cards for testing
//				index = num.nextInt(player.getHand().size());
//				seenCards.add(player.getHand().get(index));
//			}
//		}
//		human.setSeenCards(seenCards);
//		KnownCardsPanel panel = new KnownCardsPanel(human);  // create the panel
//        JFrame frame = new JFrame();  // create the frame
//        frame.setContentPane(panel); // put the panel in the frame
//        frame.setSize(180, 750);  // size the frame
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
//        frame.setVisible(true); // make it visible
//	}
	
	private void displayInHand(Player player, JPanel panel, CardType type) {
		int counter = 0;
		//sets people for cards in hand
		for (int i = 0; i < player.getHand().size(); i++) {
			if (player.getHand().get(i).getType().equals(type)) {
				JTextField temp = new JTextField(player.getHand().get(i).getCardName());
				temp.setEditable(false);
				panel.add(temp);
				temp.setBackground(Color.white);
				counter++;
			}
		}
		if (counter == 0) {
			JTextField temp = new JTextField("None");
			temp.setEditable(false);
			temp.setBackground(Color.white);
			panel.add(temp);
		}
	}
	
	private void displaySeen(Player player, JPanel panel, CardType type) {
		int counter = 0;
		//sets people for seen cards
		for (int i = 0; i < player.getSeenCards().size(); i++) {
			if (player.getSeenCards().get(i).getType().equals(type)) {
				JTextField temp = new JTextField(player.getSeenCards().get(i).getCardName());
				temp.setEditable(false);
				panel.add(temp);
				temp.setBackground(player.getSeenCards().get(i).getOwner().getColor());
				counter++;
			}
		}
		if (counter == 0) {
			JTextField temp = new JTextField("None");
			temp.setEditable(false);
			temp.setBackground(Color.white);
			panel.add(temp);
		}
	}
	
}
