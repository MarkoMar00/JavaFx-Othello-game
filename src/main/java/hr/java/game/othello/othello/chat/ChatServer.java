package hr.java.game.othello.othello.chat;

import hr.java.game.othello.othello.jndi.ConfigurationReader;
import hr.java.game.othello.othello.model.ConfigurationKey;
import hr.java.game.othello.othello.util.DialogUtils;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ChatServer {

    private static final int RANDOM_PORT_HINT = 0;

    public static void main(String[] args) {
        try {
            Integer rmiPort = Integer.parseInt(ConfigurationReader.getValue(ConfigurationKey.RMI_PORT));
            Registry registry = LocateRegistry.createRegistry(rmiPort);
            ChatService remoteService = new ChatServiceImpl();
            ChatService skeleton = (ChatService) UnicastRemoteObject.exportObject(remoteService, RANDOM_PORT_HINT);
            registry.rebind(ChatService.REMOTE_OBJECT_NAME, skeleton);
            System.err.println("Chat server started!");
        } catch (RemoteException e) {
            DialogUtils.showActionFailure(e.getMessage());
        }
    }
}
