package Game;

import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Game {
	private static JTextArea outputArea;
	static Room currentRoom = World.buildWorld();
	public static HashMap<String, String> descriptions;
	public static ArrayList<Item> inventory = new ArrayList<>();

	public static void makeMap() {
		descriptions = new HashMap<>();
		try {
			Scanner fileScan = new Scanner(new File("Rooms.txt"));
			while (fileScan.hasNextLine()) {
				String line = fileScan.nextLine();
				String name = line;
				String desc = "";
				line = fileScan.nextLine();
				while (!line.equals("#")) {
					desc = desc + line;
					line = fileScan.nextLine();
				}
				descriptions.put(name, desc);
			}
			fileScan.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void move(String d) {
		String[] directions = { "east", "west", "north", "south", "up", "down" };
		int numD = 6;
		int i = 0;
		while (i < 6) {
			if (directions[i].equals(d)) {
				numD = i;
			}
			i++;
		}
		if (numD == 6) {
			Game.print("You can't go imaginary directions!");
		} else if (currentRoom.getExit(numD) == null) {
			Game.print("You can't go that way!");
		} else if (currentRoom.getExit(numD).getLock() == true) {
			Game.print("That way is locked! You must find an item before you can go that way!");
		} else {
			currentRoom = currentRoom.getExit(numD);
		}
	}

	public static void saveGame() {
		try {
			File saveFile = new File("save0.sav");
			saveFile.createNewFile();
			ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(saveFile));
			outStream.writeObject(currentRoom);
			outStream.writeObject(Room.getRooms());
			outStream.writeObject(inventory);
			outStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void loadGame() {
		try {
			File saveFile = new File("save0.sav");
			ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(saveFile));
			currentRoom = (Room) inStream.readObject();
			Room.setRooms((HashMap<String, Room>) inStream.readObject());
			inventory = (ArrayList<Item>) inStream.readObject();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		runGameGui();
	}

	public static void takeItem(String n) {
		if (currentRoom.getItem(n) == null) {
			Game.print("There is no " + n + " in this room!");
		} else {
			if (currentRoom.getItem(n).getWeight().equals("l")) {
				Game.print("You picked up a " + n + "!");
				inventory.add(currentRoom.getItem(n));
				currentRoom.removeItem(n);
			} else {
				Game.print("You can't pick up a " + n + "!");
			}
		}
	}

	public static void useItem(String n) {
		int i = 0;
		Item item = null;
		while (i < inventory.size()) {
			if (n.equals(inventory.get(i).getItem())) {
				item = inventory.get(i);
				item.use();
			}
		}
		if(item == null) {
			Game.print("You don't have that item!");
		}
	}

	public static void runGameGui() {

		makeMap();

		JFrame frame = new JFrame("Text Adventure");
		frame.setSize(1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		outputArea = new JTextArea();
		outputArea.setEditable(false);
		outputArea.setLineWrap(true);
		outputArea.setWrapStyleWord(true);
		outputArea.setFont(new Font("Consolas", Font.PLAIN, 18));

		outputArea.setBackground(new Color(25, 25, 25));
		outputArea.setForeground(new Color(220, 220, 220));
		outputArea.setCaretColor(Color.WHITE);

		outputArea.setMargin(new Insets(10, 10, 10, 10));

		JScrollPane scrollPane = new JScrollPane(outputArea);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		// Bottom panel (label + input box + button)
		JPanel inputPanel = new JPanel(new BorderLayout());

		JLabel inputLabel = new JLabel(" Command: ");
		inputLabel.setFont(new Font("Consolas", Font.BOLD, 18));

		JTextField inputField = new JTextField();
		inputField.setFont(new Font("Consolas", Font.PLAIN, 20));
		inputField.setPreferredSize(new Dimension(0, 45));

		inputField.setBackground(new Color(40, 40, 40));
		inputField.setForeground(Color.WHITE);
		inputField.setCaretColor(Color.WHITE);

		JButton submitButton = new JButton("Enter");
		submitButton.setFont(new Font("Consolas", Font.BOLD, 18));
		submitButton.setPreferredSize(new Dimension(120, 45));

		inputPanel.add(inputLabel, BorderLayout.WEST);
		inputPanel.add(inputField, BorderLayout.CENTER);
		inputPanel.add(submitButton, BorderLayout.EAST);

		frame.add(scrollPane, BorderLayout.CENTER);
		frame.add(inputPanel, BorderLayout.SOUTH);

		Game.print("══════════════════════════════");
		Game.print("      TEXT ADVENTURE");
		Game.print("══════════════════════════════");
		Game.print("");
		Game.print("You are in the " + currentRoom);
		Game.print("");
		Game.print("What would you like to do?");

		Runnable processCommand = () -> {

			String command = inputField.getText().trim().toLowerCase();
			inputField.setText("");

			if (command.isEmpty()) {
				return;
			}

			Game.print("");
			Game.print("> " + command);

			command = command.replaceAll("\\b(the|to)\\b", "");
			command = command.replaceAll("\\s+", " ").trim();

			String[] separate = command.split(" ");

			try {

				if (separate[0].equals("i")) {

					Game.print(inventory);

				} else if (separate[0].equals("take")) {

					if (separate.length > 1) {

						takeItem(separate[1]);

					} else {

						Game.print("Error: no item entered to take.");
					}

				} else if (separate[0].equals("go")) {

					if (separate.length > 1) {

						move(separate[1]);
						Game.print("You are in the " + currentRoom);

					} else {

						Game.print("Go where?");
					}

				} else if (separate[0].equals("exit")) {

					Game.print("You Escaped!");
					frame.dispose();

				} else if (separate[0].equals("load")) {

					loadGame();

				} else if (separate[0].equals("save")) {

					saveGame();

				} else if (separate[0].equals("use")) {
					if (separate.length > 1) {
						useItem(separate[1]);
					} else {
						Game.print("use what?");
					}
				} else {
					Game.print("You can't do that!");
				}
			} catch (Exception ex) {
				Game.print("Invalid command.");
			}

			Game.print("");
			Game.print("What would you like to do?");
		};

		inputField.addActionListener(e -> processCommand.run());
		submitButton.addActionListener(e -> processCommand.run());

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		inputField.requestFocusInWindow();
	}

	public static void runGame() {
		makeMap();
		String command = "";
		Scanner input = new Scanner(System.in);
		Game.print("You are in the " + currentRoom);
		while (!command.equals("exit")) {
			Game.print("What would you like to do? ");
			command = input.nextLine().toLowerCase();
			command = command.replaceAll("\\b(the|to)\\b", "");
			command = command.replaceAll("\\s+", " ");
			Game.print("You would like to: " + command);
			String[] seperate = command.split(" ");
			if (seperate[0].equals("i")) {
				Game.print(inventory);
			} else if (seperate[0].equals("take")) {
				if (seperate.length > 1) {
					takeItem(seperate[1]);
				} else {
					Game.print("Error no item entered to take!");
				}
			} else if (seperate[0].equals("go")) {
				move(seperate[1]);
				Game.print("You are in the " + currentRoom);
			} else {
				print("You can't do that!");
			}
		} // end while
		input.close();
		System.out.println("You Escaped!");
	} // end runGame()

	public static void print(Object text) {
		if (outputArea != null) {
			outputArea.append(text + "\n");
			outputArea.setCaretPosition(outputArea.getDocument().getLength());
		} else {
			System.out.println(text);
		}
	}

	public static Room getCurrentRoom() {
		return currentRoom;
	}

} // end class Game