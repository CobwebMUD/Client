package cobwebMudJClient;

import javax.swing.JOptionPane;

public class AccountManager {

	private CobwebClient cClient;
	
	public AccountManager(CobwebClient cClient) {
		this.cClient = cClient;
		newAccount();
	}
	
	private void newAccount() {
		cClient.send("NEWACC");
		String userName = JOptionPane.showInputDialog("Enter Username");
		String pass = JOptionPane.showInputDialog("Enter password");
		String email = JOptionPane.showInputDialog("Enter email");
		cClient.send("UserInfo/" + userName + "/" + pass + "/" + email + "/");
	}
	
}
