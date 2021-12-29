package de.stylextv.maple.cache.io.serialize;

import java.util.BitSet;

import de.stylextv.maple.cache.CachedChunk;
import de.stylextv.maple.io.resource.StreamedResource;
import de.stylextv.maple.io.serialize.Serializer;

public class CachedChunkSerializer extends Serializer<CachedChunk> {
	
	@Override
	public CachedChunk readFrom(StreamedResource r) {
		int x = Serializer.INTEGER.readFrom(r);
		int z = Serializer.INTEGER.readFrom(r);
		
		int height = Serializer.INTEGER.readFrom(r);
		int bottomY = Serializer.INTEGER.readFrom(r);
		
		BitSet bitSet = Serializer.BIT_SET.readFrom(r);
		
		return new CachedChunk(x, z, height, bottomY, bitSet);
	}
	
	@Override
	public void writeTo(StreamedResource r, CachedChunk c) {
		int x = c.getX();
		int z = c.getZ();
		
		int height = c.getHeight();
		int bottomY = c.getBottomY();
		
		BitSet bitSet = c.getBitSet();
		
		Serializer.INTEGER.writeTo(r, x);
		Serializer.INTEGER.writeTo(r, z);
		
		Serializer.INTEGER.writeTo(r, height);
		Serializer.INTEGER.writeTo(r, bottomY);
		
		Serializer.BIT_SET.writeTo(r, bitSet);
	}
	
}
