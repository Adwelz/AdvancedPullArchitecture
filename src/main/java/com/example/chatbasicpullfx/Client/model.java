package com.example.chatbasicpullfx.Client;

import com.example.chatbasicpullfx.Server.Connection;
import com.example.chatbasicpullfx.Server.ConnectionImpl;
import com.example.chatbasicpullfx.Server.Dialogue;
import com.example.chatbasicpullfx.Shared.Message;
import com.example.chatbasicpullfx.Shared.User;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

public class model {

    private static model instance;
    Dialogue dialogue;
    Connection connection;
    private User currentUser;
    private User adressee;

    private model(){
        try {
            connection = (Connection) Naming.lookup("rmi://localhost:1099/Connection");
            dialogue = (Dialogue) Naming.lookup("rmi://localhost:1099/Dialogue"+currentUser.getName());
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    void sendMessage(String msg) throws RemoteException {
        dialogue.sendMessage(adressee,msg);
    }

    void disconnectUser() throws RemoteException {
        connection.disconnect(currentUser);
    }

    public boolean connectUser(String userName) {
        try{
            currentUser = new User(userName);
            if(connection.connect(currentUser)){
                return true;
            }

        }catch (RemoteException e){
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Message> getMessages() throws RemoteException {

        return adressee != null? dialogue.getMessages(currentUser, adressee): new ArrayList<Message>() ;
    }

    public ArrayList<User> getUserList(){
        ArrayList<User> users = new ArrayList<>();
        try{
            users = dialogue.getClients();
        }catch (RemoteException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public void setAdressee(User adressee){
        this.adressee = adressee;
    }
    public User getAdressee(){
        return adressee;
    }

    public static model getInstance(){
        if(instance == null){
            instance = new model();
        }
        return instance;
    }
}
