package Prof;

import java.awt.EventQueue;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.event.ListSelectionListener;

import RMI.ServeurInterface;

import javax.swing.event.ListSelectionEvent;

public class Classes_prof extends JFrame {

	private JPanel contentPane;
	private ServeurInterface si;
	private String username;
	private String classe="";

	/**
	 * Create the frame.
	 */
	public Classes_prof(ServeurInterface si,String username) throws RemoteException {
		this.si=si;
		this.username=username;
		final DefaultListModel<String> model = new DefaultListModel<>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 438, 391);
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
				classe=list.getSelectedValue().toString();
			}
		});
		list.setBounds(165, 94, 78, 187);
		contentPane.add(list);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel.setForeground(new Color(0, 128, 255));
		lblNewLabel.setBounds(10, 51, 416, 22);
		contentPane.add(lblNewLabel);
		
		
		JButton btnRetourner = new JButton("retourner");
		btnRetourner.setBackground(new Color(0, 0, 64));
		btnRetourner.setForeground(new Color(255, 255, 255));
		btnRetourner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//retourner a la page d'acceuill
				Prof_home ph = new Prof_home(si,username);
				ph.show();
				dispose();
			}
		});
		btnRetourner.setBounds(10, 313, 99, 30);
		contentPane.add(btnRetourner);
		
		JLabel lblLesClasses = new JLabel("LES CLASSES");
		lblLesClasses.setForeground(new Color(255, 255, 255));
		lblLesClasses.setHorizontalAlignment(SwingConstants.CENTER);
		lblLesClasses.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblLesClasses.setBounds(44, 10, 314, 40);
		contentPane.add(lblLesClasses);
		
		
		//recuperer les classes du prof dans il est affecter 
		String[] classes = si.findClassesProf(username).split(",");
		for (int i = 0; i < classes.length; i++) {
            model.addElement(classes[i]);
       }
		
		JButton btnNewButton = new JButton("acceder");
		btnNewButton.setBackground(new Color(0, 0, 64));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//acceder au chat de la classe choisis
				if(classes[0].equals(""))
					lblNewLabel.setText("il y a pas de classe pour vous");
				else if(classe.equals(""))
					lblNewLabel.setText("veuilez selectionner une classe");
				else {
					try {
						Prof_chat pc= new Prof_chat(si,classe,username);
						pc.show();
						dispose();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		btnNewButton.setBounds(165, 291, 85, 30);
		contentPane.add(btnNewButton);
	}

}
