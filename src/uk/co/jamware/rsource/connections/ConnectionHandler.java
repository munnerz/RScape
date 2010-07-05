package uk.co.jamware.rsource.connections;

import uk.co.jamware.rsource.*;
import uk.co.jamware.rsource.connections.types.ClientUpdate;
import uk.co.jamware.rsource.connections.types.PlayerConnection;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class ConnectionHandler implements Runnable {
	private ArrayList<ConnectionReference> connections = new ArrayList<ConnectionReference>();
	private ArrayList<ConnectionReference> toProcess = new ArrayList<ConnectionReference>();
	public boolean conHandlerRunning = true;
	
	public ConnectionHandler() {
		misc.printText("Connection Handler running.");
	}
	
	public void run() {
		while(conHandlerRunning) {
			try {
				processConnections();
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
				misc.printError("Error in processing connections");
			}
		}
	}
	
	public void processConnections() {
		if(toProcess.isEmpty())
			return;
		misc.printText("size: "+toProcess.size());
		ArrayList<ConnectionReference> a = new ArrayList<ConnectionReference>(toProcess);
		ListIterator<ConnectionReference> it;
		synchronized(toProcess) {
			it = a.listIterator();
			toProcess.clear();
		}
		while(it.hasNext()) {
			misc.printText("working");
			ConnectionReference c = (ConnectionReference) it.next();
			if(c.connection() != null) {
				misc.printError("Strange logic error in connection reference! Skipping!");
				toProcess.remove(c);
				continue;
			}
			misc.printText("Starting connection");
			ConnectionInterface con = (c.isUpdate == true ? new ClientUpdate(c.socket) : new PlayerConnection(c.socket));
			new Thread(con).start();
			c.setConnection(con);
			connections.add(c);
		}
	}
	
	public void addConnectionReference(Socket s) throws IOException {
		misc.printText("Adding connection");
		DataInputStream d = new DataInputStream(s.getInputStream());
		boolean type = (d.readByte() == 15 ? true : false);
		toProcess.add(new ConnectionReference(type, s));
		misc.printText("New connection accepted of type "+(type == true ? "update connection" : "player connection"));
	}
	
	class ConnectionReference {
		ConnectionInterface c;
		boolean isUpdate;
		Socket socket;
		
		public void setConnection(ConnectionInterface c) {
			this.c = c;
		}
		
		public ConnectionInterface connection() {
			return c;
		}
		
		public ConnectionReference(boolean _isUpdate, Socket _socket) {
			isUpdate = _isUpdate;
			socket = _socket;
		}
	}
}
