package Admin;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import RMI.ServeurInterface;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Create_acc extends JFrame {

	private JPanel contentPane;
	private ServeurInterface si;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Create the frame.
	 */
	public Create_acc(ServeurInterface si) {
		this.si=si;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 398, 343);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCreateAcc = new JLabel("Create Account");
		lblCreateAcc.setForeground(new Color(255, 255, 255));
		lblCreateAcc.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblCreateAcc.setBounds(87, 10, 180, 40);
		contentPane.add(lblCreateAcc);
		
		textField = new JTextField();
		textField.setBounds(127, 98, 180, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(127, 136, 180, 19);
		contentPane.add(passwordField);
		
		JLabel lblNewLabel = new JLabel("login :");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(37, 101, 45, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_2.setForeground(new Color(0, 128, 255));
		lblNewLabel_2.setBounds(37, 60, 270, 28);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblPassword = new JLabel("password :");
		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setBounds(37, 139, 63, 13);
		contentPane.add(lblPassword);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBackground(new Color(0, 0, 64));
		comboBox.setForeground(new Color(255, 255, 255));
		comboBox.setBounds(127, 183, 180, 21);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel_1 = new JLabel("profil :");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(37, 187, 45, 13);
		contentPane.add(lblNewLabel_1);
		
		comboBox.addItem("etudiant");
		comboBox.addItem("professeur");
		
		JButton btnNewButton = new JButton("create");
		btnNewButton.setBackground(new Color(0, 0, 64));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//creer un compte etudiant ou prof
				if(textField.getText().equals(""))
					lblNewLabel_2.setText("veuillez saisir le login");
				else if (passwordField.getText().equals(""))
					lblNewLabel_2.setText("veuillez saisir le password");
				else {
					try {
						lblNewLabel_2.setText(si.addAccount(comboBox.getSelectedItem().toString(),textField.getText().trim(),passwordField.getText().trim()));
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}	
			}
		});
		btnNewButton.setBounds(196, 231, 111, 40);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("retourner");
		btnNewButton_1.setBackground(new Color(0, 0, 64));
		btnNewButton_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//retourner a la page d'acceuill
				Admin_home ah = new Admin_home(si);
				ah.show();
				dispose();
			}
		});
		btnNewButton_1.setBounds(36, 231, 94, 40);
		contentPane.add(btnNewButton_1);
		
		
	}
}
