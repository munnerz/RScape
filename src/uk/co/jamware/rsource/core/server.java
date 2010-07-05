package uk.co.jamware.rsource.core;

import java.net.*;
import java.io.*;

import uk.co.jamware.rsource.Loader;
import uk.co.jamware.rsource.misc;

public class server implements Runnable {	
	public void run() {
		try {
			ServerSocket socks = new ServerSocket(port);
			misc.printText("Server running on port "+port);
			while(online) {
				Socket s = socks.accept();
				Loader.getLoader().connectionHandler.addConnectionReference(s);				
			}
			socks.close();
			socks = null;
		} catch(IOException e) {
			online = false;
			e.printStackTrace();
			misc.printError("Server gone down.");

		}
	}
	
	private int port = 43594;
	public boolean online = true;
}