package Etudiant;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import RMI.ServeurInterface;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Color;

public class Etudiant_chat extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private ServeurInterface si;
	private String classe;
	private String username;
	private JTextPane textPane;
	private Etudiant etudiant;
	private JPanel panel;
	private tableau_partager tp;
	
	/**
	 * Create the frame.
	 */
	public Etudiant_chat(ServeurInterface si,String classe,String username) throws RemoteException{
		this.si=si;
		this.classe=classe;
		this.username=username;
		tp=new tableau_partager(username,si,classe);
		final DefaultListModel<String> model = new DefaultListModel<>();
		final DefaultListModel<String> model1 = new DefaultListModel<>();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent e) {
		        try {
		            //supprimer un étudiant de la arraylist etudiants dans le serveur
					si.removeEtudiant(username);
			        e.getWindow().dispose();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
		setBounds(100, 100, 999, 559);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("list d'etudiants inscrit");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(31, 31, 190, 13);
		contentPane.add(lblNewLabel);
		
		JList list = new JList(model);
		list.setBackground(new Color(0, 0, 64));
		list.setForeground(new Color(255, 255, 255));
		list.setBounds(31, 54, 240, 91);
		contentPane.add(list);
		
		JLabel lblNewLabel_1 = new JLabel("list d'etudiants connectes");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(31, 151, 190, 13);
		contentPane.add(lblNewLabel_1);
		//recuperer les etudiants d'une classe
		String[] etudiants = si.findEtudiantsInscrit(classe).split(",");
		for (int i = 0; i < etudiants.length; i++) {
            model.addElement(etudiants[i]);
       }
		
		JLabel lblNewLabel_2 = new JLabel("ressources");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setBounds(31, 283, 77, 13);
		contentPane.add(lblNewLabel_2);
		
		JButton btnNewButton_1 = new JButton("tableau blanc");
		btnNewButton_1.setBackground(new Color(0, 0, 64));
		btnNewButton_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					tp.show();
				
			}
		});
		btnNewButton_1.setBounds(31, 462, 240, 32);
		contentPane.add(btnNewButton_1);
		
		textPane = new JTextPane();
		textPane.setBackground(new Color(0, 0, 64));
		textPane.setForeground(new Color(255, 255, 255));
		textPane.setBounds(281, 34, 655, 394);
		contentPane.add(textPane);
		
		JList list_1 = new JList(model1);
		list_1.setBackground(new Color(0, 0, 64));
		list_1.setForeground(new Color(255, 255, 255));
		list_1.setBounds(31, 166, 240, 83);
		contentPane.add(list_1);
		
		textField = new JTextField();
		textField.setBackground(new Color(0, 0, 64));
		textField.setForeground(new Color(255, 255, 255));
		textField.setColumns(10);
		textField.setBounds(281, 451, 560, 43);
		contentPane.add(textField);
		
		JButton btnNewButton = new JButton("envoyer");
		btnNewButton.setBackground(new Color(0, 0, 64));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//envoyer un message dans le chat
					etudiant.sendMessage();
	                textField.setText("");
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(851, 450, 85, 44);
		contentPane.add(btnNewButton);
		
		panel = new JPanel();
		panel.setForeground(new Color(255, 255, 255));
		panel.setBackground(new Color(0, 0, 64));
		panel.setBounds(31, 306, 240, 146);
		contentPane.add(panel);

		etudiant= new Etudiant(si,username,textField,textPane,panel,this.classe,tp);
		
		JButton btnNewButton_2 = new JButton("actualiser");
		btnNewButton_2.setBackground(new Color(0, 0, 64));
		btnNewButton_2.setForeground(new Color(255, 255, 255));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//actualiser la liste des etudiants qui sont connecter
					model1.clear();
					String[] etudiants_connecter = si.EtudiantsConnecter(classe).split(",");
					for (int i = 0; i < etudiants_connecter.length; i++) {
			            model1.addElement(etudiants_connecter[i]);
			       }
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_2.setBounds(110, 259, 77, 21);
		contentPane.add(btnNewButton_2);
		
		//recuperer les etudiants qui sont connecter
		String[] etudiants_connecter = si.EtudiantsConnecter(classe).split(",");
		for (int i = 0; i < etudiants_connecter.length; i++) {
            model1.addElement(etudiants_connecter[i]);
       }
		
		
		

	}
}
