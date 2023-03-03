package e_learning;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import RMI.Serveur;

public class MainServeur {

	public static void main(String[] args) throws RemoteException, MalformedURLException {
		Serveur s = new Serveur();
        LocateRegistry.createRegistry(1099);
        Naming.rebind("irisi",s);
        System.out.println("Serveur Pret");
	}

}
