package de.stylextv.maple.io.serialize.serializers;

import java.nio.ByteBuffer;

import de.stylextv.maple.io.resource.StreamedResource;
import de.stylextv.maple.io.serialize.Serializer;

public class IntegerSerializer extends Serializer<Integer> {
	
	private static final int SIZE_IN_BYTES = 4;
	
	@Override
	public Integer readFrom(StreamedResource r) {
		byte[] data = r.read(SIZE_IN_BYTES);
		
		ByteBuffer buffer = ByteBuffer.wrap(data);
		
		return buffer.getInt();
	}
	
	@Override
	public void writeTo(StreamedResource r, Integer i) {
		ByteBuffer buffer = ByteBuffer.allocate(SIZE_IN_BYTES);
		
		buffer.putInt(i);
		
		byte[] data = buffer.array();
		
		r.write(data);
	}
	
}
