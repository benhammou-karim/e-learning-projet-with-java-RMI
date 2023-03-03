package Etudiant;

import java.awt.EventQueue;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import RMI.ServeurInterface;

import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;

public class Classes_etudiant extends JFrame {

	private JPanel contentPane;
	private ServeurInterface si;
	private String username;
	private String classe="";
	
	public Classes_etudiant(ServeurInterface si,String username) throws RemoteException{
		this.si=si;
		this.username=username;
		final DefaultListModel<String> model = new DefaultListModel<>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 498, 378);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList list = new JList(model);
		list.setBackground(new Color(0, 0, 64));
		list.setForeground(new Color(255, 255, 255));
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				classe=list.getSelectedValue().toString();
			}
		});
		list.setBounds(179, 94, 78, 187);
		contentPane.add(list);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setForeground(new Color(0, 128, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel.setBounds(27, 60, 416, 22);
		contentPane.add(lblNewLabel);
		
		//recuperer les classes d'etudiant dans il est inscrits
		String[] classes = si.findClassesInscrit(username).split(",");
		for (int i = 0; i < classes.length; i++) {
            model.addElement(classes[i]);
       }
		
		JButton btnNewButton = new JButton("acceder");
		btnNewButton.setBackground(new Color(0, 0, 64));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(classes[0].equals(""))
					lblNewLabel.setText("il y a pas de classe pour vous");
				else if(classe.equals(""))
					lblNewLabel.setText("veuilez selectionner une classe");
				else {
					try {
						//acceder au chat de la classe choisis
						Etudiant_chat ec= new Etudiant_chat(si,list.getSelectedValue().toString(),username);
						ec.show();
						dispose();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setBounds(179, 289, 85, 30);
		contentPane.add(btnNewButton);
		
		JLabel lblLesClasses = new JLabel("LES CLASSES");
		lblLesClasses.setForeground(new Color(255, 255, 255));
		lblLesClasses.setHorizontalAlignment(SwingConstants.CENTER);
		lblLesClasses.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblLesClasses.setBounds(63, 10, 314, 40);
		contentPane.add(lblLesClasses);
		
		JButton btnRetourner = new JButton("retourner");
		btnRetourner.setBackground(new Color(0, 0, 64));
		btnRetourner.setForeground(new Color(255, 255, 255));
		btnRetourner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//retourner a la page d'acceuill
				Etudiant_home eh = new Etudiant_home(si,username);
				eh.show();
				dispose();
			}
		});
		btnRetourner.setBounds(10, 301, 99, 30);
		contentPane.add(btnRetourner);
		
		
		
		
	}

}
