package Admin;

import java.awt.EventQueue;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import RMI.ServeurInterface;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class Affecter_prof extends JFrame {

	private JPanel contentPane;
	private ServeurInterface si;

	/**
	 * Create the frame.
	 * @throws RemoteException 
	 */
	public Affecter_prof(ServeurInterface si) throws RemoteException {
		this.si=si;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 573, 288);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setForeground(new Color(255, 255, 255));
		comboBox.setBackground(new Color(0, 0, 64));
		comboBox.setBounds(260, 83, 136, 21);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("classes :");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(164, 87, 86, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("professeurs :");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(164, 131, 86, 13);
		contentPane.add(lblNewLabel_1);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setForeground(new Color(255, 255, 255));
		comboBox_1.setBackground(new Color(0, 0, 64));
		comboBox_1.setBounds(260, 127, 136, 21);
		contentPane.add(comboBox_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_2.setForeground(new Color(0, 128, 255));
		lblNewLabel_2.setBounds(10, 52, 539, 21);
		contentPane.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("affecter");
		btnNewButton.setBackground(new Color(0, 0, 64));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Affecter un prof a une classe
				if(comboBox.getItemCount()==0 || comboBox.getSelectedItem().toString().equals(""))
					lblNewLabel_2.setText("tous les classes ont deja ete affecte");
				else {
					try {
						lblNewLabel_2.setText(si.AffecterProf(comboBox.getSelectedItem().toString(),comboBox_1.getSelectedItem().toString()));
						comboBox.removeItem(comboBox.getSelectedItem().toString());
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setBounds(285, 178, 96, 40);
		contentPane.add(btnNewButton);
		
		JLabel lblAffectationDesProfs = new JLabel("AFFECTATION DES PROFS");
		lblAffectationDesProfs.setForeground(new Color(255, 255, 255));
		lblAffectationDesProfs.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblAffectationDesProfs.setBounds(136, 10, 284, 40);
		contentPane.add(lblAffectationDesProfs);
		
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
		btnRetourner.setBounds(164, 178, 96, 40);
		contentPane.add(btnRetourner);
		
		
		//recuperer les classes qui n'ont pas de prof
		String[] classes = si.findClassesAffectation().split(",");
		
		for (int i = 0; i < classes.length; i++) {
	    	   comboBox.addItem(classes[i]);
	       }
		//recuperer les profs
		String[] professeurs = si.findProfs().split(",");
		
		for (int i = 0; i < professeurs.length; i++) {
	    	   comboBox_1.addItem(professeurs[i]);
	       }
	}

}
