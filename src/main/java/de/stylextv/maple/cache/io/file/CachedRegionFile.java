package de.stylextv.maple.cache.io.file;

import java.io.File;

import de.stylextv.maple.cache.CachedChunk;
import de.stylextv.maple.cache.CachedRegion;
import de.stylextv.maple.io.resource.types.StreamedFile;
import de.stylextv.maple.io.serialize.Serializer;

public class CachedRegionFile extends StreamedFile {
	
	public CachedRegionFile(File f) {
		super(f);
	}
	
	// TODO rework
	public void readRegion(CachedRegion r) {
		while(!isEmpty()) {
			
			CachedChunk c = Serializer.CACHED_CHUNK.readFrom(this);
			
			r.addChunk(c);
		}
	}
	
	public void writeRegion(CachedRegion r) {
		for(CachedChunk c : r.chunks()) {
			
			Serializer.CACHED_CHUNK.writeTo(this, c);
		}
	}
	
}
