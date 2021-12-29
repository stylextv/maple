package de.stylextv.maple.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.stylextv.maple.io.resource.types.StreamedFile;
import de.stylextv.maple.io.serialize.Serializer;

public class ConfigHelper {
	
	private static final String FOLDER = "config/";
	
	private static final String FILE_EXTENSION = ".json";
	
	private static Gson gson;
	
	static {
		GsonBuilder builder = new GsonBuilder();
		
		builder.setPrettyPrinting();
		
		gson = builder.create();
	}
	
	public static <T> T loadFile(String path, Class<T> clazz) {
		StreamedFile f = openFile(path);
		
		T t = null;
		
		if(f.exists()) {
			
			String json = Serializer.STRING.readFrom(f);
			
			t = gson.fromJson(json, clazz);
		}
		
		f.close();
		
		return t;
	}
	
	public static <T> void saveFile(String path, Object o) {
		StreamedFile f = openFile(path);
		
		String json = gson.toJson(o);
		
		Serializer.STRING.writeTo(f, json);
		
		f.close();
	}
	
	private static StreamedFile openFile(String path) {
		String s = FOLDER + path + FILE_EXTENSION;
		
		return new StreamedFile(s);
	}
	
}
