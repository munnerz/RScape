package uk.co.jamware.rsource.connections.types;

import java.net.Socket;

import uk.co.jamware.rsource.misc;
import uk.co.jamware.rsource.connections.ConnectionInterface;
import uk.co.jamware.rsource.connections.types.support.Cryption;
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
		try {
		fillInStream(1);
		int playerNameHash = inStream.readUnsignedByte();
		for(int i = 0; i < 9; i++)
			outStream.writeUnsignedByte(0);
		long serverSessionKey = ((long)(java.lang.Math.random() * 99999999D) << 32) + (long)(java.lang.Math.random() * 99999999D);
		outStream.writeLong(serverSessionKey);
		writeOut();
		fillInStream(2);
		int t = inStream.readUnsignedByte();
		boolean reconnecting = (t == 16 ? false : true);
		int packetSize = inStream.readUnsignedByte();
		int encryptedPacketSize = packetSize - (36 + 1 + 1 + 2);
		fillInStream(packetSize);
		if(inStream.readUnsignedByte() != 255) {
			misc.printError("Strange client connection, killing now.");
			disconnect();
			return;
		}
		
		int clientVersion = inStream.readUnsignedShort();
		boolean lowMemory = (inStream.readUnsignedByte() == 1 ? true : false);
		
		for(int i = 0; i < 9; i++)
			inStream.readUnsignedInt();
		
		encryptedPacketSize--;
		if(encryptedPacketSize != inStream.readUnsignedByte()) {
			misc.printError("Encryption packet size mismatch!");
		}
		
		long clientSessionKey = inStream.readUnsignedLong();
		serverSessionKey = inStream.readUnsignedLong();
		int signlinkUid = inStream.readUnsignedInt();
		inStream.readUnsignedByte();
		
		String username = inStream.readRuneString();
		String password = inStream.readRuneString();
		
		int[] isaacSeed = new int[]
		              			{ (int) (clientSessionKey >> 0x20), (int) clientSessionKey, (int) (serverSessionKey >> 0x20), (int) serverSessionKey };
		
		cryption = new Cryption(isaacSeed);
		
		outStream.writeUnsignedByte(2); //connected success
		outStream.writeUnsignedByte(2); //staff status
		outStream.writeUnsignedByte(0); //flagged
		writeOut();
		
		} catch(Exception e) {
			misc.printError("ERROR LOADING GAME");
			e.printStackTrace();
		}
	}
}
