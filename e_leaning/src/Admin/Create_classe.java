package Admin;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import RMI.ServeurInterface;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Create_classe extends JFrame {

	private JPanel contentPane;
	private ServeurInterface si;
	private JTextField textField;

	
	/**
	 * Create the frame.
	 */
	public Create_classe(ServeurInterface si) {
		this.si=si;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 355, 240);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCreateClasse = new JLabel("Create classe");
		lblCreateClasse.setForeground(new Color(255, 255, 255));
		lblCreateClasse.setBackground(new Color(255, 255, 255));
		lblCreateClasse.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblCreateClasse.setBounds(67, 10, 180, 40);
		contentPane.add(lblCreateClasse);
		
		textField = new JTextField();
		textField.setBounds(79, 91, 168, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Libelle :");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(24, 94, 45, 13);
		contentPane.add(lblNewLabel);
		

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_1.setForeground(new Color(0, 128, 255));
		lblNewLabel_1.setBounds(24, 60, 223, 21);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Create");
		btnNewButton.setBackground(new Color(0, 0, 64));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//creer une classe
				if(textField.getText().equals(""))
					lblNewLabel_1.setText("veuillez saisir le libelle");
				else {
					try {
						lblNewLabel_1.setText(si.addClasse(textField.getText().trim()));
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setBounds(148, 131, 99, 40);
		contentPane.add(btnNewButton);
		
		JButton btnRetourner = new JButton("retourner");
		btnRetourner.setBackground(new Color(0, 0, 64));
		btnRetourner.setForeground(new Color(255, 255, 255));
		btnRetourner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//retourner a la page d'acceuill
				Admin_home ah = new Admin_home(si);
				ah.show();
				dispose();
			}
		});
		btnRetourner.setBounds(24, 131, 99, 40);
		contentPane.add(btnRetourner);
		
	}

}
