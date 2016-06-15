package cobwebMudJClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

	// client socket
	private Socket sock;
	// port server is running on
	private int port = 9876;

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		connect();
	}

	private void connect() {
		try {
			sock = new Socket("127.0.0.1", port);
			BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			listen(in);
		} catch (UnknownHostException e) {
			Main.clientErr(e.toString(), 0);
		} catch (IOException e) {
			Main.clientErr(e.toString(), 0);
		}
	}

	private void listen(BufferedReader in) {
		Scanner userIn = new Scanner(System.in);
		System.out.println("Type 'play' to begin game.");
		String fromU = userIn.nextLine();
		if (fromU.equals("play")) {
			Main.send(sock, "GAMESTART");
		}
		while (true) {
			try {
				String fromServ = in.readLine();
				System.out.println("From Server: '" + fromServ + "'");
				fromU = userIn.nextLine();
				if (fromU.equals("")) {
					Main.send(sock, "null");
				} else {
					Main.send(sock, fromU);
				}
				if (fromServ.contains("Press enter to exit game")) {
					break;
				}
			} catch (IOException e) {
				Main.clientErr(e.toString(), 1);
			}
		}
		listen(in);
	}

	public static void send(Socket sock, String msg) {
		try {
			PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
			out.println(msg + "\0");
		} catch (IOException e) {
			clientErr(e.toString(), 1);
		}
	}

	public static void clientErr(String msg, int op) {
		System.out.println("Client ERROR: " + msg);
		if (op == 0) {
			System.out.println("Client exiting...");
			System.exit(0);
		} else if (op == 1) {
			System.out.println("Client proceeding with operations...");
			return;
		} else {
			System.out.println("Unidentified operation: '" + op + "'");
			System.out.println("Client proceeding with operations...");
			return;
		}
	}

}
