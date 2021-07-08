package de.stylextv.lynx.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public class FileAccess {
	
	private static final int COMPRESSION_LEVEL = Deflater.BEST_COMPRESSION;
	
	private File file;
	
	private boolean compress;
	
	private InputStream inputStream;
	
	private OutputStream outputStream;
	
	public FileAccess(File f) {
		this(f, true);
	}
	
	public FileAccess(File f, boolean compress) {
		this.file = f;
		this.compress = compress;
		
		f.getParentFile().mkdirs();
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
	
	public void write(byte[] data) {
		try {
			
			getOutputStream().write(data);
			
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
	
	public boolean exists() {
		return file.exists();
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
	
	public InputStream getInputStream() {
		if(inputStream == null) {
			try {
				
				inputStream = new FileInputStream(file);
				
				if(compress) {
					
					inputStream = new InflaterInputStream(inputStream);
				}
				
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			}
		}
		
		return inputStream;
	}
	
	public OutputStream getOutputStream() {
		if(outputStream == null) {
			try {
				
				outputStream = new FileOutputStream(file);
				
				if(compress) {
					
					Deflater deflater = new Deflater(COMPRESSION_LEVEL);
					
					outputStream = new DeflaterOutputStream(outputStream, deflater);
				}
				
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			}
		}
		
		return outputStream;
	}
	
	public File getFile() {
		return file;
	}
	
}
