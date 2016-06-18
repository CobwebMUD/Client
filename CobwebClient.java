package cobwebMudJClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class CobwebClient {

	// socket connected to server
	private Socket sock;
	// socket input stream
	private BufferedReader in;
	// socket output stream
	private PrintWriter out;

	// Cobweb Client constructor @param -> address of server, port of server
	public CobwebClient(String addr, int port) {
		try {
			// Initialize socket and input / output steams
			sock = new Socket(addr, port);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// returns output from socket
	public String read() {
		try {
			// read message in through buffered reader until a "new line" ->
			// "\n" character
			return in.readLine();
		} catch (IOException e) {
			return "FAILED TO READ FROM SOCKET!!!";
		}
	}

	// sends string to server
	public void send(String msg) {
		try {
			out = new PrintWriter(sock.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// null terminating 0 so server knows to stop reading input
		out.println(msg + "\0");
	}

}
