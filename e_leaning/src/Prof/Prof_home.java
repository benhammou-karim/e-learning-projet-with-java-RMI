package Prof;

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

public class Prof_home extends JFrame {

	private JPanel contentPane;
	private ServeurInterface si;
	private String username;


	/**
	 * Create the frame.
	 */
	public Prof_home(ServeurInterface si,String username) {
		this.si=si;
		this.username=username;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 348, 302);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblProfHome = new JLabel("WELCOME SIR "+username);
		lblProfHome.setForeground(new Color(255, 255, 255));
		lblProfHome.setHorizontalAlignment(SwingConstants.CENTER);
		lblProfHome.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblProfHome.setBounds(10, 10, 314, 40);
		contentPane.add(lblProfHome);
		
		JButton btnListDesClasses = new JButton("list des classes");
		btnListDesClasses.setBackground(new Color(0, 0, 64));
		btnListDesClasses.setForeground(new Color(255, 255, 255));
		btnListDesClasses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//afficher l'interface des classes du prof
					Classes_prof cp = new Classes_prof(si,username);
					cp.show();
					dispose();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnListDesClasses.setBounds(88, 85, 137, 29);
		contentPane.add(btnListDesClasses);
		
		JButton btnNewButton_4 = new JButton("Quitter");
		btnNewButton_4.setBackground(new Color(0, 0, 64));
		btnNewButton_4.setForeground(new Color(255, 255, 255));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_4.setBounds(88, 117, 137, 29);
		contentPane.add(btnNewButton_4);
	}

}
