package de.stylextv.lynx.io;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.BitSet;

import de.stylextv.lynx.Constants;
import de.stylextv.lynx.cache.CachedChunk;
import de.stylextv.lynx.cache.CachedRegion;
import de.stylextv.lynx.context.GameContext;

public class FileSystem {
	
	public static final File ROOT_FOLDER = new File(GameContext.directory(), "." + Constants.MOD_ID);
	
	public static CachedRegion readRegion(CachedRegion r, FileAccess f) {
		while(!f.isEmpty()) {
			
			CachedChunk chunk = readChunk(r, f);
			
			r.storeChunk(chunk);
		}
		
		return r;
	}
	
	public static void writeRegion(CachedRegion r, FileAccess f) {
		for(CachedChunk chunk : r.chunks()) {
			
			writeChunk(chunk, f);
		}
	}
	
	public static CachedChunk readChunk(CachedRegion r, FileAccess f) {
		int x = readInt(f);
		int z = readInt(f);
		
		BitSet bitSet = readBitSet(CachedChunk.BIT_AMOUNT, f);
		
		return new CachedChunk(r, x, z, bitSet);
	}
	
	public static void writeChunk(CachedChunk r, FileAccess f) {
		int x = r.getX();
		int z = r.getZ();
		
		BitSet bitSet = r.getBitSet();
		
		writeInt(x, f);
		writeInt(z, f);
		
		writeBitSet(bitSet, f);
	}
	
	public static BitSet readBitSet(int n, FileAccess f) {
		int l = n / 8;
		
		byte[] data = f.read(l);
		
		BitSet bitSet = BitSet.valueOf(data);
		
		return bitSet;
	}
	
	public static void writeBitSet(BitSet bitSet, FileAccess f) {
		int l = bitSet.size() / 8;
		
		
	}
	
	public static int readInt(FileAccess f) {
		byte[] data = f.read(4);
		
		ByteBuffer buffer = ByteBuffer.wrap(data);
		
		return buffer.getInt();
	}
	
	public static void writeInt(int i, FileAccess f) {
		ByteBuffer buffer = ByteBuffer.allocate(4).putInt(i);
		
		byte[] data = buffer.array();;
		
		f.write(data);
	}
	
	public static String readText(FileAccess f) {
		byte[] data = f.readAll();
		
		return new String(data);
	}
	
	public static void writeText(String s, FileAccess f) {
		byte[] data = s.getBytes();
		
		f.write(data);
	}
	
	public static FileAccess openFile(File f) {
		return new FileAccess(f);
	}
	
}
