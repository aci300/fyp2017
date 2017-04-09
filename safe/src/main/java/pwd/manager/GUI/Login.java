package pwd.manager.GUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;

import pwd.manager.safe.UserLogin;

import javax.swing.event.ListSelectionEvent;
import javax.swing.JComboBox;

public class Login {

	private UserLogin user = new UserLogin(); 
	private JFrame frame;
	public static String username;
	public static String password;
	private JPasswordField passwordField;
	private JList<String> accountsList;
	private JTextArea textBox = new JTextArea();
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;


	
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
		final JButton sign_in = new JButton("Sign in");
		final JButton new_user = new JButton("New User");
		final JButton create_user = new JButton("Create User");

		final JButton btnSignOut = new JButton("Sign out");

		frame = new JFrame();
		frame.setBounds(100, 100, 945, 521);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 104, 247, 304);
		frame.getContentPane().add(scrollPane);
		accountsList = new JList<String>();
		scrollPane.setViewportView(accountsList);
	/*	accountsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				try {
					if (subjectsList.getSelectedIndex() >= 0)
						textBox.setText(mc.displayEmailContent(subjectsList
								.getSelectedIndex()));
					listFlags.setListData(mc.getFlags());
				} catch (MessagingException e) {
					System.out.println("Invalid or missing credentials!!");
				} catch (IOException e) {
					textBox.setText("Invalid or missing credentials!! ");
				}
			}
		});*/
		
		scrollPane.setVisible(false);

		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(340, 107, 420, 286);
		frame.getContentPane().add(scrollPane_1);

		textBox.setText("You are NOT connected to your account. \r\nPlease insert your username and password and press Sign in");
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
		 * will establish the connection to the DB and will set the
		 * JFrane accordingly for the next stage
		 */
		sign_in.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				try {

					
					password = new String(((JPasswordField) passwordField)
							.getPassword());
					username = txtUsername.getText();
					user.login(username, password);
				} catch (IllegalArgumentException e) {
					scrollPane.setVisible(false);
					textBox.setText("Invalid or missing credentials!!! ");

				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		sign_in.setBounds(663, 31, 97, 22);
		frame.getContentPane().add(sign_in);
		
		/**
		 * The mouseListener for the Sign in button , when the button is clicked
		 * will establish the connection to the DB and will set the
		 * JFrane accordingly for the next stage
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

					create_user.setBounds(700, 31, 117, 22);
					frame.getContentPane().add(create_user);

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
					
					txtUsername.setColumns(20);
					password = new String(((JPasswordField) passwordField)
							.getPassword());
					username = txtUsername.getText();
					user.createUser(username, password);
					create_user.setVisible(false);
					new_user.setVisible(true);
					sign_in.setVisible(true);
					txtUsername.setText("Username");
					txtUsername.setBounds(31, 31, 265, 22);
					frame.getContentPane().add(txtUsername);
				} catch (IllegalArgumentException e) {
					scrollPane.setVisible(false);
					textBox.setText("Invalid or missing credentials!!! ");

				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}
}
