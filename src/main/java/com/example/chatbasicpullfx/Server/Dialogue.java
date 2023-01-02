package com.example.chatbasicpullfx.Server;

import com.example.chatbasicpullfx.Shared.Message;
import com.example.chatbasicpullfx.Shared.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Dialogue extends Remote {
    void sendMessage(User to, String message) throws RemoteException;
    ArrayList<Message> getMessages(User from, User to) throws RemoteException;
    ArrayList<User> getClients() throws RemoteException;
}
