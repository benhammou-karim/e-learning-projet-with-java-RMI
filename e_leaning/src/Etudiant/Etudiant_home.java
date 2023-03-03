package Etudiant;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import RMI.ServeurInterface;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Etudiant_home extends JFrame {

	private JPanel contentPane;
	private ServeurInterface si;
	private String username;

	/**
	 * Create the frame.
	 */
	public Etudiant_home(ServeurInterface si,String username) {
		this.si=si;
		this.username=username;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 405, 278);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEtudiantHome = new JLabel("WELCOME "+username);
		lblEtudiantHome.setForeground(new Color(255, 255, 255));
		lblEtudiantHome.setHorizontalAlignment(SwingConstants.CENTER);
		lblEtudiantHome.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblEtudiantHome.setBounds(77, 33, 238, 40);
		contentPane.add(lblEtudiantHome);
		
		JButton btnListDesClasses = new JButton("List des classes");
		btnListDesClasses.setBackground(new Color(0, 0, 64));
		btnListDesClasses.setForeground(new Color(255, 255, 255));
		btnListDesClasses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//afficher l'interface d'inscription du l'etudiant dans une classe
				list_classes lc;
				try {
					lc = new list_classes(si,username);
					lc.show();
					dispose();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnListDesClasses.setBounds(111, 83, 137, 29);
		contentPane.add(btnListDesClasses);
		
		JButton btnMesClasses = new JButton("Mes Classes");
		btnMesClasses.setBackground(new Color(0, 0, 64));
		btnMesClasses.setForeground(new Color(255, 255, 255));
		btnMesClasses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//afficher l'interface des classes d'etudiant dans il est inscrit
				try {
					Classes_etudiant ce = new Classes_etudiant(si,username);
					ce.show();
					dispose();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnMesClasses.setBounds(111, 117, 137, 29);
		contentPane.add(btnMesClasses);
		
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.setBackground(new Color(0, 0, 64));
		btnQuitter.setForeground(new Color(255, 255, 255));
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnQuitter.setBounds(111, 151, 137, 29);
		contentPane.add(btnQuitter);
	}

}
