package com.example.chatbasicpullfx.Server;

import com.example.chatbasicpullfx.Shared.Message;
import com.example.chatbasicpullfx.Shared.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;


public class DialogueImpl extends UnicastRemoteObject implements Dialogue{

    //Attributs
    private User user;
    private ConnectionImpl connection ;

    //HashMap pour garder en mémoire les messages entre 2 utilisateurs.
    //Une clé unique associe 2 utilisateurs.
    HashMap<String, ArrayList<Message>> bDDMessage = new HashMap<>();

    protected DialogueImpl(User user) throws RemoteException {
        super();
        this.user=user;
    }

    @Override
    public void sendMessage(User to, String message) throws RemoteException {
        String key = createBDDKey(user.getName(), to.getName());
        Message msg = new Message(user.getName(), to.getName() , message);
        if(bDDMessage.containsKey(key)){
            bDDMessage.get(key).add(msg);
        }else{
            ArrayList<Message> arrayMsg = new ArrayList<Message>();
            arrayMsg.add(msg);
            bDDMessage.put(key, arrayMsg);
        }
    }

    @Override
    public ArrayList<Message> getMessages(User from, User to) throws RemoteException {
        String key = createBDDKey(from.getName(), to.getName());
        return bDDMessage.containsKey(key) ? bDDMessage.get(key) : new ArrayList<Message>();
    }

    @Override
    public ArrayList<User> getClients() throws RemoteException {
        return connection.getUsers();
    }

    private String createBDDKey(String from, String to){
        String[] couple = {from,to};
        Arrays.sort(couple);
        String key = "";
        for (String user : couple) {
            key += user;
        }
        return key;
    }

}