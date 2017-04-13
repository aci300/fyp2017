package pwd.manager.GUI;


import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;

import pwd.manager.hibernate.model.Database;

import javax.swing.event.ListSelectionEvent;


public class DBSettings {
	
	private JFrame frame;
	public  String password;
	public  String DBusername;
	public  String DBUrl;
	private JPasswordField passwordField;
	private JScrollPane scrollPane;

	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DBSettings window = new DBSettings();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public DBSettings(){
		initialize(); 
	}
	
	
	private void initialize() {
		
		final JTextField DBUsername = new JTextField();
		final JTextField DBurl = new JTextField();
		
		final JButton apply = new JButton("Apply");
		final JButton close = new JButton("Close");

		
		frame = new JFrame("Database settings");
		frame.setBounds(200, 200, 330, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(200, 200, 300, 300);
		frame.getContentPane().add(scrollPane);
		
		scrollPane.setVisible(false);
		
		DBUsername.setText("Database Username");
		DBUsername.setBounds(30, 60, 250, 22);
		frame.getContentPane().add(DBUsername);
		DBUsername.setColumns(20);
		
		DBurl.setText("Database URL - PostgreSql/MySQL");
		DBurl.setBounds(30, 30, 250, 22);
		frame.getContentPane().add(DBurl);
		DBurl.setColumns(40);
		
		passwordField = new JPasswordField();
		passwordField.setText("Password");
		passwordField.setBounds(30, 90, 250, 22);
		frame.getContentPane().add(passwordField);
		
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frame.dispose();  
			}
		}); 
		close.setBounds(170, 140, 70, 20);
		frame.getContentPane().add(close);
		
		apply.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				password = new String(((JPasswordField) passwordField)
						.getPassword());
				DBusername = DBUsername.getText();
				DBUrl = DBurl.getText();
				Database db = new Database(DBusername, password, DBUrl); 
				try {
					db.setDBProperties();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				frame.dispose();  
			}
		}); 
		apply.setBounds(60, 140, 70, 20);
		frame.getContentPane().add(apply);
		
		
	}
}
