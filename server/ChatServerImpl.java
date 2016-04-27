package server;
 
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import RMITest.OwnStackImplementation;
import client.IChatClientCallback;
 
public class ChatServerImpl extends UnicastRemoteObject implements IChatServer {
 
  private static final long serialVersionUID = 1L;
  private static final int registryPort = 1099;
 
  // Hier speichern wir die Callbacks!
  private Map<String, IChatClientCallback> users;
 
  private ChatServerImpl() throws RemoteException {
    super();
    HashMap<String, IChatClientCallback> callbackHashMap = new HashMap<>();
    users = Collections.synchronizedMap(callbackHashMap);
  }
 
  public boolean login(String userID, IChatClientCallback receiver)
  throws RemoteException {
	  if (users.containsKey(userID)){
		  return false;
	  }
      users.put(userID, receiver);
      for(IChatClientCallback user : users.values()){
    	  user.receiveUserLogin(userID, users.keySet().toArray());
      }
	  return true;
  }
 
  public void logout(String userID) throws RemoteException {
    users.remove(userID);
    for(IChatClientCallback user : users.values()){
  	  user.receiveUserLogout(userID, users.keySet().toArray());
    }
  }
 
  public void chat(String userID, String message) throws RemoteException {
	  if (!users.containsKey(userID)){
		  return;
	  }
	  for(IChatClientCallback user : users.values()){
    	  user.receiveChat(userID, message);
      }
  }
 
  public static void main(String[] args) {
    try {
      LocateRegistry.createRegistry(1099);
      Naming.bind("rmi://localhost/queue", new ChatServerImpl());
      System.out.println("ChatServer ready");
    } catch (Exception ex) {
      ex.printStackTrace();
      System.exit(0);
    }
  }
}
