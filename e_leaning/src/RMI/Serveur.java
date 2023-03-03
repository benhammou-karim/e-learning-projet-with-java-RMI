package RMI;

import java.awt.Color;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Serveur extends UnicastRemoteObject implements ServeurInterface{
	
	 private static String BDD = "elearning";
		private static String url = "jdbc:mysql://localhost:3306/" + BDD;
		private static String user = "root";
		private static String passwd = "";
		private ArrayList<ProfilInterface> etudiants=new ArrayList<ProfilInterface>();
		private ArrayList<ProfilInterface> profs=new ArrayList<ProfilInterface>();
		    
	public Serveur() throws RemoteException {
        super();
    }
	
	//cette fonction pour verifier le login et le password de user si il est etudiant ou prof ou admin
	public String verify(String log,String pass) throws RemoteException {
		try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, passwd);
            Statement stmt = conn.createStatement();
            ResultSet res =stmt.executeQuery("select profil from personne where username='"+log+"' and password='"+pass+"'");
            if(res.next()) {
	            return res.getString(1);
            }
            conn.close();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Erreur");
        }
        return "false";
	}

	//cette fonction pour ajouter un compte soit etudiant soit prof
	public String addAccount(String profil, String log, String pass) throws RemoteException {
		 try {
	            Class.forName("com.mysql.jdbc.Driver");
	            Connection conn = DriverManager.getConnection(url, user, passwd);
	            Statement stmt = conn.createStatement();
	            ResultSet res =stmt.executeQuery("select id_personne from personne where profil='"+profil+"' and username='"+log+"' and password='"+pass+"'");
	            if(res.next()) {
		             return "compte existe déja";
	            }
	            stmt.execute("insert into personne(username,password,profil) value('"+log+"','"+pass+"','"+profil+"')");
	            conn.close();
	        } catch (Exception e){
	            e.printStackTrace();
	            System.out.println("Erreur");
	        }
		return "compte creer avec success";
	}
	
	//cette fonction pour ajouter une classe
	public String addClasse(String libelle) throws RemoteException {
		 try {
	            Class.forName("com.mysql.jdbc.Driver");
	            Connection conn = DriverManager.getConnection(url, user, passwd);
	            Statement stmt = conn.createStatement();
	            ResultSet res =stmt.executeQuery("select id_classe from classe where libelle='"+libelle+"'");
	            if(res.next()) {
		             return "classe existe déja";
	            }
	            stmt.execute("insert into classe(libelle) value('"+libelle+"')");
	            conn.close();
	        } catch (Exception e){
	            e.printStackTrace();
	            System.out.println("Erreur");
	        }
		 return "classe creer avec success";
	}
	
	//cette fonction pour recuperer les classes qui n'ont pas de prof
	@Override
	public String findClassesAffectation() throws RemoteException {
		try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, passwd);
            Statement stmt = conn.createStatement();
            ResultSet res =stmt.executeQuery("select libelle from classe where id_professeur is null");
            String message="";
            while(res.next()) {
	             message+=res.getString(1)+",";
            }
            conn.close();
            return message;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Erreur");
        }
        return "";
	}
	
	//cette fonction pour recuperer les classes dans l'etudiant n'est pas inscrit
	@Override
	public String findClasses(String etudiant) throws RemoteException {
		try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, passwd);
            Statement stmt = conn.createStatement();
            ResultSet res =stmt.executeQuery("select id_personne from personne where username='"+etudiant+"'");
            int id = 0;
            if(res.next()) {
	            id=res.getInt(1);
            }
            String message="";
            ArrayList<Integer> id_classes = new ArrayList<Integer>();
            ArrayList<String> classes = new ArrayList<String>();
        	res =stmt.executeQuery("select id_classe from inscription where id_etudiant="+id+" and (statut='accepter' OR statut='attente')");
            while(res.next()) {
            	id_classes.add(res.getInt(1));
            }
            res =stmt.executeQuery("select libelle from classe");
            while(res.next()) {
            	classes.add(res.getString(1));
            }
            if(id_classes.isEmpty()) {
            	for(int i=0;i<classes.size();i++) {
                    	message+=classes.get(i)+",";
                    }
            	return message;
            }else {
            	for(int i=0;i<id_classes.size();i++) {
            		res =stmt.executeQuery("select libelle from classe where id_classe="+id_classes.get(i)+"");
                    if(res.next()) {
                    	for(int j=0;j<classes.size();j++) {
                        	if(classes.get(j).equals(res.getString(1))) {
                        		classes.remove(res.getString(1));
                        	}
                        }
                    }
                }
            	for(int i=0;i<classes.size();i++) {
                	message+=classes.get(i)+",";
                }
            }
            conn.close();
        	return message;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Erreur");
        }
        return "";
	}
	
	//cette fonction pour recuperer les profs
	public String findProfs() throws RemoteException {
		try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, passwd);
            Statement stmt = conn.createStatement();
        	ResultSet res =stmt.executeQuery("select username from personne where profil='professeur'");
        	String message="";
            while(res.next()) {
                	message+=res.getString(1)+",";
            }
            conn.close();
            return message;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Erreur");
        }
        return "";
	}

	//cette fonction pour affecter un prof a une classe
	@Override
	public String AffecterProf(String classe, String prof) throws RemoteException {
		try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, passwd);
            Statement stmt = conn.createStatement();
            ResultSet res =stmt.executeQuery("select id_personne from personne where username='"+prof+"'");
            int id = 0;
            if(res.next()) {
	            id=res.getInt(1);
            }
            stmt.execute("update classe set id_professeur="+id+" where libelle='"+classe+"'");
            conn.close();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Erreur");
        }		
		return "le professeur "+prof+" a ete affecter a la classe "+classe+" avec success";
	}

	//cette fonction pour que l'etudiant s'inscrit dans une classe
	@Override
	public String inscrire(String classe, String etudiant) throws RemoteException {
		try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, passwd);
            Statement stmt = conn.createStatement();
            ResultSet res =stmt.executeQuery("select id_personne from personne where username='"+etudiant+"'");
            int id_etud = 0;
            if(res.next()) {
            	id_etud=res.getInt(1);
            }
            res =stmt.executeQuery("select id_classe from classe where libelle='"+classe+"'");
            int id_classe = 0;
            if(res.next()) {
            	id_classe=res.getInt(1);
            }
            stmt.execute("insert into inscription(id_etudiant,id_classe,statut) value("+id_etud+","+id_classe+",'attente')");
            conn.close();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Erreur");
        }			
		return "inscrit avec success";
	}

	//cette fonction pour recuperer les noms des etudiant qui on une demande en fils d'attente
	@Override
	public String findEtudiants() throws RemoteException {
		try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, passwd);
            Statement stmt = conn.createStatement();
            String message="";
            ArrayList<Integer> id_etudiants = new ArrayList<Integer>();
            ResultSet res =stmt.executeQuery("select id_etudiant from inscription where statut='attente'");
            while(res.next()) {
            	id_etudiants.add(res.getInt(1));
            }
            for(int i=0;i<id_etudiants.size();i++) {
            	res =stmt.executeQuery("select username from personne where id_personne="+id_etudiants.get(i)+"");
                if(res.next()) {
                	message+=res.getString(1)+",";
                }
            }
            conn.close();
            return message;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Erreur");
        }
        return "";
	}

	//cette fonction pour recuperer les classes dans les demandes des etudiants qui en dans la fils d'attentes
	@Override
	public String findClasseForEtudiant(String etudiant) throws RemoteException {
		try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, passwd);
            Statement stmt = conn.createStatement();
            ResultSet res =stmt.executeQuery("select id_personne from personne where username='"+etudiant+"'");
            int id_etud = 0;
            if(res.next()) {
            	id_etud=res.getInt(1);
            }
            String message="";
            ArrayList<Integer> id_classes = new ArrayList<Integer>();
        	res =stmt.executeQuery("select id_classe from inscription where id_etudiant="+id_etud+" and statut='attente'");
            while(res.next()) {
            	id_classes.add(res.getInt(1));
            }
            for(int i=0;i<id_classes.size();i++) {
            	res =stmt.executeQuery("select libelle from classe where id_classe="+id_classes.get(i)+"");
                if(res.next()) {
                	message+=res.getString(1)+",";
                }
            }
            conn.close();
            return message;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Erreur");
        }
        return "";
		
	}

	//cette fonction pour refuser une demande d'inscription
	@Override
	public String refuser(String classe, String etudiant) throws RemoteException {
		try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, passwd);
            Statement stmt = conn.createStatement();
            ResultSet res =stmt.executeQuery("select id_personne from personne where username='"+etudiant+"'");
            int id_etud = 0;
            if(res.next()) {
            	id_etud=res.getInt(1);
            }
            res =stmt.executeQuery("select id_classe from classe where libelle='"+classe+"'");
            int id_classe = 0;
            if(res.next()) {
            	id_classe=res.getInt(1);
            }
            stmt.execute("update inscription set statut='refuser' where id_classe="+id_classe+" and id_etudiant="+id_etud+" and statut='attente'");
            conn.close();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Erreur");
        }		
		return "la demande a ete refusé";
	}
	
	//cette fonction pour accepter une demande d'inscription
	@Override
	public String accepter(String classe, String etudiant) throws RemoteException {
		try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, passwd);
            Statement stmt = conn.createStatement();
            ResultSet res =stmt.executeQuery("select id_personne from personne where username='"+etudiant+"'");
            int id_etud = 0;
            if(res.next()) {
            	id_etud=res.getInt(1);
            }
            res =stmt.executeQuery("select id_classe from classe where libelle='"+classe+"'");
            int id_classe = 0;
            if(res.next()) {
            	id_classe=res.getInt(1);
            }
            stmt.execute("update inscription set statut='accepter' where id_classe="+id_classe+" and id_etudiant="+id_etud+" and statut='attente'");
            conn.close();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Erreur");
        }		
		return "la demande a ete accepter";
	}

	//cette fonction pour recuperer les classes des etudiants dans il sont inscrits
	@Override
	public String findClassesInscrit(String etudiant) throws RemoteException {
		try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, passwd);
            Statement stmt = conn.createStatement();
            ResultSet res =stmt.executeQuery("select id_personne from personne where username='"+etudiant+"'");
            int id_etud = 0;
            if(res.next()) {
            	id_etud=res.getInt(1);
            }
            String message="";
            ArrayList<Integer> id_classes = new ArrayList<Integer>();
        	res =stmt.executeQuery("select id_classe from inscription where id_etudiant="+id_etud+" and statut='accepter'");
            while(res.next()) {
            	id_classes.add(res.getInt(1));
            }
            for(int i=0;i<id_classes.size();i++) {
            	res =stmt.executeQuery("select libelle from classe where id_classe="+id_classes.get(i)+"");
                if(res.next()) {
                	message+=res.getString(1)+",";
                }
            }
            conn.close();
            return message;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Erreur");
        }
        return "";
	}
	
	//cette fonction pour recuperer les classes du prof dans il est affecter 
	@Override
	public String findClassesProf(String prof) throws RemoteException {
		try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, passwd);
            Statement stmt = conn.createStatement();
            ResultSet res =stmt.executeQuery("select id_personne from personne where username='"+prof+"'");
            int id_prof = 0;
            if(res.next()) {
            	id_prof=res.getInt(1);
            }
            String message="";
        	res =stmt.executeQuery("select libelle from classe where id_professeur="+id_prof+"");
            	while(res.next()) {
                	message+=res.getString(1)+",";
                }
            conn.close();
            return message;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Erreur");
        }
        return "";
	}
	
	//cette fonction pour recuperer les etudiants d'une classe
	@Override
	public String findEtudiantsInscrit(String classe) throws RemoteException {
		try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, passwd);
            Statement stmt = conn.createStatement();
            ResultSet res =stmt.executeQuery("select id_classe from classe where libelle='"+classe+"'");
            int id_classe = 0;
            if(res.next()) {
            	id_classe=res.getInt(1);
            }
            String message="";
            ArrayList<Integer> id_etudiants = new ArrayList<Integer>();
        	res =stmt.executeQuery("select id_etudiant from inscription where id_classe="+id_classe+" and statut='accepter'");
            while(res.next()) {
            	id_etudiants.add(res.getInt(1));
            }
            for(int i=0;i<id_etudiants.size();i++) {
            	res =stmt.executeQuery("select username from personne where id_personne="+id_etudiants.get(i)+"");
                if(res.next()) {
                	message+=res.getString(1)+",";
                }
            }
            conn.close();
            return message;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Erreur");
        }
        return "";
	}
	
	//cette fonction pour ajouter un etudiant dans la arraylist profs
	@Override
	public void addEtudiant(ProfilInterface etudiant) throws RemoteException {
		etudiants.add(etudiant);
		
	}
	
	//cette fonction pour ajouter un prof dans la arratlist etudiants
	@Override
	public void addProf(ProfilInterface prof) throws RemoteException {
		profs.add(prof);
		
	}
	
	//cette fonction pour deffuser le message a la classe
	@Override
	public void broadcastMessage(String message,String classe) throws RemoteException {
		for(int i=0;i<profs.size();i++){
			if(profs.get(i).getClasse().equals(classe))
				profs.get(i).afficherMessage(message);
        }
		for(int i=0;i<etudiants.size();i++){
			if(etudiants.get(i).getClasse().equals(classe))
				etudiants.get(i).afficherMessage(message);
        }
	}
	
	//cette fonction pour deffuser le fichier a la classe 
	@Override
	public void broadcastFile(ArrayList<Integer> pointeur, String nomfichier,String classe) throws RemoteException {
		for(int i=0;i<profs.size();i++){
			if(profs.get(i).getClasse().equals(classe))
				profs.get(i).afficherFile(pointeur,nomfichier);
        }
		for(int i=0;i<etudiants.size();i++){
			if(etudiants.get(i).getClasse().equals(classe))
				etudiants.get(i).afficherFile(pointeur,nomfichier);
        }
		
	}
	
	//cette fonction pour recuperer les etudiants qui sont connecter (a partir de la arraylist etudiants)
	@Override
	public String EtudiantsConnecter(String classe) throws RemoteException {
		String message="";
		for(int i=0;i<etudiants.size();i++){
			if(etudiants.get(i).getClasse().equals(classe))
				message+=etudiants.get(i).getUsername()+",";
        }
		return message;
				
	}
	
	//cette fonction pour recuperer les etudiants qui sont connecter 
	@Override
	public String EtudiantsConnecterForProf(String classe) throws RemoteException {
		String message="";
		for(int i=0;i<etudiants.size();i++){
			if(etudiants.get(i).getClasse().equals(classe))
				message+=etudiants.get(i).getUsername()+",";
        }
		for(int i=0;i<profs.size();i++){
			if(profs.get(i).getClasse().equals(classe))
				return message;
        }
		return "";
				
	}
	
	//cette fonction pour autoriser l'etudiant a dessiner dans le tableau blanc
	public void autoriserEtudiant(boolean autorisation,String username) throws RemoteException {
		for(int i=0;i<etudiants.size();i++){
			if(etudiants.get(i).getUsername().equals(username))
				etudiants.get(i).setAutorisation(autorisation);
        }
	}
	
	//cette fonction pour interdire l'étudiant a dessiner dans le tableau blanc
	public void refuserEtudiant(boolean autorisation, String username) throws RemoteException {
		for(int i=0;i<etudiants.size();i++){
			if(etudiants.get(i).getUsername().equals(username))
				etudiants.get(i).setAutorisation(autorisation);
        }		
	}

	
    //cette fonction pour partager le tablanc blanc avec les etudiant
	@Override
	public void partagerScrenn(int x, int y,String classe,Color color) throws RemoteException {
		// TODO Auto-generated method stub
		for(int i=0;i<etudiants.size();i++){
			if(etudiants.get(i).getClasse().equals(classe)) {
				etudiants.get(i).partage(x,y,color);
			}
        }
	}

    //cette fonction pour partager le tablanc blanc d'etudiant avec le prof et ces amis
	@Override
	public void partagerScrennProf(int x, int y, String classe, Color color, String username) throws RemoteException {
		// TODO Auto-generated method stub
		for(int i=0;i<etudiants.size();i++){
			if(etudiants.get(i).getClasse().equals(classe)) {
				if(!etudiants.get(i).getUsername().equals(username))
					etudiants.get(i).partage(x,y,color);
			}
        }
		for(int i=0;i<profs.size();i++){
			if(profs.get(i).getClasse().equals(classe))
				profs.get(i).partage(x,y,color);
        }
	}

    //cette fonction pour supprimer un étudiant de la arraylist etudiants
	@Override
	public void removeEtudiant(String username) throws RemoteException {
		for(int i=0;i<etudiants.size();i++){
			if(etudiants.get(i).getUsername().equals(username)) {
					etudiants.remove(etudiants.get(i));
			}
        }
		
	}

    //cette fonction pour supprimer le prof de la arraylist profs
	@Override
	public void removeProf(String username) throws RemoteException {
		for(int i=0;i<profs.size();i++){
			if(profs.get(i).getUsername().equals(username))
				profs.remove(profs.get(i));
        }		
	}

	
}
