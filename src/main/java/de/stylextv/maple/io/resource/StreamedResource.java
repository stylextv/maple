package de.stylextv.maple.io.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import de.stylextv.maple.io.serialize.Serializer;

public abstract class StreamedResource {
	
	private static final int COMPRESSION_LEVEL = Deflater.BEST_COMPRESSION;
	
	private static final int FORMAT_VERSION = 0x9BA2F52;
	
	private boolean compress;
	
	private InputStream inputStream;
	
	private OutputStream outputStream;
	
	public StreamedResource(boolean compress) {
		this.compress = compress;
	}
	
	public byte[] readAll() {
		try {
			
			return getInputStream().readAllBytes();
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public byte[] read(int length) {
		try {
			
			return getInputStream().readNBytes(length);
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public byte read() {
		try {
			
			return (byte) getInputStream().read();
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return -1;
	}
	
	public void write(byte[] data) {
		for(byte b : data) {
			
			write(b);
		}
	}
	
	public void write(byte b) {
		try {
			
			getOutputStream().write(b);
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void close() {
		try {
			
			if(inputStream != null) inputStream.close();
			if(outputStream != null) outputStream.close();
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean isEmpty() {
		return available() == 0;
	}
	
	public int available() {
		try {
			
			return getInputStream().available();
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return 0;
	}
	
	protected abstract InputStream inputStream();
	protected abstract OutputStream outputStream();
	
	public InputStream getInputStream() {
		if(inputStream == null) {
			
			inputStream = inputStream();
			
			if(compress) inputStream = new InflaterInputStream(inputStream);
		}
		
		return inputStream;
	}
	
	public OutputStream getOutputStream() {
		if(outputStream == null) {
			
			outputStream = outputStream();
			
			if(compress) {
				
				Deflater deflater = new Deflater(COMPRESSION_LEVEL);
				
				outputStream = new DeflaterOutputStream(outputStream, deflater);
				
				Serializer.INTEGER.writeTo(this, FORMAT_VERSION);
			}
		}
		
		return outputStream;
	}
	
	public boolean exists() {
		if(!compress) return true;
		
		int i = Serializer.INTEGER.readFrom(this);
		
		return i == FORMAT_VERSION;
	}
	
	public boolean isCompressed() {
		return compress;
	}
	
}