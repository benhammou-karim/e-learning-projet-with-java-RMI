package Admin;

import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.util.Arrays;

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

public class Gestion_classe extends JFrame {

	private JPanel contentPane;
	private ServeurInterface si;

	
	/**
	 * Create the frame.
	 * @throws RemoteException 
	 */
	public Gestion_classe(ServeurInterface si) throws RemoteException {
		this.si=si;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 449, 350);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBackground(new Color(0, 0, 64));
		comboBox_1.setForeground(new Color(255, 255, 255));
		comboBox_1.setBounds(171, 153, 187, 21);
		contentPane.add(comboBox_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBackground(new Color(0, 0, 64));
		comboBox.setForeground(new Color(255, 255, 255));
		//combobox (onchange event) 
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getItemCount() != 0) {
					String[] notifications;
					try {
						comboBox_1.removeAllItems();
						//recuperer la classe dans la demande de l'etudiant selectionner
						notifications = si.findClasseForEtudiant(comboBox.getSelectedItem().toString()).split(",");
						for (int i = 0; i < notifications.length; i++) {
					    	   comboBox_1.addItem(notifications[i]);
					       }
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}	
			}
		});
		comboBox.setBounds(171, 100, 187, 21);
		contentPane.add(comboBox);
		
		
		
		JLabel lblNewLabel = new JLabel("etudiant");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(38, 104, 45, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("classe");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(38, 157, 45, 13);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_2.setForeground(new Color(0, 128, 255));
		lblNewLabel_2.setBounds(10, 60, 415, 30);
		contentPane.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Accepter");
		btnNewButton.setBackground(new Color(0, 0, 64));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getItemCount()==0 || comboBox.getSelectedItem().toString().equals("") || comboBox_1.getItemCount()==0 || comboBox_1.getSelectedItem().toString().equals(""))
					lblNewLabel_2.setText("il y a aucune demande");
				else {
					try {
						//accepter une demande
						lblNewLabel_2.setText(si.accepter(comboBox_1.getSelectedItem().toString(),comboBox.getSelectedItem().toString()));
						comboBox.removeAllItems();
						comboBox_1.removeAllItems();
						//recuperer les noms des etudiant qui on une demande en fils d'attente
						String[] notifications = removeDuplicates(si.findEtudiants().split(","));
							for (int i = 0; i < notifications.length; i++) {
						    	   comboBox.addItem(notifications[i]);
						       }
							
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setBounds(86, 194, 104, 40);
		contentPane.add(btnNewButton);
		
		JButton btnRefuser = new JButton("Refuser");
		btnRefuser.setBackground(new Color(0, 0, 64));
		btnRefuser.setForeground(new Color(255, 255, 255));
		btnRefuser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getItemCount()==0 || comboBox.getSelectedItem().toString().equals("") || comboBox_1.getItemCount()==0 || comboBox_1.getSelectedItem().toString().equals(""))
					lblNewLabel_2.setText("il y a aucune demande");
				else {
					try {
						//refuser la demande
						lblNewLabel_2.setText(si.refuser(comboBox_1.getSelectedItem().toString(),comboBox.getSelectedItem().toString()));
						comboBox.removeAllItems();
						comboBox_1.removeAllItems();
						//recuperer les noms des etudiant qui on une demande en fils d'attente
						String[] notifications = removeDuplicates(si.findEtudiants().split(","));
						for (int i = 0; i < notifications.length; i++) {
					    	   comboBox.addItem(notifications[i]);
					       }
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnRefuser.setBounds(211, 194, 110, 40);
		contentPane.add(btnRefuser);
		
		JLabel lblGestionDuneClasse = new JLabel("GESTION D'UNE CLASSE");
		lblGestionDuneClasse.setForeground(new Color(255, 255, 255));
		lblGestionDuneClasse.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblGestionDuneClasse.setBounds(86, 10, 256, 40);
		contentPane.add(lblGestionDuneClasse);
		
		
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
		btnNewButton_1.setBounds(10, 263, 104, 40);
		contentPane.add(btnNewButton_1);
		//recuperer les noms des etudiant qui on une demande en fils d'attente
		 String[] notifications = removeDuplicates(si.findEtudiants().split(","));
		for (int i = 0; i < notifications.length; i++) {
	    	   comboBox.addItem(notifications[i]);
	       }
		
		
	}
	
	public String[] removeDuplicates(String[] arr) {
		Arrays.sort(arr);
		int k = 0;
		for (int i = 0; i < arr.length; i++) {
			if (i == 0 || !arr[i].equals(arr[i - 1])) {
				arr[k++]=arr[i];
			}
		}
		return Arrays.copyOf(arr, k);
	}
}
