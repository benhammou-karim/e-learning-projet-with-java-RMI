package Etudiant;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import RMI.ServeurInterface;

public class tableau_partager extends JFrame implements MouseMotionListener{

	private JPanel contentPane;
	private int x;
	private int y;
	private Color color;
	private boolean autorisation=false; 
	private String username;
	private String classe;
	private ServeurInterface si;
	/**
	 * Create the frame.
	 */
	public tableau_partager(String username,ServeurInterface si,String classe) {
		this.classe=classe;
		this.si=si;
		color=Color.BLACK;
		this.username=username;
		setTitle(username);
		setBounds(100, 100, 863, 536);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.addMouseMotionListener(this);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("red");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = Color.RED;
			}
		});
		btnNewButton.setForeground(new Color(255, 0, 0));
		btnNewButton.setBackground(new Color(255, 0, 0));
		btnNewButton.setBounds(764, 0, 85, 21);
		contentPane.add(btnNewButton);
		
		JButton btnYellow = new JButton("yellow");
		btnYellow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = Color.YELLOW;
			}
		});
		btnYellow.setForeground(new Color(255, 255, 0));
		btnYellow.setBackground(new Color(255, 255, 0));
		btnYellow.setBounds(764, 20, 85, 21);
		contentPane.add(btnYellow);
		
		JButton btnNewButton_1_1 = new JButton("green");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = Color.GREEN;
			}
		});
		btnNewButton_1_1.setBackground(new Color(0, 255, 0));
		btnNewButton_1_1.setForeground(new Color(0, 255, 0));
		btnNewButton_1_1.setBounds(764, 41, 85, 21);
		contentPane.add(btnNewButton_1_1);
		
		JButton btnNewButton_1_2 = new JButton("blue");
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = Color.BLUE;
			}
		});
		btnNewButton_1_2.setBackground(new Color(0, 0, 255));
		btnNewButton_1_2.setForeground(new Color(0, 0, 255));
		btnNewButton_1_2.setBounds(764, 61, 85, 21);
		contentPane.add(btnNewButton_1_2);
		
		JButton btnNewButton_1_2_1 = new JButton("black");
		btnNewButton_1_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = Color.BLACK;
			}
		});
		btnNewButton_1_2_1.setForeground(new Color(0, 0, 0));
		btnNewButton_1_2_1.setBackground(new Color(0, 0, 0));
		btnNewButton_1_2_1.setBounds(764, 81, 85, 21);
		contentPane.add(btnNewButton_1_2_1);
		
		JButton btnNewButton_1 = new JButton("WHITE");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = Color.WHITE;
			}
		});
		btnNewButton_1.setBounds(764, 100, 85, 21);
		contentPane.add(btnNewButton_1);		
		setContentPane(contentPane);
	}

	
	
	public void modify(Color color,int x,int y) {
		//modifier la couleur et les coordonne
		this.color=color;
		this.x=x;
		this.y=y;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// si le prof autorise l'etudiant a ecrire (autorisation = true)
			if(autorisation) {
				//recuperer les coordonné et dessiner 
				x=e.getX(); 
				y=e.getY();
				repaint();
				try {
					//partager le dessin avec le prof et les amis de classe
					si.partagerScrennProf(x,y,classe,color,username);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
				
	}
	
	public void paint(Graphics g) {
		g.setColor(color);
		if(color==Color.WHITE)
			g.fillOval(x, y, 100, 100);
		g.fillOval(x, y, 5, 5);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void setAutorisation(boolean autorisation) {
		this.autorisation=autorisation;
	}
	
}
