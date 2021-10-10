package de.stylextv.maple.config;

import java.io.File;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.stylextv.maple.io.FileAccess;
import de.stylextv.maple.io.FileSystem;

public class ConfigHelper {
	
	private static final File SAVE_FOLDER = new File(FileSystem.ROOT_FOLDER, "config");
	
	private static final String FILE_EXTENSION = ".json";
	
	public static <T> T loadFile(String name, Class<T> clazz) {
		FileAccess access = openFile(name);
		
		if(!access.exists()) return null;
		
		Gson gson = newGson();
		
		String json = FileSystem.readText(access);
		
		access.close();
		
		return gson.fromJson(json, clazz);
	}
	
	public static <T> void saveFile(String name, Object o) {
		FileAccess access = openFile(name);
		
		Gson gson = newGson();
		
		String json = gson.toJson(o);
		
		FileSystem.writeText(json, access);
		
		access.close();
	}
	
	private static Gson newGson() {
		GsonBuilder builder = new GsonBuilder();
		
		builder.setPrettyPrinting();
		
		Gson gson = builder.create();
		
		return gson;
	}
	
	private static FileAccess openFile(String name) {
		File f = new File(SAVE_FOLDER, name + FILE_EXTENSION);
		
		return FileSystem.openFile(f, false);
	}
	
}
