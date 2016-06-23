package cobwebMudJClient;

public class Main {

	// CobwebClient manages communications with sever <- kind of a socket
	// wrapper class
	private CobwebClient cClient;
	// DisplayFrame frame that User Interface is displayed inside
	private DisplayFrame dFrame;
	// UserInterface displays graphics handles user input / output
	private UserInterface uI;

	public static void main(String[] args) {
		Main main = new Main(700, 500);
		// start user interaction
		main.runGame();
		// when runGame() returns close client
		System.exit(0);
	}

	// @param -> width of Main window, height of main window
	public Main(int width, int height) {
		// Initialize objects
		cClient = new CobwebClient("127.0.0.1", 9876);
		uI = new UserInterface(width, height);
		dFrame = new DisplayFrame(uI, "Cobweb MUD", width, height);
		// bind user interface to display frame
		dFrame.bind();
		//log in to server 
		String str = cClient.read();
		if (str.equals("LOGIN")) {
			new AccountManager(cClient);
		}
	}

	// handles communication between UserInterface and CobwebClient
	private void runGame() {
		uI.append("Type 'play' to begin game... (press enter for more options)");
		String fromU = uI.readIn();
		if (fromU.equals("play")) {
			// tell server to start game script
			cClient.send("GAMESTART");
			// while game script is running...
			while (true) {
				// wait for server to send a message then append it to the uI
				String fromServ = cClient.read();
				uI.append("Cobweb: '" + fromServ + "'");
				// have user enter a response <- NOTE uI.readIN() BLOCKS!!!
				fromU = uI.readIn();
				// if user enters nothing send null to server. "" is associated
				// with a dead client *_*
				if (fromU.equals("")) {
					cClient.send("null");
				} else {
					cClient.send(fromU);
				}
				/*
				 * if server sends back any text containing
				 * "Press enter to exit game" then stop client server
				 * interaction... (sloppy i know i will fix soon ~ rice)
				 */
				if (fromServ.contains("Press enter to exit game")) {
					break;
				}
			}
		} else {
			// extra options for client
			uI.append("Type q to quit");
			fromU = uI.readIn();
			if (fromU.equals("q")) {
				uI.append("Exiting...");
				cClient.send("EXIT");
				try {
					// sleep for 2 seconds to give server a chance to disconnect
					// client
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// close window
				dFrame.close();
				return;
			}
		}
		// make recursive call to runGame()
		runGame();
	}

}
