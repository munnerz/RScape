package uk.co.jamware.rsource.connections.types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import uk.co.jamware.rsource.misc;
import uk.co.jamware.rsource.connections.io.Stream;
import uk.co.jamware.rsource.connections.types.support.Cryption;

public class Connection {	
	public Connection(Socket s) {
		connected = true;
		outStream = new Stream();
		inStream = new Stream();
		try {
			output = new DataOutputStream(s.getOutputStream());
			input = new DataInputStream(s.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			disconnect();
			e.printStackTrace();
		}
	}

	public boolean fillInStream(int i) {
		try {
			byte[] b = new byte[i];
			int a = input.read(b,0,i);
			if(a == -1) {
				disconnect();
				return false;
			}
			inStream.setBuffer(b);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
			return false;
		}
	}
	
	public int availableIn() throws IOException {
		return input.available();
	}
	
	public void writeOut() {
		if(!connected)
			return;
		byte[] toWrite = outStream.getBufferForSending();
		try {
			output.write(toWrite);
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			disconnect();
			e.printStackTrace();
		}
	}
	
	public boolean isDisconnected() {
		return !connected;
	}
	
	public void disconnect() {
		connected = false;
		try {
			output.close();
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		output = null;
		input = null;
		outStream = null;
		inStream = null;
	}
	
	public Stream inStream, outStream;
	private DataOutputStream output;
	private boolean connected = false;
	private DataInputStream input;
	public Cryption cryption;
}
