package com.example.chatbasicpullfx.Server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

    public static void main(String[] args) {
        try {
            // registry creation
            LocateRegistry.createRegistry(1099);

            // component instanciation and implicit activation
            DialogueImpl DialogueComponent = new DialogueImpl();

            System.out.println(DialogueComponent.getRef().remoteToString());

            //publication of component reference in the registry
            Naming.rebind("Dialogue", DialogueComponent);

            System.out.println("Serveur actif");

        } catch (RemoteException | MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
