package e_learning;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Admin.Admin_home;
import Etudiant.Etudiant_home;
import Prof.Prof_home;
import RMI.ServeurInterface;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.awt.event.ActionEvent;

public class Home extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private String url = "rmi://localhost/irisi";
    
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Home() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 505, 360);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("E-Learning Application");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(115, 10, 239, 48);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Login :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(63, 130, 70, 19);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("password :");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_2.setBounds(48, 199, 85, 19);
		contentPane.add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setBounds(191, 130, 208, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(191, 199, 208, 19);
		contentPane.add(passwordField);
		

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setForeground(new Color(0, 128, 255));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_3.setBounds(88, 68, 311, 30);
		contentPane.add(lblNewLabel_3);
		
		JButton btnNewButton = new JButton("LOGIN");
		btnNewButton.setBackground(new Color(0, 0, 64));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//recuperer une reference pour l'objet distant
					ServeurInterface si = (ServeurInterface) Naming.lookup(url);
					//verifier le login et le password de user si il est etudiant ou prof ou admin
				    String verify = si.verify(textField.getText().trim(),passwordField.getText().trim()).trim();
					if(verify.equals("admin")) {
						//afficher l'interface d'accueil pour l'admin
						Admin_home ah =  new Admin_home(si);
						ah.show();
						dispose();
					}else if(verify.equals("professeur")) {
						//afficher l'interface d'accueil pour le prof
						Prof_home ph =  new Prof_home(si,textField.getText().trim());
						ph.show();
						dispose();
					}else if(verify.equals("etudiant")) {
						//afficher l'interface d'accueil pour l'etudiant
						Etudiant_home eh =  new Etudiant_home(si,textField.getText().trim());
						eh.show();
						dispose();
					}else if(verify.equals("false")) {
						//afficher un message d'erreur
						lblNewLabel_3.setText("Login ou password est incorrect");
					}
				} catch (MalformedURLException | RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(154, 262, 113, 35);
		contentPane.add(btnNewButton);
		
	}
}
