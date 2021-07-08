package de.stylextv.lynx.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileAccess {
	
	private File file;
	
	private FileInputStream inputStream;
	
	private FileOutputStream outputStream;
	
	public FileAccess(File f) {
		this.file = f;
		
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
	
	public void close() {
		try {
			
			if(inputStream != null) inputStream.close();
			if(outputStream != null) outputStream.close();
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public FileInputStream getInputStream() {
		if(inputStream == null) {
			try {
				
				inputStream = new FileInputStream(file);
				
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			}
		}
		
		return inputStream;
	}
	
	public FileOutputStream getOutputStream() {
		if(outputStream == null) {
			try {
				
				outputStream = new FileOutputStream(file);
				
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
