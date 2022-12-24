package com.example.chatbasicpullfx.Server;


import com.example.chatbasicpullfx.Shared.Message;
import com.example.chatbasicpullfx.Server.CustomExceptions.ConnectionException;
import com.example.chatbasicpullfx.Shared.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


public class DialogueImpl extends UnicastRemoteObject implements Dialogue{

    //Attributs
    ArrayList<User> users = new ArrayList<>();

    //HashMap pour garder en mémoire les messages entre 2 utilisateurs.
    //Une clé unique associe 2 utilisateurs.
    HashMap<String, ArrayList<Message>> bDDMessage = new HashMap<>();

    protected DialogueImpl() throws RemoteException {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean connect(User user) throws RemoteException {
        AtomicBoolean state = new AtomicBoolean(false);
        try{
            if(users.stream().allMatch(u -> u.getName() != user.getName())){
                users.add(user);
                state.set(true);
                System.out.println("User ("+ user.getName() +") connection successful");
            }else{
                throw new ConnectionException("Erreur de connexion");
            }
        }catch(ConnectionException e){
            System.out.println(e.getExceptionMsg());
            e.printStackTrace();
        }
        return state.get();
    }

    @Override
    public void disconnect(User user) throws RemoteException {
        ((ArrayList<User>)users.clone()).forEach(u -> {
            if(Objects.equals(u.getName(), user.getName())){
                System.out.println("User ("+ user.getName() +") disconnection successful");
                users.remove(u);
            }
        });
    }
    @Override
    public void sendMessage(User from, User to, String message) throws RemoteException {
        String key = createBDDKey(from.getName(), to.getName());
        Message msg = new Message(from.getName(), to.getName() , message);
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
        return users;
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