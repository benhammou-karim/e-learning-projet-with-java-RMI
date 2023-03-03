package RMI;

import java.awt.Color;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServeurInterface extends Remote {
	//cette fonction pour verifier le login et le password de user si il est etudiant ou prof ou admin
    public String verify(String log,String pass) throws RemoteException;
	//cette fonction pour ajouter un compte soit etudiant soit prof
    public String addAccount(String profil,String log,String pass) throws RemoteException;
	//cette fonction pour ajouter une classe
    public String addClasse(String libelle) throws RemoteException;
	//cette fonction pour recuperer les classes dans l'etudiant n'est pas inscrit
    public String findClasses(String etudiant) throws RemoteException;
	//cette fonction pour recuperer les classes qui n'ont pas de prof
    public String findClassesAffectation() throws RemoteException;
	//cette fonction pour recuperer les profs
    public String findProfs() throws RemoteException;
	//cette fonction pour affecter un prof a une classe
    public String AffecterProf(String classe,String prof) throws RemoteException;
	//cette fonction pour que l'etudiant s'inscrit dans une classe
    public String inscrire(String classe,String etudiant) throws RemoteException;
	//cette fonction pour recuperer les noms des etudiant qui on une demande en fils d'attente
    public String findEtudiants() throws RemoteException;
	//cette fonction pour recuperer les classes dans les demandes des etudiants qui en dans la fils d'attentes
    public String findClasseForEtudiant(String etudiant) throws RemoteException;
	//cette fonction pour refuser une demande d'inscription
    public String refuser(String classe,String etudiant) throws RemoteException;
	//cette fonction pour accepter une demande d'inscription
    public String accepter(String classe,String etudiant) throws RemoteException;
	//cette fonction pour recuperer les classes des etudiants dans il sont inscrits
    public String findClassesInscrit(String etudiant) throws RemoteException;
	//cette fonction pour recuperer les classes du prof dans il est affecter 
    public String findClassesProf(String prof) throws RemoteException;
	//cette fonction pour recuperer les etudiants d'une classe
    public String findEtudiantsInscrit(String classe) throws RemoteException;
	//cette fonction pour ajouter un etudiant dans la arraylist etudiants
    public void addEtudiant(ProfilInterface etudiant) throws RemoteException;
	//cette fonction pour ajouter un prof dans la arratlist profs
    public void addProf(ProfilInterface prof) throws RemoteException;
	//cette fonction pour deffuser le message a la classe
    public void broadcastMessage(String message,String classe) throws RemoteException;
	//cette fonction pour recuperer les etudiants qui sont connecter (a partir de la arraylist etudiants)
    public String EtudiantsConnecter(String classe) throws RemoteException;
	//cette fonction pour recuperer les etudiants qui sont connecter 
    public String EtudiantsConnecterForProf(String classe) throws RemoteException;
	//cette fonction pour deffuser le fichier a la classe 
    public void broadcastFile(ArrayList<Integer> pointeur,String nomfichier, String classe) throws RemoteException;
	//cette fonction pour autoriser l'etudiant a dessiner dans le tableau blanc
    public void autoriserEtudiant(boolean autorisation,String username) throws RemoteException;
	//cette fonction pour interdire l'étudiant a dessiner dans le tableau blanc
    public void refuserEtudiant(boolean autorisation,String username) throws RemoteException;    
    //cette fonction pour partager le tablanc blanc avec les etudiant
    public void partagerScrenn(int x,int y,String classe,Color color) throws RemoteException;
    //cette fonction pour partager le tablanc blanc d'etudiant avec le prof et ces amis
    public void partagerScrennProf(int x,int y,String classe,Color color,String username) throws RemoteException;
    //cette fonction pour supprimer le prof de la arraylist profs
    public void removeProf(String username) throws RemoteException;
    //cette fonction pour supprimer un étudiant de la arraylist etudiants
    public void removeEtudiant(String username) throws RemoteException;
}

