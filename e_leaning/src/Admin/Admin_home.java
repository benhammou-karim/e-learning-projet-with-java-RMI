package Admin;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import RMI.ServeurInterface;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Admin_home extends JFrame {

	private JPanel contentPane;
	private ServeurInterface si;

	/**
	 * Create the frame.
	 */
	public Admin_home(ServeurInterface si) {
		this.si=si;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 254, 312);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ADMIN HOME");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblNewLabel.setBounds(39, 10, 180, 40);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Creer un compte");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(0, 0, 64));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//afficher l'interface pour creer un compte etudiant ou prof
				Create_acc ca = new Create_acc(si);
				ca.show();
				dispose();
			}
		});
		btnNewButton.setBounds(39, 60, 137, 29);
		contentPane.add(btnNewButton);
		
		JButton btnCreerUneClasse = new JButton("Creer une classe");
		btnCreerUneClasse.setBackground(new Color(0, 0, 64));
		btnCreerUneClasse.setForeground(new Color(255, 255, 255));
		btnCreerUneClasse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//afficher l'interface pour creer une classe
				Create_classe cc = new Create_classe(si);
				cc.show();
				dispose();
			}
		});
		btnCreerUneClasse.setBounds(39, 94, 137, 29);
		contentPane.add(btnCreerUneClasse);
		
		JButton btnAffecterProf = new JButton("affecter prof");
		btnAffecterProf.setBackground(new Color(0, 0, 64));
		btnAffecterProf.setForeground(new Color(255, 255, 255));
		btnAffecterProf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//afficher l'interface pour affecter un prof a une classe
				Affecter_prof ap;
				try {
					ap = new Affecter_prof(si);
					ap.show();
					dispose();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnAffecterProf.setBounds(39, 128, 137, 29);
		contentPane.add(btnAffecterProf);
		
		JButton btnGererUneClasse = new JButton("gerer une classe");
		btnGererUneClasse.setBackground(new Color(0, 0, 64));
		btnGererUneClasse.setForeground(new Color(255, 255, 255));
		btnGererUneClasse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//afficher l'interface pour la gestion des demandes (accepter/refuser)
				try {
					Gestion_classe gc = new Gestion_classe(si);
					gc.show();
					dispose();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGererUneClasse.setBounds(39, 164, 137, 29);
		contentPane.add(btnGererUneClasse);
		
		JButton btnNewButton_4 = new JButton("Quitter");
		btnNewButton_4.setBackground(new Color(0, 0, 64));
		btnNewButton_4.setForeground(new Color(255, 255, 255));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_4.setBounds(39, 198, 137, 29);
		contentPane.add(btnNewButton_4);
	}

}
