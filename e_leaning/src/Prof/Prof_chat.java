package Prof;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import RMI.ServeurInterface;

import javax.swing.JTextPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Prof_chat extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private ServeurInterface si;
	private String classe;
	private String username;
	private JTextPane textPane;
	private Prof prof;
	private JPanel panel;
	private Tableau_blanc tb;

	/**
	 * Create the frame.
	 */
	public Prof_chat(ServeurInterface si,String classe,String username) throws RemoteException{
		this.si=si;
		this.classe=classe;
		this.username=username;
		final DefaultListModel<String> model = new DefaultListModel<>();
		final DefaultListModel<String> model1 = new DefaultListModel<>();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent e) {
		        try {
		            //supprimer un prof de la arraylist profs dans le serveur
					si.removeProf(username);
			        e.getWindow().dispose();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
		tb = new Tableau_blanc(si,classe);
		setBounds(100, 100, 973, 542);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textPane = new JTextPane();
		textPane.setForeground(new Color(255, 255, 255));
		textPane.setBackground(new Color(0, 0, 64));
		textPane.setBounds(272, 35, 655, 394);
		contentPane.add(textPane);
		
		JButton btnNewButton = new JButton("envoyer");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(0, 0, 64));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//envoyer un message
					prof.sendMessage();
	                textField.setText("");
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(842, 451, 85, 44);
		contentPane.add(btnNewButton);
		
		textField = new JTextField();
		textField.setBackground(new Color(0, 0, 64));
		textField.setForeground(new Color(255, 255, 255));
		textField.setBounds(272, 452, 560, 43);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JList list = new JList(model);
		list.setBackground(new Color(0, 0, 64));
		list.setForeground(new Color(255, 255, 255));
		list.setBounds(22, 55, 240, 91);
		contentPane.add(list);
		
		//recuperer les etudiants d'une classe
		String[] etudiants = si.findEtudiantsInscrit(classe).split(",");
		for (int i = 0; i < etudiants.length; i++) {
            model.addElement(etudiants[i]);
       }
		
		JList list_1 = new JList(model1);
		list_1.setBackground(new Color(0, 0, 64));
		list_1.setForeground(new Color(255, 255, 255));
		list_1.setBounds(22, 175, 240, 81);
		contentPane.add(list_1);
		
		JLabel lblNewLabel = new JLabel("les etudiants inscrits");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(22, 32, 158, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("les etudiants connectes");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(22, 152, 158, 13);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("ressources");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setBounds(22, 292, 132, 13);
		contentPane.add(lblNewLabel_2);
		
		JButton btnNewButton_1 = new JButton("tableau blanc");
		btnNewButton_1.setBackground(new Color(0, 0, 64));
		btnNewButton_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tb.show();
			}
		});
		btnNewButton_1.setBounds(22, 463, 240, 32);
		contentPane.add(btnNewButton_1);
		
		
		panel = new JPanel();
		panel.setForeground(new Color(255, 255, 255));
		panel.setBackground(new Color(0, 0, 64));
		panel.setBounds(22, 315, 240, 114);
		contentPane.add(panel);
		
		prof = new Prof(si,username,textField,textPane,panel,classe,tb);
		
		JButton btnNewButton_2 = new JButton("browse");
		btnNewButton_2.setBackground(new Color(0, 0, 64));
		btnNewButton_2.setForeground(new Color(255, 255, 255));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//choisir un fichier depuis son pc 
				JFileChooser filechooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		        int file = filechooser.showOpenDialog(null);
		        if (file == JFileChooser.APPROVE_OPTION) {
		            File fichier = filechooser.getSelectedFile();
		            String[] ext = fichier.getName().split("\\.");
		            if(ext[ext.length - 1].equals("txt")|| ext[ext.length - 1].equals("png")|| ext[ext.length - 1].equals("jpg")||
		                ext[ext.length - 1].equals("jpeg")|| ext[ext.length - 1].equals("pdf")|| ext[ext.length - 1].equals("docx")||
		                ext[ext.length - 1].equals("rar")|| ext[ext.length - 1].equals("doc")
		            ){
		                try {
		                    ArrayList<Integer> pointeur;
		                    try (FileInputStream inputstream = new FileInputStream(fichier)) {
		                    	pointeur = new ArrayList<Integer>();
		                        int input=0;
		                        while((input=inputstream.read()) != -1) {
		                        	pointeur.add(input);
		                        }
		                        inputstream.close();
		                    }
		                    //envoyer le fichier au etudiants
		                    si.broadcastFile(pointeur,fichier.getName(),classe);
		                } catch (FileNotFoundException e1) {
		                    System.out.println(e1.getMessage());
		                } catch (RemoteException e1) {
		                    System.out.println(e1.getMessage());
		                } catch (IOException e1) {
		                    System.out.println("Error: " + e1.getMessage());
		                }
		            }
		            else {
	                    JOptionPane.showMessageDialog(new JFrame(),"veuillez choisir un fichier d'extention (txt,png,jpg,jpeg,pdf,docx,doc,rar)"," enregistrer",JOptionPane.INFORMATION_MESSAGE);
		            }
		        }
			}
		});
		btnNewButton_2.setBounds(22, 439, 240, 21);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_2_1 = new JButton("autoriser");
		btnNewButton_2_1.setBackground(new Color(0, 0, 64));
		btnNewButton_2_1.setForeground(new Color(255, 255, 255));
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//autoriser etudiant a ecrire dans le tableau blanc
					si.autoriserEtudiant(true,list_1.getSelectedValue().toString());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_2_1.setBounds(103, 261, 77, 21);
		contentPane.add(btnNewButton_2_1);
		
		JButton btnNewButton_2_2 = new JButton("actualiser");
		btnNewButton_2_2.setBackground(new Color(0, 0, 64));
		btnNewButton_2_2.setForeground(new Color(255, 255, 255));
		btnNewButton_2_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model1.clear();
				String[] etudiants_connecter;
				try {
					//actualiser la liste des etudiants qui sont connecter
					etudiants_connecter = si.EtudiantsConnecterForProf(classe).split(",");
					for (int i = 0; i < etudiants_connecter.length; i++) {
			            model1.addElement(etudiants_connecter[i]);
			       }
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

			}
		});
		btnNewButton_2_2.setBounds(22, 261, 77, 21);
		contentPane.add(btnNewButton_2_2);
		
		JButton btnNewButton_2_1_1 = new JButton("interdire");
		btnNewButton_2_1_1.setBackground(new Color(0, 0, 64));
		btnNewButton_2_1_1.setForeground(new Color(255, 255, 255));
		btnNewButton_2_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//interdire l'etudiant d'ecrire dans le tableau
					si.autoriserEtudiant(false,list_1.getSelectedValue().toString());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_2_1_1.setBounds(185, 261, 77, 21);
		contentPane.add(btnNewButton_2_1_1);
		
		//recuperer les etudiants qui sont connecter 
		String[] etudiants_connecter = si.EtudiantsConnecterForProf(classe).split(",");
		for (int i = 0; i < etudiants_connecter.length; i++) {
            model1.addElement(etudiants_connecter[i]);
       }

	}
}
