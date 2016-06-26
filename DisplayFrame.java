package cobwebMudJClient;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DisplayFrame extends JFrame {

	// graphic container that is displayed in window
	private JPanel content;

	// @param -> JPanel, title of window, width of window, height of window
	public DisplayFrame(JPanel content, String title, int width, int height) {
		this.content = content;
		super.setSize(width, height);
		super.setTitle(title);
		super.setLocationRelativeTo(null);
		super.setVisible(true);
	}

	// make content visible in window
	public void bind() {
		setVisible(false);
		super.setContentPane(content);
		super.revalidate();
		setVisible(true);
	}

	// close window
	public void close() {
		super.setVisible(false);
	}

	// change content in window
	public void setContent(JPanel content) {
		this.content = content;
	}

	// get content in window
	public JPanel getContent() {
		return content;
	}
}
