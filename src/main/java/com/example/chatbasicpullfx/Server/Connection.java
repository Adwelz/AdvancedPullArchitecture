package com.example.chatbasicpullfx.Server;

import com.example.chatbasicpullfx.Shared.User;

import java.rmi.RemoteException;

public interface Connection {
    boolean connect(User user) throws RemoteException;
    void disconnect(User user) throws RemoteException;
}
