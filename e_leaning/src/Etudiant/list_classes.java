package Etudiant;

import java.awt.EventQueue;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;

import RMI.ServeurInterface;

import javax.swing.event.ListSelectionEvent;
import java.awt.Color;

public class list_classes extends JFrame {

	private JPanel contentPane;
	private ServeurInterface si;
	private String username;
	private String classe="";
	
	/**
	 * Create the frame.
	 * @throws RemoteException 
	 */
	public list_classes(ServeurInterface si,String username) throws RemoteException {
		this.si=si;
		this.username=username;
		final DefaultListModel<String> model = new DefaultListModel<>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 399, 456);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList list = new JList(model);
		list.setForeground(new Color(255, 255, 255));
		list.setBackground(new Color(0, 0, 64));
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(list.getSelectedIndex() != -1)
					classe= list.getSelectedValue().toString();
			}
		});
		list.setBounds(148, 96, 102, 259);
		contentPane.add(list);
		
		//recuperer les classes dans l'etudiant n'est pas inscrit
		String[] classes = si.findClasses(username).split(",");
		for (int i = 0; i < classes.length; i++) {
            model.addElement(classes[i]);
       }
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel.setForeground(new Color(0, 128, 255));
		lblNewLabel.setBounds(10, 60, 365, 26);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("s'inscrire");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(0, 0, 64));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(classe.equals(""))
					lblNewLabel.setText("veuilez selectionner une classe");
				else {
					try {
						//s'inscrire dans une classe
						model.remove(list.getSelectedIndex());
						lblNewLabel.setText(si.inscrire(classe,username));
						classe="";
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setBounds(142, 369, 108, 27);
		contentPane.add(btnNewButton);
		
		JLabel lblEtudiantHome = new JLabel("WELCOME <dynamic>");
		lblEtudiantHome.setForeground(new Color(255, 255, 255));
		lblEtudiantHome.setHorizontalAlignment(SwingConstants.CENTER);
		lblEtudiantHome.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblEtudiantHome.setBounds(84, 10, 238, 40);
		contentPane.add(lblEtudiantHome);
		
		JButton btnRetourner = new JButton("retourner");
		btnRetourner.setForeground(new Color(255, 255, 255));
		btnRetourner.setBackground(new Color(0, 0, 64));
		btnRetourner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//retourner a la page d'acceuill
				Etudiant_home eh = new Etudiant_home(si,username);
				eh.show();
				dispose();
			}
		});
		btnRetourner.setBounds(10, 382, 108, 27);
		contentPane.add(btnRetourner);
		
		
		
		
		
		
	}
}
