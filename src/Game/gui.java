package Game;
import javax.swing.*;
import java.awt.*;

public class gui extends JFrame {
	private JTextArea outputArea;
	private JTextField inputField;
	private JButton enterButton;

	public gui() {
	        setTitle("Text Adventure");
	        setSize(800, 500);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);

	        // Story Output Area
	        outputArea = new JTextArea();
	        outputArea.setEditable(false);
	        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
	        outputArea.setText(
	                "Welcome to the Adventure!\n\n" +
	                "You stand in a dark forest.\n" +
	                "Paths lead north and east.\n\n" +
	                "> ");

	        JScrollPane scrollPane = new JScrollPane(outputArea);

	        // Input Components
	        inputField = new JTextField();
	        enterButton = new JButton("Enter");

	        JPanel inputPanel = new JPanel(new BorderLayout());
	        inputPanel.add(inputField, BorderLayout.CENTER);
	        inputPanel.add(enterButton, BorderLayout.EAST);

	        // Action for command entry
	        enterButton.addActionListener(e -> processCommand());
	        inputField.addActionListener(e -> processCommand());

	        add(scrollPane, BorderLayout.CENTER);
	        add(inputPanel, BorderLayout.SOUTH);
	    }

	private void processCommand() {
		String command = inputField.getText().trim();

		if (!command.isEmpty()) {
			outputArea.append(command + "\n");

			switch (command.toLowerCase()) {
			case "look":
				outputArea.append("Tall trees surround you.\n");
				break;

			case "north":
			case "go north":
				outputArea.append("You walk north and find an ancient gate.\n");
				break;

			case "help":
				outputArea.append("Commands: look, go north, help\n");
				break;

			default:
				outputArea.append("I don't understand that command.\n");
			}

			outputArea.append("\n> ");
		}

		inputField.setText("");
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new gui().setVisible(true);
		});

	}
}
