package hr.java.game.othello.othello.chat;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ChatServiceImpl implements ChatService{

    private List<String> chatHistory = new ArrayList<>();

    @Override
    public void sendMessage(String message) throws RemoteException {
        chatHistory.add(message);
    }

    @Override
    public List<String> returnChatHistory() throws RemoteException {
        return chatHistory;
    }
}
