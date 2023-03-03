package RMI;

import java.awt.Color;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import Etudiant.tableau_partager;
import Prof.Tableau_blanc;

public interface ProfilInterface extends Remote{
	//cette fonction pour afficher un message defuser au prof et a tous les etudiants de la classe
    public void afficherMessage(String message) throws RemoteException; 
	//cette fonction pour envoyer un message au prof et a tous les etudiants de la classe
    public void sendMessage() throws RemoteException;
	//cette fonction pour afficher un fichier defuser a tous les etudiants de la classe d'apres le prof
    public void afficherFile(ArrayList<Integer> pointeur,String nomfichier) throws RemoteException;
	//cette fonction pour recuperer le nom du l'etudiant
    public String getUsername()throws RemoteException;
	//cette fonction pour recuperer la classe de l'etudiant
    public String getClasse()throws RemoteException;
	//cette fonction pour autoriser ou interdir l'etudiant a ecrire dans le tableau blanc
    public void setAutorisation(boolean autorisation)throws RemoteException;
	//cette fonction pour recuperer le tableau blanc de l'etudiant
    public tableau_partager getTableau() throws RemoteException;
	//cette fonction pour recuperer le tableau blanc de prof
    public Tableau_blanc getTableauB() throws RemoteException;
	//cette fonction pour partager le dessin du prof dans le tableau blanc de l'etudiant ou l'inverse
    public void partage(int x,int y,Color color) throws RemoteException;
}
