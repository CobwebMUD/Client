package cobwebMudJClient;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class ChatRoomClient extends JPanel implements Runnable {

	private Socket sock;
	private boolean listen;
	private JTextArea msgBox;
	private JTextArea userIn;
	private JButton send;

	public ChatRoomClient(String addr, int port) {
		msgBox = new JTextArea(10, 30);
		userIn = new JTextArea(1, 30);
		msgBox.setBackground(Color.BLACK);
		msgBox.setForeground(Color.WHITE);
		userIn.setBackground(Color.BLACK);
		userIn.setForeground(Color.WHITE);

		send = new JButton("Send.");
		try {
			sock = new Socket(addr, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new PrintWriter(sock.getOutputStream(), true).println(userIn.getText() + "\0");
					msgBox.append(userIn.getText() + "\n");
					userIn.setText("");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		// create new thread to listen for msg's from server
		Thread thread = new Thread(this);
		listen = true;
		thread.start();
		// add UI elements
		this.add(new JScrollPane(msgBox));
		this.add(userIn);
		this.add(send);
	}

	@Override
	public void run() {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		char buff[] = new char[128];
		while (listen) {
			try {
				in.read(buff, 0, buff.length);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			String buffS = new String(buff);
			msgBox.append(buffS + "\n");
		}
	}

}
