package de.stylextv.lynx.io;

import java.io.File;
import java.math.BigInteger;
import java.nio.ByteBuffer;

import de.stylextv.lynx.Constants;
import de.stylextv.lynx.cache.CachedChunk;
import de.stylextv.lynx.cache.CachedRegion;
import de.stylextv.lynx.context.GameContext;

public class FileSystem {
	
	public static final File ROOT_FOLDER = new File(GameContext.directory(), "." + Constants.MOD_ID);
	
	public static CachedRegion readRegion(CachedRegion r, FileAccess f) {
		
		
		return r;
	}
	
	public static void writeRegion(CachedRegion r, FileAccess f) {
		
	}
	
	public static CachedChunk readChunk(FileAccess f) {
		
		
		return chunk;
	}
	
	public static void writeChunk(CachedChunk r, FileAccess f) {
		
	}
	
	public static int readBitSet(FileAccess f) {
		byte[] data = f.read(4);
		
		return ByteBuffer.wrap(data).getInt();
	}
	
	public static void writeBitSet(int i, FileAccess f) {
		byte[] data = ByteBuffer.allocate(4).putInt(i).array();;
		
		f.write(data);
	}
	
	public static int readInt(FileAccess f) {
		byte[] data = f.read(4);
		
		return ByteBuffer.wrap(data).getInt();
	}
	
	public static void writeInt(int i, FileAccess f) {
		byte[] data = ByteBuffer.allocate(4).putInt(i).array();;
		
		f.write(data);
	}
	
	public static String readText(FileAccess f) {
		String s = new String(f.readAll());
		
		return s;
	}
	
	public static void writeText(String s, FileAccess f) {
		f.write(s.getBytes());
	}
	
	public static FileAccess openFile(File f) {
		System.out.println("opening file: " + f);
		
		return new FileAccess(f);
	}
	
}
