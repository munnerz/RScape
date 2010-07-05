package uk.co.jamware.rsource.connections.types;

import java.net.Socket;

import uk.co.jamware.rsource.misc;
import uk.co.jamware.rsource.connections.ConnectionInterface;

public class ClientUpdate extends Connection implements ConnectionInterface {
	
	public ClientUpdate(Socket s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleConnection() {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		for(int i = 0; i < 8; i++)
			outStream.writeUnsignedByte((byte) 0);
		writeOut();
		while(fillInStream(4) && !isDisconnected()) {
			int cacheId = inStream.readUnsignedByte();
			int partId = inStream.readUnsignedShort();
			int priority = inStream.readUnsignedByte(); /*	immediate = 2
														 *	not logged in = 1
														 *	normal = 0
														 */
			misc.printText("Got request for file "+partId+" in cache "+cacheId+" priority: "+priority);
		}
		// TODO Auto-generated method stub
		
	}

}
