package uk.co.jamware.rsource.connections.io;

public class Stream {
	public byte[] buffer;
	public int currentOffset = 0;
	public int maxSize = 1000;
	
	public Stream(byte[] buffer) {
		this.buffer = buffer;
	}
	
	public Stream() {
		buffer = new byte[maxSize];
	}
	
	public void setBuffer(byte[] b) {
		currentOffset = 0;
		buffer = b;
	}
	
	public int readUnsignedByte() {
		return (byte) (buffer[currentOffset++] & 0xff);
	}
	public int readUnsignedShort() {
		currentOffset += 2;
	    return ((buffer[currentOffset - 2] & 0xff) << 8) + (buffer[currentOffset - 1] & 0xff);
	}
	
	public void writeUnsignedByte(int i) {
		buffer[currentOffset++] = (byte) i;
	}
	
	public byte[] getBufferForSending() {
		byte[] toSend = buffer;
		buffer = new byte[maxSize];
		currentOffset = 0;
		return toSend;
	}
}
