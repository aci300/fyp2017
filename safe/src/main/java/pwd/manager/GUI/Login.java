
package pwd.manager.GUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;
import pwd.manager.safe.UserLogin;

import javax.swing.event.ListSelectionEvent;
//import javax.swing.JComboBox;

public class Login {

	private UserLogin user = new UserLogin();
	private JFrame frame;
	private String usrlogin;
	public String username;
	public String password;
	private JPasswordField passwordField;
	private JPasswordField passwordField2;
	private JList<String> accountsList;
	private JTextArea textBox = new JTextArea();
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private String usrpwd = null;
	public String DBpassword;
	public String DBusername;
	public String DBUrl;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		initialize();
	}

	private void initialize() {

		final JTextField txtUsername = new JTextField();
		final JTextField txtDescription = new JTextField();
		final JTextField txtHint = new JTextField();
		final JButton sign_in = new JButton("Sign in");
		final JButton dbSettings = new JButton("DB Settings");
		final JButton new_user = new JButton("New User");
		final JButton create_user = new JButton("Create User");
		final JButton new_acc = new JButton("New account");
		final JButton create_acc = new JButton("Create");
		final JButton get_accounts = new JButton("Get Accounts");
		final JButton btnSignOut = new JButton("Sign out");
		final JTextField DBUsername = new JTextField();
		final JTextField DBurl = new JTextField();
		final JButton apply = new JButton("Apply");

		frame = new JFrame("Password Manager");
		frame.setBounds(100, 100, 945, 521);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 154, 247, 304);
		frame.getContentPane().add(scrollPane);
		accountsList = new JList<String>();
		scrollPane.setViewportView(accountsList);
		accountsList.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent arg0) {
				if (accountsList.getSelectedIndex() >= 0) {
					textBox.setText(user.displayDecryptedPassbyIndex(accountsList.getSelectedIndex()));
				}
			}
		});

		scrollPane.setVisible(false);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(340, 107, 420, 150);
		frame.getContentPane().add(scrollPane_1);

		textBox.setText(
				"You are NOT connected . \r\nPlease insert your username and password and press Sign in \r\n Or create a New User");
		textBox.setWrapStyleWord(true);
		scrollPane_1.setViewportView(textBox);

		txtUsername.setText("Username");
		txtUsername.setBounds(31, 31, 265, 22);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(20);

		passwordField = new JPasswordField();
		passwordField.setBounds(340, 31, 254, 22);
		frame.getContentPane().add(passwordField);

		/**
		 * The mouseListener for the Sign in button , when the button is clicked
		 * will establish the connection to the DB and will set the JFrane
		 * accordingly for the next stage
		 */
		sign_in.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				try {
					password = new String(((JPasswordField) passwordField).getPassword());
					username = txtUsername.getText();
					user.login(username, password);
					usrpwd = password;
					usrlogin = username;
					btnSignOut.setVisible(true);
					scrollPane.setVisible(true);
					sign_in.setVisible(false);
					passwordField.setVisible(false);
					txtUsername.setVisible(false);
					new_user.setVisible(false);
					new_acc.setVisible(true);
					get_accounts.setVisible(true);
					passwordField2.setVisible(false);
					DBUsername.setVisible(false);
					DBurl.setVisible(false);
					apply.setVisible(false);
					dbSettings.setVisible(false);
					accountsList.setModel(new DefaultListModel<String>());
					textBox.setText("You are connected. \nPlease choose one option!");
				} catch (IllegalArgumentException e) {
					String message = e.getMessage();

					scrollPane.setVisible(false);
					textBox.setText(message);

				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeySpecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		sign_in.setBounds(663, 31, 97, 22);
		frame.getContentPane().add(sign_in);

		/**
		 * The mouseListener for the Sign in button , when the button is clicked
		 * will establish the connection to the DB and will set the JFrane
		 * accordingly for the next stage
		 */
		new_user.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				try {
					txtUsername.setText("New Username");
					txtUsername.setBounds(31, 31, 265, 22);
					frame.getContentPane().add(txtUsername);
					new_user.setVisible(false);
					sign_in.setVisible(false);
					create_user.setVisible(true);
					dbSettings.setVisible(false);

				} catch (IllegalArgumentException e) {
					scrollPane.setVisible(false);
					textBox.setText("Invalid or missing credentials!!! ");

				}

			}
		});

		new_user.setBounds(800, 31, 97, 22);
		frame.getContentPane().add(new_user);

		create_user.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					password = new String(((JPasswordField) passwordField).getPassword());
					username = txtUsername.getText();
					user.createUser(username, password);
					textBox.setText("The new user has been added! \nPlease sign in or create a new user! ");
					create_user.setVisible(false);
					new_user.setVisible(true);
					sign_in.setVisible(true);
					txtUsername.setText("Username");
					passwordField.setText("Password");
					frame.getContentPane().add(txtUsername);
					create_user.setVisible(false);
				} catch (IllegalArgumentException e) {
					scrollPane.setVisible(false);
					textBox.setText("Invalid or missing credentials!!! ");

				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeySpecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		create_user.setBounds(700, 31, 117, 22);
		create_user.setVisible(false);
		frame.getContentPane().add(create_user);

		txtDescription.setText("Description");
		txtDescription.setBounds(31, 60, 265, 22);
		txtDescription.setVisible(false);
		frame.getContentPane().add(txtDescription);
		txtDescription.setColumns(50);

		txtHint.setText("Hint");
		txtHint.setBounds(340, 60, 254, 22);
		txtHint.setVisible(false);
		frame.getContentPane().add(txtHint);
		txtHint.setColumns(20);

		new_acc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				try {
					frame.getContentPane().add(txtUsername);
					txtUsername.setVisible(true);
					passwordField.setVisible(true);
					txtDescription.setVisible(true);
					txtHint.setVisible(true);
					sign_in.setVisible(false);
					get_accounts.setVisible(false);
					create_acc.setVisible(true);
					new_acc.setVisible(false);
					dbSettings.setVisible(false);
					txtUsername.setText("New Account");
					passwordField.setText("Password");
					textBox.setText("Add the details for the new accout");

				} catch (IllegalArgumentException e) {
					scrollPane.setVisible(false);
					textBox.setText("Invalid or missing credentials!!! ");

				}

			}
		});

		new_acc.setBounds(663, 31, 127, 22);
		new_acc.setVisible(false);
		frame.getContentPane().add(new_acc);

		create_acc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				try {
					password = new String(((JPasswordField) passwordField).getPassword());
					username = txtUsername.getText();
					String desc = txtDescription.getText();
					String hint = txtHint.getText();
					user.createAccount(username, password, usrlogin, usrpwd, desc, hint);
					txtUsername.setVisible(false);
					passwordField.setVisible(false);
					txtDescription.setVisible(false);
					txtHint.setVisible(false);
					get_accounts.setVisible(true);
					sign_in.setVisible(false);
					create_acc.setVisible(false);
					new_acc.setVisible(true);
					textBox.setText("The new account has been added. Refresh the list! ");

				} catch (IllegalArgumentException e) {
					txtUsername.setText("New Account");
					passwordField.setText("Password");
					get_accounts.setVisible(true);
					accountsList.setVisible(true);
					scrollPane.setVisible(true);
					textBox.setText(e.getMessage());

				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		create_acc.setBounds(663, 31, 97, 22);
		create_acc.setVisible(false);
		frame.getContentPane().add(create_acc);

		get_accounts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {

					scrollPane.setVisible(true);
					accountsList.setVisible(true);
					textBox.setText("Select one account to get the content");
					accountsList.setListData(user.getAccounts(usrlogin));

				} catch (IllegalArgumentException e) {
					scrollPane.setVisible(false);
					textBox.setText("Invalid or missing credentials!!! ");

				}
			}
		});
		get_accounts.setBounds(12, 90, 124, 25);
		get_accounts.setVisible(false);
		frame.getContentPane().add(get_accounts);

		DBUsername.setBounds(30, 120, 250, 22);
		frame.getContentPane().add(DBUsername);
		DBUsername.setColumns(20);

		DBurl.setBounds(30, 90, 250, 22);
		frame.getContentPane().add(DBurl);
		DBurl.setColumns(200);

		passwordField2 = new JPasswordField();
		passwordField2.setBounds(30, 150, 250, 22);
		frame.getContentPane().add(passwordField2);
		passwordField2.setVisible(false);
		DBUsername.setVisible(false);
		DBurl.setVisible(false);
		apply.setVisible(false);

		dbSettings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				passwordField2.setVisible(true);
				DBUsername.setVisible(true);
				DBurl.setVisible(true);
				apply.setVisible(true);
				dbSettings.setVisible(false);
				DBUsername.setText("Database Username");
				DBurl.setText("Database URL - PostgreSql/MySQL");
				passwordField2.setText("Password");

			}
		});

		apply.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				DBpassword = new String(((JPasswordField) passwordField2).getPassword());
				DBusername = DBUsername.getText();
				DBUrl = DBurl.getText();
				user.setDBSettings(DBusername, DBpassword, DBUrl);
				// user = new UserLogin(db);
				textBox.setText(
						"The Database Settings have been updated.\r\nYou are NOT connected.\nPlease sign in or create a new user!");

				passwordField2.setVisible(false);
				DBUsername.setVisible(false);
				DBurl.setVisible(false);
				apply.setVisible(false);
				dbSettings.setVisible(true);
				// frame.dispose();
			}
		});
		apply.setBounds(60, 200, 70, 20);
		frame.getContentPane().add(apply);

		dbSettings.setBounds(60, 200, 100, 20);
		frame.getContentPane().add(dbSettings);
		// dbSettings.setVisible(true);

		btnSignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					user.signout();
					btnSignOut.setVisible(false);
					scrollPane.setVisible(false);
					sign_in.setVisible(true);
					// btnSignOut.setVisible(true);
					passwordField.setVisible(true);
					txtUsername.setVisible(true);
					new_user.setVisible(true);
					new_acc.setVisible(false);
					get_accounts.setVisible(false);
					dbSettings.setVisible(true);
					txtUsername.setText("Username");
					passwordField.setText("Password");
					textBox.setText("Successfully signed out. \nSign in or create a new user  ");
				} catch (Exception e1) {
					textBox.setText("You were not signed in !!!!");
				}
			}
		});
		btnSignOut.setBounds(700, 400, 97, 25);
		btnSignOut.setVisible(false);
		frame.getContentPane().add(btnSignOut);
	}
}
