package uk.co.jamware.rsource.connections.types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import uk.co.jamware.rsource.connections.io.Stream;

public class Connection {
	public Stream inStream, outStream;
	private DataOutputStream output;
	private DataInputStream input;
	
	public Connection(Socket s) {
		outStream = new Stream();
		inStream = new Stream();
		try {
			output = new DataOutputStream(s.getOutputStream());
			input = new DataInputStream(s.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean fillInStream(int i) {
		try {
			byte[] b = new byte[i];
			input.read(b, 0, i);
			inStream.setBuffer(b);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public void writeOut() {
		byte[] toWrite = outStream.getBufferForSending();
		try {
			output.write(toWrite);
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
