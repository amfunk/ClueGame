package clueGame;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

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
		counter = 0;
		for (int i = 0; i < player.getHand().size(); i++) {
			if (player.getHand().get(i).getType().equals(CardType.PERSON)) {
				JTextField temp = new JTextField(player.getHand().get(i).getCardName());
				temp.setEditable(false);
				people.add(temp);
				temp.setBackground(Color.white);
				counter++;
			}
		}
		if (counter == 0) {
			JTextField temp = new JTextField("None");
			temp.setEditable(false);
			temp.setBackground(Color.white);
			people.add(temp);
		}
		JLabel peopleSeen = new JLabel("Seen:");
		people.add(peopleSeen);
		add(people);
		
		//sets room section
		JPanel rooms = new JPanel();
		rooms.setLayout(new GridLayout(0, 1));
		rooms.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		JLabel roomsHand = new JLabel("In Hand:");
		rooms.add(roomsHand);
		counter = 0;
		for (int i = 0; i < player.getHand().size(); i++) {
			if (player.getHand().get(i).getType().equals(CardType.ROOM)) {
				JTextField temp = new JTextField(player.getHand().get(i).getCardName());
				temp.setEditable(false);
				rooms.add(temp);
				temp.setBackground(Color.white);
				counter++;
			}
		}
		if (counter == 0) {
			JTextField temp = new JTextField("None");
			temp.setEditable(false);
			temp.setBackground(Color.white);
			rooms.add(temp);
		}
		JLabel roomsSeen = new JLabel("Seen:");
		rooms.add(roomsSeen);
		add(rooms);
		
		//sets weapon section
		JPanel weapons = new JPanel();
		weapons.setLayout(new GridLayout(0, 1));
		weapons.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		JLabel weaponsHand = new JLabel("In Hand:");
		weapons.add(weaponsHand);
		counter = 0;
		for (int i = 0; i < player.getHand().size(); i++) {
			if (player.getHand().get(i).getType().equals(CardType.WEAPON)) {
				JTextField temp = new JTextField(player.getHand().get(i).getCardName());
				temp.setEditable(false);
				temp.setBackground(Color.white);
				weapons.add(temp);
				counter++;
			}
		}
		if (counter == 0) {
			JTextField temp = new JTextField("None");
			temp.setEditable(false);
			temp.setBackground(Color.white);
			weapons.add(temp);
		}
		JLabel weaponsSeen = new JLabel("Seen:");
		weapons.add(weaponsSeen);
		add(weapons);
	}
	
	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		board.initialize();
		List<Player> players = board.getPlayers();
		Player human = null;
		for (Player player : players) {
			if (player.isHuman()) {
				human = player;
			}
		}
		KnownCardsPanel panel = new KnownCardsPanel(human);  // create the panel
        JFrame frame = new JFrame();  // create the frame
        frame.setContentPane(panel); // put the panel in the frame
        frame.setSize(180, 750);  // size the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
        frame.setVisible(true); // make it visible
	}
	
}
