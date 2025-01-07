package hr.java.game.othello.othello.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatService extends Remote {
    String REMOTE_OBJECT_NAME = "hr.tvz.rmi.service";
    void sendMessage(String message) throws RemoteException;
    List<String> returnChatHistory() throws RemoteException;

}
