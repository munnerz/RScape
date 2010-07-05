package uk.co.jamware.rsource.connections.types;

import java.net.Socket;

import uk.co.jamware.rsource.misc;
import uk.co.jamware.rsource.connections.ConnectionInterface;
import uk.co.jamware.rsource.connections.types.support.Text;

public class PlayerConnection extends Connection implements ConnectionInterface {

	public PlayerConnection(Socket s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleConnection() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		fillInStream(1);
		int playerNameHash = inStream.readUnsignedByte();
		for(int i = 0; i < 9; i++)
			outStream.writeUnsignedByte(0);
		// TODO Auto-generated method stub
		
	}

}
