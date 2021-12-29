package de.stylextv.maple.io.serialize.serializers;

import java.nio.ByteBuffer;

import de.stylextv.maple.io.resource.StreamedResource;
import de.stylextv.maple.io.serialize.Serializer;

public class LongSerializer extends Serializer<Long> {
	
	private static final int SIZE_IN_BYTES = 8;
	
	@Override
	public Long readFrom(StreamedResource r) {
		byte[] data = r.read(SIZE_IN_BYTES);
		
		ByteBuffer buffer = ByteBuffer.wrap(data);
		
		return buffer.getLong();
	}
	
	@Override
	public void writeTo(StreamedResource r, Long l) {
		ByteBuffer buffer = ByteBuffer.allocate(SIZE_IN_BYTES);
		
		buffer.putLong(l);
		
		byte[] data = buffer.array();
		
		r.write(data);
	}
	
}
