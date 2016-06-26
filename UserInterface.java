package cobwebMudJClient;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

@SuppressWarnings("serial")
public class UserInterface extends JPanel {

	private JEditorPane console;
	private JTextArea userIn;

	// @param -> width of window, height of window
	public UserInterface(int width, int height) {
		super.setBackground(Color.BLACK);
		// format UI stuff below
		super.setLayout(new GridBagLayout());
		GridBagConstraints gBC = new GridBagConstraints();
		gBC.fill = GridBagConstraints.HORIZONTAL;
		gBC.gridx = 0;
		gBC.gridy = 0;
		gBC.ipadx = width;
		gBC.ipady = height - 100;
		gBC.weighty = 1;
		console = new JEditorPane();
		console.setEditable(false);
		console.setBackground(Color.BLACK);
		console.setForeground(Color.WHITE);
		console.setVisible(true);
		JScrollPane js = new JScrollPane(console);
		js.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		js.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		super.add(js, gBC);
		userIn = new JTextArea();
		userIn.setBackground(Color.BLACK);
		userIn.setForeground(Color.WHITE);
		userIn.setEditable(true);
		gBC = new GridBagConstraints();
		gBC.fill = GridBagConstraints.HORIZONTAL;
		gBC.gridx = 0;
		gBC.gridy = 1;
		gBC.ipadx = width;
		gBC.ipady = 10;
		gBC.weighty = 0;
		super.add(userIn, gBC);
	}

	// blocking method that returns input from user
	public String readIn() {
		String in = "";
		userIn.setText(">> ");
		userIn.setCaretPosition(3);
		do {
			in = userIn.getText();
		} while (userIn.getLineCount() == 1);
		append(in.substring(0, in.length() - 1));
		userIn.setText(">> ");
		return in.substring(3, in.length() - 1);
	}

	// append text to console
	public void append(String str) {
		Document doc = console.getDocument();
		console.setCaretPosition(doc.getLength());
		try {
			doc.insertString(doc.getLength(), str + "\n", null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

}
