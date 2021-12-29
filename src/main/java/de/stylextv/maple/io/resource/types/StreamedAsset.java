package de.stylextv.maple.io.resource.types;

import java.io.InputStream;
import java.io.OutputStream;

import de.stylextv.maple.io.resource.StreamedResource;

public class StreamedAsset extends StreamedResource {
	
	private static final String ASSETS_FOLDER = "assets/";
	
	private String path;
	
	public StreamedAsset(String path) {
		this(path, true);
	}
	
	public StreamedAsset(String path, boolean compress) {
		super(compress);
		
		this.path = path;
	}
	
	@Override
	protected InputStream inputStream() {
		Thread thread = Thread.currentThread();
		
		ClassLoader loader = thread.getContextClassLoader();
		
		return loader.getResourceAsStream(ASSETS_FOLDER + path);
	}
	
	@Override
	protected OutputStream outputStream() {
		return null;
	}
	
	public String getPath() {
		return path;
	}
	
}
