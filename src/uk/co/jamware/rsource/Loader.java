package uk.co.jamware.rsource;

import uk.co.jamware.rsource.connections.ConnectionHandler;
import uk.co.jamware.rsource.core.*;

public class Loader {
	
	public static void main(String args[]) {
		Loader.getLoader().launch();
	}
	
	public void launch() {
		connectionHandler = new ConnectionHandler();
		new Thread(connectionHandler).start();
		
		server = new server();
		new Thread(server).start();
	}
	
	public static Loader getLoader(){
		if(loader == null)
			loader = new Loader();
		return loader;
	}
	protected Loader() {}
	private static Loader loader = null;//volatile is needed so that multiple thread can reconcile the instance
	public ConnectionHandler connectionHandler;
	public server server;
}
