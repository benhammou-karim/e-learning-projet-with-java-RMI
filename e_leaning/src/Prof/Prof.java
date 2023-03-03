package Prof;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import Etudiant.tableau_partager;
import RMI.ProfilInterface;
import RMI.ServeurInterface;

public class Prof extends UnicastRemoteObject implements ProfilInterface{
	private ServeurInterface si;
    private String username;
    private JTextField textField;
    private JTextPane textPane;
    private JPanel panel;
    private String classe;    
    private Tableau_blanc tb;

    
    public Prof(ServeurInterface si,String username,JTextField textField,JTextPane textPane,JPanel panel,String classe,Tableau_blanc tb) throws RemoteException{
        this.username = username;
        this.si = si;
        this.textField = textField;
        this.textPane = textPane;
        this.panel = panel;
        this.classe=classe;
        this.tb=tb;
        si.addProf(this);
    }

	//cette fonction pour afficher un message defuser au prof et a tous les etudiants de la classe
	@Override
	public void afficherMessage(String message) throws RemoteException {
		StyledDocument chat = textPane.getStyledDocument();
	       try {
	        chat.insertString(chat.getLength(), message + "\n", null);
	       } catch (BadLocationException e1) {
	            e1.printStackTrace();
	       }
		
	}

	//cette fonction pour envoyer un message au prof et a tous les etudiants de la classe
	@Override
	public void sendMessage() throws RemoteException {
		try {
			si.broadcastMessage(username + " : " + textField.getText(),classe);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	//cette fonction pour afficher un fichier defuser a tous les etudiants de la classe d'apres le prof
	public void afficherFile(ArrayList<Integer> pointeur, String nomfichier) throws RemoteException {
		JLabel label = new JLabel("<HTML><U><font size=\"4\" color=\"#365899\">" + nomfichier + "</font></U></HTML>");
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				  try {
	                    FileOutputStream outputstream = new FileOutputStream(System.getProperty("user.home") + "\\" + nomfichier);
	                    String[] ext = nomfichier.split("\\.");
	                    for (int i = 0; i<pointeur.size(); i++) {
	                        int pt = pointeur.get(i);
	                        if(ext[ext.length - 1].equals("txt")|| ext[ext.length - 1].equals("png")|| ext[ext.length - 1].equals("jpg")||
	        		                ext[ext.length - 1].equals("jpeg")|| ext[ext.length - 1].equals("pdf")|| ext[ext.length - 1].equals("docx")||
	        		                ext[ext.length - 1].equals("rar")|| ext[ext.length - 1].equals("doc")
	        		            )
	                        outputstream.write((char)pt);
	                        else{
	                        	outputstream.write((byte)pt);
	                        }
	                    }
	                    outputstream.flush();
	                    outputstream.close();
	                    JOptionPane.showMessageDialog(new JFrame(),"le fichier a ete enregistré dans " + System.getProperty("user.home") + "\\" + nomfichier," Saved",JOptionPane.INFORMATION_MESSAGE);
	                } catch (FileNotFoundException ex) {
	                    System.out.println("Error: " + ex.getMessage());
	                } catch (IOException ex) {
	                    System.out.println("Error: " + ex.getMessage());
	                }  				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
           
        });
        panel.add(label);
        panel.repaint();
        panel.revalidate();
	}

	//cette fonction pour recuperer le nom du l'etudiant
	@Override
	public String getUsername() throws RemoteException {
		// TODO Auto-generated method stub
		return username;
	}

	//cette fonction pour recuperer la classe de l'etudiant
	@Override
	public String getClasse() throws RemoteException {
		// TODO Auto-generated method stub
		return classe;
	}

	//cette fonction pour autoriser ou interdir l'etudiant a ecrire dans le tableau blanc
	@Override
	public void setAutorisation(boolean autorisation) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	//cette fonction pour recuperer le tableau blanc de prof
	@Override
	public Tableau_blanc getTableauB() throws RemoteException {
		// TODO Auto-generated method stub
		return tb;
	}
	
	//cette fonction pour partager le dessin du prof dans le tableau blanc de l'etudiant ou l'inverse
	public void partage(int x, int y,Color color) throws RemoteException {
		// TODO Auto-generated method stub
		tb.modify(color,x,y);
		  tb.repaint();
	
	}
	
	@Override
	public tableau_partager getTableau() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
