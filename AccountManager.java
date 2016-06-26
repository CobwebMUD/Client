package cobwebMudJClient;

import javax.swing.JOptionPane;

public class AccountManager {

	private CobwebClient cClient;
	
	private DisplayFrame frame;

	public AccountManager(CobwebClient cClient, DisplayFrame frame) {
		this.cClient = cClient;
		this.frame = frame;
		String[] newOrExist = { "new account", "existing account" };
		int ans = JOptionPane.showOptionDialog(frame, "new or existing", "login", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, newOrExist, newOrExist[1]);
		if (ans == 0) {
			newAccount();
		} else if (ans == 1) {
			existingAccount();
		} else {
			System.out.println("Wat wat in the but" + ans);
		}
	}

	private void newAccount() {
		cClient.send("NEWACC");
		String userName = JOptionPane.showInputDialog(frame, "Enter Username");
		String pass = JOptionPane.showInputDialog(frame, "Enter password");
		String email = JOptionPane.showInputDialog(frame, "Enter email");
		cClient.send("UserInfo/" + userName + "/" + pass + "/" + email + "/");
	}

	private void existingAccount() {
		cClient.send("EXISTACC");
		String prompt = cClient.read();
		cClient.send(JOptionPane.showInputDialog(frame, prompt));
		prompt = cClient.read();
		cClient.send(JOptionPane.showInputDialog(frame, prompt));
	}

}
