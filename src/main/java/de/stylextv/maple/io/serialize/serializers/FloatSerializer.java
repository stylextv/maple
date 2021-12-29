package de.stylextv.maple.io.serialize.serializers;

import java.nio.ByteBuffer;

import de.stylextv.maple.io.resource.StreamedResource;
import de.stylextv.maple.io.serialize.Serializer;

public class FloatSerializer extends Serializer<Float> {
	
	private static final int SIZE_IN_BYTES = 4;
	
	@Override
	public Float readFrom(StreamedResource r) {
		byte[] data = r.read(SIZE_IN_BYTES);
		
		ByteBuffer buffer = ByteBuffer.wrap(data);
		
		return buffer.getFloat();
	}
	
	@Override
	public void writeTo(StreamedResource r, Float f) {
		ByteBuffer buffer = ByteBuffer.allocate(SIZE_IN_BYTES);
		
		buffer.putFloat(f);
		
		byte[] data = buffer.array();
		
		r.write(data);
	}
	
}
